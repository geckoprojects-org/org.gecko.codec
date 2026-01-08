# Codec V2 Development Guide

This document provides context for continuing codec.v2 development across sessions. It captures the goals, current state, and links to detailed architecture documentation.

**Last Updated:** 2026-01-08

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

### Key Projects

| Project | Purpose | Status | Architecture Doc |
|---------|---------|--------|------------------|
| `org.eclipse.fennec.model.metadata` | Generic MetadataService infrastructure | ✅ Complete | [model-metadata-architecture.md](../org.eclipse.fennec.model.metadata/model-metadata-architecture.md) |
| `org.eclipse.fennec.codec.metadata` | Codec-specific aspects and annotation parsing | ✅ Complete | [codec-metadata-architecture.md](../org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md) |
| `org.eclipse.fennec.codec.v2` | New codec implementation | ✅ Phase 1 Complete | See Section 5 |
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

### 5.1 Phase 1 Complete: End-to-End Round-Trip Serialization ✅

**Date Completed:** 2025-12-16

The codec.v2 implementation now supports full round-trip serialization/deserialization of EMF EObjects:

#### Implemented Components

**Serialization (`org.eclipse.fennec.codec.v2.ser`):**
- `CodecEObjectSerializer` - Main serializer orchestrating all entries
- `SerializationEntry` interface with implementations:
  - `IdSerializationEntry` - `_id` field
  - `TypeSerializationEntry` - `_type` field with URI strategy
  - `SuperTypeSerializationEntry` - `_supertype` field (optional)
  - `AttributeSerializationEntry` - EAttribute values
  - `ReferenceSerializationEntry` - Containment (inline) and non-containment (`$ref`)
- `SerializationState` - Tracks current object and caches feature values

**Deserialization (`org.eclipse.fennec.codec.v2.deser`):**
- `CodecEObjectDeserializer` - Main deserializer orchestrating all entries
- `DeserializationEntry` interface with implementations:
  - `IdDeserializationEntry` - Reads `_id` field
  - `TypeDeserializationEntry` - Resolves EClass from `_type` URI
  - `AttributeDeserializationEntry` - Reads attribute values
  - `ReferenceDeserializationEntry` - Handles containment and non-containment refs
- `DeserializationState` - Tracks EObject creation and unresolved references

**Configuration (`org.eclipse.fennec.codec.v2.config`):**
- `CodecConfiguration` - Builder-pattern module configuration
- `effective/` package with pre-merged immutable configs:
  - `EffectiveCodecConfig` - Root config with caching, includes MetadataService reference
  - `EffectiveClassConfig`, `EffectiveFeatureConfig`
  - `EffectiveIdConfig`, `EffectiveTypeConfig`, `EffectiveSuperTypeConfig`
- `ConfigurationMerger` - Single point for configuration resolution (creates EffectiveCodecConfig)

**Context (`org.eclipse.fennec.codec.v2.context`):**
- `EMFCodecContext` - Base interface providing EffectiveCodecConfig access
- `EMFCodecReadContext` - Read context with type hints and child context creation
- `EMFCodecWriteContext` - Write context interface
- `EMFContextHolder` - Internal holder for EMF state (uses EffectiveCodecConfig)
- `CodecReadContext`, `CodecWriteContext` - Generic implementations

**Jackson Integration (`org.eclipse.fennec.codec.v2.jackson`):**
- `CodecJsonFactory` - Creates CodecJsonParser with EffectiveCodecConfig
- `CodecJsonParser` - UTF8StreamJsonParser with CodecJsonReadContext
- `CodecJsonReadContext` - JSON-specific EMFCodecReadContext implementation
- `CodecTokenBufferReadContext` - For token replay (featurePath resolution)

**Resource (`org.eclipse.fennec.codec.v2.resource`):**
- `CodecResource` - EMF Resource implementation
  - `doSave()` - Serializes EObjects to JSON
  - `doLoad()` - Deserializes JSON to EObjects
  - `resolveReferences()` - Post-deserialization reference resolution

**Module (`org.eclipse.fennec.codec.v2.module`):**
- `CodecModule` - Jackson module registration

#### Tested Capabilities

All tests in `CodecResourceRoundTripTest`:

