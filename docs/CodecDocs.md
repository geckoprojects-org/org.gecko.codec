# Codec 

## The Aim of the Project

The goal of the *codec* project is to have a common framework to be used underneath when saving or loading a `Resource`, no matter if we want to use a json file, a mongo db, a Lucene document or whatever.

Having a shared system underneath that provides the functionality to actually save and load an `EObject` will make it easier, when it comes to add a new persistence mechanism, for instance.

The idea is that the final user would have only to care about the actual persistence-related stuff (e.g. if we want to work with mongo we would need to implement the layer that talks to our general framework using the specific of mongo, etc.).

## What we use

We use `jackson` under the hood, and we tried to expose as much functionalities as possible, in such a way one has control over the majority of the options there. In addition to that we also added some more options, to be able to customize the serialization/deserialization process as much as possible.

The idea is to have some default configuration and then the user should be allowed to overwrite it at the level of the single save/load operation, by passing the corresponding options.

## How does it work?

Everything starts with 3 configurators: 

+ `CodecFactoryConfigurator`: responsible for setting up a `CodecFactory`
+ `ObjectMapperConfigurator`: responsible for setting up an `ObjectMapper`
+ `CodecModuleConfigurator`: responsible for setting up a `CodecModule`

These configurators are just service providers, and we have a default implementation for all three of them. The implementations are configurable services, meaning that they can be immediately configured in such a way to have the desired basic behavior which should be applied in the general case. Further customization at the level of the single save/load operation can then be set through the loading/saving options, which can then overwrite what has been previously set through the configurations.

Let's have a look now at the details.

### `CodecFactoryConfigurator`

Through this service one can set up the properties of the `JsonFactory`, such as:

+ `TokenStreamFactory.Feature`
+ `StreamWriteFeature`
+ `JsonWriteFeature`
+ `StreamReadFeature`
+ `JsonReadFeature`

The configuration accepts the following properties:

+ `enableFeatures` 
+ `disableFeatures`

whose values should be arrays of `String` with the names of the properties we want to enable or disable. One can
specify the property either with the full name (e. g. `TokenStreamFactory.Feature.CANONICALIZE_FIELD_NAMES`) or just with the simple name (e.g. `CANONIALIZE_FIELD_NAMES`). If the prefix was specified only that specific feature will be enabled/disabled, otherwise all features with the same name will be enabled/disabled (e.g. features common to both serialization and deserialization). The default values of all these properties are the ones reported in the `jackson` documentation.

Another property to be set in the configuration is:

+ `type` 

This is set to `json` in the default implementation and specifies the type of implementation we are going
to use. It's a useful property to keep track of the type of the implementation you want to use and can be used also to inject corresponding services in other components.

The other two options that can be set through configuration are:

+ `genFactory.target`
+ `parserFactory.target`

These take as value a filter like `(type = XXXX)`, where you can specify the type of `CodecGeneratorFactory` and `CodecParserFactory` you want to inject. These are **optional** references; if none is found a default `DefaultCodecJsonFactory` will be created, otherwise a `CodecFactory` with the injected generator/parser factories will be created.

The service provider is defined in `org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator`, while our default implementation is in `org.eclipse.fennec.codec.jackson.DefaultCodecFactoryConfigurator`.

### `ObjectMapperConfigurator`

Through this service is possible to configure properties that belong to the `ObjectMapper`: 

+ `MapperFeature`
+ `SerializationFeature`
+ `DeserializationFeature`
+ `StreamWriteFeature`
+ `JsonWriteFeature`
+ `StreamReadFeature`
+ `JsonReadFeature`
+ `DateTimeFeature`
+ `EnumFeature`

One can enable/disable a feature via configuration, using the properties:

+ `enableFeatures` 
+ `disableFeatures`, 

which work as the corresponding ones we saw for the `CodecFactoryConfigurator`. 

In addition to that, one can set:

+ `dateFormat` (default is "yyyy-MM-dd'T'HH:mm:ss")
+ `locale` (default is `en-US`)
+ `timeZone` (default is "Europe/Berlin")
+ `type` (default `json`, same meaning as for the `CodecFactoryConfigurator`)

