# Codec Metadata Architecture

## Overview

`org.eclipse.fennec.codec.metadata` provides codec-specific aspects for the Model Metadata Service. It extends `org.eclipse.fennec.model.metadata` with serialization configuration defined entirely in EMF (`codec.ecore`).

**Purpose:** Define how EMF model elements (EClasses, EStructuralFeatures) are serialized/deserialized to various formats (JSON, XML, CSV, MongoDB, etc.).

---

## Dependencies

```
org.eclipse.fennec.codec.metadata
         │
         └──→ org.eclipse.fennec.model.metadata (metadata.ecore - base infrastructure)
```

**No dependency on codec.v2** - this is purely metadata, not serialization logic.

---

## Project Structure

```
org.eclipse.fennec.codec.metadata/
├── model/
│   └── codec.ecore                    (EMF model for codec aspects)
│
├── src/
│   └── org/eclipse/fennec/codec/metadata/
│       └── provider/
│           ├── CodecAnnotationConstants.java   (annotation keys and defaults)
│           └── package-info.java
│
├── src-gen/                           (EMF-generated code)
│   └── org/eclipse/fennec/codec/metadata/model/codec/
│       ├── CodecPackage.java
│       ├── CodecFactory.java
│       ├── ClassCodecAspect.java
│       ├── FeatureCodecAspect.java
│       ├── ReferenceCodecAspect.java
│       ├── TypeSerializationConfig.java
│       ├── IdSerializationConfig.java
│       ├── ReferenceSerializationConfig.java
│       ├── SuperTypeSerializationConfig.java
│       ├── FeatureSerializationConfig.java
│       ├── CodecConfig.java
│       └── impl/                      (implementation classes)
│
└── test/
    └── org/eclipse/fennec/codec/metadata/
        └── ecore/
            └── EcoreTestHelper.java   (test utilities)
```

---

## EMF Model: codec.ecore

The codec aspects and configuration classes are defined in `codec.ecore`, which extends `metadata.ecore`.