| Feature | Status |
|---------|--------|
| String attributes | ✅ |
| Integer attributes | ✅ |
| Boolean attributes | ✅ |
| Double attributes | ✅ |
| Multi-valued attributes (lists) | ✅ |
| Containment references (nested objects) | ✅ |
| Complex objects (all features) | ✅ |
| Non-containment references (single) | ✅ |
| Non-containment references (multi) | ✅ |
| Reference resolution within resource | ✅ |

### 5.2 Completed (Pre-Phase 1)

- [x] Created `codec-v2-serialization-spec.md` with full specification
- [x] Added Aspect infrastructure to `metadata.ecore` (Aspect, ClassAspect, FeatureAspect)
- [x] Created codec aspects in `codec.ecore` (ClassCodecAspect, FeatureCodecAspect, ReferenceCodecAspect)
- [x] Created configuration classes (TypeSerializationConfig, IdSerializationConfig, etc.)
- [x] Removed redundant Java code replaced by EMF generation
- [x] Merged codec.metadata.model into codec.metadata
- [x] Created `CodecAnnotationConstants.java` with annotation keys and defaults

### 5.3 Phase 2.1 Complete: EAnnotation-Based Configuration ✅

**Date Completed:** 2025-12-17

The codec.v2 implementation now supports EAnnotation-based configuration. EAnnotations in Ecore models are parsed and used to customize serialization behavior.

#### Implemented Components

**MetadataServiceFactory (`org.eclipse.fennec.codec.v2.util`):**
- `MetadataServiceFactory.create()` - Creates MetadataService with CodecAspectProvider registered
- `MetadataServiceFactory.configureForCodec()` - Adds codec aspect support to existing service

**TypeStrategy Support:**
- `TypeSerializationEntry` - Now supports NAME, CLASS, NUMERIC, URI strategies
- `TypeDeserializationEntry` - Resolves EClass based on strategy (simple name, URI, etc.)

#### Tested EAnnotation Features

All tests in `CodecResourceAnnotationTest`:

| Feature | Annotation | Status |
|---------|------------|--------|
| Custom ID key | `codec.id` with `key` | ✅ |
| ID strategy (ID_FIELD, COMBINED) | `codec.id` with `strategy` | ✅ |
| Combined ID with separator | `codec.id` with `separator`, `idFeatures` | ✅ |
| Custom type key | `codec.type` with `typeKey` | ✅ |
| Type strategy NAME | `codec.type` with `strategy=NAME` | ✅ |
| Transient features | `codec.transient` | ✅ |
| SuperType serialization | `codec.supertype` | ✅ |
| Aspect parsing from EAnnotations | - | ✅ |
| Round-trip with annotations | - | ✅ |

#### Example Annotated Ecore

```xml
<eClassifiers xsi:type="ecore:EClass" name="Product">
  <!-- Custom ID key -->
  <eAnnotations source="codec.id">
    <details key="strategy" value="ID_FIELD"/>
    <details key="key" value="id"/>
  </eAnnotations>
  <!-- NAME type strategy instead of URI -->
  <eAnnotations source="codec.type">
    <details key="strategy" value="NAME"/>
    <details key="typeKey" value="type"/>
  </eAnnotations>
  ...
</eClassifiers>
```

### 5.4 Phase 2.2 Complete: MAPPED TypeStrategy ✅

**Date Completed:** 2025-12-17

The MAPPED TypeStrategy enables discriminator-based polymorphic serialization, where type is determined by a value in the content rather than a full URI.

#### Implemented Components

**TypeDiscriminatorRegistry (`org.eclipse.fennec.codec.metadata.type`):**
- `TypeDiscriminatorRegistry` - Single mapId-scoped registry for discriminator→EClass mappings
- Thread-safe bidirectional lookup (discriminator→EClass and EClass→discriminator)

**TypeDiscriminatorService (`org.eclipse.fennec.codec.metadata.type`):**
- Parent service holding multiple registries by mapId
- `fromMetadataService()` - Populates from MetadataService by scanning ClassCodecAspects
- `getEClassFromAny()` - Finds EClass across all registered mapIds

**CodecAspectProvider Updates:**
- Parses `codec.type.{mapId}` annotations (mapId-scoped)
- Automatically sets `strategy=MAPPED` for mapId-scoped annotations
- Extracts `typeDiscriminator` value for registration

