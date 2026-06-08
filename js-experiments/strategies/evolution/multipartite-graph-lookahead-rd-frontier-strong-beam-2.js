import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-strong-beam-2');

export const generateMultipartiteGraphLookaheadRdFrontierStrongBeam2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
