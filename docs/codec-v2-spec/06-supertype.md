# SuperType Serialization

[← Back to Overview](00-overview.md) | [← Type Serialization](05-type.md)

> **See also:** [Key Configuration](02-key-configuration.md) for the complete key naming conventions.

---

SuperType serialization has two orthogonal dimensions:
- **Selection** (ALL, SINGLE, NONE) - which supertypes to include
- **Format** (PLAIN, STRUCTURED) - per [Serialization Strategies](01-strategies.md), how to represent each entry

## 1. SuperType Format

### 1.1 PLAIN Format

Array of plain type identifiers (strings):

```json
{
  "_supertype": ["Entity", "Auditable", "Timestamped"]
}
```

Or as URIs:
```json
{
  "_supertype": [
    "http://example.org/base/1.0#//Entity",
    "http://example.org/audit/1.0#//Auditable"
  ]
}
```

### 1.2 STRUCTURED Format

Array of structured type objects:

```json
{
  "_supertype": [
    { "schema": "http://example.org/base/1.0", "type": "Entity" },
    { "schema": "http://example.org/audit/1.0", "type": "Auditable" }
  ]
}
```

---

## 2. Mixed Format (Smart Compression)

Similar to type info, supertypes use a **smart format**:
- Supertypes from **same schema** as the object: plain name only
- Supertypes from **different schema**: full URI

```json
{
  "_supertype": ["Entity", "NamedElement", "http://audit.org/1.0#//Auditable"]
}
```

See [Global Options - Smart Compression](02-global-options.md#1-smart-compression) for details.

---

## 3. SuperType Configuration

The configuration defines **keys and format**, not actual values.

### 3.1 EAnnotation (on EClass)

```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.supertype"/>
    <details key="enabled" value="true"/>
    <details key="selection" value="ALL"/>
    <details key="supertypeKey" value="_supertype"/>
  </eAnnotations>
</eClassifiers>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `enabled` | true, false | false | Enable supertype serialization |
| `selection` | ALL, ALL_EMF, SINGLE, NONE | ALL (when enabled) | Which supertypes to include |
| `superTypeKey` | any string | `_supertype` (PLAIN) / `supertype` (STRUCTURED in type container) | JSON property name |
| `format` | PLAIN, STRUCTURED | PLAIN | SerializationFormat |
| `schemaKey` | any string | `schema` | Key for schema in STRUCTURED |
| `typeKey` | any string | `type` | Key for type name in STRUCTURED |

**Selection Values:**
| Value | Description |
|-------|-------------|
| `ALL` | All domain model supertypes (excludes EMF base classes like EObject) |
| `ALL_EMF` | All supertypes including EMF base classes (EObject, EModelElement, etc.) |
| `SINGLE` | Only the immediate/direct supertype |
| `NONE` | No supertypes (equivalent to enabled=false) |

### 3.2 Java Builder (Runtime Override)

**Minimal (all defaults: PLAIN format, ALL selection):**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .build();
```
**Resulting JSON:**
```json
{
  "_supertype": ["Entity", "Auditable"]
}
```

**STRUCTURED format:**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .structured()
    .build();
```
**Resulting JSON:**
```json
{
  "_supertype": [
    { "schema": "http://example.org/base/1.0", "type": "Entity" },
    { "schema": "http://example.org/audit/1.0", "type": "Auditable" }
  ]
}
```

**With custom keys:**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .structured()
    .schemaKey("ns")
    .typeKey("class")
    .build();
```
**Resulting JSON:**
```json
{
  "_supertype": [
    { "ns": "http://example.org/base/1.0", "class": "Entity" },
    { "ns": "http://example.org/audit/1.0", "class": "Auditable" }
  ]
}
```

**Smart compression (same-schema supertypes as plain names):**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .useSmartCompression(true)
    .build();
```
**Resulting JSON:** (Entity from same schema, Auditable from different)
```json
{
  "_supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
}
```

**At serialization time:**
1. Serializer reads `SuperTypeSerializationConfig` from options (Java or JSON)
2. Extracts supertypes from EObject's EClass
3. Applies smart compression (same schema → name only)
4. Combines config (keys/format) + supertype data → JSON output

---

## 4. Default SuperType Settings

| Setting | PLAIN Default | STRUCTURED Default |
|---------|---------------|-------------------|
| Enabled | `false` | `false` |
| Selection | `ALL` (when enabled) | `ALL` (when enabled) |
| SuperType Key | `_supertype` | `supertype` (inside type container) |
| Schema Key | - | `schema` |
| Type Key | - | `type` |
| Format | `PLAIN` | `STRUCTURED` |

**When enabled, default output:**
```json
{
  "_supertype": ["Entity", "Auditable"]
}
```

---

[Next: ID Serialization →](07-id.md)
