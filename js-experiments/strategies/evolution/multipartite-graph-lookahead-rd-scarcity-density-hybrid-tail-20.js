import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-density-hybrid-tail-20');

export const generateMultipartiteGraphLookaheadRdScarcityDensityHybridTail20Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
