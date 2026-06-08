import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-strong-adaptive-penalty');

export const generateMultipartiteGraphLookaheadRdScarcityStrongAdaptivePenaltyRecords =
  createEvolutionLookaheadStrategy(DEFINITION.config);
