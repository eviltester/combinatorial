import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-strong-candidate-6');

export const generateMultipartiteGraphLookaheadRdDensityStrongCandidate6Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
