# Codec V2 Development Guide

This document provides context for continuing codec.v2 development across sessions. It captures the goals, current state, and links to detailed architecture documentation.

**Last Updated:** 2026-01-23 (Spec Reorganization Completed)

---

## 0. Development Workflow (MUST FOLLOW)

### Testing Commands (CRITICAL)

**Codec V2 uses JUnit 5 tests, NOT OSGi tests!**

```bash
# Codec V2 projects - use these:
./gradlew :org.eclipse.fennec.codec.v2:test
./gradlew :org.eclipse.fennec.codec.metadata:test
./gradlew :org.eclipse.fennec.model.metadata:test
./gradlew :org.eclipse.fennec.codec.jsonschema.v2:test
./gradlew :org.eclipse.fennec.codec.openapi:test

# Do NOT use testOSGi for v2 projects!
```

### Workflow Rules

1. **Specification First**
   - Check spec (`docs/codec-v2-spec/`) before implementing
   - Spec is the source of truth - clarify gaps before coding
   - Never implement something that contradicts the spec

2. **Test-Driven Development**
   - Create tests BEFORE implementation
   - Tests must FAIL before implementation (proves test validity)
   - Tests must PASS after implementation

3. **Failing Tests = Investigation Required**
   - **NEVER change tests just to make them pass**
   - Investigate root cause: implementation bug? side effect? spec gap?
   - Even "unrelated" failures may reveal important side effects

4. **Spec Completeness Requirements**
   - Serialization vs Deserialization: clarify when each config applies
   - Examples required for: property maps, config builder, annotations
   - Default behavior must be documented
   - Override behavior must be explicit
   - Error cases must be documented

5. **Configuration Hierarchy Consistency**
   - Every ANNOTATION feature → MUST have property/config builder equivalent
   - NOT every property → needs annotation (some are runtime-only)
   - Ask about contradictions or gaps - don't assume

6. **Code Quality**
   - Use imports, NEVER fully qualified class names
   - Look for `@claude`/`@CLAUDE` comments - these are instructions

### Available Agents

Agents in `.claude/agents/` help enforce this workflow:

| Agent | Purpose |
|-------|---------|
| `spec-validator` | Validate changes against spec, reveal gaps/contradictions |
| `test-runner` | Run JUnit tests (not OSGi!), investigate failures |
| `example-curator` | Identify good test examples for documentation |
| `error-hardening` | Find misconfiguration cases, harden error handling |
| `doc-updater` | Update docs at session end |
| `code-reviewer` | Check imports, @claude comments, quality |

---

## 1. Project Overview

We are building **codec.v2**, a new EMF serialization codec based on the specification in the [`codec-v2-spec/`](codec-v2-spec/) folder. The goal is to create a clean, well-structured implementation that follows the configuration hierarchy defined in the spec.

**Specification Documents:**
- [Overview & TOC](codec-v2-spec/00-overview.md) - Start here
- [Architecture](codec-v2-spec/01-architecture.md) - Component overview (foundational)
- [Configuration Resolution](codec-v2-spec/02-config-resolution.md) - How config is resolved
- [Common Types](codec-v2-spec/04-common-types.md) - SerializationFormat, TypeStrategy, etc.
- [Global Options](codec-v2-spec/05-global-options.md) - Smart compression, field ordering
- [Type](codec-v2-spec/06-type.md), [SuperType](codec-v2-spec/07-supertype.md), [ID](codec-v2-spec/09-id.md), [Reference](codec-v2-spec/10-reference.md) - Core serialization targets
- [Custom Values](codec-v2-spec/15-custom-values.md) - Custom value readers/writers
- [Format Abstraction](codec-v2-spec/17-format-abstraction.md) - Multi-format support (JSON, BSON, CSV, OpenAPI)

### Key Projects

| Project | Purpose | Status | Architecture Doc |
|---------|---------|--------|------------------|
| `org.eclipse.fennec.model.metadata` | Generic MetadataService infrastructure | ✅ Complete | [model-metadata-architecture.md](../org.eclipse.fennec.model.metadata/model-metadata-architecture.md) |
| `org.eclipse.fennec.codec.metadata` | Codec-specific aspects and annotation parsing | ✅ Complete | [codec-metadata-architecture.md](../org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md) |
| `org.eclipse.fennec.codec.v2` | New codec implementation | ✅ Phase 3 Complete | See [00-overview.md](codec-v2-spec/00-overview.md) |
| `org.eclipse.fennec.codec.jsonschema.v2` | JSON Schema ↔ EPackage converters | ✅ Complete | See [17-format-abstraction.md](codec-v2-spec/17-format-abstraction.md) |
| `org.eclipse.fennec.codec.openapi` | OpenAPI 3.x resource with JSON Schema support | ✅ Complete | See [17-format-abstraction.md](codec-v2-spec/17-format-abstraction.md) |
| `org.eclipse.fennec.codec.v2.example` | Examples and integration tests | Not started | - |
| `org.eclipse.fennec.codec.*` (other) | Old codec implementations (reference only) | Existing | - |

### What's Old vs New

- **OLD** (`org.eclipse.fennec.codec.info*`): Old ModelInfoService that parses models - partially spec-compliant
- **NEW** (`org.eclipse.fennec.model.metadata` + `org.eclipse.fennec.codec.metadata`): Clean implementation following spec

---

## 2. Architecture

For detailed architecture, see:
- **[model-metadata-architecture.md](../org.eclipse.fennec.model.metadata/model-metadata-architecture.md)** - Core MetadataService, Aspect pattern, resolution strategy
- **[codec-metadata-architecture.md](../org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md)** - Codec aspects, EAnnotation mapping, value readers/writers

### 2.1 Two-Layer Design

