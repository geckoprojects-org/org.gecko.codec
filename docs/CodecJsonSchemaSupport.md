# Codec Json Schema Support

In the `org.eclipse.fennec.codec.jsonschema` we added support for (de-)serializing jsonschema into/from an `EPackage`.

This has been achieved by adding the possibility to customize the `CodecEMFSerializers` and `CodecEMFDeserializers` which are linked to the `CodecModule` when calling `setupModule`.

These are now service components, so one can inject their custom ones, as we do in the `jsonschema` bundle, in the `JsonSchemaCodecEMFSerializers` and `JsonSchemaCodecEMFDeserializers`.

In there we call, when the type to be (de-)serialized is an `EObject` a `SmartJsonSchemaDeserializer` or `SmartJsonSchemaSerializer`, that checks, based on the information retrieved by the `CodecEMFContext`, whether the current object or the current feature (if we are not in the root object) contain the proper `jsonschema` extra annotation. 

This can be set either via annotations, under source `codec.extras`, or via load/save options using the `CodecModelInfoOptions.CODEC_EXTRAS`, both at the level of an `EClassifier` or of an `EFeature`.

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