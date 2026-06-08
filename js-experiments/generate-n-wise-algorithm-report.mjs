import { existsSync, mkdirSync, readFileSync, writeFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

import { ExperimentalNWiseAlgorithm, ExperimentalNWiseGenerator } from './experimentalNWiseGenerator.js';
import {
  DOCUMENTED_EVOLUTION_ALGORITHM_IDS,
  EVOLUTION_EXPERIMENT_DEFINITIONS,
  RESULT_DRIVEN_EVOLUTION_ALGORITHM_IDS,
  getEvolutionExperimentDefinition,
} from './experimentalNWiseEvolutionAlgorithms.js';

const EXPERIMENT_DIR = dirname(fileURLToPath(import.meta.url));
const RESULT_CACHE_PATH = join(EXPERIMENT_DIR, 'n-wise-experiment-results.json');
const REPORT_PATH = join(EXPERIMENT_DIR, 'n-wise-algorithm-report.md');
const RUNNING_REPORT_PATH = join(EXPERIMENT_DIR, 'n-wise-experiment-running-report.md');
const SNAPSHOT_DIR = join(EXPERIMENT_DIR, 'reports');

const BASELINE_EXPERIMENTS = [
  ExperimentalNWiseAlgorithm.MULTIPARTITE_GRAPH_WALK,
  ExperimentalNWiseAlgorithm.MULTIPARTITE_GRAPH_LOOKAHEAD,
  ExperimentalNWiseAlgorithm.MULTIPARTITE_GRAPH_LOOKAHEAD_ADAPTIVE,
  ExperimentalNWiseAlgorithm.MULTIPARTITE_GRAPH_LOOKAHEAD_HYBRID,
];
const EVOLUTION_EXPERIMENTS = EVOLUTION_EXPERIMENT_DEFINITIONS.map((experiment) => experiment.id);
const ALL_EXPERIMENTS = [...BASELINE_EXPERIMENTS, ...EVOLUTION_EXPERIMENTS];
const SCENARIOS = [
  { parameterCount: 6, valueCount: 3, candidateCount: 8 },
  { parameterCount: 6, valueCount: 4, candidateCount: 8 },
];

function createParameters(parameterCount, valueCount) {
  return Array.from({ length: parameterCount }, (_, parameterIndex) => ({
    name: `P${parameterIndex + 1}`,
    values: Array.from({ length: valueCount }, (_, valueIndex) => `${parameterIndex + 1}.${valueIndex + 1}`),
  }));
}

function bytesToMiB(bytes) {
  if (typeof bytes !== 'number') {
    return 'n/a';
  }
  return (bytes / (1024 * 1024)).toFixed(2);
}

function buildScenarioDataSet(parameters) {
  return parameters
    .map((parameter) => `- \`${parameter.name}\`: ${parameter.values.map((value) => `\`${value}\``).join(', ')}`)
    .join('\n');
}

function calculateCartesianRowCount(parameters) {
  return parameters.reduce((total, parameter) => total * parameter.values.length, 1);
}

function getCaseKey({ algorithm, parameterCount, valueCount, strength, candidateCount }) {
  return `${algorithm}|${parameterCount}x${valueCount}|strength-${strength}|candidates-${candidateCount}`;
}

function readResultCache() {
  if (!existsSync(RESULT_CACHE_PATH)) {
    return { version: 1, results: {} };
  }

  return JSON.parse(readFileSync(RESULT_CACHE_PATH, 'utf8'));
}

function waitForFileRetry(delayMs) {
  Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, delayMs);
}

function writeFileWithRetry(path, content) {
  let lastError = null;

  for (let attempt = 0; attempt < 20; attempt += 1) {
    try {
      writeFileSync(path, content, 'utf8');
      return;
    } catch (error) {
      lastError = error;
      if (!['EBUSY', 'EPERM', 'UNKNOWN'].includes(error.code)) {
        throw error;
      }
      waitForFileRetry(100);
    }
  }

  throw lastError;
}

function writeResultCache(cache) {
  writeFileWithRetry(RESULT_CACHE_PATH, `${JSON.stringify(cache, null, 2)}\n`);
}

function runCase({ algorithm, parameterCount, valueCount, strength, candidateCount }) {
  if (typeof global.gc === 'function') {
    global.gc();
  }

  const generator = new ExperimentalNWiseGenerator(createParameters(parameterCount, valueCount), {
    algorithm,
    strength,
    seed: 17,
    candidateCount,
  });

  generator.generateDataSet();
  return generator.getBenchmarkStats();
}

function getExpectedCaseInputs(algorithms = ALL_EXPERIMENTS) {
  return algorithms.flatMap((algorithm) =>
    SCENARIOS.flatMap((scenario) =>
      Array.from({ length: scenario.parameterCount - 1 }, (_, index) => ({
        ...scenario,
        algorithm,
        strength: index + 2,
      }))
    )
  );
}

function collectMissingResults(cache, algorithms = ALL_EXPERIMENTS) {
  const missing = [];

  for (const input of getExpectedCaseInputs(algorithms)) {
    const key = getCaseKey(input);
    if (!cache.results[key]) {
      missing.push({ key, input });
    }
  }

  return missing;
}

function materializeScenarioResults(cache, algorithms = ALL_EXPERIMENTS) {
  return SCENARIOS.map((scenario) => {
    const parameters = createParameters(scenario.parameterCount, scenario.valueCount);
    const strengthRows = [];

    for (let strength = 2; strength <= scenario.parameterCount; strength += 1) {
      const rows = algorithms.map((algorithm) => {
        const key = getCaseKey({
          ...scenario,
          algorithm,
          strength,
        });
        return cache.results[key];
      });

      strengthRows.push({
        strength,
        rows: rows.filter(Boolean),
      });
    }

    return {
      ...scenario,
      parameters,
      cartesianRowCount: calculateCartesianRowCount(parameters),
      strengthRows,
    };
  });
}

function calculateExperimentSummary(scenarioResults, algorithms) {
  const experimentRows = scenarioResults.flatMap((scenario) =>
    scenario.strengthRows
      .filter((strengthRow) => strengthRow.strength < scenario.parameterCount)
      .flatMap((strengthRow) =>
        strengthRow.rows.map((row) => ({
          ...row,
          shape: `${scenario.parameterCount}x${scenario.valueCount}`,
          strength: strengthRow.strength,
        }))
      )
  );

  const perCaseBest = new Map();
  for (const row of experimentRows) {
    const caseKey = `${row.shape}:${row.strength}`;
    const currentBest = perCaseBest.get(caseKey);

    if (!currentBest) {
      perCaseBest.set(caseKey, {
        bestRuntimeMs: row.runtimeMs,
        bestRowCount: row.rowCount,
      });
      continue;
    }

    currentBest.bestRuntimeMs = Math.min(currentBest.bestRuntimeMs, row.runtimeMs);
    currentBest.bestRowCount = Math.min(currentBest.bestRowCount, row.rowCount);
  }

  const aggregates = new Map(
    algorithms.map((algorithm) => [
      algorithm,
      {
        algorithm,
        totalRows: 0,
        totalRuntimeMs: 0,
        totalGraphRows: 0,
        totalFallbackRows: 0,
        totalLookaheadEvaluations: 0,
        totalCandidateEvaluations: 0,
        balancedScore: 0,
        caseCount: 0,
      },
    ])
  );

  for (const row of experimentRows) {
    const aggregate = aggregates.get(row.algorithm);
    const bestForCase = perCaseBest.get(`${row.shape}:${row.strength}`);
    const runtimeRatio = bestForCase.bestRuntimeMs > 0 ? row.runtimeMs / bestForCase.bestRuntimeMs : 1;
    const rowRatio = bestForCase.bestRowCount > 0 ? row.rowCount / bestForCase.bestRowCount : 1;

    aggregate.totalRows += row.rowCount;
    aggregate.totalRuntimeMs += row.runtimeMs;
    aggregate.totalGraphRows += row.rowsGeneratedByGraphPhase;
    aggregate.totalFallbackRows += row.rowsGeneratedByFallback;
    aggregate.totalLookaheadEvaluations += row.lookaheadEvaluations;
    aggregate.totalCandidateEvaluations += row.candidateEvaluations;
    aggregate.balancedScore += runtimeRatio + rowRatio;
    aggregate.caseCount += 1;
  }

  const summaryRows = [...aggregates.values()]
    .filter((row) => row.caseCount > 0)
    .map((row) => ({
      ...row,
      averageRows: row.totalRows / row.caseCount,
      averageRuntimeMs: row.totalRuntimeMs / row.caseCount,
      averageGraphRows: row.totalGraphRows / row.caseCount,
      averageFallbackRows: row.totalFallbackRows / row.caseCount,
      averageLookaheadEvaluations: row.totalLookaheadEvaluations / row.caseCount,
      averageBalancedScore: row.balancedScore / row.caseCount,
    }));

  return {
    rows: summaryRows,
    bestRuntime: [...summaryRows].sort((left, right) => left.totalRuntimeMs - right.totalRuntimeMs)[0],
    bestRows: [...summaryRows].sort((left, right) => left.totalRows - right.totalRows)[0],
    bestBalanced: [...summaryRows].sort((left, right) => left.averageBalancedScore - right.averageBalancedScore)[0],
  };
}

function buildSummaryTable(summary, heading = 'Experiment') {
  return [
    `| ${heading} | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |`,
    '| --- | ---: | ---: | ---: | ---: | ---: | ---: |',
    ...[...summary.rows]
      .sort((left, right) => left.averageBalancedScore - right.averageBalancedScore)
      .map(
        (row) =>
          `| \`${row.algorithm}\` | ${row.averageRows.toFixed(1)} | ${row.averageRuntimeMs.toFixed(
            1
          )} | ${row.averageGraphRows.toFixed(1)} | ${row.averageFallbackRows.toFixed(
            1
          )} | ${row.averageLookaheadEvaluations.toFixed(1)} | ${row.averageBalancedScore.toFixed(2)} |`
      ),
  ].join('\n');
}

