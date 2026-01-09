# Type Serialization

[← Back to Overview](00-overview.md) | [← Global Configuration Options](04-global-options.md)

> **See also:**
> - [Serialization Strategies](01-strategies.md) for the Format × Strategy matrix
> - [Key Configuration](02-key-configuration.md) for the complete key naming conventions

---

## 1. Format × Strategy for Type

Type serialization uses **two orthogonal dimensions** (see [Serialization Strategies](01-strategies.md)):

1. **Format**: PLAIN | STRUCTURED - how the data is presented
2. **Strategy**: URI | NAME | CLASS | NUMERIC | MAPPED | SCHEMA_AND_TYPE - what information is transported

### 1.1 PLAIN Format Examples

| Strategy | Output Example |
|----------|----------------|
| URI | `"_type": "http://example.org/person/1.0#//Person"` |
| NAME | `"_type": "Person"` |
| CLASS | `"_type": "org.example.Person"` |
| NUMERIC | `"_type": "3"` |
| MAPPED | `"_type": "customer"` |
| SCHEMA_AND_TYPE | `"_schema": "http://example.org/person/1.0", "_type": "Person"` |

### 1.2 STRUCTURED Format Examples

| Strategy | Output Example |
|----------|----------------|
| URI | `"_type": { "uri": "http://example.org/person/1.0#//Person" }` |
| NAME | `"_type": { "type": "Person" }` |
| CLASS | `"_type": { "class": "org.example.Person" }` |
| NUMERIC | `"_type": { "schema": "http://example.org/person/1.0", "classifier": 3 }` |
| MAPPED | `"_type": { "discriminator": "customer" }` |
| SCHEMA_AND_TYPE | `"_type": { "schema": "http://example.org/person/1.0", "type": "Person" }` |

### 1.3 MAPPED Strategy (Discriminator-Based Polymorphism)

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

### 1.4 SCHEMA_AND_TYPE Strategy

The SCHEMA_AND_TYPE strategy transports both schema URI and type name as separate pieces of information.

**PLAIN format** - two separate top-level fields:
```json
{
  "_schema": "http://example.org/person/1.0",
  "_type": "Person"
}
```

**STRUCTURED format** - nested object with both:
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "type": "Person"
  }
}
```

**Configurable keys (PLAIN):**
- `schemaKey`: schema field (default: `_schema`)
- `typeKey`: type field (default: `_type`)

**Configurable keys (STRUCTURED):**
- `typeKey`: outer container key (default: `_type`)
- `schemaKey`: schema field inside object (default: `schema`)
- `nameKey`: type name field inside object (default: `type`)
- `superTypeKey`: supertype field inside object (default: `supertype`, optional)

### 1.5 NUMERIC Strategy

The NUMERIC strategy uses EMF classifier IDs instead of type names for compactness.

**PLAIN format** - just the classifier ID as string:
```json
{
  "_type": "3"
}
```

**STRUCTURED format** - schema + classifier ID:
```json
{
  "_type": { "schema": "http://example.org/person/1.0", "classifier": 3 }
}
```

**Configurable keys (STRUCTURED):**
- `typeKey`: outer key (default: `_type`)
- `schemaKey`: schema field (default: `schema`)
- `classifierKey`: classifier ID field (default: `classifier`)

> **Warning:** Classifier IDs are positional and can change when the model evolves. See [Global Options - NUMERIC Strategy](02-global-options.md#21-numeric-strategy---compatibility-warning) for details.

---

## 2. SuperType in STRUCTURED Format

When Type format is STRUCTURED and SuperType is enabled, supertype information is included **inside** the `_type` object as a `supertype` field:

```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "type": "Person",
    "supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
  }
}
```

SuperType values follow namespace matching rules:
- **Same namespace** as schema: simple EClass name (e.g., `"Entity"`)
- **Different namespace**: full EClass URI (e.g., `"http://audit.org/1.0#//Auditable"`)

> **See [SuperType Serialization](06-supertype.md)** for complete SuperType configuration including selection modes, presentation styles, and PLAIN format examples.

---

## 3. Type Configuration

The configuration defines **format, strategy, and keys** - not actual values. Values come from the EObject at runtime.

### 3.1 EAnnotation (on EClass)

```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.type"/>
    <details key="format" value="STRUCTURED"/>
    <details key="strategy" value="SCHEMA_AND_TYPE"/>
    <details key="typeKey" value="_type"/>
    <details key="include" value="true"/>
  </eAnnotations>
