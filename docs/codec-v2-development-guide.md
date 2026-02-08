# Codec V2 Development Guide

This document provides context for continuing codec.v2 development across sessions. It captures the goals, current state, and links to detailed architecture documentation.

**Last Updated:** 2026-02-08 (GAP-002 verified, GAP-003 strictness implemented)

**Session Summary:**
- **Verified GAP-002 (Fallback Strategy)** — Already fully implemented in `TypeDiscriminatorService`
- **Implemented GAP-003 (Feature Strictness):**
  - Created `ClassConfig` API class with `strictOnUnknown`, `strictOnMissing` fields
  - Added `ConfigurationResolver.resolveClassConfig(EClass)` with full merge cascade
  - Added convenience builder methods `strictOnUnknown()`, `strictOnMissing()`
  - Wired into `CodecEObjectDeserializer`:
    - `deserializeProperty()` — checks `strictOnUnknown` for non-deferred fields
    - `processDeferredProperties()` — checks `strictOnUnknown` for deferred fields (key fix!)
    - `checkStrictOnMissing()` — checks required features after deserialization
- **Created StrictnessIntegrationTest.java** — 11 tests covering strictOnUnknown, strictOnMissing, class-level overrides
- **Created test-strictness.ecore** — Model with required features (lowerBound=1) for testing
- Final codec.v2 test count: ~1019 tests, 0 failures

**Next Session:** Continue with GAP-014 (inherit enum - AnnotationInheritance), then GAP-004 (Diagnostic Options)

---

## 0. Active Task Hierarchy (SESSION CONTINUITY)

This section tracks the current task hierarchy to prevent context loss during nested investigations.

### 0.1 How to Use This Section

**When starting work:** Check this section first to understand where we are.

**When a new issue arises during work:**
1. **ASK:** "Is this a child task (fix now, return to parent) or independent task (add to TODO, continue)?"
2. **If child task:** Add it to the hierarchy below with proper indentation
3. **If independent task:** Add to §5.4 "Remaining Work" or §10.4 "Current TODO List"

**When completing a task:** Mark it ✅ and return to the parent task.

**Format:**
```
MAIN TASK: [description] - [status: ACTIVE/PAUSED/✅]
├── CHILD: [description] - [status]
│   ├── CHILD: [sub-issue] - [status]
│   └── CHILD: [sub-issue] - [status]
└── RETURN TO: [next step after children complete]
```

### 0.2 Current Task Hierarchy

```
COMPLETED: Integration Tests + PLAIN Reference Format - ✅ (2026-02-06)
│
│  INTEGRATION TESTS:
│  │  - NEW: FeatureVisibilityIntegrationTest.java (20 tests)
│  │    - ignoreRead/ignoreWrite/ignore on attributes and containment refs
│  │    - Comparison tests, round-trip tests, edge cases
│  │  - ENHANCED: ExpandReferenceTest.java (+8 edge case tests)
│  │    - expand + null, expand + ignoreWrite, expandIgnoreBidirectional
│  │    - empty multi-valued with serializeEmpty, mixed expand/proxy
│  │
│  PLAIN REFERENCE FORMAT IMPLEMENTATION:
│  │  - Deserialization: ReferenceDeserializationEntry.deserializeSingleValued()
│  │    - Added VALUE_STRING handling for single-valued non-containment
│  │    - Multi-valued PLAIN was already supported
│  │  - Serialization: ReferenceSerializationEntry.writeReferenceObject()
│  │    - Added refFormat field from ReferenceConfig
│  │    - PLAIN outputs bare URI string, STRUCTURED outputs object with _type/$ref
│  │  - NEW: PlainReferenceFormatTest.java (16 tests)
│  │    - PLAIN deser (single/multi), type hints, mixed formats, PLAIN ser, round-trip
│  │  - SPEC UPDATE: 10-reference.md §1.1 PLAIN Strategy
│  │    - Type resolution table, polymorphism warning, implementation status
│  │
│  CONFIGURATION: refFormat via optionsProperties
│  │  ConfigurationResolver.builder().optionsProperties(Map.of("refFormat", "PLAIN")).build()
│  │
│  FINAL STATE: codec.v2 = ~1008 tests, 0 failures
│
│  NEXT STEPS:
│  │  - Continue with Plan B GAP work (GAP-002/003/014 are migration tasks)
│  │  - GAP-004 (Diagnostic Options) is a new feature, lower priority

PREVIOUS: Code Cleanup, Deprecated Removal & Helper Extraction - ✅ (2026-02-05)
> **Older completed tasks archived in [`codec-v2-session-history.md`](codec-v2-session-history.md)**

```

