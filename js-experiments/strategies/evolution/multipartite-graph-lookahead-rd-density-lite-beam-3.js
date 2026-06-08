import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-lite-beam-3');

export const generateMultipartiteGraphLookaheadRdDensityLiteBeam3Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
