# Global Configuration Options

[← Common Types](04-common-types.md) | [Next: Type Serialization →](06-type.md)

---

These options apply globally across all serialization targets (type, ID, reference, supertype, features).

> **See also:** [Annotation Reference](16-annotation-reference.md) for complete configuration key reference and scope applicability matrix.

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
| **NUMERIC** | ✅ Yes | Root `_type` + schema establishes context |
| **NONE** | ❌ No | No root `_type` field to establish context |

> **Note:** When using `typeStrategy=NONE` or discriminator mapping without a `_type` field, there is no context schema. Smart compression cannot determine which types are "same schema", so all contained objects will use full URIs regardless of the smart compression setting.

> **See also:** [Discriminator Mapping](08-discriminator-mapping.md) for type resolution via feature paths.

### 1.4 Configuration

| Annotation Key | Property Key | Type | Default |
|----------------|--------------|------|---------|
| `smartCompression` | `codec.smartCompression` | `boolean` | `false` |

**Java Builder:**
```java
CodecConfiguration.builder()
    .smartCompression(true)
    .build();
```

**Property Map:**
```java
Map<String, Object> options = new HashMap<>();
options.put("codec.smartCompression", true);
```

**EAnnotation (on EPackage):**
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="smartCompression" value="true"/>
</eAnnotations>
```

> **Important - Symmetry requirement:** `smartCompression` must be configured **identically** for serialization and deserialization. If you serialize with `smartCompression=true`, you must also deserialize with `smartCompression=true`. A mismatch produces a WARNING at runtime and may cause type resolution failures.

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

## 2. NUMERIC Strategy

Uses EClass classifier IDs instead of names for maximum compactness.

**Configuration:**

```java
// Global - applies to type serialization
CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.NUMERIC)
    .typeSchemaKey("s")       // Short keys for compactness
    .typeKey("c")
    .build();
```

**Property Map:**
```java
Map<String, Object> options = new HashMap<>();
options.put("codec.typeStrategy", "NUMERIC");
options.put("codec.typeSchemaKey", "s");
options.put("codec.typeKey", "c");
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

## 3. Field Ordering (Future Feature)

> **Status:** Planned for future implementation. The configuration keys below are proposals.

Controls the order of properties in serialized output.

### Field Order Mode

| Mode | Description |
|------|-------------|
| `DECLARATION` **(default)** | Features in EClass declaration order |
| `ALPHABETICAL` | Features sorted alphabetically by key |

### Metadata Fields Position

| Option | Default | Description |
|--------|---------|-------------|
| `metadataFieldsFirst` | `true` | Place `_type`, `_id`, `_supertype` before features |

When `metadataFieldsFirst=true`, the output order is:
1. `_type` (if enabled) — default first when `idOnTop=false`
2. `_supertype` (if enabled)
3. `_id` (if enabled) — or first if `idOnTop=true`
4. Features (in configured order)

> **Note:** The relative order of `_id` and `_type` is controlled by `idOnTop` (see [09-id.md §8.7](09-id.md#87-metadata-field-ordering-idontop)). The default (`idOnTop=false`) places `_type` before `_id`.

**Java Builder (proposed):**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .fieldOrder(FieldOrder.DECLARATION)    // default
    .metadataFieldsFirst(true)             // default: metadata fields first
    .build();

// Alphabetical ordering with ID always first
CodecConfiguration alphabetical = CodecConfiguration.builder()
    .fieldOrder(FieldOrder.ALPHABETICAL)
    .metadataFieldsFirst(true)             // _id, _type, _supertype first, then alphabetical
    .build();
```

**EAnnotation (proposed, on EPackage):**
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="fieldOrder" value="ALPHABETICAL"/>
  <details key="metadataFieldsFirst" value="true"/>
</eAnnotations>
```

---

## 4. Global Feature Ignore List

A codec-wide list of feature names to skip during **both serialization and deserialization**, regardless of individual feature configuration.

| Annotation Key | Property Key | Type | Default |
|----------------|--------------|------|---------|
| `ignoreFeatures` | `codec.ignoreFeatures` | `List<String>` | empty |

This is useful for:
- Skipping audit fields (`createdAt`, `updatedAt`, `version`) across all EClasses
- Excluding internal/technical features from API output
- Temporary exclusion without modifying model annotations

**Java Builder:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .ignoreFeatures("createdAt", "updatedAt", "version", "internalId")
    .build();
```

**Property Map:**
```java
Map<String, Object> options = new HashMap<>();
options.put("codec.ignoreFeatures", List.of("createdAt", "updatedAt", "version"));
```

**EAnnotation (on EPackage or EClass):**
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="ignoreFeatures" value="createdAt,updatedAt,version"/>
</eAnnotations>
```

> **See also:** [Annotation Reference](16-annotation-reference.md) (Feature Visibility) for per-feature `ignore` configuration.

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

| Setting | Property Key | Default Value |
|---------|--------------|---------------|
| Smart Compression | `codec.smartCompression` | `false` |
| Type Strategy | `codec.typeStrategy` | `URI` |
| Type Format | `codec.typeFormat` | `PLAIN` |
| Type Scope | `codec.typeScope` | `ALL` |
| ID Strategy | `codec.idStrategy` | `ID_FIELD` |
| ID Format | `codec.idFormat` | `PLAIN` |
| ID Scope | `codec.idScope` | `ALL` |
| Ignore Features | `codec.ignoreFeatures` | empty |

> **Note:** StrategyScope (`typeScope`, `idScope`, etc.) is **runtime-only** configuration - not available via EAnnotations. See [Configuration Resolution](02-config-resolution.md) (section 5) for details.

---

[Next: Type Serialization →](06-type.md)
