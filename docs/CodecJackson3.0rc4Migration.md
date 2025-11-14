# Codec: Migration to Jackson 3.0 rc4

## Additional Changes wrt rc2

### DateTime specific Constants

+ The ways to configure date time for the ObjectMapper have been unified into a new `DateTimeFeature`, which now combines:
  + `JavaTimeFeature`
  + `Serialization/DeserializazionFeature` concerning datetime.
+ The changes have been taken into account in our `ObjectMapperConfigurator`.

## Enum specific Constants

+ The ways to configure enum for the ObjectMapper have been unified into a new `EnumFeature`, which now combines:
  + `Serialization/DeserializationFeature` concerning enum.
+ The changes have been taken into account in our `ObjectMapperConfigurator.`

## Method Signature Changes

+ The methods to find a serializer/deserializer in `tools.jackson.databind.deser.Deserializers.Base` and `tools.jackson.databind.deser.Serializers.Base` now take, instead of a `BeanDescription`, a `Supplier` as argument;