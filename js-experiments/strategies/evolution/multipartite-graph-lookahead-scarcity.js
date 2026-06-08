import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-scarcity');

export const generateMultipartiteGraphLookaheadScarcityRecords = createEvolutionLookaheadStrategy(DEFINITION.config);
