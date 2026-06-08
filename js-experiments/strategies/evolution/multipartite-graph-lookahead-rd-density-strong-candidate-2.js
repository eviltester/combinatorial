import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-strong-candidate-2');

export const generateMultipartiteGraphLookaheadRdDensityStrongCandidate2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
