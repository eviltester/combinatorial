import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-density-candidate-6');

export const generateMultipartiteGraphLookaheadRdScarcityDensityCandidate6Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
