import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-strong-beam-3');

export const generateMultipartiteGraphLookaheadRdScarcityStrongBeam3Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
