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

The system provides a **unified interface** that works for both:
- **Attributes** (`EAttribute`) - Transform primitive/data type values
- **References** (`EReference`) - Customize reference URI format and resolution

### 1.1 Core Components

| Component | Purpose |
|-----------|---------|
| `CodecValueWriter<T, F>` | Functional interface for custom serialization |
| `CodecValueReader<T, F>` | Functional interface for custom deserialization |
| `CodecValueRegistry` | Registry to store named readers/writers |

---

## 2. Unified Interface Architecture

### 2.1 Generic Type Parameters

Both interfaces use two type parameters:

```java
CodecValueReader<T, F extends EStructuralFeature>
CodecValueWriter<T, F extends EStructuralFeature>
```

| Parameter | Meaning |
|-----------|---------|
| `T` | The value type being read/written |
| `F` | The feature type (`EAttribute` or `EReference`) |

### 2.2 Usage by Feature Type

| Feature Type | T (Value) | F (Feature) | Use Case |
|--------------|-----------|-------------|----------|
| Attribute | `Date`, `byte[]`, etc. | `EAttribute` | Format dates, encode binary |
| Reference | `String` (URI) | `EReference` | Custom ID schemes, URI formats |

---

## 3. Interfaces

### 3.1 CodecValueWriter

```java
@FunctionalInterface
public interface CodecValueWriter<T, F extends EStructuralFeature> {
    /**
     * Writes a value to the JSON generator.
     *
     * @param value the value to write (never null)
     * @param feature the feature being serialized
     * @param gen the JSON generator
     * @param ctxt the serialization context (may be null)
     * @throws IOException if writing fails
     */
    void write(T value, F feature, JsonGenerator gen, SerializationContext ctxt)
        throws IOException;
}
```

### 3.2 CodecValueReader

```java
@FunctionalInterface
public interface CodecValueReader<T, F extends EStructuralFeature> {
    /**
     * Reads a value from the JSON parser.
     * The parser is positioned at the value token (not null).
     *
     * @param parser the JSON parser
     * @param feature the feature being deserialized
     * @param ctxt the deserialization context (may be null)
     * @return the parsed value
     * @throws IOException if reading fails
     */
    T read(JsonParser parser, F feature, DeserializationContext ctxt)
        throws IOException;
}
```

### 3.3 Why Feature and Context Parameters?

The `feature` parameter provides:
- Access to feature metadata (name, type, annotations)
- Ability to implement feature-specific logic in a single reader/writer

The `ctxt` parameter provides:
- Access to the serialization/deserialization context
- Smart compression context (root schema URI)
- Resource information for cross-document references

---

## 4. Attribute Value Readers/Writers

### 4.1 Purpose

Transform attribute values during serialization/deserialization:
- Custom date/time formats
- Binary encoding (Base64, hex)
- Domain-specific value transformations

### 4.2 Example: Date Formatting

```java
// Writer - converts Date to ISO 8601 string
CodecValueWriter<Date, EAttribute> isoDateWriter = (date, attr, gen, ctxt) -> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    gen.writeString(sdf.format(date));
};

// Reader - parses ISO 8601 string to Date
CodecValueReader<Date, EAttribute> isoDateReader = (parser, attr, ctxt) -> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.parse(parser.getString());
};
```

### 4.3 Example: Base64 Binary

```java
// Writer - encodes byte[] to Base64
CodecValueWriter<byte[], EAttribute> base64Writer = (data, attr, gen, ctxt) ->
    gen.writeString(Base64.getEncoder().encodeToString(data));

// Reader - decodes Base64 to byte[]
CodecValueReader<byte[], EAttribute> base64Reader = (parser, attr, ctxt) ->
    Base64.getDecoder().decode(parser.getString());
```

### 4.4 Serialization Flow

```
AttributeSerializationEntry
    │
    ├─ Check if customWriter configured
    │
    ├─ YES: customWriter.write(value, attribute, gen, ctxt)
    │
    └─ NO: Default serialization (gen.writeString/writeNumber/etc.)
```

### 4.5 Deserialization Flow

```
AttributeDeserializationEntry
    │
    ├─ Check if customReader configured
    │
    ├─ YES: customReader.read(parser, attribute, ctxt)
    │
    └─ NO: Default deserialization (parser.getString/getIntValue/etc.)
```

---

## 5. Reference Value Readers/Writers

### 5.1 Purpose

Customize how non-containment reference URIs are written and read:
- Custom ID schemes (MongoDB ObjectId, UUID formats)
- Alternative URI formats
- Domain-specific reference encoding

### 5.2 When Applied

Reference value writers/readers are used for **non-containment references only**:
- The `_ref` field value (or custom ref key)
- NOT for containment references (serialized inline)

### 5.3 Example: MongoDB ObjectId

```java
// Writer - extracts MongoDB ObjectId from target object
CodecValueWriter<EObject, EReference> mongoIdWriter = (target, ref, gen, ctxt) -> {
    // Assumes target has an "_id" attribute with ObjectId
    Object id = target.eGet(target.eClass().getEStructuralFeature("_id"));
    if (id != null) {
        gen.writeString(id.toString());
    } else {
        // Fall back to fragment URI
        gen.writeString(target.eResource().getURIFragment(target));
    }
};

// Reader - returns the ObjectId string for later resolution
CodecValueReader<String, EReference> mongoIdReader = (parser, ref, ctxt) -> {
    return parser.getString(); // ObjectId string to be resolved later
};
```

### 5.4 Example: Custom URI Scheme

