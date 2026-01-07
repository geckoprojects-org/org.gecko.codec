# Reference Serialization

[← Back to Overview](00-overview.md) | [← ID Serialization](06-id.md)

---

This chapter covers both **non-containment references** and **cross-document containments**, as they share the same serialization format and configuration.

## 1. Reference Strategies

### 1.1 PLAIN Strategy

Single value (URI or ID):

```json
{
  "employer": "datainmotion"
}
```

Or as URI:
```json
{
  "employer": "http://example.org/data#//@companies.0"
}
```

### 1.2 STRUCTURED Strategy (Default)

Nested object with type and reference:

```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "datainmotion"
  }
}
```

**Configurable keys:**
- `typeKey`: type field (default: `_type`)
- `refKey`: reference field (default: `_ref`)

---

## 2. Multi-valued References

### PLAIN Strategy
```json
{
  "employees": ["john-doe", "jane-smith", "bob-wilson"]
}
```

### STRUCTURED Strategy
```json
{
  "employees": [
    { "_type": "http://example.org/person/1.0#//Employee", "_ref": "john-doe" },
    { "_type": "http://example.org/person/1.0#//Employee", "_ref": "jane-smith" },
    { "_type": "http://example.org/person/1.0#//Manager", "_ref": "bob-wilson" }
  ]
}
```

---

## 3. Reference Configuration

The configuration defines **keys and format**, not actual values.

### 3.1 EAnnotation (on EReference)

```xml
<!-- Switch to PLAIN format (STRUCTURED is default) -->
<eStructuralFeatures xsi:type="ecore:EReference" name="employer" eType="#//Company">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.reference"/>
    <details key="format" value="PLAIN"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `format` | PLAIN, STRUCTURED | STRUCTURED | SerializationFormat |
| `typeKey` | any string | `_type` | Key for type in STRUCTURED |
| `refKey` | any string | `_ref` | Key for reference value |
| `expand` | true, false | false | Serialize full object instead of proxy |

**Note:** Type information in STRUCTURED format follows the type configuration (see [Type Serialization](03-type.md)). With smart compression enabled, type is omitted when instance type equals reference type (see [Global Options - Smart Compression](02-global-options.md#1-smart-compression)).

### 3.2 Java Builder (Runtime Override)

**Minimal (default: STRUCTURED):**
```java
ReferenceSerializationConfig config = ReferenceSerializationConfig.builder().build();
```
**Resulting JSON:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "acme-corp"
  }
}
```

**PLAIN format:**
```java
ReferenceSerializationConfig config = ReferenceSerializationConfig.builder()
    .plain()
    .build();
```
**Resulting JSON:**
```json
{
  "employer": "acme-corp"
}
```

**STRUCTURED with custom keys:**
```java
ReferenceSerializationConfig config = ReferenceSerializationConfig.builder()
    .structured()
    .typeKey("$type")
    .refKey("$ref")
    .build();
```
**Resulting JSON:**
```json
{
  "employer": {
    "$type": "http://example.org/company/1.0#//Company",
    "$ref": "acme-corp"
  }
}
```

---

## 4. Proxy and Expand Handling

### 4.1 Default Behavior: Proxy Serialization

Non-containment references are serialized as **proxies by default**. This requires:
- `_type`: Type information (proxy URI doesn't always indicate type)
- `_ref`: Proxy URI for resolution

Only objects with a URI can be serialized as references (standard EMF behavior).

**Default proxy output:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "companies.json#//@companies.0"
  }
}
```

### 4.2 Expand: Inline Serialization

When expand is enabled and the reference is **resolved** (not a proxy), the referenced object is serialized inline instead of as a proxy reference.

**Important:** Expanded objects are serialized **without `_ref`**. This distinguishes them from proxy references during deserialization:
- With `_ref` → proxy (resolve via URI)
- Without `_ref` → orphan object (fully deserialized, not contained)

**Expanded output:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_id": "acme-corp",
    "name": "Acme Corporation",
    "industry": "Manufacturing"
  }
}
```

Note: No `_ref` field is present. The object is serialized with its full data, and on deserialization it becomes an orphan object (not a proxy).

**Expand Configuration:**