When using our default implementations, the `CodecFactoryConfigurator` is injected in the `ObjectMapperConfigurator` so one has to specify the right one to use, with the configuration property 

+ `codecFactoryConfigurator.target` (takes a filter, e. g. ` (type = XXXX)`).

The service provider is defined in `org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator`, while our default implementation is in `org.eclipse.fennec.codec.jackson.DefaultObjectMapperConfigurator`.

> [!WARNING]
>
> In `jackson < 3.` calling `ObjectMapper.Builder.build`  does **NOT** return a new `ObjectMapper`, we added an intermediate `ObjectMapperBuilderFactory` 
>
> ```java
> public interface ObjectMapperBuilderFactory {
> 	Builder createObjectMapperBuilder();
> }
> ```
>
> which is called from our `DefaultObjectMapperConfigurator`. In this way we are getting a new `ObjectMapper.Builder` when we want to configure the `ObjectMapper` and so a new instance of the `ObjectMapper` itself when loading/saving a Resource.

### `CodecModuleConfigurator`

The service provider is defined in `org.eclipse.fennec.codec.configurator.CodecModuleConfigurator`, while our default implementation is in `org.eclipse.fennec.codec.jackson.module.DefaultCodecModuleConfigurator`.

This service is responsible for setting up the `CodecModule.Builder`. Through the configuration (`org.eclipse.fennec.codec.configurator.CodecModuleConfig`) one can set:

+ `superTypeKey`: to instruct which keyword to use when serializing the supertype. Default is `_supertype`;

+ `refKey`: to instruct which keyword to use when serializing the reference. Default is `$ref`

+ `proxyKey` to instruct which keyword to use when serializing proxies. In the default implementation is `_proxy` (**CURRENTLY NOT USED**);

+ `timestampKey`: to instruct which keyword to use when serializing timestamps. In the default implementation is `_timestamp` (**CURRENTLY NOT USED**);

+ `serializeDefaultValue`: to instruct the module to serialize default attributes values. Default is `FALSE`;

+ `serializeEmptyValue`: to instruct the module weather to serialize or not empty values. This refers to empty lists or one dimensional arrays, and to empty `String`. Default value is `FALSE`;

+ `serializeNullValue`: to instruct the module weather to serialize or not `null` values. This refers to `null` lists or one dimensional arrays, and to `null` objects. Default value is `FALSE`;

+ `useNamesFromExtendedMetaData`: to specify weather or not to use the name set to through the `EXTENDED_META_DATA` annotation, instead of the `EStructuralFeature` name. Default value is `FALSE`;

+ `useId`: option used to instruct the module to serialize the id information.  Default value is `TRUE`;

+ `idOnTop`: option used to instruct the module on weather the id information should be serialized first or not. If set to `FALSE`, the id information is serialized after the type information. Default value is `TRUE`;

+ `serializeIdField`: option used to instruct the module to additionally serialize the id field of an `EObject` as it is. This might be superfluous when using as id strategy the one that uses the id field itself, but it might be useful when the id strategy is set to COMBINED. In our default implementation the default value is `FALSE`;

+ `idFeatureAsPrimaryKey`: if it is set to `Boolean.TRUE` the value of the ID information will be used as the primary key if it exists. Default value is `TRUE`;

+ `writeEnumLiterals`: option to specify weather `Enumerator` values should be serialized by literals or by name. When deserializing the option should be consistent with what was used during serialization (as it should always be the case). If, for whatever reason, it is not, then the deserialization mechanism will fall back and try to deserialize in both ways.  Default is `FALSE`, namely enumerators are serialized by name.

+ `serializeType`: option used to instruct the module weather to serialize the type information or not. Default is `TRUE`;

+ `serializeSuperTypes`: option used to instruct the module weather to serialize the supertype information or not. If this is set to TRUE but `serializeType` is set to FALSE, then this option is ignored. If this is set to TRUE, only the direct parent will be listed as supertype. For the whole inheritance chain, look at `serializeAllSuperTypes` option.  Default is `Boolean.FALSE`;

