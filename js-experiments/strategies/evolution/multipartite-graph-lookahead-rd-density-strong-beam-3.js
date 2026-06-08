import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-strong-beam-3');

export const generateMultipartiteGraphLookaheadRdDensityStrongBeam3Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