```
┌─────────────────────────────────────────────────────────────────┐
│                    org.eclipse.fennec.codec.v2                   │
│                  (Codec implementation - uses metadata)          │
└───────────────────────────────┬─────────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────────┐
│                 org.eclipse.fennec.codec.metadata                │
│          (Codec-specific aspects and annotation parsing)         │
│                                                                  │
│   - ClassCodecAspect, FeatureCodecAspect, ReferenceCodecAspect  │
│   - TypeSerializationConfig, IdSerializationConfig, etc.        │
│   - Annotation parsing (codec.type, codec.id, codec.reference)  │
│   - Default value handling from spec                            │
└───────────────────────────────┬─────────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────────┐
│                 org.eclipse.fennec.model.metadata                │
│              (Generic MetadataService infrastructure)            │
│                                                                  │
│   - PackageMetadata, ClassMetadata, FeatureMetadata             │
│   - Aspect, ClassAspect, FeatureAspect (abstract bases)         │
│   - MetadataService API                                         │
│   - TypeDiscriminatorRegistry                                    │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 MetadataService Role

The MetadataService is a **dynamic component** that:
1. Listens to EMF model registry for EPackage changes
2. Parses EAnnotations from registered EPackages
3. Creates and caches metadata (aspects) for each EClass/EStructuralFeature
4. Provides pre-computed metadata to CodecResourceFactory

```
EPackage registered → MetadataService triggered →
  Parse EAnnotations → Apply defaults → Create Aspects → Cache
```

### 2.3 EMF Aspect Pattern (now in Ecore)

Aspects are now defined as EMF classes in the Ecore models:

```
metadata.ecore (generic):
  Aspect (abstract)
  ├── ClassAspect (abstract)     - attached to ClassMetadata.aspects
  └── FeatureAspect (abstract)   - attached to FeatureMetadata.aspects

codec.ecore (codec-specific):
  ClassCodecAspect extends ClassAspect
    - typeConfig: TypeSerializationConfig
    - idConfig: IdSerializationConfig
    - superTypeConfig: SuperTypeSerializationConfig
    - discriminatorValue, inheritFromParent

  FeatureCodecAspect extends FeatureAspect
    - effectiveKey, serialize, serializeNull/Empty/Defaults
    - valueWriterName, valueReaderName

  ReferenceCodecAspect extends FeatureCodecAspect
    - referenceConfig: ReferenceSerializationConfig
    - typeConfig: TypeSerializationConfig
    - inheritTypeFromTarget, expand
```

---

## 3. Configuration Hierarchy

From the spec (highest to lowest priority):

| Priority | Level | Scope | Description |
|----------|-------|-------|-------------|
| 1 (highest) | **Load/Save options** | Per-operation | `resource.save(options)` / `resource.load(options)` |
| 2 | **ResourceFactory defaults** | Per-factory | `defaultSaveOptions`/`defaultLoadOptions` |
| 3 | **Codec module config** | Per-codec | Jackson module configuration |
| 4 | **Configuration properties** | External | System properties, config files |
| 5 | **EAnnotations** | Per-model | Declared in .ecore model (static) |
| 6 (lowest) | **Built-in defaults** | Global | Hardcoded codec defaults |

**Current focus**: Implementing Level 5 (EAnnotation parsing) and Level 6 (built-in defaults).

---

## 4. EAnnotation Specification

### 4.1 Annotation Source

All codec annotations use source: `http://eclipse.org/fennec/codec`

### 4.2 Annotation Markers (Detail Keys)

| Marker | Applies To | Purpose |
|--------|------------|---------|
| `codec.type` | EClass, EReference | Type serialization config |
| `codec.id` | EClass | ID serialization config |
| `codec.reference` | EReference | Reference serialization config |
| `codec.supertype` | EClass | Supertype serialization config |
| `serialize` | EStructuralFeature | Whether to serialize (false = transient) |
| `key` | EStructuralFeature | Custom JSON property name |
| `valueWriterName` | EStructuralFeature | Custom value writer |
| `valueReaderName` | EStructuralFeature | Custom value reader |

### 4.3 Built-in Defaults (from spec)

**Type Configuration:**
```
strategy = URI
typeKey = "_type"
include = true
schemaKey = "schema"
nameKey = "name"
```

**ID Configuration:**
```
strategy = ID_FIELD  (or NONE if no ID attribute)
idKey = "_id"
separator = "/"
keyMode = ID_ONLY
format = PLAIN
```

**Reference Configuration:**
```
format = PLAIN
typeKey = "_type"
refKey = "_ref"
includeType = true
```

**SuperType Configuration:**
```
enabled = false
selection = ALL
superTypeKey = "_supertype"
format = PLAIN
```

**Feature Configuration:**
```
serialize = true
serializeNull = false
serializeDefaults = false
serializeEmpty = false
```

---

## 5. Current State

> **For test coverage details, see [15-test-coverage.md](codec-v2-spec/19-test-coverage.md)**

### 5.1 Implementation Phases

| Phase | Description | Status |
|-------|-------------|--------|
| **Phase 1** | Core Serialization - End-to-end round-trip, basic type/ID/reference handling, Jackson integration | ✅ Complete |
| **Phase 2** | Configuration - EAnnotation-based config, discriminator mapping, smart compression, array roots | ✅ Complete |
| **Phase 3** | Advanced Features - Custom value readers/writers, additional TypeStrategies, polymorphic lists, circular references | ✅ Complete |
| **Phase 4** | Production Readiness - Performance optimization, OSGi integration, cross-resource references | In Progress |

### 5.2 Detailed Implementation Checklist

**Core**
- [x] CodecResource implementation
- [x] ConfigurationMerger
- [x] EffectiveCodecConfig with lazy caching
- [x] EMFContextHolder using EffectiveCodecConfig
- [x] Jackson context integration

**Type Serialization (Format × Strategy Matrix)**

