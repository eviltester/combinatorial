import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-lite-beam-3');

export const generateMultipartiteGraphLookaheadRdScarcityLiteBeam3Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
