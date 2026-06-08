import { ExperimentalNWiseGenerator } from './experimentalNWiseGenerator.js';
import { NWiseAlgorithm, NWiseGenerator } from './stable/n-wise/nWiseGenerator.js';

function createParameters(parameterCount, valueCount) {
  return Array.from({ length: parameterCount }, (_, parameterIndex) => ({
    name: `P${parameterIndex + 1}`,
    values: Array.from({ length: valueCount }, (_, valueIndex) => `${parameterIndex + 1}.${valueIndex + 1}`),
  }));
}

const experimentalGenerator = new ExperimentalNWiseGenerator(createParameters(5, 3), {
  algorithm: 'multipartite-graph-lookahead-rd-scarcity-density-beam-3',
  strength: 3,
  candidateCount: 8,
  seed: 17,
});
experimentalGenerator.generateDataSet();

const stableGenerator = new NWiseGenerator(createParameters(5, 3), {
  algorithm: NWiseAlgorithm.GREEDY,
  strength: 3,
  candidateCount: 8,
  seed: 17,
});
stableGenerator.generateDataSet();

for (const stats of [experimentalGenerator.getCoverageStats(), stableGenerator.getCoverageStats()]) {
  if (Math.abs(stats.coveragePercentage - 100) > 0.00001) {
    throw new Error(`Expected full coverage, got ${stats.coveragePercentage}`);
  }
}

process.stdout.write('Smoke test passed: experimental and stable generators reached 100% coverage.\n');
