# Non-OSGi Migration Technical Document

## Overview

This document outlines the technical changes required to create a non-OSGi version of the Eclipse Fennec Codec framework. The analysis focuses on core bundles, excluding test and model bundles.

## Core Bundles to Migrate

1. **org.eclipse.fennec.codec** - Core codec framework
2. **org.eclipse.fennec.codec.info** - Model annotation processing and codec metadata
3. **org.eclipse.fennec.codec.json** - JSON implementation
4. **org.eclipse.fennec.codec.mongo** - MongoDB persistence
5. **org.eclipse.fennec.codec.csv** - CSV support
6. **org.eclipse.fennec.codec.jsonschema** - JSON Schema support
7. **org.eclipse.fennec.codec.ecowitt** - EcoWitt support
8. **org.eclipse.fennec.codec.constants** - Shared constants

---

## 1. Service Injection to Explicit Setters

### 1.1 org.eclipse.fennec.codec Bundle

#### DefaultCodecModuleConfigurator
**File:** `org.eclipse.fennec.codec/src/org/eclipse/fennec/codec/jackson/module/DefaultCodecModuleConfigurator.java:31-42`

**Current OSGi Pattern:**
```java
@Component(name = "DefaultCodecModuleConfigurator",
    service = CodecModuleConfigurator.class,
    configurationPolicy = ConfigurationPolicy.REQUIRE,
    property = "type=json")
public class DefaultCodecModuleConfigurator implements CodecModuleConfigurator {

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    CodecEMFSerializers serializers;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    CodecEMFDeserializers deserializers;

    @Activate
    public void activate(CodecModuleConfig codecConfig) { ... }
}
```

**Required Changes:**
- Remove `@Component`, `@Reference`, `@Activate` annotations
- Replace `@Activate` method with explicit constructor or initialization method
- Add explicit setter methods for `serializers` and `deserializers`
- Configuration via properties file/builder pattern instead of `CodecModuleConfig` from ConfigurationAdmin

**Suggested Non-OSGi Pattern:**
```java
public class DefaultCodecModuleConfigurator implements CodecModuleConfigurator {

    private CodecEMFSerializers serializers;
    private CodecEMFDeserializers deserializers;
    private CodecModuleConfig codecConfig;

    public DefaultCodecModuleConfigurator(CodecModuleConfig codecConfig) {
        this.codecConfig = codecConfig;
    }

    public void setSerializers(CodecEMFSerializers serializers) {
        this.serializers = serializers;
    }

    public void setDeserializers(CodecEMFDeserializers deserializers) {
        this.deserializers = deserializers;
    }

    public void initialize() {
        if (serializers == null || deserializers == null) {
            throw new IllegalStateException("Serializers and deserializers must be set");
        }
        moduleBuilder = new CodecModule.Builder();
        configureModuleBuilder(codecConfig);
    }
}
```

---

#### DefaultCodecFactoryConfigurator
**File:** `org.eclipse.fennec.codec/src/org/eclipse/fennec/codec/jackson/DefaultCodecFactoryConfigurator.java:36-81`

**Current OSGi Pattern:**
```java
@Component(name = "DefaultCodecFactoryConfigurator",
    service = CodecFactoryConfigurator.class,
    configurationPolicy = ConfigurationPolicy.REQUIRE,
    property = {"type=json"})
public class DefaultCodecFactoryConfigurator extends AbstractCodecFactoryConfigurator {

    @Reference(target="(type=json)", cardinality = ReferenceCardinality.OPTIONAL,
        policy = ReferencePolicy.DYNAMIC)
    public void setGenFactory(CodecGeneratorFactory<?,?> genFactory) { ... }

    @Reference(target="(type=json)", cardinality = ReferenceCardinality.OPTIONAL,
        policy = ReferencePolicy.DYNAMIC)
    public void setParserFactory(CodecParserFactory<?,?> parserFactory) { ... }

    @Activate
    public void activate(Map<String, Object> properties) { ... }
}
```

