import { generateMultipartiteGraphLookaheadScarcityRecords } from './multipartite-graph-lookahead-scarcity.js';
import { generateMultipartiteGraphLookaheadFrontierRecords } from './multipartite-graph-lookahead-frontier.js';
import { generateMultipartiteGraphLookaheadHardestSeedRecords } from './multipartite-graph-lookahead-hardest-seed.js';
import { generateMultipartiteGraphLookaheadHardestPartitionRecords } from './multipartite-graph-lookahead-hardest-partition.js';
import { generateMultipartiteGraphLookaheadBeam2Records } from './multipartite-graph-lookahead-beam-2.js';
import { generateMultipartiteGraphLookaheadBeam3Records } from './multipartite-graph-lookahead-beam-3.js';
import { generateMultipartiteGraphLookaheadAdaptivePenaltyRecords } from './multipartite-graph-lookahead-adaptive-penalty.js';
import { generateMultipartiteGraphLookaheadDensityRecords } from './multipartite-graph-lookahead-density.js';
import { generateMultipartiteGraphLookaheadScarcityFrontierRecords } from './multipartite-graph-lookahead-scarcity-frontier.js';
import { generateMultipartiteGraphLookaheadHardestSeedBeamRecords } from './multipartite-graph-lookahead-hardest-seed-beam.js';
import { generateMultipartiteGraphLookaheadRdScarcityLiteCandidate2Records } from './multipartite-graph-lookahead-rd-scarcity-lite-candidate-2.js';
import { generateMultipartiteGraphLookaheadRdScarcityLiteCandidate6Records } from './multipartite-graph-lookahead-rd-scarcity-lite-candidate-6.js';
import { generateMultipartiteGraphLookaheadRdScarcityLiteBeam2Records } from './multipartite-graph-lookahead-rd-scarcity-lite-beam-2.js';
import { generateMultipartiteGraphLookaheadRdScarcityLiteBeam3Records } from './multipartite-graph-lookahead-rd-scarcity-lite-beam-3.js';
import { generateMultipartiteGraphLookaheadRdScarcityLiteHybridTail20Records } from './multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20.js';
import { generateMultipartiteGraphLookaheadRdScarcityLiteAdaptivePenaltyRecords } from './multipartite-graph-lookahead-rd-scarcity-lite-adaptive-penalty.js';
import { generateMultipartiteGraphLookaheadRdScarcityStrongCandidate2Records } from './multipartite-graph-lookahead-rd-scarcity-strong-candidate-2.js';
import { generateMultipartiteGraphLookaheadRdScarcityStrongCandidate6Records } from './multipartite-graph-lookahead-rd-scarcity-strong-candidate-6.js';
import { generateMultipartiteGraphLookaheadRdScarcityStrongBeam2Records } from './multipartite-graph-lookahead-rd-scarcity-strong-beam-2.js';
import { generateMultipartiteGraphLookaheadRdScarcityStrongBeam3Records } from './multipartite-graph-lookahead-rd-scarcity-strong-beam-3.js';
import { generateMultipartiteGraphLookaheadRdScarcityStrongHybridTail20Records } from './multipartite-graph-lookahead-rd-scarcity-strong-hybrid-tail-20.js';
import { generateMultipartiteGraphLookaheadRdScarcityStrongAdaptivePenaltyRecords } from './multipartite-graph-lookahead-rd-scarcity-strong-adaptive-penalty.js';
import { generateMultipartiteGraphLookaheadRdFrontierLiteCandidate2Records } from './multipartite-graph-lookahead-rd-frontier-lite-candidate-2.js';
import { generateMultipartiteGraphLookaheadRdFrontierLiteCandidate6Records } from './multipartite-graph-lookahead-rd-frontier-lite-candidate-6.js';
import { generateMultipartiteGraphLookaheadRdFrontierLiteBeam2Records } from './multipartite-graph-lookahead-rd-frontier-lite-beam-2.js';
import { generateMultipartiteGraphLookaheadRdFrontierLiteBeam3Records } from './multipartite-graph-lookahead-rd-frontier-lite-beam-3.js';
import { generateMultipartiteGraphLookaheadRdFrontierLiteHybridTail20Records } from './multipartite-graph-lookahead-rd-frontier-lite-hybrid-tail-20.js';
import { generateMultipartiteGraphLookaheadRdFrontierLiteAdaptivePenaltyRecords } from './multipartite-graph-lookahead-rd-frontier-lite-adaptive-penalty.js';
import { generateMultipartiteGraphLookaheadRdFrontierStrongCandidate2Records } from './multipartite-graph-lookahead-rd-frontier-strong-candidate-2.js';
import { generateMultipartiteGraphLookaheadRdFrontierStrongCandidate6Records } from './multipartite-graph-lookahead-rd-frontier-strong-candidate-6.js';
import { generateMultipartiteGraphLookaheadRdFrontierStrongBeam2Records } from './multipartite-graph-lookahead-rd-frontier-strong-beam-2.js';
import { generateMultipartiteGraphLookaheadRdFrontierStrongBeam3Records } from './multipartite-graph-lookahead-rd-frontier-strong-beam-3.js';
import { generateMultipartiteGraphLookaheadRdFrontierStrongHybridTail20Records } from './multipartite-graph-lookahead-rd-frontier-strong-hybrid-tail-20.js';
import { generateMultipartiteGraphLookaheadRdFrontierStrongAdaptivePenaltyRecords } from './multipartite-graph-lookahead-rd-frontier-strong-adaptive-penalty.js';
import { generateMultipartiteGraphLookaheadRdDensityLiteCandidate2Records } from './multipartite-graph-lookahead-rd-density-lite-candidate-2.js';
import { generateMultipartiteGraphLookaheadRdDensityLiteCandidate6Records } from './multipartite-graph-lookahead-rd-density-lite-candidate-6.js';
import { generateMultipartiteGraphLookaheadRdDensityLiteBeam2Records } from './multipartite-graph-lookahead-rd-density-lite-beam-2.js';
import { generateMultipartiteGraphLookaheadRdDensityLiteBeam3Records } from './multipartite-graph-lookahead-rd-density-lite-beam-3.js';
import { generateMultipartiteGraphLookaheadRdDensityLiteHybridTail20Records } from './multipartite-graph-lookahead-rd-density-lite-hybrid-tail-20.js';
import { generateMultipartiteGraphLookaheadRdDensityLiteAdaptivePenaltyRecords } from './multipartite-graph-lookahead-rd-density-lite-adaptive-penalty.js';
import { generateMultipartiteGraphLookaheadRdDensityStrongCandidate2Records } from './multipartite-graph-lookahead-rd-density-strong-candidate-2.js';
import { generateMultipartiteGraphLookaheadRdDensityStrongCandidate6Records } from './multipartite-graph-lookahead-rd-density-strong-candidate-6.js';
import { generateMultipartiteGraphLookaheadRdDensityStrongBeam2Records } from './multipartite-graph-lookahead-rd-density-strong-beam-2.js';
import { generateMultipartiteGraphLookaheadRdDensityStrongBeam3Records } from './multipartite-graph-lookahead-rd-density-strong-beam-3.js';
import { generateMultipartiteGraphLookaheadRdDensityStrongHybridTail20Records } from './multipartite-graph-lookahead-rd-density-strong-hybrid-tail-20.js';
import { generateMultipartiteGraphLookaheadRdDensityStrongAdaptivePenaltyRecords } from './multipartite-graph-lookahead-rd-density-strong-adaptive-penalty.js';
import { generateMultipartiteGraphLookaheadRdScarcityDensityCandidate2Records } from './multipartite-graph-lookahead-rd-scarcity-density-candidate-2.js';
import { generateMultipartiteGraphLookaheadRdScarcityDensityCandidate6Records } from './multipartite-graph-lookahead-rd-scarcity-density-candidate-6.js';
import { generateMultipartiteGraphLookaheadRdScarcityDensityBeam2Records } from './multipartite-graph-lookahead-rd-scarcity-density-beam-2.js';
import { generateMultipartiteGraphLookaheadRdScarcityDensityBeam3Records } from './multipartite-graph-lookahead-rd-scarcity-density-beam-3.js';
import { generateMultipartiteGraphLookaheadRdScarcityDensityHybridTail20Records } from './multipartite-graph-lookahead-rd-scarcity-density-hybrid-tail-20.js';
import { generateMultipartiteGraphLookaheadRdScarcityDensityAdaptivePenaltyRecords } from './multipartite-graph-lookahead-rd-scarcity-density-adaptive-penalty.js';
import { generateMultipartiteGraphLookaheadRdFrontierDensityCandidate2Records } from './multipartite-graph-lookahead-rd-frontier-density-candidate-2.js';
import { generateMultipartiteGraphLookaheadRdFrontierDensityCandidate6Records } from './multipartite-graph-lookahead-rd-frontier-density-candidate-6.js';
import { generateMultipartiteGraphLookaheadRdFrontierDensityBeam2Records } from './multipartite-graph-lookahead-rd-frontier-density-beam-2.js';
import { generateMultipartiteGraphLookaheadRdFrontierDensityBeam3Records } from './multipartite-graph-lookahead-rd-frontier-density-beam-3.js';
import { generateMultipartiteGraphLookaheadRdFrontierDensityHybridTail20Records } from './multipartite-graph-lookahead-rd-frontier-density-hybrid-tail-20.js';
import { generateMultipartiteGraphLookaheadRdFrontierDensityAdaptivePenaltyRecords } from './multipartite-graph-lookahead-rd-frontier-density-adaptive-penalty.js';

