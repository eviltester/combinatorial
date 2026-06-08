import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-scarcity-frontier');

export const generateMultipartiteGraphLookaheadScarcityFrontierRecords = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