**Required Changes:**
- Remove `@Component`, `@Reference`, `@Activate` annotations
- Remove OSGi service filter targets like `target="(type=json)"`
- Replace dynamic references with simple setter methods
- Configuration via properties file instead of ConfigurationAdmin

---

#### DefaultObjectMapperConfigurator
**File:** `org.eclipse.fennec.codec/src/org/eclipse/fennec/codec/jackson/DefaultObjectMapperConfigurator.java:31-54`

**Current OSGi Pattern:**
```java
@Component(name = "DefaultObjectMapperConfigurator",
    service = ObjectMapperConfigurator.class,
    configurationPolicy = ConfigurationPolicy.REQUIRE,
    property = "type=json")
public class DefaultObjectMapperConfigurator implements ObjectMapperConfigurator {

    @Reference(target="(type=json)")
    CodecFactoryConfigurator codecFactoryConfigurator;

    @Activate
    public void activate(Map<String, Object> properties) { ... }
}
```

**Required Changes:**
- Remove `@Component`, `@Reference`, `@Activate` annotations
- Add explicit setter for `codecFactoryConfigurator`
- Configuration via properties/builder pattern

---

#### DefaultCodecEMFSerializers
**File:** `org.eclipse.fennec.codec/src/org/eclipse/fennec/codec/jackson/databind/ser/DefaultCodecEMFSerializers.java:45`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "DefaultCodecEMFSerializers",
    service = CodecEMFSerializers.class)
public class DefaultCodecEMFSerializers extends Serializers.Base
    implements CodecEMFSerializers
```

**Required Changes:**
- Remove `@Component` annotation
- Add factory method or builder for instantiation
- Explicitly inject dependencies via constructor/setters

---

#### DefaultCodecEMFDeserializers
**File:** `org.eclipse.fennec.codec/src/org/eclipse/fennec/codec/jackson/databind/deser/DefaultCodecEMFDeserializers.java:45`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "DefaultCodecEMFDeserializers",
    service = CodecEMFDeserializers.class)
public class DefaultCodecEMFDeserializers extends Deserializers.Base
    implements CodecEMFDeserializers
```

**Required Changes:**
- Remove `@Component` annotation
- Add factory method or builder for instantiation

---

### 1.2 org.eclipse.fennec.codec.info Bundle

#### CodecModelInfoImpl
**File:** `org.eclipse.fennec.codec.info/src/org/eclipse/fennec/codec/info/impl/CodecModelInfoImpl.java:62-140`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "CodecModelInfoService",
    service = CodecModelInfo.class)
public class CodecModelInfoImpl extends HashMap<String, Object>
    implements CodecModelInfo {

    @Reference
    CodecInfoHolderHelper codecInfoHolderHelper;

    @Activate
    public void activate() { ... }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE,
        policy = ReferencePolicy.DYNAMIC)
    public void bindEPackage(EPackage ePackage) { ... }

    public void unbindEPackage(EPackage ePackage) { ... }
}
```

**Required Changes:**
- Remove `@Component`, `@Reference`, `@Activate` annotations
- Remove OSGi dynamic service tracking (`bindEPackage`/`unbindEPackage`)
- Replace with explicit registration methods:
  - `registerEPackage(EPackage ePackage)`
  - `unregisterEPackage(EPackage ePackage)`
- Add explicit setter for `codecInfoHolderHelper`
- Implement manual initialization instead of `@Activate`

---

#### CodecInfoHolderHelper
**File:** `org.eclipse.fennec.codec.info/src/org/eclipse/fennec/codec/info/helper/CodecInfoHolderHelper.java:38-49`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "CodecInfoHolderHelper",
    service = CodecInfoHolderHelper.class)
public class CodecInfoHolderHelper {

    ComponentServiceObjects<ResourceSet> rsFactory;

    @Activate
    public CodecInfoHolderHelper(@Reference ComponentServiceObjects<ResourceSet> rsFactory) {
        this.rsFactory = rsFactory;
    }
}
```

