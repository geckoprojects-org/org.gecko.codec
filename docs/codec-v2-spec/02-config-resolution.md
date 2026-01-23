# Configuration Resolution

[← Architecture](01-architecture.md)

---

This chapter defines how configuration is resolved across all codec features. Understanding this hierarchy is essential as it applies to every serialization target (type, ID, reference, supertype) and feature.

## 1. Two-Dimensional Configuration Model

Configuration resolution operates across **two orthogonal dimensions**:

1. **Source Hierarchy (Vertical)**: Where configuration comes from (runtime vs. static)
2. **Scope Chain (Horizontal)**: What EMF element configuration applies to (feature vs. class vs. global)

This model allows fine-grained control: you can set a global default in the model, override it per-class at runtime, and further override it per-feature for specific operations.

---

## 2. Dimension 1: Source Hierarchy (Vertical)

Configuration can be provided at different sources. **Higher priority sources override lower ones.**

| Priority | Source | Lifecycle | Configuration Methods |
|:--------:|--------|-----------|----------------------|
| 1 (highest) | **Load/Save Options** | Per-operation | Property map |
| 2 | **Resource** | Per-resource | Property map, ConfigBuilder |
| 3 | **ResourceFactory** | Per-factory | Property map, ConfigBuilder |
| 4 | **Jackson Module Config** | Per-codec | Property map, ConfigBuilder |
| 5 | **EAnnotations** | Per-model (static) | Ecore model annotations |
| 6 (lowest) | **Built-in Defaults** | Global | Hardcoded in codec |

**Key principle:** Dynamic overrides static. Runtime settings always win over model-defined settings.

```
Load/Save Options    ← dynamic, per-operation
        ↓
Resource config      ← can be set when creating resource
        ↓
ResourceFactory      ← factory-wide defaults
        ↓
Jackson Module       ← codec-wide defaults
        ↓
EAnnotations         ← static, defined in .ecore model
        ↓
Built-in Defaults    ← hardcoded fallback
        ↓
    ════════════════════════
    │ Effective Value      │
    ════════════════════════
```

---

## 3. Dimension 2: Scope Chain (Horizontal)

Within any single source level, configuration can be scoped to different EMF elements. **More specific scopes override less specific ones.**

| Priority | Scope | Applies To |
|:--------:|-------|------------|
| 1 (highest) | **EReference/EAttribute** | Single feature |
| 2 | **EClass** | All instances of that class |
| 3 | **Global** | All objects in codec (with optional StrategyScope) |
| 4 (lowest) | **Default** | Built-in fallback |

```
EReference annotation    ← most specific
        ↓
EClass annotation        ← class-level default
        ↓
Global config            ← codec-wide (respects StrategyScope)
        ↓
Built-in Default         ← hardcoded fallback
        ↓
    ════════════════════════
    │ Effective Value      │
    ════════════════════════
```

---

## 4. Combined Resolution Algorithm

When resolving a configuration value, **both dimensions are considered**:

```
For property P on EReference R of EClass C:

1. Check Load/Save options for R.P, then C.P, then global P
2. Check Resource config for R.P, then C.P, then global P
3. Check ResourceFactory for R.P, then C.P, then global P
4. Check Jackson Module for R.P, then C.P, then global P
5. Check EAnnotation on R for P
6. Check EAnnotation on C for P
7. Check global config for P (respecting StrategyScope)
8. Use built-in default for P

First non-null value wins.
```

**Example Resolution:**

For `typeStrategy` on `Person.address` (an EReference):

1. Load options: `Person.address.typeStrategy` → not set
2. Load options: `Person.typeStrategy` → not set
3. Load options: `typeStrategy` → not set
4. Resource config: same pattern → not set
5. ResourceFactory: same pattern → not set
6. Jackson Module: same pattern → not set
7. EAnnotation on `address` feature: `@CODEC(codec.type="...")` → not set
8. EAnnotation on `Person` class: `@CODEC(codec.typeStrategy="NAME")` → **found: NAME**

Result: `typeStrategy = NAME`

---

## 5. StrategyScope (Global Level Modifier)

At the **Global** level (codec-wide configuration), certain properties support **StrategyScope** to control where they apply. This provides a middle ground between "everywhere" and "nowhere."

| StrategyScope | Applies To |
|---------------|------------|
| `ALL` | Root + all containments + all non-containments **(default)** |
| `ROOT_ONLY` | Root object only |
| `ROOT_CONTAINMENT` | Root + objects accessed via containment references |
| `ROOT_NON_CONTAINMENT` | Root + objects accessed via non-containment references |

**Example:** Global `typeStrategy=NAME` with `typeScope=ROOT_ONLY`:
- Root object uses NAME strategy
- All nested objects fall back to default (URI)

**Configuration:**
```java
CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.NAME)
    .typeScope(StrategyScope.ROOT_ONLY)  // Only applies to root
    .build();
```

**EAnnotation equivalent:**
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="codec.typeStrategy" value="NAME"/>
  <details key="codec.typeScope" value="ROOT_ONLY"/>
