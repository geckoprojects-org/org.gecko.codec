# Reference Serialization

[← ID Serialization](09-id.md) | [Next: Feature Serialization →](11-feature.md)

---

> **See also:**
> - [Naming Conventions](03-naming-conventions.md) for key naming conventions
> - [Annotation Reference](16-annotation-reference.md) (Reference Configuration) for complete configuration keys

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
- `refTypeKey`: type field (default: `_type`)
- `refKey`: reference field (default: `_ref`)

---

## 2. Multi-valued References

### PLAIN Strategy
```json
{
  "employees": ["john-doe", "jane-smith", "bob-wilson"]
}
```

### STRUCTURED Strategy (Default)
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

### 3.1 Configuration Keys

| Annotation Key | Property Key | Global | ERef | Default | Description |
|----------------|--------------|:------:|:----:|---------|-------------|
| `refFormat` | `codec.refFormat` | ✅ | ✅ | `STRUCTURED` | Output format (PLAIN, STRUCTURED) |
| `refKey` | `codec.refKey` | ✅ | ✅ | `_ref` | Reference value key |
| `refTypeKey` | `codec.refTypeKey` | ✅ | ✅ | `_type` | Type key in STRUCTURED |
| `expand` | `codec.expand` | ✅ | ✅ | `false` | Inline full object vs proxy |

**Note:** Reference configuration is NOT supported on EClass (different references on the same class may need different formats).

