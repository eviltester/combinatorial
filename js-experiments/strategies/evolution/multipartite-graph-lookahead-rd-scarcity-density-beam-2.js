import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-density-beam-2');

export const generateMultipartiteGraphLookaheadRdScarcityDensityBeam2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
