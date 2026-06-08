# Experimental N-Wise Algorithm Report

This report covers experimental multipartite strategies only. Stable pairwise, IPOG, greedy, and production n-wise implementations remain outside this comparison on purpose.

## Overall Summary

- Best runtime across non-full-factorial cases: `multipartite-graph-lookahead-hybrid`
- Best row count across non-full-factorial cases: `multipartite-graph-lookahead-rd-scarcity-lite-beam-2`
- Best balanced runtime/rows result: `multipartite-graph-lookahead-hybrid`

| Strategy | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-hybrid` | 316.5 | 2005.6 | 165.3 | 151.3 | 650.5 | 2.29 |
| `multipartite-graph-walk` | 313.6 | 2192.0 | 0.0 | 0.0 | 0.0 | 2.42 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 312.6 | 3919.0 | 312.6 | 0.0 | 4271.5 | 2.72 |
| `multipartite-graph-lookahead-scarcity-frontier` | 306.8 | 3809.0 | 306.8 | 0.0 | 4230.1 | 2.73 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 311.1 | 3641.6 | 196.8 | 114.4 | 2912.1 | 2.80 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 310.9 | 3239.0 | 310.9 | 0.0 | 4245.8 | 2.84 |
| `multipartite-graph-lookahead-density` | 311.1 | 3148.3 | 311.1 | 0.0 | 4239.0 | 2.90 |
| `multipartite-graph-lookahead-scarcity` | 307.5 | 3503.1 | 307.5 | 0.0 | 4241.6 | 2.98 |
| `multipartite-graph-lookahead` | 315.8 | 2862.4 | 299.8 | 16.0 | 1214.0 | 3.00 |
| `multipartite-graph-lookahead-hardest-partition` | 308.5 | 3567.0 | 308.5 | 0.0 | 1031.3 | 3.08 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 305.3 | 3639.1 | 305.3 | 0.0 | 4143.1 | 3.09 |
| `multipartite-graph-lookahead-hardest-seed` | 313.4 | 3800.1 | 313.4 | 0.0 | 4300.9 | 3.09 |
| `multipartite-graph-lookahead-adaptive-penalty` | 312.1 | 3672.6 | 312.1 | 0.0 | 4251.8 | 3.09 |
| `multipartite-graph-lookahead-beam-2` | 303.0 | 3662.8 | 303.0 | 0.0 | 4113.8 | 3.19 |
| `multipartite-graph-lookahead-adaptive` | 318.4 | 2594.4 | 302.3 | 16.1 | 980.4 | 3.20 |
| `multipartite-graph-lookahead-frontier` | 313.5 | 4832.6 | 313.5 | 0.0 | 4277.6 | 3.53 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 302.3 | 7125.4 | 302.3 | 0.0 | 4117.6 | 3.92 |
| `multipartite-graph-lookahead-beam-3` | 305.6 | 4801.8 | 305.6 | 0.0 | 4145.3 | 3.99 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 304.9 | 4711.4 | 304.9 | 0.0 | 4138.1 | 4.58 |

## Baseline Experimental Strategies

- Best runtime across non-full-factorial cases: `multipartite-graph-lookahead-hybrid`
- Best row count across non-full-factorial cases: `multipartite-graph-walk`
- Best balanced runtime/rows result: `multipartite-graph-lookahead-hybrid`

| Baseline strategy | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-hybrid` | 316.5 | 2005.6 | 165.3 | 151.3 | 650.5 | 2.17 |
| `multipartite-graph-walk` | 313.6 | 2192.0 | 0.0 | 0.0 | 0.0 | 2.29 |
| `multipartite-graph-lookahead` | 315.8 | 2862.4 | 299.8 | 16.0 | 1214.0 | 2.87 |
| `multipartite-graph-lookahead-adaptive` | 318.4 | 2594.4 | 302.3 | 16.1 | 980.4 | 3.03 |

## Documented Evolution Experiments

