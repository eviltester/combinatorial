import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-strong-beam-2');

export const generateMultipartiteGraphLookaheadRdScarcityStrongBeam2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