+ `serializeAllSuperTypes`: option used to instruct the module weather to serialize the whole chain of inheritance when serializing supertype information. This option is ignored if either `serializeSuperTypes` or `serializeType` is set to `FALSE`. Default value is `FALSE`;

+ `serializeSuperTypesAsArray`: option used to specify weather the supertype information should be written in the form of a `String` array. If set to `FALSE`, then a comma separated `String` is used. This option is ignored if either `serializeSuperTypes` or `serializeType` is set to `FALSE`. Default value is `TRUE`.

  

Our `CodecModule`, which is then built through the `CodecModule.Builder`, is an extension of the `tools.jackson.databind.module.SimpleModule`, which allows to set the additional configured properties and to overwrite the `tools.jackson.databind.module.SimpleModule#setupModule` method, which is where we add our serializers/deserializers. 

### The `CodecModelInfo`

Another key ingredient is the `org.eclipse.fennec.codec.info.CodecModelInfo` service. This is responsible for creating the `PackageCodecInfo` whenever a new `EPackage` is registered. The `PackageCodeInfo` is defined in the `org.eclipse.fennec.codec.info.model`. For every `Eclassifier`, it contains info about codec annotations that might have been used (e.g, to specify the id strategy on to mark a feature as transient). This info will be then used and merged with the options passed to save/load a resource. 

There are several codec model annotations currently supported, which are defined in `org.eclipse.fennec.codec.options.CodecAnnotations`:

+ `CODEC_INHERIT`: annotation at the `EClassifier` level for specifying that codec annotations on the direct parent should be inherited, even if the parent comes from another `EPackage`. By default only if the parent belongs to the same `EPackage` then the codec annotations are inherited.

+ `CODEC_TRANSIENT`: annotation at the `EStructuralFeature` level, for specifying that the feature should not be serialized. 

+ `CODEC_ID`:  Annotation to specify, at the level of an EClass how to treat the id field during (de-)serialization. The details map may contain:

  ​	  - `key`: the property name to be used when serializing the id info

  ​	  - `strategy`: either `ID_FIELD` or `COMBINED`

  ​	  - `separator`: when the strategy is `COMBINED` we can specify with this property the separator character to be used

  ​	  - `idValueReaderName`: a name for a `CodecValueReader` to be used when deserializing the id info

  ​	  - `idValueWriterName`: a name for a `CodecValueWriter` to be used when serializing the id info

  ​	  - `idFeatures`: a comma separated String, with the URI of the features to be used as id 

+ `CODEC_TYPE`: annotation used for specifying how to treat the type information of the object marked this way. This annotation can be either put at the `EClass` level or at the `EReference` level. The details map keys are:

  ​	 - `strategy`: to specify a strategy for the type value reader/writer (supported are NAME, CLASS, URI)

  ​	 - `include`: to specify weather the type information should be considered or not (default is true)

  ​	  - `typeKey`: the String to be looked for retrieving the type of the object and the property name to be used when serializing the type (default is `_type`)

  ​	 - `typeValueWriterName`: a name for a CodecValueWriter to serialize the type info

  ​	 - `typeValueReaderName`: a name for a CodecValueReader to deserialize the type info

  ​	 - additional <key, value> pairs in the details map should represent string to look for when deserializing the object, to decide which type of object it is.

+ `CODEC_VALUE_WRITER_NAME` annotation at the `EStructuralFeature` level, to specify the name for the `CodecValueWriter` that should be used when serializing that feature. The actual `CodecValueWriter` object should then be one of the automatically registered ones (see the paragraph on `CodecValueWriter/Reader`) or should be passed through the options when saving a Resource. 

+ `CODEC_VALUE_READER_NAME`: same as `CODEC_VALUE_WRITER_NAME` but for deserialization.

#### `CodecValueWriter` and `CodecValueReader`

The automatically registered `CodecValueWriter` and `CodecValueReader` are defined in `org.eclipse.fennec.codec.info.helper.CodecIOHelper`. They are registered based on the type of the `CodecModelInfo`, so not all of them are automatically available for all the `CodecModelInfo`. In particular: 

