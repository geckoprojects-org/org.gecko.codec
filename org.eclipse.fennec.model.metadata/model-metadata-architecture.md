# Architecture Specification: EMF Model Metadata Service

## 1. Executive Summary

This document outlines the architecture for a **Model Metadata Service**, a centralized runtime metadata registry for an EMF-based framework.

**The Goal:** To decouple technical concerns (Serialization/Codecs, ORM mapping, Historization strategies, Units of Measurement) from the core domain model (Ecore). Instead of relying on slow runtime parsing of `EAnnotations` or polluting the domain model with technical attributes, we establish a dedicated "Shadow Model" (Metadata Layer) that is computed once at startup.

**Implementation:** The metadata layer is itself an EMF model (`metadata.ecore`), enabling:
- Type-safe access to configuration
- Serialization/persistence of pre-computed metadata
- Extension via EMF inheritance (codec aspects, ORM aspects, etc.)

## 2. Problem Statement & Motivation

- **Performance:** Iterating over `EAnnotations` and parsing string-based details during high-frequency operations (like serialization or DB writes) is inefficient.
- **Separation of Concerns:** The domain model (`WeatherForecast`, `PVPlant`) should not contain hard-coded logic for SQL column names, JSON keys, or InfluxDB retention policies.
- **External Models:** We often use third-party Ecore models (e.g., Geo-Standards) where we cannot add `EAnnotations` physically. We need a way to "attach" metadata externally.
- **Polyglot Persistence:** A single `EAttribute` (e.g., `temperature`) might need to be persisted as a current value in SQL (ORM) AND as a time-series stream in OGC SensorThings API.

## 3. Core Architecture: EMF-Based Aspect Pattern

The architecture follows a **Feature-Aspect pattern** implemented entirely in EMF. The `MetadataService` acts as a repository where every registered `EPackage` is wrapped by a `PackageMetadata` object containing `ClassMetadata` and `FeatureMetadata`, each with pluggable "Aspects".

### 3.1. EMF Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              metadata.ecore                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌──────────────────┐         ┌──────────────────┐                          │
│  │ MetadataRegistry │────────▶│ PackageMetadata  │                          │
│  │                  │ *       │                  │                          │
│  └──────────────────┘         │ - ePackage       │                          │
│                               │ - nsURI          │                          │
│                               └────────┬─────────┘                          │
│                                        │ contains *                         │
│                               ┌────────▼─────────┐                          │
│                               │  ClassMetadata   │                          │
│                               │                  │                          │
│                               │ - eClass         │                          │
│                               │ - name           │                          │
│                               │ - typeURI        │                          │
│                               │ - hasId          │                          │
│                               │ - superTypes     │                          │
│                               │ - aspects: ClassAspect[*]                   │
│                               └────────┬─────────┘                          │
│                                        │ contains *                         │
│                    ┌───────────────────┴───────────────────┐                │
│                    │                                       │                │
│           ┌────────▼─────────┐                 ┌───────────▼───────┐        │
│           │ AttributeMetadata│                 │ ReferenceMetadata │        │
│           │                  │                 │                   │        │
│           │ - eAttribute     │                 │ - eReference      │        │
│           │ - isId           │                 │ - containment     │        │
│           │ - defaultValue   │                 │ - targetClass     │        │
│           │ - aspects        │                 │ - oppositeMetadata│        │
│           └──────────────────┘                 │ - aspects         │        │
│                    ▲                           └───────────────────┘        │
│                    │                                    ▲                   │
│                    └─────────┬──────────────────────────┘                   │
│                              │                                              │
│                    ┌─────────▼─────────┐                                    │
│                    │  FeatureMetadata  │ (abstract)                         │
│                    │                   │                                    │
│                    │ - eFeature        │                                    │
│                    │ - name            │                                    │
│                    │ - featureID       │                                    │
│                    │ - aspects: FeatureAspect[*]                            │
│                    └───────────────────┘                                    │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                            ASPECT HIERARCHY                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│                        ┌─────────────┐                                       │
│                        │   Aspect    │ (abstract)                            │
│                        │             │                                       │
│                        │ - typeId    │                                       │
│                        └──────┬──────┘                                       │
│                               │                                              │
│              ┌────────────────┼────────────────┐                             │
│              │                                 │                             │
│     ┌────────▼────────┐              ┌────────▼────────┐                    │
│     │   ClassAspect   │ (abstract)   │  FeatureAspect  │ (abstract)         │
│     └────────┬────────┘              └────────┬────────┘                    │
│              │                                │                             │
│              │ extended by codec.ecore        │ extended by codec.ecore     │
│              ▼                                ▼                             │
│     ClassCodecAspect               FeatureCodecAspect                       │
│     ClassORMAspect                 FeatureORMAspect                         │
│     ClassHistoryAspect             FeatureHistoryAspect                     │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 3.2. Key EMF Classes