</eAnnotations>
```

---

## 6. Configuration Dependencies

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

## 7. EAnnotation and Config Builder Parity

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

## 8. EffectiveConfig Pattern

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

## 9. ResourceFactory Configuration

The `CodecResourceFactory` provides Level 2 configuration in the hierarchy. It supports both constructor-based and setter-based configuration for flexibility with different environments.

### 9.1 Constructor-Based Configuration (Recommended)

For programmatic use, constructor injection is preferred:

```java
// Minimal - uses default configuration
CodecResourceFactory factory = new CodecResourceFactory(metadataService);

// With custom configuration
CodecConfiguration config = CodecConfiguration.builder()
    .smartCompression(true)
    .typeStrategy(TypeStrategy.NAME)
    .build();
CodecResourceFactory factory = new CodecResourceFactory(metadataService, config);

// With custom Jackson mapper
JsonMapper.Builder mapperBuilder = JsonMapper.builder()
    .enable(SerializationFeature.INDENT_OUTPUT);
CodecResourceFactory factory = new CodecResourceFactory(metadataService, config, mapperBuilder);
```

### 9.2 Setter-Based Configuration (DI Frameworks)

For dependency injection frameworks (Spring, CDI, OSGi DS), use the parameterless constructor with setters:

```java
// Parameterless constructor for DI
CodecResourceFactory factory = new CodecResourceFactory();

// Required: MetadataService must be set before creating resources
factory.setMetadataService(metadataService);

// Optional: Custom configuration (defaults to CodecConfiguration.defaults())
factory.setConfiguration(config);

// Optional: Custom Jackson mapper
factory.setMapperBuilder(mapperBuilder);

// Optional: Default load/save options
factory.setDefaultLoadOptions(loadOptions);
factory.setDefaultSaveOptions(saveOptions);
```

### 9.3 OSGi Declarative Services Example

```java
@Component(service = Resource.Factory.class)
public class CodecResourceFactoryComponent extends CodecResourceFactory {

    @Reference
    public void setMetadataService(MetadataService metadataService) {
        super.setMetadataService(metadataService);
    }

    @Activate
    public void activate(Map<String, Object> properties) {
        // Build configuration from Config Admin properties
        CodecConfiguration config = CodecConfiguration.builder()
            .fromProperties(properties)
            .build();
        setConfiguration(config);
    }
}
```

### 9.4 Spring Configuration Example

```java
@Configuration
public class CodecConfig {

    @Bean
    public CodecResourceFactory codecResourceFactory(MetadataService metadataService) {
        CodecResourceFactory factory = new CodecResourceFactory();
        factory.setMetadataService(metadataService);
        factory.setConfiguration(CodecConfiguration.builder()
            .smartCompression(true)
            .build());
        return factory;
    }
}
```

### 9.5 Default Options

Default load/save options are applied to all resources created by the factory:

```java
Map<Object, Object> defaultLoadOptions = new HashMap<>();
defaultLoadOptions.put(CodecResource.CODEC_ROOT_SCHEMA, "http://example.org/1.0");

factory.setDefaultLoadOptions(defaultLoadOptions);

// All resources created by this factory will use these defaults
Resource resource = factory.createResource(uri);
resource.load(inputStream, null);  // Uses factory defaults
```

Per-operation options override factory defaults:
```java
Map<Object, Object> operationOptions = new HashMap<>();
operationOptions.put(CodecResource.CODEC_ROOT_SCHEMA, "http://other.org/2.0");

resource.load(inputStream, operationOptions);  // Overrides factory default
```

---

## 10. Jackson Configuration

The `JsonMapper.Builder` can be provided at different levels:

| Level | How to Provide | Scope |
|-------|----------------|-------|
| **ResourceFactory** | Constructor parameter or `setMapperBuilder()` | All resources from factory |
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

## 11. Load/Save Option Keys Registry

This section documents all option keys that can be passed to `resource.load(options)` or `resource.save(options)`.

### 11.1 Resource-Level Options

These options are defined in `CodecResource`:

| Option Key | Type | Direction | Description |
|------------|------|-----------|-------------|
| `CODEC_ROOT_OBJECT` | `EClass` or `String` (URI) | Load | Type hint for root object deserialization |
| `CODEC_ROOT_SCHEMA` | `String` (URI) | Load | Context schema for NAME strategy resolution |

**Usage:**
```java
Map<String, Object> options = new HashMap<>();
options.put(CodecResource.CODEC_ROOT_OBJECT, PersonPackage.Literals.PERSON);
options.put(CodecResource.CODEC_ROOT_SCHEMA, "http://example.org/person/1.0");
resource.load(inputStream, options);
```

### 11.2 Context Options

These options are defined in `ContextHelper` and affect the Jackson serialization/deserialization context:

| Option Key | Type | Direction | Description |
|------------|------|-----------|-------------|
| `CODEC_FEATURE_TYPE_HINTS` | `Map<EStructuralFeature, EClass>` | Load | Type hints for specific features |
| `CODEC_FEATURE_VALUE_READERS` | `Map<EStructuralFeature, String>` | Load | Custom value readers per feature |
| `CODEC_FEATURE_VALUE_WRITERS` | `Map<EStructuralFeature, String>` | Save | Custom value writers per feature |

**Usage:**
```java
Map<EStructuralFeature, EClass> typeHints = new HashMap<>();
typeHints.put(GeoPackage.Literals.FEATURE__GEOMETRY, GeoPackage.Literals.POINT);