PLAIN format:
- [x] URI strategy (default) - ser + deser
- [x] NAME strategy - ser + deser
- [x] CLASS strategy - ser only
- [x] NUMERIC strategy - ser + deser
- [x] SCHEMA_AND_TYPE strategy - two separate fields (`_schema` + `_type`) - ser + deser
- [x] NONE strategy - no type field

STRUCTURED format:
- [x] SCHEMA_AND_TYPE - `{"schema":"...","type":"..."}` - ser + deser
- [x] URI - `{"uri":"<uri>"}` - ser + deser
- [x] NAME - `{"type":"<name>"}` - ser + deser
- [x] CLASS - `{"class":"<class>"}` - ser only
- [x] NUMERIC - `{"schema":"...","classifier":N}` - ser + deser

**ID Serialization**
- [x] Single ID field
- [x] Multiple ID fields with separator
- [x] PLAIN format
- [x] STRUCTURED format
- [x] ID_ONLY, BOTH, FEATURE_ONLY modes
- [x] Separator serialization (both formats)

**Reference Serialization**
- [x] Non-containment references
- [x] STRUCTURED format (default)
- [x] PLAIN format
- [x] Cross-document containment detection
- [x] Proxy creation
- [x] Expand feature (serialization + deserialization)
- [x] Proxy with projection

**Features**
- [x] EAttribute serialization
- [x] EReference serialization
- [x] Null/default/empty handling
- [x] Key customization
- [x] Enum serialization options (LITERAL, NAME, VALUE strategies)
- [x] Extended metadata names
- [x] EJavaObject attributes (any JSON value → native Java types)
- [x] JSON-to-String conversion for EString attributes (deserialization only)

**Global Options**
- [x] Smart compression (type omission)
- [x] Smart compression (same-schema names)
- [ ] Field ordering
- [x] Global feature ignore list

**SuperType Serialization**
- [x] ALL, SINGLE, ALL_EMF, NONE selection modes
- [x] ARRAY presentation (default)
- [x] STRING presentation (configurable separator)
- [x] PLAIN format (standalone `_supertype` field)
- [x] STRUCTURED format (inside `_type` object as `supertype` field)
- [x] Deserialization with optional validation

**Advanced**
- [ ] Cross-resource references (ResourceSet-based resolution)
- [x] Custom value readers/writers (unified interface)
- [x] Annotation inheritance

### 5.3 OpenAPI/JSON Schema Support

**Implementation Complete ✅**

| Feature | Status |
|---------|--------|
| JSON Schema → EPackage conversion | ✅ |
| EPackage → JSON Schema conversion | ✅ |
| OpenAPI 3.x resource implementation | ✅ |
| Real-world roundtrip tests | ✅ |

**OpenAPI Roundtrip Results:**

| File | Size | Schemas | Paths | Operations | Field Preservation |
|------|------|---------|-------|------------|-------------------|
| petstore.json | 45 KB | 6 → 6 (100%) | 13 | 19 | 92.6% |
| bike.json | 99 KB | 43 → 43 (100%) | 13 | 14 | 90.8% |
| sevdesk.json | 677 KB | 74 → 74 (100%) | 117 | 151 | 87.6% |
| kubernetes-api.json | 1944 KB | 249 → 249 (100%) | 113 | 248 | 86.7% |

### 5.4 Remaining Work (Phase 4)

| Feature | Priority | Description |
|---------|----------|-------------|
| OSGi Integration | High | CodecResourceFactory service registration |
| Cross-Resource References | Medium | ResourceSet-based resolution |
| Feature Type Hints | Medium | `CODEC_FEATURE_TYPE_HINTS` load option |
| Feature Value Readers | Medium | `CODEC_FEATURE_VALUE_READERS` load option |
| Feature Value Writers | Medium | `CODEC_FEATURE_VALUE_WRITERS` save option |
| Performance Optimization | Low | Large object graph handling |

### 5.5 Deferred

- Expand Depth > 1
- Field ordering options
- Streaming serialization

---

## 6. Key Files Reference

### Specifications and Documentation
- `docs/codec-v2-serialization-spec.md` - **Source of truth** for all configuration
- `docs/codec-v2-development-guide.md` - This document

### EMF Models
- `org.eclipse.fennec.model.metadata/model/metadata.ecore` - Generic metadata infrastructure
- `org.eclipse.fennec.codec.metadata/model/codec.ecore` - Codec-specific aspects and configs

### Codec V2 Implementation

**Serialization:**
- `org.eclipse.fennec.codec.v2.ser.CodecEObjectSerializer` - Main EObject serializer
- `org.eclipse.fennec.codec.v2.ser.SerializationEntry` - Entry interface
- `org.eclipse.fennec.codec.v2.ser.*SerializationEntry` - Specific entry implementations

**Deserialization:**
- `org.eclipse.fennec.codec.v2.deser.CodecEObjectDeserializer` - Main EObject deserializer
- `org.eclipse.fennec.codec.v2.deser.DeserializationEntry` - Entry interface
- `org.eclipse.fennec.codec.v2.deser.*DeserializationEntry` - Specific entry implementations
- `org.eclipse.fennec.codec.v2.deser.DeserializationState` - Tracks deserialization state

**Configuration:**
- `org.eclipse.fennec.codec.v2.config.CodecConfiguration` - Module configuration builder
- `org.eclipse.fennec.codec.v2.config.effective.*` - Pre-merged immutable configs
- `org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger` - Config resolution

**Resource:**
- `org.eclipse.fennec.codec.v2.resource.CodecResource` - EMF Resource implementation

**Module:**
- `org.eclipse.fennec.codec.v2.module.CodecModule` - Jackson module