```java
// Writer - uses custom "urn:myapp:" scheme
CodecValueWriter<EObject, EReference> customUriWriter = (target, ref, gen, ctxt) -> {
    String id = extractId(target);
    String typeName = target.eClass().getName();
    gen.writeString("urn:myapp:" + typeName + "/" + id);
};

// Reader - parses custom URI
CodecValueReader<String, EReference> customUriReader = (parser, ref, ctxt) -> {
    String uri = parser.getString();
    // The URI is returned as-is; resolution happens later via proxy
    return uri;
};
```

### 5.5 Serialization Flow

```
ReferenceSerializationEntry (non-containment)
    │
    ├─ writeReferenceObject()
    │       │
    │       ├─ Write _type (with smart compression)
    │       │
    │       ├─ Check if customWriter configured
    │       │       │
    │       │       ├─ YES: customWriter.write(target, reference, gen, ctxt)
    │       │       │
    │       │       └─ NO: Default URI (EcoreUtil.getURI or fragment)
    │       │
    │       └─ Write as { "_type": "...", "_ref": "..." }
```

### 5.6 Deserialization Flow

```
ReferenceDeserializationEntry (non-containment with _ref)
    │
    ├─ Read _ref field
    │       │
    │       ├─ Check if customReader configured
    │       │       │
    │       │       ├─ YES: refUri = customReader.read(parser, reference, ctxt)
    │       │       │
    │       │       └─ NO: refUri = parser.getString()
    │       │
    │       └─ Create UnresolvedReference with refUri
    │
    └─ Proxy resolution happens later
```

---

## 6. Registration

### 6.1 CodecValueRegistry

Writers and readers are registered by name:

```java
CodecValueRegistry registry = new CodecValueRegistry();

// Attribute writers
registry.registerWriter("isoDate", isoDateWriter);
registry.registerWriter("base64Binary", base64Writer);

// Attribute readers
registry.registerReader("isoDate", isoDateReader);
registry.registerReader("base64Binary", base64Reader);

// Reference writers (same registry)
registry.registerWriter("mongoId", mongoIdWriter);

// Reference readers
registry.registerReader("mongoId", mongoIdReader);
```

### 6.2 Passing Registry to CodecResource

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

## 7. Activation per Feature

### 7.1 EAnnotation on EAttribute

```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="createdAt" eType="...">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.feature.valueWriterName" value="isoDate"/>
    <details key="codec.feature.valueReaderName" value="isoDate"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 7.2 EAnnotation on EReference

```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="manager" eType="#//Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.feature.valueWriterName" value="mongoId"/>
    <details key="codec.feature.valueReaderName" value="mongoId"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 7.3 Runtime Override via Load/Save Options

```java
Map<String, Object> options = CodecOptionsBuilder.create()
    .forFeature(MyPackage.Literals.PERSON__CREATED_AT)
    .valueWriterName("isoDate")
    .valueReaderName("isoDate")
    .build();

resource.save(outputStream, options);
```

---

## 8. Null Handling

### 8.1 Attributes

Custom readers/writers are **not called for null values**:
- Null attribute values are serialized as JSON `null`
- JSON `null` is deserialized as `null` without calling reader

### 8.2 References

Custom readers/writers are **not called for null references**:
- Null references are serialized according to `serializeNull` config
- JSON `null` sets the reference to `null` without calling reader

---

## 9. Error Handling

### 9.1 Writer Errors

If a custom writer throws an `IOException`, it is wrapped:

```java
throw new UncheckedIOException(
    "Custom value writer failed for " + feature.getName(), e);
```

### 9.2 Reader Errors

If a custom reader throws an `IOException`, it is wrapped:

```java
throw new UncheckedIOException(
    "Custom value reader failed for " + feature.getName(), e);
```

### 9.3 Missing Writer/Reader

If a configured name is not found in the registry:
- Fall back to default serialization/deserialization
- No error is thrown (silent fallback)

---

## 10. Registry API

### 10.1 Registration

```java
CodecValueRegistry registerWriter(String name, CodecValueWriter<?, ?> writer);
CodecValueRegistry registerReader(String name, CodecValueReader<?, ?> reader);
```

### 10.2 Lookup

```java
Optional<CodecValueWriter<?, ?>> getWriter(String name);
Optional<CodecValueReader<?, ?>> getReader(String name);

// Type-safe lookup
<T, F extends EStructuralFeature> Optional<CodecValueWriter<T, F>>
    getWriter(String name, Class<T> valueType, Class<F> featureType);

<T, F extends EStructuralFeature> Optional<CodecValueReader<T, F>>
    getReader(String name, Class<T> valueType, Class<F> featureType);
```

### 10.3 Introspection

```java
boolean hasWriter(String name);
boolean hasReader(String name);
Map<String, CodecValueWriter<?, ?>> getWriters();
Map<String, CodecValueReader<?, ?>> getReaders();
```

---

## 11. Configuration Hierarchy

Custom value reader/writer names follow the standard configuration hierarchy:

1. **Load/Save Options** (highest priority)
2. **ResourceFactory Defaults**
3. **CodecConfiguration**
4. **EAnnotation on EStructuralFeature**
5. **Built-in Defaults** (no custom reader/writer)

---

## 12. Implementation Classes

| Class | Purpose |
|-------|---------|
| `CodecValueReader<T, F>` | Interface for custom deserialization |
| `CodecValueWriter<T, F>` | Interface for custom serialization |
| `CodecValueRegistry` | Named reader/writer storage |
| `AttributeSerializationEntry` | Invokes attribute writers |
| `AttributeDeserializationEntry` | Invokes attribute readers |
| `ReferenceSerializationEntry` | Invokes reference writers |
| `ReferenceDeserializationEntry` | Invokes reference readers |

---

[Next: Polymorphism and Inheritance →](11-polymorphism.md)
