import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-density-lite-candidate-2');

export const generateMultipartiteGraphLookaheadRdDensityLiteCandidate2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
