# Codec: Migration to Jackson 3.0 rc2

## Package Name Changes

+ `com.fasterxml.jackson.*` --> `tools.jackson.*`
+ **ONLY EXCEPTION**: `jackson-annotations` which stays as it was

## Class Name Changes

+ `JsonSerializer/JsonDeserializer` --> `ValueSerializer/ValueDeserializer`
+ `SerializerProvider` --> `SerializationContext`
+ `JsonParseException` --> `StreamReadException`
+ `JsonMappingException` --> `DatabindException`
+ `JsonProcessingException` --> `JacksonException`
+ `ObjectCodec` --> `TreeCodec`
+ `JsonLocation`--> `TokenStreamLocation`

## Method Name Changes

+ `jp.getCurrentName()` --> `jp.currentName()`
+ `jp.getCurrentToken()` --> `jp.currentToken()`
+ `jp.getText()` --> `jp.getString()`
+ `jp.writeFieldName()` --> `jp.writeName()`
+ `jp.nextTextValue()` --> `jp.nextStringValue()`
+ `jp.writeNullField()` --> `jp.writeNullProperty()`
+ `jp.writeStringField()` --> `jp.writeStringProperty()`
+ `JsonNode#fieldsNames()` --> `JsonNode#propertyNames()`
+ `JsonNode#asText()` --> `JsonNode#asString()`
+ `com.fasterxml.jackson.databind.type.TypeFactory#defaultInstance()` --> `tool.jackson.databind.type.TypeFactory#createDefaultInstance()`
+ `new TokenBuffer(JsonParser parser)` --> `TokenBuffer.forBuffering(JsonParser parser, DeserializationContext ctxt)`
+ `JsonGenerator#wirteObjectField` --> `JsonGenerator#wirtePOJOProperty`
+ `JsonGenerator#wirteObject` --> `JsonGenerator#wirtePOJO`
+ `DeserializationContext#getCodec().getFactory().createGenerator(Writer)` --> `DeserializationContext#tokenStreamFactory().createGenerator(ObjectWriterContext.empty(), writer)`

## Configuration Feature Changes

There are a lot of features that can be configured, both at the level of the `TokenStreamFactory` and at the level of the `ObjectMapper`. There was a refactoring of those features in `jackson` 3; some changed names, some changed default value, etc. So here is a recap:

### TokenStreamFactory.Feature

+ `tools.jackson.core.TokenStreamFactory.Feature` replaces now the `com.fasterxml.jackson.core.JsonFactory.Feature`

+ Some changed name and some changed default value;

  |     jackson 2 (name + default)      |       jackson 3 (name + default)       |
  | :---------------------------------: | :------------------------------------: |
  |    `INTERN_FIELD_NAMES` (`true`)    |   `INTERN_PROPERTY_NAMES` (`false`)    |
  | `CANONICALIZE_FIELD_NAMES` (`true`) | `CANONICALIZE_PROPERTY_NAMES` (`true`) |

+ The feature `USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING` has been removed



### StreamReadFeature

+ `tools.jackson.core.StreamReadFeature` were included in `jackson 2.x` `com.fasterxml.jackson.core.JsonParser.Feature`  

+ Now they are separated because they are not json-specific;

+ These are the features that changed default value:

  

|           Feature            | Default in jackson 2.x | Default in jackson 3 |
| :--------------------------: | :--------------------: | :------------------: |
|   `USE_FAST_DOUBLE_PARSER`   |        `false`         |        `true`        |
| `USE_FAST_BIG_NUMBER_PARSER` |        `false`         |        `true`        |

### JsonReadFeature

+ `tools.jackson.core.JsonReadFeature` were mixed in jackson 2.x with non json specific features within `com.fasterxml.jackson.core.JsonParser.Feature`