**TypeDeserializationEntry Updates:**
- Uses `TypeDiscriminatorService` for discriminator-based resolution
- Discriminator lookup happens first, before strategy-based resolution
- Falls back to URI/NAME strategies if discriminator not found

**ConfigurationMerger Updates:**
- Builds `TypeDiscriminatorService` from MetadataService
- Passes service to `EffectiveCodecConfig`

#### Tested MAPPED Features

All tests in `CodecResourceMappedTypeTest`:

| Feature | Status |
|---------|--------|
| Parse MAPPED strategy from annotations | ✅ |
| Parse discriminator values from annotations | ✅ |
| TypeDiscriminatorService population | ✅ |
| Serialize with discriminator value | ✅ |
| Deserialize from discriminator value | ✅ |
| Round-trip single objects | ✅ |
| Polymorphic containers with mixed types | ✅ |

#### Example MAPPED Ecore Annotation

```xml
<!-- Base class defines mapId and type key -->
<eClassifiers xsi:type="ecore:EClass" name="Device" abstract="true">
  <eAnnotations source="codec.type.iot-sensors">
    <details key="typeKey" value="_type"/>
  </eAnnotations>
  ...
</eClassifiers>

<!-- Concrete class registers discriminator -->
<eClassifiers xsi:type="ecore:EClass" name="TemperatureSensor" eSuperTypes="#//Device">
  <eAnnotations source="codec.type.iot-sensors">
    <details key="typeDiscriminator" value="temp-sensor"/>
  </eAnnotations>
  ...
</eClassifiers>
```

**Resulting JSON:**
```json
{
  "_type": "temp-sensor",
  "deviceId": "device-001",
  "temperature": 25.5
}
```

### 5.5 Type Resolution Flow (Deserialization)

This section clearly documents how the deserializer determines the EClass for an object.

#### 5.5.1 Primary Type Resolution: `_type` Field

The default and primary way to determine type is via the `_type` field in JSON content:

```json
{
  "_type": "http://example.org/1.0#//Person",
  "name": "John"
}
```

The `_type` value can be:
- **Full URI** (e.g., `"http://example.org/1.0#//Person"`) → Resolved via EPackage registry
- **Discriminator value** (e.g., `"temp-sensor"`) → Resolved via `TypeDiscriminatorService` (MAPPED strategy)

This applies to **both root objects and nested/contained objects**.

#### 5.5.2 Fallback: CODEC_ROOT_OBJECT Hint

When `_type` is **missing** from the JSON content, the `CODEC_ROOT_OBJECT` load option provides the type hint:

```java
Map<String, Object> options = Map.of(
    CodecResource.CODEC_ROOT_OBJECT, PersonPackage.eINSTANCE.getPerson()
);
resource.load(inputStream, options);
```

The `CODEC_ROOT_OBJECT` value can be:
- **EClass** → Used directly as the object's type
- **String** → Interpreted as EClass URI, resolved to EClass

**Important:** This hint is only for the **root object**. Nested objects must have their type information in the content (either `_type` field or via featurePath).

#### 5.5.3 Nested Objects Type Resolution

For nested/contained objects:
1. **With `_type`**: Parse and resolve (URI or MAPPED discriminator)
2. **Without `_type`**: Use the EReference's `eType` to determine expected type

Example: A `DeviceContainer` with `devices: Device[*]` reference. Each device in the array should have `_type` to determine the concrete type (e.g., `"temp-sensor"` → `TemperatureSensor`).

#### 5.5.4 MAPPED Strategy with featurePath

For MAPPED strategy, type can also be determined from a content field instead of `_type`:

```
<eAnnotations source="codec.type.lorawan-uplink">
  <details key="typeKeyFeaturePath" value="info.profileName"/>
</eAnnotations>
```

This tells the deserializer to:
1. Scan the content to find `info.profileName` value (e.g., `"temperature-profile"`)
2. Use `TypeDiscriminatorService.getEClassFromAny("temperature-profile")` to resolve EClass
3. Requires buffering since we may need to scan ahead before knowing the type

**featurePath is ONLY applicable for MAPPED strategy** - it's an alternative location for the discriminator value.

#### 5.5.5 Resolution Priority Summary

| Priority | Source | Applies To |
|----------|--------|------------|
| 1 | `_type` field in content | Root + Nested |
| 2 | `typeKeyFeaturePath` content (MAPPED) | Root + Nested (if configured) |
| 3 | `CODEC_ROOT_OBJECT` hint | Root only |
| 4 | EReference `eType` | Nested only (fallback) |

