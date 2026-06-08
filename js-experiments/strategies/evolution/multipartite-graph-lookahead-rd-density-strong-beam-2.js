import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-strong-beam-2');

export const generateMultipartiteGraphLookaheadRdDensityStrongBeam2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
