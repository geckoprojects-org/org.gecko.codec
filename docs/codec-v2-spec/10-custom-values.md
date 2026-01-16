# Custom Value Readers/Writers

[← Back to Overview](00-overview.md) | [← Feature Serialization](09-feature.md)

---

Extensibility hooks for custom serialization logic.

## 1. Overview

Custom value readers/writers allow you to:
- Transform values during serialization/deserialization
- Handle special data types (dates, binary, custom formats)
- Implement domain-specific encoding
- Customize reference URI formats
- Convert embedded formats (e.g., JSON Schema to EPackage)

### 1.1 Interface Hierarchy

The system provides a **type-safe interface hierarchy**:

```
CodecValueReader<T, F extends EStructuralFeature>
├── AttributeValueReader<T>      extends CodecValueReader<T, EAttribute>
└── ReferenceValueReader<T>      extends CodecValueReader<T, EReference>
                                 where T extends EObject

CodecValueWriter<T, F extends EStructuralFeature>
├── AttributeValueWriter<T>      extends CodecValueWriter<T, EAttribute>
└── ReferenceValueWriter<T>      extends CodecValueWriter<T, EReference>
                                 where T extends EObject
```

### 1.2 Use Cases by Interface

| Interface | Use Case | Example |
|-----------|----------|---------|
| `AttributeValueReader<T>` | Transform primitive/data type values | ISO 8601 date parsing |
| `AttributeValueWriter<T>` | Format primitive/data type values | Base64 encoding |
| `ReferenceValueReader<T>` | Read containment references in custom format | JSON Schema → EPackage |
| `ReferenceValueWriter<T>` | Write containment references in custom format | EPackage → JSON Schema |
| `CodecValueReader<String, EReference>` | Transform non-containment reference URIs | MongoDB ObjectId → EMF URI |
| `CodecValueWriter<EObject, EReference>` | Write non-containment reference URIs | Custom URI scheme |

### 1.3 Core Components

| Component | Purpose |
|-----------|---------|
| `CodecValueWriter<T, F>` | Base interface for custom serialization |
| `CodecValueReader<T, F>` | Base interface for custom deserialization |
| `AttributeValueWriter<T>` | Specialized for EAttribute with `canHandle()` |
| `AttributeValueReader<T>` | Specialized for EAttribute with `canHandle()` |
| `ReferenceValueWriter<T>` | Specialized for containment EReference with `canHandle()` |
| `ReferenceValueReader<T>` | Specialized for containment EReference with `canHandle()` |
| `CodecValueRegistry` | Registry to store named readers/writers |

---

## 2. Interface Architecture

### 2.1 Base Interfaces

```java
@FunctionalInterface
public interface CodecValueReader<T, F extends EStructuralFeature> {
    T read(JsonParser parser, F feature, DeserializationContext ctxt)
        throws IOException;
}

@FunctionalInterface
public interface CodecValueWriter<T, F extends EStructuralFeature> {
    void write(T value, F feature, JsonGenerator gen, SerializationContext ctxt)
        throws IOException;
}
```

### 2.2 Specialized Attribute Interfaces

```java
public interface AttributeValueReader<T> extends CodecValueReader<T, EAttribute> {
    /**
     * Checks if this reader can handle the given attribute.
     */
    boolean canHandle(EAttribute attribute);
}

public interface AttributeValueWriter<T> extends CodecValueWriter<T, EAttribute> {
    /**
     * Checks if this writer can handle the given attribute.
     */
    boolean canHandle(EAttribute attribute);
}
```

### 2.3 Specialized Reference Interfaces

```java
public interface ReferenceValueReader<T extends EObject>
        extends CodecValueReader<T, EReference> {
    /**
     * Checks if this reader can handle the given reference.
     * Typically checks if the reference type is compatible.
     */
    boolean canHandle(EReference reference);
}

public interface ReferenceValueWriter<T extends EObject>
        extends CodecValueWriter<T, EReference> {
    /**
     * Checks if this writer can handle the given reference.
     * Typically checks if the reference type is compatible.
     */
    boolean canHandle(EReference reference);
}
```

### 2.4 The `canHandle()` Method

The `canHandle()` method enables **type-safe validation** at construction time:

```java
public class EPackageValueReader implements ReferenceValueReader<EPackage> {
    @Override
    public boolean canHandle(EReference reference) {
        // Only handle references of type EPackage
        return EcorePackage.Literals.EPACKAGE.isSuperTypeOf(
            reference.getEReferenceType());
    }

    @Override
    public EPackage read(JsonParser parser, EReference ref, DeserializationContext ctxt) {
        // Convert JSON Schema to EPackage
        return converter.convert(parser.readValueAsTree());
    }
}
```

Benefits:
- **Early validation**: Incompatible readers/writers are rejected at construction
- **Clear error messages**: Log warnings when reader/writer cannot handle a feature
- **Type safety**: Prevents runtime ClassCastException

---

## 3. Attribute Value Readers/Writers

### 3.1 Purpose

Transform attribute values during serialization/deserialization:
- Custom date/time formats
- Binary encoding (Base64, hex)
- Domain-specific value transformations

### 3.2 Example: Date Formatting

```java
public class ISODateReader implements AttributeValueReader<Date> {
    private final SimpleDateFormat sdf;

    public ISODateReader() {
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public boolean canHandle(EAttribute attribute) {
        return attribute.getEAttributeType().getInstanceClass() == Date.class;
    }

    @Override
    public Date read(JsonParser parser, EAttribute attr, DeserializationContext ctxt)
            throws IOException {
        try {
            return sdf.parse(parser.getString());
        } catch (ParseException e) {
            throw new IOException("Invalid date format", e);
        }
    }
}

public class ISODateWriter implements AttributeValueWriter<Date> {
    private final SimpleDateFormat sdf;

    public ISODateWriter() {
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public boolean canHandle(EAttribute attribute) {
        return attribute.getEAttributeType().getInstanceClass() == Date.class;
    }

    @Override
    public void write(Date date, EAttribute attr, JsonGenerator gen,
            SerializationContext ctxt) throws IOException {
        gen.writeString(sdf.format(date));
    }
}
```

### 3.3 Example: Base64 Binary (Lambda Style)

For simple cases, lambdas can still be used with the base interfaces:

```java
// Writer - encodes byte[] to Base64
CodecValueWriter<byte[], EAttribute> base64Writer = (data, attr, gen, ctxt) ->
    gen.writeString(Base64.getEncoder().encodeToString(data));

// Reader - decodes Base64 to byte[]
CodecValueReader<byte[], EAttribute> base64Reader = (parser, attr, ctxt) ->
    Base64.getDecoder().decode(parser.getString());
```

### 3.4 Serialization Flow

```
AttributeSerializationEntry
    │
    ├─ Lookup customWriter from registry
    │
    ├─ If writer instanceof AttributeValueWriter:
    │       │
    │       ├─ Check canHandle(attribute)
    │       │       │
    │       │       ├─ YES: Use this writer
    │       │       │
    │       │       └─ NO: Log warning, fall back to default
    │
    ├─ If customWriter available:
    │       customWriter.write(value, attribute, gen, ctxt)
    │
    └─ Otherwise: Default serialization
```

---

## 4. Reference Value Readers/Writers

### 4.1 Two Types of Reference Customization

| Type | Interface | Purpose | When Used |
|------|-----------|---------|-----------|
| **Containment** | `ReferenceValueReader<T>` / `ReferenceValueWriter<T>` | Convert entire object structure | JSON Schema ↔ EPackage |
| **Non-Containment URI** | `CodecValueReader<String, EReference>` / `CodecValueWriter<EObject, EReference>` | Transform reference URI | MongoDB ObjectId ↔ EMF URI |

### 4.2 Containment Reference Readers/Writers

Used for **containment references** where embedded content needs custom conversion.

#### Example: JSON Schema to EPackage (OpenAPI)

```java
public class EPackageValueReader implements ReferenceValueReader<EPackage> {
    private final JsonSchemaToEPackageConverter converter;

    @Override
    public boolean canHandle(EReference reference) {
        return EcorePackage.Literals.EPACKAGE.isSuperTypeOf(
            reference.getEReferenceType());
    }

    @Override
    public EPackage read(JsonParser parser, EReference ref, DeserializationContext ctxt)
            throws IOException {
        TreeNode tree = parser.readValueAsTree();
        return converter.convert((JsonNode) tree, null);
    }
}

public class EPackageValueWriter implements ReferenceValueWriter<EPackage> {
    @Override
    public boolean canHandle(EReference reference) {
        return EcorePackage.Literals.EPACKAGE.isSuperTypeOf(
            reference.getEReferenceType());
    }

    @Override
    public void write(EPackage value, EReference ref, JsonGenerator gen,
            SerializationContext ctxt) throws IOException {
        JsonNode schema = converter.convert(value);
        gen.writePOJO(schema);
    }
}
```

