import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-lite-hybrid-tail-20');

export const generateMultipartiteGraphLookaheadRdFrontierLiteHybridTail20Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
