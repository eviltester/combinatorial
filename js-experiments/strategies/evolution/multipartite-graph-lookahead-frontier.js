import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-frontier');

export const generateMultipartiteGraphLookaheadFrontierRecords = createEvolutionLookaheadStrategy(DEFINITION.config);
