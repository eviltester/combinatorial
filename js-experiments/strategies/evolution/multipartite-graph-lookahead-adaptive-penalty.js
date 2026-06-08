import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-adaptive-penalty');

export const generateMultipartiteGraphLookaheadAdaptivePenaltyRecords = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
