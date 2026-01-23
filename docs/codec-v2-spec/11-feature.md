# Feature Serialization

[← Reference Serialization](10-reference.md) | [Next: Polymorphism →](12-polymorphism.md)

---

> **See also:**
> - [Naming Conventions](03-naming-conventions.md) for key naming conventions
> - [Annotation Reference](16-annotation-reference.md) (Feature Configuration) for complete configuration keys

---

Features (EStructuralFeatures) have additional serialization options beyond the metadata targets.

## 1. Null, Default, and Empty Value Handling

### 1.1 Codec-Wide Defaults

Global settings that apply to all features unless overridden:

| Annotation Key | Property Key | Global | ERef | EAttr | Default | Description |
|----------------|--------------|:------:|:----:|:-----:|---------|-------------|
| `serializeDefaults` | `codec.serializeDefaults` | ✅ | ✅ | ✅ | `false` | Include fields with default values |
| `serializeNull` | `codec.serializeNull` | ✅ | ✅ | ✅ | `false` | Include fields with explicit null values |
| `serializeEmpty` | `codec.serializeEmpty` | ✅ | ✅ | ✅ | `false` | Include empty collections |

**Java Builder (Codec-Wide):**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .serializeDefaults(false)  // Default: omit default values
    .serializeNull(false)      // Default: omit null values
    .serializeEmpty(false)     // Default: omit empty collections
    .build();
```

**Default Behavior (serializeDefaults=false, serializeNull=false, serializeEmpty=false):**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe",
  "firstName": "John",
  "lastName": "Doe"
  // "middleName" omitted (null)
  // "age" omitted (default value 0)
  // "tags" omitted (empty collection)
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

**With serializeEmpty=true:**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe",
  "firstName": "John",
  "lastName": "Doe",
  "tags": []
}
```

### 1.2 Feature Visibility Control

Control which features are serialized/deserialized using directional ignore and force options:

| Annotation Key | Property Key | ERef | EAttr | Default | Description |
|----------------|--------------|:----:|:-----:|---------|-------------|
| `ignore` | `codec.ignore` | ✅ | ✅ | `false` | Skip both read AND write |
| `ignoreRead` | `codec.ignoreRead` | ✅ | ✅ | `false` | Skip deserialization only |
| `ignoreWrite` | `codec.ignoreWrite` | ✅ | ✅ | `false` | Skip serialization only |
| `forceRead` | `codec.forceRead` | ✅ | ✅ | `false` | Force read EMF transient/volatile |
| `forceWrite` | `codec.forceWrite` | ✅ | ✅ | `false` | Force write EMF transient/volatile |

**Note:** These options are per-feature only (not supported on EClass).

**EAnnotation (on EStructuralFeature):**

```xml
<!-- Ignore feature completely (both read and write) -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="internalCache">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="ignore" value="true"/>
  </eAnnotations>
</eStructuralFeatures>

<!-- Ignore on write only (read from JSON but don't write back) -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="importedField">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="ignoreWrite" value="true"/>
  </eAnnotations>
</eStructuralFeatures>

<!-- Force write volatile/derived feature -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="coordinates" volatile="true">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="forceWrite" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Resolution logic:**
```
Serialization:
1. If feature in ignoreFeatures OR ignoreWrite=true OR ignore=true → skip
2. If EMF transient/volatile/derived → check forceWrite
   - forceWrite=true → continue
   - forceWrite=false → skip
3. Serialize normally

Deserialization:
1. If feature in ignoreFeatures OR ignoreRead=true OR ignore=true → skip
2. If EMF transient/volatile → check forceRead
   - forceRead=true → continue (feature must be changeable)
   - forceRead=false → skip
