import {
  buildNodeIndex,
  buildUncoveredTupleIncidence,
  completeRecord,
  createRecordFromVertices,
  createUncoveredIncidenceCache,
  createVertexId,
  getEdgeKey,
  tupleMatchesSelection,
} from './shared.js';

const DEFAULT_CONFIG = {
  beamWidth: 1,
  candidateLimit: 4,
  densityWeight: 0,
  frontierWeight: 0,
  lookaheadVertexLimit: 2,
  phaseSwitchUncoveredThreshold: null,
  seedMode: 'edge-pressure',
  partitionMode: 'open',
  penaltyMode: 'constant',
  scarcityWeight: 0,
  updateSyntheticUsageDuringFallback: true,
  useIncidenceCache: false,
};

function createSyntheticUsageState() {
  return {
    nodeUsageCounts: new Map(),
    edgeUsageCounts: new Map(),
  };
}

function getNodeUsageCount(syntheticUsage, vertex) {
  return syntheticUsage.nodeUsageCounts.get(createVertexId(vertex)) || 0;
}

function getEdgeUsageCount(syntheticUsage, leftVertex, rightVertex) {
  return syntheticUsage.edgeUsageCounts.get(getEdgeKey(leftVertex, rightVertex)) || 0;
}

function updateSyntheticUsageCounts(syntheticUsage, selectedVertices) {
  const uniqueVertices = [];
  const seenVertices = new Set();

  for (const vertex of selectedVertices) {
    const vertexId = createVertexId(vertex);
    if (seenVertices.has(vertexId)) {
      continue;
    }
    seenVertices.add(vertexId);
    uniqueVertices.push(vertex);
    syntheticUsage.nodeUsageCounts.set(vertexId, getNodeUsageCount(syntheticUsage, vertex) + 1);
  }

  for (let index = 0; index < uniqueVertices.length; index += 1) {
    for (let nextIndex = index + 1; nextIndex < uniqueVertices.length; nextIndex += 1) {
      const edgeKey = getEdgeKey(uniqueVertices[index], uniqueVertices[nextIndex]);
      syntheticUsage.edgeUsageCounts.set(
        edgeKey,
        getEdgeUsageCount(syntheticUsage, uniqueVertices[index], uniqueVertices[nextIndex]) + 1
      );
    }
  }
}

function getRowVertices(context, completedRecord) {
  return context.parameters.map((parameter, parameterIndex) => ({
    parameterIndex,
    parameterName: parameter.name,
    value: completedRecord.get(parameter.name),
  }));
}

function countCoveredTuples(model) {
  let coveredTuples = 0;

  for (const target of model.coverageTargets) {
    coveredTuples += model.coverage.get(target.key).size;
  }

  return coveredTuples;
}

function calculateTupleScarcity(tuple, nodeWeights, edgeWeights) {
  let nodePressure = 0;
  let edgePressure = 0;

  for (const vertex of tuple.vertices) {
    nodePressure += 1 / Math.max(1, nodeWeights.get(createVertexId(vertex)) || 0);
  }

  for (let index = 0; index < tuple.vertices.length; index += 1) {
    for (let nextIndex = index + 1; nextIndex < tuple.vertices.length; nextIndex += 1) {
      edgePressure +=
        1 / Math.max(1, edgeWeights.get(getEdgeKey(tuple.vertices[index], tuple.vertices[nextIndex])) || 0);
    }
  }

  return nodePressure + edgePressure;
}

function tupleWeight(tuple, config, nodeWeights, edgeWeights) {
  return 1 + calculateTupleScarcity(tuple, nodeWeights, edgeWeights) * (config.scarcityWeight / 100);
}

