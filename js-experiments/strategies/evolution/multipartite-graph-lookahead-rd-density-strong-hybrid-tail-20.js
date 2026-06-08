import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-strong-hybrid-tail-20');

export const generateMultipartiteGraphLookaheadRdDensityStrongHybridTail20Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