3. Deserialize normally
```

**Use cases:**
- `ignore=true`: Completely hide a feature from JSON (both directions)
- `ignoreWrite=true`: Read from JSON but don't write back (one-way import)
- `ignoreRead=true`: Write to JSON but ignore during read (computed output fields)
- `forceWrite=true`: Serialize volatile/derived features (e.g., GeoJSON coordinates)
- `forceRead=true`: Deserialize into volatile/derived features (roundtrip support)

### 1.3 Feature-Specific Value Overrides

Override codec-wide settings for individual features:

| Annotation Key | Property Key | ERef | EAttr | Default | Description |
|----------------|--------------|:----:|:-----:|---------|-------------|
| `serializeNull` | `codec.serializeNull` | ✅ | ✅ | `false` | Include null values in output |
| `serializeDefaults` | `codec.serializeDefaults` | ✅ | ✅ | `false` | Include default values in output |
| `serializeEmpty` | `codec.serializeEmpty` | ✅ | ✅ | `false` | Include empty collections in output |

**Semantics:** `true` = include in output, `false` = omit from output. Feature-level overrides codec-wide.

**EAnnotation (on EStructuralFeature):**

```xml
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

**Java Builder (Runtime Override):**
```java
// Ignore feature completely
FeatureConfigBuilder.forFeature("internalCache")
    .ignore(true)
    .build();

// Force serialize null for specific feature (override codec default)
FeatureConfigBuilder.forFeature("middleName")
    .serializeNull(true)
    .build();

// Skip default values for specific feature
FeatureConfigBuilder.forFeature("counter")
    .serializeDefaults(false)
    .build();
```

**Resolution Order:**
1. Feature-specific setting (if defined)
2. Codec-wide setting
3. Built-in default

### 1.4 Runtime Ignore List

The `ignoreFeatures` property provides runtime bulk ignore without model annotations:

| Property Key | Global | EClass | Description |
|--------------|:------:|:------:|-------------|
| `codec.ignoreFeatures` | 🔧 | 🔧 | Features to ignore (both read+write) |

**Note:** This is a **runtime-only** property (no EAnnotation support).

**Accepts:**
- Comma-separated string: `"internalId,debugInfo,tempCache"`
- `List<String>`: `List.of("internalId", "debugInfo", "tempCache")`
- `List<EStructuralFeature>`: `List.of(MyPackage.Literals.MY_CLASS__INTERNAL_ID, ...)`

**Property Map:**
```java
Map<String, Object> options = Map.of(
    "codec.ignoreFeatures", "internalId,debugInfo,tempCache"
);

// Or with list
Map<String, Object> options = Map.of(
    "codec.ignoreFeatures", List.of("internalId", "debugInfo")
);
```

### 1.5 Deserialization Behavior

The `serializeNull`, `serializeDefaults`, and `serializeEmpty` settings are **serialization-only** configurations. They control what is written to JSON but do **NOT** affect how JSON is read.

Deserialization follows these rules:

| JSON Input | Deserialization Result |
|------------|------------------------|
| Field with explicit `null` value | See type-specific behavior below |
| Field missing from JSON | Feature retains EMF default value |
| Field with value | Sets feature to parsed value |

#### Type-Specific Null Handling

EMF handles `null` differently for object types vs primitive types:

| Feature Type | JSON `null` Result | Reason |
|--------------|-------------------|--------|
| **Object types** (EString, EObject, etc.) | Sets to `null` | Object references can hold null |
| **Primitive types** (EInt, EBoolean, EDouble, etc.) | Resets to EMF default | Primitives cannot hold null; EMF treats null as "unset" |

**Important:** For primitive types, `eSet(attribute, null)` resets to the `defaultValueLiteral`, NOT the Java primitive default (0, false).

**Example:**

Given an EMF model with:
```java
EAttribute middleName;  // EString, optional (no default)
EAttribute count;       // EInt, defaultValueLiteral="42"
EAttribute active;      // EBoolean, defaultValueLiteral="true"
```

