# Test Coverage

[← Scenarios](18-scenarios.md) | [Next: Open Questions →](99-open-questions.md)

---

## 1. Overview

This document provides a comprehensive mapping between spec sections and their corresponding test files. It helps maintain traceability between specification requirements and their test coverage.

**Test Suite Statistics:**
- Total Tests: **774+**
- Test Files: **60+**
- Test Models: **15+**

---

## 2. Test Coverage Matrix

### 2.1 Core Serialization/Deserialization

| Spec Section | Test Files | Coverage |
|--------------|------------|----------|
| [06-type.md](06-type.md) | `TypeSerializationEntryTest.java`, `TypeDeserializationEntryTest.java`, `TypeResolutionUriTest.java`, `CodecResourceMappedTypeTest.java` | ✅ Complete |
| [07-supertype.md](07-supertype.md) | `SuperTypeSerializationEntryTest.java`, `SuperTypeDeserializationEntryTest.java`, `CodecResourceSuperTypeTest.java` | ✅ Complete |
| [09-id.md](09-id.md) | `IdSerializationEntryTest.java`, `IdDeserializationEntryTest.java`, `CodecResourceIdTest.java` | ✅ Complete |
| [10-reference.md](10-reference.md) | `ReferenceSerializationEntryTest.java`, `ReferenceDeserializationEntryTest.java`, `CrossDocumentContainmentTest.java`, `ExpandReferenceTest.java`, `ProxyCreationTest.java` | ✅ Complete |
| [11-feature.md](11-feature.md) | `AttributeSerializationEntryTest.java`, `AttributeDeserializationEntryTest.java`, `EnumSerializationTest.java`, `ExtendedMetaDataTest.java` | ✅ Complete |
| [14-custom-values.md](14-custom-values.md) | `CodecValueRegistryTest.java`, `ReferenceSerializationEntryCustomWriterTest.java`, `ReferenceDeserializationEntryCustomReaderTest.java`, `CodecResourceCustomValueTest.java` | ✅ Complete |
| [12-polymorphism.md](12-polymorphism.md) | `CodecResourceAdvancedTest.java` (PolymorphicListTests), `CodecResourceSmartCompressionTest.java` | ✅ Complete |

### 2.2 Configuration

| Spec Section | Test Files | Coverage |
|--------------|------------|----------|
| [02-config-resolution.md](02-config-resolution.md) | `ConfigurationMergerTest.java`, `EffectiveCodecConfigTest.java` | ✅ Complete |
| [05-global-options.md](05-global-options.md) | `GlobalIgnoreFeatureTest.java`, `SmartCompressionSameSchemaTest.java`, `CodecResourceSmartCompressionTest.java` | ✅ Complete |

### 2.3 Integration

| Spec Section | Test Files | Coverage |
|--------------|------------|----------|
| [01-architecture.md](01-architecture.md) | `CodecModuleTest.java`, `CodecSerializersTest.java`, `CodecDeserializersTest.java`, `CodecJsonFactoryTest.java` | ✅ Complete |

---

## 3. Detailed Test File Mapping

### 3.1 Serialization Entry Tests (`org.eclipse.fennec.codec.v2.ser`)

