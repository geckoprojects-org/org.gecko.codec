# Custom Value Readers/Writers

[← Back to Overview](00-overview.md) | [← Feature Serialization](09-feature.md)

---

Extensibility hooks for custom serialization logic.

## 1. Overview

Custom value readers/writers allow you to:
- Transform values during serialization/deserialization
- Handle special data types (dates, binary, custom formats)
- Implement domain-specific encoding

The system consists of:
- **`CodecValueWriter<T>`** - Functional interface for custom serialization
- **`CodecValueReader<T>`** - Functional interface for custom deserialization
- **`CodecValueRegistry`** - Registry to store named readers/writers

---

## 2. Interfaces

### 2.1 CodecValueWriter

```java
@FunctionalInterface
public interface CodecValueWriter<T> {
    /**
     * Writes a value to the JSON generator.
     *
     * @param value the value to write (never null)
     * @param gen the JSON generator
     * @throws IOException if writing fails
     */
    void write(T value, JsonGenerator gen) throws IOException;
}
```

### 2.2 CodecValueReader

```java
@FunctionalInterface
public interface CodecValueReader<T> {
    /**
     * Reads a value from the JSON parser.
     * The parser is positioned at the value token (not null).
     *
     * @param parser the JSON parser
     * @return the parsed value
     * @throws IOException if reading fails
     */
    T read(JsonParser parser) throws IOException;
}
```

---

## 3. Registration

### 3.1 CodecValueRegistry

Writers and readers are registered by name in a `CodecValueRegistry`:

```java
CodecValueRegistry registry = new CodecValueRegistry();

// Register writers
registry.registerWriter("isoDate", (Date date, JsonGenerator gen) ->
    gen.writeString(ISO_FORMAT.format(date)));

registry.registerWriter("base64Binary", (byte[] data, JsonGenerator gen) ->
    gen.writeString(Base64.getEncoder().encodeToString(data)));

// Register readers
registry.registerReader("isoDate", parser ->
    ISO_FORMAT.parse(parser.getString()));

registry.registerReader("base64Binary", parser ->
    Base64.getDecoder().decode(parser.getString()));
```

### 3.2 Passing Registry to CodecResource

```java
CodecResource resource = new CodecResource(
    uri,
    metadataService,
    configuration,
    registry,      // CodecValueRegistry
    mapperBuilder
);
```

### 3.3 Passing Registry via CodecModule

```java
CodecModule module = CodecModule.builder()
    .configuration(config)
    .metadataService(metadataService)
    .valueRegistry(registry)
    .build();
```

---

## 4. Activation per Feature

To use a custom reader/writer for a specific attribute, configure the feature with the registered name.

### 4.1 EAnnotation (on EStructuralFeature)

```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="createdAt" eType="ecore:EDataType...">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.feature.valueWriterName" value="isoDate"/>
    <details key="codec.feature.valueReaderName" value="isoDate"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 4.2 EffectiveFeatureConfig

The configuration hierarchy resolves `valueWriterName` and `valueReaderName`:

```java
EffectiveFeatureConfig featureConfig = codecConfig.getFeatureConfig(attribute);
String writerName = featureConfig.getValueWriterName(); // "isoDate"
String readerName = featureConfig.getValueReaderName(); // "isoDate"
```

### 4.3 Runtime Override via Load/Save Options

```java
Map<String, Object> options = CodecOptionsBuilder.create()
    .forFeature(MyPackage.Literals.PERSON__CREATED_AT)
    .valueWriterName("isoDate")
    .valueReaderName("isoDate")
    .build();

resource.save(outputStream, options);
```

---

## 5. Serialization Behavior

### 5.1 AttributeSerializationEntry

When serializing an attribute:

1. Check if `valueWriterName` is configured in `EffectiveFeatureConfig`
2. If configured, look up writer in `CodecValueRegistry`
3. If writer found, use it instead of default serialization
4. If writer not found, fall back to default serialization

```java
// Internal logic (simplified)
if (customWriter != null) {
    customWriter.write(value, gen);
} else {
    // Default: gen.writeString/writeNumber/etc.
}
```

### 5.2 Null Handling

Custom writers are **not called for null values**. Null is always serialized as JSON `null`:

```json
{
  "createdAt": null
}
```

---

## 6. Deserialization Behavior

### 6.1 AttributeDeserializationEntry

When deserializing an attribute:

1. Check if `valueReaderName` is configured in `EffectiveFeatureConfig`
2. If configured, look up reader in `CodecValueRegistry`
3. If reader found, use it instead of default deserialization
4. If reader not found, fall back to default deserialization

```java
// Internal logic (simplified)
if (customReader != null) {
    return customReader.read(parser);
} else {
    // Default: parser.getString/getIntValue/etc.
}
```

### 6.2 Null Handling

Custom readers are **not called for null values**. JSON `null` always results in a null value being set on the EObject.

---

## 7. Examples

### 7.1 Custom Date Formatting

**Writer:**
```java
CodecValueWriter<Date> isoDateWriter = (date, gen) -> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    gen.writeString(sdf.format(date));
};
```

**Reader:**
```java
CodecValueReader<Date> isoDateReader = parser -> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.parse(parser.getString());
};
```

**Output:**
```json
{
  "createdAt": "2025-12-06T10:30:00Z"
}
```

### 7.2 Base64 Binary Encoding

**Writer:**
```java
CodecValueWriter<byte[]> base64Writer = (data, gen) ->
    gen.writeString(Base64.getEncoder().encodeToString(data));
```

**Reader:**
```java
CodecValueReader<byte[]> base64Reader = parser ->
    Base64.getDecoder().decode(parser.getString());
```

**Output:**
```json
{
  "thumbnail": "SGVsbG8gV29ybGQh"
}
```

### 7.3 Custom Enum Serialization

**Writer (lowercase):**
```java
CodecValueWriter<Status> lowerCaseEnumWriter = (status, gen) ->
    gen.writeString(status.name().toLowerCase());
```

**Reader:**
```java
CodecValueReader<Status> lowerCaseEnumReader = parser ->
    Status.valueOf(parser.getString().toUpperCase());
```

**Output:**
```json
{
  "status": "active"
}
```

---

## 8. Error Handling

### 8.1 Writer Errors

If a custom writer throws an `IOException`, it is wrapped in an `UncheckedIOException`:

```java
throw new UncheckedIOException(
    "Custom value writer failed for attribute: " + attribute.getName(), e);
```

### 8.2 Reader Errors

If a custom reader throws an `IOException`, it is wrapped in an `UncheckedIOException`:

```java
throw new UncheckedIOException(
    "Custom value reader failed for attribute: " + attribute.getName(), e);
```

### 8.3 Missing Writer/Reader

If a configured `valueWriterName` or `valueReaderName` is not found in the registry, the codec falls back to default serialization/deserialization silently. No error is thrown.

---

## 9. Registry API

### 9.1 Registration

```java
void registerWriter(String name, CodecValueWriter<?> writer);
void registerReader(String name, CodecValueReader<?> reader);
```

### 9.2 Lookup

```java
Optional<CodecValueWriter<?>> getWriter(String name);
Optional<CodecValueReader<?>> getReader(String name);
boolean hasWriter(String name);
boolean hasReader(String name);
```

### 9.3 Introspection

```java
Map<String, CodecValueWriter<?>> getWriters();
Map<String, CodecValueReader<?>> getReaders();
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

[Next: Polymorphism and Inheritance →](11-polymorphism.md)
