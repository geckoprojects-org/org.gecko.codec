# Global Configuration Options

[← Back to Overview](00-overview.md) | [← Configuration Hierarchy](03-config-hierarchy.md)

---

These options apply globally across all serialization targets (type, ID, reference, supertype, features).

## 1. Smart Compression

Smart Compression reduces redundancy by using **simple type names** instead of full URIs when the type belongs to the same schema as the root object.

### 1.1 Core Principle

When smart compression is enabled, type values are written as **simple names** (e.g., `"Manager"`) instead of **full URIs** (e.g., `"http://example.org/1.0#//Manager"`) when the type's schema matches the root object's schema.

| Scenario | Smart Compression OFF | Smart Compression ON |
|----------|----------------------|----------------------|
| Type same schema as root | Write full URI | Write simple name |
| Type different schema | Write full URI | Write full URI |
| SuperType same schema | Write full URI | Write simple name |
| SuperType different schema | Write full URI | Write full URI |

**Key Rule:** `_type` is always written when type information is enabled. Smart compression only affects the **format** of the type value (simple name vs full URI), not whether to write it.

### 1.2 Context Schema

The "context schema" (root schema) is derived from the root object's type declaration:

| Type Strategy | Context Schema Source |
|---------------|----------------------|
| URI | Parsed from `_type` URI (e.g., `"http://example.org/1.0#//Company"` → `http://example.org/1.0`) |
| SCHEMA_AND_TYPE | Explicitly from `_schema` field |
| NAME | From root object's EPackage nsURI |
| MAPPED | From root object's EPackage nsURI |

### 1.3 Applicability by Type Strategy

Smart compression requires a **context schema** to be established from the root object. This means smart compression only applies when the root object writes a `_type` field.

| Type Strategy | Smart Compression Applies? | Reason |
|---------------|---------------------------|--------|
| **URI** | ✅ Yes | Root `_type` establishes context schema |
| **SCHEMA_AND_TYPE** | ✅ Yes | Root `_schema` establishes context schema |
| **NAME** | ✅ Yes | Root `_type` + EPackage establishes context |
| **MAPPED** | ✅ Yes | Root `_type` + EPackage establishes context |
| **NUMERIC** | ✅ Yes | Root `_type` + schema establishes context |
| **DiscriminatorPath** | ❌ No | No root `_type` field to establish context |

> **Note:** When using `discriminatorPath` (featurePath-based type resolution), the root object does not write a `_type` field. Without a root `_type`, there is no context schema, and smart compression cannot determine which types are "same schema". In this case, all contained objects will use full URIs regardless of the smart compression setting.

### 1.4 Configuration

```java
CodecConfiguration.builder()
    .smartCompression(true)
    .build();
```

### 1.5 Examples

#### Example 1: URI Strategy with Containment References

**Model:**
- `http://example.org/1.0`: `Company`, `Person`, `Manager` (Manager extends Person)
- `http://external.org/hr/1.0`: `Contractor`
- `Company.employees: Person[*]` (containment)

**Without smart compression:**
```json
{
  "_type": "http://example.org/1.0#//Company",
  "employees": [
    { "_type": "http://example.org/1.0#//Person", "name": "John" },
    { "_type": "http://example.org/1.0#//Manager", "name": "Jane" },
    { "_type": "http://external.org/hr/1.0#//Contractor", "name": "Bob" }
  ]
}
```

**With smart compression:**
```json
{
  "_type": "http://example.org/1.0#//Company",
  "employees": [
    { "_type": "Person", "name": "John" },
    { "_type": "Manager", "name": "Jane" },
    { "_type": "http://external.org/hr/1.0#//Contractor", "name": "Bob" }
  ]
}
```

Note: `Person` and `Manager` use simple names (same schema), `Contractor` uses full URI (different schema).

#### Example 2: SCHEMA_AND_TYPE Strategy with Containment References

**Without smart compression:**
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "Company",
  "employees": [
    { "_type": "http://example.org/1.0#//Person", "name": "John" },
    { "_type": "http://example.org/1.0#//Manager", "name": "Jane" },
    { "_type": "http://external.org/hr/1.0#//Contractor", "name": "Bob" }
  ]
}
```

**With smart compression:**
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "Company",
  "employees": [
    { "_type": "Person", "name": "John" },
    { "_type": "Manager", "name": "Jane" },
    { "_type": "http://external.org/hr/1.0#//Contractor", "name": "Bob" }
  ]
}
```

