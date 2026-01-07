# Polymorphism and Inheritance

[← Back to Overview](00-overview.md) | [← Custom Value Readers/Writers](09-custom-values.md)

---

## 1. Reference Type vs Instance Type

When serializing references, the serializer decides which type information to write based on the actual instance type and smart compression setting.

**Core Rule:** The serializer always uses the **concrete instance type** (from `eObject.eClass()`), never the declared reference type.

**Smart Compression Behavior:**

| Smart Compression | Instance Type == Reference Type | Action |
|-------------------|--------------------------------|--------|
| ON | Yes | Omit `_type` (can be inferred) |
| ON | No | Write instance type |
| OFF | Yes | Write instance type |
| OFF | No | Write instance type |

**Examples:**

```java
// Reference typed as Person, contains FancyPerson instance
EReference employeeRef;  // type = Person
EObject instance;        // eClass = FancyPerson
```

**Smart Compression ON, instance type differs from reference type:**
```json
{
  "employee": {
    "_type": "http://example.org/person/1.0#//FancyPerson",
    "_ref": "john-doe"
  }
}
```

**Smart Compression ON, instance type equals reference type:**
```json
{
  "employee": {
    "_ref": "john-doe"
  }
}
```
(Type omitted because it can be inferred from the reference declaration)

**Smart Compression OFF (always writes instance type):**
```json
{
  "employee": {
    "_type": "http://example.org/person/1.0#//FancyPerson",
    "_ref": "john-doe"
  }
}
```

### 1.1 Option: Always Write Reference Type (Serialization-Only)

An optional setting to write the **declared reference type** instead of the instance type:

```java
ReferenceSerializationConfig.builder()
    .writeReferenceType(true)  // Write declared type, not instance type
    .build();
```

**Result (reference typed as Person, instance is FancyPerson):**
```json
{
  "employee": {
    "_type": "http://example.org/person/1.0#//Person",
    "_ref": "john-doe"
  }
}
```

> **⚠️ Warning:** This is a **serialization-only option**. Using `writeReferenceType=true` may cause deserialization failures if:
> - The reference type is an interface (cannot instantiate)
> - The reference type is abstract (cannot instantiate)
> - Instance-specific features from subclass are lost
>
> Use only when the consuming system specifically requires the declared type.

---

## 2. Annotation Inheritance Levels

Controls how codec annotations are inherited across the EClass hierarchy.

| Level | Description | Inherits From |
|-------|-------------|---------------|
| `DIRECT` | Only direct parent | Immediate superclass/interface |
| `ALL` | Full hierarchy | All ancestors up to EObject |
| `NONE` | No inheritance | Only concrete class annotations |

**Default:** `DIRECT`

**Rationale for DIRECT as default:**
- Avoids accidentally inheriting annotations from external library base classes
- Predictable behavior - only looks one level up
- Matches OSGi DS component annotation inheritance semantics

### 2.1 EAnnotation (on EClass)

```xml
<!-- Force inheritance from parent even if parent is from different EPackage -->
<eClassifiers xsi:type="ecore:EClass" name="Employee" eSuperTypes="#//Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.inherit" value="ALL"/>
  </eAnnotations>
</eClassifiers>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `codec.inherit` | DIRECT, ALL, NONE | DIRECT | Annotation inheritance level |

### 2.2 Java Builder (Runtime Override)

```java
CodecConfig config = CodecConfig.builder()
    .annotationInheritance(AnnotationInheritance.DIRECT)  // Default
    .build();

// Or per-class override
ClassCodecConfig config = ClassCodecConfig.builder()
    .eClass(EmployeeClass)
    .annotationInheritance(AnnotationInheritance.ALL)
    .build();
```

**Example Hierarchy:**
```
EObject (EMF base)
  └── Entity (library class, has @CODEC_ID)
        └── Person (your model, has @CODEC_TYPE)
              └── Employee (your model, no annotations)
```

| Inheritance Level | Employee sees annotations from |
|-------------------|--------------------------------|
| `NONE` | Employee only (none) |
| `DIRECT` | Employee + Person (`@CODEC_TYPE`) |
| `ALL` | Employee + Person + Entity (`@CODEC_TYPE`, `@CODEC_ID`) |

---

## 3. Inheritance Resolution Order

When the same annotation exists at multiple levels, the most specific (closest to concrete class) wins:

1. Concrete class annotations (highest priority)
2. Direct parent annotations
3. Grandparent annotations (only if `ALL`)
4. Global codec defaults (lowest priority)

---

[Next: Configuration Scenarios →](11-scenarios.md)
