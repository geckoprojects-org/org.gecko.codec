# Codec Jackson Migration from rc6 to rc10

+ `org.eclipse.fennec.codec.CodecFactory`: removed method `createParser(URL url)` since it is not present anymore in the jackson `TokenStreamFactory`
+ `org.eclipse.fennec.codec.test.CodecModelInfoOverviewTest`: changed `ObjectMapper#getRegisteredModules()` to `ObjectMapper#registeredModules()`
+ `org.eclipse.fennec.codec.test.CodecModelConfigOverviewTest`: changed `ObjectMapper#getRegisteredModules()` to `ObjectMapper#registeredModules()`
+ `com.fasterxml.jackson.core:jackson-annotations:2.19.2` moved to `com.fasterxml.jackson.core:jackson-annotations:2.20`
+ `tools.jackson.core.json.JsonWriteFeature.ESCAPE_FORWARD_SLASHES` has been brought back to be **false** as default