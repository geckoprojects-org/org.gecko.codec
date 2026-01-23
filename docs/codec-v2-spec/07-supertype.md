# SuperType Serialization

[← Type Serialization](06-type.md) | [Next: Discriminator Mapping →](08-discriminator-mapping.md)

---

> **See also:**
> - [Naming Conventions](03-naming-conventions.md) for key naming conventions
> - [Annotation Reference](16-annotation-reference.md) (SuperType Configuration) for complete configuration keys

---

SuperType serialization describes class inheritance hierarchy. It has two orthogonal dimensions:
- **Strategy** (`superTypeStrategy`): ALL, ALL_EMF, SINGLE, NONE - which supertypes to include
- **Presentation** (`superTypeAsArray`): true (array) or false (string) - how to present multiple values

**Important:** SuperType is an **extension of Type configuration**:
- Inherits `typeFormat` when `superTypeFormat` is not explicitly set
- Inherits `typeScope` for scope control
- Only applies at Global and EClass level (not EReference or EAttribute)

---

## 1. Format Follows Type

| Type Format | SuperType Output Location |
|-------------|---------------------------|
| PLAIN | Standalone `_supertype` field at root level |
| STRUCTURED | Inside `_type` object as `supertype` field |

---

## 2. PLAIN Format (Type is PLAIN)

When Type format is PLAIN, SuperType is a standalone field at root level.

### 2.1 ARRAY Presentation (Default)

Array of URI strings:

```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": ["http://example.org/1.0#//Entity", "http://audit.org/1.0#//Auditable"]
}
```

With smart compression (same namespace → simple name):

```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
}
```

### 2.2 STRING Presentation

Separator-joined URI string (default separator is `,`):

```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": "http://example.org/1.0#//Entity,http://audit.org/1.0#//Auditable"
}
```

With smart compression:

```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": "Entity,http://audit.org/1.0#//Auditable"
}
```

---

## 3. STRUCTURED Format (Type is STRUCTURED)

When Type format is STRUCTURED, SuperType is included **inside** the `_type` object as a `supertype` field. Presentation (ARRAY or STRING) still applies.

### 3.1 ARRAY Presentation (Default)

```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
  }
}
```

### 3.2 STRING Presentation

```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": "Entity,http://audit.org/1.0#//Auditable"
  }
}
```

---

## 4. Value Resolution Rules

SuperType values follow namespace matching rules based on the root EClass's EPackage:

| Condition | SuperType Value |
|-----------|-----------------|
| Supertype from **same namespace** as root EClass | Simple EClass name (e.g., `"Entity"`) |
| Supertype from **different namespace** | Full EClass URI (e.g., `"http://audit.org/1.0#//Auditable"`) |
| Smart compression **OFF** | Always full EClass URI |

**Example:** If root type is `http://example.org/1.0#//Person`:
- `Entity` from `http://example.org/1.0` → `"Entity"` (same namespace)
- `Auditable` from `http://audit.org/1.0` → `"http://audit.org/1.0#//Auditable"` (different namespace)

---

## 5. SuperType Strategy

The `superTypeStrategy` configuration controls which supertypes to include:

| Value | Description |
|-------|-------------|
| `ALL` **(default)** | All domain model supertypes (excludes EMF base classes like EObject, EModelElement) |
| `ALL_EMF` | All supertypes including EMF base classes |
| `SINGLE` | Only the immediate/direct supertype |
| `NONE` | No supertypes (equivalent to `superTypeSerialize=false`) |

**Example:** Given `Employee extends Person extends Entity`:

| Strategy | Output |
|----------|--------|
| `ALL` | `["Person", "Entity"]` |
| `ALL_EMF` | `["Person", "Entity", "EObject"]` |
| `SINGLE` | `["Person"]` (only direct parent) |
| `NONE` | (no supertype field) |

---

## 6. SuperType Configuration

### 6.1 Configuration Keys

| Annotation Key | Property Key | Default | Description |
|----------------|--------------|---------|-------------|
| `superTypeSerialize` | `codec.superTypeSerialize` | `false` | Enable supertype serialization |
| `superTypeStrategy` | `codec.superTypeStrategy` | `ALL` | Which supertypes to include |
| `superTypeKey` | `codec.superTypeKey` | `_supertype` | JSON property name |
| `superTypeAsArray` | `codec.superTypeAsArray` | `true` | Array (true) or string (false) |
| `superTypeSeparator` | `codec.superTypeSeparator` | `,` | Separator for string presentation |
| `superTypeFormat` | `codec.superTypeFormat` | (inherits from `typeFormat`) | Output format (PLAIN/STRUCTURED) |
| `superTypeSchemaKey` | `codec.superTypeSchemaKey` | `schema` | Schema key in STRUCTURED |
| `superTypeNameKey` | `codec.superTypeNameKey` | `type` | Name key in STRUCTURED |

> **Note:** SuperType configuration only applies at **Global** and **EClass** level. It is invalid on EReference or EAttribute (SuperType describes class inheritance, not how an object is accessed).

### 6.2 EAnnotation (on EClass)

```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="superTypeSerialize" value="true"/>
    <details key="superTypeStrategy" value="ALL"/>
    <details key="superTypeAsArray" value="true"/>
    <details key="superTypeSeparator" value=","/>
    <details key="superTypeKey" value="_supertype"/>
  </eAnnotations>
</eClassifiers>
```

