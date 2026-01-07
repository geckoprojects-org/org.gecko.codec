# Configuration Hierarchy

[← Back to Overview](00-overview.md) | [← Serialization Strategies](01-strategies.md)

---

This chapter defines how configuration is resolved across all codec features. Understanding this hierarchy is essential as it applies to every serialization target (type, ID, reference, supertype) and feature.

## 1. Configuration Levels

Configuration follows a **dynamic-before-static** principle. Higher priority levels override lower ones.

| Priority | Level | Scope | Example |
|----------|-------|-------|---------|
| 1 (highest) | **Load/Save options** | Per-operation | `resource.save(options)` |
| 2 | **ResourceFactory defaults** | Per-factory | `factory.getDefaultSaveOptions()` |
| 3 | **Codec module config** | Per-codec | Jackson module configuration |
| 4 | **Configuration properties** | External | System properties, config files |
| 5 | **EAnnotations** | Per-model | Declared in .ecore model |
| 6 (lowest) | **Built-in defaults** | Global | Hardcoded codec defaults |

**Key Principle:** Every configuration setting (EAnnotation detail key, codec module feature) has a corresponding runtime option key. This allows any setting to be overridden at load/save time without modifying the model.

---

## 2. Override Rules

- **Dynamic overrides static**: Runtime settings always win over declared settings
- **Specific overrides general**: Feature-level config overrides class-level, which overrides codec-level
- **Explicit overrides implicit**: A configured value always overrides a derived/default value

### Resolution Flow

```
Load/Save Options (highest priority)
        ↓ (if not set)
ResourceFactory Defaults
        ↓ (if not set)
Codec Module Config
        ↓ (if not set)
Configuration Properties
        ↓ (if not set)
EAnnotations (MetadataService)
        ↓ (if not set)
Built-in Defaults (lowest priority)
        ↓
    ═══════════════════════════
    │ EffectiveConfig (merged) │
    ═══════════════════════════
```

---

## 3. Scope Levels

Configuration can be applied at different scopes:

| Scope | Applies To | Example |
|-------|------------|---------|
| **Codec-wide** | All serialization | `smartCompression(true)` |
| **Per-class** | Specific EClass | `forClass(Person.class).typeStrategy(NAME)` |
| **Per-feature** | Specific feature | `forFeature("firstName").key("first_name")` |

**Resolution order within scopes:**
1. Feature-specific config (if defined)
2. Class-specific config (if defined)
3. Codec-wide config
4. Built-in default

---

## 4. Configuration Dependencies

Certain settings have logical dependencies. When a parent setting is disabled, dependent settings are implicitly disabled.

### Type and SuperType Dependency

| `serializeType` | `serializeSuperTypes` (configured) | `serializeSuperTypes` (effective) |
|-----------------|-----------------------------------|-----------------------------------|
| `true` | `true` | `true` |
| `true` | `false` | `false` |
| `false` | `true` | `false` (implicitly disabled) |
| `false` | `false` | `false` |

**Rationale:** Supertypes are type information. If type serialization is disabled, serializing supertypes would be inconsistent.

### Smart Compression Dependencies

Smart compression affects multiple targets:
- Type serialization (omit when inferable)
- Reference type (omit when equals declared type)
- Same-schema names (use simple name instead of URI)

---

## 5. EAnnotation and Config Builder Parity

Every codec feature that can be configured via **EAnnotations** can also be configured via **Config Builder**, and vice versa.

**EAnnotation (static, in model):**
```xml
<eStructuralFeatures name="firstName">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="key" value="first_name"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Config Builder (dynamic, at runtime):**
```java
FeatureSerializationConfig.builder()
    .feature("firstName")
    .key("first_name")
    .build();
```

**Load/Save options (per-operation override):**
```java
Map<String, Object> options = new HashMap<>();
options.put("Person.firstName.key", "givenName");  // Overrides both above
resource.save(outputStream, options);
```

---

## 6. Resolution Example

For feature `firstName` on class `Person`:

1. Check load/save options for `Person.firstName.key`
2. Check ResourceFactory defaults for `Person.firstName.key`
3. Check codec module config for `Person.firstName.key`
4. Check system property `fennec.codec.Person.firstName.key`
5. Check `@CODEC_KEY` annotation on `firstName` feature
6. Use EMF feature name `firstName`

**First non-null value wins.**

---

## 7. EffectiveConfig Pattern

The codec merges all configuration levels into an immutable `EffectiveConfig` snapshot:

```java
// ConfigurationMerger combines all levels
EffectiveCodecConfig effectiveConfig = ConfigurationMerger.merge(
    loadSaveOptions,      // Level 1
    factoryDefaults,      // Level 2
    moduleConfig,         // Level 3
    systemProperties,     // Level 4
    metadataService,      // Level 5 (EAnnotations)
    builtInDefaults       // Level 6
);

// Lazy-cached per-class and per-feature configs
EffectiveClassConfig classConfig = effectiveConfig.getClassConfig(personClass);
EffectiveFeatureConfig featureConfig = effectiveConfig.getFeatureConfig(firstNameFeature);
```

This ensures:
- Configuration is resolved once, not on every serialization call
- Immutable snapshot prevents mid-operation changes
- Lazy caching optimizes memory for large models

---

## 8. Jackson Configuration

The `JsonMapper.Builder` can be provided at different levels:

| Level | How to Provide | Scope |
|-------|----------------|-------|
| **ResourceFactory** | Constructor parameter | All resources from factory |
| **CodecResource** | Constructor parameter | Single resource |
| **Default** | `null` → `JsonMapper.builder()` | Built-in default |

**Example:**
```java
JsonMapper.Builder customBuilder = JsonMapper.builder()
    .enable(SerializationFeature.INDENT_OUTPUT)
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

CodecResourceFactory factory = new CodecResourceFactory(
    metadataService,
    codecConfig,
    customBuilder  // All resources use this base configuration
);
```

The codec applies its serializers/deserializers on top of the provided base configuration.

---

[Next: Global Configuration Options →](03-global-options.md)