function buildSeedEdges(uncoveredTuples, syntheticUsage, config, nodeWeights, edgeWeights) {
  const seedEdges = new Map();

  for (const tuple of uncoveredTuples) {
    const scarcity = calculateTupleScarcity(tuple, nodeWeights, edgeWeights);

    for (let index = 0; index < tuple.vertices.length; index += 1) {
      const leftVertex = tuple.vertices[index];
      for (let nextIndex = index + 1; nextIndex < tuple.vertices.length; nextIndex += 1) {
        const rightVertex = tuple.vertices[nextIndex];
        const edgeKey = getEdgeKey(leftVertex, rightVertex);
        const existing = seedEdges.get(edgeKey);
        const weight = config.seedMode === 'scarcity' || config.seedMode === 'hardest-tuple' ? 1 + scarcity : 1;

        if (existing) {
          existing.uncoveredEdgeWeight += weight;
          existing.scarcityScore += scarcity;
        } else {
          seedEdges.set(edgeKey, {
            leftVertex,
            rightVertex,
            uncoveredEdgeWeight: weight,
            scarcityScore: scarcity,
            syntheticUsageCount: getEdgeUsageCount(syntheticUsage, leftVertex, rightVertex),
          });
        }
      }
    }
  }

  return [...seedEdges.values()];
}

function chooseByScore(items, getScore, random) {
  let bestItems = [];
  let bestScore = Number.NEGATIVE_INFINITY;

  for (const item of items) {
    const score = getScore(item);
    if (score > bestScore) {
      bestScore = score;
      bestItems = [item];
    } else if (score === bestScore) {
      bestItems.push(item);
    }
  }

  return bestItems.length > 0 ? bestItems[Math.floor(random() * bestItems.length)] : null;
}

function chooseSeedEdge(seedEdges, nodeWeights, config, random) {
  return chooseByScore(
    seedEdges,
    (edge) => {
      const nodeWeightSum =
        (nodeWeights.get(createVertexId(edge.leftVertex)) || 0) +
        (nodeWeights.get(createVertexId(edge.rightVertex)) || 0);
      const scarcityScore = config.seedMode === 'hardest-tuple' ? edge.scarcityScore * 1000 : edge.scarcityScore * 100;
      const pressureScore = edge.uncoveredEdgeWeight * 100 + nodeWeightSum;
      const reusePenalty = edge.syntheticUsageCount * (config.seedMode === 'hardest-tuple' ? 5 : 25);

      return scarcityScore + pressureScore - reusePenalty;
    },
    random
  );
}

function hasConsistentTuple(uncoveredTuples, selectedVertices, candidateNode) {
  const nextSelection = [...selectedVertices, candidateNode];
  return uncoveredTuples.some((tuple) => tupleMatchesSelection(tuple, nextSelection));
}

function calculateChainWeight(uncoveredTuples, selectedVertices, candidateNode, config, nodeWeights, edgeWeights) {
  const nextSelection = [...selectedVertices, candidateNode];
  let weight = 0;

  for (const tuple of uncoveredTuples) {
    if (tupleMatchesSelection(tuple, nextSelection)) {
      weight += tupleWeight(tuple, config, nodeWeights, edgeWeights);
    }
  }

  return weight;
}

function calculateEdgeSum(selectedVertices, candidateNode, edgeWeights) {
  return selectedVertices.reduce(
    (total, vertex) => total + (edgeWeights.get(getEdgeKey(candidateNode, vertex)) || 0),
    0
  );
}

function calculateCoverageProgress(context) {
  const totalTuples = context.model.getTotalTargetTupleCount();
  return totalTuples > 0 ? countCoveredTuples(context.model) / totalTuples : 1;
}

function calculateSyntheticPenalty(context, selectedVertices, candidateNode, syntheticUsage, config) {
  let multiplier = 1;

  if (config.penaltyMode === 'adaptive') {
    multiplier = 0.35 + calculateCoverageProgress(context) * 1.4;
  } else if (config.penaltyMode === 'light') {
    multiplier = 0.5;
  } else if (config.penaltyMode === 'strong') {
    multiplier = 1.6;
  }

  const nodePenalty = getNodeUsageCount(syntheticUsage, candidateNode) * 10;
  const edgePenalty =
    selectedVertices.reduce((total, vertex) => total + getEdgeUsageCount(syntheticUsage, candidateNode, vertex), 0) * 5;
  return (nodePenalty + edgePenalty) * multiplier;
}