### Aspect Classes

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              codec.ecore                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  From metadata.ecore:          Extended in codec.ecore:                      │
│                                                                              │
│  ┌─────────────┐               ┌────────────────────┐                       │
│  │ ClassAspect │◄──────────────│ ClassCodecAspect   │                       │
│  │ (abstract)  │               │                    │                       │
│  └─────────────┘               │ - typeConfig       │                       │
│                                │ - idConfig         │                       │
│                                │ - superTypeConfig  │                       │
│                                │ - inheritFromParent│                       │
│                                │ - discriminatorVal │                       │
│                                └────────────────────┘                       │
│                                                                              │
│  ┌──────────────┐              ┌────────────────────┐                       │
│  │FeatureAspect │◄─────────────│ FeatureCodecAspect │                       │
│  │ (abstract)   │              │                    │                       │
│  └──────────────┘              │ - effectiveKey     │                       │
│                                │ - serialize        │                       │
│                                │ - serializeNull    │                       │
│                                │ - serializeEmpty   │                       │
│                                │ - serializeDefaults│                       │
│                                │ - valueWriterName  │                       │
│                                │ - valueReaderName  │                       │
│                                └─────────┬──────────┘                       │
│                                          │                                  │
│                                          │ extends                          │
│                                          ▼                                  │
│                                ┌────────────────────┐                       │
│                                │ReferenceCodecAspect│                       │
│                                │                    │                       │
│                                │ - referenceConfig  │                       │
│                                │ - typeConfig       │                       │
│                                │ - inheritTypeFrom  │                       │
│                                │   Target           │                       │
│                                │ - expand           │                       │
│                                └────────────────────┘                       │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Configuration Classes

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         Configuration Hierarchy                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  From metadata.ecore:          Extended in codec.ecore:                      │
│                                                                              │
│  ┌───────────────┐             ┌─────────────────────────┐                  │
│  │ BaseTypeConfig│◄────────────│ TypeSerializationConfig │                  │
│  │               │             │                         │                  │
│  │ - strategy    │             │ - discriminatorPath     │                  │
│  │ - include     │             │ - discriminatorValue    │                  │
│  │ - typeKey     │             └─────────────────────────┘                  │
│  │ - schemaKey   │                                                          │
│  │ - nameKey     │                                                          │
│  └───────────────┘                                                          │
│                                                                              │
│  ┌───────────────┐             ┌─────────────────────────┐                  │
│  │ BaseIdConfig  │◄────────────│ IdSerializationConfig   │                  │
│  │               │             │                         │                  │
│  │ - strategy    │             │ - idFeatures            │                  │
│  │ - keyMode     │             │ - idValueWriterName     │                  │
│  │ - format      │             │ - idValueReaderName     │                  │
│  │ - idKey       │             └─────────────────────────┘                  │
│  │ - separator   │                                                          │
│  └───────────────┘                                                          │
│                                                                              │
│  ┌─────────────────┐           ┌───────────────────────────┐                │
│  │BaseReferenceConf│◄──────────│ReferenceSerializationConf │                │
│  │                 │           │                           │                │
│  │ - format        │           │ - includeType             │                │
│  │ - typeKey       │           │ - expand                  │                │
│  │ - refKey        │           └───────────────────────────┘                │
│  └─────────────────┘                                                        │
│                                                                              │
│  ┌─────────────────┐           ┌───────────────────────────┐                │
│  │BaseSuperTypeConf│◄──────────│SuperTypeSerializationConf │                │
│  │                 │           │                           │                │
│  │ - enabled       │           │ - useSmartCompression     │                │
│  │ - selection     │           └───────────────────────────┘                │
│  │ - format        │                                                        │
│  │ - superTypesKey │                                                        │
│  └─────────────────┘                                                        │
│                                                                              │
│  ┌─────────────────┐           ┌───────────────────────────┐                │
│  │BaseFeatureConfig│◄──────────│FeatureSerializationConfig │                │
│  │                 │           │                           │                │
│  │ - key           │           │ - featureName             │                │
│  │ - serialize     │           │ - valueWriterName         │                │
│  │ - serializeNull │           │ - valueReaderName         │                │
│  │ - serializeEmpty│           │ - expand                  │                │
│  │ - serializeDef  │           │ - referenceConfig         │                │
│  └─────────────────┘           │ - typeConfig              │                │
│                                └───────────────────────────┘                │
└─────────────────────────────────────────────────────────────────────────────┘
```

### CodecConfig (Root Configuration)

```
CodecConfig
  - format: SerializationFormat = PLAIN
  - useNumericIds: boolean = false
  - typeConfig: TypeSerializationConfig (containment)
  - containmentTypeConfig: TypeSerializationConfig (containment)
  - referenceTypeConfig: TypeSerializationConfig (containment)
  - idConfig: IdSerializationConfig (containment)
  - referenceConfig: ReferenceSerializationConfig (containment)
  - superTypeConfig: SuperTypeSerializationConfig (containment)
  - featureConfigs: FeatureSerializationConfig[*] (containment)
  - expand: boolean = false
  - expandDepth: int = 1
  - expandIgnoreBidirectional: boolean = true
  - serializeNull: boolean = false
  - serializeEmpty: boolean = false
  - serializeDefaults: boolean = false
```

---

## EAnnotation Mapping

### Annotation Source

All codec annotations use source: `http://eclipse.org/fennec/codec`

### Annotation Markers and Detail Keys

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

### Example EAnnotation → Aspect

**Ecore:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.id"/>
  </eAnnotations>
  <eAnnotations source="codec.id">
    <details key="strategy" value="ID_FIELD"/>
    <details key="key" value="_id"/>
  </eAnnotations>
  <eAnnotations source="codec.type">
    <details key="strategy" value="URI"/>
    <details key="typeKey" value="_type"/>
  </eAnnotations>
</eClassifiers>
```

**Resolved Aspect (EMF objects):**
```java
ClassCodecAspect aspect = ...;  // from ClassMetadata.getAspects()

// ID config
IdSerializationConfig idConfig = aspect.getIdConfig();
idConfig.getStrategy();     // IdStrategy.ID_FIELD
idConfig.getIdKey();        // "_id"

// Type config
TypeSerializationConfig typeConfig = aspect.getTypeConfig();
typeConfig.getStrategy();   // TypeStrategy.URI
typeConfig.getTypeKey();    // "_type"
```

---

## Built-in Defaults (from spec)

Defaults are defined directly in the Ecore model via `defaultValueLiteral`:

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
strategy = ID_FIELD
idKey = "_id"
separator = "-"
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

## CodecAnnotationConstants

The `CodecAnnotationConstants` class provides:
- Annotation source URIs
- Detail key constants
- Strategy value constants
- Helper methods for parsing

```java
public final class CodecAnnotationConstants {
    // Annotation sources
    public static final String CODEC_ID = "codec.id";
    public static final String CODEC_TYPE = "codec.type";
    public static final String CODEC_REFERENCE = "codec.reference";
    public static final String CODEC_SUPERTYPE = "codec.supertype";
    public static final String CODEC_TRANSIENT = "codec.transient";
    public static final String CODEC_INHERIT = "codec.inherit";