## 1. Project Goal

**Fennec Codec V2** is a rewrite of the EMF JSON codec with a metadata-driven architecture:

- **Metadata Layer** parses EAnnotations at EPackage registration time
- **Codec Layer** consumes metadata during serialization/deserialization
- **Configuration** supports 5 sources (Options, Resource, Factory, Module, Annotation) merged hierarchically
- **Spec-driven development** — implementation follows `docs/codec-v2-spec/`

## 2. Architecture Overview

### 2.1 Two-Layer Architecture

```
┌─────────────────────────────────────────────────────────────┐
│ org.eclipse.fennec.codec.v2 (Codec Runtime)                │
│                                                             │
│ ┌─────────────────────┐   ┌───────────────────────────┐   │
│ │ CodecEObjectSerializer├──→ SerializationEntry (Type, │   │
│ │ CodecEObjectDeserializer  │ ID, Feature, Reference)   │   │
│ └─────────────────────┘   └───────────────────────────┘   │
│            │                                               │
│            ▼                                               │
│ ┌─────────────────────────────────────────────────────┐   │
│ │ EffectiveCodecConfig (resolves per-feature config) │   │
│ └─────────────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────────────────────────┘
                       │ uses metadata
                       ▼
┌─────────────────────────────────────────────────────────────┐
│ org.eclipse.fennec.codec.metadata (Metadata Service)       │
│                                                             │
│ ┌────────────────────┐   ┌──────────────────────────────┐  │
│ │ CodecAspectProvider├──→│ Parses EAnnotations          │  │
│ │ MetadataService     │   │ Creates aspect objects       │  │
│ └────────────────────┘   │ (TypeDiscriminatorService,   │  │
│                           │  ClassConfig, FeatureConfig) │  │
│                           └──────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 Key Components

| Component | Location | Purpose |
|-----------|----------|---------|
| **MetadataService** | `codec.metadata` | EPackage registration, aspect creation |
| **CodecAspectProvider** | `codec.metadata.provider` | Parses EAnnotations → Config objects |
| **TypeDiscriminatorService** | `codec.metadata.type` | Manages discriminator→EClass mappings |
| **EffectiveCodecConfig** | `codec.api.config.effective` | Per-feature config resolution |
| **CodecEObjectSerializer** | `codec.v2.ser` | Orchestrates serialization entries |
| **CodecEObjectDeserializer** | `codec.v2.deser` | Orchestrates deserialization entries |
| **SerializationEntry** | `codec.v2.ser` | Type/ID/Feature/Reference serializers |
| **DeserializationEntry** | `codec.v2.deser` | Type/ID/Feature/Reference deserializers |

### 2.3 Configuration Hierarchy (5 Sources)

From highest to lowest priority:

1. **SaveOptions/LoadOptions** (per-operation)
2. **Resource options** (per-resource)
3. **ResourceFactory options** (per-factory)
4. **CodecModule** (global config)
5. **EAnnotation** (model metadata)

See `docs/codec-v2-spec/02-config-resolution.md` for details.

## 3. Current State (2026-02-05)

### 3.1 What Works

✅ **Configuration Layer (metadata.ecore + codec.api)**
- All 6 config types: Type, SuperType, Discriminator, ID, Feature, Reference
- Spec tests complete (54+48+38+78+64+56 = 338 tests)
- Resolver spec tests complete (24+21+17+17+30+23+20 = 152 tests)
- Layer 1 annotation validation (parse-time errors for invalid EAnnotations)
- Config merging (5-source hierarchy with toBuilder() pattern)

✅ **Metadata Service**
- EPackage registration
- Aspect parsing (CodecAspectProvider)
- TypeDiscriminatorService (discriminator value → EClass mapping)
- Diagnostic collection during parsing

✅ **Codec Runtime (fully cleaned up)**
- `org.eclipse.fennec.codec.*` packages (all deprecated `codec.v2.*` code deleted)
- Serialization entries (Type, ID, Feature, Reference)
- Deserialization entries (Type, ID, Feature, Reference)
- CodecEObjectSerializer/Deserializer orchestrators
- CodecResource + CodecModule
- Jackson integration (JsonParser/JsonGenerator + custom buffer)
- EffectiveCodecConfig (per-feature resolution)
- ConfigurationResolver (replaces old CodecConfiguration)

✅ **Utility Helpers (org.eclipse.fennec.codec.util)**
- `AnnotationHelper` — ExtendedMetaData annotation lookups
- `CodecResourceHelper` — Type resolution, compatibility checks
- `MetadataServiceFactory` — MetadataWhiteboard creation
- `TypeResolutionHelper` — EClass resolution by name, class name, numeric ID, URI (22 tests)
- `EMapHelper` — EMap reference detection, key/value feature access (18 tests)

✅ **Integration Tests**
- 23 resource test files (193 tests) — migrated to new codec.* packages
- 9 ser/deser/type integration tests — migrated to new codec.* packages
- GeoJson, JsonSchema, OpenAPI codecs — migrated to non-deprecated API
- ForceReadWriteTest.java — verifies two-gate model + forceRead/forceWrite

✅ **Discriminator Mapping (Type Mapping Registry)**
- All 19 tests in CodecResourceInlineMappingTest passing
- Deserialization, serialization, ERROR/FALLBACK strategies, root-level, supertype inheritance

✅ **Deprecated Code Removed**
- All `codec.v2.*` source/test files deleted (55 src + 9 test)
- All `codec.api.value.*` and `codec.api.diagnostic.*` files deleted (11 src + 14 test)
- Zero skipped tests remaining

### 3.2 Test Status

**Current Counts (2026-02-05, after cleanup + helper extraction):**
- **codec.v2:** 992 tests, 0 failures, 0 skipped
- **codec.api:** ~490 tests
- **codec.metadata:** ~220 tests
- **model.metadata:** ~200 tests
- **codec.geojson, codec.jsonschema.v2, codec.openapi:** ~617 tests combined
- **Total across all 7 projects:** ~2519 tests, 0 failures, 0 skipped

**Test Organization:**
- `org.eclipse.fennec.codec.api/test` — config API tests (spec + resolver tests)
- `org.eclipse.fennec.codec.metadata/test` — aspect provider tests + TypeDiscriminatorServiceTest
- `org.eclipse.fennec.codec.v2/test/org/eclipse/fennec/codec/*` — runtime tests (all active)
- `org.eclipse.fennec.codec.v2/test/org/eclipse/fennec/codec/util/*` — helper unit tests

### 3.3 What's Next

**IMMEDIATE (Next Session):**
1. Plan B GAP work — GAP-002 (Fallback Strategy), GAP-003 (Strictness), GAP-014 (inherit enum) are migration tasks
2. OR continue integration test coverage (reference expansion, feature visibility)
3. GAP-004 (Diagnostic Options) is a new feature — lower priority

**SHORT TERM:**
1. Complete Plan B Phase 1 migration GAPs
2. Create integration tests for complex scenarios
3. Plan B Phase 2 (GAP-006 through GAP-010)

**LONG TERM:**
1. Performance testing
2. Documentation completion (user guide, migration guide)
3. Plan C (optional features): custom serializers, advanced streaming

## 4. Key Documents

### 4.1 Specification (Source of Truth)

**Location:** `docs/codec-v2-spec/`

| File | Purpose |
|------|---------|
| `00-overview.md` | Table of contents |
| `01-introduction.md` | Goals, architecture, terminology |
| `02-config-resolution.md` | 5-source hierarchy, scope chain |
| `06-type.md` | Type serialization/deserialization |
| `07-supertype.md` | SuperType configuration |
| `08-discriminator-mapping.md` | Discriminator resolution |
| `09-id.md` | ID serialization/deserialization |
| `10-reference.md` | Reference serialization/deserialization |
| `11-feature.md` | Feature (attribute) serialization/deserialization |
| `12-feature-serialization.md` | Feature visibility model, gates |
| `15-error-handling.md` | Diagnostics, validation rules |
| `16-annotation-reference.md` | Complete property reference |
| `17-format-abstraction.md` | PLAIN vs STRUCTURED formats |

### 4.2 Architecture Documents

**Metadata Layer:**
- `org.eclipse.fennec.model.metadata/model-metadata-architecture.md`
- `org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md`

**Development:**
- `docs/codec-v2-development-guide.md` (this file)
- `docs/codec-v2-plans.md` (roadmap, GAP analysis)
- `docs/codec-v2-session-history.md` (archived completed task entries)
- `docs/codec-v2-migration-notes.md` (V1 → V2 migration guide)
- `docs/codec-v2-reference.md` (EMF concepts, terminology, API reference)

## 5. Development Workflow

### 5.1 Making Changes

1. **Check spec first** — `docs/codec-v2-spec/`
2. **Write test** — TDD approach (spec test or integration test)
3. **Implement** — follow spec exactly
4. **Verify** — run relevant test suite
5. **Update docs** — if behavior clarified or new feature added

### 5.2 Testing Strategy

**Spec Tests** (codec.api):
- Unit tests for configuration objects
- Resolver tests for configuration resolution
- Validation tests for diagnostic rules

**Integration Tests** (codec.v2):
- Resource tests (CodecResource end-to-end)
- Ser/Deser tests (specific features)
- Roundtrip tests (symmetry verification)

**Custom Codec Tests** (codec.geojson, codec.jsonschema.v2, codec.openapi):
- Domain-specific serialization tests
- Custom value reader/writer tests

### 5.3 Common Patterns

**Configuration Resolution:**
```java
EffectiveCodecConfig effectiveConfig = ...;
TypeConfig resolved = effectiveConfig.resolveTypeConfig(eClass);
if (resolved.getTypeStrategy() == TypeStrategy.NAME) {
    // Use name-based type serialization
}
```

**Aspect Creation (Metadata Layer):**
```java
// In CodecAspectProvider
ClassConfig config = MetadataFactory.eINSTANCE.createClassConfig();
config.setTypeStrategy(TypeStrategy.NAME);
// Validation happens here (Layer 1)
validateTypeConfig(config, eClass, diagnostics);
```

**Entry Pattern (Codec Layer):**
```java
// Serialization
TypeSerializationEntry entry = new TypeSerializationEntry(effectiveConfig, ...);
entry.serialize(eObject, generator, context);

// Deserialization
TypeDeserializationEntry entry = new TypeDeserializationEntry(effectiveConfig, ...);
EClass resolved = entry.resolveEClass(parser, context, currentReference);
```

**Discriminator Mapping (Type Mapping Registry):**
```java
// Deserialization - targeted resolve with mapId
String mapId = getDiscriminatorMapId(hintEClass);
EClass resolved = typeResolver.resolve(mapId, discriminatorValue, namespace, hintEClass, fallbackStrategy);

// Serialization - reverse lookup
String mapId = typeDiscriminatorService.getMapIdForEClass(actualEClass);
String discriminatorValue = typeDiscriminatorService.getDiscriminatorValue(mapId, actualEClass);
```

### 5.4 Remaining Work

**Plan B Phase 1: Migration GAPs**
- [✅] GAP-001: Feature Visibility — DONE
- [✅] GAP-005: ID Value Key — DONE
- [✅] GAP-002: Fallback Strategy wiring — DONE (TypeDiscriminatorService handles inline + typeMapping fallback)
- [✅] GAP-003: Feature Strictness — DONE (ClassConfig, strictOnUnknown/Missing in deserialization)
- [ ] GAP-014: inherit enum (AnnotationInheritance: DIRECT/ALL/NONE, requires codec.ecore change)
- [ ] GAP-004: Diagnostic Options (NEW FEATURE, not migration)

**Integration Tests**
- [✅] Discriminator mapping tests (CodecResourceInlineMappingTest.java)
- [✅] Feature visibility integration tests (FeatureVisibilityIntegrationTest.java — ignoreRead/Write/ignore)
- [✅] Reference expansion integration tests (ExpandReferenceTest.java — edge cases added)
- [✅] PLAIN reference format tests (PlainReferenceFormatTest.java — ser/deser/round-trip)
- [✅] Strictness integration tests (StrictnessIntegrationTest.java — strictOnUnknown/Missing)
- [ ] Type resolution integration tests (beyond discriminator mapping)
- [ ] ID serialization integration tests

**Code Quality** (completed)
- [✅] Deprecated code removal (codec.v2.*, codec.api.value.*, codec.api.diagnostic.*)
- [✅] @claude comment cleanup
- [✅] Helper extraction: TypeResolutionHelper, EMapHelper

**Documentation:**
- [ ] User guide (how to use codec v2)
- [ ] Migration guide (v1 → v2)
- [ ] Performance guide

## 6. Debugging Tips

### 6.1 Common Issues

**Issue: Config not taking effect**
- Check scope chain (Options > Resource > Factory > Module > Annotation)
- Check if property is runtime-only (can't be in EAnnotation)
- Check if validation rule prevents it (check diagnostics)

**Issue: Type resolution fails**
- Check TypeStrategy (NONE disables type info)
- Check discriminator mapping (registered in MetadataService?)
- Check fallback chain (discriminator → hints → declared type)

**Issue: Feature not serialized/deserialized**
- Check visibility gates (ignore, ignoreWrite, ignoreRead)
- Check force flags (forceWrite, forceRead for volatile/transient)
- Check value gates (serializeNull, serializeEmpty, serializeDefault)

**Issue: Discriminator mapping not working**
- Check mapId extraction: is discriminatorMapId set on DiscriminatorConfig?
- Check fallback strategy: ERROR vs FALLBACK behavior
- Check supertype walking: does getMapIdForEClass() find the config?
- Check exception propagation: is IllegalStateException from ERROR strategy being re-thrown?

### 6.2 Diagnostic Inspection

All metadata parsing errors are collected in `DiagnosticCollector`:

```java
DiagnosticCollector diagnostics = ...;
for (Diagnostic diag : diagnostics.getDiagnostics()) {
    System.err.println(diag.getSeverity() + ": " + diag.getMessage());
}
```

## 7. Migration Notes (V1 → V2)

> See [`codec-v2-migration-notes.md`](codec-v2-migration-notes.md) for full migration details (package changes, API changes, EAnnotation changes).

## 8. Known Issues & Limitations

### 8.1 Current Limitations

1. **Cross-document containment references** — not yet supported (deser only)
2. **Custom Jackson modules** — limited integration
3. **Streaming mode** — not optimized for large documents

### 8.2 Spec Gaps (To Be Addressed)

1. Entry-build pattern not documented in spec §1.2
2. Deserialization gate shouldDeserialize() usage not explicit
3. isChangeable() pre-check not documented

### 8.3 Fixed Bugs

**2026-02-05:**
✅ **Discriminator mapping not using targeted resolve()** (TypeDeserializationEntry, CodecEObjectDeserializer)
- Now extracts mapId from DiscriminatorConfig and calls targeted `resolve(mapId, value, ...)`
- Fallback strategy (ERROR vs FALLBACK) now respected correctly

✅ **Exception propagation for ERROR strategy** (CodecEObjectDeserializer)
- IllegalStateException from unknown discriminator now re-thrown in replayDeferredValue() and deserializeContainedObject()

✅ **FeaturePathTypeResolver handling standard _type paths** (FeaturePathTypeResolver)
- Added `!isTypeKey(discriminatorPath)` guard to prevent interference with standard type key

✅ **Discriminator mapping serialization** (TypeSerializationEntry)
- Added resolveTypeMappingDiscriminator() that calls TypeDiscriminatorService.getDiscriminatorValue(mapId, eClass)
- Fixed hasDiscriminatorPath() to only suppress _type for nested paths

✅ **Supertype inheritance for discriminator maps** (TypeDiscriminatorService)
- getMapIdForEClass() now walks supertypes via getEAllSuperTypes()

**2026-02-04:**
✅ **forceRead not working for volatile features** (ConfigurationResolver:420)
- Only checked `isForceWrite()`, now checks `isForceRead()` too

✅ **forceWrite two-gate model** (AttributeSerializationEntry, ReferenceSerializationEntry)
- forceWrite now ONLY affects visibility gate, NOT value gate
- serializeNull/Empty/Default still apply with forceWrite=true

## 9. Gradle Commands

```bash
# Build everything
./gradlew build

# Test specific project
./gradlew :org.eclipse.fennec.codec.v2:test
./gradlew :org.eclipse.fennec.codec.metadata:test
./gradlew :org.eclipse.fennec.codec.api:test

# Test specific test class
./gradlew :org.eclipse.fennec.codec.v2:test --tests CodecResourceInlineMappingTest

# Clean build
./gradlew clean build

# Skip tests
./gradlew build -x test
```

**Note:** Do NOT use `testOSGi` for v2 projects (only for old codec).

## 10. Session Handoff Protocol

### 10.1 At Session Start

1. Read this document (section 0 "Active Task Hierarchy")
2. Check git status: `git status`, `git log --oneline -10`
3. Review "Next Session" in header
4. Ask user for clarification if needed

### 10.2 During Session

1. Update section 0.2 "Current Task Hierarchy" as work progresses
2. Mark tasks ✅ when complete
3. Add child tasks for nested investigations
4. Document decisions and blockers

### 10.3 At Session End

1. Update header: "Last Updated", "Session Summary", "Next Session"
2. Add new "COMPLETED" entry in section 0.2
3. Mark all tasks ✅
4. Commit changes: descriptive commit message

### 10.4 Current TODO List

**Next session:**
1. Plan B GAP work: GAP-002 (Fallback Strategy), GAP-003 (Strictness), GAP-014 (inherit enum) — all migration tasks
2. OR continue integration test coverage (reference expansion, feature visibility)
3. GAP-004 (Diagnostic Options) is a new feature — lower priority

## 11. Reference Information

> See [`codec-v2-reference.md`](codec-v2-reference.md) for full reference details (EMF concepts, terminology, Jackson integration, metadata service usage, config builder patterns, deprecated API audit + migration order).

---

## 12. Session Continuity Tips

If context is lost:

1. Read this document first: `docs/codec-v2-development-guide.md`
2. Check spec for details: `docs/codec-v2-serialization-spec.md`
3. Review current TODO state (if available)
4. Examine recent git commits for context
5. Ask user for clarification if needed

The key insight: **MetadataService parses EAnnotations at EPackage registration time and creates pre-computed aspect objects that the codec uses at serialization time.**

**For Discriminator Mapping:** All packages are registered with MetadataService before serialization/deserialization. When registering an EPackage, the MetadataService runs through codec aspects and updates the TypeDiscriminatorService. By the time deserialization happens, there's already a mapping from discriminator values (like "temp-sensor") to EClasses. The runtime uses targeted `resolve(mapId, value, ...)` for correct fallback strategy behavior (ERROR throws exception, FALLBACK returns null). Serialization performs reverse lookup via `getDiscriminatorValue(mapId, eClass)`. The system supports supertype inheritance (walks supertypes to find discriminator config) and root-level discriminator mapping (not just for contained references).
