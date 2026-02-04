# EMF Configuration Model Proposal

[← Back to Annotation Reference](annotation-scope-reference.md)

---

## Overview

This document proposes using an **EMF-based model** for codec configuration instead of (or alongside) property maps. The configuration model would live in `org.eclipse.fennec.codec.v2` and provide:

1. **Persistence** - Save/load configurations as XMI files
2. **Immutability** - Effective configuration via `EcoreUtil.copy()`
3. **Type safety** - EMF-generated interfaces with proper types
4. **Code generation** - Generate builders, constants, and documentation from the model
5. **Template configurations** - Reusable base configurations at any hierarchy level

---

## Motivation

### Current Complexity

The annotation-scope-reference documents 50+ configuration properties across different scopes:

| Scope Level | Approximate Properties |
|-------------|------------------------|
| Global | ~30 properties |
| EClass | ~25 properties |
| EReference | ~15 properties |
| EAttribute | ~10 properties |

Maintaining builders, property constants, and documentation manually is error-prone.

### Benefits of EMF Model

| Benefit | Description |
|---------|-------------|
| **Single source of truth** | Model defines properties, types, documentation, validation |
| **Reduced human error** | No manual sync between constants, builders, docs |
| **Always up-to-date docs** | Generate markdown from model EAnnotations |
| **Type safety** | Builders generated with correct types |
| **Persistence for free** | XMI load/save via EMF |
| **Tooling** | EMF editors, validation, compare |
| **Decoupling** | `codec.v2` independent from `codec.metadata` aspects |

---

## Architecture

### Current Flow (with aspects)

```
EAnnotations → CodecAspectProvider → ClassCodecAspect/ReferenceCodecAspect
                                              ↓
                                     Serializer/Deserializer
```

### Proposed Flow (with EMF config model)

```
EAnnotations → CodecAspectProvider → Aspects (in codec.metadata)
                                              ↓
                                     Aspect → CodecConfiguration converter
                                              ↓
Property Maps ─────────────────────→ CodecConfiguration (EMF model in codec.v2)
                                              ↓
XMI Files ─────────────────────────→ (loaded directly)
                                              ↓
                                     Serializer/Deserializer
```

### Configuration Hierarchy with EMF Model

Any level in the source hierarchy can provide an EMF config object OR a property map:

```
Load/Save Options (property map) ──────────────────┐
        ↓                                          │
Resource config (EMF model OR map) ────────────────┤
        ↓                                          │ all merged into
ResourceFactory (EMF model OR map) ────────────────┤ effective config
        ↓                                          │ (immutable EMF object)
Jackson Module (EMF model OR map) ─────────────────┤
        ↓                                          │
EAnnotations → converted to EMF model ─────────────┤
        ↓                                          │
Built-in Defaults ─────────────────────────────────┘
                                                   ↓
                                    ════════════════════════════
                                    │ Effective Configuration  │
                                    │ (immutable EMF EObject)  │
                                    ════════════════════════════
```

**Key principle:** The EMF configuration at any level acts as a **template/base** that subsequent levels can still override.

---

## Proposed Model Structure

### Project Location