**MetadataRegistry** - Root container for all package metadata. Can be serialized for fast startup.

**PackageMetadata** - Wraps an `EPackage`:
- Reference to the original `EPackage`
- Cached `nsURI` for fast lookup
- Contains all `ClassMetadata` for classes in this package

**ClassMetadata** - Wraps an `EClass`:
- Reference to the original `EClass`
- Cached `name`, `typeURI`, `classifierID`
- Pre-resolved `superTypes` and `allSuperTypes`
- Pre-resolved `idFeatures` (features forming the ID)
- **Contains `aspects: ClassAspect[*]`** - pluggable configurations

**FeatureMetadata** (abstract) - Base for feature metadata:
- Reference to the original `EStructuralFeature`
- Cached `name`, `featureID`
- **Contains `aspects: FeatureAspect[*]`** - pluggable configurations

**AttributeMetadata** extends FeatureMetadata:
- Reference to `EAttribute`
- `isId` flag, `defaultValue`

**ReferenceMetadata** extends FeatureMetadata:
- Reference to `EReference`
- `containment` flag
- Pre-resolved `targetClassMetadata`, `oppositeMetadata`

### 3.3. Aspect Base Classes

**Aspect** (abstract) - Base for all aspects:
```java
// Generated from metadata.ecore
public interface Aspect extends EObject {
    String getTypeId();  // e.g., "codec", "orm", "history"
    void setTypeId(String value);
}
```

**ClassAspect** (abstract) extends Aspect - For class-level configuration
**FeatureAspect** (abstract) extends Aspect - For feature-level configuration

## 4. Aspect Definitions (Extension Points)

Aspects are defined in separate Ecore models that extend `metadata.ecore`. Each concern (codec, ORM, history) defines its own aspect types.

### 4.1. Codec Aspect (in `codec.ecore`)

Defines how an EClass/EFeature is serialized to JSON, XML, etc.

```
ClassCodecAspect extends ClassAspect
  - typeConfig: TypeSerializationConfig
  - idConfig: IdSerializationConfig
  - superTypeConfig: SuperTypeSerializationConfig
  - discriminatorValue: String
  - inheritFromParent: boolean

FeatureCodecAspect extends FeatureAspect
  - effectiveKey: String
  - serialize: boolean
  - serializeNull/Empty/Defaults: boolean
  - valueWriterName, valueReaderName: String

ReferenceCodecAspect extends FeatureCodecAspect
  - referenceConfig: ReferenceSerializationConfig
  - typeConfig: TypeSerializationConfig
  - expand: boolean
```

### 4.2. ORM Aspect (future)

Defines how an EFeature maps to a relational database column.

```
FeatureORMAspect extends FeatureAspect
  - columnName: String
  - sqlType: String
  - isNullable: boolean
  - converterClass: String
```

### 4.3. Unit Aspect (future)

Defines the physical unit of the data.

```
FeatureUnitAspect extends FeatureAspect
  - symbol: String (e.g., "W/m²")
  - category: String (e.g., "Irradiance")
  - siBaseUnit: String
  - conversionFactor: double
```

### 4.4. History Aspect (future)

Defines if and how a feature's value history should be preserved.