#### 5.5.6 Internal Implementation: ContextHelper and EXPECTED_TYPE

The type hint is passed through the deserialization tree using Jackson context attributes.

**Key Class: `ContextHelper` (`org.eclipse.fennec.codec.v2.context.ContextHelper`)**

```java
// Get expected type (returns null if not set, throws if wrong type)
EClass hint = ContextHelper.getExpectedType(ctxt);

// Set expected type (throws if null)
ContextHelper.setExpectedType(ctxt, eClass);

// Clear expected type
ContextHelper.clearExpectedType(ctxt);
```

**Context Attributes:**

| Attribute | Type | Description |
|-----------|------|-------------|
| `CODEC_EXPECTED_TYPE` | `EClass` | Type hint for current object |
| `CODEC_UNRESOLVED_REFERENCES` | `List<UnresolvedReference>` | Collector for cross-references |

**Contract for `EXPECTED_TYPE`:**
- **MUST** always be of type `EClass` (never a URI string)
- URI resolution happens **before** setting the attribute
- If non-`EClass` value is set → `IllegalStateException`

**Flow:**
1. `CodecResource.doLoad()`: Resolves `CODEC_ROOT_OBJECT` → sets `EXPECTED_TYPE`
2. `CodecEObjectDeserializer`: Uses `ContextHelper.getExpectedType(ctxt)`
3. `ReferenceDeserializationEntry`: Sets `reference.getEReferenceType()` as `EXPECTED_TYPE` for nested objects

This enables:
- **Concrete reference types**: No `_type` needed in nested objects
- **Abstract reference types**: `_type` required to specify concrete subtype

See **Spec Section 15.7** for detailed flow diagrams.

### 5.6 Implementation Status

1. ~~**EAnnotation-based configuration**~~ ✅ Complete

2. **TypeStrategies**
   - ✅ `NAME` - Simple class name
   - ✅ `URI` - Full EClass URI (default)
   - ✅ `MAPPED` - Discriminator-based type resolution
   - ✅ `MAPPED` with featurePath - Type from content field
   - `SCHEMA_AND_TYPE` - Separate schema/type fields (not started)
   - `STRUCTURED` - Nested object format (not started)
   - `NUMERIC` - Classifier IDs (not started)

3. ✅ **Smart Compression** - Omit `_type` when instance type == reference type

4. ✅ **Array Root Objects** - Multiple root objects serialize as JSON array

5. **Pending Features** (see Section 11 for details)
   - Cross-resource references
   - Custom value readers/writers
   - SuperType serialization
   - OSGi integration

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

## 9. Completed Work (Phase 2.3)

### 9.1 Cross-Package Dynamic Registration Tests ✅

**Completed:** Tests for MAPPED type discriminators across multiple EPackages.

**Files Created:**
- `test-mapped-type-ext.ecore` - Extension package with PressureSensor and LightSensor
- `CodecResourceCrossPackageTest.java` - Comprehensive tests

**Key Learning:** Cross-package ecore references must use namespace URI (e.g., `http://test.example.org/mapped/1.0#//Device`) instead of relative paths for proper EMF resolution.

**Added to TypeDiscriminatorService:**
- `unregisterPackage(PackageMetadata)` - Removes discriminators for a package
- `unregisterClass(ClassMetadata)` - Removes discriminator for a single class

**Test Coverage:**
- Both packages registered - all discriminators available
- Only base package registered - extension discriminators not available
- Dynamic registration/unregistration
- Re-registration restores discriminators

### 9.2 FeaturePath-Based Type Discrimination Tests ✅ (Test Framework Ready)

**Completed:** Test model and test class structure for featurePath-based type discrimination.

**Files Created:**
- `test-featurepath-type.ecore` - Test model with `typeKeyFeaturePath` annotations
- `CodecResourceFeaturePathTypeTest.java` - Test class with disabled implementation tests

**Test Model Demonstrates:**
- **Nested path:** `info.profileName` for LoRaWAN-style messages
- **Single-level path:** `messageType` for simple messages

**Annotation Parsing Tests Pass:** Discriminators are registered correctly from `typeKeyFeaturePath` annotations.

---

## 10. Completed Work (Phase 2.4)

