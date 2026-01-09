# Custom Value Readers/Writers

[← Back to Overview](00-overview.md) | [← Feature Serialization](09-feature.md)

---

Extensibility hooks for custom serialization logic.

## 1. Overview

Custom value readers/writers allow you to:
- Transform values during serialization/deserialization
- Handle special data types (dates, binary, custom formats)
- Implement domain-specific encoding

---

## 2. Registration

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .valueWriter("dateWriter", new ISO8601DateWriter())
    .valueReader("dateReader", new ISO8601DateReader())
    .build();
```

---

## 3. Usage per Target

### 3.1 EAnnotation (on EStructuralFeature)

```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="createdAt" eType="ecore:EDataType...">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.value.writer.name" value="dateWriter"/>
    <details key="codec.value.reader.name" value="dateReader"/>
  </eAnnotations>
</eStructuralFeatures>
```

The writer/reader must be registered in the codec configuration or be an auto-registered built-in.

### 3.2 Java Builder (Runtime Override)

**For Type:**
```java
TypeSerializationConfig.builder()
    .typeWriterName("customTypeWriter")
    .typeReaderName("customTypeReader")
    .build();
```

**For ID:**
```java
IdSerializationConfig.builder()
    .idWriterName("uuidWriter")
    .idReaderName("uuidReader")
    .build();
```

**For Features:**
```java
FeatureSerializationConfig.builder()
    .feature("createdAt")
    .valueWriterName("dateWriter")
    .valueReaderName("dateReader")
    .build();
```

---

## 4. Example: Custom Date Formatting

**Custom Writer:**
```java
public class ISO8601DateWriter implements CodecValueWriter<Date> {
    @Override
    public void write(Date value, JsonGenerator gen) throws IOException {
        gen.writeString(ISO8601_FORMAT.format(value));
    }
}
```

**Resulting Output:**
```json
{
  "createdAt": "2025-12-06T10:30:00Z"
}
```

---

[Next: Polymorphism and Inheritance →](11-polymorphism.md)