| JSON Input | middleName | count | active |
|------------|------------|-------|--------|
| `{"middleName": null, "count": 10}` | `null` | `10` | `true` (EMF default) |
| `{"count": 10}` | `null` (String default) | `10` | `true` (EMF default) |
| `{"middleName": "M", "count": null}` | `"M"` | `42` (EMF default¹) | `true` (EMF default) |
| `{}` | `null` | `42` (EMF default) | `true` (EMF default) |

¹ Note: `count: null` results in `42` (the EMF defaultValueLiteral), NOT `0` (Java int default).

**Key Points:**

1. **Explicit `null` for object types** → Feature is set to `null`
2. **Explicit `null` for primitive types** → Feature is reset to EMF default (via `eSet(attr, null)`)
3. **Missing field** → Feature retains its EMF default value (same effect as `null` for primitives)
4. **`serializeNull`/`serializeDefaults` do NOT affect deserialization** - these are output controls only
5. **Multi-valued features** → JSON `null` is ignored; missing field results in empty list

**Rationale:**

- **Object types respect explicit `null`**: If the JSON explicitly contains `"field": null`, the intent is to set the value to null.
- **Primitive types use EMF semantics**: EMF primitives cannot hold null, so null is interpreted as "unset" which returns the default value.
- **Missing fields use defaults**: This aligns with JSON conventions where omitted fields are unspecified, and EMF defaults provide sensible fallback values.
- **Symmetry not required**: Serialization decisions (what to include) are independent from deserialization decisions (how to interpret).

---

## 2. Feature Key Customization

Override the JSON property name for a feature.

| Annotation Key | Property Key | ERef | EAttr | Default | Description |
|----------------|--------------|:----:|:-----:|---------|-------------|
| `key` | `codec.key` | ✅ | ✅ | (feature name) | Custom JSON property name |

**Note:** Feature key customization is per-feature only (not supported on EClass).