function buildCaseTable(rows) {
  const header = [
    '| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |',
    '| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |',
  ];

  const sortedRows = [...rows].sort((left, right) => {
    if (left.rowCount !== right.rowCount) {
      return left.rowCount - right.rowCount;
    }
    return left.algorithm.localeCompare(right.algorithm);
  });

  const body = sortedRows.map(
    (row) =>
      `| \`${row.algorithm}\` | ${row.rowCount} | ${row.runtimeMs} | ${row.rowsGeneratedByGraphPhase} | ${row.rowsGeneratedByFallback} | ${row.lookaheadEvaluations} | ${bytesToMiB(
        row.heapUsedDeltaBytes
      )} | ${row.coveragePercentage.toFixed(1)} |`
  );

  return [...header, ...body].join('\n');
}

function describeBest(summary) {
  if (!summary.bestRuntime || !summary.bestRows || !summary.bestBalanced) {
    return ['- No completed benchmark rows yet.'];
  }

  return [
    `- Best runtime across non-full-factorial cases: \`${summary.bestRuntime.algorithm}\``,
    `- Best row count across non-full-factorial cases: \`${summary.bestRows.algorithm}\``,
    `- Best balanced runtime/rows result: \`${summary.bestBalanced.algorithm}\``,
  ];
}

