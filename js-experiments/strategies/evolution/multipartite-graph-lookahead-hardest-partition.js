import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('multipartite-graph-lookahead-hardest-partition');

export const generateMultipartiteGraphLookaheadHardestPartitionRecords = createEvolutionLookaheadStrategy(
  DEFINITION.config
);
