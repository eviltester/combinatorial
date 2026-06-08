import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-strong-candidate-6');

export const generateMultipartiteGraphLookaheadRdFrontierStrongCandidate6Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
