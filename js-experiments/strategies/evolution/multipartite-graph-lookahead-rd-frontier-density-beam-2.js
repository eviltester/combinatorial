import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-density-beam-2');

export const generateMultipartiteGraphLookaheadRdFrontierDensityBeam2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
