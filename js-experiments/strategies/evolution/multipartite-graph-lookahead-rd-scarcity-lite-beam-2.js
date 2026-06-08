import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-lite-beam-2');

export const generateMultipartiteGraphLookaheadRdScarcityLiteBeam2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
