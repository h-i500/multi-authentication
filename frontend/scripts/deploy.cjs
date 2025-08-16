// dist を quarkus-authz の META-INF/resources/app へコピー
import { cpSync, mkdirSync, rmSync } from 'node:fs'
import { fileURLToPath } from 'node:url'
import { dirname, resolve } from 'node:path'

const __dirname = dirname(fileURLToPath(import.meta.url))
const repoRoot = resolve(__dirname, '..', '..')
const target = resolve(repoRoot, 'quarkus-authz', 'src', 'main', 'resources', 'META-INF', 'resources', 'app')
const dist = resolve(__dirname, '..', 'dist')

rmSync(target, { recursive: true, force: true })
mkdirSync(target, { recursive: true })
cpSync(dist, target, { recursive: true })
console.log(`✔ Copied frontend/dist → ${target}`)