function calculateLookaheadTupleGain(context, selectedVertices, candidateNode) {
  context.benchmarkDetails.lookaheadEvaluations += 1;
  const partialRecord = createRecordFromVertices(context, [...selectedVertices, candidateNode]);
  const completedRecord = completeRecord(context.model, context.parameters, partialRecord);
  return context.model.calculateCoverageScore(completedRecord);
}

function calculateFrontierScore(allNodes, uncoveredTuples, nextSelection, selectedParameters) {
  let score = 0;

  for (const node of allNodes) {
    if (selectedParameters.has(node.parameterIndex)) {
      continue;
    }

    if (hasConsistentTuple(uncoveredTuples, nextSelection, node)) {
      score += 1;
    }
  }

  return score;
}

function createProgressRecord(context, tuple = context.model.getFirstUncoveredTuple()) {
  if (!tuple) {
    return null;
  }

  return completeRecord(context.model, context.parameters, context.model.createRecordFromTuple(tuple));
}

function getCompatibleCandidatesForParameter(
  allNodes,
  uncoveredTuples,
  selectedVertices,
  selectedParameters,
  parameterIndex
) {
  return allNodes.filter(
    (node) =>
      node.parameterIndex === parameterIndex &&
      !selectedParameters.has(node.parameterIndex) &&
      hasConsistentTuple(uncoveredTuples, selectedVertices, node)
  );
}

function getCandidatePool(allNodes, uncoveredTuples, selectedVertices, selectedParameters, config) {
  if (config.partitionMode !== 'hardest') {
    return allNodes.filter(
      (node) =>
        !selectedParameters.has(node.parameterIndex) && hasConsistentTuple(uncoveredTuples, selectedVertices, node)
    );
  }

  let bestCandidates = [];
  let smallestCompatibleCount = Number.POSITIVE_INFINITY;

  for (const node of allNodes) {
    if (selectedParameters.has(node.parameterIndex)) {
      continue;
    }

    const candidates = getCompatibleCandidatesForParameter(
      allNodes,
      uncoveredTuples,
      selectedVertices,
      selectedParameters,
      node.parameterIndex
    );

    if (candidates.length > 0 && candidates.length < smallestCompatibleCount) {
      smallestCompatibleCount = candidates.length;
      bestCandidates = candidates;
    }
  }

  return bestCandidates;
}

function scoreCandidate(
  context,
  config,
  allNodes,
  uncoveredTuples,
  nodeWeights,
  edgeWeights,
  selectedVertices,
  selectedParameters,
  syntheticUsage,
  candidateNode
) {
  context.benchmarkDetails.candidateEvaluations += 1;

  const chainWeight = calculateChainWeight(
    uncoveredTuples,
    selectedVertices,
    candidateNode,
    config,
    nodeWeights,
    edgeWeights
  );
  const edgeSum = calculateEdgeSum(selectedVertices, candidateNode, edgeWeights);
  const nodeWeight = nodeWeights.get(createVertexId(candidateNode)) || 0;
  const nextSelection = [...selectedVertices, candidateNode];
  const nextParameters = new Set([...selectedParameters, candidateNode.parameterIndex]);
  const completionWeight = context.model.calculateCoverageScore(createRecordFromVertices(context, nextSelection));
  const shouldUseLookahead = selectedVertices.length <= config.lookaheadVertexLimit;
  const newTupleGain = shouldUseLookahead
    ? calculateLookaheadTupleGain(context, selectedVertices, candidateNode)
    : completionWeight;
  const frontierScore =
    config.frontierWeight > 0 ? calculateFrontierScore(allNodes, uncoveredTuples, nextSelection, nextParameters) : 0;
  const densityScore = config.densityWeight > 0 ? chainWeight / Math.max(1, nextSelection.length) : 0;
  const syntheticPenalty = calculateSyntheticPenalty(context, selectedVertices, candidateNode, syntheticUsage, config);

  return {
    node: candidateNode,
    score:
      newTupleGain * 10000 +
      chainWeight * 1000 +
      edgeSum * 100 +
      nodeWeight * 10 +
      completionWeight +
      frontierScore * config.frontierWeight +
      densityScore * config.densityWeight -
      syntheticPenalty,
  };
}

