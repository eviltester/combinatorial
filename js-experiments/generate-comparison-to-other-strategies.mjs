import { readFileSync, writeFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

import { ExperimentalNWiseGenerator } from './experimentalNWiseGenerator.js';
import { NWiseAlgorithm, NWiseGenerator } from './stable/n-wise/nWiseGenerator.js';
import { PairwiseGenerator } from './stable/n-wise/pairwiseGenerator.js';

const EXPERIMENT_DIR = dirname(fileURLToPath(import.meta.url));
const EXPERIMENT_RESULT_PATH = join(EXPERIMENT_DIR, 'n-wise-experiment-results.json');
const COMPARISON_REPORT_PATH = join(EXPERIMENT_DIR, 'comparison-to-other-strategies.md');
const SCENARIOS = [
  { parameterCount: 6, valueCount: 3, candidateCount: 8 },
  { parameterCount: 6, valueCount: 4, candidateCount: 8 },
];
const BASELINE_EXPERIMENTS = new Set([
  'multipartite-graph-walk',
  'multipartite-graph-lookahead',
  'multipartite-graph-lookahead-adaptive',
  'multipartite-graph-lookahead-hybrid',
]);

function createParameters(parameterCount, valueCount) {
  return Array.from({ length: parameterCount }, (_, parameterIndex) => ({
    name: `P${parameterIndex + 1}`,
    values: Array.from({ length: valueCount }, (_, valueIndex) => `${parameterIndex + 1}.${valueIndex + 1}`),
  }));
}

function runStableCase({ algorithm, parameterCount, valueCount, strength, candidateCount }) {
  if (typeof global.gc === 'function') {
    global.gc();
  }

  const generator = new NWiseGenerator(createParameters(parameterCount, valueCount), {
    algorithm,
    strength,
    candidateCount,
    runs: algorithm === NWiseAlgorithm.AETG ? 2 : 1,
    seed: 17,
  });

  generator.generateDataSet();
  return generator.getBenchmarkStats();
}

function runPairwiseCase({ parameterCount, valueCount }) {
  if (typeof global.gc === 'function') {
    global.gc();
  }

  const generator = new PairwiseGenerator(createParameters(parameterCount, valueCount));
  const startedAtMs = Date.now();
  generator.generateDataSet();
  const stats = generator.getCoverageStats();

  return {
    algorithm: 'pairwise-legacy',
    strength: 2,
    parameterCount,
    valueCounts: Array(parameterCount).fill(valueCount),
    rowCount: stats.totalRecords,
    runtimeMs: Date.now() - startedAtMs,
    coveragePercentage: stats.coveragePercentage,
  };
}

function runStableStrategies() {
  const rows = [];

  for (const scenario of SCENARIOS) {
    rows.push({
      ...runPairwiseCase(scenario),
      shape: `${scenario.parameterCount}x${scenario.valueCount}`,
    });

    for (let strength = 2; strength <= scenario.parameterCount; strength += 1) {
      for (const algorithm of Object.values(NWiseAlgorithm)) {
        rows.push({
          ...runStableCase({
            ...scenario,
            algorithm,
            strength,
          }),
          shape: `${scenario.parameterCount}x${scenario.valueCount}`,
        });
      }
    }
  }

  return rows;
}

function readNewExperimentRows() {
  const cache = JSON.parse(readFileSync(EXPERIMENT_RESULT_PATH, 'utf8')).results;

  return Object.values(cache)
    .filter((row) => row.strength < row.parameterCount)
    .filter((row) => !BASELINE_EXPERIMENTS.has(row.algorithm))
    .map((row) => ({
      ...row,
      shape: `${row.parameterCount}x${row.valueCounts[0]}`,
    }));
}

function summarizeRows(rows, group) {
  const aggregates = new Map();

  for (const row of rows.filter((item) => item.strength < item.parameterCount)) {
    const aggregate = aggregates.get(row.algorithm) || {
      algorithm: row.algorithm,
      group,
      totalRows: 0,
      totalRuntimeMs: 0,
      caseCount: 0,
    };

    aggregate.totalRows += row.rowCount;
    aggregate.totalRuntimeMs += row.runtimeMs;
    aggregate.caseCount += 1;
    aggregates.set(row.algorithm, aggregate);
  }

  return [...aggregates.values()].map((row) => ({
    ...row,
    averageRows: row.totalRows / row.caseCount,
    averageRuntimeMs: row.totalRuntimeMs / row.caseCount,
  }));
}

function buildSummaryTable(rows) {
  return [
    '| Group | Strategy | Cases | Avg rows | Avg runtime ms |',
    '| --- | --- | ---: | ---: | ---: |',
    ...rows.map(
      (row) =>
        `| ${row.group} | \`${row.algorithm}\` | ${row.caseCount} | ${row.averageRows.toFixed(
          1
        )} | ${row.averageRuntimeMs.toFixed(1)} |`
    ),
  ].join('\n');
}

function buildCaseTable(rows) {
  return [
    '| Shape | Strength | Strategy | Rows | Runtime ms | Coverage % |',
    '| --- | ---: | --- | ---: | ---: | ---: |',
    ...rows.map(
      (row) =>
        `| ${row.shape} | ${row.strength} | \`${row.algorithm}\` | ${row.rowCount} | ${row.runtimeMs} | ${row.coveragePercentage.toFixed(
          1
        )} |`
    ),
  ].join('\n');
}

function buildMarkdownReport() {
  const stableRows = runStableStrategies();
  const newExperimentRows = readNewExperimentRows();
  const stableSummary = summarizeRows(stableRows, 'Stable app strategy');
  const pairwiseSummary = stableSummary.filter((row) => row.algorithm === 'pairwise-legacy');
  const stableNWiseSummary = stableSummary.filter((row) => row.algorithm !== 'pairwise-legacy');
  const newExperimentSummary = summarizeRows(newExperimentRows, 'New JS experiment');
  const bestStableByRows = [...stableNWiseSummary].sort((left, right) => left.totalRows - right.totalRows)[0];
  const bestStableByRuntime = [...stableNWiseSummary].sort((left, right) => left.totalRuntimeMs - right.totalRuntimeMs)[0];
  const bestNewByRows = [...newExperimentSummary].sort((left, right) => left.totalRows - right.totalRows)[0];
  const bestNewByRuntime = [...newExperimentSummary].sort((left, right) => left.totalRuntimeMs - right.totalRuntimeMs)[0];
  const sortedStableSummary = [...stableNWiseSummary].sort((left, right) => left.averageRows - right.averageRows);
  const sortedNewSummary = [...newExperimentSummary].sort((left, right) => left.averageRows - right.averageRows);
  const topNewRows = sortedNewSummary.slice(0, 10);
  const stableCaseRows = stableRows
    .filter((row) => row.strength < row.parameterCount)
    .sort((left, right) => {
      if (left.shape !== right.shape) {
        return left.shape.localeCompare(right.shape);
      }
      if (left.strength !== right.strength) {
        return left.strength - right.strength;
      }
      return left.rowCount - right.rowCount;
    });

  return `# Comparison To Other Strategies

This compares the new JavaScript n-wise experiments against the stable n-wise and pairwise strategies currently used in the grid-table-editor app. The n-wise comparison uses the same benchmark shapes as the experimental report:

- 6 parameters x 3 values
- 6 parameters x 4 values
- strengths 2 through 6
- non-full-factorial strengths 2 through 5 are used for average summaries
- candidate count 8 and seed 17 where supported

\`pairwise-legacy\` is listed separately because it only supports strength 2.

## Headline

- Pairwise legacy strength-2 average: ${pairwiseSummary[0].averageRows.toFixed(1)} rows, ${pairwiseSummary[0].averageRuntimeMs.toFixed(1)} ms
- Best stable n-wise row count: \`${bestStableByRows.algorithm}\` (${bestStableByRows.averageRows.toFixed(1)} average rows)
- Best stable n-wise runtime: \`${bestStableByRuntime.algorithm}\` (${bestStableByRuntime.averageRuntimeMs.toFixed(1)} ms average)
- Best new experiment row count: \`${bestNewByRows.algorithm}\` (${bestNewByRows.averageRows.toFixed(1)} average rows)
- Best new experiment runtime: \`${bestNewByRuntime.algorithm}\` (${bestNewByRuntime.averageRuntimeMs.toFixed(1)} ms average)

The new strategies are useful research probes, but they do not beat the stable app strategies on this benchmark set. \`greedy\` and \`ipog\` are much faster and produce fewer average rows than the best new experimental strategy. \`aetg\` also beats the best new experiment on row count while running much faster.

## Pairwise Legacy Summary

${buildSummaryTable(pairwiseSummary)}

## Stable N-Wise Strategy Summary

${buildSummaryTable(sortedStableSummary)}

## Best New Experiment Summary

${buildSummaryTable(topNewRows)}

## Stable Strategy Case Results

${buildCaseTable(stableCaseRows)}
`;
}

const markdown = buildMarkdownReport();
writeFileSync(COMPARISON_REPORT_PATH, markdown, 'utf8');
process.stdout.write(markdown);
