# 16. Format Abstraction and Custom Parsers/Generators

This chapter defines how codec.v2 supports multiple serialization formats beyond JSON, including BSON (MongoDB), CSV, query strings, and custom protocols.

## 1. Overview

The codec is designed to be **format-agnostic** at its core, with format-specific adapters for different backends:

| Format | Backend | Use Cases |
|--------|---------|-----------|
| JSON | Jackson native | REST APIs, file storage, configuration |
| BSON | MongoDB driver | MongoDB persistence |
| CSV/Query String | Custom parser | HTTP query parameters, simple key-value |
| EcoWitt | Custom parser | Weather station device protocol |
| JSON Schema | Jackson | Schema generation/parsing |

**Design Goal:** The serialization/deserialization logic (type handling, ID strategies, references) should be implemented once and work across all formats.

---

## 2. Architecture

### 2.1 Layered Design

```
┌─────────────────────────────────────────────────────────────────────┐
│                     Application Layer                                │
│         (EMF Resource, load/save operations)                        │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────────────┐
│                     Codec Logic Layer                                │
│    (Serialization/Deserialization Entries, Type/ID/Reference)       │
│                                                                      │
│  - TypeSerializationEntry / TypeDeserializationEntry                │
│  - IdSerializationEntry / IdDeserializationEntry                    │
│  - ReferenceSerializationEntry / ReferenceDeserializationEntry      │
│  - AttributeSerializationEntry / AttributeDeserializationEntry      │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────────────┐
│                   Value Transformation Layer                         │
│              (CodecValueReader / CodecValueWriter)                   │
│                                                                      │
│  - Format-agnostic value transformation                             │
│  - Date formatting, Base64 encoding, custom ID schemes              │
│  - Pluggable via configuration                                      │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────────────┐
│                   Stream Abstraction Layer                           │
│              (CodecStreamReader / CodecStreamWriter)                 │
│                                                                      │
│  - Abstract token-based read/write operations                       │
│  - Hides format-specific details                                    │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
         ┌──────────────────────┼──────────────────────┐
         │                      │                      │
         ▼                      ▼                      ▼
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│  JSON Adapter   │  │  BSON Adapter   │  │  CSV Adapter    │
│  (Jackson)      │  │  (MongoDB)      │  │  (Query String) │
└─────────────────┘  └─────────────────┘  └─────────────────┘
```

### 2.2 Token Model

All formats are abstracted to a common token stream model:

```java
public enum CodecToken {
    // Structure tokens
    START_OBJECT,
    END_OBJECT,
    START_ARRAY,
    END_ARRAY,

    // Content tokens
    PROPERTY_NAME,

    // Value tokens
    VALUE_STRING,
    VALUE_NUMBER_INT,
    VALUE_NUMBER_FLOAT,
    VALUE_TRUE,
    VALUE_FALSE,
    VALUE_NULL,
    VALUE_BINARY,        // For formats with native binary support (BSON)
    VALUE_EMBEDDED,      // For format-specific embedded types

    // Special
    NOT_AVAILABLE
}
```

---

## 3. Stream Abstraction Interfaces

### 3.1 CodecStreamReader

Abstract interface for reading from any format:

```java
public interface CodecStreamReader extends Closeable {

    // Token navigation
    CodecToken currentToken();
    CodecToken nextToken();

    // Property access
    String currentName();

    // Value access - primitives
    String getString();
    int getIntValue();
    long getLongValue();
    double getDoubleValue();
    boolean getBooleanValue();

    // Value access - complex
    byte[] getBinaryValue();
    BigDecimal getDecimalValue();
    BigInteger getBigIntegerValue();

    // Format-specific value access
    <T> T getNativeValue(Class<T> type);

    // Location for diagnostics
    CodecLocation currentLocation();

    // Structure navigation
    void skipChildren();

    // Context
    int getCurrentDepth();
    boolean isInArray();
    boolean isInObject();
}
```

### 3.2 CodecStreamWriter

Abstract interface for writing to any format:

```java
public interface CodecStreamWriter extends Closeable, Flushable {

    // Structure
    void writeStartObject();
    void writeEndObject();
    void writeStartArray();
    void writeEndArray();

    // Property name
    void writePropertyName(String name);

    // Values - primitives
    void writeString(String value);
    void writeNumber(int value);
    void writeNumber(long value);
    void writeNumber(double value);
    void writeNumber(BigDecimal value);
    void writeBoolean(boolean value);
    void writeNull();

    // Values - complex
    void writeBinary(byte[] data);

    // Format-specific value writing
    <T> void writeNativeValue(T value);

    // Raw output (for pre-formatted content)
    void writeRaw(String raw);
}
```

### 3.3 CodecLocation

Location information for diagnostics:

```java
public interface CodecLocation {
    long getCharOffset();    // -1 if not available
    int getLineNr();         // -1 if not available
    int getColumnNr();       // -1 if not available
    String getSourceRef();   // Resource URI or input description
}
```

---

## 4. Value Reader/Writer Abstraction

### 4.1 Current Design (Jackson-coupled)