+ These features changed name or default value with respect to those defined in jackson 2.x `JsonParser.Feature`:

  |       jackson 2.x (name + default)       |         jackson 3 (name + default)          |
  | :--------------------------------------: | :-----------------------------------------: |
  |        `ALLOW_COMMENTS` (`false`)        |       `ALLOW_JAVA_COMMENTS` (`false`)       |
  |  `ALLOW_UNQUOTED_FIELD_NAMES` (`false`)  |  `ALLOW_UNQUOTED_PROPERTY_NAMES` (`false`)  |
  | `ALLOW_NUMERIC_LEADING_ZEROS` (`false`)  | `ALLOW_LEADING_ZEROS_FOR_NUMBERS` (`false`) |
  | `ALLOW_UNQUOTED_CONTROL_CHARS` (`false`) |  `ALLOW_UNESCAPED_CONTROL_CHARS` (`false)   |

  

### StreamWriteFeature

+ `tools.jackson.core.StreamWriteFeature` were included in `jackson 2.x` `com.fasterxml.jackson.core.JsonGenerator.Feature`;
+   Now they are separated because they are not json-specific;
+ No changes in default values for these features.

### JsonWriteFeature

+ `tools.jackson.core.JsonWriteFeature` were mixed in jackson 2.x with non json specific features within `com.fasterxml.jackson.core.JsonGenerator.Feature`;

+ Some changed name or default value:

  | jackson 2.x (name + default) |   jackson 3 (name + default)    |
  | :--------------------------: | :-----------------------------: |
  | `QUOTE_FIELD_NAMES` (`true`) | `QUOTE_PROPERTY_NAMES` (`true`) |

+ `ESCAPE_FORWARD_SLASHES` is new in `JsonWriteFeature` (`true`), but the comment says it was disabled in jackson 2.x. However, I could not find it in jackson 2.x 

### SerializationFeature

+ `tools.jackson.databind.SerializationFeature` replaces the `com.fasterxml.jackson.databind.SerializationFeature`

+ Some changes in default values:

  |      jackson 2.x (name + default)       |       jackson 3 (name + default)       |
  | :-------------------------------------: | :------------------------------------: |
  |  `WRITE_DATES_AS_TIMESTAMPS` (`true`)   | `WRITE_DATES_AS_TIMESTAMPS` (`false`)  |
  |  `WRITE_DATES_AS_TIMESTAMPS` (`true`)   | `WRITE_DATES_AS_TIMESTAMPS` (`false`)  |
  | `WRITE_ENUMS_USING_TO_STRING` (`false`) | `WRITE_ENUMS_USING_TO_STRING` (`true`) |

+ New properties are:

  + `FAIL_ON_ORDER_MAP_BY_INCOMPARABLE_KEY` (`false`)

### DeserializationFeature

+ `tools.jackson.databind.DeserializationFeature` replaces `com.fasterxml.jackson.databind.DeserializationFeature`

+ Some changes in default values:

  |      jackson 2.x (name + default)       |       jackson 3 (name + default)       |
  | :-------------------------------------: | :------------------------------------: |
  |  `FAIL_ON_UNKNOWN_PROPERTIES` (`true`)  | `FAIL_ON_UNKNOWN_PROPERTIES` (`false`) |
  | `FAIL_ON_NULL_FOR_PRIMITIVES` (`false`) | `FAIL_ON_NULL_FOR_PRIMITIVES` (`true`) |
  |   `FAIL_ON_TRAILING_TOKENS` (`false`)   |   `FAIL_ON_TRAILING_TOKENS` (`true`)   |
  | `READ_ENUMS_USING_TO_STRING` (`false`)  | `READ_ENUMS_USING_TO_STRING` (`true`)  |

+ New properties are:

  + `FAIL_ON_UNEXPECTED_VIEW_PROPERTIES` (`true`)

### MapperFeature

+ `tools.jackson.databind.MapperFeature` replaces `com.fasterxml.jackson.databind.MapperFeature`

+ Some changes in default values:

  |        jackson 2.x (name + default)        |         jackson 3 (name + default)         |
  | :----------------------------------------: | :----------------------------------------: |
  |     `USE_GETTERS_AS_SETTERS` (`true`)      |     `USE_GETTERS_AS_SETTERS` (`false`)     |
  | `ALLOW_FINAL_FIELDS_AS_MUTATORS` (`true`)  | `ALLOW_FINAL_FIELDS_AS_MUTATORS` (`false`) |
  |  `ALLOW_VOID_VALUED_PROPERTIES` (`false`)  |  `ALLOW_VOID_VALUED_PROPERTIES` (`true`)   |
  | `SORT_PROPERTIES_ALPHABETICALLY` (`false`) | `SORT_PROPERTIES_ALPHABETICALLY` (`true`)  |

+ Removed properties:

  + `USE_STD_BEAN_NAMING`
  + `IGNORE_DUPLICATE_MODULE_REGISTRATIONS`
  + `BLOCK_UNSAFE_POLYMORPHIC_BASE_TYPES`



## Other Changes and Considerations

+ The new `JacksonException` extends `RuntimeException`, so we got rid of all the `throws IOException` not needed anymore

+ Modules are registered through the ObjectMapper Builder and not directly through the mapper, thus:

  ```java
  //Jackson 2.x
  EMFModule module = new EMFModule();
  mapper.registerModule(module);
  
  //Jackson 3.0 
  objMapperBuilder.addModule(module);
  ```

+ `tools.jackson.databind.ser.jdk.MapSerializer#construct` changed the order of the parameter list in the method signature, thus:

  ```java
  //Jacskon 2.x
  public static MapSerializer construct(
  		Set<String> ignoredEntries, 
  		JavaType mapType,
          boolean staticValueType, 
          TypeSerializer vts,
          JsonSerializer<Object> keySerializer, 
          JsonSerializer<Object> valueSerializer,
          Object filterId)
              
   
  //Jackson 3.0 
  public static MapSerializer construct(
  		JavaType mapType,
          boolean staticValueType, 
          TypeSerializer vts,
          ValueSerializer<Object> keySerializer, 
          ValueSerializer<Object> valueSerializer,
          Object filterId,
          Set<String> ignoredEntries, 
          Set<String> includedEntries)
  ```

+ `tools.jackson.databind.ser.Serailizer.Base` methods to look for serializers not take an additional parameter of type `com.fasterxml.jackson.annotation.JsonFormat.Value`, to the methods in `EMFSerializers` have been changed accordingly

+ In our `org.eclipse.emfcloud.jackson.databind.deser.CollectionDeserializer#createCollection` we had to replace the call to construct a collection from the type of the collection because the old one was not available anymore, thus:

  ```java
  //Jackson 2.x
  type = (CollectionType) ctxt.getFactory().mapAbstractType(ctxt.getConfig(), type);
  
  //Jackson 3.0
  type = (CollectionType) ctxt.getTypeFactory().constructCollectionLikeType(
      	type.getRawClass(),type.getContentType().getRawClass()
  	);
  ```

+ In `org.gecko.codec.jackson.databind.CodecWriteContext` we removed the constructor without the `currentObject` because in the parent it was not available anymore

+ Our `org.gecko.codec.jackson.databind.deser.CodecParserBaseImpl` now inherits from `tools.jackson.core.json.JsonParserBase`

+ `JsonWriteFeature.ESCAPE_FORWARD_SLASHES` was disabled by default in jackson 2.x, but now it is enabled by default. This means that if we do not disable it the forward slashes are escaped, resulting, for instance, in things like:

  ```json
  {
    "_id" : "d1f14a91-4571-4c56-824f-2c5e08099be7",
    "_type" : "http:\/\/example.de\/person\/1.0#\/\/SpecificBusinessPerson",
    "_supertype" : [ "http:\/\/example.de\/person\/1.0#\/\/Person", "http:\/\/example.de\/person\/1.0#\/\/BusinessPerson" ],
    "name" : "John",
    "lastName" : "Doe",
    "birthDate" : "1990-06-20T00:00:00",
    "title" : [ "Mrs", "Dr" ],
    "getFullName" : "John Doe"
  }
  ```

  This was making some tests of the json part fail. Disabling the property is possible at the level of the `ObjectMapperConfigurator`:

  ```java
  @WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
  		@Property(key = "type", value="json"),
  		@Property(key = "disableFeatures", value={"JsonWriteFeature.ESCAPE_FORWARD_SLASHES"}, type = Type.Array)
  })
  ```

  


## Changes to double check

+ Our `org.gecko.codec.jackson.DefaultCodecFactoryConfigurator.CodecFactoryBuilder` changed signature (**TO BE DOUBLE CHECKED IF IT MAKES SENSE**)

  ```java
  //Jackson 2.x
  private class CodecFactoryBuilder<F extends JsonFactory, B extends TSFBuilder<F, B>> extends TSFBuilder<F, B>{
  
  		/* 
  		 * (non-Javadoc)
  		 * @see com.fasterxml.jackson.core.TSFBuilder#build()
  		 */
  		@SuppressWarnings("unchecked")
  		@Override
  		public F build() {
  			return (F) new CodecFactory(genFactory, parserFactory);
  		}	
  	}
  
  //Jackson 3.0
  private class CodecFactoryBuilder<F extends JsonFactory, B extends JsonFactoryBuilder> extends JsonFactoryBuilder{
  
  		/**
  		 * Creates a new instance.
  		 * @param base
  		 */
  		protected CodecFactoryBuilder(JsonFactory base) {
  			super(base);
  		}
  
  		/* 
  		 * (non-Javadoc)
  		 * @see com.fasterxml.jackson.core.TSFBuilder#build()
  		 */
  		@SuppressWarnings("unchecked")
  		@Override
  		public F build() {
  			return (F) new CodecFactory(this, genFactory, parserFactory);
  		}		
  	}
  ```

  

+ There are some new methods to implement when inheriting from `JsonGenerator` and `JsonParser` and not all of them are currently implemented;
+ `org.eclipse.emfcloud.jackson.databind.deser.EMFDeserializers` should now implement also `hasDeserializerFor()`;
+ `JsonParser#getCodec` does not exist anymore. We should check if we still need the `TreeCodec` to be passed around (I think we are not actually doing anything with it around);

## Tests

### Mongo

+ Serialization tests work all fine;

+ Deserialization tests all fail when reading the end of document with this error:

  ```bash
  org.bson.BsonInvalidOperationException: readEndDocument can only be called when ContextType is DOCUMENT or SCOPE_DOCUMENT, not when ContextType is ARRAY.
  ```

  While debugging, especially looking in our `org.gecko.codec.jackson.databind.deser.CodecParserBaseImpl#nextToken` it seems that the token are read in the right order (property name, then value, etc) and that when it reads the end of the document the token is actually `DOCUMENT` and not `ARRAY`. The issue seems to be in the `org.bson.AbstarctBsonReader#readEndDocument()`:

  ```java
     @Override
      public void readEndDocument() {
          if (isClosed()) {
              throw new IllegalStateException("BSONBinaryWriter");
          }
          if (getContext().getContextType() != BsonContextType.DOCUMENT && getContext().getContextType() != BsonContextType.SCOPE_DOCUMENT) {
              throwInvalidContextType("readEndDocument",
                                      getContext().getContextType(), BsonContextType.DOCUMENT, BsonContextType.SCOPE_DOCUMENT);
          }
          if (getState() == State.TYPE) {
              readBsonType(); // will set state to EndOfDocument if at end of document
          }
          if (getState() != State.END_OF_DOCUMENT) {
              throwInvalidState("readEndDocument", State.END_OF_DOCUMENT);
          }
          doReadEndDocument();
          setStateOnEnd();
      }
  ```

  where the `getContext().getContextType()` returns `ARRAY`. 
  
  This ends up here from the `tools.jackson.databind.ObjectReader#bindAndClose` method, which called a `_verifyNoTrailingTokens` if the option `DeserializationFeature.FAIL_ON_TRAILING_TOKENS` is enabled. In jackson 2.x this was disabled by default, but now it is enabled. 
  
  To avoid the issue, we can disable the feature at the level of the `ObjectMapperConfigurator`:
  
  ```java
  @WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
  		@Property(key = "codecFactoryConfigurator.target", value="(type=mongo)"),
  		@Property(key = "type", value="mongo"),
  		@Property(key = "disableFeatures", value={"DeserializationFeature.FAIL_ON_TRAILING_TOKENS"}, type = Type.Array)
  })
  ```
  
  But still, this should be checked, because if someone wants to enable it, it should not fail like this.





