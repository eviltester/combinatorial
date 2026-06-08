import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-density-candidate-2');

export const generateMultipartiteGraphLookaheadRdScarcityDensityCandidate2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