The current interfaces are tightly coupled to Jackson:

```java
// Current - Jackson specific
public interface CodecValueReader<T, F extends EStructuralFeature> {
    T read(JsonParser parser, F feature, DeserializationContext ctxt) throws IOException;
}

public interface CodecValueWriter<T, F extends EStructuralFeature> {
    void write(T value, F feature, JsonGenerator gen, SerializationContext ctxt) throws IOException;
}
```

### 4.2 Proposed Design (Format-agnostic)

New interfaces using the stream abstraction:

```java
// Proposed - format agnostic
public interface CodecValueReader<T, F extends EStructuralFeature> {
    T read(CodecStreamReader reader, F feature, CodecReadContext ctxt);
}

public interface CodecValueWriter<T, F extends EStructuralFeature> {
    void write(T value, F feature, CodecStreamWriter writer, CodecWriteContext ctxt);
}
```

### 4.3 Backward Compatibility

For Jackson-based formats, provide adapter implementations:

```java
public class JacksonStreamReader implements CodecStreamReader {
    private final JsonParser parser;

    public JacksonStreamReader(JsonParser parser) {
        this.parser = parser;
    }

    @Override
    public CodecToken currentToken() {
        return mapToken(parser.currentToken());
    }

    @Override
    public String getString() {
        return parser.getString();
    }

    // ... delegate all methods to JsonParser
}

public class JacksonStreamWriter implements CodecStreamWriter {
    private final JsonGenerator generator;

    // ... delegate all methods to JsonGenerator
}
```

---

## 5. Format Adapters

### 5.1 JSON Adapter (Jackson Native)

The default adapter using Jackson's built-in JSON support:

```java
public class JsonCodecAdapter implements CodecFormatAdapter {

    @Override
    public CodecStreamReader createReader(InputStream input, CodecReadContext ctxt) {
        JsonFactory factory = ctxt.getJsonFactory();
        JsonParser parser = factory.createParser(input);
        return new JacksonStreamReader(parser);
    }

    @Override
    public CodecStreamWriter createWriter(OutputStream output, CodecWriteContext ctxt) {
        JsonFactory factory = ctxt.getJsonFactory();
        JsonGenerator generator = factory.createGenerator(output);
        return new JacksonStreamWriter(generator);
    }
}
```

### 5.2 BSON Adapter (MongoDB)

Adapter wrapping MongoDB's BSON reader/writer:

```java
public class BsonCodecAdapter implements CodecFormatAdapter {

    @Override
    public CodecStreamReader createReader(InputStream input, CodecReadContext ctxt) {
        BsonReader bsonReader = new BsonBinaryReader(ByteBuffer.wrap(readBytes(input)));
        return new BsonStreamReader(bsonReader);
    }

    @Override
    public CodecStreamWriter createWriter(OutputStream output, CodecWriteContext ctxt) {
        BsonWriter bsonWriter = new BsonBinaryWriter(output);
        return new BsonStreamWriter(bsonWriter);
    }
}

public class BsonStreamReader implements CodecStreamReader {
    private final BsonReader reader;

    @Override
    public <T> T getNativeValue(Class<T> type) {
        // Support BSON-specific types
        if (type == ObjectId.class) {
            return type.cast(reader.readObjectId());
        }
        if (type == Decimal128.class) {
            return type.cast(reader.readDecimal128());
        }
        return null;
    }

    // ... map BSON types to CodecToken
}
```

### 5.3 Query String Adapter (CSV/EcoWitt)

Adapter for simple key-value formats:

```java
public class QueryStringCodecAdapter implements CodecFormatAdapter {

    @Override
    public CodecStreamReader createReader(InputStream input, CodecReadContext ctxt) {
        Map<String, String> data = QueryStringParser.parse(input);
        return new MapStreamReader(data);
    }

    @Override
    public CodecStreamWriter createWriter(OutputStream output, CodecWriteContext ctxt) {
        return new QueryStringStreamWriter(output);
    }
}

public class MapStreamReader implements CodecStreamReader {
    private final Map<String, String> data;
    private final Iterator<Map.Entry<String, String>> iterator;
    private State state = State.BEGIN;

    // Simulates object structure from flat map:
    // START_OBJECT -> (PROPERTY_NAME, VALUE_STRING)* -> END_OBJECT
}
```

---

## 6. Format-Specific Features

### 6.1 Native Type Support

Some formats have native types that don't exist in JSON:

| Format | Native Types | Handling |
|--------|--------------|----------|
| BSON | ObjectId, Decimal128, BsonBinary, Date | `getNativeValue(Class)` / `writeNativeValue(Object)` |
| JSON | - | All values as JSON primitives |
| CSV | - | All values as strings |

### 6.2 Custom Value Writers for Format-Specific Types

