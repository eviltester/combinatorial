import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-density');

export const generateMultipartiteGraphLookaheadDensityRecords = createEvolutionLookaheadStrategy(DEFINITION.config);
