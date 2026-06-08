import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-strong-hybrid-tail-20');

export const generateMultipartiteGraphLookaheadRdFrontierStrongHybridTail20Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