```
FeatureHistoryAspect extends FeatureAspect
  - strategy: HistoryStrategy (SNAPSHOT, TIME_SERIES)
  - standard: HistoryStandard (NONE, NGSI_LD, OGC_STA)
  - targetIdentifier: String
```

## 5. Configuration Classes (in metadata.ecore)

Base configuration classes define defaults directly in the Ecore model:

```
BaseTypeConfig (abstract)
  - strategy: TypeStrategy = URI
  - include: boolean = true
  - typeKey: String = "_type"
  - schemaKey: String = "schema"
  - nameKey: String = "name"

BaseIdConfig (abstract)
  - strategy: IdStrategy = ID_FIELD
  - keyMode: IdKeyMode = ID_ONLY
  - format: SerializationFormat = PLAIN
  - idKey: String = "_id"
  - separator: String = "-"

BaseReferenceConfig (abstract)
  - format: SerializationFormat = PLAIN
  - typeKey: String = "_type"
  - refKey: String = "_ref"

BaseSuperTypeConfig (abstract)
  - enabled: boolean = false
  - selection: SuperTypeSelection = ALL
  - format: SerializationFormat = PLAIN
  - superTypeKey: String = "_supertype"
```

Concrete config classes in `codec.ecore` extend these to add codec-specific fields.

## 6. Enums (in metadata.ecore)

```
SerializationFormat: PLAIN, STRUCTURED

TypeStrategy: NAME, CLASS, URI, MAPPED, STRUCTURED, SCHEMA_AND_TYPE, NUMERIC

IdStrategy: ID_FIELD, COMBINED, NONE

IdKeyMode: ID_ONLY, BOTH, FEATURE_ONLY

SuperTypeSelection: ALL, ALL_EMF, SINGLE, NONE
```

## 7. Resolution Strategy (The "Builder" Logic)

When `registerPackage(EPackage)` is called, the service builds the metadata using a **3-Layer Cascade**. The first source to provide a value wins.

1. **Level 1: External Configuration (Highest Priority)**
   - Source: A dedicated DSL (`.modelinfo` file) or Java Config class.
   - Benefit: Allows annotating third-party models without touching their Ecore.

2. **Level 2: EAnnotations (Standard EMF)**
   - Source: Standard `.ecore` file annotations.
   - Example: `source="http://eclipse.org/fennec/codec"`, `details="strategy=URI"`
   - Benefit: Keeps definitions close to the model.

3. **Level 3: Convention / Defaults (Fallback)**
   - Source: EMF default values in config classes.
   - Logic: Default values defined in `metadata.ecore` and `codec.ecore`.

## 8. Service API (metadata-api.ecore)

The service interfaces are defined in EMF (`metadata-api.ecore`), enabling type-safe operations and code generation.

### 8.1 MetadataService

Main service interface for metadata lookup and provider management:

```
MetadataService (interface)
  // Package operations
  +registerPackage(ePackage: EPackage): PackageMetadata
  +unregisterPackage(ePackage: EPackage): void
  +getPackageMetadata(nsURI: EString): PackageMetadata

  // Class lookup
  +getClassMetadata(eClass: EClass): ClassMetadata
  +getClassMetadataByURI(uri: EString): ClassMetadata
  +getClassMetadataByName(className: EString, nsURI: EString): ClassMetadata

  // Feature lookup
  +getFeatureMetadata(feature: EStructuralFeature): FeatureMetadata
  +getFeatureMetadataByURI(uri: EString): FeatureMetadata
  +getFeatureMetadataByName(featureName: EString, className: EString, nsURI: EString): FeatureMetadata
  +getFeatureMetadataFromClass(featureName: EString, classMetadata: ClassMetadata): FeatureMetadata

  // Aspect lookup (convenience)
  +getClassAspect(eClass: EClass, aspectTypeId: EString): ClassAspect
  +getFeatureAspect(feature: EStructuralFeature, aspectTypeId: EString): FeatureAspect

  // Registry access
  +getRegistry(): MetadataRegistry

  // Provider management (dynamic)
  +registerAspectProvider(provider: AspectProvider): void
  +unregisterAspectProvider(provider: AspectProvider): void
  +getAspectProviders(): EList<AspectProvider>
```

