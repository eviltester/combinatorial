# N-Wise Evolution

This folder holds the multipartite and lookahead experiments outside the app repo so the stable app implementation can stay focused on the normal implementations.

## Current experimental set

- `multipartite-graph-walk`
- `multipartite-graph-lookahead`
- `multipartite-graph-lookahead-adaptive`
- `multipartite-graph-lookahead-hybrid`

## Suggested next experiments

These are the next experiments to add as separate strategy files. The focus is single-pass generation that tries to reduce row count without turning runtime into a multi-pass cleanup problem.

1. `multipartite-graph-lookahead-scarcity`

   - Weight tuples by rarity so hard-to-cover tuples dominate row construction earlier.

2. `multipartite-graph-lookahead-frontier`

   - Score candidates by immediate coverage plus how many compatible next-step choices remain after selecting them.

3. `multipartite-graph-lookahead-hardest-seed`

   - Seed each row from the rarest uncovered edge or tuple fragment rather than the current broad edge-pressure heuristic.

4. `multipartite-graph-lookahead-hardest-partition`

   - Choose the next partition by minimum remaining compatible values before choosing the next node inside it.

5. `multipartite-graph-lookahead-beam-2`

   - Keep the best two partial rows while constructing a row, then emit the strongest finished candidate.

6. `multipartite-graph-lookahead-beam-3`

   - Same as beam-2, but with three live partial rows to test whether row count drops enough to justify the extra runtime.

7. `multipartite-graph-lookahead-adaptive-penalty`

   - Make node and edge reuse penalties lighter early and stronger late so early dense coverage is not over-penalized.

8. `multipartite-graph-lookahead-density`

   - Replace raw tuple gain with a density-style score that rewards candidates that co-cover hard tuples efficiently.

9. `multipartite-graph-lookahead-scarcity-frontier`

   - Combine rarity weighting with frontier preservation to test whether the two strongest heuristics compound well.

10. `multipartite-graph-lookahead-hardest-seed-beam`
    - Pair hard seeding with a tiny beam width to test whether better row starts plus limited branching reduces row count more reliably.

## Results-driven follow-ups to watch

- If `hybrid` keeps winning runtime but loses a little on row count, try scarcity weighting only in the graph phase before the handoff.
- If beam width `2` cuts rows meaningfully, add a stricter runtime guard so the beam can collapse back to width `1` on large cases.
- If rarity weighting helps row count but slows too much, cache rarity scores per uncovered target slice instead of recomputing them per candidate.

## Results after 62-strategy run

The full experiment run completed 62 strategies: 4 baseline experimental strategies, 10 documented evolution strategies, and 48 result-driven follow-up variants. Results are cached in `n-wise-experiment-results.json`, summarized in `n-wise-experiment-running-report.md`, and fully expanded in `n-wise-algorithm-report.md`. Per-strategy full comparison snapshots are in `reports/`.

- Best runtime and balanced score: `multipartite-graph-lookahead-hybrid`.
- Best row count: `multipartite-graph-lookahead-rd-scarcity-density-beam-3`.
- Best result-driven balanced variant: `multipartite-graph-lookahead-rd-scarcity-density-hybrid-tail-20`.
- Strongest row-count family: scarcity/density plus beam width 2 or 3.
- Strongest runtime/row-count compromise among new variants: scarcity/density or density-strong with a hybrid tail.

## Next experiments suggested by results

- Add `scarcity-density-beam-3-tail` variants that combine the best row-count family with a hybrid tail handoff.
- Sweep hybrid tail thresholds for scarcity-density: `hybrid-tail-10`, `hybrid-tail-15`, `hybrid-tail-25`, and `hybrid-tail-30`.
- Add dynamic beam collapse variants that start at beam width 3, then collapse to width 1 when uncovered tuple density falls below a threshold.
- Add candidate-limit sweeps around the strongest scarcity/density profiles using limits 1, 3, and 5.
- De-prioritize frontier-only scoring unless combined with density or a tail guard.
