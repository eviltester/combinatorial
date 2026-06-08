import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-density-beam-3');

export const generateMultipartiteGraphLookaheadRdScarcityDensityBeam3Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