| Test File | Spec Reference | Description |
|-----------|---------------|-------------|
| `IdSerializationEntryTest.java` | [09-id.md](09-id.md) | ID serialization in PLAIN/STRUCTURED formats |
| `TypeSerializationEntryTest.java` | [06-type.md](06-type.md) | Type serialization with all strategies |
| `SuperTypeSerializationEntryTest.java` | [07-supertype.md](07-supertype.md) | SuperType serialization |
| `AttributeSerializationEntryTest.java` | [11-feature.md](11-feature.md) | EAttribute serialization |
| `ReferenceSerializationEntryTest.java` | [10-reference.md](10-reference.md) | EReference serialization |
| `ReferenceSerializationEntryCustomWriterTest.java` | [14-custom-values.md#5](14-custom-values.md#5-reference-value-readerswriters) | Custom reference value writers |
| `CrossDocumentContainmentTest.java` | [10-reference.md#6](10-reference.md#6-cross-document-containment) | Cross-document containment detection |
| `ExpandReferenceTest.java` | [10-reference.md#4](10-reference.md#4-expand-feature) | Non-containment reference expansion |
| `EnumSerializationTest.java` | [11-feature.md#4](11-feature.md#4-enum-serialization) | Enum serialization strategies |
| `ExtendedMetaDataTest.java` | [11-feature.md#3](11-feature.md#3-extended-metadata-names) | Extended metadata names |
| `SmartCompressionSameSchemaTest.java` | [05-global-options.md#1](05-global-options.md#1-smart-compression) | Smart compression for same-schema types |

### 3.2 Deserialization Entry Tests (`org.eclipse.fennec.codec.v2.deser`)

| Test File | Spec Reference | Description |
|-----------|---------------|-------------|
| `IdDeserializationEntryTest.java` | [09-id.md](09-id.md) | ID deserialization |
| `TypeDeserializationEntryTest.java` | [06-type.md](06-type.md) | Type deserialization with all strategies |
| `SuperTypeDeserializationEntryTest.java` | [07-supertype.md](07-supertype.md) | SuperType deserialization |
| `AttributeDeserializationEntryTest.java` | [11-feature.md](11-feature.md) | EAttribute deserialization |
| `ReferenceDeserializationEntryTest.java` | [10-reference.md](10-reference.md) | EReference deserialization |
| `ReferenceDeserializationEntryCustomReaderTest.java` | [14-custom-values.md#5](14-custom-values.md#5-reference-value-readerswriters) | Custom reference value readers |
| `TypeResolutionUriTest.java` | [06-type.md#2](06-type.md#2-type-strategies) | URI-based type resolution |

### 3.3 Resource Integration Tests (`org.eclipse.fennec.codec.v2.resource`)

| Test File | Spec Reference | Description |
|-----------|---------------|-------------|
| `CodecResourceRoundTripTest.java` | [00-overview.md](00-overview.md) | Basic round-trip serialization |
| `CodecResourceIdTest.java` | [09-id.md](09-id.md) | ID serialization integration |
| `CodecResourceSuperTypeTest.java` | [07-supertype.md](07-supertype.md) | SuperType serialization integration |
| `CodecResourceSmartCompressionTest.java` | [05-global-options.md#1](05-global-options.md#1-smart-compression) | Smart compression integration |
| `CodecResourceCustomValueTest.java` | [14-custom-values.md](14-custom-values.md) | Custom value readers/writers integration |
| `CodecResourceAnnotationTest.java` | [02-config-resolution.md](02-config-resolution.md) | EAnnotation-based configuration |
| `CodecResourceCrossPackageTest.java` | [06-type.md#2.4](06-type.md#24-mapped-strategy) | Cross-package MAPPED strategy |
| `CodecResourceMappedTypeTest.java` | [06-type.md#2.4](06-type.md#24-mapped-strategy) | MAPPED type strategy |
| `CodecResourceFeaturePathTypeTest.java` | [06-type.md](06-type.md) | FeaturePath type resolution |
| `CodecResourceArrayRootTest.java` | [18-scenarios.md](18-scenarios.md) | Array as root object |
| `GlobalIgnoreFeatureTest.java` | [05-global-options.md#4](05-global-options.md#4-global-ignore-features) | Global feature ignore |
| `ProxyCreationTest.java` | [10-reference.md#5](10-reference.md#5-proxy-creation) | Proxy creation for references |
| `CodecResourceAdvancedTest.java` | [12-polymorphism.md](12-polymorphism.md), [10-reference.md](10-reference.md) | Advanced scenarios |

### 3.4 Custom Value Tests (`org.eclipse.fennec.codec.v2.value`)

| Test File | Spec Reference | Description |
|-----------|---------------|-------------|
| `CodecValueRegistryRegisterTest.java` | [14-custom-values.md#6](14-custom-values.md#6-registration) | Registry registration |
| `CodecValueRegistryGetWriterTest.java` | [14-custom-values.md#10](14-custom-values.md#10-registry-api) | Writer lookup |
| `CodecValueRegistryGetReaderTest.java` | [14-custom-values.md#10](14-custom-values.md#10-registry-api) | Reader lookup |
| `CodecValueRegistryGetAllTest.java` | [14-custom-values.md#10](14-custom-values.md#10-registry-api) | Registry introspection |
| `CodecValueRegistryHasTest.java` | [14-custom-values.md#10](14-custom-values.md#10-registry-api) | Has reader/writer checks |

### 3.5 Module Tests (`org.eclipse.fennec.codec.v2.module`)

| Test File | Spec Reference | Description |
|-----------|---------------|-------------|
| `CodecModuleBuilderTest.java` | [01-architecture.md](01-architecture.md) | Module builder API |
| `CodecModuleFactoryMethodsTest.java` | [01-architecture.md](01-architecture.md) | Module factory methods |
| `CodecModuleVersionTest.java` | [01-architecture.md](01-architecture.md) | Module versioning |
| `CodecModuleConfigurationDelegationTest.java` | [01-architecture.md](01-architecture.md) | Configuration delegation |
| `CodecModuleGlobalIgnoreTest.java` | [05-global-options.md#4](05-global-options.md#4-global-ignore-features) | Global ignore via module |

---

## 4. Advanced Scenario Coverage

The `CodecResourceAdvancedTest.java` covers scenarios that span multiple spec sections:

### 4.1 Polymorphic Lists
**Spec:** [12-polymorphism.md](12-polymorphism.md)

| Test Method | Scenario |
|-------------|----------|
| `roundTripsZooWithMixedAnimalTypes` | Zoo with Dog, Cat, Bird (inheritance hierarchy) |
| `roundTripsZooWithFeaturedAnimalReference` | Polymorphic non-containment reference |
| `roundTripsEmptyPolymorphicList` | Empty polymorphic collection |

### 4.2 Bidirectional References
**Spec:** [10-reference.md](10-reference.md)

| Test Method | Scenario |
|-------------|----------|
| `roundTripsParentWithChildren` | Parent-children bidirectional containment |
| `roundTripsGrandparentWithNestedChildren` | 3-level nested bidirectional hierarchy |

### 4.3 Circular References
**Spec:** [10-reference.md](10-reference.md)

| Test Method | Scenario |
|-------------|----------|
| `roundTripsSimpleCircularReference` | A → B → A cycle |
| `roundTripsSelfReferentialNode` | A → A self-reference |
| `roundTripsMultiValuedCircularConnections` | Complex graph with multiple circular refs |

### 4.4 Null/Default Value Handling
**Spec:** [11-feature.md](11-feature.md)

| Test Method | Scenario |
|-------------|----------|
| `deserializesEntityWithDefaultValues` | Default values when not in JSON |
| `roundTripsEntityWithExplicitValues` | Overriding defaults |
| `roundTripsEntityWithNullOptionalChild` | Null optional containment |
| `roundTripsEntityWithNestedOptionalChild` | Non-null optional containment |

### 4.5 Error Scenarios
**Spec:** [00-overview.md#2](00-overview.md#2-error-and-warning-handling)

| Test Method | Scenario |
|-------------|----------|
| `handlesUnknownTypeGracefully` | Unknown type URI returns null |
| `handlesMalformedJsonGracefully` | Invalid JSON throws exception |
| `handlesEmptyJsonObject` | Empty `{}` handling |

---

## 5. Test Models

| Ecore Model | Used By | Purpose |
|-------------|---------|---------|
| `test-roundtrip.ecore` | `CodecResourceRoundTripTest` | Person, Address, Company |
| `test-annotated.ecore` | `CodecResourceAnnotationTest` | Product, Order with annotations |
| `test-id.ecore` | `CodecResourceIdTest` | Single/combined ID testing |
| `test-smart-compression.ecore` | Smart compression tests | Dog, Cat, Owner, Kennel |
| `test-mapped-type.ecore` | `CodecResourceMappedTypeTest` | Device, Sensors with discriminators |
| `test-mapped-type-ext.ecore` | Cross-package tests | Extension sensors |
| `test-advanced.ecore` | `CodecResourceAdvancedTest` | Polymorphism, bidirectional, circular |
| `test-serialization.ecore` | Entry unit tests | Serialization entry testing |
| `test-deserialization.ecore` | Entry unit tests | Deserialization entry testing |
| `test-featurepath-type.ecore` | FeaturePath tests | FeaturePath type resolution |
| `test-enum-serialization.ecore` | `EnumSerializationTest` | Enum strategies |

---

## 6. Coverage Gaps

The following scenarios currently have limited or no test coverage:

| Scenario | Status | Priority |
|----------|--------|----------|
| Large object graphs (1000+ elements) | Not covered | Low |
| Concurrent access / thread-safety | Not covered | Low |
| Performance benchmarks | Not covered | Low |
| Cross-resource references | Partial | Medium |
| NUMERIC type strategy edge cases | Partial | Low |

---

## 7. Adding New Tests

When adding new tests, follow these conventions:

### 7.1 Spec Reference in Test Class
```java
/**
 * Tests for {@link SomeEntry}.
 *
 * @see <a href="docs/codec-v2-spec/XX-section.md">Spec: Section Name</a>
 */
@DisplayName("SomeEntry")
class SomeEntryTest {
```

### 7.2 Spec Reference in Test Method
```java
@Test
@DisplayName("description matching spec requirement")
void testMethodName() {
    // Test implementation
}
```

### 7.3 Update This Document
After adding tests, update the corresponding section in this document to maintain traceability.

---

[Next: Open Questions →](99-open-questions.md)
