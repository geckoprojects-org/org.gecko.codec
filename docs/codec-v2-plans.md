# Codec V2 Remaining Plans

This document consolidates all active plans for completing the codec.v2 migration. It merges the
Spec Compliance Refactoring Plan and the Deprecated API Migration Plan into a single phased roadmap.

**Created:** 2026-02-02
**Related documents:**
- [`docs/codec-v2-development-guide.md`](codec-v2-development-guide.md) — Session continuity, current state
- [`docs/codec-v2-spec/`](codec-v2-spec/) — Specification (source of truth)

---

## Table of Contents

1. [Current State](#1-current-state)
2. [Architecture Overview](#2-architecture-overview)
3. [Plan A: Deprecated API Migration](#3-plan-a-deprecated-api-migration-codecvaluereaderwriter)
4. [Plan B: Spec Compliance Gaps](#4-plan-b-spec-compliance-gaps)
5. [Execution Roadmap](#5-execution-roadmap)
6. [Critical Files Reference](#6-critical-files-reference)

---

## 1. Current State

### What's Done

The 8-step package migration from `codec.v2.*` to `codec.*` is **complete**:
- New spec-compliant classes live in `org.eclipse.fennec.codec.*` packages
- Old `codec.v2.*` classes are deprecated + tests disabled
- `ConfigurationResolver` replaced `CodecConfiguration` + `ConfigurationMerger`
- All 6 config types have spec + resolver tests (Type, Id, SuperType, Feature, Reference, Discriminator)
- 1461 tests total, 0 failures

### What Remains

Two categories of work:

| Category | Description | Scope |
|----------|-------------|-------|
| **Plan A** | Migrate from deprecated `codec.api.value.*` to new `codec.value.*` API | 8 SRC + 8 TEST files |
| **Plan B** | Fill spec compliance gaps (14 identified GAPs) | Metadata model + entries + tests |

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

## 3. Plan A: Deprecated API Migration (CodecValueReader/Writer)

### Goal

Replace all usages of deprecated `codec.api.value.*` types with `codec.value.*` types
in the new `codec.*` package SRC and TEST files.

### Key Differences: Old vs New API

| Aspect | Old (`codec.api.value`) | New (`codec.value`) |
|--------|------------------------|---------------------|
| Reader method | `T read(JsonParser, F, DeserializationContext)` | `T read(CodecReaderContext, F)` |
| Writer method | `void write(T, F, JsonGenerator, SerializationContext)` | `void write(T, F, CodecWriterContext)` |
| Registration | `registry.registerReader("name", reader)` | `registry.register(reader)` (uses `getName()`) |
| Registry class | `codec.api.value.CodecValueRegistry` | `codec.value.CodecValueRegistry` |
| Config access | Not available | Via `ctx.getConfig()` |
| Diagnostics | Not available | Via `ctx.getDiagnostics()` |

### Step A1: Create Context Implementations (codec.api project)

**New files in `codec.api/src/org/eclipse/fennec/codec/value/`:**

#### `SimpleEffectiveCodecConfig.java`
Lightweight value holder implementing the `EffectiveCodecConfig` interface:
```java
public class SimpleEffectiveCodecConfig implements EffectiveCodecConfig {
    private final TypeConfig typeConfig;
    private final SuperTypeConfig superTypeConfig;
    private final IdConfig idConfig;
    private final FeatureConfig featureConfig;
    private final ReferenceConfig referenceConfig;
    private final DiscriminatorConfig discriminatorConfig;
    private final boolean smartCompressionEnabled;
    // Constructor + getters
}
```

#### `DefaultCodecReaderContext.java`
```java
public class DefaultCodecReaderContext implements CodecReaderContext {
    private final JsonParser parser;
    private final DeserializationContext jacksonContext;
    private final EffectiveCodecConfig config;
    private final DiagnosticCollector diagnostics;
    // Constructor + getters
}
```

#### `DefaultCodecWriterContext.java`
```java
public class DefaultCodecWriterContext implements CodecWriterContext {
    private final JsonGenerator generator;
    private final SerializationContext jacksonContext;
    private final EffectiveCodecConfig config;
    private final DiagnosticCollector diagnostics;
    // Constructor + getters
}
```

**Tests:** `DefaultCodecReaderContextTest.java`, `DefaultCodecWriterContextTest.java`

**Risk:** Low — new files only, no existing code modified.

### Step A2: Switch CodecValueRegistry Import (all files)

Drop-in import swap from `codec.api.value.CodecValueRegistry` to `codec.value.CodecValueRegistry`.
Same API surface (getWriter, getReader, registerWriter, registerReader) plus auto-registration.

**SRC files (7):**
- `codec.config.effective.EffectiveCodecConfig`
- `codec.module.CodecModule`
- `codec.resource.CodecResource`
- `codec.ser.AttributeSerializationEntry`
- `codec.ser.ReferenceSerializationEntry`
- `codec.deser.AttributeDeserializationEntry`
- `codec.deser.ReferenceDeserializationEntry`

**TEST files (8):**
- `codec.deser.AttributeDeserializationEntryCanHandleTest`
- `codec.deser.ReferenceDeserializationEntryCanHandleTest`
- `codec.deser.ReferenceDeserializationEntryCustomReaderTest`
- `codec.ser.AttributeSerializationEntryCanHandleTest`
- `codec.ser.ReferenceSerializationEntryCanHandleTest`
- `codec.ser.ReferenceSerializationEntryCustomWriterTest`
- `codec.module.CodecModuleBuilderTest`
- `codec.resource.CodecResourceCustomValueTest`

**Risk:** Low — same API, import-only change.

### Step A3: Migrate EffectiveCodecConfig Typed Getters

Update `EffectiveCodecConfig.getValueWriter()` and `getValueReader()` to return new interface types
(`codec.value.CodecValueWriter/Reader` instead of `codec.api.value.*`).

**Risk:** Low — same API surface.

### Step A4: Migrate Entry Classes

Pattern for each of the 4 entry classes:
1. Change field types from old to new interfaces
2. Change constructor to accept full `EffectiveCodecConfig` (class) instead of just `CodecValueRegistry`
3. At call site, build `SimpleEffectiveCodecConfig` + `DefaultCodecWriterContext`/`DefaultCodecReaderContext`

**Per-file details:**

| Entry Class | Has EffectiveCodecConfig? | Diagnostics Source |
|-------------|--------------------------|-------------------|
| `AttributeSerializationEntry` | Needs adding | From `EffectiveCodecConfig.getDiagnostics()` |
| `ReferenceSerializationEntry` | Already has it | From `EffectiveCodecConfig.getDiagnostics()` |
| `AttributeDeserializationEntry` | Needs adding | From `DeserializationState.getDiagnosticCollector()` |
| `ReferenceDeserializationEntry` | Needs adding | From `DeserializationState.getDiagnosticCollector()` |

**Before:**
```java
customWriter.write(value, attribute, gen, ctxt);
```

**After:**
```java
CodecWriterContext writerCtx = new DefaultCodecWriterContext(gen, ctxt, effectiveConfig, diagnostics);
customWriter.write(value, attribute, writerCtx);
```

**Risk:** Medium — constructor signature changes ripple to orchestrators and tests.

### Step A5: Migrate Tests (8 files)

Update test files to use new value interfaces:
1. Change imports
2. Add `getName()` method to inline reader/writer implementations
3. Use `CodecReaderContext`/`CodecWriterContext` in method signatures
4. Create test context objects using `DefaultCodecReaderContext`/`DefaultCodecWriterContext`

**Risk:** Medium — method signature changes in inline implementations.

### Step A6: Verify and Clean Up

- Run: `./gradlew :org.eclipse.fennec.codec.v2:cleanTest :org.eclipse.fennec.codec.v2:test`
- Verify deprecation warnings drop to old `v2.*` package files only
- Update development guide §11.8

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

## 5. Execution Roadmap

### Recommended Order

```
Plan A (Deprecated API Migration) ← DO FIRST
  A1: Create context implementations (new files only)
  A2: Switch CodecValueRegistry imports (15 files)
  A3: Update EffectiveCodecConfig typed getters
  A4: Migrate 4 entry classes
  A5: Migrate 8 test files
  A6: Verify + clean up

Plan B Phase 1 (Core Metadata Gaps):
  GAP-001: Feature Visibility (directional ignore/force)
  GAP-002: Fallback Strategy wiring
  GAP-003: Feature Strictness attributes
  GAP-004: Diagnostic Options
  GAP-005: ID Value Key
  GAP-014: inherit enum

Plan B Phase 2 (Advanced Features):
  GAP-008: Value Reader/Writer handlers (depends on Plan A completion)
  GAP-009: Scope wiring for runtime options
  GAP-007: Expand deserialization
  GAP-006: Metadata merge behavior
  GAP-010: Hierarchy resolution tests

Plan B Phase 3 (Polish):
  GAP-011, GAP-012, GAP-013
```

**Rationale:** Plan A first because:
- GAP-008 (value handlers) depends on the new reader/writer API being wired
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

## 6. Critical Files Reference

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
