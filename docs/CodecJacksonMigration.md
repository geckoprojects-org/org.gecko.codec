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
  			return (F) new CodecFactory(genFactory, parserFactory);
  		}		
  	}
  ```

  

+ There are some new methods to implement when inheriting from `JsonGenerator` and `JsonParser` and not all of them are currently implemented;
+ `org.eclipse.emfcloud.jackson.databind.deser.EMFDeserializers` should now implement also `hasDeserializerFor()`;
+ `JsonParser#getCodec` does not exist anymore. We should check if we still need the `TreeCodec` to be passed around (I think we are not actually doing anything with it around);

## Tests

### Json

+ Weird behaviour when serializing type and url strings. The `/` gets for some reasons escaped so the resulting string is something like 

  ​	

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

  While debugging, the method chain that gets called is something like that:

  	+ `tools.jackson.core.json.UTF8JsonGenerator#writeString`
  	+ `tools.jackson.core.json.UTF8JsonGenerator#_writeStringSegments`
  	+ `tools.jackson.core.json.UTF8JsonGenerator#_writeStringSegment`

  This last one looks like:

  ```java
  private final void _writeStringSegment(String text, int offset, int len) throws JacksonException
      {
          // note: caller MUST ensure (via flushing) there's room for ASCII only
          // Fast+tight loop for ASCII-only, no-escaping-needed output
          len += offset; // becomes end marker, then
  
          int outputPtr = _outputTail;
          final byte[] outputBuffer = _outputBuffer;
          final int[] escCodes = _outputEscapes;
  
          while (offset < len) {
              int ch = text.charAt(offset);
              // note: here we know that (ch > 0x7F) will cover case of escaping non-ASCII too:   <-- IMPORTANT PART!!!
              if (ch > 0x7F || escCodes[ch] != 0) {
                  break;
              }
              outputBuffer[outputPtr++] = (byte) ch;
              ++offset;
          }
          _outputTail = outputPtr;
          if (offset < len) {
              if (_characterEscapes != null) {
                  _writeCustomStringSegment2(text, offset, len);
              } else if (_maximumNonEscapedChar == 0) {
                  _writeStringSegment2(text, offset, len);
              } else {
                  _writeStringSegmentASCII2(text, offset, len);
              }
          }
      }
  ```

  But it looked the same also in Jackson 2.x, so it's weird that we only get a problem now because of it...

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