**Note:** Type information in STRUCTURED format follows the type configuration (see [Type Serialization](06-type.md)). With smart compression enabled, type is omitted when instance type equals reference type (see [Global Options - Smart Compression](05-global-options.md#1-smart-compression)).

### 3.2 EAnnotation (on EReference)

```xml
<!-- Switch to PLAIN format (STRUCTURED is default) -->
<eStructuralFeatures xsi:type="ecore:EReference" name="employer" eType="#//Company">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="refFormat" value="PLAIN"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 3.3 Java Builder (Runtime Override)

**Minimal (default: STRUCTURED):**
```java
CodecConfiguration config = CodecConfiguration.builder().build();
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
CodecConfiguration config = CodecConfiguration.builder()
    .refFormat(SerializationFormat.PLAIN)
    .build();
```
**Resulting JSON:**
```json
{
  "employer": "acme-corp"
}
```

**STRUCTURED with custom keys (JSON-LD style):**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .refFormat(SerializationFormat.STRUCTURED)
    .refTypeKey("@type")
    .refKey("@id")
    .build();
```
**Resulting JSON:**
```json
{
  "employer": {
    "@type": "http://example.org/company/1.0#//Company",
    "@id": "acme-corp"
  }
}
```

### 3.4 Property Map

```java
Map<String, Object> options = Map.of(
    "codec.refFormat", "PLAIN"
);

// Or with custom keys
Map<String, Object> options = Map.of(
    "codec.refFormat", "STRUCTURED",
    "codec.refTypeKey", "@type",
    "codec.refKey", "@id"
);
```

---

## 4. Per-Reference Format Configuration

Unlike Type (where strategy is per-class), Reference format **can be configured per-reference**. This allows different references on the same class to use different formats.

### 4.1 Why Per-Reference Format is Supported

| Aspect | Type Strategy | Reference Format |
|--------|---------------|------------------|
| **Applies to** | The object being serialized | How a reference is written |
| **Semantic binding** | Object has one type identity | Different references may need different formats |
| **Use case** | "Person is always identified as Person" | "employer uses STRUCTURED, friends uses PLAIN" |

**Example:** An EClass with multiple references using different formats:

```java
// Reference to employer uses STRUCTURED (for type safety)
ReferenceConfigBuilder.forReference(PersonPackage.Literals.PERSON__EMPLOYER)
    .refFormat(SerializationFormat.STRUCTURED)
    .build();

// Reference to friends uses PLAIN (for compactness)
ReferenceConfigBuilder.forReference(PersonPackage.Literals.PERSON__FRIENDS)
    .refFormat(SerializationFormat.PLAIN)
    .build();
```

**Resulting JSON:**
```json
{
  "_type": "Person",
  "name": "John",
  "employer": {
    "_type": "http://example.org#//Company",
    "_ref": "acme-corp"
  },
  "friends": ["alice", "bob"]
}
```

### 4.2 Configuration Inheritance

Reference format follows the standard configuration hierarchy:

1. **Per-reference annotation/config** (highest priority)
2. **Global codec config**
3. **Built-in default** (`STRUCTURED`)

If no per-reference format is specified, the global default applies.

---

## 5. Proxy and Expand Handling

### 5.1 Default Behavior: Proxy Serialization

Non-containment references are serialized as **proxies by default**. This requires:
- `type`: Type information (proxy URI doesn't always indicate type)
- `ref`: Proxy URI for resolution

Only objects with a URI can be serialized as references (standard EMF behavior).

**Default proxy output (STRUCTURED):**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "companies.json#//@companies.0"
  }
}
```

#### 5.1.1 Serialization Algorithm

The serializer follows this decision tree for each reference value:

```
serializeReference(target):
  1. Is target null?
     → YES: Write null (if serializeNull enabled) or skip

  2. Is this a containment reference AND target in same resource?
     → YES: Serialize target inline (nested object)

  3. Is this a containment reference AND target in different resource?
     → YES: This is CROSS-DOCUMENT CONTAINMENT → serialize as reference

  4. Is expand enabled for this reference AND target is resolved (not proxy)?
     → YES: Serialize target inline (expanded reference)

  5. DEFAULT: Serialize as proxy reference
```

**URI Determination:**

| Scenario | URI Format | Example |
|----------|------------|---------|
| Same-document reference | Fragment only | `//@employees.0` |
| Cross-document reference | Relative URI from source | `other.json#//@employees.0` |
| Proxy (target is proxy) | Use existing proxy URI | (preserved from original) |
| Object without resource | Fallback to EClass URI | `http://example.org/1.0#//Person` |

**Proxy Detection:**

When the target object `eIsProxy() == true`:
1. The proxy is **NOT expanded** (expand only works on resolved objects)
2. The proxy URI is obtained via `InternalEObject.eProxyURI()`
3. The URI is serialized as-is or made relative to the source resource

**Type Information:**

Type is always included in proxy references to enable type-safe deserialization. When smart compression is enabled and the target type is from the same schema as the root object, only the simple name is used.

**Edge Cases:**

| Scenario | Behavior |
|----------|----------|
| Reference to unresolved proxy | Serialize using proxy URI |
| Reference to object with no resource | Use fallback EClass URI |
| Bidirectional reference with expand | Skip if `expandIgnoreBidirectional=true` |
| Multi-valued reference | Array of proxy/expanded objects |

### 5.2 Expand: Inline Serialization

When expand is enabled and the reference is **resolved** (not a proxy), the referenced object is serialized inline instead of as a proxy reference.

**Important:** Expanded objects are serialized **without `ref`**. This distinguishes them from proxy references during deserialization:
- With `ref` → proxy (resolve via URI)
- Without `ref` → orphan object (fully deserialized, not contained)

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

Note: No `ref` field is present. The object is serialized with its full data, and on deserialization it becomes an orphan object (not a proxy).

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
    <details key="expand" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Java Builder:**

```java
// Expand ALL non-containment references
CodecConfiguration.builder()
    .expandGlobal(true)
    .expandDepth(2)
    .expandIgnoreBidirectional(true)
    .build();

// Expand SPECIFIC references by EReference (type-safe)
CodecConfiguration.builder()
    .expand(
        PersonPackage.eINSTANCE.getPerson_Employer(),
        PersonPackage.eINSTANCE.getPerson_Manager()
    )
    .build();

// Expand SPECIFIC references by name
CodecConfiguration.builder()
    .expand("employer", "manager")
    .build();

// Mixed: add references incrementally
CodecConfiguration.builder()
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

### 5.3 Bi-directional Reference Handling

When expanding, bi-directional (opposite) references are **ignored by default** to prevent cycles:

```
Person.employer ←→ Company.employees (opposite)
```

If expanding `employer`, the `employees` back-reference in Company is skipped.

**Configuration:**
- `expandIgnoreBidirectional=true` **(default)**: Skip opposite references
- `expandIgnoreBidirectional=false`: Include (use with caution, may cause cycles)

---

## 6. Type Configuration per Context

Type serialization can be configured differently for each context:

| Context | Config | Use Case |
|---------|--------|----------|
| Root objects | `typeStrategy(...)` | Main serialization |
| Containments | `containmentTypeStrategy(...)` | Inline contained objects |
| References (proxy + expanded) | `referenceTypeStrategy(...)` | Non-containment refs |

**Java Builder:**
```java
CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.URI)  // root: full URI
    .containmentTypeStrategy(TypeStrategy.NAME)  // contained: just name (context clear)
    .referenceTypeStrategy(TypeStrategy.URI)   // refs: full URI for resolution
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

## 7. Cross-Document Containment

When a contained object is stored in a different document, it is serialized similarly to a reference.

### 7.1 Cross-Document Containment Strategies

#### PLAIN Strategy

```json
{
  "address": "addresses.json#//@addresses.0"
}
```

#### STRUCTURED Strategy (Default)