+ For the ID field: `CodecIOHelper.DEFAULT_ID_VALUE_READER`, `CodecIOHelper.IDFIELD_VALUE_WRITER`, `CodecIOHelper.DEFAULT_ID_VALUE_WRITER`;
+ For the TYPE field: `CodecIOHelper.DEFAULT_ECLASS_READER`, `CodecIOHelper.READ_BY_NAME`, `CodecIOHelper.READ_BY_CLASS`, `CodecIOHelper.URI_WRITER`, `CodecIOHelper.WRITE_BY_NAME`, `CodecIOHelper.WRITE_BY_CLASS_NAME`;
+ For the SUPERTYPE: `CodecIOHelper.ALL_SUPERTYPE_WRITER`, `CodecIOHelper.SINGLE_SUPERTYPE_WRITER`.

> [!WARNING]
>
> For the supertype there is currently **NO** possibility to set a different `CodecValueWriter` or `CodecValueReader`.

### The `CodecResource`

The `CodecResource` is an extension of the `org.eclipse.emf.ecore.resource.impl.ResourceImpl`, which allows to combine what has been previously configured (the `CodecFactory`, the `ObjectMapper`, the `CodecModule` and the `CodecModelnfo`), with what the final user actually needs when serializing and deserializing a resource.
Through the options one can use when saving/loading, it is possible to overwrite most of the properties previously configured, allowing to have a general setup for e. g. a `json` serialization and, at the same time, the possibility
to change some default behavior. After, and **only after that**, the `ObjectMapper` and the `CodecModule` are actually created from the respective builders and bound together.

The options one can set when saving/loading a Resource so to overwrite the default behavior can be divided in three main categories:

+ `ObjectMapperOptions`: to overwrite options set via the `ObjectMapperConfigurator`;
+ `CodecModuleOptions`: to overwrite options set via the `CodecModuleConfigurator`;
+ `CodecModelInfoOptions`: to overwrite options set via annotation in the EMF model by the `CodecModelInfo` service.

#### `ObjectMapperOptions`

These are defined in `org.eclipse.fennec.codec.constants.ObjectMapperOptions` and are:

+ `OBJ_MAPPER_DATE_FORMAT`: to overwrite the `dateFormat` property of the `ObjectMapperConfigurator`;
+ `OBJ_MAPPER_LOCALE`: to overwrite the `locale` property of the `ObjectMapperConfigurator`;
+ `OBJ_MAPPER_TIME_ZONE`: to overwrite the `timeZone` property of the `ObjectMapperConfigurator`;
+ `OBJ_MAPPER_SERIALIZATION_FEATURES_WITH`: to specify a `List` of `tools.jackson.databind.SerializationFeature` that should be enabled;
+ `OBJ_MAPPER_SERIALIZATION_FEATURES_WITHOUT`: to specify a `List` of `tools.jackson.databind.SerializationFeature` that should be disabled;
+ `OBJ_MAPPER_DESERIALIZATION_FEATURES_WITH`: to specify a `List` of `tools.jackson.databind.DeserializationFeature` that should be enabled;
+ `OBJ_MAPPER_DESERIALIZATION_FEATURES_WITHOUT`: to specify a `List` of `tools.jackson.databind.DeserializationFeature` that should be disabled;
+ `OBJ_MAPPER_FEATURES_WITH`: to specify a `List` of `tools.jackson.databind.MapperFeature` that should be enabled;
+ `OBJ_MAPPER_FEATURES_WITHOUT`: to specify a `List` of `tools.jackson.databind.MapperFeature` that should be disabled;
+ `OBJ_MAPPER_FORMAT_SER_FEATURES_WITH`: to specify a `List` of  `tools.jackson.core.FormatFeature` for serialization that should be enabled. Since the `FormatFeature` are meant to be format specific, we currently support only `JsonWriteFeature` for this option. 
+ `OBJ_MAPPER_FORMAT_SER_FEATURES_WITHOUT`: to specify a `List` of  `tools.jackson.core.FormatFeature` for serialization that should be disabled. Since the `FormatFeature` are meant to be format specific, we currently support only `JsonWriteFeature` for this option. 
+ `OBJ_MAPPER_FORMAT_DESER_FEATURES_WITH`: to specify a `List` of  `tools.jackson.core.FormatFeature` for serialization that should be enabled. Since the `FormatFeature` are meant to be format specific, we currently support only `JsonReadFeature` for this option. 
+ `OBJ_MAPPER_FORMAT_DESER_FEATURES_WITHOUT`: to specify a `List` of  `tools.jackson.core.FormatFeature` for serialization that should be disabled. Since the `FormatFeature` are meant to be format specific, we currently support only `JsonReadFeature` for this option. 