function scorePartial(context, selectedVertices) {
  const partialRecord = createRecordFromVertices(context, selectedVertices);
  const completedRecord = completeRecord(context.model, context.parameters, partialRecord);
  return context.model.calculateCoverageScore(completedRecord);
}

function selectCandidateScores(
  context,
  config,
  allNodes,
  uncoveredTuples,
  nodeWeights,
  edgeWeights,
  selectedVertices,
  selectedParameters,
  syntheticUsage
) {
  const scoredCandidates = getCandidatePool(
    allNodes,
    uncoveredTuples,
    selectedVertices,
    selectedParameters,
    config
  ).map((node) =>
    scoreCandidate(
      context,
      config,
      allNodes,
      uncoveredTuples,
      nodeWeights,
      edgeWeights,
      selectedVertices,
      selectedParameters,
      syntheticUsage,
      node
    )
  );

  return scoredCandidates.sort((left, right) => right.score - left.score).slice(0, config.candidateLimit);
}

function buildRowWithGreedySelection(
  context,
  config,
  allNodes,
  uncoveredTuples,
  nodeWeights,
  edgeWeights,
  seedEdge,
  syntheticUsage
) {
  const selectedVertices = [seedEdge.leftVertex, seedEdge.rightVertex];
  const selectedParameters = new Set([seedEdge.leftVertex.parameterIndex, seedEdge.rightVertex.parameterIndex]);

  while (selectedVertices.length < context.parameters.length) {
    const bestCandidate = chooseByScore(
      selectCandidateScores(
        context,
        config,
        allNodes,
        uncoveredTuples,
        nodeWeights,
        edgeWeights,
        selectedVertices,
        selectedParameters,
        syntheticUsage
      ),
      (candidate) => candidate.score,
      context.random
    );

    if (!bestCandidate) {
      break;
    }

    selectedVertices.push(bestCandidate.node);
    selectedParameters.add(bestCandidate.node.parameterIndex);
  }

  return selectedVertices;
}

function buildRowWithBeamSelection(
  context,
  config,
  allNodes,
  uncoveredTuples,
  nodeWeights,
  edgeWeights,
  seedEdge,
  syntheticUsage
) {
  let beams = [
    {
      selectedVertices: [seedEdge.leftVertex, seedEdge.rightVertex],
      selectedParameters: new Set([seedEdge.leftVertex.parameterIndex, seedEdge.rightVertex.parameterIndex]),
      score: 0,
    },
  ];

  while (beams.some((beam) => !beam.terminal && beam.selectedVertices.length < context.parameters.length)) {
    const nextBeams = [];
    let expandedAnyBeam = false;

    for (const beam of beams) {
      if (beam.terminal || beam.selectedVertices.length >= context.parameters.length) {
        nextBeams.push(beam);
        continue;
      }

      const candidates = selectCandidateScores(
        context,
        config,
        allNodes,
        uncoveredTuples,
        nodeWeights,
        edgeWeights,
        beam.selectedVertices,
        beam.selectedParameters,
        syntheticUsage
      );

      if (candidates.length === 0) {
        nextBeams.push({ ...beam, terminal: true });
        continue;
      }

      expandedAnyBeam = true;
      for (const candidate of candidates) {
        nextBeams.push({
          selectedVertices: [...beam.selectedVertices, candidate.node],
          selectedParameters: new Set([...beam.selectedParameters, candidate.node.parameterIndex]),
          score: beam.score + candidate.score,
        });
      }
    }

    if (nextBeams.length === 0) {
      break;
    }

    if (!expandedAnyBeam) {
      beams = nextBeams;
      break;
    }

    beams = nextBeams
      .map((beam) => ({
        ...beam,
        score: beam.score + scorePartial(context, beam.selectedVertices) * 100000,
      }))
      .sort((left, right) => right.score - left.score)
      .slice(0, config.beamWidth);
  }

  const bestBeam = chooseByScore(
    beams,
    (beam) => beam.score + scorePartial(context, beam.selectedVertices) * 100000,
    context.random
  );
  return bestBeam?.selectedVertices || [];
}

