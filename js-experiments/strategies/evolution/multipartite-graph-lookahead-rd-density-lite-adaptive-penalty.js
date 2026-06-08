import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-lite-adaptive-penalty');

export const generateMultipartiteGraphLookaheadRdDensityLiteAdaptivePenaltyRecords = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
