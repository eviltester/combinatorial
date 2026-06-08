import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-strong-hybrid-tail-20');

export const generateMultipartiteGraphLookaheadRdScarcityStrongHybridTail20Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
