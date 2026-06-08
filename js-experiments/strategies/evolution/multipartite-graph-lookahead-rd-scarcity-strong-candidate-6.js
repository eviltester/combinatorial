import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-strong-candidate-6');

export const generateMultipartiteGraphLookaheadRdScarcityStrongCandidate6Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
