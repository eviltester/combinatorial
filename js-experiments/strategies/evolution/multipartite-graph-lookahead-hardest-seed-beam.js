import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-hardest-seed-beam');

export const generateMultipartiteGraphLookaheadHardestSeedBeamRecords = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
