# Comparison To Other Strategies

This compares the new JavaScript n-wise experiments against the stable n-wise and pairwise strategies currently used in the grid-table-editor app. The n-wise comparison uses the same benchmark shapes as the experimental report:

- 6 parameters x 3 values
- 6 parameters x 4 values
- strengths 2 through 6
- non-full-factorial strengths 2 through 5 are used for average summaries
- candidate count 8 and seed 17 where supported

`pairwise-legacy` is listed separately because it only supports strength 2.

## Headline

- Pairwise legacy strength-2 average: 22.5 rows, 3.5 ms
- Best stable n-wise row count: `greedy` (256.8 average rows)
- Best stable n-wise runtime: `ipog` (6.5 ms average)
- Best new experiment row count: `multipartite-graph-lookahead-rd-scarcity-density-beam-3` (302.1 average rows)
- Best new experiment runtime: `multipartite-graph-lookahead-rd-density-strong-hybrid-tail-20` (2573.3 ms average)

The new strategies are useful research probes, but they do not beat the stable app strategies on this benchmark set. `greedy` and `ipog` are much faster and produce fewer average rows than the best new experimental strategy. `aetg` also beats the best new experiment on row count while running much faster.

## Pairwise Legacy Summary

| Group | Strategy | Cases | Avg rows | Avg runtime ms |
| --- | --- | ---: | ---: | ---: |
| Stable app strategy | `pairwise-legacy` | 2 | 22.5 | 3.5 |

## Stable N-Wise Strategy Summary

| Group | Strategy | Cases | Avg rows | Avg runtime ms |
| --- | --- | ---: | ---: | ---: |
| Stable app strategy | `greedy` | 8 | 256.8 | 12.5 |
| Stable app strategy | `ipog` | 8 | 265.8 | 6.5 |
| Stable app strategy | `aetg` | 8 | 298.0 | 204.1 |
| Stable app strategy | `hypergraph-vertex` | 8 | 312.1 | 1492.5 |
| Stable app strategy | `pict-gcd` | 8 | 315.1 | 23.4 |
| Stable app strategy | `compatibility-graph` | 8 | 345.1 | 1231.9 |

## Best New Experiment Summary

| Group | Strategy | Cases | Avg rows | Avg runtime ms |
| --- | --- | ---: | ---: | ---: |
| New JS experiment | `multipartite-graph-lookahead-rd-scarcity-density-beam-3` | 8 | 302.1 | 2932.3 |
| New JS experiment | `multipartite-graph-lookahead-rd-scarcity-lite-beam-2` | 8 | 302.3 | 7125.4 |
| New JS experiment | `multipartite-graph-lookahead-beam-2` | 8 | 303.0 | 3662.8 |
| New JS experiment | `multipartite-graph-lookahead-rd-density-strong-beam-2` | 8 | 303.5 | 6872.1 |
| New JS experiment | `multipartite-graph-lookahead-rd-scarcity-strong-beam-2` | 8 | 303.8 | 4070.8 |
| New JS experiment | `multipartite-graph-lookahead-rd-frontier-density-beam-2` | 8 | 303.8 | 9411.0 |
| New JS experiment | `multipartite-graph-lookahead-rd-frontier-strong-beam-2` | 8 | 303.9 | 5207.6 |
| New JS experiment | `multipartite-graph-lookahead-rd-frontier-strong-beam-3` | 8 | 304.1 | 5935.1 |
| New JS experiment | `multipartite-graph-lookahead-rd-frontier-lite-beam-2` | 8 | 304.3 | 5091.3 |
| New JS experiment | `multipartite-graph-lookahead-rd-density-lite-beam-2` | 8 | 304.3 | 3212.6 |

## Stable Strategy Case Results

