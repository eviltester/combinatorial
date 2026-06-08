import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-lite-beam-3');

export const generateMultipartiteGraphLookaheadRdFrontierLiteBeam3Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