### 10.1 Smart Compression Implementation ✅

**Completed:** 2025-12-29

Smart compression is now fully implemented as a global feature that suppresses redundant `_type` information.

**Behavior:**
| Smart Compression | Instance Type == Reference Type | Action |
|-------------------|--------------------------------|--------|
| ON | Yes | Omit `_type` (can be inferred from reference declaration) |
| ON | No | Write `_type` (polymorphic - concrete type differs) |
| OFF | Yes/No | Always write `_type` |

**Configuration:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .smartCompression(true)  // Default is OFF per spec
    .build();
```

**Key Files:**
- `CodecConfiguration.java` - `smartCompression` setting
- `EffectiveCodecConfig.java` - Runtime access
- `ReferenceSerializationEntry.java` - Type suppression logic
- `TypeSerializationEntry.java` - Context-aware `shouldSerialize()`
- `ContextHelper.java` - `SUPPRESS_TYPE` context flag

**Tests:** `CodecResourceSmartCompressionTest.java` - 11 tests covering ON/OFF behavior

### 10.2 FeaturePath Deserialization Implementation ✅

**Completed:** 2025-12-29

FeaturePath-based type resolution is now fully implemented, including hint-free deserialization.

**Key Components:**
- `FeaturePathTypeResolver.java` - TokenBuffer-based content scanning
- `CodecTokenBuffer.java` - Buffers JSON tokens for replay after type discovery
- `CodecEObjectDeserializer.java` - Integrates featurePath resolution

**Features:**
- Nested paths: `info.profileName` navigates into nested objects
- Single-level paths: `messageType` at root level
- Hint-free: When no `CODEC_ROOT_OBJECT` is provided, searches all registered discriminatorPaths

**Tests:** All tests in `CodecResourceFeaturePathTypeTest.java` now pass (previously 3 were disabled)

### 10.3 FeaturePath Serialization ✅

When `typeDiscriminatorPath` is configured:
- Root object: `_type` is omitted (discriminator value is in content)
- Nested objects with smart compression ON: `_type` suppressed when instance == reference type

### 10.4 Array Root Objects ✅

**Completed:** 2025-12-29

EMF Resources support multiple root elements. The codec now properly handles:

**Serialization:**
- Single root object → JSON object `{...}`
- Multiple root objects → JSON array `[{...}, {...}]`

**Deserialization:**
- JSON object → Single root object in `resource.getContents()`
- JSON array → Multiple root objects in `resource.getContents()`
- Empty array `[]` → Empty contents

**Implementation:** `CodecResource.doLoad()` peeks at first token (START_ARRAY vs START_OBJECT) and loops through array elements when needed.

**Tests:** `CodecResourceArrayRootTest.java` - 10 tests covering serialization, deserialization, and round-trip

---

## 10.5 Expand Reference Serialization ✅

**Completed:** 2026-01-07

Non-containment references can now be serialized inline (expanded) instead of as proxy references.

**Configuration:**
```java
// Expand ALL non-containment references
CodecConfiguration.builder()
    .expandGlobal(true)
    .build();

// Expand SPECIFIC references
CodecConfiguration.builder()
    .expand(PersonPackage.eINSTANCE.getPerson_Employer())
    .expand("manager")
    .build();