    // Detail keys
    public static final String KEY_STRATEGY = "strategy";
    public static final String KEY_KEY = "key";
    public static final String KEY_TYPE_KEY = "typeKey";
    public static final String KEY_INCLUDE = "include";
    // ... more keys

    // Strategy values
    public static final String STRATEGY_URI = "URI";
    public static final String STRATEGY_NAME = "NAME";
    public static final String STRATEGY_ID_FIELD = "ID_FIELD";
    public static final String STRATEGY_COMBINED = "COMBINED";
    // ... more strategies

    // Helper methods
    public static boolean isTypeMapAnnotation(String source);
    public static String extractMapId(String source);
    public static String extractStaticDiscriminatorValue(String key);
}
```

---

## Annotation Parser (To Be Implemented)

The annotation parser will:
1. Read EAnnotations from EPackage/EClass/EStructuralFeature
2. Create EMF aspect objects (ClassCodecAspect, FeatureCodecAspect, etc.)
3. Apply defaults from Ecore defaultValueLiteral when annotations are missing
4. Handle inheritance (codec.inherit annotation)

```java
public interface CodecAspectBuilder {

    /**
     * Build ClassCodecAspect from EClass annotations.
     */
    ClassCodecAspect buildClassAspect(EClass eClass);

    /**
     * Build FeatureCodecAspect from EStructuralFeature annotations.
     */
    FeatureCodecAspect buildFeatureAspect(EStructuralFeature feature);

    /**
     * Build ReferenceCodecAspect from EReference annotations.
     */
    ReferenceCodecAspect buildReferenceAspect(EReference reference);
}
```

---

## Usage with MetadataService

```java
// MetadataService registers an EPackage
MetadataService service = ...;
PackageMetadata pkgMeta = service.registerPackage(PersonPackage.eINSTANCE);

// Get class metadata
ClassMetadata classMeta = service.getClassMetadata(PersonPackage.Literals.PERSON).get();

// Get codec aspect (EMF object)
ClassCodecAspect codecAspect = classMeta.getAspects().stream()
    .filter(ClassCodecAspect.class::isInstance)
    .map(ClassCodecAspect.class::cast)
    .findFirst()
    .orElse(null);

// Access type-safe configuration
TypeSerializationConfig typeConfig = codecAspect.getTypeConfig();
TypeStrategy strategy = typeConfig.getStrategy();  // TypeStrategy.URI
String typeKey = typeConfig.getTypeKey();          // "_type"
boolean include = typeConfig.isInclude();          // true

// Feature-level configuration
FeatureMetadata featureMeta = service.getFeatureMetadata(
    PersonPackage.Literals.PERSON__FIRST_NAME
).get();

FeatureCodecAspect featureAspect = featureMeta.getAspects().stream()
    .filter(FeatureCodecAspect.class::isInstance)
    .map(FeatureCodecAspect.class::cast)
    .findFirst()
    .orElse(null);

String effectiveKey = featureAspect.getEffectiveKey();  // "firstName" or custom
boolean serialize = featureAspect.isSerialize();         // true
```

---

## Migration from V1 (codec.info)

| V1 Class | V2 EMF Class | Notes |
|----------|--------------|-------|
| `EClassCodecInfo` | `ClassCodecAspect` | Now EMF-generated |
| `FeatureCodecInfo` | `FeatureCodecAspect` | Now EMF-generated |
| `TypeInfo` | `TypeSerializationConfig` | Extends BaseTypeConfig |
| `IdentityInfo` | `IdSerializationConfig` | Extends BaseIdConfig |
| `SuperTypeInfo` | `SuperTypeSerializationConfig` | Extends BaseSuperTypeConfig |
| `TypeStrategy` enum | `TypeStrategy` EEnum | In metadata.ecore |
| `IdStrategy` enum | `IdStrategy` EEnum | In metadata.ecore |
| `CodecModelInfo` | `MetadataService` | Service-based lookup |

---

## Next Steps

1. **Implement CodecAspectBuilder** - Parse EAnnotations, create EMF aspects
2. **Integrate with MetadataService** - Register as AspectProvider
3. **Write Tests** - Create test Ecore models with annotations, verify parsing
4. **Implement CodecValueReader/Writer** - Type resolution, custom transformations