**`org.eclipse.fennec.codec.v2`** - The configuration model is specific to codec.v2 serialization behavior. This:
- Reduces coupling (codec.v2 doesn't need codec.metadata for configuration)
- Clean separation: `codec.metadata` = EAnnotation parsing, `codec.v2` = configuration + serialization

### Model Hierarchy

```
CodecConfiguration (root)
├── type: TypeConfiguration
├── id: IdConfiguration
├── superType: SuperTypeConfiguration
├── reference: ReferenceConfiguration
├── feature: FeatureConfiguration
├── classConfigs: EList<ClassCodecConfig>
├── referenceConfigs: EList<ReferenceCodecConfig>
└── attributeConfigs: EList<AttributeCodecConfig>

TypeConfiguration
├── strategy: TypeStrategy
├── format: SerializationFormat
├── key: EString [default: "_type"]
├── nameKey: EString [default: "type"]
├── schemaKey: EString [default: "schema"]
├── include: EBoolean [default: true]
├── scope: StrategyScope [default: ALL]
└── formatScope: StrategyScope [default: ALL]

IdConfiguration
├── strategy: IdStrategy
├── format: SerializationFormat
├── key: EString [default: "_id"]
├── features: EList<EString>
├── separator: EString [default: "-"]
├── keyMode: IdKeyMode
├── onTop: EBoolean [default: true]
├── serializeSeparator: EBoolean [default: true]
├── separatorKey: EString [default: "separator"]
├── scope: StrategyScope [default: ALL]
└── formatScope: StrategyScope [default: ALL]

SuperTypeConfiguration
├── serialize: EBoolean [default: false]
├── key: EString [default: "_superTypes"]
├── strategy: SuperTypeStrategy
├── asArray: EBoolean [default: true]
├── separator: EString [default: ","]
├── format: SerializationFormat
├── schemaKey: EString
└── nameKey: EString

ReferenceConfiguration
├── format: SerializationFormat
├── key: EString [default: "_ref"]
├── typeKey: EString [default: "_type"]
└── expand: EBoolean [default: false]

FeatureConfiguration
├── serialize: EBoolean [default: true]
├── serializeNull: EBoolean [default: false]
├── serializeEmpty: EBoolean [default: false]
├── serializeDefaults: EBoolean [default: false]
└── enumSerialization: EnumSerialization [default: LITERAL]

ClassCodecConfig
├── eClass: EClass [reference]
├── type: TypeConfiguration [containment]
├── id: IdConfiguration [containment]
├── superType: SuperTypeConfiguration [containment]
└── feature: FeatureConfiguration [containment]

ReferenceCodecConfig
├── eReference: EReference [reference]
├── type: TypeConfiguration [containment]
├── id: IdConfiguration [containment]  // only format/key
├── reference: ReferenceConfiguration [containment]
└── feature: FeatureConfiguration [containment]

AttributeCodecConfig
├── eAttribute: EAttribute [reference]
├── key: EString
└── feature: FeatureConfiguration [containment]
```

### Enums (with documentation)

```
TypeStrategy
├── URI [0] - "Full EClass URI: http://example.org#//Person (default)"
├── NAME [1] - "Simple EClass name: Person"
├── CLASS [2] - "Java class name: org.example.PersonImpl"
├── SCHEMA_AND_TYPE [3] - "Separate schema + type fields"
├── NUMERIC [4] - "EMF classifier ID number"
└── NONE [5] - "No type information written/expected"

IdStrategy
├── ID_FIELD [0] - "Use features marked with eID=true (default)"
├── COMBINED [1] - "Combine multiple features with separator"
└── NONE [2] - "No ID serialization"

SerializationFormat
├── PLAIN [0] - "Simple value: \"Person\" (default)"
└── STRUCTURED [1] - "Object with keys: {\"type\": \"Person\"}"

StrategyScope
├── ALL [0] - "Root + all containments + all non-containments (default)"
├── ROOT_ONLY [1] - "Root object only"
├── ROOT_CONTAINMENT [2] - "Root + containment references"
└── ROOT_NON_CONTAINMENT [3] - "Root + non-containment references"

IdKeyMode
├── ID_ONLY [0] - "Only _id field, no individual features (default)"
├── BOTH [1] - "Both _id and individual feature fields"
└── FEATURE_ONLY [2] - "Only individual feature fields, no _id"

SuperTypeStrategy
├── ALL [0] - "All supertypes including interfaces (default)"
├── ALL_EMF [1] - "All EMF supertypes (excludes EObject)"
├── SINGLE [2] - "First direct supertype only"
└── NONE [3] - "No supertypes"

EnumSerialization
├── LITERAL [0] - "Use enum literal name: ACTIVE (default)"
├── VALUE [1] - "Use enum ordinal: 1"
└── NAME [2] - "Use enum name: Active"
```

---

## Code Generation

### What We Can Generate

| Generated Artifact | Source | Benefit |
|-------------------|--------|---------|
| EMF interfaces/impl | Standard genmodel | Free with EMF |
| Builder classes | Custom generator | Type-safe, documented |
| Property key constants | Custom generator | Single source of truth |
| Validation rules | EAnnotations on model | Consistent validation |
| Documentation (Markdown) | EAnnotations | Always in sync |
| Property map converters | Custom generator | Bidirectional conversion |

### Example: Enum with Documentation in Ecore

```xml
<eClassifiers xsi:type="ecore:EEnum" name="TypeStrategy">
  <eAnnotations source="http://www.eclipse.org/emf/2002/GenModel">
    <details key="documentation" value="Strategy for serializing type information"/>
  </eAnnotations>
  <eLiterals name="URI" value="0">
    <eAnnotations source="http://www.eclipse.org/emf/2002/GenModel">
      <details key="documentation" value="Full EClass URI: http://example.org#//Person (default)"/>
    </eAnnotations>
  </eLiterals>
  <eLiterals name="NAME" value="1">
    <eAnnotations source="http://www.eclipse.org/emf/2002/GenModel">
      <details key="documentation" value="Simple EClass name: Person"/>
    </eAnnotations>
  </eLiterals>
  <!-- ... -->
</eClassifiers>
```

### Example: Generated Builder

```java
/**
 * Builder for {@link TypeConfiguration}.
 * <p>
 * Strategy for serializing type information.
 *
 * @generated
 */
public class TypeConfigurationBuilder {

    private final TypeConfiguration config;

    public TypeConfigurationBuilder() {
        this.config = CodecConfigFactory.eINSTANCE.createTypeConfiguration();
    }

    /**
     * Sets the type identification strategy.
     *
     * @param strategy the strategy (URI, NAME, CLASS, SCHEMA_AND_TYPE, NUMERIC, NONE)
     * @return this builder
     * @see TypeStrategy
     */
    public TypeConfigurationBuilder strategy(TypeStrategy strategy) {
        config.setStrategy(strategy);
        return this;
    }

    /**
     * Sets the type identification strategy from string.
     *
     * @param strategy the strategy name (case-insensitive)
     * @return this builder
     * @throws IllegalArgumentException if strategy is not valid
     */
    public TypeConfigurationBuilder strategy(String strategy) {
        config.setStrategy(TypeStrategy.get(strategy.toUpperCase()));
        return this;
    }

    /**
     * Sets the JSON key for type information.
     * Default: "_type"
     *
     * @param key the JSON key
     * @return this builder
     */
    public TypeConfigurationBuilder key(String key) {
        config.setKey(key);
        return this;
    }

    // ... more methods

    public TypeConfiguration build() {
        return EcoreUtil.copy(config); // immutable copy
    }
}
```

### Example: Generated Constants

```java
/**
 * Property key constants for codec configuration.
 * <p>
 * Generated from CodecConfig.ecore
 *
 * @generated
 */
public final class CodecConfigKeys {

    private CodecConfigKeys() {}

    // === Type Configuration ===

    /** Strategy for serializing type information */
    public static final String TYPE_STRATEGY = "codec.typeStrategy";

    /** Output format: PLAIN or STRUCTURED */
    public static final String TYPE_FORMAT = "codec.typeFormat";

    /** JSON key for type information. Default: "_type" */
    public static final String TYPE_KEY = "codec.typeKey";

    // === ID Configuration ===

    /** Strategy for building object IDs */
    public static final String ID_STRATEGY = "codec.idStrategy";

    // ... all keys with Javadoc from model
}
```

### Example: Generated Property Map Converter

```java
/**
 * Converts between property maps and EMF configuration objects.
 *
 * @generated
 */
public class CodecConfigConverter {

    /**
     * Converts a property map to TypeConfiguration.
     */
    public TypeConfiguration toTypeConfiguration(Map<String, Object> properties) {
        TypeConfiguration config = CodecConfigFactory.eINSTANCE.createTypeConfiguration();

        if (properties.containsKey(CodecConfigKeys.TYPE_STRATEGY)) {
            config.setStrategy(toTypeStrategy(properties.get(CodecConfigKeys.TYPE_STRATEGY)));
        }
        if (properties.containsKey(CodecConfigKeys.TYPE_FORMAT)) {
            config.setFormat(toSerializationFormat(properties.get(CodecConfigKeys.TYPE_FORMAT)));
        }
        // ... more properties

        return config;
    }

    /**
     * Converts TypeConfiguration to a property map.
     */
    public Map<String, Object> toPropertyMap(TypeConfiguration config) {
        Map<String, Object> properties = new HashMap<>();

        if (config.getStrategy() != null) {
            properties.put(CodecConfigKeys.TYPE_STRATEGY, config.getStrategy().getName());
        }
        // ... more properties

        return properties;
    }

    private TypeStrategy toTypeStrategy(Object value) {
        if (value instanceof TypeStrategy) return (TypeStrategy) value;
        if (value instanceof String) return TypeStrategy.get(((String) value).toUpperCase());
        throw new IllegalArgumentException("Cannot convert to TypeStrategy: " + value);
    }
}
```

---

## Usage Examples

### Creating Configuration Programmatically

```java
// Using builders (generated)
CodecConfiguration config = CodecConfiguration.builder()
    .type(TypeConfiguration.builder()
        .strategy(TypeStrategy.NAME)
        .key("_type")
        .format(SerializationFormat.PLAIN)
        .build())
    .id(IdConfiguration.builder()
        .strategy(IdStrategy.ID_FIELD)
        .key("_id")
        .build())
    .forClass(PersonPackage.Literals.PERSON)
        .type(TypeConfiguration.builder()
            .strategy(TypeStrategy.URI)
            .build())
        .end()
    .build();
```

### Loading Configuration from XMI

```java
// Load from XMI file
ResourceSet rs = new ResourceSetImpl();
Resource configResource = rs.getResource(URI.createURI("config/person-codec.xmi"), true);
CodecConfiguration config = (CodecConfiguration) configResource.getContents().get(0);

// Use in codec
CodecResource resource = new CodecResource(uri);
resource.setConfiguration(config);
resource.load(inputStream, Collections.emptyMap());
```

### Package-Level Configuration Reference

```xml
<!-- In Person.ecore -->
<ecore:EPackage name="person" nsURI="http://example.org/person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="configUri" value="platform:/plugin/org.example/config/person-codec.xmi"/>
  </eAnnotations>
  <!-- ... EClasses ... -->
</ecore:EPackage>
```

### Mixing EMF Config with Property Maps

```java
// EMF config as base
CodecConfiguration baseConfig = loadFromXmi("base-config.xmi");

// Property map overrides at load time
Map<String, Object> options = new HashMap<>();
options.put(CodecOptions.CODEC_CONFIGURATION, baseConfig);
options.put("codec.typeKey", "@type");  // override just this property

resource.load(inputStream, options);
```

---

## Relationship to Existing Components

### codec.metadata Aspects

The existing aspects (`ClassCodecAspect`, `ReferenceCodecAspect`, etc.) remain in `codec.metadata` for EAnnotation parsing. A converter bridges them to the new configuration model:

```java
// In codec.v2
public class AspectToConfigConverter {

    public CodecConfiguration convert(MetadataService metadataService, EPackage ePackage) {
        CodecConfiguration config = CodecConfigFactory.eINSTANCE.createCodecConfiguration();

        for (EClassifier classifier : ePackage.getEClassifiers()) {
            if (classifier instanceof EClass eClass) {
                ClassMetadata metadata = metadataService.getClassMetadata(eClass);
                ClassCodecAspect aspect = metadata.getAspect(ClassCodecAspect.class);

                if (aspect != null) {
                    ClassCodecConfig classConfig = convertClassAspect(eClass, aspect);
                    config.getClassConfigs().add(classConfig);
                }
            }
        }

        return config;
    }
}
```

### Decoupling Benefit

With this approach, `codec.v2` can work **without** `codec.metadata`:

```
Option A: With codec.metadata
  EAnnotations → codec.metadata → Aspects → codec.v2 (via converter)

Option B: Without codec.metadata
  Property maps → codec.v2 (direct)
  XMI config → codec.v2 (direct)
```

---

## Open Questions

1. **Merge semantics** - When merging configurations, should `null` values be treated as "not set" (inherit from lower level) or "explicitly unset"?

2. **Validation timing** - Validate during `build()` or defer to serialization time?

3. **Generator technology** - Use Xtend/Xtext, plain Java, or another templating approach?

4. **Discriminator mapping** - How to model the inline mappings (`inlineMapping.*`) in EMF?

5. **Feature-level config keys** - `ClassCodecConfig` references `EClass`, but how to handle cross-package references in XMI?

---

## TODO

- [ ] Create `CodecConfig.ecore` in `org.eclipse.fennec.codec.v2/model/`
- [ ] Define all enums with documentation EAnnotations
- [ ] Define configuration classes (TypeConfiguration, IdConfiguration, etc.)
- [ ] Define scope-level classes (ClassCodecConfig, ReferenceCodecConfig, AttributeCodecConfig)
- [ ] Create genmodel and generate EMF code
- [ ] Design code generator for builders
- [ ] Design code generator for property key constants
- [ ] Design code generator for property map converters
- [ ] Implement AspectToConfigConverter
- [ ] Implement configuration merge logic
- [ ] Add tests for configuration model
- [ ] Add tests for conversion and merging
- [ ] Update annotation-scope-reference to reference this model

---

## Revision History

| Date | Changes |
|------|---------|
| 2026-01-23 | Initial proposal based on discussion |
| 2026-01-23 | Added model structure with TypeConfiguration, IdConfiguration sub-objects |
| 2026-01-23 | Added code generation examples (builders, constants, converters) |
| 2026-01-23 | Added usage examples and relationship to existing components |
