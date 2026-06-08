import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-density-hybrid-tail-20');

export const generateMultipartiteGraphLookaheadRdFrontierDensityHybridTail20Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