**Required Changes:**
- Remove `@Component`, `@Reference`, `@Activate` annotations
- Replace `ComponentServiceObjects<ResourceSet>` with direct `ResourceSet` instance or factory pattern
- **Critical:** `ComponentServiceObjects` is OSGi-specific for prototype-scoped services. Replace with:
  - Simple `ResourceSetFactory` interface
  - Or direct `ResourceSet` instance management
  - Or use supplier/factory pattern: `Supplier<ResourceSet>`

**OSGi API Dependency:** Uses `org.osgi.service.component.ComponentServiceObjects` which must be replaced with custom factory pattern.

---

#### URIReader, EClassReaderByName, EClassReaderByQualifiedName
**Files:**
- `org.eclipse.fennec.codec.info/src/org/eclipse/fennec/codec/info/value/readers/URIReader.java:33-37`
- Similar pattern in `EClassReaderByName.java` and `EClassReaderByQualifiedName.java`

**Current OSGi Pattern:**
```java
public class URIReader implements CodecValueReader<String, EClass>{

    private ComponentServiceObjects<ResourceSet> rsFactory;

    public URIReader(ComponentServiceObjects<ResourceSet> rsFactory) {
        this.rsFactory = rsFactory;
    }

    public EClass readValue(String value, DeserializationContext context) {
        ResourceSet resourceSet = rsFactory.getService();
        try {
            return (EClass) resourceSet.getEObject(URI.createURI(value), true);
        } finally {
            rsFactory.ungetService(resourceSet);
        }
    }
}
```

**Required Changes:**
- Replace `ComponentServiceObjects<ResourceSet>` with factory pattern or direct instance
- Suggested pattern: `Supplier<ResourceSet>` or custom `ResourceSetFactory` interface

---

### 1.3 org.eclipse.fennec.codec.json Bundle

#### JsonResourceFactory
**File:** `org.eclipse.fennec.codec.json/src/org/eclipse/fennec/codec/json/resource/JsonResourceFactory.java:31-52`

**Current OSGi Pattern:**
```java
@Component(name = "JsonRF",
    service = {Resource.Factory.class, JsonResourceFactory.class},
    property = {EMFNamespaces.EMF_CONFIGURATOR_NAME + "=CodecJson",
                EMFNamespaces.EMF_MODEL_FILE_EXT + "=json",
                EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/json"})
public class JsonResourceFactory extends ResourceFactoryImpl {

    @Reference
    private CodecModelInfo modelInfo;

    @Reference(target="(type=json)")
    private ObjectMapperConfigurator objMapperConfigurator;

    @Reference(target="(type=json)")
    private CodecModuleConfigurator codecModuleConfigurator;
}
```

**Required Changes:**
- Remove `@Component`, `@Reference` annotations
- Remove OSGi service properties (EMF namespace properties are OSGi-specific)
- Add constructor or setters for all dependencies
- Register factory programmatically with EMF Registry

---

### 1.4 org.eclipse.fennec.codec.mongo Bundle

#### MongoResourceFactory
**File:** `org.eclipse.fennec.codec.mongo/src/org/eclipse/fennec/codec/mongo/resource/MongoResourceFactory.java:27-46`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name= "MongoRF",
    service = Resource.Factory.class,
    property = { EMFNamespaces.EMF_CONFIGURATOR_NAME + "=mongo",
                EMFNamespaces.EMF_MODEL_PROTOCOL + "=mongodb" })
public class MongoResourceFactory extends ResourceFactoryImpl {

    @Reference
    MongoDatabaseProvider provider;

    @Reference
    private CodecModelInfo modelInfo;

    @Reference(target="(type=mongo)")
    private ObjectMapperConfigurator objMapperConfigurator;

    @Reference(target="(type=mongo)")
    private CodecModuleConfigurator codecModuleConfigurator;
}
```

**Required Changes:**
- Remove `@Component`, `@Reference` annotations
- Replace `MongoDatabaseProvider` (Gecko OSGi-specific) with standard MongoDB driver APIs
- **External Dependency:** `org.gecko.mongo.osgi.MongoDatabaseProvider` must be replaced with `com.mongodb.client.MongoDatabase` or similar

---

#### MongoCodecFactoryConfigurator
**File:** `org.eclipse.fennec.codec.mongo/src/org/eclipse/fennec/codec/mongo/codec/MongoCodecFactoryConfigurator.java:33-42`

**Current OSGi Pattern:**
```java
@Component(name="MongoCodecFactoryConfigurator",
    service = CodecFactoryConfigurator.class,
    property = "type=mongo",
    configurationPolicy = ConfigurationPolicy.REQUIRE)