- `multipartite-graph-lookahead-scarcity`: Weight tuple chains by uncovered rarity so hard-to-cover combinations are pulled forward.
- `multipartite-graph-lookahead-frontier`: Add a frontier-preservation score for candidates that leave more compatible next choices open.
- `multipartite-graph-lookahead-hardest-seed`: Start each row from the rarest uncovered tuple fragment instead of broad edge pressure.
- `multipartite-graph-lookahead-hardest-partition`: Choose the next parameter from the smallest compatible frontier before choosing its value.
- `multipartite-graph-lookahead-beam-2`: Keep the best two partial rows while constructing each generated row.
- `multipartite-graph-lookahead-beam-3`: Keep the best three partial rows to test whether extra branching buys fewer rows.
- `multipartite-graph-lookahead-adaptive-penalty`: Make reuse penalties light early and stronger late so dense early coverage is not over-penalized.
- `multipartite-graph-lookahead-density`: Reward candidates that co-cover uncovered tuples efficiently rather than only raw gain.
- `multipartite-graph-lookahead-scarcity-frontier`: Combine rarity weighting with frontier preservation.
- `multipartite-graph-lookahead-hardest-seed-beam`: Pair hard seeding with a tiny beam to test stronger starts plus limited branching.

- Best runtime across non-full-factorial cases: `multipartite-graph-lookahead-density`
- Best row count across non-full-factorial cases: `multipartite-graph-lookahead-beam-2`
- Best balanced runtime/rows result: `multipartite-graph-lookahead-scarcity-frontier`