function commitRecord(context, config, syntheticUsage, completedRecord, rowMode, hasSwitchedToFallbackTail) {
  context.dataRecords.push(completedRecord);
  context.model.updateCoverage(completedRecord);

  if (rowMode === 'graph') {
    context.benchmarkDetails.rowsGeneratedByGraphPhase += 1;
  } else {
    context.benchmarkDetails.rowsGeneratedByFallback += 1;
  }

  const shouldUpdateSyntheticUsage =
    rowMode === 'graph' || (!hasSwitchedToFallbackTail && config.updateSyntheticUsageDuringFallback);

  if (shouldUpdateSyntheticUsage) {
    updateSyntheticUsageCounts(syntheticUsage, getRowVertices(context, completedRecord));
  }
}

export function createEvolutionLookaheadStrategy(strategyConfig) {
  const config = Object.freeze({ ...DEFAULT_CONFIG, ...strategyConfig });

  return function generateEvolutionLookaheadVariant(context) {
    const { model } = context;
    const allNodes = buildNodeIndex(context.parameters);
    const syntheticUsage = createSyntheticUsageState();
    const totalTuples = model.getTotalTargetTupleCount();
    const uncoveredIncidenceCache = config.useIncidenceCache ? createUncoveredIncidenceCache(context) : null;
    let hasSwitchedToFallbackTail = false;

    while (!model.isFullyCovered()) {
      const firstUncoveredTuple = model.getFirstUncoveredTuple();
      if (!firstUncoveredTuple) {
        break;
      }

      if (config.phaseSwitchUncoveredThreshold !== null && !hasSwitchedToFallbackTail) {
        const uncoveredFraction = totalTuples > 0 ? (totalTuples - countCoveredTuples(model)) / totalTuples : 0;
        if (uncoveredFraction <= config.phaseSwitchUncoveredThreshold) {
          hasSwitchedToFallbackTail = true;
          context.benchmarkDetails.phaseSwitchRow = context.dataRecords.length + 1;
        }
      }

      if (hasSwitchedToFallbackTail) {
        const fallbackRecord = createProgressRecord(context, firstUncoveredTuple);
        if (!fallbackRecord) {
          break;
        }

        commitRecord(context, config, syntheticUsage, fallbackRecord, 'fallback', hasSwitchedToFallbackTail);
        uncoveredIncidenceCache?.invalidate();
        continue;
      }

      const { uncoveredTuples, nodeWeights, edgeWeights } = uncoveredIncidenceCache
        ? uncoveredIncidenceCache.getCurrent()
        : buildUncoveredTupleIncidence(context);

      if (uncoveredTuples.length === 0) {
        break;
      }

      const seedEdges = buildSeedEdges(uncoveredTuples, syntheticUsage, config, nodeWeights, edgeWeights);
      context.benchmarkDetails.seedEdgesConsidered += seedEdges.length;
      const seedEdge = chooseSeedEdge(seedEdges, nodeWeights, config, context.random);
      let completedRecord = null;
      let rowMode = 'graph';

      if (!seedEdge) {
        completedRecord = createProgressRecord(context, firstUncoveredTuple);
        rowMode = 'fallback';
      }

      if (!completedRecord) {
        const selectedVertices =
          config.beamWidth > 1
            ? buildRowWithBeamSelection(
                context,
                config,
                allNodes,
                uncoveredTuples,
                nodeWeights,
                edgeWeights,
                seedEdge,
                syntheticUsage
              )
            : buildRowWithGreedySelection(
                context,
                config,
                allNodes,
                uncoveredTuples,
                nodeWeights,
                edgeWeights,
                seedEdge,
                syntheticUsage
              );

        completedRecord = completeRecord(
          model,
          context.parameters,
          createRecordFromVertices(context, selectedVertices)
        );
      }

      if (!completedRecord || model.calculateCoverageScore(completedRecord) <= 0) {
        completedRecord = createProgressRecord(context, firstUncoveredTuple);
        rowMode = 'fallback';
      }

      if (!completedRecord) {
        break;
      }

      commitRecord(context, config, syntheticUsage, completedRecord, rowMode, hasSwitchedToFallbackTail);
      uncoveredIncidenceCache?.invalidate();
    }
  };
}
