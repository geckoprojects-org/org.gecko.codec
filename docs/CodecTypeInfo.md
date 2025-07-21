# Codec Type Info

This document is intended to describe how the serialization/deserialization of the type information works in our codec.

## The Model

The `TypeInfo` object is defined in our `codec-info`model (`org.eclipse.fennec.codec.info.model`):

![](./TypeInfoModel.png)

It consists of:

+ `typeStrategy`: the strategy to be used when (de-)serializing the type information. Supported values are:
  + `NAME`: uses the class name
  + `CLASS`: uses the instance class name
  + `URI`: uses the class URI
+ `ignoreType`: to specify whether the type information should be ignored or not (default is `false`)
+ `typeKey`: to specify the String to look for when deserializing and the one to use when serializing the type information
+ `typeMap`: this is the type mapping, where the keys are the values we can expect to encounter for the `typeKey` and the values are the corresponding type we should map the object to. The values have to be consistent with the `typeStrategy` (e.g. if `typeStrategy` is `NAME` then, the mapped value cannot be a URI, but it must be a class name) or with the action of the `CodecValueReader/Writer`
+ `typeValueReaderName`: specify a `CodecValueReader` name to be used when deserializing. If none is specified, this is set according to the `typeStrategy` . If this is set, this will overwrite the one set based on the `typeStrategy` (e.g. `typeStrategy` is not set, but `typeValueReaderName` has been set to `URI`, then the type token to be deserialized has to be an URI)
+ `typeValueWriterName` specify a `CodecValueWriter` name to be used when serializing. If none is specified, this is set according to the `typeStrategy`. If this is set, it will overwrite the one determined by the `typeStreategy` (e.g. `typeStrategy` is `NAME`, but `typeValueWriterName` is `URI`, then in the serialized document we have to expect a URI as type value for the `typeKey`)

## The Type Key

The `typeKey` can be:

+ the name of an `EAttribute` of the object we want to serialize;
+ the path to an `EAttribute` of a **contained** `EReference` of the object we want to serialize;
+ a completely different String with no match in the object we want to serialize.

So, let's say we have a `Person` with an attribute `name` and a **contained** reference to an `Address`, which has a `street` attribute. The `typeKey`could be, for instance:

+ `name` : to match the `name` attribute of `Person`
+ `address.street`: to match the `street` attribute of the `address` reference of `Person`
+ `typeOfPerson`: a completely different String which has no match in the `Person` model.

## Serialization

If the `typeKey` matches one of the `EObject` features, then there is nothing special to do, as the feature will be serialized as usual.

If, instead, it consists of another `String`, the type of the object will be, first converted using the `CodecValueWriter` and then a match will be looked for in the `typeMap`. If a match is found, then the corresponding `typeMap` key will be serialized, otherwise the original object type after applying the `CodecValueWrtier` is serialized.

## Deserialization

When deserializing an object, we will go over the deserialized document and look for the `typeKey`. If a match is found, we then will look at the value of such token and we will look in the `typeMap` for a key with that value. If one is found then we will deserialize the corresponding `typeMap` value with the `CodecValueReader`. 

If no match is found in the `typeMap`, then we will try to directly deserialize the token value with the `CodecValueReader`.

## Annotations

The TypeInfo can be set either through model annotations or through save/load options. 

The type annotation can be placed either at the level of an `EClass` or at the level of an `EReference`. 

The source of the `EAnnotation` is `codec.type`; The details entry keys are:

+ `typeKey`: to set the `typeKey` property
+ `strategy`: to set the `typeStrategy`
+ `include`: to set the `typeIgnore` property
+ `typeValueReaderName`: to set the name of the value reader;
+ `typeValueWriterName`: to set the name of the value writer;
+ additional key, value pairs will be interpret for the `typeMap` property.

 ## Save/Load Options

All the properties of the TypeInfo can be overwritten through load/save options. They have to be grouped by `EClass` or `EReference`, depending on which element the properties should apply. So, for an `EClass` using the `CodecResourceOptions.CODEC_OPTIONS`, which then accepts a Map whose keys are the `EClass` and the values are the map of options specific for that class. While, for an `EReference` the same, but the `CodecResourceOptions.CODEC_OPTIONS` should be then passed as key of the class options to which the reference belongs.

The options to be used are the following:

+ `CodecModelInfoOptions.CODEC_TYPE_KEY`: to overwrite the `typeKey`;
+ `CodecModelInfoOptions.CODEC_TYPE_STRATEGY`: to overwrite the `typeStrategy`;
+ `CodecModelInfoOptions.CODEC_TYPE_INCLUDE`: to overwrite the include annotation;
+ `CodecModelInfoOptions.CODEC_TYPE_VALUE_READER_NAME`: to overwrite the value reader name;
+ `CodecModelInfoOptions.CODEC_TYPE_VALUE_WRITER_NAME`: to overwrite the value writer name;
+ `CodecModelInfoOptions.CODEC_TYPE_MAP`: to overwrite the `typeMap`.