# Codec Model Annotations

## EClass Level

The following codec model annotations are supported at the `EClass` level:

|    Source    | Details Map Key |                         Description                          |
| :----------: | :-------------: | :----------------------------------------------------------: |
|   `codec`    |    `inherit`    | Specifying weather codec annotations from parent `EClass` belonging to different `EPackage` should be inherited or not. Default behavior is false. |
| `codec.type` |    `include`    | Specifying weather to serialize type info or not. Supported values are true and false. |
| `codec.type` |      `use`      | Specifying the strategy to use when serializing the type. Supported values are NAME, CLASS and URI |
|  `codec.id`  |   `strategy`    | Specifying a strategy for serializing the id field. Supported values are ID_FIELD and COMBINED |
|  `codec.id`  |   `separator`   | Specifying the value of the separator used when constructing a combined id from multiple fields. Only used when `codec.id.strategy` is COMBINED. |
|  `codec.id`  |  `reader.name`  | Specifying a name for a `CodecValueReader` to be used when deserializing id field. |
|  `codec.id`  |  `writer.name`  | Specifying a name for a `CodecValueWriter` to be used when serializing id field. |



## EStructuralFeature Level

The following codec model annotations are supported at the `EStructuralFeature` level:

|   Source   | Details Map Key |                         Description                          |
| :--------: | :-------------: | :----------------------------------------------------------: |
| `codec.id` |   `id.field`    | Specifying that the annotated `EStructuralFeature` should be treated as an id field. |
| `codec.id` |   `id.order`    | Specifying the order of the annotated `EStructuralFeature` when treated as an id field in combination with others. Only used when codec.id.strategy` is COMBINED. |
|  `codec`   |    `ignore`     | Specifying that the annotated `EStructuralFeature` should be ignored during serialization. |

