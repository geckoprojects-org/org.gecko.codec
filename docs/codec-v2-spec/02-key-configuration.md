# Key Configuration

[← Back to Overview](00-overview.md) | [← Serialization Strategies](01-strategies.md)

---

## 1. Overview

The codec uses special JSON keys to represent metadata (type, schema, supertype, id, references). These keys follow a consistent naming convention and are fully configurable.

**Key Principle:**
- **PLAIN format**: Keys use `_` prefix and appear at root level
- **STRUCTURED format**: Keys without prefix appear inside nested container objects

---

## 2. Default Key Names

### 2.1 PLAIN vs STRUCTURED Keys

| Feature | PLAIN Key (Root) | STRUCTURED Key (Inner) | Description |
|---------|------------------|------------------------|-------------|
| Schema | `_schema` | `schema` | EPackage namespace URI |
| Type | `_type` | `type` | EClass name |
| SuperType | `_supertype` | `supertype` | Parent type(s) |
| ID | `_id` | (feature names) | Object identifier |
| Separator | `_separator` | `separator` | ID feature separator (for combined IDs) |
| Reference | `_ref` | `ref` | Non-containment reference |

### 2.2 Container Keys (STRUCTURED only)

In STRUCTURED format, related metadata is grouped into container objects:

| Container | Default Key | Contains |
|-----------|-------------|----------|
| Type Container | `_type` | `schema`, `type`, `supertype` |
| ID Container | `_id` | `separator`, feature values |

---

## 3. PLAIN Format Examples

All metadata keys appear at root level with `_` prefix:

### 3.1 Minimal (URI Strategy)
```json
{
  "_type": "http://example.org/1.0#//Person",
  "_id": "maho",
  "firstName": "Mark",
  "lastName": "Hoffmann"
}
```

### 3.2 With Schema (SCHEMA_AND_TYPE Strategy)
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "Person",
  "_id": "maho",
  "firstName": "Mark"
}
```

### 3.3 With SuperType
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "BusinessPerson",
  "_supertype": "Person",
  "_id": "maho",
  "firstName": "Mark"
}
```

### 3.4 Multiple SuperTypes
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "BusinessPerson",
  "_supertype": ["Person", "Auditable"],
  "_id": "maho",
  "firstName": "Mark"
}
```

### 3.5 With Non-Containment Reference
```json
{
  "_type": "http://example.org/1.0#//Person",
  "_id": "maho",
  "firstName": "Mark",
  "employer": { "_ref": "datainmotion" }
}
```

---

## 4. STRUCTURED Format Examples

Metadata is grouped into container objects, inner keys have no prefix:

### 4.1 Type Container
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person"
  },
  "_id": "maho",
  "firstName": "Mark"
}
```

### 4.2 With SuperType
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "BusinessPerson",
    "supertype": "Person"
  },
  "_id": "maho",
  "firstName": "Mark"
}
```

### 4.3 Multiple SuperTypes
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "BusinessPerson",
    "supertype": ["Person", "Auditable"]
  },
  "_id": "maho",
  "firstName": "Mark"
}
```

### 4.4 Structured ID (Multiple Features)
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person"
  },
  "_id": {
    "separator": "-",
    "firstName": "Mark",
    "lastName": "Hoffmann"
  }
}
```

### 4.5 With Non-Containment Reference
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person"
  },
  "_id": "maho",
  "firstName": "Mark",
  "employer": {
    "type": "http://example.org/1.0#//Company",
    "ref": "datainmotion"
  }
}
```

---

## 5. Key Configuration Options

All keys are configurable via annotations or CodecConfiguration.

### 5.1 Configuration Table

| Option | PLAIN Default | STRUCTURED Default | Description |
|--------|---------------|-------------------|-------------|
| `schemaKey` | `_schema` | `schema` | Schema/Namespace key |
| `typeKey` | `_type` | `type` | Type name key |
| `rootTypeKey` | - | `_type` | Root container key (STRUCTURED only) |
| `superTypeKey` | `_supertype` | `supertype` | SuperType key |
| `idKey` | `_id` | `_id` | ID container key |
| `separatorKey` | `_separator` | `separator` | Separator key (format-aware default) |
| `refKey` | `_ref` | `ref` | Reference key |

### 5.2 CodecConfiguration Builder

```java
// Custom keys for JSON-LD style output
CodecConfiguration.builder()
    .rootTypeKey("@context")      // STRUCTURED container
    .schemaKey("@vocab")          // Inner schema key
    .typeKey("@type")             // Inner type key
    .superTypeKey("@extends")     // Inner supertype key
    .idKey("@id")                 // ID key
    .refKey("@id")                // Reference key
    .build();
```

### 5.3 EAnnotation Configuration

```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="codec.type"/>
  <details key="typeKey" value="@type"/>
  <details key="schemaKey" value="@vocab"/>
  <details key="rootTypeKey" value="@context"/>
</eAnnotations>
```

---

## 6. Custom Key Examples

### 6.1 JSON-LD Style
```json
{
  "@context": {
    "@vocab": "http://example.org/1.0",
    "@type": "Person",
    "@extends": "Entity"
  },
  "@id": "maho",
  "firstName": "Mark",
  "employer": { "@id": "datainmotion" }
}
```

### 6.2 MongoDB Style
```json
{
  "_t": "Person",
  "_id": "maho",
  "firstName": "Mark",
  "employer": { "$ref": "companies", "$id": "datainmotion" }
}
```

---

## 7. Format Detection (Deserialization)

The deserializer auto-detects the format used:

| Detection | Format |
|-----------|--------|
| `"_type": "string"` | PLAIN |
| `"_type": { ... }` | STRUCTURED |
| `"_schema": ..., "_type": ...` (both present) | PLAIN (SCHEMA_AND_TYPE) |

The deserializer uses the configured keys for detection, so custom keys work transparently.

---

## 8. Consistency Rules

1. **Prefix Convention**: Root-level metadata keys use `_` prefix, inner keys don't
2. **Container Grouping**: In STRUCTURED format, related metadata (schema, type, supertype) shares one container
3. **Reference Format**: References always use an object wrapper (`{ "_ref": "..." }` or `{ "ref": "..." }`)
4. **Configurability**: Every key is overridable via annotation or CodecConfiguration

---

[Next: Configuration Hierarchy →](03-config-hierarchy.md)