</eClassifiers>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `format` | PLAIN, STRUCTURED | PLAIN | How data is presented (flat vs nested) |
| `strategy` | URI, NAME, CLASS, NUMERIC, MAPPED, SCHEMA_AND_TYPE | URI | What type information to transport |
| `include` | true, false | true | Whether to include type info |
| `includeSupertypes` | true, false | false | Whether to include supertype info |
| `typeKey` | any string | `_type` | Outer key (both formats) |
| `schemaKey` | any string | `_schema` (PLAIN) / `schema` (STRUCTURED) | Key for schema |
| `nameKey` | any string | `type` (STRUCTURED only) | Key for type name inside object |
| `superTypeKey` | any string | `supertype` | Key for supertype array |

### 3.2 Java Builder (Runtime Override)

**Minimal (default: PLAIN format, URI strategy):**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder().build();
```
**Resulting JSON:**
```json
{
  "_type": "http://example.org/person/1.0#//Person"
}
```

**PLAIN format with NAME strategy:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .format(SerializationFormat.PLAIN)
    .strategy(TypeStrategy.NAME)
    .build();
```
**Resulting JSON:**
```json
{
  "_type": "Person"
}
```

**STRUCTURED format with SCHEMA_AND_TYPE strategy:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .format(SerializationFormat.STRUCTURED)
    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
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

**STRUCTURED format with supertypes:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .format(SerializationFormat.STRUCTURED)
    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
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
    .format(SerializationFormat.STRUCTURED)
    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
    .typeKey("@context")
    .schemaKey("@vocab")
    .nameKey("@type")
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
2. Determines output structure from `format` (PLAIN vs STRUCTURED)
3. Determines content from `strategy` (URI, NAME, SCHEMA_AND_TYPE, etc.)
4. Extracts actual values from EObject's EClass (schema URI, name, supertypes)
5. Combines config (format/strategy/keys) + EObject data (values) → JSON output

---

## 4. Default Type Settings

| Setting | Default Value | Description |
|---------|---------------|-------------|
| Format | `PLAIN` | Output as flat key-value |
| Strategy | `URI` | Full EClass URI |
| Type Key | `_type` | Outer key for type info |
| Schema Key | `_schema` (PLAIN) / `schema` (STRUCTURED) | Key for schema |
| Name Key | `type` (STRUCTURED only) | Key for type name inside object |
| Include | `true` | Whether to include type info |
| Include Supertypes | `false` | Whether to include supertype info |

**Default Output (PLAIN + URI):**
```json
{
  "_type": "http://example.org/person/1.0#//Person"
}
```

**STRUCTURED + SCHEMA_AND_TYPE Output:**
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "type": "Person"
  }
}
```

---

## 5. Deserialization

### 5.1 Format Detection

Deserializers detect the format from the JSON structure:

| Input | Detected Format | Strategy Detection |
|-------|-----------------|-------------------|
| `"_type": "string"` | PLAIN | Detect by content: URI (contains `#//`), CLASS (contains `.`), NAME/MAPPED (simple string) |
| `"_type": { ... }` | STRUCTURED | Read inner keys to determine strategy |
| `"_schema": ..., "_type": "string"` | PLAIN | SCHEMA_AND_TYPE strategy (two separate fields) |

### 5.2 Type Resolution

The deserializer resolves the EClass based on the detected format and strategy:

1. Detect format (PLAIN or STRUCTURED) from JSON structure
2. Extract type information based on strategy
3. Lookup EPackage in MetadataService by namespace URI
4. Resolve EClass by name within the package
5. Create and populate EObject

**Example (PLAIN + URI):**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "name": "John"
}
```
The deserializer extracts `http://example.org/person/1.0` as namespace URI and `Person` as class name.

**Example (STRUCTURED + SCHEMA_AND_TYPE):**
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "type": "Person"
  },
  "name": "John"
}
```

### 5.3 CODEC_ROOT_OBJECT Option

The `CODEC_ROOT_OBJECT` option specifies the expected root EClass for deserialization.

**Required when:**
- Type information is NOT present in the content (`include=false` was used during serialization)

**Optional when:**
- Type information IS present in the content (default behavior)
- Can be used as a hint or validation even when type info exists

**Usage:**
```java
Map<String, Object> options = new HashMap<>();
options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, PersonPackage.eINSTANCE.getPerson());
resource.load(inputStream, options);
```

### 5.4 Type Resolution Rules

| Content has `_type` | `CODEC_ROOT_OBJECT` set | Behavior |
|---------------------|-------------------------|----------|
| Yes | No | Use content type (default case) |
| Yes | Yes | Use content type, warn if differs from hint |
| No | Yes | Use hint type |
| No | No | **ERROR**: Cannot determine type |

When both content type and hint are present but differ:
- Log a WARNING
- Continue with content type (content wins)
- Example: Hint says `Person`, content says `Employee` → use `Employee`, log warning

---

[Next: SuperType Serialization →](06-supertype.md)