```

**Key Behavior:**
- Expanded objects are serialized WITHOUT `_ref` field
- This distinguishes them from proxy references during deserialization:
  - With `_ref` → proxy (resolve via URI)
  - Without `_ref` → orphan object (fully deserialized, not contained)
- Proxy objects are NOT expanded (cannot expand what isn't resolved)
- Bi-directional references are skipped by default to prevent cycles

**Implementation Limitations:**
- `expandDepth` only supports depth=1 (no nested expansion)

**Key Files:**
- `CodecConfiguration.java` - expand settings
- `EffectiveCodecConfig.java` - runtime access
- `ReferenceSerializationEntry.java` - expansion logic (serialization)
- `ReferenceDeserializationEntry.java` - orphan/projection detection (deserialization)
- `ExpandReferenceTest.java` - full round-trip tests

### 10.6 Cross-Document Containment ⚠️ Partial

**Status:** Serialization works, deserialization creates proxies

Cross-document containment occurs when a contained object is stored in a different EMF resource. The serializer correctly detects this and serializes as `_ref`:

```json
{
  "address": {
    "_type": "http://example.org/address/1.0#//Address",
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

**Not Implemented:** Deserialization resolution. Cross-document containments are deserialized as proxy objects that require manual resolution via the ResourceSet.

### 10.7 Enum Serialization ✅

**Completed:** 2026-01-07

Enum values can be serialized using different strategies:

| Strategy | Output Example |
|----------|----------------|
| `LITERAL` | `"status": "ACTIVE"` (uses `EEnumLiteral.getLiteral()`) |
| `NAME` | `"status": "Active"` (uses `EEnumLiteral.getName()`) |
| `VALUE` | `"status": 1` (uses `EEnumLiteral.getValue()`) |

**Configuration:**
```java
CodecConfiguration.builder()
    .enumSerialization(EnumSerializationStrategy.LITERAL)  // Default
    .build();
```

**Key Files:**
- `CodecConfiguration.java` - `enumSerialization` setting
- `AttributeSerializationEntry.java` - enum writing logic
- `AttributeDeserializationEntry.java` - enum reading logic
- `EnumSerializationTest.java` - round-trip tests

### 10.8 Extended Metadata Names ✅

**Completed:** 2026-01-08

Feature JSON keys can use names from XSD ExtendedMetaData annotations instead of EMF feature names.

**Configuration:**
```java
CodecConfiguration.builder()
    .useNamesFromExtendedMetaData(true)  // Default: false
    .build();
```

**Resolution Priority:**
1. Explicit codec annotation `key` (highest priority)
2. ExtendedMetaData `name` (when enabled)
3. Feature name (default fallback)

**Key Changes:**
- Added `extendedMetaDataName` to `FeatureMetadata` in metadata.ecore
- `MetadataServiceImpl` populates the field on package registration
- `ConfigurationMerger.resolveFeatureKey()` uses the pre-computed value

**Key Files:**
- `FeatureMetadata` - `extendedMetaDataName` attribute
- `MetadataServiceImpl.java` - extraction on registration
- `ConfigurationMerger.java` - key resolution with ExtendedMetaData support
- `AnnotationHelper.java` - fallback for direct annotation lookup
- `ExtendedMetaDataTest.java` - round-trip tests

### 10.9 SuperType Serialization ✅

**Completed:** 2026-01-08

SuperType information can be serialized as PLAIN format (array of URIs or single string).

**Configuration:**
```java
CodecConfiguration.builder()
    .serializeSuperTypes(true)        // Enable supertype serialization
    .serializeAllSuperTypes(true)     // ALL selection (false = SINGLE)
    .superTypeKey("_supertype")       // Custom key
    .build();
```

**Selection Modes:**
- `ALL` - All supertypes in hierarchy (excluding EMF base types)
- `ALL_EMF` - All supertypes including EMF base types
- `SINGLE` - Only direct supertype

**Key Files:**
- `SuperTypeSerializationEntry.java` - serialization logic
- `EffectiveSuperTypeConfig.java` - pre-merged config
- `CodecResourceSuperTypeTest.java` - integration tests

**Note:** STRUCTURED format for SuperType is pending (part of Priority 1). ID STRUCTURED format is complete (see Section 10.11).

### 10.10 Global Feature Ignore List ✅

**Completed:** 2026-01-08

A codec-wide list of feature names to skip during both serialization and deserialization.

**Configuration:**
```java
// Varargs method:
CodecConfiguration.builder()
    .globalIgnoreFeatures("createdAt", "updatedAt", "version")
    .build();

// Or add one at a time:
CodecConfiguration.builder()
    .globalIgnore("createdAt")
    .globalIgnore("updatedAt")
    .build();
```

**Behavior:**
| Operation | Behavior |
|-----------|----------|
| Serialization | Feature is omitted from output |
| Deserialization | Feature value in JSON is ignored (not set on EObject) |

**Use Cases:**
- Skip audit fields across all EClasses
- API versioning (V1 ignores V2 fields)
- Temporary exclusion without model changes

**Key Files:**
- `CodecConfiguration.java` - `globalIgnore()` builder method
- `EffectiveCodecConfig.java` - `isGloballyIgnored()` check
- `ConfigurationMerger.java` - `resolveFeatureSerialize()` checks global ignore
- `GlobalIgnoreFeatureTest.java` - integration tests

### 10.11 ID STRUCTURED Format ✅

**Completed:** 2026-01-08

ID serialization now supports STRUCTURED format where ID is serialized as a nested object with individual fields.

**Configuration:**
```java
CodecConfiguration.builder()
    .idFormat(SerializationFormat.STRUCTURED)
    .idFeatures(List.of("firstName", "lastName", "sequence"))
    .idSeparator("-")  // Used when combining values for EIDAttribute
    .idSerializeSeparator(true)  // Include separator in JSON (default: true)
    .idSeparatorKey("_separator")  // Key for separator field (default: "_separator")
    .idKeyMode(IdKeyMode.ID_ONLY)  // ID_ONLY, BOTH, FEATURE_ONLY
    .build();
```

**Serialization Formats:**

| Format | JSON Output |
|--------|-------------|
| `PLAIN` (single ID) | `"_id": "john-123"` |
| `PLAIN` (multiple features) | `"_id": "John-Doe-42"` (separator-joined) |
| `STRUCTURED` (serializeSeparator=true) | `"_id": {"_separator": "-", "firstName": "John", ...}` |
| `STRUCTURED` (serializeSeparator=false) | `"_id": {"firstName": "John", ...}` |

**Separator Serialization Options:**
- `idSerializeSeparator(true)` (default) - Include separator in JSON, no config needed for deserialization
- `idSerializeSeparator(false)` - Compact JSON, separator must be configured for deserialization
- `idSeparatorKey("_separator")` (default) - Customize the JSON key for separator

**IdKeyMode Values:**
- `ID_ONLY` - Only `_id` key is serialized (default)
- `BOTH` - Both `_id` and individual features serialized
- `FEATURE_ONLY` - No `_id`, individual features only

**Deserialization Implementation:**

Uses TokenBuffer approach for STRUCTURED format:
1. Capture nested object content into TokenBuffer
2. Parse buffered content to extract field values (including separator if present)
3. Set feature values on EObject
4. Optionally set combined value on EIDAttribute using separator (from JSON or config)

**Key Files:**
- `IdSerializationEntry.java` - STRUCTURED format serialization
- `IdDeserializationEntry.java` - TokenBuffer-based STRUCTURED deserialization
- `CodecEObjectDeserializer.java` - Deferred property replay for ID entries
- `CodecResourceIdTest.java` - 27 comprehensive tests

**Test Coverage:**
| Feature | Status |
|---------|--------|
| PLAIN single ID | ✅ |
| PLAIN multiple features with separator | ✅ |
| PLAIN custom separator | ✅ |
| STRUCTURED single ID | ✅ |
| STRUCTURED multiple features (with separator) | ✅ |
| STRUCTURED multiple features (without separator) | ✅ |
| STRUCTURED custom separator key | ✅ |
| STRUCTURED custom ID key | ✅ |
| IdKeyMode.ID_ONLY | ✅ |
| IdKeyMode.BOTH | ✅ |
| IdKeyMode.FEATURE_ONLY | ✅ |
| Round-trip PLAIN | ✅ |
| Round-trip STRUCTURED (with/without separator) | ✅ |

---

## 11. Pending Work (Priority Order)

### 11.1 Additional TypeStrategies (Priority 1 - Next)

- `SCHEMA_AND_TYPE` - Separate schema/type fields
- `STRUCTURED` - Nested object format (applies to type, supertype) - **ID STRUCTURED is done (see 10.11)**
- `NUMERIC` - Classifier IDs

### 11.2 Error Handling Improvements (Priority 2)

- Better diagnostics and recovery
- EMF Resource error/warning collection

### 11.3 Custom Value Readers/Writers (Priority 3)

- `CodecValueRegistry` integration
- Support for `valueWriterName`/`valueReaderName` annotations

### 11.4 OSGi Integration (Priority 4)

- Create ResourceFactory for OSGi registration
- Test with OSGi runtime

### Deferred Items

The following items are lower priority and deferred for later:

| Item | Description |
|------|-------------|
| **Expand Depth > 1** | Nested expansion (expandDepth > 1) |
| **Cross-Resource References** | ResourceSet-based resolution |
| **Smart compression (same-schema)** | Omit schema when same as context |
| **Field ordering options** | ALPHABETICAL, DECLARATION order |
| **Annotation inheritance** | Inherit annotations from supertypes |

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
