# N-Wise Experiment Running Report

Completed strategies: 62 / 62

- Best runtime across non-full-factorial cases: `multipartite-graph-lookahead-hybrid`
- Best row count across non-full-factorial cases: `multipartite-graph-lookahead-rd-scarcity-density-beam-3`
- Best balanced runtime/rows result: `multipartite-graph-lookahead-hybrid`

| Strategy | Avg rows | Avg runtime ms | Avg graph rows | Avg fallback rows | Avg lookahead evals | Balanced score |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| `multipartite-graph-lookahead-hybrid` | 316.5 | 2005.6 | 165.3 | 151.3 | 650.5 | 2.30 |
| `multipartite-graph-lookahead-rd-scarcity-density-hybrid-tail-20` | 312.4 | 2645.3 | 196.6 | 115.8 | 2908.5 | 2.36 |
| `multipartite-graph-walk` | 313.6 | 2192.0 | 0.0 | 0.0 | 0.0 | 2.43 |
| `multipartite-graph-lookahead-rd-density-strong-hybrid-tail-20` | 311.3 | 2573.3 | 196.5 | 114.8 | 2900.4 | 2.49 |
| `multipartite-graph-lookahead-rd-scarcity-strong-hybrid-tail-20` | 311.5 | 2662.5 | 197.4 | 114.1 | 2921.6 | 2.51 |
| `multipartite-graph-lookahead-rd-density-lite-hybrid-tail-20` | 314.3 | 3321.0 | 197.1 | 117.1 | 2910.9 | 2.61 |
| `multipartite-graph-lookahead-rd-density-strong-adaptive-penalty` | 310.9 | 3047.0 | 310.9 | 0.0 | 4236.3 | 2.65 |
| `multipartite-graph-lookahead-rd-scarcity-strong-adaptive-penalty` | 307.4 | 2937.9 | 307.4 | 0.0 | 4239.9 | 2.67 |
| `multipartite-graph-lookahead-rd-scarcity-density-beam-3` | 302.1 | 2932.3 | 302.1 | 0.0 | 4127.6 | 2.70 |
| `multipartite-graph-lookahead-rd-density-strong-candidate-6` | 311.3 | 3589.8 | 311.3 | 0.0 | 4248.9 | 2.72 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-6` | 312.6 | 3919.0 | 312.6 | 0.0 | 4271.5 | 2.72 |
| `multipartite-graph-lookahead-scarcity-frontier` | 306.8 | 3809.0 | 306.8 | 0.0 | 4230.1 | 2.73 |
| `multipartite-graph-lookahead-rd-density-lite-beam-2` | 304.3 | 3212.6 | 304.3 | 0.0 | 4133.8 | 2.75 |
| `multipartite-graph-lookahead-rd-scarcity-density-candidate-2` | 314.3 | 3069.0 | 314.3 | 0.0 | 4295.5 | 2.78 |
| `multipartite-graph-lookahead-rd-scarcity-density-candidate-6` | 310.5 | 5253.4 | 310.5 | 0.0 | 4243.4 | 2.79 |
| `multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20` | 311.1 | 3641.6 | 196.8 | 114.4 | 2912.1 | 2.80 |
| `multipartite-graph-lookahead-rd-scarcity-lite-candidate-2` | 310.9 | 3239.0 | 310.9 | 0.0 | 4245.8 | 2.84 |
| `multipartite-graph-lookahead-density` | 311.1 | 3148.3 | 311.1 | 0.0 | 4239.0 | 2.90 |
| `multipartite-graph-lookahead-rd-frontier-lite-candidate-2` | 313.0 | 3700.0 | 313.0 | 0.0 | 4261.8 | 2.92 |
| `multipartite-graph-lookahead-rd-frontier-lite-candidate-6` | 312.5 | 3484.3 | 312.5 | 0.0 | 4268.5 | 2.94 |
| `multipartite-graph-lookahead-rd-scarcity-density-adaptive-penalty` | 306.8 | 6299.4 | 306.8 | 0.0 | 4230.1 | 2.96 |
| `multipartite-graph-lookahead-scarcity` | 307.5 | 3503.1 | 307.5 | 0.0 | 4241.6 | 2.98 |
| `multipartite-graph-lookahead-rd-frontier-density-hybrid-tail-20` | 312.4 | 3512.9 | 196.9 | 115.5 | 2906.4 | 3.00 |
| `multipartite-graph-lookahead` | 315.8 | 2862.4 | 299.8 | 16.0 | 1214.0 | 3.00 |
| `multipartite-graph-lookahead-rd-density-lite-candidate-6` | 312.8 | 3381.3 | 312.8 | 0.0 | 4274.5 | 3.01 |
| `multipartite-graph-lookahead-rd-scarcity-strong-beam-2` | 303.8 | 4070.8 | 303.8 | 0.0 | 4146.8 | 3.03 |
| `multipartite-graph-lookahead-rd-scarcity-lite-adaptive-penalty` | 307.6 | 4182.0 | 307.6 | 0.0 | 4249.0 | 3.08 |
| `multipartite-graph-lookahead-rd-density-lite-beam-3` | 306.1 | 3798.5 | 306.1 | 0.0 | 4150.8 | 3.08 |
| `multipartite-graph-lookahead-hardest-partition` | 308.5 | 3567.0 | 308.5 | 0.0 | 1031.3 | 3.08 |
| `multipartite-graph-lookahead-hardest-seed-beam` | 305.3 | 3639.1 | 305.3 | 0.0 | 4143.1 | 3.09 |
| `multipartite-graph-lookahead-hardest-seed` | 313.4 | 3800.1 | 313.4 | 0.0 | 4300.9 | 3.09 |
| `multipartite-graph-lookahead-adaptive-penalty` | 312.1 | 3672.6 | 312.1 | 0.0 | 4251.8 | 3.10 |
| `multipartite-graph-lookahead-rd-frontier-density-candidate-6` | 311.6 | 4642.6 | 311.6 | 0.0 | 4252.6 | 3.13 |
| `multipartite-graph-lookahead-rd-density-strong-beam-3` | 306.5 | 3421.5 | 306.5 | 0.0 | 4145.3 | 3.19 |
| `multipartite-graph-lookahead-beam-2` | 303.0 | 3662.8 | 303.0 | 0.0 | 4113.8 | 3.20 |
| `multipartite-graph-lookahead-adaptive` | 318.4 | 2594.4 | 302.3 | 16.1 | 980.4 | 3.21 |
| `multipartite-graph-lookahead-rd-frontier-lite-beam-2` | 304.3 | 5091.3 | 304.3 | 0.0 | 4131.6 | 3.26 |
| `multipartite-graph-lookahead-rd-frontier-lite-adaptive-penalty` | 313.8 | 3913.8 | 313.8 | 0.0 | 4289.1 | 3.28 |
| `multipartite-graph-lookahead-rd-scarcity-strong-candidate-6` | 312.5 | 3790.1 | 312.5 | 0.0 | 4268.3 | 3.29 |
| `multipartite-graph-lookahead-rd-scarcity-strong-candidate-2` | 311.8 | 3987.8 | 311.8 | 0.0 | 4265.4 | 3.40 |
| `multipartite-graph-lookahead-rd-frontier-density-candidate-2` | 312.9 | 3944.5 | 312.9 | 0.0 | 4261.0 | 3.44 |
| `multipartite-graph-lookahead-rd-frontier-density-adaptive-penalty` | 312.3 | 4300.6 | 312.3 | 0.0 | 4256.1 | 3.46 |
| `multipartite-graph-lookahead-rd-frontier-strong-candidate-2` | 314.5 | 5083.3 | 314.5 | 0.0 | 4284.6 | 3.52 |
| `multipartite-graph-lookahead-frontier` | 313.5 | 4832.6 | 313.5 | 0.0 | 4277.6 | 3.53 |
| `multipartite-graph-lookahead-rd-frontier-strong-hybrid-tail-20` | 314.8 | 5264.4 | 197.9 | 116.9 | 2921.4 | 3.55 |
| `multipartite-graph-lookahead-rd-frontier-strong-beam-2` | 303.9 | 5207.6 | 303.9 | 0.0 | 4129.1 | 3.63 |
| `multipartite-graph-lookahead-rd-scarcity-strong-beam-3` | 304.5 | 4731.5 | 304.5 | 0.0 | 4146.0 | 3.74 |
| `multipartite-graph-lookahead-rd-density-lite-adaptive-penalty` | 313.4 | 7337.9 | 313.4 | 0.0 | 4273.5 | 3.74 |
| `multipartite-graph-lookahead-rd-frontier-lite-hybrid-tail-20` | 314.1 | 4589.1 | 197.6 | 116.5 | 2917.9 | 3.79 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 302.3 | 7125.4 | 302.3 | 0.0 | 4117.6 | 3.92 |
| `multipartite-graph-lookahead-rd-frontier-density-beam-3` | 304.5 | 5874.1 | 304.5 | 0.0 | 4128.9 | 3.95 |
| `multipartite-graph-lookahead-rd-density-strong-beam-2` | 303.5 | 6872.1 | 303.5 | 0.0 | 4121.5 | 3.97 |
| `multipartite-graph-lookahead-beam-3` | 305.6 | 4801.8 | 305.6 | 0.0 | 4145.3 | 3.99 |
| `multipartite-graph-lookahead-rd-frontier-strong-adaptive-penalty` | 312.4 | 6194.6 | 312.4 | 0.0 | 4263.8 | 4.26 |
| `multipartite-graph-lookahead-rd-frontier-strong-beam-3` | 304.1 | 5935.1 | 304.1 | 0.0 | 4121.0 | 4.27 |
| `multipartite-graph-lookahead-rd-frontier-strong-candidate-6` | 314.4 | 4586.9 | 314.4 | 0.0 | 4292.3 | 4.29 |
| `multipartite-graph-lookahead-rd-scarcity-lite-beam-3` | 304.9 | 4711.4 | 304.9 | 0.0 | 4138.1 | 4.58 |
| `multipartite-graph-lookahead-rd-frontier-lite-beam-3` | 306.1 | 6907.5 | 306.1 | 0.0 | 4145.9 | 4.78 |
| `multipartite-graph-lookahead-rd-frontier-density-beam-2` | 303.8 | 9411.0 | 303.8 | 0.0 | 4125.4 | 4.84 |
| `multipartite-graph-lookahead-rd-density-strong-candidate-2` | 311.4 | 3478.6 | 311.4 | 0.0 | 4242.5 | 5.24 |
| `multipartite-graph-lookahead-rd-scarcity-density-beam-2` | 304.4 | 5144.5 | 304.4 | 0.0 | 4146.4 | 5.80 |
| `multipartite-graph-lookahead-rd-density-lite-candidate-2` | 312.9 | 6425.9 | 312.9 | 0.0 | 4263.1 | 7.01 |

## Next Experiments Suggested By Results

- Add a `scarcity-density-beam-3-tail` family: `multipartite-graph-lookahead-rd-scarcity-density-beam-3` won row count, but beam work needs a hybrid tail or collapse guard before it can compete on balanced score.
- Add `scarcity-density-hybrid-tail-10` and `scarcity-density-hybrid-tail-30`: the strongest result-driven balanced variant used the 20% hybrid tail, so the next pass should sweep the handoff threshold.
- Add a dynamic beam policy that starts at width 3 for small/front-loaded cases and collapses to width 1 or fallback once uncovered tuples fall below the hybrid-tail threshold.
- Add a candidate-limit sweep around the best density/scarcity profiles using limits 1, 3, and 5; limit 6 rarely offset its extra runtime enough in this run.
- De-prioritize pure frontier-heavy variants unless they are paired with density or a tail guard; frontier-only scoring trailed the best balanced and row-count profiles.

## Latest Completed Strategies

- `multipartite-graph-lookahead-rd-scarcity-density-beam-2`
- `multipartite-graph-lookahead-rd-scarcity-density-beam-3`
- `multipartite-graph-lookahead-rd-scarcity-density-hybrid-tail-20`
- `multipartite-graph-lookahead-rd-scarcity-density-adaptive-penalty`
- `multipartite-graph-lookahead-rd-frontier-density-candidate-2`
- `multipartite-graph-lookahead-rd-frontier-density-candidate-6`
- `multipartite-graph-lookahead-rd-frontier-density-beam-2`
- `multipartite-graph-lookahead-rd-frontier-density-beam-3`
- `multipartite-graph-lookahead-rd-frontier-density-hybrid-tail-20`
- `multipartite-graph-lookahead-rd-frontier-density-adaptive-penalty`

