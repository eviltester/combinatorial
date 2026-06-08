import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-lite-candidate-6');

export const generateMultipartiteGraphLookaheadRdDensityLiteCandidate6Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