| Documented experiment | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-scarcity-frontier` | 306.8 | 3809.0 | 306.8 | 0.0 | 4230.1 | 2.16 |
| `multipartite-graph-lookahead-density` | 311.1 | 3148.3 | 311.1 | 0.0 | 4239.0 | 2.30 |
| `multipartite-graph-lookahead-scarcity` | 307.5 | 3503.1 | 307.5 | 0.0 | 4241.6 | 2.39 |
| `multipartite-graph-lookahead-hardest-seed` | 313.4 | 3800.1 | 313.4 | 0.0 | 4300.9 | 2.44 |
| `multipartite-graph-lookahead-adaptive-penalty` | 312.1 | 3672.6 | 312.1 | 0.0 | 4251.8 | 2.45 |
| `multipartite-graph-lookahead-hardest-partition` | 308.5 | 3567.0 | 308.5 | 0.0 | 1031.3 | 2.46 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 305.3 | 3639.1 | 305.3 | 0.0 | 4143.1 | 2.47 |
| `multipartite-graph-lookahead-beam-2` | 303.0 | 3662.8 | 303.0 | 0.0 | 4113.8 | 2.62 |
| `multipartite-graph-lookahead-frontier` | 313.5 | 4832.6 | 313.5 | 0.0 | 4277.6 | 2.71 |
| `multipartite-graph-lookahead-beam-3` | 305.6 | 4801.8 | 305.6 | 0.0 | 4145.3 | 3.07 |

## Results-Driven Follow-Up Experiments

- `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2`: Follow-up to test whether rarity helps without dominating normal tuple gain. Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.
- `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6`: Follow-up to test whether rarity helps without dominating normal tuple gain. Broader local search to see whether extra candidates reduce rows enough to pay for runtime.
- `multipartite-graph-lookahead-rd-scarcity-lite-beam-2`: Follow-up to test whether rarity helps without dominating normal tuple gain. Small beam overlay for profiles that may benefit from one extra partial row.
- `multipartite-graph-lookahead-rd-scarcity-lite-beam-3`: Follow-up to test whether rarity helps without dominating normal tuple gain. Wider bounded beam overlay for profiles where row count matters more than runtime.
- `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20`: Follow-up to test whether rarity helps without dominating normal tuple gain. Earlier fallback tail for profiles that look promising but expensive late in coverage.
- `multipartite-graph-lookahead-rd-scarcity-lite-adaptive-penalty`: Follow-up to test whether rarity helps without dominating normal tuple gain. Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.
- `multipartite-graph-lookahead-rd-scarcity-strong-candidate-2`: Follow-up for cases where row count rewards aggressive rarity pressure. Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.
- `multipartite-graph-lookahead-rd-scarcity-strong-candidate-6`: Follow-up for cases where row count rewards aggressive rarity pressure. Broader local search to see whether extra candidates reduce rows enough to pay for runtime.
- `multipartite-graph-lookahead-rd-scarcity-strong-beam-2`: Follow-up for cases where row count rewards aggressive rarity pressure. Small beam overlay for profiles that may benefit from one extra partial row.
- `multipartite-graph-lookahead-rd-scarcity-strong-beam-3`: Follow-up for cases where row count rewards aggressive rarity pressure. Wider bounded beam overlay for profiles where row count matters more than runtime.
- `multipartite-graph-lookahead-rd-scarcity-strong-hybrid-tail-20`: Follow-up for cases where row count rewards aggressive rarity pressure. Earlier fallback tail for profiles that look promising but expensive late in coverage.
- `multipartite-graph-lookahead-rd-scarcity-strong-adaptive-penalty`: Follow-up for cases where row count rewards aggressive rarity pressure. Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.
- `multipartite-graph-lookahead-rd-frontier-lite-candidate-2`: Follow-up to preserve future choices with a low frontier weight. Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.
- `multipartite-graph-lookahead-rd-frontier-lite-candidate-6`: Follow-up to preserve future choices with a low frontier weight. Broader local search to see whether extra candidates reduce rows enough to pay for runtime.
- `multipartite-graph-lookahead-rd-frontier-lite-beam-2`: Follow-up to preserve future choices with a low frontier weight. Small beam overlay for profiles that may benefit from one extra partial row.
- `multipartite-graph-lookahead-rd-frontier-lite-beam-3`: Follow-up to preserve future choices with a low frontier weight. Wider bounded beam overlay for profiles where row count matters more than runtime.
- `multipartite-graph-lookahead-rd-frontier-lite-hybrid-tail-20`: Follow-up to preserve future choices with a low frontier weight. Earlier fallback tail for profiles that look promising but expensive late in coverage.
- `multipartite-graph-lookahead-rd-frontier-lite-adaptive-penalty`: Follow-up to preserve future choices with a low frontier weight. Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.
- `multipartite-graph-lookahead-rd-frontier-strong-candidate-2`: Follow-up to test a high frontier weight when hard partitions are costly. Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.
- `multipartite-graph-lookahead-rd-frontier-strong-candidate-6`: Follow-up to test a high frontier weight when hard partitions are costly. Broader local search to see whether extra candidates reduce rows enough to pay for runtime.
- `multipartite-graph-lookahead-rd-frontier-strong-beam-2`: Follow-up to test a high frontier weight when hard partitions are costly. Small beam overlay for profiles that may benefit from one extra partial row.
- `multipartite-graph-lookahead-rd-frontier-strong-beam-3`: Follow-up to test a high frontier weight when hard partitions are costly. Wider bounded beam overlay for profiles where row count matters more than runtime.
- `multipartite-graph-lookahead-rd-frontier-strong-hybrid-tail-20`: Follow-up to test a high frontier weight when hard partitions are costly. Earlier fallback tail for profiles that look promising but expensive late in coverage.
- `multipartite-graph-lookahead-rd-frontier-strong-adaptive-penalty`: Follow-up to test a high frontier weight when hard partitions are costly. Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.
- `multipartite-graph-lookahead-rd-density-lite-candidate-2`: Follow-up to reward efficient co-coverage while keeping raw gain primary. Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.
- `multipartite-graph-lookahead-rd-density-lite-candidate-6`: Follow-up to reward efficient co-coverage while keeping raw gain primary. Broader local search to see whether extra candidates reduce rows enough to pay for runtime.
- `multipartite-graph-lookahead-rd-density-lite-beam-2`: Follow-up to reward efficient co-coverage while keeping raw gain primary. Small beam overlay for profiles that may benefit from one extra partial row.
- `multipartite-graph-lookahead-rd-density-lite-beam-3`: Follow-up to reward efficient co-coverage while keeping raw gain primary. Wider bounded beam overlay for profiles where row count matters more than runtime.
- `multipartite-graph-lookahead-rd-density-lite-hybrid-tail-20`: Follow-up to reward efficient co-coverage while keeping raw gain primary. Earlier fallback tail for profiles that look promising but expensive late in coverage.
- `multipartite-graph-lookahead-rd-density-lite-adaptive-penalty`: Follow-up to reward efficient co-coverage while keeping raw gain primary. Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.
- `multipartite-graph-lookahead-rd-density-strong-candidate-2`: Follow-up to test whether density should outrank broader edge pressure. Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.
- `multipartite-graph-lookahead-rd-density-strong-candidate-6`: Follow-up to test whether density should outrank broader edge pressure. Broader local search to see whether extra candidates reduce rows enough to pay for runtime.
- `multipartite-graph-lookahead-rd-density-strong-beam-2`: Follow-up to test whether density should outrank broader edge pressure. Small beam overlay for profiles that may benefit from one extra partial row.
- `multipartite-graph-lookahead-rd-density-strong-beam-3`: Follow-up to test whether density should outrank broader edge pressure. Wider bounded beam overlay for profiles where row count matters more than runtime.
- `multipartite-graph-lookahead-rd-density-strong-hybrid-tail-20`: Follow-up to test whether density should outrank broader edge pressure. Earlier fallback tail for profiles that look promising but expensive late in coverage.
- `multipartite-graph-lookahead-rd-density-strong-adaptive-penalty`: Follow-up to test whether density should outrank broader edge pressure. Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.
- `multipartite-graph-lookahead-rd-scarcity-density-candidate-2`: Follow-up for variants where rare tuple starts also need efficient co-coverage. Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.
- `multipartite-graph-lookahead-rd-scarcity-density-candidate-6`: Follow-up for variants where rare tuple starts also need efficient co-coverage. Broader local search to see whether extra candidates reduce rows enough to pay for runtime.
- `multipartite-graph-lookahead-rd-scarcity-density-beam-2`: Follow-up for variants where rare tuple starts also need efficient co-coverage. Small beam overlay for profiles that may benefit from one extra partial row.
- `multipartite-graph-lookahead-rd-scarcity-density-beam-3`: Follow-up for variants where rare tuple starts also need efficient co-coverage. Wider bounded beam overlay for profiles where row count matters more than runtime.
- `multipartite-graph-lookahead-rd-scarcity-density-hybrid-tail-20`: Follow-up for variants where rare tuple starts also need efficient co-coverage. Earlier fallback tail for profiles that look promising but expensive late in coverage.
- `multipartite-graph-lookahead-rd-scarcity-density-adaptive-penalty`: Follow-up for variants where rare tuple starts also need efficient co-coverage. Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.
- `multipartite-graph-lookahead-rd-frontier-density-candidate-2`: Follow-up for variants where future choice preservation and dense co-coverage may compound. Runtime guard inspired by hybrid/adaptive variants that evaluate fewer candidates.
- `multipartite-graph-lookahead-rd-frontier-density-candidate-6`: Follow-up for variants where future choice preservation and dense co-coverage may compound. Broader local search to see whether extra candidates reduce rows enough to pay for runtime.
- `multipartite-graph-lookahead-rd-frontier-density-beam-2`: Follow-up for variants where future choice preservation and dense co-coverage may compound. Small beam overlay for profiles that may benefit from one extra partial row.
- `multipartite-graph-lookahead-rd-frontier-density-beam-3`: Follow-up for variants where future choice preservation and dense co-coverage may compound. Wider bounded beam overlay for profiles where row count matters more than runtime.
- `multipartite-graph-lookahead-rd-frontier-density-hybrid-tail-20`: Follow-up for variants where future choice preservation and dense co-coverage may compound. Earlier fallback tail for profiles that look promising but expensive late in coverage.
- `multipartite-graph-lookahead-rd-frontier-density-adaptive-penalty`: Follow-up for variants where future choice preservation and dense co-coverage may compound. Adaptive reuse penalty overlay for profiles that over-avoid repeated nodes too early.

- Best runtime across non-full-factorial cases: `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2`
- Best row count across non-full-factorial cases: `multipartite-graph-lookahead-rd-scarcity-lite-beam-2`
- Best balanced runtime/rows result: `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6`

| Results-driven experiment | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 312.6 | 3919.0 | 312.6 | 0.0 | 4271.5 | 2.11 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 311.1 | 3641.6 | 196.8 | 114.4 | 2912.1 | 2.14 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 310.9 | 3239.0 | 310.9 | 0.0 | 4245.8 | 2.18 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 302.3 | 7125.4 | 302.3 | 0.0 | 4117.6 | 2.89 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 304.9 | 4711.4 | 304.9 | 0.0 | 4138.1 | 3.29 |

## Scenario 6x3

Input data set:
- `P1`: `1.1`, `1.2`, `1.3`
- `P2`: `2.1`, `2.2`, `2.3`
- `P3`: `3.1`, `3.2`, `3.3`
- `P4`: `4.1`, `4.2`, `4.3`
- `P5`: `5.1`, `5.2`, `5.3`
- `P6`: `6.1`, `6.2`, `6.3`

For Cartesian all combinations the number of rows would be 729.

### Strength 2

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-beam-2` | 14 | 12 | 14 | 0 | 0 | 1.09 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 14 | 6 | 14 | 0 | 0 | 3.44 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 14 | 4 | 14 | 0 | 0 | 2.96 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 14 | 4 | 14 | 0 | 0 | 2.73 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 14 | 4 | 14 | 0 | 0 | 2.82 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 14 | 6 | 14 | 0 | 0 | 3.19 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 14 | 4 | 14 | 0 | 0 | 2.93 | 100.0 |
| `multipartite-graph-lookahead` | 15 | 8 | 15 | 0 | 0 | 2.53 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 15 | 13 | 15 | 0 | 0 | 2.52 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 15 | 3 | 15 | 0 | 0 | 2.80 | 100.0 |
| `multipartite-graph-lookahead-density` | 15 | 4 | 15 | 0 | 0 | 2.73 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 15 | 4 | 15 | 0 | 0 | 2.91 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 15 | 5 | 15 | 0 | 0 | 3.07 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 15 | 4 | 15 | 0 | 0 | 3.58 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 15 | 3 | 8 | 7 | 0 | 2.37 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 15 | 7 | 15 | 0 | 0 | 3.71 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 15 | 8 | 15 | 0 | 0 | 4.25 | 100.0 |
| `multipartite-graph-walk` | 15 | 8 | 0 | 0 | 0 | 1.07 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 16 | 4 | 9 | 7 | 0 | 2.38 | 100.0 |

