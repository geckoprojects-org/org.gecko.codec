# CLAUDE.md

## Quick Reference

### Commands
```bash
./gradlew build                              # Build all
./gradlew :project-name:test                 # Unit tests (JUnit 5)
./gradlew :project-name:testOSGi             # OSGi tests (old codec only)
```

### Codec V2 Testing
- `./gradlew :org.eclipse.fennec.codec.v2:test` - codec.v2 tests
- `./gradlew :org.eclipse.fennec.codec.metadata:test` - metadata tests
- **Do NOT use `testOSGi` for v2 projects**

## Key Rules

1. **Spec First** - Always check `docs/codec-v2-spec/` before implementing
2. **TDD** - Tests before implementation
3. **Ask Early** - If unsure after 2-3 search cycles, ask
4. **Imports** - Never use fully qualified class names
5. **@claude comments** - Treat as instructions

## Documentation (read when needed)

| Topic | Document |
|-------|----------|
| Current state & next steps | `docs/codec-v2-development-guide.md` |
| Spec (source of truth) | `docs/codec-v2-spec/` |
| Plans & GAPs | `docs/codec-v2-plans.md` |
| Architecture details | See below |

### Architecture Docs (read on demand)
- `org.eclipse.fennec.model.metadata/model-metadata-architecture.md`
- `org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md`

## Session Handoff
Update `docs/codec-v2-development-guide.md` at session end.
