import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-hardest-seed');

export const generateMultipartiteGraphLookaheadHardestSeedRecords = createEvolutionLookaheadStrategy(DEFINITION.config);
