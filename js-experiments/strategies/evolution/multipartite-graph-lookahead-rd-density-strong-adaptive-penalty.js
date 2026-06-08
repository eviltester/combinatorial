import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-strong-adaptive-penalty');

export const generateMultipartiteGraphLookaheadRdDensityStrongAdaptivePenaltyRecords = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
