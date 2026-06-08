import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-lite-candidate-2');

export const generateMultipartiteGraphLookaheadRdFrontierLiteCandidate2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