**Utilities:**
- `org.eclipse.fennec.codec.v2.util.MetadataServiceFactory` - Factory for creating MetadataService with CodecAspectProvider
- `org.eclipse.fennec.codec.v2.util.CodecDiagnostic` - EMF Resource.Diagnostic implementation for error/warning reporting
- `org.eclipse.fennec.codec.v2.util.DiagnosticCollector` - Aggregates diagnostics during serialization/deserialization
- `org.eclipse.fennec.codec.v2.context.ContextHelper` - Context attribute management and diagnostic helper methods

**Tests:**
- `org.eclipse.fennec.codec.v2.resource.CodecResourceRoundTripTest` - Round-trip integration tests
- `org.eclipse.fennec.codec.v2.resource.CodecResourceAnnotationTest` - EAnnotation-based configuration tests
- `org.eclipse.fennec.codec.v2.ser.*Test` - Serialization unit tests
- `org.eclipse.fennec.codec.v2.deser.*Test` - Deserialization unit tests

### Metadata Infrastructure
- `org.eclipse.fennec.codec.metadata.provider.CodecAnnotationConstants` - Annotation keys and helper methods
- `org.eclipse.fennec.model.metadata.api.MetadataService` - Core metadata service interface
- `org.eclipse.fennec.model.metadata.service.MetadataServiceImpl` - Default implementation

### Generated EMF Code
- `org.eclipse.fennec.codec.metadata/src-gen/` - Generated interfaces and implementations for codec.ecore
- `org.eclipse.fennec.model.metadata/src-gen/` - Generated code for metadata.ecore

---

## 7. Spec Review Findings & Working Documents

A comprehensive review of the specification was conducted on 2026-01-20. The findings and ongoing work are documented in:

### 7.0 Key Reference Documents

| Document | Purpose | Status |
|----------|---------|--------|
| **[codec-v2-spec-review-findings.md](codec-v2-spec-review-findings.md)** | Comprehensive spec review results | Reference |
| **[16-annotation-reference.md](codec-v2-spec/16-annotation-reference.md)** | **Definitive reference** - all config options at all scope levels | ✅ Spec |
| **[99-open-questions.md](codec-v2-spec/99-open-questions.md)** | Design questions + implementation roadmap | ✅ Spec |
| **[epackage-scope-proposal.md](codec-v2-spec-working/epackage-scope-proposal.md)** | EPackage scope level proposal | Working |
| **[emf-configuration-model-proposal.md](codec-v2-spec-working/emf-configuration-model-proposal.md)** | EMF config model proposal | Working |

### 7.0.1 Annotation & Configuration Reference (IMPORTANT)

The **[16-annotation-reference.md](codec-v2-spec/16-annotation-reference.md)** document defines:

- **Which annotation keys are valid at each EMF element level** (EPackage, EClass, EReference, EAttribute)
- **Override behavior** (EReference → EClass → Global → Default)
- **Error cases** for misplaced annotations
- **Scope settings** (global-only, not in EAnnotations)

