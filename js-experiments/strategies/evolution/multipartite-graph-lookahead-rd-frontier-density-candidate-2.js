import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-density-candidate-2');

export const generateMultipartiteGraphLookaheadRdFrontierDensityCandidate2Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
