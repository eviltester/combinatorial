import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-lite-adaptive-penalty');

export const generateMultipartiteGraphLookaheadRdScarcityLiteAdaptivePenaltyRecords = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