export const EVOLUTION_N_WISE_STRATEGIES = Object.freeze({
  'multipartite-graph-lookahead-scarcity': generateMultipartiteGraphLookaheadScarcityRecords,
  'multipartite-graph-lookahead-frontier': generateMultipartiteGraphLookaheadFrontierRecords,
  'multipartite-graph-lookahead-hardest-seed': generateMultipartiteGraphLookaheadHardestSeedRecords,
  'multipartite-graph-lookahead-hardest-partition': generateMultipartiteGraphLookaheadHardestPartitionRecords,
  'multipartite-graph-lookahead-beam-2': generateMultipartiteGraphLookaheadBeam2Records,
  'multipartite-graph-lookahead-beam-3': generateMultipartiteGraphLookaheadBeam3Records,
  'multipartite-graph-lookahead-adaptive-penalty': generateMultipartiteGraphLookaheadAdaptivePenaltyRecords,
  'multipartite-graph-lookahead-density': generateMultipartiteGraphLookaheadDensityRecords,
  'multipartite-graph-lookahead-scarcity-frontier': generateMultipartiteGraphLookaheadScarcityFrontierRecords,
  'multipartite-graph-lookahead-hardest-seed-beam': generateMultipartiteGraphLookaheadHardestSeedBeamRecords,
  'multipartite-graph-lookahead-rd-scarcity-lite-candidate-2':
    generateMultipartiteGraphLookaheadRdScarcityLiteCandidate2Records,
  'multipartite-graph-lookahead-rd-scarcity-lite-candidate-6':
    generateMultipartiteGraphLookaheadRdScarcityLiteCandidate6Records,
  'multipartite-graph-lookahead-rd-scarcity-lite-beam-2': generateMultipartiteGraphLookaheadRdScarcityLiteBeam2Records,
  'multipartite-graph-lookahead-rd-scarcity-lite-beam-3': generateMultipartiteGraphLookaheadRdScarcityLiteBeam3Records,
  'multipartite-graph-lookahead-rd-scarcity-lite-hybrid-tail-20':
    generateMultipartiteGraphLookaheadRdScarcityLiteHybridTail20Records,
  'multipartite-graph-lookahead-rd-scarcity-lite-adaptive-penalty':
    generateMultipartiteGraphLookaheadRdScarcityLiteAdaptivePenaltyRecords,
  'multipartite-graph-lookahead-rd-scarcity-strong-candidate-2':
    generateMultipartiteGraphLookaheadRdScarcityStrongCandidate2Records,
  'multipartite-graph-lookahead-rd-scarcity-strong-candidate-6':
    generateMultipartiteGraphLookaheadRdScarcityStrongCandidate6Records,
  'multipartite-graph-lookahead-rd-scarcity-strong-beam-2':
    generateMultipartiteGraphLookaheadRdScarcityStrongBeam2Records,
  'multipartite-graph-lookahead-rd-scarcity-strong-beam-3':
    generateMultipartiteGraphLookaheadRdScarcityStrongBeam3Records,
  'multipartite-graph-lookahead-rd-scarcity-strong-hybrid-tail-20':
    generateMultipartiteGraphLookaheadRdScarcityStrongHybridTail20Records,
  'multipartite-graph-lookahead-rd-scarcity-strong-adaptive-penalty':
    generateMultipartiteGraphLookaheadRdScarcityStrongAdaptivePenaltyRecords,
  'multipartite-graph-lookahead-rd-frontier-lite-candidate-2':
    generateMultipartiteGraphLookaheadRdFrontierLiteCandidate2Records,
  'multipartite-graph-lookahead-rd-frontier-lite-candidate-6':
    generateMultipartiteGraphLookaheadRdFrontierLiteCandidate6Records,
  'multipartite-graph-lookahead-rd-frontier-lite-beam-2': generateMultipartiteGraphLookaheadRdFrontierLiteBeam2Records,
  'multipartite-graph-lookahead-rd-frontier-lite-beam-3': generateMultipartiteGraphLookaheadRdFrontierLiteBeam3Records,
  'multipartite-graph-lookahead-rd-frontier-lite-hybrid-tail-20':
    generateMultipartiteGraphLookaheadRdFrontierLiteHybridTail20Records,
  'multipartite-graph-lookahead-rd-frontier-lite-adaptive-penalty':
    generateMultipartiteGraphLookaheadRdFrontierLiteAdaptivePenaltyRecords,
  'multipartite-graph-lookahead-rd-frontier-strong-candidate-2':
    generateMultipartiteGraphLookaheadRdFrontierStrongCandidate2Records,
  'multipartite-graph-lookahead-rd-frontier-strong-candidate-6':
    generateMultipartiteGraphLookaheadRdFrontierStrongCandidate6Records,
  'multipartite-graph-lookahead-rd-frontier-strong-beam-2':
    generateMultipartiteGraphLookaheadRdFrontierStrongBeam2Records,
  'multipartite-graph-lookahead-rd-frontier-strong-beam-3':
    generateMultipartiteGraphLookaheadRdFrontierStrongBeam3Records,
  'multipartite-graph-lookahead-rd-frontier-strong-hybrid-tail-20':
    generateMultipartiteGraphLookaheadRdFrontierStrongHybridTail20Records,
  'multipartite-graph-lookahead-rd-frontier-strong-adaptive-penalty':
    generateMultipartiteGraphLookaheadRdFrontierStrongAdaptivePenaltyRecords,
  'multipartite-graph-lookahead-rd-density-lite-candidate-2':
    generateMultipartiteGraphLookaheadRdDensityLiteCandidate2Records,
  'multipartite-graph-lookahead-rd-density-lite-candidate-6':
    generateMultipartiteGraphLookaheadRdDensityLiteCandidate6Records,
  'multipartite-graph-lookahead-rd-density-lite-beam-2': generateMultipartiteGraphLookaheadRdDensityLiteBeam2Records,
  'multipartite-graph-lookahead-rd-density-lite-beam-3': generateMultipartiteGraphLookaheadRdDensityLiteBeam3Records,
  'multipartite-graph-lookahead-rd-density-lite-hybrid-tail-20':
    generateMultipartiteGraphLookaheadRdDensityLiteHybridTail20Records,
  'multipartite-graph-lookahead-rd-density-lite-adaptive-penalty':
    generateMultipartiteGraphLookaheadRdDensityLiteAdaptivePenaltyRecords,
  'multipartite-graph-lookahead-rd-density-strong-candidate-2':
    generateMultipartiteGraphLookaheadRdDensityStrongCandidate2Records,
  'multipartite-graph-lookahead-rd-density-strong-candidate-6':
    generateMultipartiteGraphLookaheadRdDensityStrongCandidate6Records,
  'multipartite-graph-lookahead-rd-density-strong-beam-2':
    generateMultipartiteGraphLookaheadRdDensityStrongBeam2Records,
  'multipartite-graph-lookahead-rd-density-strong-beam-3':
    generateMultipartiteGraphLookaheadRdDensityStrongBeam3Records,
  'multipartite-graph-lookahead-rd-density-strong-hybrid-tail-20':
    generateMultipartiteGraphLookaheadRdDensityStrongHybridTail20Records,
  'multipartite-graph-lookahead-rd-density-strong-adaptive-penalty':
    generateMultipartiteGraphLookaheadRdDensityStrongAdaptivePenaltyRecords,
  'multipartite-graph-lookahead-rd-scarcity-density-candidate-2':
    generateMultipartiteGraphLookaheadRdScarcityDensityCandidate2Records,
  'multipartite-graph-lookahead-rd-scarcity-density-candidate-6':
    generateMultipartiteGraphLookaheadRdScarcityDensityCandidate6Records,
  'multipartite-graph-lookahead-rd-scarcity-density-beam-2':
    generateMultipartiteGraphLookaheadRdScarcityDensityBeam2Records,
  'multipartite-graph-lookahead-rd-scarcity-density-beam-3':
    generateMultipartiteGraphLookaheadRdScarcityDensityBeam3Records,
  'multipartite-graph-lookahead-rd-scarcity-density-hybrid-tail-20':
    generateMultipartiteGraphLookaheadRdScarcityDensityHybridTail20Records,
  'multipartite-graph-lookahead-rd-scarcity-density-adaptive-penalty':
    generateMultipartiteGraphLookaheadRdScarcityDensityAdaptivePenaltyRecords,
  'multipartite-graph-lookahead-rd-frontier-density-candidate-2':
    generateMultipartiteGraphLookaheadRdFrontierDensityCandidate2Records,
  'multipartite-graph-lookahead-rd-frontier-density-candidate-6':
    generateMultipartiteGraphLookaheadRdFrontierDensityCandidate6Records,
  'multipartite-graph-lookahead-rd-frontier-density-beam-2':
    generateMultipartiteGraphLookaheadRdFrontierDensityBeam2Records,
  'multipartite-graph-lookahead-rd-frontier-density-beam-3':
    generateMultipartiteGraphLookaheadRdFrontierDensityBeam3Records,
  'multipartite-graph-lookahead-rd-frontier-density-hybrid-tail-20':
    generateMultipartiteGraphLookaheadRdFrontierDensityHybridTail20Records,
  'multipartite-graph-lookahead-rd-frontier-density-adaptive-penalty':
    generateMultipartiteGraphLookaheadRdFrontierDensityAdaptivePenaltyRecords,
});
