# Feature Serialization

[← Back to Overview](00-overview.md) | [← Reference Serialization](08-reference.md)

---

Features (EStructuralFeatures) have additional serialization options beyond the metadata targets.

## 1. Null, Default, and Empty Value Handling

### 1.1 Codec-Wide Defaults

Global settings that apply to all features unless overridden:

| Option | Description | Default |
|--------|-------------|---------|
| `serializeDefaults` | Include fields with default values | `false` |
| `serializeNull` | Include fields with explicit null values | `false` |
| `serializeEmpty` | Include empty collections | `true` |

**Java Builder (Codec-Wide):**
```java
CodecConfig config = CodecConfig.builder()
    .serializeDefaults(false)  // Default: omit default values
    .serializeNull(false)      // Default: omit null values
    .serializeEmpty(true)      // Default: include empty collections
    .build();
```

**Default Behavior (serializeDefaults=false, serializeNull=false):**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe",
  "firstName": "John",
  "lastName": "Doe"
  // "middleName" omitted (null)
  // "age" omitted (default value 0)
}
```

**With serializeNull=true:**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe",
  "firstName": "John",
  "middleName": null,
  "lastName": "Doe"
}
```

### 1.2 Feature-Specific Overrides

Override codec-wide settings for individual features:

| Option | Default | Description |
|--------|---------|-------------|
| `serialize` | `true` | Serialize this feature (false = skip/transient) |
| `serializeNull` | `false` | Include null values in output |
| `serializeDefaults` | `false` | Include default values in output |
| `serializeEmpty` | `true` | Include empty collections in output |

**Semantics:** `true` = include in output, `false` = omit from output. Feature-level overrides codec-wide.

**EAnnotation (on EStructuralFeature):**

```xml
<!-- Mark feature as transient (never serialize) -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="internalCache">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="serialize" value="false"/>
  </eAnnotations>
</eStructuralFeatures>

<!-- Force include null values for this feature (override codec default) -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="middleName">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="serializeNull" value="true"/>
  </eAnnotations>
</eStructuralFeatures>

<!-- Skip default values for this feature -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="counter">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="serializeDefaults" value="false"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `serialize` | true, false | true | Serialize this feature (false = transient) |
| `serializeNull` | true, false | false | Include null values |
| `serializeDefaults` | true, false | false | Include default values |
| `serializeEmpty` | true, false | true | Include empty collections |

**Java Builder (Runtime Override):**
```java
// Always skip a feature (transient)
FeatureSerializationConfig transientConfig = FeatureSerializationConfig.builder()
    .feature("internalCache")
    .serialize(false)  // Never serialize
    .build();

// Force serialize null for specific feature (override codec default)
FeatureSerializationConfig nullConfig = FeatureSerializationConfig.builder()
    .feature("middleName")
    .serializeNull(true)  // Include even if null
    .build();

// Skip default values for specific feature
FeatureSerializationConfig defaultConfig = FeatureSerializationConfig.builder()
    .feature("counter")
    .serializeDefaults(false)  // Skip if value equals default
    .build();
```

**Resolution Order:**
1. Feature-specific setting (if defined)
2. Codec-wide setting
3. Built-in default

---

## 2. Feature Key Customization

Override the JSON property name for a feature. Names can be customized at multiple levels.

**Resolution Order (highest to lowest priority):**
1. **Config Builder** - Runtime configuration override
2. **Model Annotation** - `@CODEC_KEY` or `<details key="key" value="..."/>` on EStructuralFeature
3. **ExtendedMetaData name** - Only when `useNamesFromExtendedMetadata=true` (see [Section 3](#3-extended-metadata-names))
4. **Feature Name** - Default EMF feature name

### 2.1 Model Annotation

Customize feature names directly in the EMF model using codec annotations:

```java
// In .ecore model or generated interface
@CODEC_KEY("first_name")
String getFirstName();
```

Or in the .ecore file:
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="firstName">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="key" value="first_name"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Resulting Output (from annotation):**
```json
{
  "first_name": "John",
  "lastName": "Doe"
}
```

### 2.2 Config Builder Override

The config builder can override annotation-defined names at runtime:

```java
// Override the annotation-defined key
FeatureSerializationConfig config = FeatureSerializationConfig.builder()
    .feature("firstName")
    .key("givenName")  // Overrides @CODEC_KEY("first_name")
    .build();