#### Serialization Flow (Containment)

```
ReferenceSerializationEntry (containment)
    │
    ├─ Lookup customWriter from registry
    │
    ├─ If writer instanceof ReferenceValueWriter:
    │       │
    │       ├─ Check canHandle(reference)
    │       │       │
    │       │       ├─ YES: containmentWriter.write(target, ref, gen, ctxt)
    │       │       │
    │       │       └─ NO: Log warning, use default serialization
    │
    └─ Default: ctxt.writeValue(gen, target)
```

### 4.3 Non-Containment Reference URI Customization

Used for **non-containment references** to transform URI format.

#### Example: MongoDB ObjectId

```java
// Writer - extracts ObjectId from target
CodecValueWriter<EObject, EReference> mongoIdWriter = (target, ref, gen, ctxt) -> {
    Object id = target.eGet(target.eClass().getEStructuralFeature("_id"));
    gen.writeString(id != null ? id.toString() :
        target.eResource().getURIFragment(target));
};

// Reader - transforms ObjectId to EMF URI
CodecValueReader<String, EReference> mongoIdReader = (parser, ref, ctxt) -> {
    String objectId = parser.getString();
    return "#/persons/" + objectId;  // Transform to EMF URI
};
```

#### Serialization Flow (Non-Containment)

```
ReferenceSerializationEntry (non-containment)
    │
    ├─ writeReferenceObject()
    │       │
    │       ├─ Write _type
    │       │
    │       ├─ If uriWriter configured:
    │       │       uriWriter.write(target, reference, gen, ctxt)
    │       │
    │       └─ Default: gen.writeString(getReferenceUri())
```

---

## 5. Registration

### 5.1 CodecValueRegistry

Writers and readers are registered by name:

```java
CodecValueRegistry registry = new CodecValueRegistry();

// Attribute handlers
registry.registerWriter("isoDate", new ISODateWriter());
registry.registerReader("isoDate", new ISODateReader());

// Containment reference handlers (ReferenceValueReader/Writer)
registry.registerReader("schemas", new EPackageValueReader());
registry.registerWriter("schemas", new EPackageValueWriter("schemas", true));

// Non-containment URI handlers (generic CodecValueReader/Writer)
registry.registerWriter("mongoId", mongoIdWriter);
registry.registerReader("mongoId", mongoIdReader);
```

### 5.2 Type Resolution at Construction Time

When a reader/writer is resolved from the registry:

```java
// In ReferenceDeserializationEntry constructor:
CodecValueReader<?, ?> reader = registry.getReader(readerName).orElse(null);

if (reader instanceof ReferenceValueReader<?> refReader) {
    // For containment references
    if (refReader.canHandle(reference)) {
        this.containmentReader = refReader;
    } else {
        LOGGER.warning("Reader cannot handle reference: " + reference.getName());
    }
} else if (reader != null) {
    // For non-containment URI transformation
    this.uriReader = (CodecValueReader<String, EReference>) reader;
}
```

### 5.3 Passing Registry to CodecResource

```java
CodecResource resource = new CodecResource(
    uri,
    metadataService,
    configuration,
    registry,      // CodecValueRegistry
    mapperBuilder
);
```

---

## 6. Activation per Feature

### 6.1 EAnnotation on EAttribute

```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="createdAt" eType="...">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="valueWriterName" value="isoDate"/>
    <details key="valueReaderName" value="isoDate"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 6.2 EAnnotation on Containment EReference

```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="schemas"
                     eType="ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//EPackage"
                     containment="true">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="valueWriterName" value="schemas"/>
    <details key="valueReaderName" value="schemas"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 6.3 EAnnotation on Non-Containment EReference

```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="manager" eType="#//Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="valueWriterName" value="mongoId"/>
    <details key="valueReaderName" value="mongoId"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 6.4 Runtime Override via Load/Save Options

```java
Map<String, Object> options = CodecOptionsBuilder.create()
    .forFeature(MyPackage.Literals.PERSON__CREATED_AT)
    .valueWriterName("isoDate")
    .valueReaderName("isoDate")
    .build();

