import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-density-candidate-6');

export const generateMultipartiteGraphLookaheadRdFrontierDensityCandidate6Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
