import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-strong-candidate-2');

export const generateMultipartiteGraphLookaheadRdScarcityStrongCandidate2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