resource.save(outputStream, options);
```

---

## 7. Null Handling

### 7.1 Attributes

Custom readers/writers are **not called for null values**:
- Null attribute values are serialized as JSON `null`
- JSON `null` is deserialized as `null` without calling reader

### 7.2 References

Custom readers/writers are **not called for null references**:
- Null references are serialized according to `serializeNull` config
- JSON `null` sets the reference to `null` without calling reader

---

## 8. Error Handling

### 8.1 Writer Errors

If a custom writer throws an `IOException`, it is wrapped:

```java
throw new UncheckedIOException(
    "Custom value writer failed for " + feature.getName(), e);
```

### 8.2 Reader Errors

If a custom reader throws an `IOException`, it is wrapped:

```java
throw new UncheckedIOException(
    "Custom value reader failed for " + feature.getName(), e);
```

### 8.3 `canHandle()` Returns False

If a specialized reader/writer's `canHandle()` returns false:
- A warning is logged
- Fall back to default serialization/deserialization
- No error is thrown

### 8.4 Missing Writer/Reader

If a configured name is not found in the registry:
- Fall back to default serialization/deserialization
- No error is thrown (silent fallback)

---

## 9. Registry API

### 9.1 Registration

```java
CodecValueRegistry registerWriter(String name, CodecValueWriter<?, ?> writer);
CodecValueRegistry registerReader(String name, CodecValueReader<?, ?> reader);
```

### 9.2 Lookup

```java
Optional<CodecValueWriter<?, ?>> getWriter(String name);
Optional<CodecValueReader<?, ?>> getReader(String name);
```

### 9.3 Introspection

```java
boolean hasWriter(String name);
boolean hasReader(String name);
Map<String, CodecValueWriter<?, ?>> getWriters();
Map<String, CodecValueReader<?, ?>> getReaders();
```

---

## 10. Configuration Hierarchy

Custom value reader/writer names follow the standard configuration hierarchy:

1. **Load/Save Options** (highest priority)
2. **ResourceFactory Defaults**
3. **CodecConfiguration**
4. **EAnnotation on EStructuralFeature**
5. **Built-in Defaults** (no custom reader/writer)

---

## 11. Implementation Classes

| Class | Purpose |
|-------|---------|
| `CodecValueReader<T, F>` | Base interface for custom deserialization |
| `CodecValueWriter<T, F>` | Base interface for custom serialization |
| `AttributeValueReader<T>` | Specialized for EAttribute with `canHandle()` |
| `AttributeValueWriter<T>` | Specialized for EAttribute with `canHandle()` |
| `ReferenceValueReader<T>` | Specialized for containment EReference with `canHandle()` |
| `ReferenceValueWriter<T>` | Specialized for containment EReference with `canHandle()` |
| `CodecValueRegistry` | Named reader/writer storage |
| `AttributeSerializationEntry` | Invokes attribute writers |
| `AttributeDeserializationEntry` | Invokes attribute readers |
| `ReferenceSerializationEntry` | Invokes reference writers (both types) |
| `ReferenceDeserializationEntry` | Invokes reference readers (both types) |

---

## 12. Real-World Example: OpenAPI with JSON Schema

OpenAPI documents embed JSON Schema in `components/schemas`. The codec handles this
by converting between `EPackage` and JSON Schema using custom `ReferenceValueReader/Writer`.

### 12.1 Quick Example

```xml
<!-- In openapi.ecore -->
<eStructuralFeatures xsi:type="ecore:EReference" name="schemas"
                     eType="ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//EPackage"
                     containment="true">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="valueReaderName" value="schemas"/>
    <details key="valueWriterName" value="schemas"/>
  </eAnnotations>
</eStructuralFeatures>
```

```java
// Register converters
registry.registerReader("schemas", new EPackageValueReader());
registry.registerWriter("schemas", new EPackageValueWriter("schemas", true));
```

```json
{
  "openapi": "3.0.3",
  "components": {
    "schemas": {
      "Person": {
        "type": "object",
        "properties": {
          "name": { "type": "string" },
          "age": { "type": "integer" }
        }
      }
    }
  }
}
```

The `schemas` object is automatically converted to an `EPackage` with an `EClass` named "Person".

**For complete OpenAPI support documentation, see [Chapter 17: OpenAPI Support](17-openapi-support.md).**

---

[Next: Polymorphism and Inheritance →](11-polymorphism.md)
