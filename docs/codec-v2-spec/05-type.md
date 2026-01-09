# Type Serialization

[← Back to Overview](00-overview.md) | [← Global Configuration Options](04-global-options.md)

> **See also:** [Key Configuration](02-key-configuration.md) for the complete key naming conventions.

---

## 1. Type Strategies

### 1.1 Plain Strategies (string value for `_type`)

| Strategy | Value | Example |
|----------|-------|---------|
| `NAME` | EClass name | `"_type": "Person"` |
| `CLASS` | Instance class name | `"_type": "org.example.Person"` |
| `URI` | Full EClass URI | `"_type": "http://example.org/person/1.0#//Person"` |
| `MAPPED` | Discriminator value | `"_type": "customer"` |
| `SCHEMA_AND_TYPE` | Name + separate schema field | `"_schema": "...", "_type": "Person"` |

### 1.2 MAPPED Strategy (Discriminator-Based Polymorphism)

The MAPPED strategy enables type discrimination based on a value found within the data itself, rather than an explicit type field. This is particularly useful for:
- **IoT/LoRaWAN devices** - where device type is embedded in payload metadata
- **JSON Schema oneOf patterns** - where type is determined by feature presence or values
- **Legacy APIs** - where type information is encoded in application-specific fields

**How it works:**

1. **Base class** defines the discriminator feature path (which field contains the type indicator)
2. **Concrete classes** define their discriminator value (what value identifies them)
3. **ModelInfoService** builds a reverse lookup map from values → EClasses

**Configuration (via annotations or CodecConfig):**

```java
// On base class (e.g., LoRaWANUplink)
TypeResolutionConfig.builder()
    .strategy(TypeStrategy.MAPPED)
    .typeFeaturePath("deviceInfo.deviceProfileName")  // Path to discriminator
    .build();

// On concrete class (e.g., DraginoLSE01Uplink)
TypeResolutionConfig.builder()
    .strategy(TypeStrategy.MAPPED)
    .discriminatorValue("Dragino_LSE01")  // Value that identifies this class
    .build();
```

**Example Input JSON (no explicit type field):**
```json
{
  "deviceInfo": {
    "deviceProfileName": "Dragino_LSE01",
    "devEui": "A84041..."
  },
  "temperature": 23.5,
  "humidity": 65
}
```

The deserializer reads `deviceInfo.deviceProfileName`, finds `"Dragino_LSE01"`, and uses the reverse lookup to instantiate `DraginoLSE01Uplink`.

**Feature-Based Type Discrimination (JSON Schema oneOf):**

For cases where type is determined by feature presence rather than a single discriminator value:

```java
TypeResolutionConfig.builder()
    .strategy(TypeStrategy.MAPPED)
    .typeFeaturePath("*")  // Special marker for feature-based detection
    .typeMap(Map.of(
        "temperature,humidity", "EnvironmentSensor",
        "latitude,longitude", "GPSTracker"
    ))
    .build();
```

The deserializer checks which features are present and matches against the type map.

### 1.3 SCHEMA_AND_TYPE Strategy (Plain)

Split into separate top-level fields (each value is a simple string):

```json
{
  "_schema": "http://example.org/person/1.0",
  "_type": "Person"
}
```

**Configurable keys:**
- `schemaKey`: schema field (default: `_schema`)
- `typeKey`: type field (default: `_type`)

### 1.4 Structured Strategies (object value for `_type`)

#### STRUCTURED Strategy

Nested object containing type details:

```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "type": "Person",
    "supertype": ["Entity", "Auditable"]
  }
}
```

**Configurable keys:**
- `rootTypeKey`: outer container key (default: `_type`)
- `schemaKey`: schema field inside object (default: `schema`)
- `typeKey`: type name field inside object (default: `type`)
- `superTypeKey`: supertype field inside object (default: `supertype`, optional)

#### NUMERIC Strategy

Nested object with schema URI and numeric classifier ID:

```json
{
  "_type": { "s": "http://example.org/person/1.0", "c": 3 }
}
```

**Configurable keys:**
- `typeKey`: outer key (default: `_type`)
- `schemaKey`: schema field (default: `s`)
- `classifierKey`: classifier ID field (default: `c`)

