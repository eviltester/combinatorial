import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-strong-candidate-2');

export const generateMultipartiteGraphLookaheadRdFrontierStrongCandidate2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
