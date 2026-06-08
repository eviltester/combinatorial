import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20');

export const generateMultipartiteGraphLookaheadRdScarcityLiteHybridTail20Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
