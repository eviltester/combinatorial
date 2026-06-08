# Experimental N-Wise Algorithm Report

This report covers experimental multipartite strategies only. Stable pairwise, IPOG, greedy, and production n-wise implementations remain outside this comparison on purpose.

## Overall Summary

- Best runtime across non-full-factorial cases: `multipartite-graph-walk`
- Best row count across non-full-factorial cases: `multipartite-graph-walk`
- Best balanced runtime/rows result: `multipartite-graph-walk`

| Strategy | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-walk` | 313.6 | 2192.0 | 0.0 | 0.0 | 0.0 | 2.02 |
| `multipartite-graph-lookahead` | 315.8 | 2862.4 | 299.8 | 16.0 | 1214.0 | 2.56 |

## Baseline Experimental Strategies

- Best runtime across non-full-factorial cases: `multipartite-graph-walk`
- Best row count across non-full-factorial cases: `multipartite-graph-walk`
- Best balanced runtime/rows result: `multipartite-graph-walk`

| Baseline strategy | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-walk` | 313.6 | 2192.0 | 0.0 | 0.0 | 0.0 | 2.02 |
| `multipartite-graph-lookahead` | 315.8 | 2862.4 | 299.8 | 16.0 | 1214.0 | 2.56 |

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

- No completed benchmark rows yet.

| Documented experiment | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |

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

- No completed benchmark rows yet.

| Results-driven experiment | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |

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
| `multipartite-graph-lookahead` | 15 | 8 | 15 | 0 | 0 | 2.53 | 100.0 |
| `multipartite-graph-walk` | 15 | 8 | 0 | 0 | 0 | 1.07 | 100.0 |

### Strength 3

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead` | 48 | 66 | 48 | 0 | 159 | 12.72 | 100.0 |
| `multipartite-graph-walk` | 49 | 35 | 0 | 0 | 0 | 1.34 | 100.0 |

### Strength 4

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead` | 140 | 362 | 140 | 0 | 541 | 1.14 | 100.0 |
| `multipartite-graph-walk` | 142 | 151 | 0 | 0 | 0 | 2.27 | 100.0 |

### Strength 5

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-walk` | 325 | 567 | 0 | 0 | 0 | 13.27 | 100.0 |
| `multipartite-graph-lookahead` | 330 | 1131 | 319 | 11 | 1304 | 13.81 | 100.0 |

### Strength 6

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead` | 729 | 2 | 0 | 0 | 0 | 1.04 | 100.0 |
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
| `multipartite-graph-lookahead` | 24 | 9 | 24 | 0 | 0 | 5.49 | 100.0 |
| `multipartite-graph-walk` | 25 | 9 | 0 | 0 | 0 | 6.24 | 100.0 |

### Strength 3

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead` | 112 | 184 | 112 | 0 | 377 | 2.84 | 100.0 |
| `multipartite-graph-walk` | 119 | 120 | 0 | 0 | 0 | 4.90 | 100.0 |

### Strength 4

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead` | 439 | 2164 | 439 | 0 | 1699 | 14.69 | 100.0 |
| `multipartite-graph-walk` | 442 | 1530 | 0 | 0 | 0 | 3.36 | 100.0 |

### Strength 5

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-walk` | 1392 | 15116 | 0 | 0 | 0 | 12.03 | 100.0 |
| `multipartite-graph-lookahead` | 1418 | 18975 | 1301 | 117 | 5632 | 15.32 | 100.0 |

### Strength 6

| Strategy | Rows | Runtime ms | Graph rows | Fallback rows | Lookahead evals | Heap delta MiB | Coverage % |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead` | 4096 | 8 | 0 | 0 | 0 | 5.06 | 100.0 |
| `multipartite-graph-walk` | 4096 | 9 | 0 | 0 | 0 | 4.59 | 100.0 |

