# Codec Json Schema Support

In the `org.eclipse.fennec.codec.jsonschema` we added support for (de-)serializing jsonschema into/from an `EPackage`.

This has been achieved by adding the possibility to customize the `CodecEMFSerializers` and `CodecEMFDeserializers` which are linked to the `CodecModule` when calling `setupModule`.

These are now service components, so one can inject their custom ones.

For the `jsonschema` case, we actually provided a static configuration, since loading a jsonschema is not a common use case and the general setup is unlikely to change. Thus we did not want the user to be forced to provide every time a configuration to make the json schema deserialization work. 

So, we instantiate the component classes ourselves and the only thing that is still a component is the dedicated `JsonSchemaResourceFactory`. 

In this way, adding to the runtime the jsonschema bundle (together with the json bundle) should be enough and no further configuration is needed from the user.

[!WARNING]

If both the json and the jsonschema `ResourceFactory` are needed we cannot guarantee that 

```java
Resource jsonSchemaRes = resourceSet.createResource(URI.createURI("test.json"), "application/schema+json");
```

would be a `CodecJsonSchemaResource`. This is due to how the `Resource.Factory.Registry` work. It always checks first whether a `Resource.Factory` with the specified file extension is present and, if so, it does not check the compatibility with the content-type. This means, that, if it finds the json `Resource.Factory` it will return it and we are going to end up with a `CodecJsonResource` and not with a `CodecJsonSchemaResource`.

That's why in such cases, we have to create the `Resource` directly via the proper `Resource.Factory`

```java
@Reference(filter = "(" + EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/schema+json)")
Resource.Factory resourceFactory;

Resource jsonSchemaRes = resourceFactory.createResource(URI.createURI("test.json"));
```

This will guarantee that the resource created will be of type `CodecJsonSchemaResource`.



## The JsonSchema Serializers/Deserializers

In the `JsonSchemaCodecEMFSerializers` and `JsonSchemaCodecEMFDeserializers`  we call, when the type to be (de-)serialized is an `EObject` a `SmartJsonSchemaDeserializer` or `SmartJsonSchemaSerializer`, that checks, based on the information retrieved by the `CodecEMFContext`, whether the current object or the current feature (if we are not in the root object) contain the proper `jsonschema` extra annotation. 

This can be set either via annotations, under source `codec.extras`, or via load/save options using the `CodecModelInfoOptions.CODEC_EXTRAS`, both at the level of an `EClassifier` or of an `EReference`.

Together with `jsonschema`, one can specify another details entry for the `codec.extras`, namely `jsonschema.feature.key`, so that to specify whether the jsonschema is contained within an inner node. 

```json
"components": {
		"schemas": {
			"Alarm": {
				"type": "object",
				"properties": {
					"flags": {
						"type": "integer",
						"format": "int32"
						}
                	}
				}
			}
		}
			
```

If this is part of a model, in which the `schemas` reference of the `components` object is annotated with `jsonschema`, no `jsonschema.feature.key` has to be provided.

Instead, if we want to deserialize a root object directly into an `EPackage`, like:

```json
"schemas": {
			"Alarm": {
				"type": "object",
				"properties": {
					"flags": {
						"type": "integer",
						"format": "int32"
						}
                	}
				}
			}
```

we have to specify that the `jsonschema.feature.key` is `schemas`.