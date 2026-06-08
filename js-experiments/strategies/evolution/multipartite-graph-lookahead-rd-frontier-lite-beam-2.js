import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-lite-beam-2');

export const generateMultipartiteGraphLookaheadRdFrontierLiteBeam2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