#### `CodecModuleOptions`

These are defined in `org.eclipse.fennec.codec.constants.CodecModuleOptions` and are:

+ `CODEC_MODULE_SERIALIZE_DEFAULT_VALUE`: to overwrite the `serializeDefaultValue` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_SERIALIZE_EMPTY_VALUE`: to overwrite the `serializeEmptyValue` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_SERIALIZE_NULL_VALUE`: to overwrite the `serializeNullValue` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA`: to overwrite the `useNamesFromExtendedMetadata` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_USE_ID`: to overwrite the `useId` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_ID_ON_TOP`: to overwrite the `idOnTop` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_SERIALIZE_ID_FIELD`: to overwrite the `serializeIdField` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_ID_FEATURE_AS_PRIMARY_KEY`: to overwrite the `idFeatureAsPrimaryKey` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_SUPERTYPE_KEY`: to overwrite the `superTypeKey` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_REFERENCE_KEY`: to overwrite the `refKey` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_PROXY_KEY`: to overwrite the `proxyKey` property of the `CodecModuleConfigurator` (**NOT IMPLEMENTED CURRENTLY**);
+ `CODEC_MODULE_TIMESTAMP_KEY`: to overwrite the `timestampKey` property of the `CodecModuleConfigurator` (**NOT IMPLEMENTED CURRENTLY**);
+ `CODEC_MODULE_SERIALIZE_TYPE`: to overwrite the `serializeType` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_SERIALIZE_SUPER_TYPES`: to overwrite the `serializeSuperTypes` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_SERIALIZE_ALL_SUPER_TYPES`: to overwrite the `serializeAllSuperTypes` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_SERIALIZE_SUPER_TYPES_AS_ARRAY`: to overwrite the `serializeSuperTypesAsArray` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_WRITE_ENUM_LITERAL`: to overwrite the `writeEnumLiteral` property of the `CodecModuleConfigurator`;
+ `CODEC_MODULE_REFERENCE_DESERIALIZER`
+ `CODEC_PROXY_FACTORY`

#### `CodecModelInfoOptions`

These are defined in `org.eclipse.fennec.codec.constants.CodecModelInfoOptions` and are:

+ `CODEC_IGNORE_FEATURE_LIST`: to specify a list of `EStructuralFeature` that should be ignored during serialization or deserialization. If an `EStructuralFeature` is marked as `transient` in the model or has been annotated with the `CODEC_TRANSIENT` annotation, it will still be ignored even is it is not present in this list;
+ `CODEC_GLOBAL_IGNORE_FEATURES_LIST`: to specify a list of `EStructuralFeature` that should be ignored globally across all EClasses during serialization or deserialization. This is particularly useful for excluding features from metamodel classes (like Ecore's `eGenericType`) without having to specify them for each individual EClass. Unlike `CODEC_IGNORE_FEATURE_LIST`, this option applies to all packages being serialized, not just the root package. **Use case**: When serializing an EPackage, you may want to exclude `eGenericType` from all ETypedElements. Since `eGenericType` is always present as a wrapper around `eType` (even when no generics are used), it can be redundant. Using this option allows you to globally exclude it: `options.put(CodecModelInfoOptions.CODEC_GLOBAL_IGNORE_FEATURES_LIST, List.of(EcorePackage.Literals.ETYPED_ELEMENT__EGENERIC_TYPE))`;
+ `CODEC_IGNORE_NOT_FEATURE_LIST`:  to specify a list of `EStructuralFeature` that should **NOT** be ignored during serialization or deserialization. If an `EStructuralFeature` is marked as `transient` in the model or has been annotated with the `CODEC_TRANSIENT` annotation, it will then be taken into account if present in this list;
+ `CODEC_ID_KEY`: to overwrite the `org.gecko.codec.constants.CodecAnnotations.CODEC_ID` key detail annotation;
+ `CODEC_ID_SRATEGY`: to overwrite the `org.gecko.codec.constants.CodecAnnotations.CODEC_ID` strategy detail annotation;
+ `CODEC_ID_SEPARATOR`: to overwrite the `org.gecko.codec.constants.CodecAnnotations.CODEC_ID` separator detail annotation;
+ `CODEC_ID_FEATURES_LIST`: to specify an ordered list of `EStructuralFeature` to be used when constructing the id, if the id strategy is set to COMBINED. Otherwise it will be ignored.
+ `CODEC_ID_VALUE_WRITER`:  to specify a `CodecValueWriter` to be used when serializing the id information; 
+ `CODEC_ID_VALUE_READER`:  to specify a `CodecValueReader` to be used when deserializing the id information; 
+ `CODEC_TYPE_VALUE_WRITER`: to specify a `CodecValueWriter` to be used when serializing the type information; 
+ `CODEC_TYPE_VALUE_READER`: to specify a `CodecValueReader` to be used when deserializing the type information; 
+ `CODEC_VALUE_WRITERS_MAP`: a Map, where the keys are of type `EStructuralFeature` and the values are of type `CodecValueWriter`, to specify the `CodecValueWrtier` to use when serializing the corresponding `EStructuralFeautre`;
+ `CODEC_VALUE_READERS_MAP`: a Map, where the keys are of type `EStructuralFeature` and the values are of type `CodecValueReader`, to specify the `CodecValueReader` to use when deserializing the corresponding `EStructuralFeautre`;
+ `CODEC_TYPE_STRATEGY`:  to overwrite the `strategy` detail of the  `org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE` annotation;
+ `CODEC_TYPE_INCLUDE`:  to overwrite the `include` detail of the  `org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE` annotation;. If the `CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE` is set to `FALSE` then this option is ignored, even if set to `TRUE`;
+ `CODEC_TYPE_KEY`: to overwrite the `key` detail of the  `org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE` annotation;
+ `CODEC_TYPE_MAP`: to overwrite or merge (depending on the `CODEC_TYPE_MAP_STRATEGY` option) the type mapping defined via model annotation;
+ `CODEC_TYPE_MAP_STRATEGY`: the strategy to use when there is a type mapping via options and one via annotations. Default behaviour is that the one from the options overwrites the one from the model annotation. 
+ `CODEC_TYPE_INFO`: to directly overwrite the entire `TypeInfo` object that is created out of the `org.gecko.codec.constants.CodecAnnotations.CODEC_TYPE` annotation.
+ `CODEC_CUSTOM_VALUE_READER`: to specify a custom `CodecValueReader` to be used during deserialization of the object marked with that
+ `CODEC_CUSTOM_VALUE_WRITER`: to specify a custom `CodecValueWriter` to be used during serialization of the object marked with that.
+ `CODEC_EXTRAS`: option to pass some extra properties. It accepts a Map of <property, value> pairs. This is used for instance in the `jsonschema` case.

As these options can be different for different `EClass`, when saving/loading a Resource, one should actually create a Map, where the keys are the `EClass` and the values are the options for that `EClass`. The Map then should be passed via the saving/loading options with the key `org.eclipse.fennec.codec.constants.CodecResourceOptions.CODEC_OPTIONS`.

```java
Map<String, Object> options = new HashMap<>();
Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
Map<String, Object> personOptions = new HashMap<>();

personOptions.put(CodecModelInfoOptions.CODEC_ID_STRATEGY, "ID_FIELD");
classOptions.put(PersonPackage.eINSTANCE.getPerson(), personOptions);
options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
resource.save(options);
```

In addition to all these options, when deserializing, the  `org.eclipse.fennec.codec.constants.CodecResourceOptions.CODEC_ROOT_OBJECT` option should be passed. This accepts as value the `EClass` of the root object that has to be deserialized. This option is **MANDATORY** if there is no type information in the document to be read.  

To make things easier, there is also a `org.eclipse.fennec.codec.options.CodecOptionsBuilder` to create the option `Map`. So, the same option map as the previous example can also be achieved like:

```java
Map<String, Object> options = CodecOptionsBuilder
    .create()
    .forClass(PersonPackage.eINSTANCE.getPerson())
    .idStrategy("ID_FIELD")
    .build();
```

#### Example: Excluding eGenericType Globally

When serializing an EPackage, `eGenericType` is often redundant since it's always present as a wrapper around `eType`, even when no generics are used. To exclude it globally across all EClasses:

```java
Map<String, Object> options = new HashMap<>();
options.put(CodecModelInfoOptions.CODEC_GLOBAL_IGNORE_FEATURES_LIST,
    List.of(EcorePackage.Literals.ETYPED_ELEMENT__EGENERIC_TYPE));

Resource resource = resourceSet.createResource(URI.createURI("my-package.json"));
resource.getContents().add(myEPackage);
resource.save(options);
```

This approach ensures that `eGenericType` is excluded from all ETypedElements (EAttributes, EReferences, EOperations) regardless of which package they belong to.



### The Serialization/Deserialization Mechanism

The actual serialization/deserialization process starts when the `CodecModule` and the `ObjectMapper` are created, after the saving/loading options have been taken into account and merged to the previously configured options.

At this point the `CodecModule#setupModule` method is called. This inherits from `tools.jackson.databind.module.SimpleModule#setupModule(tools.jackson.databind.Module.SetupContext)`, and it is where the module registers the serializers and deserializers.

In particular, we are overwriting the `tools.jackson.databind.ser.Serializers.Base` and `tools.jackson.databind.ser.Deserializers.Base` behavior, in case an `EObject` is found to be saved/loaded. In such case, indeed, we are redirecting the flow to take our corresponding `CodecEObjectSerializer` and `CodecEObjectDeserializer`.

### `CodecEObjectSerializer`

The `CodecEObjectSerializer` is defined in `org.eclipse.fennec.codec.jackson.databind.ser` and it extends the `tools.jackson.databind.ValueSerializer`.

When its `serialize` method is called, the first thing it does is retrieving the corresponding `PackageCodecInfo` from the `CodecModule` and extracting from that the `EClassCodecInfo` which corresponds to the `EObject` it wants to write.

Then the `JsonGenerator` starts writing the object, and, based on the `CodecModule` options, calls the corresponding `CodecInfoSerializer` for the various `CodecInfo` objects (id, type, features, etc).

The actual type of the `JsonGenerator` here depends on the `CodecGeneratorFactory` that has been used when constructing the `JsonFactory`. 

The `CodecInfoSerializer` type is defined in  `org.eclipse.fennec.codec.jackson.databind.ser.CodecInfoSerializer` and is just an interface which provide a 

```java
void serialize(EObject rootObj, JsonGenerator gen, SerializationContext provider);
```

method.

There are then several implementations for that:

+ `IdCodecInfoSerializer`: responsible for writing the id information;
+ `TypeCodecInfoSerializer`: responsible for writing the type information;
+ `SuperTypeCodecInfoSerializer`: responsible for writing the supertype information;
+ `FeatureCodecInfoSerializer`: responsible for writing `EAttribute`;
+ `ReferenceCodecInfoSerializer`: responsible for writing containment and non containment `EReference`;
+ `EnumeratorCodecInfoSerializer`: responsible for writing enumerators;
+ `OperationCodecInfoSerializer`: responsible for writing `EOperation`.

### `CodecEObjectDeserializer`

The `CodecEObjectDeserializer` is defined in `org.eclipse.fennec.codec.jackson.databind.deser` and it extends the `tools.jackson.databind.ValueDeserializer`.

When an `EObject` has to be read, the first thing it tries to do is to get the type information from the document, so it can construct the right object. This is done in several ways:

+ Look for the `org.eclipse.fennec.codec.constants.CodecResourceOptions.CODEC_ROOT_OBJECT`: this is mandatory in case the type information is not present in the document to be read. In case of root object then we might get the information directly from here. 
+ Look for the type of the current attribute from the `DeserializationContext`: this is available in case of contained references;
+ Look for the type keyword in the document: if the type information has been serialized this should always bring to a result;

Once the right object has been built, we can go through the other features and call the corresponding `CodecInfoDeserializer`.

The `CodecInfoDeserializer` is defined in `org.eclipse.fennec.codec.jackson.databind.deser` and is just an interface with one method: 

```java
public void deserializeAndSet(JsonParser jp, EObject current, DeserializationContext ctxt, Resource resource);
```

The implementations are:

+ `FeatureCodecInfoDeserializer`: responsible for reading attributes, containment and non containment references, enumerators;
+ `ReferenceCodecInfoDeserializer`: it is called from the `FeatureCodecInfoDeserializer` in order to perform the actual deserialization of non containment references.

### The Layer in Between

What allows to use this general setup for multiple persistence mechanisms is a layer in between that is formed, on the serialization side, from the `org.eclipse.fennec.codec.jackson.databind.ser.CodecGeneratorBaseImpl`, and, on the deserialization side, from the `org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl`.

These are `abstract` classes which inherit, respectively, from `tools.jackson.core.base.GeneratorBase` and `tools.jackson.core.base.ParserBase`. Extending these classes was necessary to allow the use of an extension of the `tools.jackson.core.TokenStreamContext`. Here, due to inheritance issues, we had to proceed in two different ways, when considering pure json and when considering everything else:

+ for the `json` case we have implemented a `CodecJsonReadContext` and a `CodecJsonWriteContext`, which extend, respecitvely, the `tools.jackson.core.json.JsonReadContext` and `tools.jackson.core.json.JsonWriteContext`. 
+ for the other cases we have implemented a `CodecReadContext` and a `CodecWriteContext` which simply extend the `tools.jackson.core.TokenStreamContext`.

In our custom contexts we can store additional information, such as the current `EStructuralFeature`. In addition to that we provide default implementations for methods that write/read, which then further implementations can overwrite. 

The idea is that specific codec implementations provide their own implementations of such classes, so that they can put all the logic which belongs to the specific persistence mechanism in there, while the general framework remains the same for every implementations.

## Implementations

We currently have implemented the codec for:

+ `json`
+ `mongodb`
+ `jsonschema` (detailed documentation can be found [here](./CodecJsonSchemaSupport.md))
+ `csv`
+ `ecowitt`

For the `json` implementation we are relying on the `JsonGenerator`s and `JsonParser`s already available in jackson, while for the `mongo` implementation we are providing `org.eclipse.fennec.codec.mongo.MongoCodecGenerator` and `org.eclipse.fennec.codec.mongo.MongoCodecParser`, which are then constructed in the corresponding `MongoGeneratorFactory` and `MongoParserFactory`, injected in the `CodecFactoryConfigurator`.

> [!IMPORTANT]
>
> **Missing or Not Tested Features**
>
> + `proxyKey` and `timestampKey` are available options in the `CodecModuleConfig` but they are not currently used in the implementations of the serialization/deserialization process;
> + currently it is only possible to save the supertypes as an array or a comma separated String of URIs; it might be useful to have a strategy like we have for the type information, so one can save the supertypes also as class names or simply as names. It might also be useful to serialize them with another separator rather than a comma separated String (?)
> + We do not have any special support for jackson annotations, such as `@JsonAnyGetter`, and I do not know if this would work out of the box;
> + Annotations like `@JsonProperty` have been introduced in emfjson, so we might want a similar behaviour in our codec annotations (this `JsonProperty` for instance allows to set an alternative name to be used for serialization without the limitation of the extended meta data on some special characters);
> + ~~serialization/deserialization of Maps has not been tested and nothing special has been implemented for them, so not sure if it works out of the box with the "standard" jackson serializers or not;~~ Works for EMap
> + Our `CodecInfoSerializer` and `CodecInfoDeserializer`, from which all our "special" serializers/deserializers inherit, are **NOT** extension of `JsonSerializer` and `JsonParser`. Maybe they could be in the future and be registered like the others. 



### TODO
* Cleanup Code:

  * Error handling
  * Java and Sonar Warnings

* Tests in org.eclipse.fennec.codec/test/ remove?
* Moving gecko EMFUtil features to codec.
* License header
* CDO