### Strength 3

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-scarcity` | 44 | 49 | 44 | 0 | 304 | 10.96 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 44 | 47 | 44 | 0 | 304 | 8.24 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 45 | 54 | 45 | 0 | 313 | 6.42 | 100.0 |
| `multipartite-graph-lookahead-density` | 45 | 50 | 45 | 0 | 313 | 5.72 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 45 | 60 | 45 | 0 | 313 | 7.63 | 100.0 |
| `multipartite-graph-lookahead-beam-2` | 46 | 78 | 46 | 0 | 312 | 3.77 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 46 | 48 | 46 | 0 | 316 | 15.55 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 46 | 44 | 25 | 21 | 252 | 2.66 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 47 | 81 | 47 | 0 | 316 | 0.92 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 47 | 41 | 47 | 0 | 315 | 7.87 | 100.0 |
| `multipartite-graph-lookahead` | 48 | 66 | 48 | 0 | 159 | 12.72 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 48 | 78 | 48 | 0 | 319 | 15.45 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 48 | 48 | 48 | 0 | 319 | 7.11 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 48 | 46 | 48 | 0 | 318 | 7.00 | 100.0 |
| `multipartite-graph-walk` | 49 | 35 | 0 | 0 | 0 | 1.34 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 50 | 49 | 50 | 0 | 94 | 9.31 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 51 | 113 | 51 | 0 | 319 | 3.72 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 52 | 28 | 21 | 31 | 84 | 5.34 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 53 | 50 | 53 | 0 | 92 | 14.50 | 100.0 |

### Strength 4

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 135 | 747 | 135 | 0 | 1404 | 15.68 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 137 | 602 | 137 | 0 | 1397 | 15.70 | 100.0 |
| `multipartite-graph-lookahead-beam-2` | 138 | 323 | 138 | 0 | 1395 | 11.50 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 138 | 330 | 138 | 0 | 1396 | 13.41 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 138 | 468 | 138 | 0 | 1407 | 15.34 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 138 | 306 | 138 | 0 | 1400 | 5.19 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 138 | 314 | 138 | 0 | 1400 | 8.71 | 100.0 |
| `multipartite-graph-lookahead` | 140 | 362 | 140 | 0 | 541 | 1.14 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 140 | 387 | 140 | 0 | 1412 | 2.55 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 141 | 359 | 141 | 0 | 1422 | 3.23 | 100.0 |
| `multipartite-graph-lookahead-density` | 141 | 372 | 141 | 0 | 1422 | 2.94 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 141 | 491 | 141 | 0 | 1422 | 9.34 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 142 | 220 | 65 | 77 | 260 | 5.92 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 142 | 382 | 142 | 0 | 1449 | 5.49 | 100.0 |
| `multipartite-graph-walk` | 142 | 151 | 0 | 0 | 0 | 2.27 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 143 | 301 | 143 | 0 | 1460 | 6.72 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 143 | 325 | 79 | 64 | 944 | 11.46 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 144 | 338 | 144 | 0 | 348 | 11.62 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 147 | 301 | 147 | 0 | 147 | 0.90 | 100.0 |

### Strength 5

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-beam-2` | 311 | 1012 | 311 | 0 | 3563 | 7.51 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 311 | 1731 | 311 | 0 | 3581 | 6.56 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 312 | 2540 | 312 | 0 | 3579 | 11.30 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 313 | 1808 | 313 | 0 | 3610 | 1.02 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 316 | 1232 | 316 | 0 | 3655 | 5.92 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 318 | 1499 | 318 | 0 | 3661 | 1.53 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 319 | 1484 | 319 | 0 | 889 | 6.56 | 100.0 |
| `multipartite-graph-lookahead-density` | 320 | 1502 | 320 | 0 | 3683 | 1.67 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 322 | 1494 | 218 | 104 | 2616 | 15.27 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 324 | 1477 | 324 | 0 | 3723 | 0.87 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 324 | 1075 | 324 | 0 | 3730 | 11.13 | 100.0 |
| `multipartite-graph-walk` | 325 | 567 | 0 | 0 | 0 | 13.27 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 326 | 1162 | 326 | 0 | 3744 | 11.90 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 327 | 836 | 185 | 142 | 740 | 12.50 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 328 | 1346 | 328 | 0 | 3767 | 11.14 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 329 | 1207 | 329 | 0 | 3804 | 6.19 | 100.0 |
| `multipartite-graph-lookahead` | 330 | 1131 | 319 | 11 | 1304 | 13.81 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 330 | 1171 | 319 | 11 | 1304 | 15.00 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 335 | 1558 | 335 | 0 | 3875 | 12.32 | 100.0 |

