const DOCUMENTED_NEXT_EXPERIMENTS = [
  {
    id: 'multipartite-graph-lookahead-scarcity',
    origin: 'documented',
    rationale: 'Weight tuple chains by uncovered rarity so hard-to-cover combinations are pulled forward.',
    config: {
      seedMode: 'scarcity',
      scarcityWeight: 85,
      candidateLimit: 4,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-frontier',
    origin: 'documented',
    rationale: 'Add a frontier-preservation score for candidates that leave more compatible next choices open.',
    config: {
      frontierWeight: 55,
      candidateLimit: 4,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-hardest-seed',
    origin: 'documented',
    rationale: 'Start each row from the rarest uncovered tuple fragment instead of broad edge pressure.',
    config: {
      seedMode: 'hardest-tuple',
      scarcityWeight: 60,
      candidateLimit: 4,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-hardest-partition',
    origin: 'documented',
    rationale: 'Choose the next parameter from the smallest compatible frontier before choosing its value.',
    config: {
      partitionMode: 'hardest',
      frontierWeight: 30,
      candidateLimit: 4,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-beam-2',
    origin: 'documented',
    rationale: 'Keep the best two partial rows while constructing each generated row.',
    config: {
      beamWidth: 2,
      candidateLimit: 3,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-beam-3',
    origin: 'documented',
    rationale: 'Keep the best three partial rows to test whether extra branching buys fewer rows.',
    config: {
      beamWidth: 3,
      candidateLimit: 3,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-adaptive-penalty',
    origin: 'documented',
    rationale: 'Make reuse penalties light early and stronger late so dense early coverage is not over-penalized.',
    config: {
      penaltyMode: 'adaptive',
      candidateLimit: 4,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-density',
    origin: 'documented',
    rationale: 'Reward candidates that co-cover uncovered tuples efficiently rather than only raw gain.',
    config: {
      densityWeight: 90,
      candidateLimit: 4,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-scarcity-frontier',
    origin: 'documented',
    rationale: 'Combine rarity weighting with frontier preservation.',
    config: {
      seedMode: 'scarcity',
      scarcityWeight: 70,
      frontierWeight: 45,
      candidateLimit: 4,
      useIncidenceCache: true,
    },
  },
  {
    id: 'multipartite-graph-lookahead-hardest-seed-beam',
    origin: 'documented',
    rationale: 'Pair hard seeding with a tiny beam to test stronger starts plus limited branching.',
    config: {
      seedMode: 'hardest-tuple',
      beamWidth: 2,
      candidateLimit: 3,
      scarcityWeight: 60,
      useIncidenceCache: true,
    },
  },
];

const RESULT_DRIVEN_SCORING_PROFILES = [
  {
    name: 'scarcity-lite',
    rationale: 'Follow-up to test whether rarity helps without dominating normal tuple gain.',
    config: { seedMode: 'scarcity', scarcityWeight: 35 },
  },
  {
    name: 'scarcity-strong',
    rationale: 'Follow-up for cases where row count rewards aggressive rarity pressure.',
    config: { seedMode: 'scarcity', scarcityWeight: 120 },
  },
  {
    name: 'frontier-lite',
    rationale: 'Follow-up to preserve future choices with a low frontier weight.',
    config: { frontierWeight: 25 },
  },
  {
    name: 'frontier-strong',
    rationale: 'Follow-up to test a high frontier weight when hard partitions are costly.',
    config: { frontierWeight: 95 },
  },
  {
    name: 'density-lite',
    rationale: 'Follow-up to reward efficient co-coverage while keeping raw gain primary.',
    config: { densityWeight: 35 },
  },
  {
    name: 'density-strong',
    rationale: 'Follow-up to test whether density should outrank broader edge pressure.',
    config: { densityWeight: 125 },
  },
  {
    name: 'scarcity-density',
    rationale: 'Follow-up for variants where rare tuple starts also need efficient co-coverage.',
    config: { seedMode: 'scarcity', scarcityWeight: 65, densityWeight: 70 },
  },
  {
    name: 'frontier-density',
    rationale: 'Follow-up for variants where future choice preservation and dense co-coverage may compound.',
    config: { frontierWeight: 55, densityWeight: 70 },
  },
];

const RESULT_DRIVEN_RUNTIME_PROFILES = [
  {
    name: 'candidate-2',
    rationale: 'Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.',
    config: { candidateLimit: 2, useIncidenceCache: true },
  },
  {
    name: 'candidate-6',
    rationale: 'Broader local search to see whether extra candidates reduce rows enough to pay for runtime.',
    config: { candidateLimit: 6, useIncidenceCache: true },
  },
  {
    name: 'beam-2',
    rationale: 'Small beam overlay for profiles that may benefit from one extra partial row.',
    config: { beamWidth: 2, candidateLimit: 3, useIncidenceCache: true },
  },
  {
    name: 'beam-3',
    rationale: 'Wider bounded beam overlay for profiles where row count matters more than runtime.',
    config: { beamWidth: 3, candidateLimit: 3, useIncidenceCache: true },
  },
  {
    name: 'hybrid-tail-20',
    rationale: 'Earlier fallback tail for profiles that look promising but expensive late in coverage.',
    config: { phaseSwitchUncoveredThreshold: 0.2, updateSyntheticUsageDuringFallback: false, useIncidenceCache: true },
  },
  {
    name: 'adaptive-penalty',
    rationale: 'Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.',
    config: { penaltyMode: 'adaptive', candidateLimit: 4, useIncidenceCache: true },
  },
];

function mergeConfig(...configs) {
  return Object.assign({}, ...configs);
}

function buildResultDrivenExperiments() {
  return RESULT_DRIVEN_SCORING_PROFILES.flatMap((scoringProfile) =>
    RESULT_DRIVEN_RUNTIME_PROFILES.map((runtimeProfile) => ({
      id: `multipartite-graph-lookahead-rd-${scoringProfile.name}-${runtimeProfile.name}`,
      origin: 'results-driven',
      rationale: `${scoringProfile.rationale} ${runtimeProfile.rationale}`,
      config: mergeConfig(scoringProfile.config, runtimeProfile.config),
    }))
  );
}

export const EVOLUTION_EXPERIMENT_DEFINITIONS = Object.freeze([
  ...DOCUMENTED_NEXT_EXPERIMENTS,
  ...buildResultDrivenExperiments(),
]);

export const EVOLUTION_ALGORITHM_IDS = Object.freeze(
  EVOLUTION_EXPERIMENT_DEFINITIONS.map((experiment) => experiment.id)
);

export const DOCUMENTED_EVOLUTION_ALGORITHM_IDS = Object.freeze(
  EVOLUTION_EXPERIMENT_DEFINITIONS.filter((experiment) => experiment.origin === 'documented').map(
    (experiment) => experiment.id
  )
);

export const RESULT_DRIVEN_EVOLUTION_ALGORITHM_IDS = Object.freeze(
  EVOLUTION_EXPERIMENT_DEFINITIONS.filter((experiment) => experiment.origin === 'results-driven').map(
    (experiment) => experiment.id
  )
);

export function getEvolutionExperimentDefinition(algorithm) {
  return EVOLUTION_EXPERIMENT_DEFINITIONS.find((experiment) => experiment.id === algorithm) || null;
}