| Shape | Strength | Strategy | Rows | Runtime ms | Coverage % |
| --- | ---: | --- | ---: | ---: | ---: |
| 6x3 | 2 | `pict-gcd` | 13 | 2 | 100.0 |
| 6x3 | 2 | `aetg` | 14 | 14 | 100.0 |
| 6x3 | 2 | `hypergraph-vertex` | 14 | 8 | 100.0 |
| 6x3 | 2 | `compatibility-graph` | 16 | 5 | 100.0 |
| 6x3 | 2 | `pairwise-legacy` | 17 | 3 | 100.0 |
| 6x3 | 2 | `greedy` | 17 | 3 | 100.0 |
| 6x3 | 2 | `ipog` | 17 | 2 | 100.0 |
| 6x3 | 3 | `aetg` | 45 | 47 | 100.0 |
| 6x3 | 3 | `greedy` | 48 | 5 | 100.0 |
| 6x3 | 3 | `pict-gcd` | 48 | 5 | 100.0 |
| 6x3 | 3 | `hypergraph-vertex` | 50 | 35 | 100.0 |
| 6x3 | 3 | `compatibility-graph` | 51 | 23 | 100.0 |
| 6x3 | 3 | `ipog` | 58 | 4 | 100.0 |
| 6x3 | 4 | `aetg` | 134 | 103 | 100.0 |
| 6x3 | 4 | `greedy` | 137 | 9 | 100.0 |
| 6x3 | 4 | `hypergraph-vertex` | 141 | 180 | 100.0 |
| 6x3 | 4 | `pict-gcd` | 142 | 11 | 100.0 |
| 6x3 | 4 | `compatibility-graph` | 153 | 123 | 100.0 |
| 6x3 | 4 | `ipog` | 169 | 6 | 100.0 |
| 6x3 | 5 | `greedy` | 288 | 9 | 100.0 |
| 6x3 | 5 | `aetg` | 308 | 97 | 100.0 |
| 6x3 | 5 | `ipog` | 318 | 5 | 100.0 |
| 6x3 | 5 | `hypergraph-vertex` | 330 | 450 | 100.0 |
| 6x3 | 5 | `pict-gcd` | 331 | 9 | 100.0 |
| 6x3 | 5 | `compatibility-graph` | 368 | 379 | 100.0 |
| 6x4 | 2 | `pict-gcd` | 23 | 3 | 100.0 |
| 6x4 | 2 | `aetg` | 23 | 25 | 100.0 |
| 6x4 | 2 | `compatibility-graph` | 24 | 4 | 100.0 |
| 6x4 | 2 | `hypergraph-vertex` | 26 | 14 | 100.0 |
| 6x4 | 2 | `pairwise-legacy` | 28 | 4 | 100.0 |
| 6x4 | 2 | `greedy` | 28 | 3 | 100.0 |
| 6x4 | 2 | `ipog` | 28 | 1 | 100.0 |
| 6x4 | 3 | `greedy` | 64 | 8 | 100.0 |
| 6x4 | 3 | `ipog` | 64 | 5 | 100.0 |
| 6x4 | 3 | `aetg` | 105 | 131 | 100.0 |
| 6x4 | 3 | `pict-gcd` | 110 | 18 | 100.0 |
| 6x4 | 3 | `hypergraph-vertex` | 115 | 129 | 100.0 |
| 6x4 | 3 | `compatibility-graph` | 120 | 73 | 100.0 |
| 6x4 | 4 | `aetg` | 420 | 337 | 100.0 |
| 6x4 | 4 | `pict-gcd` | 440 | 34 | 100.0 |
| 6x4 | 4 | `hypergraph-vertex` | 446 | 1821 | 100.0 |
| 6x4 | 4 | `greedy` | 448 | 37 | 100.0 |
| 6x4 | 4 | `ipog` | 448 | 14 | 100.0 |
| 6x4 | 4 | `compatibility-graph` | 495 | 1197 | 100.0 |
| 6x4 | 5 | `greedy` | 1024 | 26 | 100.0 |
| 6x4 | 5 | `ipog` | 1024 | 15 | 100.0 |
| 6x4 | 5 | `aetg` | 1335 | 879 | 100.0 |
| 6x4 | 5 | `hypergraph-vertex` | 1375 | 9303 | 100.0 |
| 6x4 | 5 | `pict-gcd` | 1414 | 105 | 100.0 |
| 6x4 | 5 | `compatibility-graph` | 1534 | 8051 | 100.0 |