```json
{
  "address": {
    "_type": "http://example.org/address/1.0#//Address",
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

Or with STRUCTURED type format:

```json
{
  "address": {
    "_type": {
      "schema": "http://example.org/address/1.0",
      "type": "Address"
    },
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

### 7.2 Cross-Document Containment Configuration

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
CodecConfiguration.builder()
    .containmentTypeStrategy(TypeStrategy.URI)  // full URI for cross-doc resolution
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

## 8. Default Reference Settings

| Setting | Property Key | Default Value |
|---------|--------------|---------------|
| Format | `codec.refFormat` | `STRUCTURED` |
| Type Key | `codec.refTypeKey` | `_type` |
| Ref Key | `codec.refKey` | `_ref` |
| Expand Global | `codec.expandGlobal` | `false` |
| Expand (specific refs) | `codec.expand` | empty |
| Expand Depth | `codec.expandDepth` | `1` |
| Expand Ignore Bidirectional | `codec.expandIgnoreBidirectional` | `true` |

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

## 9. Deserialization

### 9.1 Reference Resolution Phases

1. **First pass**: Deserialize all objects, collect unresolved references
2. **Second pass**: Resolve references using collected ID/URI mappings

### 9.2 Non-Containment Reference Deserialization

Non-containment references are deserialized based on the presence of `_ref`:

| Has `_ref`? | Has other fields? | Result |
|-------------|-------------------|--------|
| Yes | No | **Proxy** - URI only |
| Yes | Yes | **Proxy with projection** - URI + populated fields |
| No | Yes | **Orphan object** - fully deserialized, not contained |

#### 9.2.1 Proxy Reference **(default)**

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

#### 9.2.2 Proxy with Projection

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

#### 9.2.3 Expanded Orphan Object

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

### 9.3 Cross-Resource References

> **Current Limitation:** Full automatic cross-resource reference resolution during deserialization is not yet implemented. The codec creates proxy objects that must be resolved manually via the ResourceSet.

#### 9.3.1 Supported Behavior

**Serialization:** ✓ Fully supported
- Cross-document containment references are serialized as `_ref` URIs
- Relative URIs are computed from source to target resource

**Deserialization:** Partial (proxy creation only)
- When a reference URI points to another resource, a **proxy object** is created
- The proxy has its `eProxyURI` set to the reference URI
- The proxy is **not automatically resolved**

#### 9.3.2 Workaround: Manual Resolution

To resolve cross-resource references after loading:

**Option 1: Pre-load resources**

Load all referenced resources into the ResourceSet before deserializing:

```java
ResourceSet resourceSet = new ResourceSetImpl();
resourceSet.getResourceFactoryRegistry()
    .getExtensionToFactoryMap()
    .put("json", new CodecResourceFactory(...));

// Pre-load all resources
Resource companiesResource = resourceSet.getResource(
    URI.createURI("companies.json"), true);
Resource personsResource = resourceSet.getResource(
    URI.createURI("persons.json"), true);

// Now proxies can be resolved via EcoreUtil
EcoreUtil.resolveAll(resourceSet);
```

**Option 2: Lazy resolution**

Use EMF's lazy resolution mechanism:

```java
ResourceSet resourceSet = new ResourceSetImpl();
resourceSet.getResourceFactoryRegistry()
    .getExtensionToFactoryMap()
    .put("json", new CodecResourceFactory(...));

// Load main resource (creates proxies for cross-references)
Resource resource = resourceSet.getResource(
    URI.createURI("persons.json"), true);
Person person = (Person) resource.getContents().get(0);

// Accessing the reference triggers resolution (loads companies.json)
Company employer = person.getEmployer();  // Resolves proxy automatically
```

**Option 3: Explicit resolution**

Resolve specific proxies manually:

```java
Person person = ...; // loaded with unresolved proxy
EObject employerProxy = person.eGet(PersonPackage.Literals.PERSON__EMPLOYER, false);

if (employerProxy.eIsProxy()) {
    URI proxyURI = ((InternalEObject) employerProxy).eProxyURI();
    EObject resolved = resourceSet.getEObject(proxyURI, true);
    person.setEmployer((Company) resolved);
}
```

#### 9.3.3 Expected Future Behavior

Full cross-resource resolution will:
1. Check if resource is already loaded in ResourceSet
2. If not loaded, attempt to load (based on ResourceSet configuration)
3. Resolve proxy to loaded object
4. Fall back to proxy if load fails or is deferred

**Configuration (planned):**

| Option | Type | Description |
|--------|------|-------------|
| `RESOLVE_CROSS_RESOURCE` | boolean | Auto-resolve cross-resource references during load |
| `LOAD_REFERENCED_RESOURCES` | boolean | Auto-load referenced resources into ResourceSet |

### 9.4 Deserialization Options

| Option | Type | Description |
|--------|------|-------------|
| `RESOLVE_PROXIES` | boolean | Resolve proxies during load |
| `STRICT_MODE` | boolean | Fail on unknown fields |

---

[Next: Feature Serialization →](11-feature.md)
