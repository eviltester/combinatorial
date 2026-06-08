import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-rd-frontier-strong-beam-3');

export const generateMultipartiteGraphLookaheadRdFrontierStrongBeam3Records = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