### 6.3 Java Builder (Runtime Override)

**Enable with defaults (ALL strategy, array presentation):**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .superTypeSerialize(true)
    .build();
```

**With STRING presentation:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .superTypeSerialize(true)
    .superTypeAsArray(false)
    .superTypeSeparator("|")  // Custom separator
    .build();
```

**With SINGLE strategy:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .superTypeSerialize(true)
    .superTypeStrategy(SuperTypeStrategy.SINGLE)
    .build();
```

**Property Map:**
```java
Map<String, Object> options = new HashMap<>();
options.put("codec.superTypeSerialize", true);
options.put("codec.superTypeStrategy", "SINGLE");
options.put("codec.superTypeAsArray", false);
```

---

## 7. Examples by Configuration

### 7.1 Type PLAIN + SuperType ARRAY (most common)

```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": ["Entity", "http://audit.org/1.0#//Auditable"],
  "name": "John"
}
```

### 7.2 Type PLAIN + SuperType STRING

```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": "Entity,http://audit.org/1.0#//Auditable",
  "name": "John"
}
```

### 7.3 Type STRUCTURED + SuperType ARRAY

```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
  },
  "name": "John"
}
```

### 7.4 Type STRUCTURED + SuperType STRING

```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": "Entity,http://audit.org/1.0#//Auditable"
  },
  "name": "John"
}
```

### 7.5 SINGLE Selection

```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": ["Entity"],
  "name": "John"
}
```

Or with STRING presentation (single value, no separator needed):

```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": "Entity",
  "name": "John"
}
```

---

## 8. Deserialization

SuperType information is typically **not needed for deserialization** since the concrete type (`_type`) fully determines the EClass to instantiate. The inheritance hierarchy is already defined in the EMF model.

**Both PLAIN and STRUCTURED formats are supported for deserialization:**

| Format | SuperType Location | Handled By |
|--------|-------------------|------------|
| PLAIN | Standalone `_supertype` field | `SuperTypeDeserializationEntry` |
| STRUCTURED | Inside `_type` object as `supertype` field | `TypeDeserializationEntry` |

### 8.1 Default Behavior (No Validation)

By default (`validateSuperTypeHierarchy=false`), the deserializer:
1. Parses the supertype field (for both PLAIN and STRUCTURED formats)
2. Ignores the parsed values (no validation)
3. Resolves the EClass from the `_type` field only
4. Creates the EObject based on the resolved type

### 8.2 Validation Mode

When `validateSuperTypeHierarchy=true`, the deserializer:
1. Parses the supertype field (array or separator-joined string)
2. Resolves the EClass from the `_type` field
3. Validates that declared supertypes match the resolved EClass's actual supertypes
4. **Fails deserialization** if hierarchy doesn't match

**This validation applies to both formats:**
- **PLAIN format**: Validation occurs after the `_supertype` field is parsed
- **STRUCTURED format**: Validation occurs while parsing the `_type` object, after extracting the `supertype` field

**Validation Rules:**
- Each declared supertype must exist in the EClass's `getEAllSuperTypes()` hierarchy
- Supertypes can be declared as simple names (same namespace) or full URIs (different namespace)
- Order of supertypes is not significant for validation
- Missing supertypes in JSON (subset) is acceptable
- Extra supertypes in JSON (not in actual hierarchy) causes validation failure

### 8.3 Configuration

| Annotation Key | Property Key | Default | Description |
|----------------|--------------|---------|-------------|
| `superTypeValidate` | `codec.superTypeValidate` | `false` | Validate supertype hierarchy on deserialization |

**Java Builder:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .superTypeSerialize(true)
    .superTypeValidate(true)  // Enable strict validation
    .build();
```

### 8.4 Deserialization Examples

**PLAIN format with ARRAY presentation:**
```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": ["Entity", "http://audit.org/1.0#//Auditable"],
  "name": "John"
}
```

**PLAIN format with STRING presentation:**
```json
{
  "_type": "http://example.org/1.0#//Person",
  "_supertype": "Entity,http://audit.org/1.0#//Auditable",
  "name": "John"
}
```

**STRUCTURED format with ARRAY presentation:**
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
  },
  "name": "John"
}
```

**STRUCTURED format with STRING presentation:**
```json
{
  "_type": {
    "schema": "http://example.org/1.0",
    "type": "Person",
    "supertype": "Entity,http://audit.org/1.0#//Auditable"
  },
  "name": "John"
}
```

### 8.5 Error Handling

When validation fails:
- Throw `SuperTypeValidationException` with descriptive message
- Include expected supertypes (from model)
- Include declared supertypes (from JSON)
- Include resolved EClass name

**Example error:**
```
SuperType hierarchy validation failed for EClass 'Person':
  Declared supertypes: [Entity, InvalidType]
  Expected supertypes: [Entity, NamedElement]
  Invalid supertype(s): [InvalidType] not found in hierarchy
```

---

[Next: Discriminator Mapping →](08-discriminator-mapping.md)

---

> **See also:** [Annotation Reference](16-annotation-reference.md) (SuperType Configuration) for complete configuration keys and invalid configuration scenarios.