#### Example 3: Non-Containment References

**Model:**
- `Company.ceo: Person` (non-containment, single)
- `Company.partners: Company[*]` (non-containment, multi)

**Without smart compression:**
```json
{
  "_type": "http://example.org/1.0#//Company",
  "name": "Acme",
  "ceo": { "_type": "http://example.org/1.0#//Manager", "_ref": "jane-123" },
  "partners": [
    { "_type": "http://example.org/1.0#//Company", "_ref": "partner-1" },
    { "_type": "http://external.org/biz/1.0#//Corporation", "_ref": "corp-99" }
  ]
}
```

**With smart compression:**
```json
{
  "_type": "http://example.org/1.0#//Company",
  "name": "Acme",
  "ceo": { "_type": "Manager", "_ref": "jane-123" },
  "partners": [
    { "_type": "Company", "_ref": "partner-1" },
    { "_type": "http://external.org/biz/1.0#//Corporation", "_ref": "corp-99" }
  ]
}
```

#### Example 4: SuperTypes with STRUCTURED Format

**Model:**
- `http://example.org/1.0`: `Person` extends `Entity`
- `http://audit.org/1.0`: `Auditable` (external supertype)

**Without smart compression:**
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": [
      "http://example.org/1.0#//Entity",
      "http://audit.org/1.0#//Auditable"
    ]
  },
  "name": "John"
}
```

**With smart compression:**
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": [
      "Entity",
      "http://audit.org/1.0#//Auditable"
    ]
  },
  "name": "John"
}
```

Note: `Entity` uses simple name (same schema), `Auditable` uses full URI (different schema).

#### Example 5: SuperTypes with PLAIN Format

**Without smart compression:**
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "Person",
  "_supertype": [
    "http://example.org/1.0#//Entity",
    "http://audit.org/1.0#//Auditable"
  ],
  "name": "John"
}
```

**With smart compression:**
```json
{
  "_schema": "http://example.org/1.0",
  "_type": "Person",
  "_supertype": [
    "Entity",
    "http://audit.org/1.0#//Auditable"
  ],
  "name": "John"
}
```

#### Example 6: Nested Containment (3 Levels Deep)

**Model:**
- `http://example.org/1.0`: `Company`, `Department`, `Person`, `Manager` (Manager extends Person)
- `Company.departments: Department[*]` (containment)
- `Department.manager: Person` (containment)

This example demonstrates that the context schema from the root object propagates to all nested levels.

**Without smart compression:**
```json
{
  "_type": "http://example.org/1.0#//Company",
  "name": "Acme",
  "departments": [
    {
      "_type": "http://example.org/1.0#//Department",
      "name": "Engineering",
      "manager": {
        "_type": "http://example.org/1.0#//Manager",
        "name": "Jane"
      }
    },
    {
      "_type": "http://example.org/1.0#//Department",
      "name": "Sales",
      "manager": {
        "_type": "http://example.org/1.0#//Person",
        "name": "Bob"
      }
    }
  ]
}
```

**With smart compression:**
```json
{
  "_type": "http://example.org/1.0#//Company",
  "name": "Acme",
  "departments": [
    {
      "_type": "Department",
      "name": "Engineering",
      "manager": {
        "_type": "Manager",
        "name": "Jane"
      }
    },
    {
      "_type": "Department",
      "name": "Sales",
      "manager": {
        "_type": "Person",
        "name": "Bob"
      }
    }
  ]
}
```

Note: All nested objects (`Department`, `Manager`, `Person`) use simple names because they all belong to the same schema as the root `Company`.

### 1.6 Deserialization

When parsing `_type` or `_supertype` values during deserialization:

1. **Contains `#`** → Full URI, resolve directly
2. **Simple name** → Combine with root schema: `rootSchema + "#//" + simpleName`

The root schema is derived from:
- `_schema` field (if SCHEMA_AND_TYPE strategy)
- Parsed from root `_type` URI (if URI strategy)

**Example resolution:**
- Root: `"_type": "http://example.org/1.0#//Company"` → context schema = `http://example.org/1.0`
- Child: `"_type": "Manager"` → resolved as `http://example.org/1.0#//Manager`

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

[Next: Type Serialization →](05-type.md)
