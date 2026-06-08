import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-lite-adaptive-penalty');

export const generateMultipartiteGraphLookaheadRdFrontierLiteAdaptivePenaltyRecords = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
