import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-lite-hybrid-tail-20');

export const generateMultipartiteGraphLookaheadRdDensityLiteHybridTail20Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