public class MongoCodecFactoryConfigurator extends AbstractCodecFactoryConfigurator {

    @Activate
    public void activate(Map<String, Object> properties) {
        initialize(properties);
        setParserFactory(new MongoParserFactory());
        setGenFactory(new MongoGeneratorFactory());
    }
}
```

**Required Changes:**
- Remove `@Component`, `@Activate` annotations
- Replace with explicit initialization method or constructor
- Configuration via properties file

---

### 1.5 org.eclipse.fennec.codec.csv Bundle

#### CSVResourceFactory
**File:** `org.eclipse.fennec.codec.csv/src/org/eclipse/fennec/codec/csv/resource/CSVResourceFactory.java:31-51`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "CSVRF",
    service = {Resource.Factory.class, CSVResourceFactory.class},
    property = {EMFNamespaces.EMF_CONFIGURATOR_NAME + "=CodecCSV",
                EMFNamespaces.EMF_MODEL_FILE_EXT + "=csv",
                EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/csv"})
public class CSVResourceFactory extends ResourceFactoryImpl {

    @Reference
    private CodecModelInfo modelInfo;

    @Reference(target="(type=csv)")
    private ObjectMapperConfigurator objMapperConfigurator;

    @Reference(target="(type=json)")  // Note: uses json module configurator
    private CodecModuleConfigurator codecModuleConfigurator;
}
```

**Required Changes:**
- Same pattern as JsonResourceFactory

---

#### CSVCodecFactoryConfigurator
**File:** `org.eclipse.fennec.codec.csv/src/org/eclipse/fennec/codec/csv/factory/CSVCodecFactoryConfigurator.java:30-49`

**Current OSGi Pattern:**
```java
@Component(immediate = true, service = CodecFactoryConfigurator.class,
    property = {"type=csv"})
public class CSVCodecFactoryConfigurator extends AbstractCodecFactoryConfigurator {

    @Activate
    public void activate(Map<String, Object> properties) {
        initialize(properties);
        setParserFactory(new CSVParserFactory());
    }
}
```

**Required Changes:**
- Same pattern as MongoCodecFactoryConfigurator

---

### 1.6 org.eclipse.fennec.codec.jsonschema Bundle

#### JsonSchemaResourceFactory
**File:** `org.eclipse.fennec.codec.jsonschema/src/org/eclipse/fennec/codec/jsonschema/resource/JsonSchemaResourceFactory.java:34-57`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "JsonSchemaRF",
    service = {Resource.Factory.class, JsonSchemaResourceFactory.class},
    property = {EMFNamespaces.EMF_CONFIGURATOR_NAME + "=CodecJson",
                EMFNamespaces.EMF_MODEL_FILE_EXT + "=json",
                EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/schema+json"})
public class JsonSchemaResourceFactory extends ResourceFactoryImpl {

    @Reference
    private CodecModelInfo modelInfo;

    private ObjectMapperConfigurator objMapperConfigurator;
    private CodecModuleConfigurator codecModuleConfigurator;

    @Activate
    public void activate() {
        codecModuleConfigurator = new JsonSchemaCodecModuleConfiguarator();
        objMapperConfigurator = new JsonSchemaObjectMapperConfigurator();
    }
}
```

**Required Changes:**
- Remove `@Component`, `@Reference`, `@Activate` annotations
- Move initialization to constructor
- Add setter for `modelInfo`

---

#### JsonSchemaCodecEMFSerializers
**File:** `org.eclipse.fennec.codec.jsonschema/src/org/eclipse/fennec/codec/jsonschema/JsonSchemaCodecEMFSerializers.java:30`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "JsonSchemaCodecEMFSerializers",
    service = CodecEMFSerializers.class)
public class JsonSchemaCodecEMFSerializers extends DefaultCodecEMFSerializers
```

**Required Changes:**
- Remove `@Component` annotation

---

#### JsonSchemaCodecEMFDeserializers
**File:** `org.eclipse.fennec.codec.jsonschema/src/org/eclipse/fennec/codec/jsonschema/JsonSchemaCodecEMFDeserializers.java:34`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "JsonSchemaCodecEMFDeserializers",
    service = CodecEMFDeserializers.class)
