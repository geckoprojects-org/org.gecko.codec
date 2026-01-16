# 17. OpenAPI Support

This chapter documents the OpenAPI codec support, which demonstrates the power of custom value readers/writers for handling complex nested formats like JSON Schema within OpenAPI documents.

---

## 1. Overview

OpenAPI 3.x documents embed JSON Schema definitions in `components/schemas`. The Fennec codec handles this by:

1. **Reading**: Converting JSON Schema objects to EMF `EPackage` with `EClass` definitions
2. **Writing**: Converting `EPackage` back to JSON Schema format

This is implemented using the `ReferenceValueReader/Writer` pattern described in [Chapter 10: Custom Values](10-custom-values.md).

---

## 2. Architecture

### 2.1 Component Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                     OpenApiResourceImpl                          │
│  ┌─────────────────────────────────────────────────────────────┐│
│  │                   CodecValueRegistry                         ││
│  │  ┌─────────────────────┐  ┌─────────────────────┐           ││
│  │  │ EPackageValueReader │  │ EPackageValueWriter │           ││
│  │  │   (name: "schemas") │  │   (name: "schemas") │           ││
│  │  └──────────┬──────────┘  └──────────┬──────────┘           ││
│  └─────────────┼────────────────────────┼───────────────────────┘│
│                │                        │                        │
│                ▼                        ▼                        │
│  ┌─────────────────────────┐  ┌─────────────────────────────┐   │
│  │ JsonSchemaToEPackage    │  │ EPackageToJsonSchema        │   │
│  │ Converter               │  │ Converter                   │   │
│  └─────────────────────────┘  └─────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 Key Classes

| Class | Module | Purpose |
|-------|--------|---------|
| `OpenApiResourceImpl` | `codec.openapi` | Resource with pre-configured registry |
| `EPackageValueReader` | `codec.jsonschema.v2` | JSON Schema → EPackage conversion |
| `EPackageValueWriter` | `codec.jsonschema.v2` | EPackage → JSON Schema conversion |
| `JsonSchemaToEPackageConverter` | `codec.jsonschema.v2` | Core deserialization logic |
| `EPackageToJsonSchemaConverter` | `codec.jsonschema.v2` | Core serialization logic |

---

## 3. Model Definition

The OpenAPI model defines the `schemas` reference as a containment to `EPackage`:

```xml
<!-- openapi.ecore -->
<eClassifiers xsi:type="ecore:EClass" name="Components">
  <eStructuralFeatures xsi:type="ecore:EReference" name="schemas"
                       eType="ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//EPackage"
                       containment="true">
    <eAnnotations source="http://eclipse.org/fennec/codec">
      <details key="valueReaderName" value="schemas"/>
      <details key="valueWriterName" value="schemas"/>
    </eAnnotations>
  </eStructuralFeatures>
</eClassifiers>
```

Key points:
- `eType` is `EPackage` from Ecore metamodel
- `containment="true"` means the schemas are owned by the Components object
- Codec annotations specify the reader/writer names in the registry

---

## 4. Resource Configuration

### 4.1 OpenApiResourceImpl

```java
public class OpenApiResourceImpl extends CodecResource {

    public OpenApiResourceImpl(URI uri) {
        super(uri);
    }

    @Override
    protected CodecValueRegistry createValueRegistry() {
        CodecValueRegistry registry = new CodecValueRegistry();

        // Register JSON Schema ↔ EPackage converters
        registry.registerReader("schemas", new EPackageValueReader());
        registry.registerWriter("schemas", new EPackageValueWriter("schemas", true));

        return registry;
    }
}
```

### 4.2 Factory Registration

```java
public class OpenApiResourceFactoryImpl implements Resource.Factory {

    @Override
    public Resource createResource(URI uri) {
        return new OpenApiResourceImpl(uri);
    }
}
```

---

## 5. Value Reader/Writer Implementation

### 5.1 EPackageValueReader

The reader implements `ReferenceValueReader<EPackage>` with type checking:

