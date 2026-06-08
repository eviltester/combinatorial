import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-strong-adaptive-penalty');

export const generateMultipartiteGraphLookaheadRdFrontierStrongAdaptivePenaltyRecords =
  createEvolutionLookaheadStrategy(DEFINITION.config);