Map<String, Object> options = new HashMap<>();
options.put(ContextHelper.FEATURE_TYPE_HINTS, typeHints);
resource.load(inputStream, options);
```

### 11.3 Internal Context Keys (Read-Only)

These keys are used internally by the codec and should not be set by users:

| Option Key | Type | Description |
|------------|------|-------------|
| `CODEC_EXPECTED_TYPE` | `EClass` | Current expected type during deserialization |
| `CODEC_UNRESOLVED_REFERENCES` | `List<UnresolvedReference>` | Collected unresolved references |
| `CODEC_DIAGNOSTIC_COLLECTOR` | `DiagnosticCollector` | Error/warning collection |
| `CODEC_SUPPRESS_TYPE` | `Boolean` | Smart compression type suppression flag |
| `CODEC_CONTEXT_SCHEMA_URI` | `String` | Current context schema for smart compression |
| `CODEC_ROOT_SERIALIZED` | `Boolean` | Root object serialization complete flag |
| `CODEC_FEATURE_TYPE_HINT` | `EClass` | Current feature's type hint |

### 11.4 Property Map Format

For configuration via property maps (OSGi Config Admin, Spring, etc.), keys follow this pattern:

```
codec.<target>.<property>
```

**Examples:**
```properties
# Global settings
codec.smartCompression=true
codec.sortPropertiesAlphabetically=false

# Type settings
codec.type.strategy=NAME
codec.type.key=_type

# ID settings
codec.id.enabled=true
codec.id.onTop=true
codec.id.key=_id

# Per-class settings (using fully qualified class name)
codec.Person.type.strategy=URI
codec.Person.id.features=firstName,lastName

# Per-feature settings
codec.Person.firstName.key=first_name
codec.Person.firstName.serialize=true
```

### 11.5 Java Constants

All option keys are available as Java constants:

```java
import org.eclipse.fennec.codec.v2.resource.CodecResource;
import org.eclipse.fennec.codec.v2.context.ContextHelper;

// Resource options
CodecResource.CODEC_ROOT_OBJECT
CodecResource.CODEC_ROOT_SCHEMA

// Context options
ContextHelper.FEATURE_TYPE_HINTS
ContextHelper.FEATURE_VALUE_READERS
ContextHelper.FEATURE_VALUE_WRITERS
```

### 11.6 Type-Safe Configuration (Preferred)

For most use cases, the type-safe `CodecConfiguration.Builder` is preferred over raw option maps:

```java
CodecConfiguration config = CodecConfiguration.builder()
    // Global settings
    .smartCompression(true)
    .useType(true)
    .typeStrategy(TypeStrategy.NAME)

    // ID settings
    .useId(true)
    .idOnTop(true)
    .idKey("_id")

    // Per-class overrides
    .forClass(PersonPackage.Literals.PERSON)
        .typeStrategy(TypeStrategy.URI)
        .idFeatures("firstName", "lastName")
        .done()

    .build();

CodecResourceFactory factory = new CodecResourceFactory(metadataService, config);
```

The builder approach provides:
- Compile-time type safety
- IDE auto-completion
- Validation of dependent settings

### 11.7 Direction-Specific Configuration

Properties can be scoped to serialization, deserialization, or both using prefixes:

| Prefix | Applies To | Example |
|--------|------------|---------|
| `codec.` | Both ser and deser | `codec.typeStrategy=NAME` |
| `codec.ser.` | Serialization only | `codec.ser.typeStrategy=NAME` |
| `codec.deser.` | Deserialization only | `codec.deser.typeStrategy=URI` |

**Use Case:** Different strategies for serialization vs deserialization:
```java
Map<String, Object> properties = Map.of(
    "codec.ser.typeStrategy", "NAME",      // Serialize with simple names
    "codec.deser.typeStrategy", "URI"      // Deserialize expects full URIs
);

// Build separate configs
CodecConfiguration serConfig = ConfigBuilder.fromMap(properties).buildSerializationConfig();
// serConfig.typeStrategy = NAME

CodecConfiguration deserConfig = ConfigBuilder.fromMap(properties).buildDeserializationConfig();
// deserConfig.typeStrategy = URI
```

**Precedence:** Specific prefix overrides general prefix:
```java
Map<String, Object> properties = Map.of(
    "codec.typeStrategy", "URI",           // Default for both
    "codec.ser.typeStrategy", "NAME"       // Override for serialization only
);
// Serialization: NAME (specific override)
// Deserialization: URI (general default)
```

**When to use direction-specific configuration:**
- External API writes URIs but you want to serialize simple names
- Legacy system compatibility (read old format, write new format)
- Testing/migration scenarios

**EAnnotation alternative:**
Direction-specific configuration is primarily a runtime concern and is not supported via EAnnotations. Use property maps or CodecConfiguration builder for direction-specific settings.

---

[Next: Naming Conventions →](03-naming-conventions.md)