```java
// MongoDB ObjectId writer
public class ObjectIdValueWriter implements CodecValueWriter<String, EAttribute> {

    @Override
    public void write(String value, EAttribute attr, CodecStreamWriter writer, CodecWriteContext ctxt) {
        if (writer instanceof BsonStreamWriter bsonWriter) {
            // Use native ObjectId
            bsonWriter.writeNativeValue(new ObjectId(value));
        } else {
            // Fall back to string representation
            writer.writeString(value);
        }
    }
}

// MongoDB Decimal128 reader
public class Decimal128ValueReader implements CodecValueReader<BigDecimal, EAttribute> {

    @Override
    public BigDecimal read(CodecStreamReader reader, EAttribute attr, CodecReadContext ctxt) {
        Decimal128 native = reader.getNativeValue(Decimal128.class);
        if (native != null) {
            return native.bigDecimalValue();
        }
        // Fall back to string parsing
        return new BigDecimal(reader.getString());
    }
}
```

### 6.3 Binary Data Handling

```java
// Base64 encoding for JSON, native binary for BSON
public class BinaryValueWriter implements CodecValueWriter<byte[], EAttribute> {

    @Override
    public void write(byte[] value, EAttribute attr, CodecStreamWriter writer, CodecWriteContext ctxt) {
        if (writer.supportsNativeBinary()) {
            writer.writeBinary(value);
        } else {
            // Encode as Base64 string
            writer.writeString(Base64.getEncoder().encodeToString(value));
        }
    }
}
```

---

## 7. Configuration

### 7.1 Format Selection

The format is typically determined by:

1. **File extension**: `.json`, `.bson`, `.csv`
2. **Content type**: `application/json`, `application/bson`
3. **Explicit option**: `CODEC_FORMAT` option in load/save options

```java
Map<String, Object> options = new HashMap<>();
options.put(CodecOptions.CODEC_FORMAT, "bson");
resource.save(outputStream, options);
```

### 7.2 Format Adapter Registry

```java
public interface CodecFormatRegistry {
    void registerAdapter(String format, CodecFormatAdapter adapter);
    CodecFormatAdapter getAdapter(String format);
    CodecFormatAdapter getAdapterForExtension(String extension);
    CodecFormatAdapter getAdapterForContentType(String contentType);
}
```

Default registrations:

| Format ID | Extensions | Content Types |
|-----------|------------|---------------|
| `json` | `.json` | `application/json`, `text/json` |
| `bson` | `.bson` | `application/bson` |
| `csv` | `.csv` | `text/csv` |
| `querystring` | - | `application/x-www-form-urlencoded` |

---

## 8. Migration Path

### 8.1 Phase 1: Introduce Abstractions (Non-Breaking)

1. Add `CodecStreamReader`, `CodecStreamWriter`, `CodecToken` interfaces
2. Add `JacksonStreamReader`, `JacksonStreamWriter` adapters
3. Keep existing `CodecValueReader`/`CodecValueWriter` with Jackson types
4. Add overloaded methods accepting stream abstractions

### 8.2 Phase 2: Update Entry Classes

1. Refactor `*SerializationEntry` to use `CodecStreamWriter`
2. Refactor `*DeserializationEntry` to use `CodecStreamReader`
3. Update custom value readers/writers to use abstractions

### 8.3 Phase 3: Add Format Adapters

1. Implement `BsonCodecAdapter`
2. Implement `QueryStringCodecAdapter`
3. Integrate with existing V1 parser/generator infrastructure

---

## 9. Compatibility with V1 Codecs

The V1 codec implementations (`CodecParserBaseImpl`, `CodecGeneratorBaseImpl`) can be wrapped:

```java
// Wrap V1 parser in stream abstraction
public class V1ParserStreamReader implements CodecStreamReader {
    private final CodecParserBaseImpl parser;

    @Override
    public CodecToken currentToken() {
        return mapToken(parser.currentToken());
    }

    @Override
    public String getString() {
        return (String) parser.doGetCurrentValue();
    }
}
```

This allows gradual migration from V1 to V2 architecture.

---

## 10. Example: Multi-Format Value Reader

A value reader that works across all formats:

```java
public class ISO8601DateReader implements CodecValueReader<Date, EAttribute> {

    private static final SimpleDateFormat FORMAT =
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public Date read(CodecStreamReader reader, EAttribute attr, CodecReadContext ctxt) {
        CodecToken token = reader.currentToken();

        switch (token) {
            case VALUE_STRING:
                // Parse ISO8601 string (JSON, CSV)
                return FORMAT.parse(reader.getString());

            case VALUE_NUMBER_INT:
                // Unix timestamp (some formats)
                return new Date(reader.getLongValue());

            case VALUE_EMBEDDED:
                // Native date type (BSON)
                Date native = reader.getNativeValue(Date.class);
                if (native != null) return native;
                // Fall through to string

            default:
                throw new CodecException("Cannot read date from token: " + token);
        }
    }
}
```

---

## 11. Open Questions

1. **Streaming vs Buffering**: Should the abstraction support streaming for large documents, or is buffering acceptable?

2. **Schema Information**: Should the stream abstraction carry schema/type hints for formats that support them?

3. **Pretty Printing**: How to handle format-specific pretty printing options?

4. **Error Recovery**: Should the abstraction support error recovery / partial parsing?

5. **Async Support**: Should we consider async/reactive stream interfaces for future scalability?
