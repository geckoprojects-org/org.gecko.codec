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
| Force Serialize Features | Empty (none) |

---

## 6. Force Serialize Volatile/Transient Features

By default, EMF features marked as `volatile`, `transient`, or `derived` are **not serialized**. This is because:
- **Transient** features are meant to be non-persistent
- **Derived** features are computed from other features
- **Volatile** features don't have storage and are computed on-the-fly

However, some models (like GeoJSON) use volatile features to provide computed views of data that **must** be serialized. The `forceSerialize` configuration allows overriding this behavior.

### 6.1 Configuration

**Java Builder:**
```java
// Force serialize specific features by name (applies to all EClasses)
CodecConfiguration config = CodecConfiguration.builder()
    .forceSerialize("data", "bbox")  // Feature names
    .build();

// Force serialize with EClass qualification (more precise)
CodecConfiguration config = CodecConfiguration.builder()
    .forceSerializeQualified("Point.data", "GeoJsonObject.bbox")
    .build();
```

### 6.2 Resolution Logic

When determining if a feature should be serialized:

1. Check if globally ignored → skip
2. Check if volatile/transient/derived:
   - If `forceSerialize` includes the feature name → continue
   - If `forceSerializeQualified` includes `EClassName.featureName` → continue
   - Otherwise → skip
3. Check EAnnotation `serialize` setting
4. Return serialization decision

### 6.3 Use Case: GeoJSON

The GeoJSON EMF model stores coordinates in a structured `Coordinates` object but exposes them as a `double[]` array through a volatile `data` attribute. For proper GeoJSON output, this volatile attribute must be serialized:

**Ecore model:**
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="data"
    eType="#//DoubleArray1D" volatile="true">
  <eAnnotations source="http:///org/eclipse/emf/ecore/util/ExtendedMetaData">
    <details key="name" value="coordinates"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Configuration:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .typeKey("type")
    .typeStrategy(TypeStrategy.NAME)
    .useNamesFromExtendedMetaData(true)
    .forceSerialize("data", "bbox")  // Force volatile features
    .build();
```

**Output:**
```json
{"type": "Point", "coordinates": [8.6821, 50.1109]}
```

Without `forceSerialize("data")`, the output would only be:
```json
{"type": "Point"}
```

### 6.4 Deserialization

The `forceSerialize` configuration also affects deserialization. Volatile features included in `forceSerialize` will be deserialized from JSON, allowing round-trip support for computed attributes.

> **Note:** For deserialization to work, the volatile feature must still be **changeable** (`isChangeable()=true`). Non-changeable features cannot be set even with `forceSerialize`.

---

## 7. Array Attributes

EAttributes can have array data types (EDataTypes with array instance classes). The codec supports both primitive and object arrays of arbitrary dimensions.

### 7.1 Supported Array Types

| Array Type | Example JSON | Use Case |
|------------|--------------|----------|
| `double[]` | `[8.68, 50.11]` | GeoJSON Point coordinates |
| `double[][]` | `[[0,0], [1,1], [2,2]]` | GeoJSON LineString coordinates |
| `double[][][]` | `[[[0,0], [1,0], [1,1], [0,0]]]` | GeoJSON Polygon coordinates |
| `double[][][][]` | Nested arrays | GeoJSON MultiPolygon coordinates |
| `int[]` | `[1, 2, 3, 4, 5]` | Integer sequences |
| `long[]` | `[1000000000000, ...]` | Large integer sequences |
| `float[]` | `[1.5, 2.5, 3.5]` | Float sequences |
| `boolean[]` | `[true, false, true]` | Boolean flags |
| `String[]` | `["a", "b", "c"]` | String lists |
| `Date[]` | `["2025-01-14", "2024-12-25"]` | Date sequences |
| `BigDecimal[]` | `["123.456", "789.012"]` | Precise decimal sequences |

### 7.2 Defining Array Data Types in Ecore

Array attributes require custom EDataTypes with the appropriate instance class:

```xml
<!-- 1D double array -->
<eClassifiers xsi:type="ecore:EDataType" name="DoubleArray1D"
    instanceClassName="double[]"/>

