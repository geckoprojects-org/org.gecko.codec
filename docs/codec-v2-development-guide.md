# Codec V2 Development Guide

This document provides context for continuing codec.v2 development across sessions. It captures the goals, current state, and links to detailed architecture documentation.

**Last Updated:** 2026-01-11 (Phase 3 Complete + Error Handling)

---

## 1. Project Overview

We are building **codec.v2**, a new EMF serialization codec based on the specification in the [`codec-v2-spec/`](codec-v2-spec/) folder. The goal is to create a clean, well-structured implementation that follows the configuration hierarchy defined in the spec.

**Specification Documents:**
- [Overview & TOC](codec-v2-spec/00-overview.md) - Start here
- [Serialization Strategies](codec-v2-spec/01-strategies.md) - PLAIN vs STRUCTURED
- [Configuration Hierarchy](codec-v2-spec/02-config-hierarchy.md) - How config is resolved (foundational)
- [Global Options](codec-v2-spec/03-global-options.md) - Smart compression, field ordering
- [Type](codec-v2-spec/04-type.md), [SuperType](codec-v2-spec/05-supertype.md), [ID](codec-v2-spec/06-id.md), [Reference](codec-v2-spec/07-reference.md) - Core serialization targets
- [Architecture](codec-v2-spec/13-architecture.md) - Component overview
- [Format Abstraction](codec-v2-spec/16-format-abstraction.md) - Multi-format support (JSON, BSON, CSV, custom)

### Key Projects

| Project | Purpose | Status | Architecture Doc |
|---------|---------|--------|------------------|
| `org.eclipse.fennec.model.metadata` | Generic MetadataService infrastructure | ✅ Complete | [model-metadata-architecture.md](../org.eclipse.fennec.model.metadata/model-metadata-architecture.md) |
| `org.eclipse.fennec.codec.metadata` | Codec-specific aspects and annotation parsing | ✅ Complete | [codec-metadata-architecture.md](../org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md) |
| `org.eclipse.fennec.codec.v2` | New codec implementation | ✅ Phase 3 Complete | See [14-implementation.md](codec-v2-spec/14-implementation.md) |
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

> **For detailed implementation status, see [14-implementation.md](codec-v2-spec/14-implementation.md)**
>
> **For test coverage details, see [15-test-coverage.md](codec-v2-spec/15-test-coverage.md)**

### 5.1 Implementation Summary

**Phase 1-3: Complete ✅**

The codec.v2 implementation is feature-complete with 774+ tests passing:

| Feature | Status |
|---------|--------|
| Type strategies (URI, NAME, MAPPED, NUMERIC, SCHEMA_AND_TYPE) | ✅ |
| ID strategies (PLAIN, STRUCTURED, combined IDs) | ✅ |
| Reference handling (containment, non-containment, cross-doc) | ✅ |
| SuperType serialization (ALL, SINGLE, ARRAY, STRING) | ✅ |
| Smart compression | ✅ |
| Custom value readers/writers (unified interface) | ✅ |
| Polymorphic lists | ✅ |
| Bidirectional references | ✅ |
| Circular references | ✅ |
| Null/default value handling | ✅ |
| Error handling (EMF Resource diagnostics) | ✅ |
| Enum serialization (LITERAL, NAME, VALUE) | ✅ |
| Extended metadata names | ✅ |
| Global feature ignore | ✅ |
| Expand references | ✅ |
| Proxy creation | ✅ |

### 5.2 Remaining Work (Phase 4)

| Feature | Priority | Description |
|---------|----------|-------------|
| OSGi Integration | High | CodecResourceFactory service registration |
| Cross-Resource References | Medium | ResourceSet-based resolution |
| Performance Optimization | Low | Large object graph handling |

### 5.3 Deferred

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

## 7. Testing Strategy

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
- `NAME` - EClass simple name
- `CLASS` - Java instance class name
- `URI` - Full EClass URI (default)
- `MAPPED` - Discriminator value from feature path
- `SCHEMA_AND_TYPE` - Separate schema and type fields
- `STRUCTURED` - Nested object with schema/name
- `NUMERIC` - Numeric classifier IDs

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
- [14-implementation.md](codec-v2-spec/14-implementation.md) - Implementation checklist
- [15-test-coverage.md](codec-v2-spec/15-test-coverage.md) - Test coverage mapping

---

## 12. Session Continuity Tips

If context is lost:

1. Read this document first: `docs/codec-v2-development-guide.md`
2. Check spec for details: `docs/codec-v2-serialization-spec.md`
3. Review current TODO state (if available)
4. Examine recent git commits for context
5. Ask user for clarification if needed

The key insight: **MetadataService parses EAnnotations at EPackage registration time and creates pre-computed aspect objects that the codec uses at serialization time.**

**For MAPPED TypeStrategy:** All packages are registered with MetadataService before serialization/deserialization. When registering an EPackage, the MetadataService runs through codec aspects and updates the TypeDiscriminatorService. By the time deserialization happens, there's already a mapping from discriminator values (like "temp-sensor") to EClasses.
