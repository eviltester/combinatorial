import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition(
  'multipartite-graph-lookahead-rd-frontier-density-adaptive-penalty'
);

export const generateMultipartiteGraphLookaheadRdFrontierDensityAdaptivePenaltyRecords =
  createEvolutionLookaheadStrategy(DEFINITION.config);