```java
public class EPackageValueReader implements ReferenceValueReader<EPackage> {

    @Override
    public boolean canHandle(EReference reference) {
        // Only handle references to EPackage
        return EcorePackage.Literals.EPACKAGE.isSuperTypeOf(
            reference.getEReferenceType());
    }

    @Override
    public EPackage read(JsonParser parser, EReference ref,
                         DeserializationContext ctxt) throws IOException {
        // Use converter to transform JSON Schema to EPackage
        JsonSchemaToEPackageConverter converter = new JsonSchemaToEPackageConverter();
        return converter.convert(parser);
    }
}
```

### 5.2 EPackageValueWriter

The writer implements `ReferenceValueWriter<EPackage>`:

```java
public class EPackageValueWriter implements ReferenceValueWriter<EPackage> {

    private final String definitionsKey;
    private final boolean useDefinitions;

    public EPackageValueWriter(String definitionsKey, boolean useDefinitions) {
        this.definitionsKey = definitionsKey;
        this.useDefinitions = useDefinitions;
    }

    @Override
    public boolean canHandle(EReference reference) {
        return EcorePackage.Literals.EPACKAGE.isSuperTypeOf(
            reference.getEReferenceType());
    }

    @Override
    public void write(EPackage value, EReference ref, JsonGenerator gen,
                      SerializationContext ctxt) throws IOException {
        EPackageToJsonSchemaConverter converter =
            new EPackageToJsonSchemaConverter(useDefinitions, definitionsKey);
        converter.convert(value, gen);
    }
}
```

---

## 6. JSON Schema Conversion

### 6.1 Schema to EPackage Mapping

| JSON Schema | EMF Element |
|-------------|-------------|
| Schema object | `EPackage` |
| Object type definition | `EClass` |
| Property (string, integer, etc.) | `EAttribute` |
| Property ($ref to object) | `EReference` |
| Array of primitives | Multi-valued `EAttribute` |
| Array of objects | Multi-valued `EReference` |
| Enum | `EEnum` |
| allOf | `EClass` with supertypes |
| oneOf/anyOf | Abstract parent with subtypes |

### 6.2 Example Conversion

**JSON Schema Input:**
```json
{
  "Pet": {
    "type": "object",
    "required": ["name"],
    "properties": {
      "id": { "type": "integer", "format": "int64" },
      "name": { "type": "string" },
      "status": {
        "type": "string",
        "enum": ["available", "pending", "sold"]
      },
      "tags": {
        "type": "array",
        "items": { "$ref": "#/components/schemas/Tag" }
      }
    }
  },
  "Tag": {
    "type": "object",
    "properties": {
      "id": { "type": "integer" },
      "name": { "type": "string" }
    }
  }
}
```

**Resulting EPackage:**
```
EPackage (nsURI derived from context)
├── EClass "Pet"
│   ├── EAttribute "id" : ELong
│   ├── EAttribute "name" : EString [lowerBound=1]
│   ├── EAttribute "status" : PetStatus (EEnum)
│   └── EReference "tags" : Tag [0..*]
├── EClass "Tag"
│   ├── EAttribute "id" : EInt
│   └── EAttribute "name" : EString
└── EEnum "PetStatus"
    ├── available = 0
    ├── pending = 1
    └── sold = 2
```

---

## 7. Roundtrip Behavior

### 7.1 Schema Preservation

Most schemas are preserved through roundtrip:

| Aspect | Preserved | Notes |
|--------|-----------|-------|
| Class names | ✅ | Exact match |
| Property names | ✅ | Exact match |
| Property types | ✅ | Mapped to closest JSON Schema type |
| Required fields | ✅ | Via `lowerBound >= 1` |
| Enumerations | ✅ | Full literal preservation |
| References | ✅ | Via `$ref` |
| Descriptions | ✅ | Via GenModel annotations |

### 7.2 Artificial Schemas

Some schemas are marked as "artificial" during conversion (e.g., inline object definitions). These are expanded inline during serialization and not recreated as top-level schemas:

```
Original: 54 schemas → After roundtrip: 50 schemas (4 artificial)
```

This is expected behavior and does not affect semantic correctness.

### 7.3 Test Results

Real-world OpenAPI files tested:

| File | Size | Schemas | After Roundtrip | Preservation |
|------|------|---------|-----------------|--------------|
| petstore.json | 46 KB | 8 | 8 | 100% |
| bike.json | ~100 KB | 54 | 50 | 93% |
| sevdesk.json | 678 KB | 234 | 199 | 85% |
| kubernetes-api.json | 1.9 MB | 286 | 286 | 100% |

---

## 8. Usage Example

### 8.1 Loading an OpenAPI Document

```java
// Register factory
Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
    .put("json", new OpenApiResourceFactoryImpl());

// Register package
EPackage.Registry.INSTANCE.put(
    OpenApiPackage.eNS_URI, OpenApiPackage.eINSTANCE);

// Load
ResourceSet resourceSet = new ResourceSetImpl();
Resource resource = resourceSet.createResource(
    URI.createFileURI("petstore.json"));

Map<String, Object> options = new HashMap<>();
options.put(CodecResource.CODEC_ROOT_OBJECT, OpenApiPackage.Literals.OPEN_API);
resource.load(options);

// Access
OpenApi openApi = (OpenApi) resource.getContents().get(0);
EPackage schemas = openApi.getComponents().getSchemas();

// Use schemas
EClass petClass = (EClass) schemas.getEClassifier("Pet");
EAttribute nameAttr = (EAttribute) petClass.getEStructuralFeature("name");
```

### 8.2 Creating and Saving

```java
// Create OpenAPI programmatically
OpenApi openApi = OpenApiFactory.eINSTANCE.createOpenApi();
openApi.setOpenapi("3.0.3");

Info info = OpenApiFactory.eINSTANCE.createInfo();
info.setTitle("My API");
info.setVersion("1.0.0");
openApi.setInfo(info);

// Create schemas as EPackage
EPackage schemas = EcoreFactory.eINSTANCE.createEPackage();
schemas.setName("schemas");
schemas.setNsURI("http://example.com/api/schemas");

EClass userClass = EcoreFactory.eINSTANCE.createEClass();
userClass.setName("User");
// ... add attributes

schemas.getEClassifiers().add(userClass);

Components components = OpenApiFactory.eINSTANCE.createComponents();
components.setSchemas(schemas);
openApi.setComponents(components);

// Save
Resource resource = new OpenApiResourceImpl(
    URI.createFileURI("output.json"));
resource.getContents().add(openApi);
resource.save(null);
```

---

## 9. Configuration Options

### 9.1 Writer Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `definitionsKey` | String | `"schemas"` | Key for schema definitions |
| `useDefinitions` | boolean | `true` | Use `$ref` for complex types |

### 9.2 Converter Annotations

The converters use annotations to preserve metadata:

| Annotation Source | Key | Purpose |
|-------------------|-----|---------|
| `http://eclipse.org/fennec/jsonschema` | `artificial` | Mark inline-expanded schemas |
| `http://www.eclipse.org/emf/2002/GenModel` | `documentation` | Schema descriptions |

---

## 10. Limitations

1. **Swagger 2.0**: Only OpenAPI 3.x is supported. Swagger 2.0 uses `definitions` instead of `components/schemas` and has different structure.

2. **Complex Schema Features**: Some advanced JSON Schema features may have limited support:
   - `patternProperties`
   - `additionalProperties` with schema (boolean supported)
   - `if`/`then`/`else`

3. **Circular References**: Handled via `$ref`, but deeply nested cycles may cause issues.

4. **Schema Loss**: Artificial/inline schemas are expanded and not preserved as named schemas after roundtrip.

---

## 11. Related Chapters

- [Chapter 10: Custom Values](10-custom-values.md) - Reader/Writer architecture
- [Chapter 16: Format Abstraction](16-format-abstraction.md) - JSON Schema converter details

---

[← Previous: Format Abstraction](16-format-abstraction.md)