### 8.2 AspectProvider

Extension point for contributing aspects to metadata:

```
AspectProvider (interface)
  +getAspectTypeId(): EString                              // e.g., "codec", "orm", "history"
  +buildClassAspect(eClass: EClass): ClassAspect           // Build aspect for class
  +buildFeatureAspect(feature: EStructuralFeature): FeatureAspect  // Build for any feature
  +buildAttributeAspect(attribute: EAttribute): FeatureAspect      // Specific for attributes
  +buildReferenceAspect(reference: EReference): FeatureAspect      // Specific for references
```

**Provider Flow:**
1. `MetadataService.registerAspectProvider(codecProvider)` - Register provider
2. `MetadataService.registerPackage(MyPackage.eINSTANCE)` - Triggers all providers
3. For each EClass/EFeature, providers build aspects
4. Aspects are attached to ClassMetadata/FeatureMetadata

## 9. Usage Example

```java
// Get metadata service
MetadataService service = ...;

// Register package (triggers metadata computation)
PackageMetadata pkgMeta = service.registerPackage(MyPackage.eINSTANCE);

// Fast lookup - O(1), no EAnnotation parsing
ClassMetadata classMeta = service.getClassMetadata(MyPackage.Literals.PERSON).get();

// Get codec aspect (type-safe EMF object)
ClassCodecAspect codecAspect = classMeta.getAspects().stream()
    .filter(a -> a instanceof ClassCodecAspect)
    .map(ClassCodecAspect.class::cast)
    .findFirst()
    .orElse(null);

// Or use convenience method
ClassCodecAspect codecAspect = service.getClassAspect(
    MyPackage.Literals.PERSON,
    ClassCodecAspect.class
).orElse(null);

// Access pre-computed values
if (codecAspect != null) {
    TypeSerializationConfig typeConfig = codecAspect.getTypeConfig();
    TypeStrategy strategy = typeConfig.getStrategy();  // URI, NAME, etc.
    String typeKey = typeConfig.getTypeKey();          // "_type"

    IdSerializationConfig idConfig = codecAspect.getIdConfig();
    IdStrategy idStrategy = idConfig.getStrategy();    // ID_FIELD, COMBINED, NONE
}

// Feature-level lookup
FeatureMetadata featureMeta = service.getFeatureMetadata(
    MyPackage.Literals.PERSON__FIRST_NAME
).get();

FeatureCodecAspect featureAspect = featureMeta.getAspects().stream()
    .filter(a -> a instanceof FeatureCodecAspect)
    .map(FeatureCodecAspect.class::cast)
    .findFirst()
    .orElse(null);

String jsonKey = featureAspect.getEffectiveKey();  // "firstName" or custom
boolean serialize = featureAspect.isSerialize();    // true/false
```

## 10. Persistence & Caching

Since metadata is now an EMF model, it can be serialized:

```java
// Save computed metadata to XMI
Resource resource = resourceSet.createResource(URI.createURI("metadata.xmi"));
resource.getContents().add(service.getRegistry());
resource.save(Collections.emptyMap());

// Load pre-computed metadata at startup (skip EAnnotation parsing)
Resource resource = resourceSet.getResource(URI.createURI("metadata.xmi"), true);
MetadataRegistry registry = (MetadataRegistry) resource.getContents().get(0);
service.loadRegistry(registry);
```

## 11. Benefits of EMF-Based Approach

1. **Type Safety:** Generated Java classes with proper getters/setters
2. **Validation:** EMF validation framework can check constraints
3. **Serialization:** XMI, JSON, or custom format via EMF Resources
4. **Tooling:** Ecore editors, code generation, model transformation
5. **Extensibility:** New aspects via Ecore inheritance
6. **Introspection:** Full EMF reflective API available
7. **Performance:** Pre-computed metadata, O(1) lookups at runtime
