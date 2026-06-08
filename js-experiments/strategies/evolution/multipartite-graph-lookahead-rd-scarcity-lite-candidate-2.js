import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-scarcity-lite-candidate-2');

export const generateMultipartiteGraphLookaheadRdScarcityLiteCandidate2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
