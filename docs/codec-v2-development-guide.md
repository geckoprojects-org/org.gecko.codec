# Codec V2 Development Guide

This document provides context for continuing codec.v2 development across sessions. It captures the goals, current state, and links to detailed architecture documentation.

**Last Updated:** 2025-12-17

---

## 1. Project Overview

We are building **codec.v2**, a new EMF serialization codec based on the specification document `codec-v2-serialization-spec.md`. The goal is to create a clean, well-structured implementation that follows the configuration hierarchy defined in the spec.

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
  - `EffectiveCodecConfig` - Root config with caching
  - `EffectiveClassConfig`, `EffectiveFeatureConfig`
  - `EffectiveIdConfig`, `EffectiveTypeConfig`, `EffectiveSuperTypeConfig`
- `ConfigurationMerger` - Single point for configuration resolution

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

### 5.5 Next Steps (Phase 2+)

1. ~~**EAnnotation-based configuration**~~ ✅ Complete

2. **Additional TypeStrategies** (partially done)
   - ✅ `NAME` - Simple class name
   - ✅ `MAPPED` - Discriminator-based type resolution
   - `SCHEMA_AND_TYPE` - Separate schema/type fields
   - `STRUCTURED` - Nested object format

3. **Cross-resource references**
   - Resolve references to objects in other resources
   - Support ResourceSet-based resolution

4. **Array root objects**
   - Support deserializing JSON arrays as multiple root objects

5. **Custom value readers/writers**
   - `CodecValueRegistry` integration
   - Support for `valueWriterName`/`valueReaderName` annotations

6. **SuperType serialization**
   - Implement when `superTypeConfig.enabled = true`

7. **OSGi integration**
   - Create ResourceFactory for OSGi registration
   - Test with OSGi runtime

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

## 9. Pending Work (Next Session)

### 9.1 Cross-Package Dynamic Registration Tests

Test that MAPPED type discriminators work across multiple EPackages and handle dynamic registration/unregistration correctly.

**User's Requirements:**
> "we have to test the dynamic, when the typemapping is going across multiple EPackages. when there is an Eclass in the new Epackage it can only serialized and de-serialized, when this package is registered. when its unregistered it shouldn't work anymore."

**Test Scenarios:**
1. **Cross-package discriminator registration**
   - Base class in Package A defines `codec.type.{mapId}` with `typeKeyFeaturePath`
   - Concrete classes in Package B extend base class and register discriminators
   - Test that serialization/deserialization works when both packages are registered

2. **Dynamic registration**
   - Register Package A first (base class)
   - Register Package B (concrete classes) - discriminators should become available
   - Verify serialization now works for concrete classes

3. **Dynamic unregistration**
   - Start with both packages registered and working
   - Unregister Package B from MetadataService
   - Verify that discriminators from Package B no longer resolve
   - Re-register Package B, verify it works again

4. **Edge cases**
   - Same discriminator value in different mapIds (should work)
   - Concurrent registration/unregistration (thread safety)
   - Registering concrete class before base class

### 9.2 FeaturePath-Based Type Discrimination Tests

Test `typeKeyFeaturePath` annotation where discriminator value is read from an existing feature in content (not a dedicated `_type` field).

**User's Requirements:**
> "furthermore we should also test the handling of the featurePath, so reading and writing the type data from an existing feature or key in the content."

**Annotation Example:**
```xml
<eAnnotations source="codec.type.lorawan-dynamic">
  <details key="typeKeyFeaturePath" value="deviceInfo.deviceProfileName"/>
</eAnnotations>
```

**Test Scenarios:**
1. **Simple feature path** - Single level path like `deviceType`
2. **Nested feature path** - Dot-notation path like `deviceInfo.deviceProfileName`
3. **Serialization** - Type discriminator should NOT be written as `_type`, value should come from the feature path
4. **Deserialization** - Read discriminator from feature path, resolve EClass via TypeDiscriminatorService

**Implementation Needed:**
- `TypeSerializationEntry` needs to check `discriminatorPath` and write to that path instead of `_type`
- `TypeDeserializationEntry` needs to read from `discriminatorPath` when strategy is MAPPED
- May need buffering/lookahead for deserialization when discriminator appears after other fields

### 9.3 Key Files for These Tests

- `TypeDiscriminatorService.java` - Already supports multi-mapId registries
- `TypeDiscriminatorRegistry.java` - Has `register()` and `unregister()` methods
- `CodecResourceMappedTypeTest.java` - Existing MAPPED tests to extend
- `test-mapped-type.ecore` - Test model to extend or create new test model

---

## 10. Session Continuity Tips

If context is lost:

1. Read this document first: `docs/codec-v2-development-guide.md`
2. Check spec for details: `docs/codec-v2-serialization-spec.md`
3. Review current TODO state (if available)
4. Examine recent git commits for context
5. Ask user for clarification if needed

The key insight: **MetadataService parses EAnnotations at EPackage registration time and creates pre-computed aspect objects that the codec uses at serialization time.**

**For MAPPED TypeStrategy:** All packages are registered with MetadataService before serialization/deserialization. When registering an EPackage, the MetadataService runs through codec aspects and updates the TypeDiscriminatorService. By the time deserialization happens, there's already a mapping from discriminator values (like "temp-sensor") to EClasses.
