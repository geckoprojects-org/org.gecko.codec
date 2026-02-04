# Codec V2 Remaining Plans

This document consolidates all active plans for completing the codec.v2 migration. It merges the
Spec Compliance Refactoring Plan and the Deprecated API Migration Plan into a single phased roadmap.

**Created:** 2026-02-02
**Updated:** 2026-02-04

**Related documents:**
- [`docs/codec-v2-development-guide.md`](codec-v2-development-guide.md) — Session continuity, current state
- [`docs/codec-v2-spec/`](codec-v2-spec/) — Specification (source of truth)

---

## Table of Contents

1. [Current State](#1-current-state)
2. [Architecture Overview](#2-architecture-overview)
3. [Plan A: Deprecated API Migration](#3-plan-a-deprecated-api-migration--complete) ✅
4. [Plan B: Spec Compliance Gaps](#4-plan-b-spec-compliance-gaps)
5. [Plan C: Documentation Examples](#5-plan-c-documentation-examples-deferred)
6. [Plan D: Discriminator Refactoring](#6-plan-d-discriminator-refactoring-deferred)
7. [Execution Roadmap](#7-execution-roadmap)
8. [Critical Files Reference](#8-critical-files-reference)

---

## 1. Current State

### What's Done

The 8-step package migration from `codec.v2.*` to `codec.*` is **complete**:
- New spec-compliant classes live in `org.eclipse.fennec.codec.*` packages
- Old `codec.v2.*` classes are deprecated + tests disabled
- `ConfigurationResolver` replaced `CodecConfiguration` + `ConfigurationMerger`
- All 6 config types have spec + resolver tests (Type, Id, SuperType, Feature, Reference, Discriminator)

**Plan A (Deprecated API Migration) is COMPLETE** (2026-02-04):
- Migrated `codec.api.value.*` to `codec.value.*` API
- Migrated dependent projects: `codec.geojson`, `codec.jsonschema.v2`, `codec.openapi`
- Fixed `forceWrite`/`forceRead` bugs (two-gate model, volatile features)

### What Remains

| Category | Description | Status |
|----------|-------------|--------|
| ~~**Plan A**~~ | ~~Migrate deprecated API~~ | ✅ COMPLETE |
| **Plan B** | Fill spec compliance gaps (14 identified GAPs) | In Progress |
| **Plan C** | Documentation examples (deferred from spec review) | Not Started |
| **Plan D** | Discriminator refactoring (deferred from spec review) | Not Started |

---

## 2. Architecture Overview

### Package Structure

```
codec.api project (org.eclipse.fennec.codec.api):
├── codec.value.*          ← NEW API interfaces (CodecValueReader/Writer, CodecReaderContext, etc.)
├── codec.api.value.*      ← OLD deprecated interfaces
├── codec.config.*         ← Config record types (TypeConfig, IdConfig, etc.)
└── codec.diagnostic.*     ← DiagnosticCollector

codec.v2 project (org.eclipse.fennec.codec.v2):
├── codec.*                ← NEW implementation (ser, deser, config, module, resource)
└── codec.v2.*             ← OLD deprecated implementation (to be deleted)
```

### The EffectiveCodecConfig Layers

Three types coexist — this is intentional, not a clash:

| Type | Location | Purpose |
|------|----------|---------|
| **Interface** | `codec.api → codec.value.EffectiveCodecConfig` | Narrow view for custom readers/writers (7 parameter-free getters) |
| **NEW Class** | `codec.v2 → codec.config.effective.EffectiveCodecConfig` | Full operation-scoped resolver (~50 methods, builder pattern) |
| **OLD Class** | `codec.v2 → codec.v2.config.effective.EffectiveCodecConfig` | Deprecated — will be deleted with old package |

The interface and class serve **different architectural layers**:
- The **interface** is a snapshot capturing already-resolved configs for the current context
- The **class** is the resolver that computes configs per-EClass/per-feature

A `SimpleEffectiveCodecConfig` bridge (Plan A, Step 1) will connect them.

### The CodecValueRegistry Copies

| Type | Location | Status |
|------|----------|--------|
| **NEW** | `codec.api → codec.value.CodecValueRegistry` | Uses new `CodecValueReader/Writer` with `getName()` auto-registration |
| **OLD** | `codec.api → codec.api.value.CodecValueRegistry` | Deprecated — uses old reader/writer interfaces |

### 3D Configuration Resolution Model

The effective configuration is determined by a 3D matrix:

**Dimension 1: Sources** (priority, highest first):
`Load/Save Options → Resource Options → ResourceFactory → Module → Annotation → Built-in Default`

**Dimension 2: Scope** (specificity, most specific first):
`Feature (EAttribute/EReference) → EClass → Global → Default`

**Dimension 3: Direction:**
`Serialization (write) | Deserialization (read)`

Resolution: First non-null value wins, scanning scope left-to-right, source top-to-bottom.

---

## 3. Plan A: Deprecated API Migration ✅ COMPLETE

**Completed:** 2026-02-04

Replaced all usages of deprecated `codec.api.value.*` types with `codec.value.*` types.

### What Was Done

1. **Core codec.v2 migration** - Entry classes, module, resource now use new API
2. **Dependent projects migrated:**
   - `codec.geojson` - Uses `ConfigurationResolver` + `forceWrite`/`forceRead`
   - `codec.jsonschema.v2` - `EPackageValueReader`/`Writer` use new context API
   - `codec.openapi` - `OperationValueReader` uses new context API
3. **Bug fixes:**
   - `forceWrite` now correctly implements two-gate model (visibility vs value gate)
   - `forceRead` now works for volatile features in `ConfigurationResolver`

### API Changes Summary

| Aspect | Old (`codec.api.value`) | New (`codec.value`) |
|--------|------------------------|---------------------|
| Reader method | `read(JsonParser, F, DeserializationContext)` | `read(CodecReaderContext, F)` |
| Writer method | `write(T, F, JsonGenerator, SerializationContext)` | `write(T, F, CodecWriterContext)` |
| Registration | `registerReader("name", reader)` | `register(reader)` (uses `getName()`) |
| Config access | Not available | Via `ctx.getConfig()` |

---

## 4. Plan B: Spec Compliance Gaps

### Gap Summary

| ID | Gap | Priority | Phase |
|----|-----|----------|-------|
| GAP-001 | Feature Visibility (directional ignore/force) | HIGH | B1 |
| GAP-002 | Fallback Strategy enum support | HIGH | B1 |
| GAP-003 | Feature Strictness (strictOnUnknown/Missing) | HIGH | B1 |
| GAP-004 | Diagnostic Options integration | HIGH | B1 |
| GAP-005 | ID Value Key support | HIGH | B1 |
| GAP-014 | `inherit` annotation type mismatch (boolean vs enum) | HIGH | B1 |
| GAP-006 | Metadata Merge behavior | MEDIUM | B2 |
| GAP-007 | Expand deserialization | MEDIUM | B2 |
| GAP-008 | Value Reader/Writer handlers | MEDIUM | B2 |
| GAP-009 | Scope wiring for runtime options | MEDIUM | B2 |
| GAP-010 | Hierarchy resolution tests | MEDIUM | B2 |
| GAP-011 | Additional misconfig test coverage | LOW | B3 |
| GAP-012 | DeserializationMode usage | LOW | B3 |
| GAP-013 | Enum-level annotation support | LOW | B3 |

### Phase B1: Core Metadata Gaps (HIGH Priority)

#### GAP-001: Feature Visibility (Directional Ignore/Force)

**Spec:** 07-feature-serialization.md §3.3

**Current:** `FeatureCodecAspect.ignore` is a simple boolean.
**Required:** Directional visibility with values: `NONE`, `IGNORE`, `IGNORE_READ`, `IGNORE_WRITE`, `FORCE_READ`, `FORCE_WRITE`.

**Changes:**
1. Add `FeatureVisibility` enum to `codec.ecore`
2. Replace `ignore: Boolean` with `visibility: FeatureVisibility`
3. Update `CodecAspectProvider` to parse `ignore=true/read/write` and `force=read/write`
4. Add valid config + misconfig tests

#### GAP-002: Fallback Strategy Enum Support

**Spec:** 07-feature-serialization.md §5.4

**Current:** `FallbackStrategy` enum exists but not wired to `ReferenceCodecAspect`.
**Required:** Parse `fallbackStrategy` from reference annotations, default `FALLBACK`.

#### GAP-003: Feature Strictness

**Spec:** 07-feature-serialization.md §6

**Current:** No strictness attributes.
**Required:** `strictOnUnknown: Boolean` and `strictOnMissing: Boolean` on `ClassCodecAspect`.

#### GAP-004: Diagnostic Options Integration

**Spec:** 15-error-handling.md §6

**Required:** Allow callers to configure diagnostic severity levels via `DiagnosticOptions`.

#### GAP-005: ID Value Key Support

**Spec:** 06-id-serialization.md §4

**Current:** `idKey` exists, no `idValueKey`.
**Required:** Add `idValueKey: String` to `IdCodecAspect`.

#### GAP-014: `inherit` Annotation Type Mismatch

**Spec:** 12-polymorphism.md §2, 02-config-resolution.md §11.7

**Current:** `ClassCodecAspect.inheritFromParent` is a **boolean**.
**Required:** `AnnotationInheritance` enum with values: `DIRECT` (default), `ALL`, `NONE`.

### Phase B2: Advanced Features (MEDIUM Priority)

| GAP | Description |
|-----|-------------|
| GAP-006 | Review metadata merge semantics against spec |
| GAP-007 | Implement expand deserialization + round-trip tests |
| GAP-008 | Wire value reader/writer handler names to actual implementations |
| GAP-009 | Connect scope resolution for load/save options at runtime |
| GAP-010 | Comprehensive hierarchy resolution test coverage |

### Phase B3: Polish (LOW Priority)

| GAP | Description |
|-----|-------------|
| GAP-011 | Additional misconfig test cases |
| GAP-012 | Wire `DeserializationMode` enum (LENIENT, STRICT, AUTO_DETECT) |
| GAP-013 | Codec annotations on EEnum types |

---

## 5. Plan C: Documentation Examples (Deferred)

These documentation tasks were identified during spec review but deferred as lower priority.

| ID | Task | Spec Section | Priority |
|----|------|--------------|----------|
| DOC-001 | SuperType load/save options examples | `07-supertype.md` §6 | MEDIUM |
| DOC-002 | Reference expand examples (annotation + options + side-by-side output) | `10-reference.md` §4.2 | HIGH |
| DOC-003 | Custom value readers/writers registration examples | `14-custom-values.md` §3-4 | MEDIUM |
| DOC-004 | NUMERIC type strategy round-trip example | `06-type.md` §1.6 | MEDIUM |

---

## 6. Plan D: Discriminator Refactoring (Deferred)

These architectural changes were identified during spec review but deferred pending a comprehensive refactoring strategy.

### D1: Remove MAPPED from TypeStrategy Enum

**Status:** DEFERRED

**Issue:** `MAPPED` is not a type representation strategy - it's a type translation layer. It should be removed from `TypeStrategy` enum and treated as a separate discriminator mapping configuration.

**Files:**
- `org.eclipse.fennec.model.metadata/model/metadata.ecore`
- All usages in serialization/deserialization code
- Tests using `TypeStrategy.MAPPED`

### D2: Inline Mapping for References

**Status:** DEFERRED

**Feature:** Simple, static discriminator mapping at per-reference level without requiring `TypeDiscriminatorService`.

**Example annotation:**
```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="partners">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="type.key" value="test"/>
    <details key="inlineMapping.FooBar" value="http://example.org#//Friend"/>
    <details key="inlineMapping.BarBaz" value="http://example.org#//Enemy"/>
  </eAnnotations>
</eStructuralFeatures>
```

### D3: Property-Based Discriminator Configuration

**Status:** DEFERRED

**Feature:** Allow discriminator mappings via load/save options for runtime flexibility.

**Example:**
```java
Map<String, Object> options = Map.of(
    "codec.type.map.lorawan-devices.Dragino_LSE01", "http://example.org#//DraginoLSE01Uplink",
    "codec.type.map.lorawan-devices.Dragino_LHT65", "http://example.org#//DraginoLHT65Uplink"
);
```

### D4: STRUCTURED Format Warning for Discriminator

**Status:** DEFERRED

**Issue:** If discriminator mapping is configured with `typeFormat=STRUCTURED`, should emit WARNING and treat as PLAIN (discriminator is a simple string value).

---

## 7. Execution Roadmap

### Recommended Order

```
Plan A (Deprecated API Migration) ✅ COMPLETE

Plan B Phase 1 (Core Metadata Gaps):
  GAP-001: Feature Visibility (directional ignore/force)
  GAP-002: Fallback Strategy wiring
  GAP-003: Feature Strictness attributes
  GAP-004: Diagnostic Options
  GAP-005: ID Value Key
  GAP-014: inherit enum

Plan B Phase 2 (Advanced Features):
  GAP-008: Value Reader/Writer handlers
  GAP-009: Scope wiring for runtime options
  GAP-007: Expand deserialization
  GAP-006: Metadata merge behavior
  GAP-010: Hierarchy resolution tests

Plan B Phase 3 (Polish):
  GAP-011, GAP-012, GAP-013

Plan C (Documentation Examples) — can be done in parallel:
  DOC-001 through DOC-004

Plan D (Discriminator Refactoring) — after Plan B:
  D1: Remove MAPPED from TypeStrategy
  D2: Inline mapping for references
  D3: Property-based discriminator config
  D4: STRUCTURED format warning
```

**Notes:**
- Plan A is complete, GAP-008 (value handlers) can now proceed
- Plan A is lower risk and removes deprecated code usage
- Cleaner codebase makes Plan B changes easier to reason about

### Property Matrix (for Plan B reference)

#### Type Properties (Spec 07§2)
| Property | Levels | Direction | Default |
|----------|--------|-----------|---------|
| typeStrategy | G, C, F | both | NAME |
| typeKey | G, C, F | both | _type |
| typeInclude | G, C, F | both | WRAPPER_OBJECT |
| typeSchemaKey | G, C, F | both | _schema |
| typeNameKey | G, C, F | both | _name |

#### ID Properties (Spec 06)
| Property | Levels | Direction | Default |
|----------|--------|-----------|---------|
| idStrategy | G, C | both | ID_FIELD |
| idKey | G, C | both | _id |
| idKeyMode | G, C | both | ID_ONLY |
| idFormat | G, C | both | PLAIN |
| idOnTop | G, C | write | true |
| idValueKey | G, C | both | value |
| idFeatures | C | both | [] |

#### Feature Properties (Spec 07§3)
| Property | Levels | Direction | Default |
|----------|--------|-----------|---------|
| key | F | both | (feature name) |
| visibility | G, C, F | read/write separate | NONE |
| serializeNull | G, C, F | write | false |
| serializeEmpty | G, C, F | write | false |
| serializeDefault | G, C, F | write | false |

#### Reference Properties (Spec 08)
| Property | Levels | Direction | Default |
|----------|--------|-----------|---------|
| expand | G, C, F | both | false |
| expandDepth | G, C, F | both | 1 |
| discriminatorPath | F | read | null |
| discriminatorValue | F | read | null |
| fallbackStrategy | G, C, F | read | FALLBACK |
| fallbackEClass | F | read | null |

#### Strictness Properties (Spec 07§6)
| Property | Levels | Direction | Default |
|----------|--------|-----------|---------|
| strictOnUnknown | G, C | read | false |
| strictOnMissing | G, C | read | false |

*Legend: G=Global, C=EClass, F=Feature*

---

## 8. Critical Files Reference

### Plan A: Files to Modify

**New files to create (codec.api project):**
- `src/org/eclipse/fennec/codec/value/DefaultCodecReaderContext.java`
- `src/org/eclipse/fennec/codec/value/DefaultCodecWriterContext.java`
- `src/org/eclipse/fennec/codec/value/SimpleEffectiveCodecConfig.java`
- `test/org/eclipse/fennec/codec/value/DefaultCodecReaderContextTest.java`
- `test/org/eclipse/fennec/codec/value/DefaultCodecWriterContextTest.java`

**SRC files to modify (codec.v2 project, `codec.*` package):**
- `config/effective/EffectiveCodecConfig.java`
- `module/CodecModule.java`
- `resource/CodecResource.java`
- `ser/AttributeSerializationEntry.java`
- `ser/ReferenceSerializationEntry.java`
- `deser/AttributeDeserializationEntry.java`
- `deser/ReferenceDeserializationEntry.java`
- `ser/CodecEObjectSerializer.java` (orchestrator — entry creation)

**TEST files to modify (codec.v2 project, `codec.*` package):**
- `deser/AttributeDeserializationEntryCanHandleTest.java`
- `deser/ReferenceDeserializationEntryCanHandleTest.java`
- `deser/ReferenceDeserializationEntryCustomReaderTest.java`
- `ser/AttributeSerializationEntryCanHandleTest.java`
- `ser/ReferenceSerializationEntryCanHandleTest.java`
- `ser/ReferenceSerializationEntryCustomWriterTest.java`
- `module/CodecModuleBuilderTest.java`
- `resource/CodecResourceCustomValueTest.java`

### Plan B: Files to Modify

**Ecore model:**
- `org.eclipse.fennec.codec.metadata/model/codec.ecore` (add enums, attributes)

**Aspect provider:**
- `org.eclipse.fennec.codec.metadata/src/*/CodecAspectProvider.java`

**Tests:**
- `org.eclipse.fennec.codec.metadata/test/*/CodecAspectProviderValidConfigTest.java`
- `org.eclipse.fennec.codec.metadata/test/*/CodecAspectProviderMisconfigTest.java`
- `org.eclipse.fennec.codec.metadata/test/*/test-codec-annotations.ecore`

### Testing Commands

```bash
# Codec V2 tests (JUnit 5, NOT OSGi)
./gradlew :org.eclipse.fennec.codec.v2:cleanTest :org.eclipse.fennec.codec.v2:test

# Metadata tests
./gradlew :org.eclipse.fennec.codec.metadata:test

# Full build
./gradlew build
```
