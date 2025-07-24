# Codec Type Info - Type (De-)Serialization Strategies

## Type Strategies

We can (de-)serialize the type info on an `EObject` with 3 different strategies:

+ `NAME`: via the simple name of the `EClass`, e.g. `Person`
+ `CLASS`: via the fully qualified class name, e.g. `org.eclipse.fennec.codec.demo.model.Person`
+ `URI`: via the uri of the `EClass`, e.g. `http://fennec.eclipse.org/codec/demo/1.0#//Person`

## How to set and overwrite the type strategy

These are set through the `strategy` option, which can either be set via model annotation `codec.type` or by load/save options (`CodecModelInfoOptions.CODEC_TYPE_STRATEGY`). If there is a `strategy` set through model annotation and a different one is set via the load/save options, this last one overwrites the previous.

If no `strategy` is specified, the default one is `URI`.

The automatically registered `CodecValueReader` and `CodecValueWriter` , corresponding to the aforementioned strategies, can be found in the `org.eclipse.fennec.codec.info` bundle. 

If you want to use a different `CodecValueReader/Writer`, then you should take care of registering the corresponding `CodecValueReader/Writer` with that name. This can be done only through the load/save options. 

So, for instance, suppose you have your own `CodecValueWriter`

```java
public static final CodecValueWriter<EClass, String> TEST_TYPE_WRITER = new CodecValueWriter<>() {

		@Override
		public String getName() {
			return "TEST_TYPE_WRITER";
		}

		@Override
		public String writeValue(EClass value, SerializationContext provider) {
			return "test.".concat(value.getName());
		}
	};
```

Then, in your saving options, you should do:

```java
Map<String, Object> options = new HashMap<>();
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> addOptions = new HashMap<>();
		addOptions.put(CodecModelInfoOptions.CODEC_TYPE_VALUE_WRITER, CodecTestHelper.TEST_TYPE_WRITER);

		classOptions.put(PersonPackage.eINSTANCE.getAddress(), addOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.save(options);
```

When doing this, your custom `CodecValueWriter` will be used during serialization of the `Address` type information.

## How to deal with type mapping

When working with type mapping, it means you can provide a map to say to the (de-)serializer how to deal with the type information. This map can either be passed through model annotations, by adding map entry to the `codec.type` annotation with keys different from the reserved ones (`strategy`, `typeKey`, `include`), or through the load/save options (`CodecModelInfoOptions.CODEC_TYPE_MAP`).

When a `typeMap` is set, the deserialization process works like this:

+ the document is scanned for the `typeKey`;
+ when this is found, the value is taken and a corresponding match in the `typeMap` is searched for;
+ if a match is found, then the corresponding `CodecValueReader` based on the `strategy` is applied to the `typeMap` value corresponding to the found key;
+ if a match is NOT found, then the corresponding `CodecValueReader` based on the `strategy` is directly applied to the token value.



