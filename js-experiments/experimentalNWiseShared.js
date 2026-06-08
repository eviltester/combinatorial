import {
  cloneRecords,
  computeMemoryDelta,
  createSeededRandom,
  readProcessMemoryUsage,
} from './stable/n-wise/nWiseShared.js';
import { EVOLUTION_ALGORITHM_IDS } from './experimentalNWiseEvolutionAlgorithms.js';

export const ExperimentalNWiseAlgorithm = Object.freeze({
  MULTIPARTITE_GRAPH_WALK: 'multipartite-graph-walk',
  MULTIPARTITE_GRAPH_LOOKAHEAD: 'multipartite-graph-lookahead',
  MULTIPARTITE_GRAPH_LOOKAHEAD_ADAPTIVE: 'multipartite-graph-lookahead-adaptive',
  MULTIPARTITE_GRAPH_LOOKAHEAD_HYBRID: 'multipartite-graph-lookahead-hybrid',
});

export const SUPPORTED_EXPERIMENTAL_N_WISE_ALGORITHMS = new Set([
  ...Object.values(ExperimentalNWiseAlgorithm),
  ...EVOLUTION_ALGORITHM_IDS,
]);

export { cloneRecords, computeMemoryDelta, createSeededRandom, readProcessMemoryUsage };