### Strength 6

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 729 | 1 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-beam-2` | 729 | 1 | 0 | 0 | 0 | 1.05 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 729 | 2 | 0 | 0 | 0 | 1.05 | 100.0 |
| `multipartite-graph-lookahead-density` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 729 | 1 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 729 | 3 | 0 | 0 | 0 | 1.05 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 729 | 1 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 729 | 1 | 0 | 0 | 0 | 1.04 | 100.0 |
| `multipartite-graph-walk` | 729 | 2 | 0 | 0 | 0 | 1.05 | 100.0 |

## Scenario 6x4

Input data set:
- `P1`: `1.1`, `1.2`, `1.3`, `1.4`
- `P2`: `2.1`, `2.2`, `2.3`, `2.4`
- `P3`: `3.1`, `3.2`, `3.3`, `3.4`
- `P4`: `4.1`, `4.2`, `4.3`, `4.4`
- `P5`: `5.1`, `5.2`, `5.3`, `5.4`
- `P6`: `6.1`, `6.2`, `6.3`, `6.4`

For Cartesian all combinations the number of rows would be 4096.

### Strength 2

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 23 | 8 | 23 | 0 | 0 | 6.55 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 23 | 8 | 23 | 0 | 0 | 6.50 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 23 | 11 | 23 | 0 | 0 | 6.76 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 23 | 6 | 23 | 0 | 0 | 6.58 | 100.0 |
| `multipartite-graph-lookahead` | 24 | 9 | 24 | 0 | 0 | 5.49 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 24 | 16 | 24 | 0 | 0 | 5.28 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 24 | 13 | 24 | 0 | 0 | 6.51 | 100.0 |
| `multipartite-graph-lookahead-density` | 24 | 9 | 24 | 0 | 0 | 6.57 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 24 | 11 | 24 | 0 | 0 | 6.58 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 24 | 13 | 24 | 0 | 0 | 6.78 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 24 | 8 | 13 | 11 | 0 | 5.03 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 25 | 12 | 25 | 0 | 0 | 6.68 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 25 | 13 | 25 | 0 | 0 | 7.98 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 25 | 17 | 25 | 0 | 0 | 7.87 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 25 | 7 | 16 | 9 | 0 | 6.26 | 100.0 |
| `multipartite-graph-walk` | 25 | 9 | 0 | 0 | 0 | 6.24 | 100.0 |
| `multipartite-graph-lookahead-beam-2` | 26 | 7 | 26 | 0 | 0 | 8.34 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 26 | 17 | 26 | 0 | 0 | 8.17 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 26 | 13 | 26 | 0 | 0 | 8.01 | 100.0 |

### Strength 3

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-hardest-partition` | 86 | 227 | 86 | 0 | 233 | 13.51 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 86 | 247 | 86 | 0 | 896 | 0.25 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 86 | 190 | 86 | 0 | 896 | 4.06 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 92 | 166 | 56 | 36 | 757 | 2.45 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 94 | 242 | 94 | 0 | 902 | 3.31 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 109 | 271 | 109 | 0 | 938 | 11.23 | 100.0 |
| `multipartite-graph-lookahead-density` | 109 | 175 | 109 | 0 | 938 | 12.05 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 109 | 336 | 109 | 0 | 938 | 15.53 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 109 | 335 | 109 | 0 | 940 | 8.39 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 109 | 203 | 109 | 0 | 940 | 13.43 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 109 | 161 | 109 | 0 | 940 | 11.44 | 100.0 |
| `multipartite-graph-lookahead-beam-2` | 111 | 188 | 111 | 0 | 952 | 8.39 | 100.0 |
| `multipartite-graph-lookahead` | 112 | 184 | 112 | 0 | 377 | 2.84 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 112 | 412 | 112 | 0 | 940 | 14.67 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 114 | 158 | 114 | 0 | 214 | 9.52 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 116 | 302 | 116 | 0 | 941 | 10.98 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 116 | 135 | 50 | 66 | 200 | 7.48 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 119 | 372 | 119 | 0 | 957 | 2.74 | 100.0 |
| `multipartite-graph-walk` | 119 | 120 | 0 | 0 | 0 | 4.90 | 100.0 |