```

**Resulting Output (config overrides annotation):**
```json
{
  "givenName": "John",
  "lastName": "Doe"
}
```

This allows models to define sensible defaults via annotations while still permitting runtime customization for specific use cases (e.g., different API versions, external system integration).

---

## 3. Extended Metadata Names

Use names from XSD extended metadata annotations instead of EMF feature names.

### 3.1 Configuration

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .useNamesFromExtendedMetadata(true)
    .build();
```

**Default:** `false`

### 3.2 Resolution Priority

When resolving the JSON key for a feature, the following priority applies:

1. **Explicit codec annotation `key`** - From `@CODEC_KEY` or `<details key="key" value="..."/>` (highest priority)
2. **ExtendedMetaData `name`** - Only when `useNamesFromExtendedMetadata=true`
3. **Feature name** - Default EMF feature name (fallback)

### 3.3 EAnnotation Format

The ExtendedMetaData annotation uses the standard EMF format:

```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="titles" upperBound="-1"
    eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString">
  <eAnnotations source="http:///org/eclipse/emf/ecore/util/ExtendedMetaData">
    <details key="name" value="title"/>
  </eAnnotations>
</eStructuralFeatures>
```

With `useNamesFromExtendedMetadata=true`, the feature `titles` serializes as `"title"` in JSON.

### 3.4 Use Case

This is useful when EMF models are generated from XSD and you want to preserve the original XML element/attribute names.

### 3.5 Migration Note

> **Breaking change from v1:** The previous codec defaulted `useNamesFromExtendedMetadata` to `true`.
> In v2, the default is `false` for the following reasons:
>
> - **Principle of least surprise** - JSON keys match EMF feature names unless explicitly configured
> - **Explicit over implicit** - ExtendedMetaData usage is opt-in
> - **Simpler mental model** - Feature name in code matches JSON key by default
> - **Performance** - No annotation lookup overhead by default
>
> If migrating from v1 and relying on ExtendedMetaData names, add `.useNamesFromExtendedMetadata(true)` to your configuration.

---

## 4. Enum Serialization

Control how enum values are serialized:

| Option | Description | Example |
|--------|-------------|---------|
| `LITERAL` | Use enum literal name | `"status": "ACTIVE"` |
| `VALUE` | Use enum ordinal value | `"status": 1` |
| `NAME` | Use enum name | `"status": "Active"` |

### 4.1 EAnnotation (on EEnum or EAttribute)

On the EEnum itself (applies to all usages):
```xml
<eClassifiers xsi:type="ecore:EEnum" name="Status">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="enumSerialization" value="LITERAL"/>
  </eAnnotations>
  <eLiterals name="ACTIVE" value="0"/>
  <eLiterals name="INACTIVE" value="1"/>
</eClassifiers>
```

Or on a specific EAttribute (overrides EEnum setting):
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="status" eType="#//Status">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="enumSerialization" value="VALUE"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 4.2 Java Builder (Codec-Wide or Feature-Specific)

```java
// Codec-wide default
CodecConfig config = CodecConfig.builder()
    .enumSerialization(EnumSerializationStrategy.LITERAL)
    .build();

// Feature-specific override
FeatureSerializationConfig featureConfig = FeatureSerializationConfig.builder()
    .feature("status")
    .enumSerialization(EnumSerializationStrategy.VALUE)
    .build();
```

### 4.3 Deserialization Behavior

Deserialization automatically detects and handles the serialization format:

| Input Type | Handling |
|------------|----------|
| String | First tries `EEnum.getEEnumLiteral(name)`, then `EEnum.getEEnumLiteralByLiteral(literal)` |
| Integer | Looked up by enum value using `EEnum.getEEnumLiteral(int)` |

All three strategies round-trip correctly:

- **LITERAL strategy**: Uses literal lookup
- **NAME strategy**: Uses name lookup
- **VALUE strategy**: Uses integer value lookup

The deserializer tries name lookup first, then falls back to literal lookup, so both NAME and LITERAL serialized values are properly deserialized.

---

## 5. Default Feature Settings

| Setting | Default Value |
|---------|---------------|
| Serialize | `true` |
| Serialize Null | `false` |
| Serialize Defaults | `false` |
| Serialize Empty | `true` |
| Enum Serialization | `LITERAL` |
| Use Names From ExtendedMetaData | `false` |

---

[Next: Custom Value Readers/Writers →](10-custom-values.md)
