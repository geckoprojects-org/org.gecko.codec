# SuperType Serialization

[← Back to Overview](00-overview.md) | [← Type Serialization](05-type.md)

> **See also:** [Key Configuration](02-key-configuration.md) for the complete key naming conventions.

---

SuperType serialization has two orthogonal dimensions:
- **Selection** (ALL, ALL_EMF, SINGLE, NONE) - which supertypes to include
- **Presentation** (ARRAY, STRING) - how to present multiple values

**Important:** SuperType format (PLAIN or STRUCTURED) follows the Type format configuration. There is no independent format setting for SuperType.

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

## 5. Selection Modes

| Value | Description |
|-------|-------------|
| `ALL` | All domain model supertypes (excludes EMF base classes like EObject, EModelElement) |
| `ALL_EMF` | All supertypes including EMF base classes |
| `SINGLE` | Only the immediate/direct supertype |
| `NONE` | No supertypes (equivalent to `enabled=false`) |

---

## 6. SuperType Configuration

### 6.1 Configuration Options

| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `enabled` | true, false | false | Enable supertype serialization |
| `selection` | ALL, ALL_EMF, SINGLE, NONE | ALL (when enabled) | Which supertypes to include |
| `asArray` | true, false | true | Array (true) or separator-joined string (false) |
| `separator` | any string | `,` | Separator for STRING presentation (when asArray=false) |
| `superTypeKey` | any string | `_supertype` (PLAIN) / `supertype` (STRUCTURED) | JSON property name |
| `useSmartCompression` | true, false | false | Use simple names for same-namespace supertypes |

**Notes:**
- Format (PLAIN/STRUCTURED) is inherited from Type configuration

### 6.2 EAnnotation (on EClass)

```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.supertype"/>
    <details key="enabled" value="true"/>
    <details key="selection" value="ALL"/>
    <details key="asArray" value="true"/>
    <details key="separator" value=","/>
    <details key="superTypeKey" value="_supertype"/>
    <details key="useSmartCompression" value="true"/>
  </eAnnotations>
</eClassifiers>
```

### 6.3 Java Builder (Runtime Override)

**Minimal (defaults: asArray=true, ALL selection):**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .build();
```

**With STRING presentation:**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .asArray(false)
    .separator("|")  // Custom separator
    .build();
```

**With SINGLE selection:**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .selection(Selection.SINGLE)
    .build();
```

**With smart compression:**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .useSmartCompression(true)
    .build();
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

However, if present in the JSON, the deserializer should:
1. Parse the supertype field (array or comma-separated string)
2. Optionally validate that declared supertypes match the resolved EClass's actual supertypes
3. Log a warning if there's a mismatch (but continue with the resolved type)

---

[Next: ID Serialization →](07-id.md)