<!-- 2D double array -->
<eClassifiers xsi:type="ecore:EDataType" name="DoubleArray2D"
    instanceClassName="double[][]"/>

<!-- 3D double array -->
<eClassifiers xsi:type="ecore:EDataType" name="DoubleArray3D"
    instanceClassName="double[][][]"/>

<!-- Using the array type in an EClass -->
<eClassifiers xsi:type="ecore:EClass" name="Point">
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="coordinates"
      eType="#//DoubleArray1D"/>
</eClassifiers>
```

### 7.3 Deserialization Behavior

The deserializer automatically detects array types based on:

1. **EDataType instance class** - Determines the target array type
2. **JSON token** - `START_ARRAY` triggers array deserialization
3. **Element type** - Primitive vs object arrays handled differently

**Primitive arrays** (`double[]`, `int[]`, etc.):
- Elements read directly from JSON numbers
- Both integer and float JSON numbers accepted for `double[]`

**Object arrays** (`Date[]`, `BigDecimal[]`, etc.):
- Elements converted from JSON strings via type-specific parsing
- Common types: `Date` (ISO format), `BigDecimal`, `BigInteger`, `UUID`
- Fallback: String constructor or `valueOf`/`parse` static methods

**Multi-dimensional arrays**:
- Recursively processed based on component type
- `double[][]` contains `double[]` elements
- `double[][][]` contains `double[][]` elements

### 7.4 Example: GeoJSON Coordinates

The GeoJSON model defines coordinate arrays using EDataTypes:

```xml
<eClassifiers xsi:type="ecore:EDataType" name="DoubleArray1D"
    instanceClassName="double[]"/>
<eClassifiers xsi:type="ecore:EDataType" name="DoubleArray2D"
    instanceClassName="double[][]"/>
<eClassifiers xsi:type="ecore:EDataType" name="DoubleArray3D"
    instanceClassName="double[][][]"/>
```

**Point** uses `double[]`:
```json
{"type": "Point", "coordinates": [8.6821, 50.1109]}
```

**LineString** uses `double[][]`:
```json
{"type": "LineString", "coordinates": [[0, 0], [10, 10], [20, 20]]}
```

**Polygon** uses `double[][][]`:
```json
{
  "type": "Polygon",
  "coordinates": [
    [[0, 0], [10, 0], [10, 10], [0, 10], [0, 0]],
    [[2, 2], [8, 2], [8, 8], [2, 8], [2, 2]]
  ]
}
```

### 7.5 Empty and Jagged Arrays

**Empty arrays** are supported:
```json
{"coordinates": []}
```
Results in a zero-length array (`new double[0]`).

**Jagged arrays** (varying inner lengths) are supported:
```json
{"data": [[1.0], [2.0, 3.0], [4.0, 5.0, 6.0]]}
```
Results in `double[3][]` with inner arrays of lengths 1, 2, 3.

### 7.6 Difference: Array Attribute vs Multi-Valued Attribute

| Aspect | Array Attribute | Multi-Valued Attribute |
|--------|-----------------|------------------------|
| EMF definition | `EAttribute` with array EDataType | `EAttribute` with `upperBound=-1` |
| Instance class | `double[]`, `String[]`, etc. | `EList<Double>`, `EList<String>` |
| JSON format | Same: `[1.0, 2.0, 3.0]` | Same: `[1.0, 2.0, 3.0]` |
| Multi-dimensional | Supported (`double[][]`) | Not directly supported |
| Use case | Fixed-structure data (coordinates) | Variable-length collections |

**Example - Array attribute:**
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="coordinates"
    eType="#//DoubleArray1D"/>
```

**Example - Multi-valued attribute:**
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="values"
    upperBound="-1" eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EDouble"/>
```

Both serialize to JSON arrays, but array attributes support multi-dimensional nesting while multi-valued attributes provide EMF list semantics.

---

[Next: Custom Value Readers/Writers →](10-custom-values.md)
