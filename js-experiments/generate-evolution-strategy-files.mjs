import { mkdirSync, writeFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

import { EVOLUTION_EXPERIMENT_DEFINITIONS } from './experimentalNWiseEvolutionAlgorithms.js';

const EXPERIMENT_DIR = dirname(fileURLToPath(import.meta.url));
const STRATEGY_DIR = join(EXPERIMENT_DIR, 'strategies', 'evolution');
const REGISTRY_PATH = join(STRATEGY_DIR, 'index.js');

function toPascalCase(value) {
  return value
    .split(/[^a-zA-Z0-9]+/)
    .filter(Boolean)
    .map((part) => `${part[0].toUpperCase()}${part.slice(1)}`)
    .join('');
}

function toStrategyFileName(id) {
  return `${id}.js`;
}

function buildStrategyFile(definition) {
  const functionName = `generate${toPascalCase(definition.id)}Records`;

  return `import { getEvolutionExperimentDefinition } from '../../experimentalNWiseEvolutionAlgorithms.js';
import { createEvolutionLookaheadStrategy } from '../evolutionLookaheadShared.js';

const DEFINITION = getEvolutionExperimentDefinition('${definition.id}');

export const ${functionName} = createEvolutionLookaheadStrategy(DEFINITION.config);
`;
}

function buildRegistryFile(definitions) {
  const imports = definitions
    .map((definition) => {
      const functionName = `generate${toPascalCase(definition.id)}Records`;
      return `import { ${functionName} } from './${toStrategyFileName(definition.id)}';`;
    })
    .join('\n');

  const entries = definitions
    .map((definition) => {
      const functionName = `generate${toPascalCase(definition.id)}Records`;
      return `  '${definition.id}': ${functionName},`;
    })
    .join('\n');

  return `${imports}

export const EVOLUTION_N_WISE_STRATEGIES = Object.freeze({
${entries}
});
`;
}

mkdirSync(STRATEGY_DIR, { recursive: true });

for (const definition of EVOLUTION_EXPERIMENT_DEFINITIONS) {
  writeFileSync(join(STRATEGY_DIR, toStrategyFileName(definition.id)), buildStrategyFile(definition), 'utf8');
}

writeFileSync(REGISTRY_PATH, buildRegistryFile(EVOLUTION_EXPERIMENT_DEFINITIONS), 'utf8');

process.stdout.write(`Generated ${EVOLUTION_EXPERIMENT_DEFINITIONS.length} evolution strategy files.\\n`);