| Level | Option | Type | Default | Description |
|-------|--------|------|---------|-------------|
| Codec | `expandGlobal` | `boolean` | `false` | Expand ALL non-containment references |
| Codec | `expand` | `EReference...` or `String...` | empty | Expand SPECIFIC references only |
| Codec | `expandDepth` | `int` | `1` | Max depth for nested expansion (only `1` currently supported) |
| Codec | `expandIgnoreBidirectional` | `boolean` | `true` | Skip opposite/bi-directional references |

A reference is expanded if:
- `expandGlobal=true`, OR
- the reference is in the `expand` list (by EReference or by name)

> **Implementation Status:** `expandDepth` values greater than 1 are not yet implemented. Currently, expansion only works for direct references (depth=1). Nested expansion (where expanded objects also expand their references) is planned for a future release.

**EAnnotation:**

On EClass (global for all references in this class):
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="expandGlobal" value="true"/>
  </eAnnotations>
</eClassifiers>
```

On EReference (specific reference):
```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="employer" eType="#//Company">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.reference"/>
    <details key="expand" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Java Builder:**

```java
// Expand ALL non-containment references
CodecConfig.builder()
    .expandGlobal(true)
    .expandDepth(2)
    .expandIgnoreBidirectional(true)
    .build();

// Expand SPECIFIC references by EReference (type-safe)
CodecConfig.builder()
    .expand(
        PersonPackage.eINSTANCE.getPerson_Employer(),
        PersonPackage.eINSTANCE.getPerson_Manager()
    )
    .build();

// Expand SPECIFIC references by name
CodecConfig.builder()
    .expand("employer", "manager")
    .build();

// Mixed: add references incrementally
CodecConfig.builder()
    .expand(PersonPackage.eINSTANCE.getPerson_Employer())
    .expand("manager")
    .build();
```

**Option Values:**

The `expand` option accepts multiple value types:

| Value Type | Example |
|------------|---------|
| `String` | `"employer"` |
| `String[]` | `new String[]{"employer", "manager"}` |
| `List<String>` | `List.of("employer", "manager")` |
| `EReference` | `PersonPackage.eINSTANCE.getPerson_Employer()` |
| `EReference[]` | `new EReference[]{...}` |
| `List<EReference>` | `List.of(...)` |

At runtime, string names are resolved against the current EClass to find the matching EReference.

### 4.3 Bi-directional Reference Handling

When expanding, bi-directional (opposite) references are **ignored by default** to prevent cycles:

```
Person.employer ←→ Company.employees (opposite)
```

If expanding `employer`, the `employees` back-reference in Company is skipped.

**Configuration:**
- `expandIgnoreBidirectional=true` (default): Skip opposite references
- `expandIgnoreBidirectional=false`: Include (use with caution, may cause cycles)

---

## 5. Type Configuration per Context

Type serialization can be configured differently for each context:

| Context | Config | Use Case |
|---------|--------|----------|
| Root objects | `type(...)` | Main serialization |
| Containments | `containmentType(...)` | Inline contained objects |
| References (proxy + expanded) | `referenceType(...)` | Non-containment refs |
| Per-feature | `feature(...).type(...)` | Specific reference |