**Resolution Order (highest to lowest priority):**
1. **Config Builder** - Runtime configuration override
2. **Model Annotation** - `<details key="key" value="..."/>` on EStructuralFeature
3. **ExtendedMetaData name** - Only when `useNamesFromExtendedMetadata=true` (see [Section 3](#3-extended-metadata-names))
4. **Feature Name** - Default EMF feature name

### 2.1 Model Annotation

Customize feature names directly in the EMF model using codec annotations:
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
FeatureConfigBuilder.forFeature("firstName")
    .key("givenName")  // Overrides annotation
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

| Property Key | Global | Default | Description |
|--------------|:------:|---------|-------------|
| `codec.useNamesFromExtendedMetadata` | 🔧 | `false` | Use ExtendedMetaData names |

**Note:** This is a **runtime-only** property (no EAnnotation support).

### 3.1 Configuration

**Java Builder:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .useNamesFromExtendedMetadata(true)
    .build();
```

**Property Map:**
```java
Map<String, Object> options = Map.of(
    "codec.useNamesFromExtendedMetadata", true
);
```

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

| Annotation Key | Property Key | Global | EAttr | Default | Description |
|----------------|--------------|:------:|:-----:|---------|-------------|
| `enumSerialization` | `codec.enumSerialization` | ✅ | ✅ | `LITERAL` | How to serialize enum values |

**Note:** `enumSerialization` does not apply to EReference (enums are attributes).

**EnumSerialization Values:**

| Value | Description | Example |
|-------|-------------|---------|
| `LITERAL` **(default)** | Use enum literal name | `"status": "ACTIVE"` |
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
CodecConfiguration config = CodecConfiguration.builder()
    .enumSerialization(EnumSerialization.LITERAL)
    .build();

// Feature-specific override
FeatureConfigBuilder.forFeature("status")
    .enumSerialization(EnumSerialization.VALUE)
    .build();
```

**Property Map:**
```java
// Codec-wide
Map<String, Object> options = Map.of(
    "codec.enumSerialization", "LITERAL"
);
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

| Setting | Property Key | Default Value |
|---------|--------------|---------------|
| Ignore | `codec.ignore` | `false` |
| Ignore Read | `codec.ignoreRead` | `false` |
| Ignore Write | `codec.ignoreWrite` | `false` |
| Force Read | `codec.forceRead` | `false` |
| Force Write | `codec.forceWrite` | `false` |
| Serialize Null | `codec.serializeNull` | `false` |
| Serialize Defaults | `codec.serializeDefaults` | `false` |
| Serialize Empty | `codec.serializeEmpty` | `false` |
| Enum Serialization | `codec.enumSerialization` | `LITERAL` |
| Use Names From ExtendedMetaData | `codec.useNamesFromExtendedMetadata` | `false` |
| Ignore Features | `codec.ignoreFeatures` | Empty (none) |

---

## 6. Force Serialize Volatile/Transient Features

By default, EMF features marked as `volatile`, `transient`, or `derived` are **not serialized**. This is because:
- **Transient** features are meant to be non-persistent
- **Derived** features are computed from other features
- **Volatile** features don't have storage and are computed on-the-fly

However, some models (like GeoJSON) use volatile features to provide computed views of data that **must** be serialized. The `forceWrite` and `forceRead` configuration allows overriding this behavior.

### 6.1 Configuration

Use per-feature `forceWrite` and `forceRead` annotations (see [Section 1.2](#12-feature-visibility-control)):

**EAnnotation:**
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="data" volatile="true">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="forceWrite" value="true"/>
    <details key="forceRead" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Java Builder:**
```java
// Force serialize specific volatile feature
FeatureConfigBuilder.forFeature("data")
    .forceWrite(true)
    .forceRead(true)
    .build();
```

### 6.2 Resolution Logic

When determining if a feature should be serialized:

1. Check if `ignore=true` OR `ignoreWrite=true` OR in `ignoreFeatures` → skip
2. Check if volatile/transient/derived:
   - If `forceWrite=true` → continue
   - Otherwise → skip
3. Serialize normally

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

**EAnnotation on volatile feature:**
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="data"
    eType="#//DoubleArray1D" volatile="true">
  <eAnnotations source="http:///org/eclipse/emf/ecore/util/ExtendedMetaData">
    <details key="name" value="coordinates"/>
  </eAnnotations>
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="forceWrite" value="true"/>
    <details key="forceRead" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Configuration:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .typeKey("type")
    .typeStrategy(TypeStrategy.NAME)
    .useNamesFromExtendedMetadata(true)
    .build();
```

**Output:**
```json
{"type": "Point", "coordinates": [8.6821, 50.1109]}
```

Without `forceWrite=true`, the output would only be:
```json
{"type": "Point"}
```

### 6.4 Deserialization

The `forceRead` configuration enables deserialization of volatile features from JSON, allowing round-trip support for computed attributes.

> **Note:** For deserialization to work, the volatile feature must still be **changeable** (`isChangeable()=true`). Non-changeable features cannot be set even with `forceRead=true`.

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

## 8. JSON Structure to String Conversion (Deserialization Only)

When a String-typed EAttribute encounters a JSON object or array during deserialization, the codec converts the entire structure to a JSON string representation. This is a **deserialization-only** feature and does not support lossless roundtrips.

### 8.1 Use Case

Some schemas (like OpenAPI/JSON Schema) define string-typed attributes that can accept any JSON value. For example, the OpenAPI Schema `default` field can be:
- A string: `"default": "hello"`
- A number: `"default": 42`
- An object: `"default": {"timeout": 30}`
- An array: `"default": [1, 2, 3]`

When the EMF model defines such an attribute as `EString`, the codec needs to handle objects and arrays gracefully.

### 8.2 Deserialization Behavior

When a String-typed attribute encounters:

| Input JSON | Result String |
|------------|---------------|
| `{"timeout": 30}` | `"{\"timeout\":30}"` |
| `[1, 2, 3]` | `"[1,2,3]"` |
| `{"nested": {"a": [1]}}` | `"{\"nested\":{\"a\":[1]}}"` |
| `[]` | `"[]"` |
| `{}` | `"{}"` |

The JSON structure is serialized to a compact JSON string without extra whitespace.

### 8.3 Roundtrip Limitation

**Important:** This conversion is **not roundtrip-safe**.

- **Input:** `{"default": {"timeout": 30}}`
- **After deserialization:** `default = "{\"timeout\":30}"` (String)
- **After serialization:** `{"default": "{\"timeout\":30}"}` (escaped string)

The re-serialized output contains an escaped string, not the original JSON object.

### 8.4 Recommended Solution for Roundtrips

If roundtrip preservation is required, use `EJavaObject` instead of `EString`:

```xml
<!-- Recommended for any-value attributes -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="default"
    eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EJavaObject"/>
```

With `EJavaObject`:
- JSON object → `Map<String, Object>` (preserves structure)
- JSON array → `List<Object>` (preserves structure)
- Roundtrip works correctly

### 8.5 When to Use String vs EJavaObject

| Scenario | Use `EString` | Use `EJavaObject` |
|----------|---------------|-------------------|
| Import-only (no re-export) | ✓ (simpler) | ✓ |
| Roundtrip required | ✗ | ✓ |
| Need to inspect structure | ✗ | ✓ |
| Just need string representation | ✓ | Possible |

---

## 9. EJavaObject Attributes

EAttributes with type `EJavaObject` (instance class `Object.class`) can hold any JSON value. The codec provides full support for deserializing and serializing these dynamic-typed attributes.

### 9.1 Defining EJavaObject Attributes

```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="metadata"
    eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EJavaObject"/>
```

### 9.2 Deserialization Behavior

When deserializing an `EJavaObject` attribute, JSON values are converted to native Java types:

| JSON Type | Java Type |
|-----------|-----------|
| String | `String` |
| Integer | `Long` |
| Decimal | `Double` |
| Boolean | `Boolean` |
| Null | `null` |
| Array | `List<Object>` |
| Object | `Map<String, Object>` (LinkedHashMap) |

**Example:**

```json
{
  "metadata": {
    "enabled": true,
    "count": 42,
    "tags": ["a", "b"],
    "config": {"timeout": 30}
  }
}
```

After deserialization:
```java
Object metadata = eObject.eGet(metadataAttribute);
// metadata is LinkedHashMap<String, Object>
Map<String, Object> map = (Map<String, Object>) metadata;
Boolean enabled = (Boolean) map.get("enabled");     // true
Long count = (Long) map.get("count");               // 42L
List<?> tags = (List<?>) map.get("tags");           // ["a", "b"]
Map<?, ?> config = (Map<?, ?>) map.get("config");   // {timeout=30}
```

### 9.3 Serialization Behavior

When serializing an `EJavaObject` attribute, Java types are converted to JSON:

| Java Type | JSON Type |
|-----------|-----------|
| `String` | String |
| `Number` (Integer, Long, Double, etc.) | Number |
| `Boolean` | Boolean |
| `null` | null |
| `List`, `Collection` | Array |
| `Map` | Object |

This enables **lossless roundtrips** for any JSON value.

### 9.4 Use Cases

**1. Schema-less metadata:**
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="extensions"
    eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EJavaObject"/>
```

**2. OpenAPI/JSON Schema default values:**
```xml
<!-- Schema default can be any JSON value -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="default"
    eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EJavaObject"/>
```

**3. Dynamic configuration:**
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="options"
    eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EJavaObject"/>
```

### 9.5 Type Safety Considerations

Since `EJavaObject` accepts any value, type checking must be done at runtime:

```java
Object value = eObject.eGet(metadataAttribute);
if (value instanceof Map<?, ?> map) {
    // Handle object
} else if (value instanceof List<?> list) {
    // Handle array
} else if (value instanceof String str) {
    // Handle string
} else if (value instanceof Number num) {
    // Handle number
} else if (value instanceof Boolean bool) {
    // Handle boolean
}
```

### 9.6 Nested Structures

Nested JSON structures are fully supported. Arrays within objects, objects within arrays, and arbitrary nesting depths are all preserved:

```json
{
  "metadata": {
    "level1": {
      "level2": {
        "values": [1, 2, {"nested": true}]
      }
    }
  }
}
```

All nested structures are recursively converted to `Map<String, Object>` and `List<Object>`.

---

## 10. Custom Value Readers and Writers

For features requiring custom serialization logic, you can specify named value reader/writer services.

| Annotation Key | Property Key | ERef | EAttr | Default | Description |
|----------------|--------------|:----:|:-----:|---------|-------------|
| `valueReaderName` | `codec.valueReaderName` | ✅ | ✅ | (none) | Custom value reader service name |
| `valueWriterName` | `codec.valueWriterName` | ✅ | ✅ | (none) | Custom value writer service name |

**EAnnotation:**
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="timestamp">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="valueReaderName" value="isoDateReader"/>
    <details key="valueWriterName" value="isoDateWriter"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Java Builder:**
```java
FeatureConfigBuilder.forFeature("timestamp")
    .valueReaderName("isoDateReader")
    .valueWriterName("isoDateWriter")
    .build();
```

**Use cases:**
- Custom date/time formatting
- Domain-specific value transformations
- Legacy format compatibility

---

## 11. Unknown Field and Strictness Handling

When deserializing JSON, fields may appear that don't correspond to any EMF feature in the target EClass. The strictness configuration controls how such mismatches are handled.

### 11.1 Strictness Configuration

| Annotation Key | Property Key | Global | EClass | ERef | EAttr | Default | Description |
|----------------|--------------|:------:|:------:|:----:|:-----:|---------|-------------|
| `strictOnUnknown` | `codec.strictOnUnknown` | ✅ | ✅ | ✅ | ✅ | `false` | ERROR on unknown JSON field |
| `strictOnMissing` | `codec.strictOnMissing` | ✅ | ✅ | ✅ | ✅ | `false` | ERROR on missing required feature |

**Behavior:**

| Mode | Unknown Field | Missing Required Field |
|------|---------------|------------------------|
| **LENIENT (default)** | Warning + skip | Warning + use default |
| **STRICT** (`strict*=true`) | Error + fail | Error + fail |

**Scope behavior:**
- **Global:** Applies to all features everywhere
- **EClass:** Applies to all features of that class
- **EReference:** Applies when deserializing objects through this reference
- **EAttribute:** Applies to this specific attribute only

### 11.2 Default Behavior (LENIENT)

Unknown fields generate a **WARNING** and are **skipped**:

```json
{
  "_type": "http://example.org/1.0#//Person",
  "firstName": "John",
  "unknownField": "some value",
  "anotherUnknown": 123
}
```

**Result:**
- `firstName` is set on the Person object
- `unknownField` and `anotherUnknown` are skipped
- Warnings are added to `resource.getWarnings()`
- Deserialization completes successfully

### 11.3 Diagnostic Message

Unknown fields produce the following warning:

| Severity | Message Template | Source |
|----------|------------------|--------|
| WARNING | `Unknown feature '{name}' for EClass {class}` | AttributeDeserializationEntry |

### 11.4 Accessing Unknown Field Warnings

```java
resource.load(inputStream, options);

for (Diagnostic warning : resource.getWarnings()) {
    if (warning.getMessage().contains("Unknown feature")) {
        System.out.println("Unknown field: " + warning.getMessage());
    }
}
```

### 11.5 Strictness Use Cases

- `strictOnUnknown=true` at Global: Fail-fast for any unexpected JSON field (strict schema validation)
- `strictOnMissing=true` on EAttribute: Ensure this specific required field is always present
- `strictOnUnknown=false` on EReference: Allow extension fields in objects accessed via this reference (forward compatibility)

---

[Next: Polymorphism →](12-polymorphism.md)
