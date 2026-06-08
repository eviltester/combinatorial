import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-beam-2');

export const generateMultipartiteGraphLookaheadBeam2Records = createEvolutionLookaheadStrategy(DEFINITION.config);
