import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-beam-3');

export const generateMultipartiteGraphLookaheadBeam3Records = createEvolutionLookaheadStrategy(DEFINITION.config);