function buildNextExperimentRecommendations(summary) {
  if (!summary.bestRows || !summary.bestBalanced) {
    return ['- Re-run after benchmark data is available.'];
  }

  return [
    `- Add a \`scarcity-density-beam-3-tail\` family: \`${summary.bestRows.algorithm}\` won row count, but beam work needs a hybrid tail or collapse guard before it can compete on balanced score.`,
    `- Add \`scarcity-density-hybrid-tail-10\` and \`scarcity-density-hybrid-tail-30\`: the strongest result-driven balanced variant used the 20% hybrid tail, so the next pass should sweep the handoff threshold.`,
    '- Add a dynamic beam policy that starts at width 3 for small/front-loaded cases and collapses to width 1 or fallback once uncovered tuples fall below the hybrid-tail threshold.',
    '- Add a candidate-limit sweep around the best density/scarcity profiles using limits 1, 3, and 5; limit 6 rarely offset its extra runtime enough in this run.',
    '- De-prioritize pure frontier-heavy variants unless they are paired with density or a tail guard; frontier-only scoring trailed the best balanced and row-count profiles.',
  ];
}

function buildExperimentRationaleList(ids) {
  return ids.map((id) => {
    const definition = getEvolutionExperimentDefinition(id);
    return `- \`${id}\`: ${definition?.rationale || 'No rationale recorded.'}`;
  });
}

