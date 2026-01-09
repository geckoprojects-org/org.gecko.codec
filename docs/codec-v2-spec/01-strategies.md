# Serialization Strategies

[← Back to Overview](00-overview.md)

---

## 1. Two Orthogonal Dimensions: Format × Strategy

Serialization is controlled by **two independent dimensions** that can be combined:

1. **Format** (PLAIN | STRUCTURED) - **HOW** the data is presented
2. **Strategy** - **WHAT** information is transported

These dimensions are orthogonal and can be multiplied to produce all valid combinations.

**Important:** Each serialization target (Type, ID, Reference, SuperType) has its own independent format and strategy settings. This allows mixing, e.g., Type in STRUCTURED + ID in PLAIN.

### 1.1 SerializationFormat Enum

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
| STRUCTURED | `"_type": { "schema": "...", "type": "..." }` | `"_supertype": [{"schema": "...", "type": "Entity"}, {"type": "Auditable"}]` |

### 1.2 TypeStrategy Enum

```java
public enum TypeStrategy {
    URI,            // Full EClass URI
    NAME,           // EClass simple name
    CLASS,          // Java instance class name
    NUMERIC,        // EMF classifier ID
    MAPPED,         // Discriminator value
    SCHEMA_AND_TYPE // Schema URI + type name (two pieces of info)
}
```

---

## 2. Format × Strategy Matrix for Type

The Format and Strategy dimensions are **orthogonal** - every combination is valid:

### 2.1 PLAIN Format

| Strategy | Output Example |
|----------|----------------|
| URI | `"_type": "http://example.org/1.0#//Person"` |
| NAME | `"_type": "Person"` |
| CLASS | `"_type": "org.example.Person"` |
| NUMERIC | `"_type": "3"` |
| MAPPED | `"_type": "customer"` |
| SCHEMA_AND_TYPE | `"_schema": "http://example.org/1.0", "_type": "Person"` |

### 2.2 STRUCTURED Format

| Strategy | Output Example |
|----------|----------------|
| URI | `"_type": { "uri": "http://example.org/1.0#//Person" }` |
| NAME | `"_type": { "type": "Person" }` |
| CLASS | `"_type": { "class": "org.example.Person" }` |
| NUMERIC | `"_type": { "schema": "http://example.org/1.0", "classifier": 3 }` |
| MAPPED | `"_type": { "discriminator": "customer" }` |
| SCHEMA_AND_TYPE | `"_type": { "schema": "http://example.org/1.0", "type": "Person" }` |

### 2.3 With Supertype (Optional Addition)

Supertype is an **optional addition** that can be combined with any Format × Strategy:

**PLAIN format with supertype:**
```json
{
  "_type": "Person",
  "_supertype": ["Entity", "Auditable"]
}
```

**STRUCTURED format with supertype:**
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": ["Entity", "Auditable"]
  }
}
```

---

## 3. Format × Strategy Matrix for ID

The same pattern applies to ID serialization:

### 3.1 ID Strategies

| Strategy | Description |
|----------|-------------|
| ID_FIELD | Use features marked with `eID="true"` |
| COMBINED | Combine multiple features with separator |

### 3.2 ID Format Examples

| Format | Single Feature | Multiple Features |
|--------|----------------|-------------------|
| PLAIN | `"_id": "john"` | `"_id": "John-Doe-1"` |
| STRUCTURED | `"_id": { "myId": "john" }` | `"_id": { "separator": "-", "firstName": "John", "lastName": "Doe", "sequence": "1" }` |

---

## 4. Per-Target Format Configuration

Each serialization target (Type, ID, Reference, SuperType) has its **own independent format setting**. This allows mixing formats, e.g., Type in STRUCTURED format while ID stays in PLAIN format.

```java
// Type in STRUCTURED format
TypeSerializationConfig typeConfig = TypeSerializationConfig.builder()
    .format(SerializationFormat.STRUCTURED)
    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
    .build();

// ID in PLAIN format (independent of Type)
IdSerializationConfig idConfig = IdSerializationConfig.builder()
    .format(SerializationFormat.PLAIN)  // default
    .build();
```

**Resulting JSON:**
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person"
  },
  "_id": "John-Doe",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Default format for all targets is PLAIN.**

---

## 5. Format Applicability per Target

| Target | PLAIN Output | STRUCTURED Output |
|--------|--------------|-------------------|
| Type | `"_type": "Person"` | `"_type": {"schema": "...", "type": "..."}` |
| ID | `"_id": "John-Doe"` | `"_id": {"firstName": "John", ...}` |
| Reference | `"_ref": "acme-corp"` | `"_ref": {"_type": "...", "uri": "..."}` |
| SuperType | `["Entity", "Auditable"]` | `[{"schema": "...", "type": "Entity"}, ...]` |

---

## 6. Numeric Optimization

NUMERIC strategy uses EMF classifier IDs instead of names for compactness:

| Format | Strategy | Output |
|--------|----------|--------|
| PLAIN | NUMERIC | `"_type": "3"` |
| STRUCTURED | NUMERIC | `"_type": { "schema": "http://...", "classifier": 3 }` |

**WARNING:** Classifier IDs are positional and can change when the model evolves. Only use for transient data, not persistent storage.

---

## 7. Key Configuration

Keys differ between PLAIN and STRUCTURED format per [Key Configuration](02-key-configuration.md):

| Target | PLAIN Key | STRUCTURED Outer Key | STRUCTURED Inner Keys |
|--------|-----------|---------------------|----------------------|
| Type | `_type` | `_type` | `schema`, `type`, `supertype` |
| ID | `_id` | `_id` | feature names, `separator` |
| Reference | `_ref` | `_ref` | `uri`, `_type` |

---

[Next: Key Configuration →](02-key-configuration.md)