public class JsonSchemaCodecEMFDeserializers extends DefaultCodecEMFDeserializers
```

**Required Changes:**
- Remove `@Component` annotation

---

### 1.7 org.eclipse.fennec.codec.ecowitt Bundle

#### EcoWittResourceFactory
**File:** `org.eclipse.fennec.codec.ecowitt/src/org/eclipse/fennec/codec/ecowitt/resource/EcoWittResourceFactory.java:31-51`

**Current OSGi Pattern:**
```java
@Component(immediate = true, name = "EcowittRF",
    service = {Resource.Factory.class, EcoWittResourceFactory.class},
    property = {EMFNamespaces.EMF_CONFIGURATOR_NAME + "=CodecEcoWitt",
                EMFNamespaces.EMF_MODEL_FILE_EXT + "=ecowitt",
                EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/ecowitt"})
public class EcoWittResourceFactory extends ResourceFactoryImpl
```

**Required Changes:**
- Same pattern as JsonResourceFactory

---

## 2. Configuration Mechanism Changes

### Current: OSGi ConfigurationAdmin

All configurators use `@Component(configurationPolicy = ConfigurationPolicy.REQUIRE)` which requires OSGi ConfigurationAdmin service.

**Example Configuration Objects:**
- `CodecModuleConfig` (used in DefaultCodecModuleConfigurator)
- `Map<String, Object> properties` (used in various activators)

### Replacement Options

#### Option 1: Properties Files
```java
// Load from classpath
Properties props = new Properties();
props.load(getClass().getResourceAsStream("/codec.properties"));

CodecModuleConfig config = CodecModuleConfig.fromProperties(props);
```

#### Option 2: Builder Pattern
```java
CodecModuleConfig config = CodecModuleConfig.builder()
    .idOnTop(true)
    .codecModuleName("json")
    .serializeDefaultValue(false)
    .build();
```

#### Option 3: Environment Variables
```java
CodecModuleConfig config = CodecModuleConfig.fromEnvironment();
```

### Configuration Properties to Support

Based on `DefaultCodecModuleConfigurator.java:52-72`:
- `idOnTop`
- `codecModuleName`
- `type`
- `idFeatureAsPrimaryKey`
- `proxyKey`
- `refKey`
- `serializeSuperTypesAsArray`
- `serializeDefaultValue`
- `serializeEmptyValue`
- `serializeNullValue`
- `serializeIdField`
- `serializeSuperTypes`
- `serializeAllSuperTypes`
- `serializeType`
- `timestampKey`
- `useId`
- `useNamesFromExtendedMetaData`
- `superTypeKey`

---

## 3. OSGi API Dependencies to Replace

### 3.1 org.osgi.service.component.ComponentServiceObjects

**Used In:**
- `CodecInfoHolderHelper.java:41`
- `URIReader.java:33`
- `EClassReaderByName.java`
- `EClassReaderByQualifiedName.java`

**Purpose:** OSGi prototype-scoped service instances (get/unget pattern)

**Replacement:**
```java
// Custom factory interface
public interface ResourceSetFactory {
    ResourceSet createResourceSet();
}

// Or use Java 8 Supplier
Supplier<ResourceSet> resourceSetSupplier;
```

---

### 3.2 org.gecko.emf.osgi.* APIs

**Used In:**
- All ResourceFactory classes (EMFNamespaces constants)
- `CodecModelInfoImpl.java:61` (EPackageConfigurator)

**Dependencies:**
- `org.gecko.emf.osgi.constants.EMFNamespaces` - Defines OSGi service properties
- `org.gecko.emf.osgi.configurator.EPackageConfigurator` - OSGi-based EPackage registration

**Replacement:**
- Remove service property constants
- Replace EPackageConfigurator with direct EPackage registration:
  ```java
  modelInfo.registerEPackage(ePackage);
  ```

---

### 3.3 org.gecko.mongo.osgi.MongoDatabaseProvider

**Used In:**
- `MongoResourceFactory.java:32`

**Purpose:** OSGi service for MongoDB connection management

**Replacement:**
- Use standard MongoDB Java Driver:
  ```java
  // Constructor injection
  public MongoResourceFactory(MongoDatabase database) {
      this.database = database;
  }

  // Or connection string
  MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
  MongoDatabase database = mongoClient.getDatabase("mydb");
  ```

---

## 4. Service Registry Pattern Changes

### Current: OSGi Declarative Services

Components automatically registered via `@Component(service = Foo.class)`

### Replacement: Manual Registration or Dependency Injection Framework

#### Option 1: Manual Factory/Registry
```java
public class CodecServiceRegistry {
    private static CodecModelInfo modelInfo;
    private static Map<String, ResourceFactory> resourceFactories = new HashMap<>();

    public static void initialize() {
        // Create core services
        CodecInfoHolderHelper helper = new CodecInfoHolderHelper(resourceSetFactory);
        modelInfo = new CodecModelInfoImpl(helper);

        // Create configurators
        CodecEMFSerializers serializers = new DefaultCodecEMFSerializers();
        CodecEMFDeserializers deserializers = new DefaultCodecEMFDeserializers();

        CodecModuleConfig config = loadConfig();
        DefaultCodecModuleConfigurator moduleConfigurator = new DefaultCodecModuleConfigurator(config);
        moduleConfigurator.setSerializers(serializers);
        moduleConfigurator.setDeserializers(deserializers);
        moduleConfigurator.initialize();

        // Create resource factories
        JsonResourceFactory jsonRF = new JsonResourceFactory();
        jsonRF.setModelInfo(modelInfo);
        jsonRF.setCodecModuleConfigurator(moduleConfigurator);
        jsonRF.setObjectMapperConfigurator(objMapperConfigurator);

        resourceFactories.put("json", jsonRF);

        // Register with EMF
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("json", jsonRF);
    }

    public static CodecModelInfo getModelInfo() { return modelInfo; }
}
```

#### Option 2: Spring Framework
```java
@Configuration
public class CodecConfiguration {

    @Bean
    public CodecInfoHolderHelper codecInfoHolderHelper(ResourceSetFactory rsFactory) {
        return new CodecInfoHolderHelper(rsFactory);
    }

    @Bean
    public CodecModelInfo codecModelInfo(CodecInfoHolderHelper helper) {
        CodecModelInfoImpl modelInfo = new CodecModelInfoImpl();
        modelInfo.setCodecInfoHolderHelper(helper);
        modelInfo.initialize();
        return modelInfo;
    }

    @Bean
    public DefaultCodecModuleConfigurator codecModuleConfigurator(
            @Value("${codec.config}") CodecModuleConfig config,
            CodecEMFSerializers serializers,
            CodecEMFDeserializers deserializers) {
        DefaultCodecModuleConfigurator configurator = new DefaultCodecModuleConfigurator(config);
        configurator.setSerializers(serializers);
        configurator.setDeserializers(deserializers);
        configurator.initialize();
        return configurator;
    }

    // etc...
}
```

#### Option 3: Lightweight DI (Guice, etc.)
```java
public class CodecModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CodecInfoHolderHelper.class).toInstance(new CodecInfoHolderHelper(resourceSetFactory));
        bind(CodecModelInfo.class).to(CodecModelInfoImpl.class).in(Singleton.class);
        bind(CodecEMFSerializers.class).to(DefaultCodecEMFSerializers.class);
        // etc...
    }
}
```

---

## 5. Build System Changes

### Current: OSGi Bundle Metadata (bnd.bnd files)

Example from `org.eclipse.fennec.codec/bnd.bnd`:
```
-library: enable-emf
Bundle-Name: Eclipse Fennec Codec
Bundle-Description: Eclipse Fennec Project Codec Framework