**Key Principles:**
1. **ID Configuration** → EClass only (identity doesn't change based on access path)
2. **Type Configuration** → EClass + EReference (strategy is class-level, format/keys can vary per-reference)
3. **SuperType Configuration** → EClass only (inheritance is intrinsic to the class)
4. **Reference Configuration** → EReference only (presentation concern for that specific reference)
5. **Feature Configuration** → EReference + EAttribute (per-feature settings)
6. **Scope Settings** → Global/CodecConfig only (NOT in EAnnotations)

### Session 2026-01-20: Major Clarifications

| Topic | Decision | Section |
|-------|----------|---------|
| **Type Strategy Scope** | EReference → EClass → Global hierarchy with Scope options | §2, §3 |
| **SuperType** | Extension of Type (follows type format/scope) | §3.3 |
| **Underscore Convention** | PLAIN: `_type`, STRUCTURED inner: `type` (no underscore) | §7 |
| **TypeStrategy.NONE** | Added for no type information scenarios | §11.3 |
| **CODEC_TYPE_MODE** | HINT (default) vs OVERRIDE for type hints | §11.6 |
| **DeserializationMode** | STRICT, LENIENT (default), AUTO_DETECT | §11.7 |
| **MAPPED removed from TypeStrategy** | Now a separate Discriminator Mapping layer | §11.8 |
| **Inline Mapping** | Per-reference discriminator mapping (NEW) | §11.8 |
| **Feature Ignore List** | Silently skip, configurable at Global/EClass/EReference | §11.9 |
| **Feature Strictness** | Separate `strictOnUnknownFeatures` / `strictOnMissingFeatures` | §11.10 |
| **Property Map Configuration** | `codec.` prefix, `codec.ser.*`, `codec.deser.*` for direction | §11.11 |

### Session 2026-01-21: Spec Clarification + Code Quality Fixes

| Topic | Decision | Section |
|-------|----------|---------|
| **Null vs Missing** | Clarified deserialization behavior | 09-feature.md §1.3 |
| **Object type null** | Explicit JSON `null` → sets feature to `null` | §1.3 |
| **Primitive type null** | Explicit JSON `null` → resets to EMF default (not Java primitive default) | §1.3 |
| **Missing field** | Retains EMF default value | §1.3 |
| **serializeNull/Defaults** | Confirmed as serialization-only settings | §1.3 |
| **Proxy serialization algorithm** | Documented complete decision tree | 08-reference.md §4.1.1 |
| **URI determination** | Same-doc=fragment, cross-doc=relative, proxy=preserved | §4.1.1 |

**Key Discoveries:**
1. EMF's `eSet(attribute, null)` for primitives resets to `defaultValueLiteral`, NOT Java primitive default (0, false)
2. Unresolved proxies serialize using their existing proxy URI from `InternalEObject.eProxyURI()`

**Tests Added:** `CodecResourceAdvancedTest.NullDefaultValueTests` - 3 new tests for null/missing behavior.

**Code Quality Fixes (9.x):**
1. **9.1 Resource leaks** - Added `finally` blocks with `closeQuietly()` in `ReferenceDeserializationEntry`
2. **9.2 FQCN violations** - Replaced all FQCNs with imports across 4 files
3. **9.3 Config validation** - Confirmed as resolved by design (getters handle dependencies)

**ID Spec Alignment:**
Updated `07-id.md` to align with Type handling patterns:
- **Section 8.1: StrategyScope Values** - `ALL`, `ROOT_ONLY`, `ROOT_CONTAINMENT`, `ROOT_NON_CONTAINMENT`
- **Section 8.2: Separate Scopes** - Independent `idScope` and `idFormatScope` (like Type)
- **Section 8.3: Setting Scope Rules** - Which settings use which scope property
- **Section 8.4: Why No Per-Reference** - Explicit rationale (like Type section 1.5.4)
- **Section 8.5-8.6: Containment Behavior, Dependencies** - Consistent with Type patterns
- **Section 9: Deserialization** - Key recognition, format detection, PLAIN/STRUCTURED deserialization, edge cases
- **Section 10: Default ID Settings** - Updated with complete serialization/deserialization algorithms

**Annotation/Property Documentation (6.x):**
- **6.1** - Expanded `00-overview.md` §1.1 with complete annotation key registry (Type, SuperType, ID, Reference, Feature)
- **6.2** - Clarified per-reference format is supported in `08-reference.md` §4 (unlike Type which is per-class)

**Error Handling Documentation (7.x):**
- **7.1** - Added complete error scenarios table to `00-overview.md` §2.1 (Type, ID, Reference, Feature, Configuration errors)
- **7.2** - Added diagnostic API examples to `00-overview.md` §2.2 (accessing, programmatic detection, suppressing warnings, fail-fast, custom handlers)

**Implementation Gaps (8.x):**
- **8.1** - Added cross-resource workaround documentation to `08-reference.md` §8.3 with three resolution options
- **8.2** - Decided Option B (keep parameter, document limitation) - limitation already documented in spec

**Feature Handling (11.x):**
- **11.1** - Added unknown field handling documentation to `09-feature.md` §10 (current: WARNING + skip; STRICT mode as future feature)

### Session 2026-01-21 (continued): Spec Review Completion

**Unknown Type Handling (5.3):**
- Added `05-type.md` §5.3.1 "Unknown Type Handling" documenting fallback behavior
- Unknown type WITH hint → WARNING, use hint
- Unknown type WITHOUT hint → ERROR, fail
- Documented resolution order (URI → Java class → Discriminator → Simple name → Numeric → Fallback)
- Updated `00-overview.md` §2.1 error scenarios to match actual implementation

**Metadata Field Ordering (2.4):**
- Documented existing `idOnTop` feature in `07-id.md` §8.7
- Default `idOnTop=true` puts `_id` before `_type` (useful for MongoDB)
- Added `codec.id.onTop` to annotation key registry in `00-overview.md`

**Option Keys Registry (3.1):**
- Added comprehensive `03-config-hierarchy.md` §10 "Load/Save Option Keys Registry"
- Documented Resource-Level, Context, and Internal options
- Added property map format (`codec.<target>.<property>`)
- Documented Java constants locations

**ResourceFactory DI Support (3.2):**
- Added parameterless constructor to `CodecResourceFactory` for DI frameworks
- Added setters: `setMetadataService()`, `setConfiguration()`, `setMapperBuilder()`
- Added `03-config-hierarchy.md` §8 "ResourceFactory Configuration" with examples for OSGi DS and Spring

### Session 2026-01-21 (final): Working Document Integration + ID Alignment

**Working Document Integration:**
Integrated remaining items from `type-strategy-scope-proposal.md` into main spec:

| Item | Integrated Into | Description |
|------|-----------------|-------------|
| **CODEC_TYPE_MODE** | `05-type.md` §5.5.1 | HINT vs OVERRIDE semantics for type hints |
| **DeserializationMode** | `05-type.md` §5.5.2 | STRICT/LENIENT/AUTO_DETECT type resolution strictness |
| **Direction-specific config** | `03-config-hierarchy.md` §10.7 | `codec.ser.*`, `codec.deser.*` prefixes |

**ID-Type Alignment Verification:**
Verified ID handling aligns with Type handling patterns:

| Feature | Type | ID | Notes |
|---------|------|-----|-------|
| **StrategyScope enum** | ✅ `typeScope` | ✅ `idScope` | Same enum, same semantics |
| **Separate format scope** | ✅ `typeFormatScope` | ✅ `idFormatScope` | Independent from strategy scope |
| **Per-reference config** | ✅ (limited) | ❌ | ID identifies object, not access path |
| **DeserializationMode** | ✅ STRICT/LENIENT/AUTO | ❌ (always lenient) | ID parsing is simpler |
| **Type hint mode** | ✅ HINT/OVERRIDE | ❌ | ID has no "hint" concept |

Added to ID spec:
- **`07-id.md` §8 "Type and ID Configuration Alignment"** - Comparison table with rationale
- **`07-id.md` §9.7 "ID Deserialization Error Handling"** - Error scenarios matching Type patterns

**Remaining in Working Document (DEFERRED):**
- Discriminator refactoring (10.x) - Major change, needs coordinated implementation
- Feature Strictness full implementation - `strictOnUnknownFeatures`, `strictOnMissingFeatures`

### Resolution Status Summary (FINAL)

| Category | Total | Resolved | Deferred | Documented |
|----------|-------|----------|----------|------------|
| 1. Contradictions | 3 | 3 | 0 | 0 |
| 2. Ser/Deser Clarity | 4 | 4 | 0 | 0 |
| 3. Config Hierarchy | 3 | 3 | 0 | 0 |
| 4. Missing Examples | 4 | 0 | 4 | 0 |
| 5. Default Behavior | 3 | 3 | 0 | 0 |
| 6. Annotation/Property | 2 | 2 | 0 | 0 |
| 7. Error Handling | 2 | 2 | 0 | 0 |
| 8. Implementation Gaps | 2 | 2 | 0 | 0 |
| 9. Code Quality | 3 | 3 | 0 | 0 |
| 10. Discriminator Refactoring | 4 | 0 | 4 | 0 |
| 11. Feature Handling | 1 | 0 | 0 | 1 |
| **TOTAL** | **31** | **22** | **8** | **1** |

### Remaining Work

**Deferred Items (Future Work):**
- **2.3** Field ordering direction (LOW priority)
- **4.1-4.4** Documentation examples for SuperType, Reference Expand, Custom Values, NUMERIC
- **10.1-10.4** Discriminator refactoring - needs coordinated strategy:
  - Remove MAPPED from TypeStrategy enum
  - Inline mapping for references
  - Property-based discriminator config
  - STRUCTURED format warning

**Documented (Feature Request):**
- **11.1** STRICT mode for unknown field handling (future feature)

### Session 2026-01-23: Spec Reorganization Complete

**Spec Structure Finalized:**

The specification reorganization is now complete. Final structure:

| Part | Chapters | Content |
|------|----------|---------|
| I: Foundations | 00-05 | Overview, Architecture, Config Resolution, Naming, Common Types, Global Options |
| II: Features | 06-12 | Type, SuperType, Discriminator, ID, Reference, Feature, Polymorphism |
| III: Runtime | 13-15 | Load/Save Options, Custom Values, Error Handling |
| IV: Reference | 16-19, 99 | Annotation Reference, Format Abstraction, Scenarios, Test Coverage, Open Questions |

**Key Chapters Revised:**

| Chapter | Changes |
|---------|---------|
| **13-load-save-options** | Option key tables, value reader/writer registration |
| **14-custom-values** | New `CodecReaderContext`/`CodecWriterContext` interfaces, `getName()` method, diagnostic support |
| **15-error-handling** | Serialization + deserialization sources, option tables, custom reader/writer integration |
| **16-annotation-reference** | Diagnostic Options section, cross-references, implementation status |
| **17-format-abstraction** | Aligned with new context interfaces |
| **99-open-questions** | Consolidated design questions + implementation roadmap |

**New Design Decisions:**

| Decision | Details |
|----------|---------|
| Value reader/writer context | `CodecReaderContext`/`CodecWriterContext` with config access and diagnostic reporting |
| Reader/writer registration | `getName()` on interfaces, builder `.valueReader(instance)`, load options `codec.valueReaders` |
| Diagnostic sources | Custom readers/writers can report via `ctx.addError()`/`ctx.addWarning()` |

**Working Documents:**

| Document | Status |
|----------|--------|
| `spec-reorganization-plan.md` | ✅ Archived (completed) |
| `annotation-scope-reference.md` | ✅ Archived (integrated into 16-annotation-reference) |
| `type-strategy-scope-proposal.md` | ✅ Archived (integrated into spec) |
| `epackage-scope-proposal.md` | Active - under review |
| `emf-configuration-model-proposal.md` | Active - under review |

### Next Session: Suggested Tasks

See **[99-open-questions.md](codec-v2-spec/99-open-questions.md)** for full implementation roadmap.

**High Priority (Spec vs Implementation Gaps):**
1. **Feature Visibility Refactor** - Add `ignore*`, `force*` annotations to replace `transient`/`serialize`
2. **Metadata Merge** - Implement `metadataMerge`/`metadataKey` for STRUCTURED format
3. **Fallback Handling** - Implement `fallbackStrategy`/`fallbackEClass` for discriminator mappings
4. **Feature Strictness** - Implement `strictOnUnknown`/`strictOnMissing`
5. **Diagnostic Options** - Complete `failFast`, `suppressWarnings` implementation

**Medium Priority:**
6. **Type/SuperType Value Reader/Writer** - Wire existing constants
7. **Expand Deserialization** - Deserialize expanded refs as detached EObjects
8. **Scope Wiring** - Wire `typeScope`, `idScope` in config resolution

**Design Decisions Needed:**
- Review EPackage Scope Level proposal
- Review EMF Configuration Model proposal

---

## 8. Testing Strategy

Tests should NOT be trivial getter/setter tests. Instead:

1. **Model-Based Testing**
   - Create Ecore test models with various annotation configurations
   - Run annotation parser on these models
   - Assert resulting aspects match expected values

2. **Default Value Testing**
   - Test that missing annotations fall back to spec defaults
   - Test partial annotations (some values set, others defaulted)

3. **Inheritance Testing**
   - Test annotation inheritance within same EPackage
   - Test cross-package inheritance with `codec.inherit`

4. **Integration Testing**
   - Full round-trip: Model → Parse → Serialize → Deserialize → Verify

---

## 8. Quick Reference: Enums

### SerializationFormat (codec-wide)
- `PLAIN` - Simple value (string, number, array of simple values)
- `STRUCTURED` - Nested object/map

### TypeStrategy
- `URI` - Full EClass URI (default)
- `NAME` - EClass simple name
- `SCHEMA_AND_TYPE` - Separate schema and type fields
- `NUMERIC` - Numeric classifier IDs
- `NONE` - No type information written/expected

**Note:** `MAPPED` was removed from TypeStrategy. Discriminator mapping is now a separate optional configuration layer. See working document Section 11.8.

### IdStrategy
- `ID_FIELD` - Single designated ID field (default)
- `COMBINED` - Multiple features combined
- `NONE` - No ID serialization

### IdKeyMode
- `ID_ONLY` - Only ID in output key
- `BOTH` - Both ID and feature name
- `FEATURE_ONLY` - Only feature name

### SuperTypeSelection
- `ALL` - All supertypes in hierarchy
- `ALL_EMF` - All EMF supertypes (skip Java interfaces)
- `SINGLE` - Only direct supertype
- `NONE` - No supertypes

---

## 9. Detailed Feature Documentation

For detailed feature documentation, see:
- [00-overview.md](codec-v2-spec/00-overview.md) - Spec overview and TOC
- [19-test-coverage.md](codec-v2-spec/19-test-coverage.md) - Test coverage mapping
- [18-annotation-reference.md](codec-v2-spec/18-annotation-reference.md) - Definitive annotation reference

---

## 10. Configuration Transition Work (Active)

This section tracks the ongoing transition from the old configuration approach to the new unified configuration architecture defined in the spec.

### 10.1 Transition Goals

1. **Complete annotation-scope-reference document** → becomes source of truth for all properties
2. **Design fluent builder API** for programmatic configuration (GlobalConfig, EClass, Reference builders)
3. **Ensure codec.metadata returns correctly merged properties** from MetadataService
4. **Adjust effective properties** according to merging rules at each level
5. **Update tests** to align with new spec (comment out invalid tests, adjust others)

### 10.2 Transition Log

| Date | Change | Files Modified | Notes |
|------|--------|----------------|-------|
| 2026-01-23 | Fixed compile error: removed `case MAPPED:` from switch | `TypeDeserializationEntry.java:446-458` | MAPPED removed from TypeStrategy enum per spec §11.8. Added `case SCHEMA_AND_TYPE:` (was missing). Discriminator mapping is now a separate layer. |
| 2026-01-23 | Commented out test using `TypeStrategy.MAPPED` | `TypeSerializationEntryTest.java:324-347` | Test is still valid conceptually - needs update to use new discriminator config approach |
| 2026-01-23 | Commented out test asserting `TypeStrategy.MAPPED` | `CodecResourceMappedTypeTest.java:135-150` | Test data file may need updating for new annotation structure |
| 2026-01-23 | Updated spec: removed MAPPED from TypeStrategy | `docs/codec-v2-spec/05-type.md` | Added NONE strategy, discriminator mapping as separate layer (§1.3), updated strategy tables |
| 2026-01-23 | Integrated migration plan into dev-guide | `docs/codec-v2-development-guide.md` | Added §10.5 Ecore Model Changes, §10.6 MAPPED→Discriminator Migration |
| 2026-01-23 | Deleted redundant document | `docs/codec-v2-spec-working/model-migration-plan.md` | Content integrated into dev-guide §10.5-10.6 |

### 10.3 Commented Out Tests (To Be Revisited)

These tests were commented out due to the `TypeStrategy.MAPPED` removal. They test **valid functionality** that still exists, but needs to be configured differently:

| Test Class | Test Method | Reason | Action Needed |
|------------|-------------|--------|---------------|
| `TypeSerializationEntryTest` | `mappedStrategy_serializesTypeWithDiscriminator()` | Uses `TypeStrategy.MAPPED` | Update to use discriminator mapping layer with different TypeStrategy |
| `CodecResourceMappedTypeTest` | `parsesMappedStrategyFromAnnotation()` | Asserts `TypeStrategy.MAPPED` from annotation | Update test data (ecore) and assertion to use new config structure |

**Note:** The discriminator mapping functionality is still valid! The tests need adjustment to:
1. Use a different TypeStrategy (e.g., `NAME`, `URI`, or `NONE`)
2. Configure discriminator mapping via the new separate layer (mapId, discriminatorValue)
3. Verify discriminator values are still output/resolved correctly

### 10.4 Current TODO List

**Phase 1: Documentation (Current)**
- [ ] Complete annotation-scope-reference document
  - [ ] Verify all properties against `CodecAspectProvider` implementation
  - [ ] Document direction-specific properties (`codec.ser.*`, `codec.deser.*`)
  - [ ] Add merging rules for each property at each level
  - [ ] Review EEnum-level annotation support

**Phase 2: Builder API Design**
- [ ] Design `GlobalConfigBuilder` for codec-wide settings
- [ ] Design `EClassConfigBuilder` for per-class settings
- [ ] Design `ReferenceConfigBuilder` for per-reference settings
- [ ] Design `TypeConfigBuilder`, `IdConfigBuilder` for sub-configs
- [ ] Support instantiation from property map
- [ ] Support fluent API chaining across builders

**Phase 3: Test Transition**
- [ ] Review each test class against spec
- [ ] Re-enable and fix commented tests one by one
- [ ] Add helper classes for better testability
- [ ] Order: Configuration → Serialization → Deserialization

**Phase 4: Implementation Alignment**
- [ ] Apply documented merging rules to `codec.metadata`
- [ ] Ensure `MetadataService` returns correctly merged properties
- [ ] Integrate with static/load-save property merging

### 10.5 Ecore Model Changes (Reference)

This section summarizes the Ecore model changes required for the new configuration architecture.

#### 10.5.1 metadata.ecore (org.eclipse.fennec.model.metadata)

| Change | Type | Element | Status |
|--------|------|---------|--------|
| Remove `MAPPED` | DELETE | `TypeStrategy.MAPPED` literal | ✅ Done |
| Add `onTop` | ADD | `BaseIdConfig.onTop : EBoolean = true` | Pending |
| Add `serializeSeparator` | ADD | `BaseIdConfig.serializeSeparator : EBoolean = true` | Pending |
| Add `separatorKey` | ADD | `BaseIdConfig.separatorKey : EString = "separator"` | Pending |

#### 10.5.2 codec.ecore (org.eclipse.fennec.codec.metadata)

| Change | Type | Element | Status |
|--------|------|---------|--------|
| Add `StrategyScope` | ADD | New EEnum (ALL, ROOT_ONLY, ROOT_CONTAINMENT, ROOT_NON_CONTAINMENT) | ✅ Done |
| Add `TypeHintMode` | ADD | New EEnum (HINT, OVERRIDE) | ✅ Done |
| Add `DeserializationMode` | ADD | New EEnum (LENIENT, STRICT, AUTO_DETECT) | ✅ Done |
| Add `strategyScope` | ADD | `TypeSerializationConfig.strategyScope : StrategyScope = ALL` | Pending |
| Add `formatScope` | ADD | `TypeSerializationConfig.formatScope : StrategyScope = ALL` | Pending |
| Add `strategyScope` | ADD | `IdSerializationConfig.strategyScope : StrategyScope = ALL` | Pending |
| Add `formatScope` | ADD | `IdSerializationConfig.formatScope : StrategyScope = ALL` | Pending |
| Add `typeHintMode` | ADD | `CodecConfig.typeHintMode : TypeHintMode = HINT` | Pending |
| Add `deserializationMode` | ADD | `CodecConfig.deserializationMode : DeserializationMode = LENIENT` | Pending |

#### 10.5.3 Test Requirements After Model Changes

**For org.eclipse.fennec.model.metadata:**
| Test | Description |
|------|-------------|
| `BaseIdConfigDefaultsTest` | Verify `onTop=true`, `serializeSeparator=true`, `separatorKey="separator"` defaults |
| `TypeStrategyEnumTest` | Verify remaining values: NAME, CLASS, URI, SCHEMA_AND_TYPE, NUMERIC, NONE |

**For org.eclipse.fennec.codec.metadata:**
| Test | Description |
|------|-------------|
| `StrategyScopeEnumTest` | Verify ALL, ROOT_ONLY, ROOT_CONTAINMENT, ROOT_NON_CONTAINMENT values |
| `TypeHintModeEnumTest` | Verify HINT, OVERRIDE values |
| `DeserializationModeEnumTest` | Verify LENIENT, STRICT, AUTO_DETECT values |
| `TypeSerializationConfigScopeTest` | Verify `strategyScope` and `formatScope` defaults |
| `IdSerializationConfigScopeTest` | Verify `strategyScope` and `formatScope` defaults |
| `CodecConfigDeserializationTest` | Verify `typeHintMode` and `deserializationMode` defaults |
| `CodecAnnotationParserScopeTest` | Verify annotation parsing for scope settings |

**For org.eclipse.fennec.codec.v2:**
| Test | Description |
|------|-------------|
| `TypeScopeSerializationTest` | Verify type info respects scope during serialization |
| `IdScopeSerializationTest` | Verify ID respects scope during serialization |
| `TypeHintModeDeserializationTest` | Verify HINT vs OVERRIDE behavior |
| `DeserializationModeTest` | Verify STRICT/LENIENT/AUTO_DETECT behavior |
| `IdOnTopSerializationTest` | Verify `_id` appears before `_type` when onTop=true |

### 10.6 MAPPED → Discriminator Migration

The discriminator mapping functionality (previously `TypeStrategy.MAPPED`) is being refactored into a separate layer.

**Current infrastructure (still valid):**
- `TypeDiscriminatorRegistry` - per-mapId registry for discriminator ↔ EClass mappings
- `TypeDiscriminatorService` - service managing registries across mapIds
- `CodecAspectProvider` - parses discriminatorValue and registers mappings

**Key change:** Discriminator is NOT a parallel to URI/NAME/CLASS strategies - it's an additional resolution mechanism that works alongside them.

**New behavior:**
```
Type resolution order (any strategy):
1. Try discriminator lookup (if configured) - inline mapping first, then named registry
2. Try configured strategy (URI, NAME, CLASS, etc.)
3. If both fail → use fallback/hints
4. If all fail → ERROR

Serialization:
1. Write type using configured strategy
2. ALSO write discriminator if configured (for interop)
```

**Files requiring updates:**
| File | Current Usage | Action |
|------|---------------|--------|
| `CodecAspectProvider.java` | Sets discriminatorValue | Keep parsing, don't set strategy to MAPPED |
| `TypeDeserializationEntry.java` | `case MAPPED:` removed | Use discriminator as fallback for all strategies |
| `TypeSerializationEntry.java` | Writes discriminator | Keep writing, independent of strategy |
| Test files | Various MAPPED references | Update to test discriminator layer separately |

### 10.7 Key Documents for Transition

| Document | Purpose | Status |
|----------|---------|--------|
| `docs/codec-v2-spec/18-annotation-reference.md` | Definitive property/annotation/level reference | ✅ Published |
| `docs/codec-v2-spec-working/type-strategy-scope-proposal.md` | Detailed scope/discriminator decisions | ✅ Reference |
| `docs/codec-v2-spec/02-config-resolution.md` | Configuration hierarchy spec | ✅ Published |
| `docs/codec-v2-spec/06-type.md` | Type serialization spec | ✅ Published |
| `docs/codec-v2-spec/08-discriminator-mapping.md` | Discriminator mapping spec | ✅ Published |

---

## 11. Session Continuity Tips

If context is lost:

1. Read this document first: `docs/codec-v2-development-guide.md`
2. Check spec for details: `docs/codec-v2-serialization-spec.md`
3. Review current TODO state (if available)
4. Examine recent git commits for context
5. Ask user for clarification if needed

The key insight: **MetadataService parses EAnnotations at EPackage registration time and creates pre-computed aspect objects that the codec uses at serialization time.**

**For Discriminator Mapping:** All packages are registered with MetadataService before serialization/deserialization. When registering an EPackage, the MetadataService runs through codec aspects and updates the TypeDiscriminatorService. By the time deserialization happens, there's already a mapping from discriminator values (like "temp-sensor") to EClasses. Note: Discriminator mapping is now a separate layer that works alongside any TypeStrategy (not a strategy itself).
