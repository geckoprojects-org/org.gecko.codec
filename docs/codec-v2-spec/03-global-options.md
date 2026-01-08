# Global Configuration Options

[← Back to Overview](00-overview.md) | [← Configuration Hierarchy](02-config-hierarchy.md)

---

These options apply globally across all serialization targets (type, ID, reference, supertype, features).

## 1. Smart Compression

Smart Compression reduces redundancy by omitting type information when it can be derived from context. This applies consistently across all serialization features.

### 1.1 Core Principle

When smart compression is enabled, type information is omitted whenever the **instance type equals the declared type**. The deserializer can infer the type from the model definition.

| Feature | Declared Type | Instance Type | Smart Compression Action |
|---------|---------------|---------------|--------------------------|
| Root Object | `CODEC_ROOT_OBJECT` hint | `eObject.eClass()` | Omit `_type` if equal |
| Containment Reference | `reference.getEReferenceType()` | `target.eClass()` | Omit `_type` if equal |
| Non-Containment Reference | `reference.getEReferenceType()` | `target.eClass()` | Omit `_type` if equal |
| Cross-Document Containment | `reference.getEReferenceType()` | `target.eClass()` | Omit `_type` if equal |

**Key Rule:** Always serialize the **instance type** (from `eObject.eClass()`), never the declared type. Smart compression only affects whether to write it, not what value to write.

### 1.2 Smart Compression Behavior Matrix

| Smart Compression | Instance Type == Declared Type | Action |
|-------------------|-------------------------------|--------|
| ON | Yes | Omit `_type` (inferable from context) |
| ON | No | Write `_type` with instance type |
| OFF | Yes | Write `_type` with instance type |
| OFF | No | Write `_type` with instance type |

### 1.3 Additional Compression: Same-Schema Names

When using STRUCTURED type format with smart compression, schema information can also be compressed:

- **Same-package references**: Use simple name instead of full URI
- **Same-schema supertypes**: Use simple name instead of full URI
- **Cross-package references**: Always include full URI

### 1.4 Configuration

```java
// Global - applies to ALL serialization targets
CodecConfig.builder()
    .smartCompression(true)  // Global default
    .build();

// Per-target override
CodecConfig.builder()
    .smartCompression(false)  // Global default OFF
    .type(TypeSerializationConfig.builder()
        .smartCompression(true)  // But ON for type info
        .build())
    .reference(ReferenceSerializationConfig.builder()
        .smartCompression(true)  // And ON for references
        .build())
    .build();
```

### 1.5 Examples

**Containment reference with smart compression ON:**

```java
// EReference employees: Person[*]  (declared type = Person)
// Contains: Person, Person, FancyPerson instances
```
```json
{
  "employees": [
    { "name": "John" },
    { "name": "Jane" },
    { "_type": "http://example.org/1.0#//FancyPerson", "name": "Bob", "fancyLevel": 5 }
  ]
}
```
Note: First two omit `_type` (Person == Person), third includes it (FancyPerson != Person).

**Non-containment reference with smart compression ON:**

```json
{
  "employees": [
    { "_ref": "john" },
    { "_ref": "jane" },
    { "_type": "http://external.org/hr/1.0#//Contractor", "_ref": "bob" }
  ]
}
```

**Cross-document containment with smart compression ON:**

```json
{
  "address": {
    "_ref": "addresses.json#//@addresses.0"
  }
}
```
Note: `_type` omitted because instance type equals declared reference type.

---

## 2. NUMERIC/INDEXED Strategy

Uses EClass classifier IDs and EStructuralFeature IDs instead of names for maximum compactness.

**Configuration:**

```java
// Global - applies to ALL serialization targets
CodecConfig.builder()
    .useNumericIds(true)  // Global default
    .build();

// Per-target
TypeSerializationConfig.builder()
    .strategy(NUMERIC)
    .schemaKey("s")       // Short keys for compactness
    .classKey("c")
    .build();

ReferenceSerializationConfig.builder()
    .strategy(NUMERIC)
    .classKey("c")
    .refKey("r")
    .build();
```

**Example with NUMERIC strategy:**

```json
{
  "_type": { "s": "http://example.org/person/1.0", "c": 3 },
  "5": "John",
  "6": "Doe",
  "12": { "c": 1, "r": "acme" }
}
```

With smart compression (same package context):

```json
{
  "_type": { "s": "http://example.org/person/1.0", "c": 3 },
  "5": "John",
  "6": "Doe",
  "12": { "c": 1, "r": "acme" },
  "15": { "s": "http://external.org/audit/1.0", "c": 2, "r": "audit-123" }
}
```

### 2.1 NUMERIC Strategy - Compatibility Warning