### Strength 4

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-beam-2` | 423 | 2608 | 423 | 0 | 5760 | 30.94 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 427 | 4190 | 427 | 0 | 5864 | 25.05 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 428 | 4415 | 428 | 0 | 5794 | 44.44 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 431 | 5185 | 431 | 0 | 5859 | 6.27 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 434 | 3085 | 434 | 0 | 5934 | 25.12 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 435 | 3240 | 435 | 0 | 5942 | 20.60 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 435 | 2810 | 435 | 0 | 5950 | 27.33 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 438 | 2470 | 438 | 0 | 5989 | 33.42 | 100.0 |
| `multipartite-graph-lookahead` | 439 | 2164 | 439 | 0 | 1699 | 14.69 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 439 | 3430 | 439 | 0 | 6016 | 30.61 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 441 | 3028 | 441 | 0 | 6001 | 19.85 | 100.0 |
| `multipartite-graph-lookahead-density` | 441 | 2931 | 441 | 0 | 6001 | 20.94 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 441 | 5088 | 441 | 0 | 6001 | 24.00 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 441 | 2876 | 441 | 0 | 6027 | 29.74 | 100.0 |
| `multipartite-graph-walk` | 442 | 1530 | 0 | 0 | 0 | 3.36 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 443 | 3039 | 443 | 0 | 1421 | 18.85 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 446 | 1680 | 204 | 242 | 816 | 8.99 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 446 | 2668 | 249 | 197 | 3976 | 31.21 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 448 | 2089 | 448 | 0 | 448 | 9.91 | 100.0 |

### Strength 5

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 1345 | 50181 | 1345 | 0 | 20830 | 1.96 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 1350 | 23944 | 1350 | 0 | 20895 | 13.05 | 100.0 |
| `multipartite-graph-lookahead-beam-2` | 1355 | 25074 | 1355 | 0 | 20928 | 28.78 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 1358 | 28669 | 1358 | 0 | 21004 | 51.94 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 1361 | 31113 | 1361 | 0 | 21088 | 3.50 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 1384 | 23380 | 1384 | 0 | 5267 | 12.60 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 1386 | 23184 | 1386 | 0 | 21539 | 20.28 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 1387 | 21250 | 1387 | 0 | 21504 | 35.17 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 1389 | 25664 | 1389 | 0 | 21563 | 27.64 | 100.0 |
| `multipartite-graph-walk` | 1392 | 15116 | 0 | 0 | 0 | 12.03 | 100.0 |
| `multipartite-graph-lookahead-density` | 1394 | 20143 | 1394 | 0 | 21555 | 19.98 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 1399 | 24425 | 922 | 477 | 14752 | 16.35 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 1402 | 27287 | 1402 | 0 | 21735 | 22.64 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 1404 | 24154 | 1404 | 0 | 21679 | 37.60 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 1409 | 31194 | 1409 | 0 | 21824 | 31.70 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 1410 | 13135 | 776 | 634 | 3104 | 19.72 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 1413 | 24727 | 1413 | 0 | 21887 | 10.78 | 100.0 |
| `multipartite-graph-lookahead` | 1418 | 18975 | 1301 | 117 | 5632 | 15.32 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 1419 | 16958 | 1301 | 118 | 5636 | 42.52 | 100.0 |

### Strength 6

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead` | 4096 | 8 | 0 | 0 | 0 | 5.06 | 100.0 |
| `multipartite-graph-lookahead-adaptive` | 4096 | 8 | 0 | 0 | 0 | 4.56 | 100.0 |
| `multipartite-graph-lookahead-adaptive-penalty` | 4096 | 5 | 0 | 0 | 0 | 4.99 | 100.0 |
| `multipartite-graph-lookahead-beam-2` | 4096 | 12 | 0 | 0 | 0 | 4.61 | 100.0 |
| `multipartite-graph-lookahead-beam-3` | 4096 | 5 | 0 | 0 | 0 | 4.97 | 100.0 |
| `multipartite-graph-lookahead-density` | 4096 | 5 | 0 | 0 | 0 | 4.95 | 100.0 |
| `multipartite-graph-lookahead-frontier` | 4096 | 9 | 0 | 0 | 0 | 4.61 | 100.0 |
| `multipartite-graph-lookahead-hardest-partition` | 4096 | 7 | 0 | 0 | 0 | 4.84 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed` | 4096 | 8 | 0 | 0 | 0 | 4.58 | 100.0 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 4096 | 8 | 0 | 0 | 0 | 4.87 | 100.0 |
| `multipartite-graph-lookahead-hybrid` | 4096 | 5 | 0 | 0 | 0 | 4.64 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 4096 | 10 | 0 | 0 | 0 | 4.68 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 4096 | 5 | 0 | 0 | 0 | 4.91 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 4096 | 6 | 0 | 0 | 0 | 4.65 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 4096 | 9 | 0 | 0 | 0 | 4.51 | 100.0 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 4096 | 9 | 0 | 0 | 0 | 5.01 | 100.0 |
| `multipartite-graph-lookahead-scarcity` | 4096 | 10 | 0 | 0 | 0 | 4.63 | 100.0 |
| `multipartite-graph-lookahead-scarcity-frontier` | 4096 | 7 | 0 | 0 | 0 | 5.18 | 100.0 |
| `multipartite-graph-walk` | 4096 | 9 | 0 | 0 | 0 | 4.59 | 100.0 |

