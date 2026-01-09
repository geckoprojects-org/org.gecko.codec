# Serialization Strategies

[← Back to Overview](00-overview.md)

---

## 1. SerializationFormat: The Codec-Wide Format Strategy

This is a **fundamental, codec-wide concept** that applies uniformly to all serialization targets (type, ID, reference, supertype). Instead of defining separate format enums per target, we use a single `SerializationFormat` enum.

```java
public enum SerializationFormat {
    /**
     * Simple value output - string, number, or array of simple values.
     * The value itself is directly usable without parsing.
     */
    PLAIN,

    /**
     * Nested object output with configurable keys.
     * The value is a map/object containing structured information.
     */
    STRUCTURED
}
```

**Definition:**
- **PLAIN**: The property value is a simple type (string, number, boolean) or an array of simple values
- **STRUCTURED**: The property value is a nested object/map (or an array of objects)

**The test:** Is the value (or each array element) a nested JSON object?

| Format | Single Value | Array Value |
|--------|--------------|-------------|
| PLAIN | `"_type": "Person"` | `"_supertype": ["Entity", "Auditable"]` |
| STRUCTURED | `"_type": { "schema": "...", "name": "..." }` | `"_supertype": [{"schema": "...", "name": "Entity"}, {"name": "Auditable"}]` |

**Format applies to each element, not the container:**

The format determines how each *individual value* is represented, independent of whether it's a single value or part of an array:

```json
// PLAIN format (array of strings)
"_supertype": ["Entity", "Auditable"]

// STRUCTURED format (array of objects)
"_supertype": [
  { "schema": "http://example.org/base/1.0", "name": "Entity" },
  { "name": "Auditable" }
]
```

**Codec-wide default with per-target override:**

```java
// Set codec-wide default
CodecConfig.builder()
    .format(SerializationFormat.STRUCTURED)  // default for all targets
    .build();

// Override for specific target
IdSerializationConfig.builder()
    .format(SerializationFormat.PLAIN)  // IDs stay plain
    .build();
```

**Why this matters for the serializer:**

When the serializer builds the property map:
- **PLAIN**: Write simple value(s) directly to the output
- **STRUCTURED**: Create a nested map/object as the property value

**Example - Type with supertypes enabled:**

*PLAIN (SCHEMA_AND_TYPE) - each property has a simple value:*
```json
{
  "_schema": "http://example.org/person/1.0",
  "_type": "Person",
  "_supertype": ["Entity", "Auditable"]
}
```

*STRUCTURED - `_type` value is a nested object containing all info:*
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "name": "Person",
    "supertype": ["Entity", "Auditable"]
  }
}
```

---

## 2. Numeric IDs (Optional Optimization)

NUMERIC is an optimization that can be applied **on top of** STRUCTURED format. It uses EMF classifier IDs instead of names for maximum compactness.

| Setting | Output |
|---------|--------|
| STRUCTURED | `"_type": { "schema": "...", "name": "Person" }` |
| STRUCTURED + numeric | `"_type": { "s": "...", "c": 3 }` |

**Configuration:**
```java
CodecConfig.builder()
    .format(SerializationFormat.STRUCTURED)
    .useNumericIds(true)  // applies to type and reference
    .build();
```

**WARNING:** Classifier IDs are assigned based on declaration order and can change when the model evolves. Only use for transient data, not persistent storage.

---

## 3. Strategy Classification

Strategies are classified by their output format:

| Strategy | Output Format | Description |
|----------|---------------|-------------|
| `NAME` | Plain | EClass simple name as string |
| `CLASS` | Plain | Instance class name as string |
| `URI` | Plain | Full EClass URI as string |
| `MAPPED` | Plain | Discriminator value as string |
| `SCHEMA_AND_TYPE` | Plain | Separate top-level fields, each with simple values |
| `STRUCTURED` | Structured | Nested object with schema, name, etc. |
| `NUMERIC` | Structured | Nested object with numeric IDs |

---

## 4. Format Applicability per Target

All targets use the unified `SerializationFormat` enum (PLAIN, STRUCTURED). The table below shows what each format produces:

| Target | PLAIN Output | STRUCTURED Output | Numeric Option |
|--------|--------------|-------------------|----------------|
| Type | `"_type": "Person"` | `"_type": {"schema": "...", "name": "..."}` | `{"s": "...", "c": 3}` |
| ID | `"_id": "John-Doe"` | `"_id": {"firstName": "John", ...}` | N/A |
| Reference | `"employer": "acme-corp"` | `"employer": {"_type": "...", "_ref": "..."}` | Uses numeric type |
| SuperType | `["Entity", "Auditable"]` | `[{"schema": "...", "name": "Entity"}, ...]` | N/A |

**Type strategies vs SerializationFormat:**

For **Type**, the specific strategy (NAME, CLASS, URI, MAPPED, SCHEMA_AND_TYPE) determines *what* plain string to use. These are all PLAIN format variations. STRUCTURED format uses schema+name objects.

| Strategy | Format | Output |
|----------|--------|--------|
| NAME | PLAIN | `"_type": "Person"` |
| CLASS | PLAIN | `"_type": "org.example.Person"` |
| URI | PLAIN | `"_type": "http://...#//Person"` |
| SCHEMA_AND_TYPE | PLAIN | `"_schema": "...", "_type": "Person"` |
| STRUCTURED | STRUCTURED | `"_type": {"schema": "...", "name": "..."}` |

---

[Next: Key Configuration →](02-key-configuration.md)
