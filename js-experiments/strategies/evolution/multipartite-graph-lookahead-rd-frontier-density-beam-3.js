import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-density-beam-3');

export const generateMultipartiteGraphLookaheadRdFrontierDensityBeam3Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
