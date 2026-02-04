# Type Serialization

[← Back to Overview](00-overview.md) | [← Global Configuration Options](04-global-options.md)

> **See also:**
> - [Serialization Strategies](01-strategies.md) for the Format × Strategy matrix
> - [Key Configuration](02-key-configuration.md) for the complete key naming conventions

---

## 1. Format × Strategy for Type

Type serialization uses **two orthogonal dimensions** (see [Serialization Strategies](01-strategies.md)):

1. **Format**: PLAIN | STRUCTURED - how the data is presented
2. **Strategy**: URI | NAME | CLASS | NUMERIC | SCHEMA_AND_TYPE | NONE - what information is transported

> **Note:** `MAPPED` was removed from TypeStrategy. Discriminator-based type resolution is now a separate optional layer that works alongside any strategy. See [Discriminator Mapping](#13-discriminator-mapping-separate-layer) below for details.

### 1.1 PLAIN Format Examples

| Strategy | Output Example |
|----------|----------------|
| URI | `"_type": "http://example.org/person/1.0#//Person"` |
| NAME | `"_type": "Person"` |
| CLASS | `"_type": "org.example.Person"` |
| NUMERIC | `"_type": "3"` |
| SCHEMA_AND_TYPE | `"_schema": "http://example.org/person/1.0", "_type": "Person"` |
| NONE | (no type field written) |

### 1.2 STRUCTURED Format Examples

| Strategy | Output Example |
|----------|----------------|
| URI | `"_type": { "uri": "http://example.org/person/1.0#//Person" }` |
| NAME | `"_type": { "type": "Person" }` |
| CLASS | `"_type": { "class": "org.example.Person" }` |
| NUMERIC | `"_type": { "schema": "http://example.org/person/1.0", "classifier": 3 }` |
| SCHEMA_AND_TYPE | `"_type": { "schema": "http://example.org/person/1.0", "type": "Person" }` |
| NONE | (no type field written) |

### 1.3 Discriminator Mapping (Separate Layer)

Discriminator-based type resolution is **not** a TypeStrategy - it's an **orthogonal layer** that works alongside any strategy. This enables type discrimination based on a value found within the data itself.

**Why it's separate from TypeStrategy:**

| Aspect | TypeStrategy (URI, NAME, etc.) | Discriminator Mapping |
|--------|-------------------------------|----------------------|
| **What it answers** | "What value represents this type?" | "How do I translate an arbitrary string to a type?" |
| **Serialization output** | Deterministic from EClass | Requires external mapping definition |
| **Deserialization input** | Self-describing (URI resolvable) | Requires registry lookup |
| **Standalone?** | Yes | No - needs discriminator definitions |

**Use cases:**
- **IoT/LoRaWAN devices** - where device type is embedded in payload metadata
- **JSON Schema oneOf patterns** - where type is determined by feature presence or values
- **Legacy APIs** - where type information is encoded in application-specific fields

**Two configuration approaches:**

#### Named Registry (on EClass annotations)

```xml
<!-- Base class defines the map and discriminator path -->
<eClassifiers name="UplinkMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="typeMapId" value="lorawan-devices"/>
    <details key="typeDiscriminatorPath" value="info.profileName"/>
  </eAnnotations>
</eClassifiers>

<!-- Concrete classes register their discriminator value -->
<eClassifiers name="TemperatureSensor">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="typeDiscriminator" value="temp-sensor"/>
  </eAnnotations>
</eClassifiers>
```

#### Inline Mapping (on EReference annotations)

```xml
<eStructuralFeatures name="contacts" upperBound="-1" eType="#//Contact">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="typeKey" value="contactType"/>
    <details key="inlineMapping.friend" value="http://example.org#//Friend"/>
    <details key="inlineMapping.enemy" value="http://example.org#//Enemy"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Resolution priority during deserialization:**
1. Discriminator Mapping (if configured) - inline mapping first, then named registry
2. Type Strategy Resolution (URI, NAME, etc.)
3. Fallback (CODEC_ROOT_OBJECT hint, reference type)

### 1.4 NONE Strategy

The `NONE` strategy indicates that no type information should be written or expected.

**Serialization with NONE:**
- No type field (`_type`) is written
- Useful for homogeneous collections, APIs that don't expect type metadata, or compact output

**Deserialization with NONE:**
- Deserializer does NOT look for type field in JSON
- Type resolution relies entirely on:
  1. `CODEC_ROOT_OBJECT` load option (for root object)
  2. `EReference.getEReferenceType()` (for nested objects)
- **ERROR** if reference type is abstract and no concrete type can be determined

**Example:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.NONE)
    .build();

// Serialization output - no _type field
// { "name": "John", "age": 30 }

// Deserialization - MUST provide CODEC_ROOT_OBJECT
Map<String, Object> options = Map.of(
    CodecResource.CODEC_ROOT_OBJECT, PersonPackage.Literals.PERSON
);
resource.load(input, options);
```

### 1.6 SCHEMA_AND_TYPE Strategy

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

### 1.7 Type Strategy Scope and Containment Behavior

Type strategy configuration applies **per-class**, not globally to all objects in a hierarchy. This has important implications for containment references.

#### 1.7.1 Strategy Scope Rules

| Strategy | Applies To | Children Behavior |
|----------|-----------|-------------------|
| **URI** | Configured class | Children use their own configured strategy (or default URI) |
| **SCHEMA_AND_TYPE** | **Root object only** | Children fall back to default (URI) |
| **NAME** | Configured class | Children use their own configured strategy (or default URI) |
| **CLASS** | Configured class | Children use their own configured strategy |
| **NUMERIC** | Configured class | Children use their own configured strategy |
| **NONE** | Configured class | Children use their own configured strategy |

**Key Rule:** SCHEMA_AND_TYPE is a **root-only strategy**. It establishes a context schema at the root level but does not propagate to contained objects.

#### 1.5.2 Example: SCHEMA_AND_TYPE with Containments

**Model:**
- `Company` (configured with SCHEMA_AND_TYPE)
- `Person` (no configuration → uses default URI)
- `Company.employees: Person[*]` (containment)

**Configuration on Company:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Company">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="typeStrategy" value="SCHEMA_AND_TYPE"/>
  </eAnnotations>
  ...
</eClassifiers>
```

**Output:**
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "Company",
  "name": "Acme Inc",
  "employees": [
    {
      "_type": "http://example.org/1.0#//Person",
      "name": "Alice"
    },
    {
      "_type": "http://example.org/1.0#//Person",
      "name": "Bob"
    }
  ]
}
```

**Observations:**
- Root `Company`: uses `_schema` + simple `_type` (SCHEMA_AND_TYPE strategy)
- Contained `Person`: uses full URI (default strategy, not inherited from parent)

#### 1.5.3 Combining with Smart Compression

To achieve compact output where contained objects use simple type names, enable **Smart Compression** (see [Global Options - Smart Compression](04-global-options.md#1-smart-compression)).

**With Smart Compression enabled:**
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "Company",
  "name": "Acme Inc",
  "employees": [
    {
      "_type": "Person",
      "name": "Alice"
    }
  ]
}
```

Smart Compression uses the root's schema context to simplify same-schema types to simple names.

#### 1.5.4 Why No Per-Reference Strategy Override?

Per-reference type strategy configuration is intentionally **not supported**. Reasons:

1. **Complexity vs. Value**: Adds significant configuration complexity for edge cases
2. **Consistency**: Same class appearing differently in different contexts is confusing
3. **Smart Compression Alternative**: Most use cases for per-reference configuration are better served by Smart Compression
4. **Deserialization Ambiguity**: Would require knowing which strategy was used per-reference during deserialization

If you need custom type handling for specific references, consider using custom value readers/writers at the reference level (see [Custom Value Readers/Writers](10-custom-values.md)).

---

### 1.6 NUMERIC Strategy

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

> **Warning:** Classifier IDs are positional and can change when the model evolves. See [Global Options - NUMERIC Strategy](04-global-options.md#2-numericindexed-strategy) for details.

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
| `strategy` | URI, NAME, CLASS, NUMERIC, SCHEMA_AND_TYPE, NONE | URI | What type information to transport |
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

### 5.1 Type Key Recognition

The deserializer automatically recognizes the following property names as type discriminators:

| Key | Description |
|-----|-------------|
| `_type` | Default type key (recommended) |
| `_class` | Alternative type key |
| `@type` | JSON-LD compatible |
| `eClass` | EMF/emfjson-jackson compatible |

> **Important:** The property name `type` is **NOT** automatically recognized as a type key, because it is a very common attribute name in data formats like GeoJSON (`"type": "Point"`), OpenAPI-generated models, etc.

To use `type` as type discriminator, configure it explicitly:

```java
CodecConfiguration config = CodecConfiguration.builder()
    .globalTypeKey("type")
    .build();
```

Or via EAnnotation on the EPackage:
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="typeKey" value="type"/>
</eAnnotations>
```

### 5.2 Format Detection

Deserializers detect the format from the JSON structure:

| Input | Detected Format | Strategy Detection |
|-------|-----------------|-------------------|
| `"_type": "string"` | PLAIN | Detect by content: URI (contains `#//`), CLASS (contains `.`), NAME (simple string) |
| `"_type": { ... }` | STRUCTURED | Read inner keys to determine strategy |
| `"_schema": ..., "_type": "string"` | PLAIN | SCHEMA_AND_TYPE strategy (two separate fields) |

### 5.3 Type Resolution

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

### 5.3.1 Unknown Type Handling

When the deserializer cannot resolve a type value to a known EClass, the behavior depends on whether a **type hint** is available.

#### Fallback to Type Hint

If a `CODEC_ROOT_OBJECT` hint is provided (or the reference type is concrete), the deserializer uses it as fallback:

| Scenario | Behavior | Severity |
|----------|----------|----------|
| Unknown type value, hint available | Use hint, continue | WARNING |
| Unknown type value, no hint | Fail | ERROR |

**Example - Unknown type with hint (succeeds):**
```java
Map<String, Object> options = Map.of(
    CodecResourceOptions.CODEC_ROOT_OBJECT, PersonPackage.Literals.PERSON
);
resource.load(inputStream, options);
// JSON: {"_type": "UnknownType", "name": "John"}
// Result: WARNING logged, Person created using hint
```

**Example - Unknown type without hint (fails):**
```java
resource.load(inputStream, Collections.emptyMap());
// JSON: {"_type": "UnknownType", "name": "John"}
// Result: ERROR - "Cannot deserialize: no type information found and no CODEC_ROOT_OBJECT hint"
```

#### Resolution Order

When resolving type values, the deserializer tries multiple strategies:

1. **Full URI** - If value contains `#//`, treat as EMF EClass URI
2. **Java class name** - If value contains `.`, look up by instance class name
3. **Discriminator mapping** - Check TypeDiscriminatorService for mapped values
4. **Simple name** - Look up by class name in context schema
5. **Numeric ID** - Parse as classifier ID within context schema
6. **Fallback** - Use hint if available, otherwise ERROR

#### Diagnostic Messages

| Scenario | Message |
|----------|---------|
| Type value not resolved | `Could not resolve EClass from type value: {value}` |
| No type info and no hint | `Cannot deserialize: no type information found and no CODEC_ROOT_OBJECT hint` |

See [Error Handling](00-overview.md#2-error-and-warning-handling) for the complete error scenarios table.

### 5.4 Context Schema and NAME Strategy

The **NAME strategy** writes only the simple EClass name (e.g., `"Person"` instead of `"http://example.org/1.0#//Person"`). For deserialization, this requires a **Context Schema** to resolve the simple name back to a full EClass URI.

#### 5.4.1 CODEC_ROOT_SCHEMA Option

Explicitly sets the context schema for deserialization:

```java
Map<String, Object> options = new HashMap<>();
options.put(CodecResourceOptions.CODEC_ROOT_SCHEMA, "http://geojson.org/1.0");
resource.load(inputStream, options);
```

With this option, all simple type names are resolved against the specified schema:
- `"type": "Point"` → `http://geojson.org/1.0#//Point`
- `"type": "Feature"` → `http://geojson.org/1.0#//Feature`

**Example (GeoJSON-like):**
```json
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": {"longitude": 8.68, "latitude": 50.11}
      }
    }
  ]
}
```

#### 5.4.2 CODEC_ROOT_OBJECT Option

Specifies the expected root EClass for deserialization. **Additionally**, it implicitly sets the context schema from the EClass's package URI.

```java
Map<String, Object> options = new HashMap<>();
options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, GeoJsonPackage.eINSTANCE.getFeatureCollection());
resource.load(inputStream, options);
```

This provides **two benefits**:
1. Root object type is known → no `_type` needed at root level
2. Context schema is established → nested objects can use simple names

**Example (no _type at root needed):**
```json
{
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": {"longitude": 8.68, "latitude": 50.11}
      }
    }
  ]
}
```

#### 5.4.3 Context Schema Resolution Order

| Source | Priority | Description |
|--------|----------|-------------|
| `CODEC_ROOT_SCHEMA` | 1 (highest) | Explicitly provided schema URI |
| `CODEC_ROOT_OBJECT` | 2 | Extracted from hint EClass's package |
| First full URI in content | 3 | Smart compression: schema from root's `_type` |
| Search all packages | 4 (lowest) | Fallback, non-deterministic! |

> **Warning:** Without a context schema, NAME strategy searches all registered packages for a matching class name. This is **non-deterministic** if multiple packages contain classes with the same name.

#### 5.4.4 Smart Compression

When the root object uses a full URI, the schema is automatically extracted and used for nested objects:

```json
{
  "_type": "http://example.org/company/1.0#//Company",
  "name": "Acme Inc",
  "employees": [
    {"_type": "Person", "name": "Alice"},
    {"_type": "Person", "name": "Bob"}
  ]
}
```

The deserializer:
1. Parses root `_type`: `http://example.org/company/1.0#//Company`
2. Extracts context schema: `http://example.org/company/1.0`
3. Resolves `"Person"` → `http://example.org/company/1.0#//Person`

### 5.5 Type Resolution Rules

| Content has `_type` | `CODEC_ROOT_OBJECT` set | Behavior |
|---------------------|-------------------------|----------|
| Yes (full URI) | No | Use content type, establish context schema |
| Yes (simple name) | No | Resolve via context schema or search all packages |
| Yes | Yes | Use content type, context schema from hint |
| No | Yes | Use hint type, context schema from hint |
| No | No | **ERROR**: Cannot determine type |

When both content type and hint are present but differ:
- Log a WARNING
- Continue with content type (content wins)
- Example: Hint says `Person`, content says `Employee` → use `Employee`, log warning

#### 5.5.1 Type Hint Mode (CODEC_TYPE_MODE)

By default, `CODEC_ROOT_OBJECT` and `CODEC_ROOT_SCHEMA` behave as **hints/fallbacks**, not as highest-priority overrides. This is an **intentional deviation** from the standard configuration hierarchy (where Load/Save options have highest priority).

**Rationale:**
- Most common use case is "help deserialize when type info is missing"
- Users expect content type information to be respected
- Forcing override by default would cause unexpected behavior

| Mode | Behavior | Use Case |
|------|----------|----------|
| `HINT` (default) | Content type wins when present; hint used as fallback | General deserialization, flexible input handling |
| `OVERRIDE` | Hint always wins for root object, ignoring content type | Force-deserialize as specific type regardless of content |

**HINT Mode (default):**
```
Type Resolution Priority:
1. Content type information (from JSON _type field) - wins when present
2. CODEC_ROOT_OBJECT / CODEC_ROOT_SCHEMA - used as fallback when content has no type
3. Reference type (EReference.getEReferenceType()) - for nested objects
4. ERROR - if no type determinable
```

**OVERRIDE Mode:**
```
Type Resolution Priority:
1. CODEC_ROOT_OBJECT - always wins for root object (follows strict config hierarchy)
2. Content type information - ignored for root object
3. Reference type - for nested objects (CODEC_ROOT_OBJECT only affects root)
```

**Configuration:**
```java
Map<String, Object> options = new HashMap<>();
options.put(CodecResource.CODEC_ROOT_OBJECT, PersonPackage.Literals.PERSON);
options.put(CodecResource.CODEC_TYPE_MODE, TypeHintMode.OVERRIDE);  // Force type
resource.load(inputStream, options);
```

**Example - HINT mode (default):**
```java
// JSON has type info - content wins, hint ignored
String json = """
    { "_type": "http://example.org#//Employee", "name": "John" }
    """;

options.put(CODEC_ROOT_OBJECT, PersonPackage.Literals.PERSON);
// Result: Deserializes as Employee (content wins)
// If Employee is subtype of Person: no warning
// If incompatible: WARNING logged but content still wins
```

**Example - OVERRIDE mode:**
```java
// Force deserialize as specific type, ignore content type
String json = """
    { "_type": "http://example.org#//OldType", "name": "John" }
    """;

options.put(CODEC_ROOT_OBJECT, PersonPackage.Literals.PERSON);
options.put(CODEC_TYPE_MODE, TypeHintMode.OVERRIDE);
// Result: Deserializes as Person (override mode, content type ignored)
```

> **Note:** This behavior deviates from the general configuration hierarchy principle where Load/Save options (Level 1) have highest priority. The deviation exists for practical usability reasons.

#### 5.5.2 Deserialization Mode (Type Resolution Strictness)

The `DeserializationMode` controls how strictly the deserializer follows the configured type strategy when resolving types.

| Mode | Behavior | Use Case |
|------|----------|----------|
| `STRICT` | Type field MUST match configured strategy exactly; missing/malformed → ERROR | Guaranteed format compliance |
| `LENIENT` (default) | Try configured strategy first, then fallback resolution | Maximum interoperability |
| `AUTO_DETECT` | Ignore configured strategy; probe JSON structure to determine format | Unknown/mixed formats |

**Why LENIENT is default:**
- Most users want deserialization to succeed when possible
- Real-world JSON often has minor variations
- STRICT is available for those who need format guarantees
- LENIENT + warnings provides best of both worlds

**STRICT Mode:**
```java
// Config says SCHEMA_AND_TYPE, but JSON only has simple name → ERROR
CodecConfiguration config = CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.SCHEMA_AND_TYPE)
    .deserializationMode(DeserializationMode.STRICT)
    .build();

// JSON: {"_type": "Person", "name": "John"}  // No _schema field
// Result: ERROR - "Missing schema field, config requires SCHEMA_AND_TYPE"
```

**LENIENT Mode (default):**
```java
// Config says SCHEMA_AND_TYPE, but JSON only has simple name → try fallback
CodecConfiguration config = CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.SCHEMA_AND_TYPE)
    // .deserializationMode(DeserializationMode.LENIENT)  // default
    .build();

// JSON: {"_type": "Person", "name": "John"}
// Result: WARNING logged, resolves "Person" using CODEC_ROOT_SCHEMA or hint
```

**AUTO_DETECT Mode:**
```java
// Ignore config, probe JSON structure
CodecConfiguration config = CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.URI)  // Config says URI
    .deserializationMode(DeserializationMode.AUTO_DETECT)
    .build();

// JSON: {"_type": "Person", "name": "John"}  // Simple name
// Result: Auto-detects NAME format, resolves using context
```

**Resolution Behavior by Mode:**

| Scenario | STRICT | LENIENT | AUTO_DETECT |
|----------|--------|---------|-------------|
| Missing type field | ERROR | Use hints | Use hints |
| Wrong format | ERROR | Try fallbacks | Probe format |
| Unknown type value | ERROR | WARNING + fallback | WARNING + fallback |

**Warning Messages (LENIENT mode):**
```
WARNING: Type resolved via fallback. Config: typeStrategy=SCHEMA_AND_TYPE,
         but type "Person" resolved using CODEC_ROOT_SCHEMA hint.
         Consider updating config or JSON to match.
```

> **Note:** `DeserializationMode` controls **type resolution** strictness. For **feature handling** strictness (unknown/missing features), see [Feature Strictness](09-feature.md#7-feature-strictness).

### 5.6 Deferred Properties

When properties appear **before** the `_type` field in the JSON, they are deferred and processed after type resolution:

```json
{
  "name": "John",
  "age": 30,
  "_type": "http://example.org/1.0#//Person"
}
```

**Processing order:**
1. `name` and `age` are stored as raw Java objects (Map, List, primitives)
2. `_type` is encountered → EClass is resolved
3. EObject is created
4. Deferred properties are replayed via TokenBuffer and deserialized

**Supported deferred value types:**
- Primitives: String, Number, Boolean, null
- Nested objects: Stored as `Map<String, Object>`, replayed as JSON objects
- Arrays: Stored as `List<Object>`, replayed as JSON arrays
- Deep nesting: Fully supported (Maps containing Maps, Lists containing Lists, etc.)

### 5.7 Polymorphic Containment References

When an EReference points to an abstract EClass, the concrete type must be specified in the JSON:

**Model:**
```
Geometry (abstract)
  ├── Point
  ├── LineString
  └── Polygon

Feature
  └── geometry: Geometry (containment)
```

**JSON:**
```json
{
  "_type": "http://geojson.org/1.0#//Feature",
  "geometry": {
    "_type": "http://geojson.org/1.0#//Point",
    "coordinates": {"longitude": 8.68, "latitude": 50.11}
  }
}
```

**With context schema (NAME strategy):**
```json
{
  "type": "Feature",
  "geometry": {
    "type": "Point",
    "coordinates": {"longitude": 8.68, "latitude": 50.11}
  }
}
```

The deserializer:
1. Gets the reference type (`Geometry`) as a hint
2. Reads `_type`/`type` from the nested object
3. Resolves concrete class (`Point`) which must be a subtype of the hint
4. If no `_type` and reference type is **concrete**: uses reference type
5. If no `_type` and reference type is **abstract**: **ERROR**

---

## 6. Real-World Example: GeoJSON

This section demonstrates deserialization of real [GeoJSON](https://geojson.org/) data using the [org.geojson.model](https://github.com/geckoprojects-org/org.gecko.emf.models/tree/main/org.geojson.model) EMF model.

### 6.1 The GeoJSON Model

The GeoJSON model uses several codec-relevant features:

1. **`type` as discriminator** - GeoJSON uses `"type": "Point"` instead of `"_type"`
2. **ExtendedMetaData for feature names** - The `data` attribute maps to JSON key `coordinates`
3. **Array data types** - Coordinates are `double[]`, `double[][]`, `double[][][]` arrays
4. **Polymorphic containment** - `Feature.geometry` can be Point, LineString, Polygon, etc.

**Model structure:**
```
GeoJsonObject (abstract)
  └── bbox: double[] (via ExtendedMetaData: "bbox")

Geometry (interface, extends GeoJsonObject)
  ├── Point
  │     └── data: double[] (via ExtendedMetaData: "coordinates")
  ├── LineString
  │     └── data: double[][] (via ExtendedMetaData: "coordinates")
  ├── Polygon
  │     └── data: double[][][] (via ExtendedMetaData: "coordinates")
  └── ...

Feature (extends GeoJsonObject)
  ├── id: String
  ├── geometry: Geometry (containment)
  └── properties: EObject (containment)

FeatureCollection (extends GeoJsonObject)
  └── features: Feature[*] (containment)
```

### 6.2 Configuration

To deserialize real GeoJSON, configure:

```java
CodecConfiguration config = CodecConfiguration.builder()
    .typeKey("type")                        // GeoJSON uses "type" not "_type"
    .useNamesFromExtendedMetaData(true)     // Map "coordinates" → data attribute
    .build();

Map<String, Object> options = new HashMap<>();
options.put(CodecResource.CODEC_ROOT_SCHEMA, "https://geojson.org/model/2016");

resource.load(inputStream, options);
```

### 6.3 Example: Point

**GeoJSON Input:**
```json
{
  "type": "Point",
  "coordinates": [8.6821, 50.1109]
}
```

**Deserialization:**
1. `"type": "Point"` → resolves to `GeoJsonPackage.eINSTANCE.getPoint()`
2. `"coordinates"` → maps to `data` attribute (via ExtendedMetaData)
3. `[8.6821, 50.1109]` → deserialized as `double[]`

### 6.4 Example: Polygon with BBox

**GeoJSON Input:**
```json
{
  "type": "Polygon",
  "bbox": [11.504, 50.895, 11.567, 50.913],
  "coordinates": [
    [
      [11.554, 50.901],
      [11.554, 50.901],
      [11.554, 50.900],
      [11.554, 50.901]
    ]
  ]
}
```

**Deserialization:**
1. `"type": "Polygon"` → `Polygon` EClass
2. `"bbox"` → `double[4]` bounding box array
3. `"coordinates"` → `double[][][]` (rings containing coordinate arrays)

### 6.5 Example: FeatureCollection

**GeoJSON Input:**
```json
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "id": "berlin",
      "geometry": {
        "type": "Point",
        "coordinates": [13.405, 52.52]
      },
      "properties": null
    },
    {
      "type": "Feature",
      "id": "frankfurt",
      "geometry": {
        "type": "Point",
        "coordinates": [8.682, 50.110]
      },
      "properties": null
    }
  ]
}
```

**Deserialization:**
1. Root: `FeatureCollection` with 2 features
2. Each feature: polymorphic `geometry` resolved by nested `"type"` field
3. Coordinates deserialized as `double[]` arrays

### 6.6 Key Takeaways

| GeoJSON Requirement | Codec Configuration |
|---------------------|---------------------|
| `type` as discriminator | `.typeKey("type")` |
| `coordinates` → `data` | `.useNamesFromExtendedMetaData(true)` |
| Context schema for NAME | `CODEC_ROOT_SCHEMA` option |
| Array coordinates | Automatic (see [Feature Serialization - Array Attributes](09-feature.md#6-array-attributes)) |

---

[Next: SuperType Serialization →](06-supertype.md)