**Java Builder:**
```java
CodecConfig.builder()
    .type(TypeSerializationConfig.builder().build())  // default: URI
    .containmentType(TypeSerializationConfig.builder()
        .strategy(TypeStrategy.NAME)  // contained: just name (context clear)
        .build())
    .referenceType(TypeSerializationConfig.builder()
        .strategy(TypeStrategy.URI)   // refs: full URI for resolution
        .build())
    .build();
```
**Resulting JSON:**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe",
  "name": "John Doe",
  "address": {
    "_type": "Address",
    "street": "123 Main St"
  },
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "companies.json#//@companies.0"
  }
}
```

---

## 6. Cross-Document Containment

When a contained object is stored in a different document, it is serialized similarly to a reference.

### 6.1 Cross-Document Containment Strategies

#### PLAIN Strategy

```json
{
  "address": "addresses.json#//@addresses.0"
}
```

#### STRUCTURED Strategy

```json
{
  "address": {
    "_type": "http://example.org/address/1.0#//Address",
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

Or with STRUCTURED type:

```json
{
  "address": {
    "_type": {
      "schema": "http://example.org/address/1.0",
      "name": "Address"
    },
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

### 6.2 Cross-Document Containment Configuration

Cross-document containments use the same configuration as non-containment references. This includes:
- **Format**: PLAIN or STRUCTURED (default: STRUCTURED)
- **Type configuration**: Same type strategy options
- **Smart compression**: Type omitted when instance type equals declared reference type
- **Expand**: Serialize full object instead of reference (if resolved and in memory)

**Detection:** The serializer detects cross-document containment when:
- The EReference is containment (`isContainment() == true`)
- The contained object's resource differs from the container's resource

> **Implementation Status:** Cross-document containment **resolution during deserialization** is not yet implemented. Serialization of cross-document containments works correctly (serialized as `_ref`), but deserialization will create proxy objects that require manual resolution via the ResourceSet.

**Type configuration:**
```java
CodecConfig.builder()
    .containmentType(TypeSerializationConfig.builder()
        .strategy(TypeStrategy.URI)  // full URI for cross-doc resolution
        .build())
    .build();
```

**With smart compression (when instance type equals declared reference type):**
```json
{
  "address": {
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

---

## 7. Default Reference Settings

| Setting | Default Value |
|---------|---------------|
| Format | `STRUCTURED` |
| Type Key | `_type` |
| Ref Key | `_ref` |
| Expand Global | `false` |
| Expand (specific refs) | empty |
| Expand Depth | `1` |
| Expand Ignore Bidirectional | `true` |

**Default Output:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "acme-corp"
  }
}
```

---

## 8. Deserialization

### 8.1 Reference Resolution Phases

1. **First pass**: Deserialize all objects, collect unresolved references
2. **Second pass**: Resolve references using collected ID/URI mappings

### 8.2 Non-Containment Reference Deserialization

Non-containment references are deserialized based on the presence of `_ref`:

| Has `_ref`? | Has other fields? | Result |
|-------------|-------------------|--------|
| Yes | No | **Proxy** - URI only |
| Yes | Yes | **Proxy with projection** - URI + populated fields |
| No | Yes | **Orphan object** - fully deserialized, not contained |

#### 8.2.1 Proxy Reference (default)

When `_ref` is present, create a proxy object:

```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "companies.json#//@companies.0"
  }
}
```

**Deserialization:**
1. Create EObject instance using `EcoreUtil.create(eClass)`
2. Cast to `InternalEObject` and call `eSetProxyURI(uri)`
3. Set on parent as non-containment reference

#### 8.2.2 Proxy with Projection

When `_ref` is present along with additional fields, create a proxy with projected data populated:

```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "companies.json#//@companies.0",
    "name": "Acme Corporation",
    "industry": "Manufacturing"
  }
}
```

**Deserialization:**
1. Create EObject instance using `EcoreUtil.create(eClass)`
2. Cast to `InternalEObject` and call `eSetProxyURI(uri)`
3. Populate additional fields (`name`, `industry`)
4. Set on parent as non-containment reference

This is useful for projections where commonly needed fields are included to avoid resolving the full object.

#### 8.2.3 Expanded Orphan Object

When `_ref` is absent, deserialize as a full orphan object (expanded non-containment):

```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_id": "acme-corp",
    "name": "Acme Corporation",
    "industry": "Manufacturing"
  }
}
```

**Deserialization:**
1. Deserialize as full EObject (same as containment)
2. Object is **not contained** by parent - it's an orphan
3. Object has **no resource** assigned
4. Set on parent as non-containment reference

**Note:** Orphan objects are transient - they exist in memory but are not part of any resource. This matches the expand use case where data is embedded for reading convenience but won't be saved back in this form.

### 8.3 Cross-Resource References

When a reference URI points to another resource:
1. Check if resource is already loaded in ResourceSet
2. If not loaded, attempt to load (depends on ResourceSet configuration)
3. Create proxy if load fails or is deferred

### 8.4 Deserialization Options

| Option | Type | Description |
|--------|------|-------------|
| `RESOLVE_PROXIES` | boolean | Resolve proxies during load |
| `STRICT_MODE` | boolean | Fail on unknown fields |

---

[Next: Feature Serialization →](08-feature.md)
