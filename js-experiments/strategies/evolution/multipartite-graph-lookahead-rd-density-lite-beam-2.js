import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-lite-beam-2');

export const generateMultipartiteGraphLookaheadRdDensityLiteBeam2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
