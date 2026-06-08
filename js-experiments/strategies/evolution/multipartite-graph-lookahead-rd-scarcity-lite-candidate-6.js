import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-lite-candidate-6');

export const generateMultipartiteGraphLookaheadRdScarcityLiteCandidate6Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