> **⚠️ IMPORTANT: Model Evolution Risk**
>
> The NUMERIC strategy uses EMF's internal classifier and feature IDs. These IDs are **assigned based on declaration order** in the Ecore model and **can change** when:
>
> - A new EClass is added before existing classes
> - A new EStructuralFeature is added before existing features
> - Classes or features are reordered in the model
> - Classes or features are removed (IDs of subsequent elements shift)
>
> **Impact:**
> - Serialized data using NUMERIC strategy may become **unreadable** after model changes
> - The same numeric ID may refer to a **different** class/feature after model evolution
> - Unlike names, numeric IDs have **no semantic stability**
>
> **Recommendations:**
> 1. Only use NUMERIC for **transient data** (caches, message queues)
> 2. Do NOT use NUMERIC for **persistent storage** unless model is frozen
> 3. Consider using **@EClassifier(id=N)** annotations to stabilize IDs
> 4. Always version your serialized data format
> 5. For long-term storage, prefer NAME or URI strategies
>
> **Comparison:**
>
> | Aspect | NAME/URI | NUMERIC |
> |--------|----------|---------|
> | Stability | High (semantic) | Low (positional) |
> | Compactness | Low | High |
> | Human readable | Yes | No |
> | Model refactoring safe | Mostly* | No |
> | Recommended for persistence | Yes | No |
>
> *Names can also change via refactoring, but this is explicit and intentional.

---

## 3. Field Ordering

Controls the order of properties in serialized output.

### Field Order Mode

| Mode | Description |
|------|-------------|
| `DECLARATION` (default) | Features in EClass declaration order |
| `ALPHABETICAL` | Features sorted alphabetically by key |

### Metadata Fields Position

| Option | Default | Description |
|--------|---------|-------------|
| `metadataFieldsFirst` | `true` | Place `_type`, `_id`, `_supertype` before features |

When `metadataFieldsFirst=true`, the output order is:
1. `_id` (if enabled)
2. `_type` (if enabled)
3. `_supertype` (if enabled)
4. Features (in configured order)

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .fieldOrder(FieldOrder.DECLARATION)    // default
    .metadataFieldsFirst(true)             // default: metadata fields first
    .build();

// Alphabetical ordering with ID always first
CodecConfig alphabetical = CodecConfig.builder()
    .fieldOrder(FieldOrder.ALPHABETICAL)
    .metadataFieldsFirst(true)             // _id, _type, _supertype first, then alphabetical
    .build();
```

**EAnnotation (on EPackage):**
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="fieldOrder" value="ALPHABETICAL"/>
  <details key="metadataFieldsFirst" value="true"/>
</eAnnotations>
```

---

## 4. Global Feature Ignore List

A codec-wide list of feature names to skip during **both serialization and deserialization**, regardless of individual feature configuration.

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `globalIgnoreFeatures` | `List<String>` | empty | Feature names to always skip |

This is useful for:
- Skipping audit fields (`createdAt`, `updatedAt`, `version`) across all EClasses
- Excluding internal/technical features from API output
- Temporary exclusion without modifying model annotations

**Java Builder:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .globalIgnoreFeatures("createdAt", "updatedAt", "version", "internalId")
    .build();

// Or add one at a time:
CodecConfiguration config = CodecConfiguration.builder()
    .globalIgnore("createdAt")
    .globalIgnore("updatedAt")
    .build();
```

**EAnnotation (on EPackage):**
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="globalIgnoreFeatures" value="createdAt,updatedAt,version"/>
</eAnnotations>
```

### 4.1 Behavior

| Operation | Behavior |
|-----------|----------|
| **Serialization** | Feature is omitted from output |
| **Deserialization** | Feature value in JSON is ignored (not set on EObject) |

**Precedence:** Global ignore list takes precedence over feature-level `serialize=true`. If a feature name is in the global ignore list, it will not be serialized even if explicitly enabled.

### 4.2 Use Case: API Versioning

Global ignore is useful for API versioning where certain fields should not be exposed:

```java
// V1 API - hide new fields
CodecConfiguration v1Config = CodecConfiguration.builder()
    .globalIgnoreFeatures("newFieldAddedInV2", "anotherV2Field")
    .build();

// V2 API - expose all fields
CodecConfiguration v2Config = CodecConfiguration.builder()
    .build();
```

When deserializing with global ignore:
- If the JSON contains an ignored field, it is silently skipped
- The EObject's feature retains its default value
- No error or warning is raised

---

## 5. Default Global Settings

| Setting | Default Value |
|---------|---------------|
| Smart Compression | `OFF` |
| NUMERIC mode | `OFF` |
| Field Order | `DECLARATION` |
| Metadata Fields First | `true` |
| Global Ignore Features | empty |

---

[Next: Type Serialization →](04-type.md)
