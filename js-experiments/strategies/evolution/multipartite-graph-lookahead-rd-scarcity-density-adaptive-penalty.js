import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition(
  'multipartite-graph-lookahead-rd-scarcity-density-adaptive-penalty'
);

export const generateMultipartiteGraphLookaheadRdScarcityDensityAdaptivePenaltyRecords =
  createEvolutionLookaheadStrategy(DEFINITION.config);
