import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-lite-candidate-6');

export const generateMultipartiteGraphLookaheadRdFrontierLiteCandidate6Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