-buildpath: \
    org.osgi.util.promise;version=latest,\
    org.eclipse.fennec.codec.info.model,\
    ...
```

### Required Changes:

1. **Remove OSGi Dependencies:**
   - Remove `org.osgi.util.promise`
   - Remove `org.osgi.service.component`
   - Remove `org.osgi.service.cm` (ConfigurationAdmin)

2. **Keep Standard Dependencies:**
   - Keep `tools.jackson.core.jackson-core`
   - Keep `tools.jackson.core.jackson-databind`
   - Keep EMF dependencies

3. **Update Build Tool:**
   - Replace BND/Gradle with standard Maven or Gradle
   - Remove OSGi manifest generation
   - Standard JAR packaging instead of OSGi bundles

---

## 6. Summary of Changes by Bundle

### org.eclipse.fennec.codec
- **Classes to Modify:** 5
  - DefaultCodecModuleConfigurator
  - DefaultCodecFactoryConfigurator
  - DefaultObjectMapperConfigurator
  - DefaultCodecEMFSerializers
  - DefaultCodecEMFDeserializers
- **Main Changes:** Remove DS annotations, add setters, replace ConfigurationAdmin

### org.eclipse.fennec.codec.info
- **Classes to Modify:** 5
  - CodecModelInfoImpl
  - CodecInfoHolderHelper
  - URIReader
  - EClassReaderByName
  - EClassReaderByQualifiedName
- **Main Changes:** Replace ComponentServiceObjects, remove dynamic service tracking
- **Critical:** ResourceSet factory pattern needed

### org.eclipse.fennec.codec.json
- **Classes to Modify:** 1
  - JsonResourceFactory
- **Main Changes:** Remove DS annotations, add setters

### org.eclipse.fennec.codec.mongo
- **Classes to Modify:** 2
  - MongoResourceFactory
  - MongoCodecFactoryConfigurator
- **Main Changes:** Replace MongoDatabaseProvider with MongoDB driver
- **External Dependency:** Must add standard MongoDB Java driver

### org.eclipse.fennec.codec.csv
- **Classes to Modify:** 2
  - CSVResourceFactory
  - CSVCodecFactoryConfigurator
- **Main Changes:** Remove DS annotations, add setters

### org.eclipse.fennec.codec.jsonschema
- **Classes to Modify:** 3
  - JsonSchemaResourceFactory
  - JsonSchemaCodecEMFSerializers
  - JsonSchemaCodecEMFDeserializers
- **Main Changes:** Remove DS annotations

### org.eclipse.fennec.codec.ecowitt
- **Classes to Modify:** 1
  - EcoWittResourceFactory
- **Main Changes:** Remove DS annotations, add setters

---

## 7. Recommended Migration Strategy

1. **Phase 1: Remove OSGi Annotations**
   - Remove all `@Component`, `@Reference`, `@Activate` annotations
   - Convert to POJOs with explicit setters

2. **Phase 2: Replace OSGi APIs**
   - Replace `ComponentServiceObjects` with factory pattern
   - Replace Gecko EMF/Mongo OSGi services with standard APIs

3. **Phase 3: Implement Configuration**
   - Choose configuration approach (properties/builder/env vars)
   - Implement `CodecModuleConfig` loading mechanism

4. **Phase 4: Create Service Registry**
   - Choose DI approach (manual/Spring/Guice)
   - Implement initialization code

5. **Phase 5: Update Build**
   - Migrate from BND to standard build
   - Update dependencies

6. **Phase 6: Testing**
   - Port tests to non-OSGi environment
   - Validate all functionality

---

## 8. Total Impact

- **Bundles to Modify:** 7
- **Classes to Modify:** ~19
- **OSGi-Specific APIs to Replace:** 3
  - ComponentServiceObjects → Factory pattern
  - Gecko EMF OSGi → Direct registration
  - Gecko Mongo OSGi → MongoDB driver
- **Configuration Migration:** ConfigurationAdmin → Properties/Builder
- **Service Management:** DS → Manual/DI framework