> **Warning:** Classifier IDs are positional and can change when the model evolves. See [Global Options - NUMERIC Strategy](02-global-options.md#21-numeric-strategy---compatibility-warning) for details.

---

## 2. Mixed Supertype Format

In STRUCTURED format, supertypes use a **smart format**:
- Supertypes from **same schema**: plain name only
- Supertypes from **different schema**: full URI

```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "type": "Person",
    "supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
  }
}
```

This is compact yet unambiguous - `Entity` is from `http://example.org/person/1.0`, while `Auditable` includes its full URI.

---

## 3. Type Configuration

The configuration defines **keys and format**, not actual values. Values come from the EObject at runtime.

### 3.1 EAnnotation (on EClass)

```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.type"/>
    <details key="strategy" value="STRUCTURED"/>
    <details key="typeKey" value="_type"/>
    <details key="include" value="true"/>
  </eAnnotations>
</eClassifiers>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `strategy` | NAME, CLASS, URI, MAPPED, STRUCTURED, SCHEMA_AND_TYPE, NUMERIC | URI | Type serialization strategy |
| `rootTypeKey` | any string | `_type` | Container key (STRUCTURED) / type key (PLAIN) |
| `include` | true, false | true | Whether to include type info |
| `schemaKey` | any string | `_schema` (PLAIN) / `schema` (STRUCTURED) | Key for schema |
| `typeKey` | any string | `_type` (PLAIN) / `type` (STRUCTURED) | Key for type name |

### 3.2 Java Builder (Runtime Override)

**Minimal (default: URI strategy):**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder().build();
```
**Resulting JSON:**
```json
{
  "_type": "http://example.org/person/1.0#//Person"
}
```

**STRUCTURED format:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .structured()
    .build();
```
**Resulting JSON:**
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "type": "Person"
  }
}
```

**STRUCTURED with supertypes:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .structured()
    .includeSupertypes(true)
    .build();
```
**Resulting JSON:**
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "type": "Person",
    "supertype": ["Entity", "Auditable"]
  }
}
```

**With custom keys:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .structured()
    .rootTypeKey("@context")
    .schemaKey("@vocab")
    .typeKey("@type")
    .build();
```
**Resulting JSON:**
```json
{
  "@context": {
    "@vocab": "http://example.org/person/1.0",
    "@type": "Person"
  }
}
```

**At serialization time:**
1. Serializer reads `TypeSerializationConfig` from options (Java or JSON)
2. Extracts actual values from EObject's EClass (schema URI, name, supertypes)
3. Combines config (keys/format) + EObject data (values) → JSON output

---

## 4. Default Type Settings

| Setting | PLAIN Default | STRUCTURED Default |
|---------|---------------|-------------------|
| Strategy | `URI` | `STRUCTURED` |
| Root Type Key | - | `_type` |
| Type Key | `_type` | `type` |
| Schema Key | `_schema` | `schema` |
| Include | `true` | `true` |

**Default Output:**
```json
{
  "_type": "http://example.org/person/1.0#//Person"
}
```

---

## 5. Deserialization

### 5.1 Format Detection

Deserializers MUST detect the format used and handle accordingly:

| Input | Detection |
|-------|-----------|
| `"_type": "string"` | PLAIN (detect NAME/CLASS/URI/MAPPED by content) |
| `"_type": { ... }` | STRUCTURED |
| `"_schema": ..., "_type": ...` | SCHEMA_AND_TYPE |

### 5.2 Backward Compatibility

Deserializers MUST support reading:
- V1 format (always PLAIN)
- V2 PLAIN format
- V2 STRUCTURED format
- V2 SCHEMA_AND_TYPE format

The deserializer should auto-detect the format regardless of codec configuration.

### 5.3 Type Resolution

**Default Behavior (URI strategy):**

When type information IS present in the content (the default case with `include=true` and `strategy=URI`), the deserializer resolves the EClass automatically:

1. Read type information (any format: PLAIN, STRUCTURED, SCHEMA_AND_TYPE)
2. Extract schema/namespace URI and class name
3. Lookup EPackage in MetadataService by namespace URI
4. Resolve EClass by name within the package
5. Create and populate EObject

**Example with URI strategy (default):**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "name": "John"
}
```
The deserializer extracts `http://example.org/person/1.0` as namespace URI and `Person` as class name.

### 5.4 CODEC_ROOT_OBJECT Option

The `CODEC_ROOT_OBJECT` option specifies the expected root EClass for deserialization. This option is:

**Required when:**
- Type information is NOT present in the content (`include=false` was used during serialization)
- Type serialization is globally disabled
- The content was produced by a system that doesn't include type metadata

**Optional when:**
- Type information IS present in the content (default behavior)
- Can be used as a hint or validation even when type info exists

**Usage:**
```java
Map<String, Object> options = new HashMap<>();
options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, PersonPackage.eINSTANCE.getPerson());
resource.load(inputStream, options);
```

### 5.5 Type Resolution Rules

#### Content Type vs Hint Type

| Content has `_type` | `CODEC_ROOT_OBJECT` set | Behavior |
|---------------------|-------------------------|----------|
| Yes | No | Use content type (default case) |
| Yes | Yes | Use content type, warn if differs from hint |
| No | Yes | Use hint type |
| No | No | **ERROR**: Cannot determine type |

#### Type Collision Warning

When both content type and hint are present but differ:
- Log a WARNING
- Continue with content type (content wins)
- Example: Hint says `Person`, content says `Employee` → use `Employee`, log warning

---

[Next: SuperType Serialization →](06-supertype.md)