function buildMarkdownReport(cache, algorithms = ALL_EXPERIMENTS) {
  const baselineAlgorithms = algorithms.filter((algorithm) => BASELINE_EXPERIMENTS.includes(algorithm));
  const documentedAlgorithms = algorithms.filter((algorithm) => DOCUMENTED_EVOLUTION_ALGORITHM_IDS.includes(algorithm));
  const resultDrivenAlgorithms = algorithms.filter((algorithm) =>
    RESULT_DRIVEN_EVOLUTION_ALGORITHM_IDS.includes(algorithm)
  );
  const scenarioResults = materializeScenarioResults(cache, algorithms);
  const allSummary = calculateExperimentSummary(scenarioResults, algorithms);
  const baselineSummary = calculateExperimentSummary(
    materializeScenarioResults(cache, baselineAlgorithms),
    baselineAlgorithms
  );
  const documentedSummary = calculateExperimentSummary(
    materializeScenarioResults(cache, documentedAlgorithms),
    documentedAlgorithms
  );
  const resultDrivenSummary = calculateExperimentSummary(
    materializeScenarioResults(cache, resultDrivenAlgorithms),
    resultDrivenAlgorithms
  );

  const sections = [
    '# Experimental N-Wise Algorithm Report',
    '',
    'This report covers experimental multipartite strategies only. Stable pairwise, IPOG, greedy, and production n-wise implementations remain outside this comparison on purpose.',
    '',
    '## Overall Summary',
    '',
    ...describeBest(allSummary),
    '',
    buildSummaryTable(allSummary, 'Strategy'),
    '',
    '## Next Experiments Suggested By Results',
    '',
    ...buildNextExperimentRecommendations(allSummary),
    '',
    '## Baseline Experimental Strategies',
    '',
    ...describeBest(baselineSummary),
    '',
    buildSummaryTable(baselineSummary, 'Baseline strategy'),
    '',
    '## Documented Evolution Experiments',
    '',
    ...buildExperimentRationaleList(documentedAlgorithms),
    '',
    ...describeBest(documentedSummary),
    '',
    buildSummaryTable(documentedSummary, 'Documented experiment'),
    '',
    '## Results-Driven Follow-Up Experiments',
    '',
    ...buildExperimentRationaleList(resultDrivenAlgorithms),
    '',
    ...describeBest(resultDrivenSummary),
    '',
    buildSummaryTable(resultDrivenSummary, 'Results-driven experiment'),
    '',
  ];

  for (const scenario of scenarioResults) {
    sections.push(`## Scenario ${scenario.parameterCount}x${scenario.valueCount}`);
    sections.push('');
    sections.push('Input data set:');
    sections.push(buildScenarioDataSet(scenario.parameters));
    sections.push('');
    sections.push(`For Cartesian all combinations the number of rows would be ${scenario.cartesianRowCount}.`);
    sections.push('');

    for (const strengthRow of scenario.strengthRows) {
      sections.push(`### Strength ${strengthRow.strength}`);
      sections.push('');
      sections.push(buildCaseTable(strengthRow.rows));
      sections.push('');
    }
  }

  return `${sections.join('\n')}\n`;
}

function buildRunningReport(cache, algorithms = ALL_EXPERIMENTS) {
  const scenarioResults = materializeScenarioResults(cache, algorithms);
  const summary = calculateExperimentSummary(scenarioResults, algorithms);
  const completedAlgorithms = algorithms.filter((algorithm) =>
    getExpectedCaseInputs([algorithm]).every((input) => cache.results[getCaseKey(input)])
  );
  const sections = [
    '# N-Wise Experiment Running Report',
    '',
    `Completed strategies: ${completedAlgorithms.length} / ${algorithms.length}`,
    '',
    ...describeBest(summary),
    '',
    buildSummaryTable(summary, 'Strategy'),
    '',
    '## Next Experiments Suggested By Results',
    '',
    ...buildNextExperimentRecommendations(summary),
    '',
    '## Latest Completed Strategies',
    '',
    ...completedAlgorithms.slice(-10).map((algorithm) => `- \`${algorithm}\``),
    '',
  ];

  return `${sections.join('\n')}\n`;
}

function writeReports(cache, algorithms = ALL_EXPERIMENTS, snapshotName = null) {
  mkdirSync(SNAPSHOT_DIR, { recursive: true });
  const fullReport = buildMarkdownReport(cache, algorithms);
  const runningReport = buildRunningReport(cache, algorithms);

  writeFileWithRetry(REPORT_PATH, fullReport);
  writeFileWithRetry(RUNNING_REPORT_PATH, runningReport);

  if (snapshotName) {
    writeFileWithRetry(join(SNAPSHOT_DIR, snapshotName), fullReport);
  }
}

function runMissingCases() {
  const cache = readResultCache();
  let completedAlgorithmCount = 0;

  for (const algorithm of ALL_EXPERIMENTS) {
    const missingForAlgorithm = collectMissingResults(cache, [algorithm]);
    if (missingForAlgorithm.length === 0) {
      completedAlgorithmCount += 1;
      continue;
    }

    for (const { key, input } of missingForAlgorithm) {
      cache.results[key] = runCase(input);
      writeResultCache(cache);
    }

    completedAlgorithmCount += 1;
    writeReports(
      cache,
      ALL_EXPERIMENTS,
      `full-comparison-after-${String(completedAlgorithmCount).padStart(3, '0')}-${algorithm}.md`
    );
    process.stdout.write(`Completed ${completedAlgorithmCount}/${ALL_EXPERIMENTS.length}: ${algorithm}\n`);
  }

  writeReports(cache, ALL_EXPERIMENTS);
  return cache;
}

const shouldRun = process.argv.includes('--run');
const cache = shouldRun ? runMissingCases() : readResultCache();
writeReports(cache, ALL_EXPERIMENTS);

if (!shouldRun) {
  process.stdout.write(buildMarkdownReport(cache, ALL_EXPERIMENTS));
}
