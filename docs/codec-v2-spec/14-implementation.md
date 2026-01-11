# Implementation

[← Back to Overview](00-overview.md) | [← Architecture](13-architecture.md)

---

## 1. Implementation Strategy

### Phase 1: Core Serialization ✅
- End-to-end round-trip serialization
- Basic type, ID, reference handling
- Jackson integration

### Phase 2: Configuration ✅
- EAnnotation-based configuration
- MAPPED TypeStrategy with discriminators
- Cross-package dynamic registration
- Smart compression
- FeaturePath resolution
- Array roots

### Phase 3: Advanced Features ✅
- Custom value readers/writers (unified interface)
- Additional TypeStrategies (SCHEMA_AND_TYPE, STRUCTURED, NUMERIC)
- Polymorphic list serialization
- Bidirectional reference handling
- Circular reference handling

### Phase 4: Production Readiness (Current)
- Performance optimization
- OSGi integration
- Cross-resource references (ResourceSet-based)
- Documentation finalization

---

## 2. Implementation Checklist

### Core
- [x] CodecResource implementation
- [x] ConfigurationMerger
- [x] EffectiveCodecConfig with lazy caching
- [x] EMFContextHolder using EffectiveCodecConfig
- [x] Jackson context integration

### Type Serialization (Format × Strategy Matrix)

**PLAIN format:**
- [x] URI strategy (default) - ser + deser
- [x] NAME strategy - ser + deser
- [x] CLASS strategy - ser only
- [x] MAPPED strategy - ser + deser
- [x] NUMERIC strategy - ser + deser (uses hint package for disambiguation)
- [x] SCHEMA_AND_TYPE strategy - two separate fields (`_schema` + `_type`) - ser + deser

**STRUCTURED format:** (all use unified `type` key except NUMERIC)
- [x] SCHEMA_AND_TYPE - `{"schema":"...","type":"..."}` - ser + deser
- [x] URI - `{"type":"<uri>"}` - ser + deser
- [x] NAME - `{"type":"<name>"}` - ser + deser
- [x] CLASS - `{"type":"<class>"}` - ser only
- [x] NUMERIC - `{"schema":"...","classifier":N}` - ser + deser
- [x] MAPPED - `{"type":"<discriminator>"}` - ser + deser

### ID Serialization
- [x] Single ID field
- [x] Multiple ID fields with separator
- [x] PLAIN format
- [x] STRUCTURED format
- [x] ID_ONLY, BOTH, FEATURE_ONLY modes
- [x] Separator serialization (both formats)

### Reference Serialization
- [x] Non-containment references
- [x] STRUCTURED format (default)
- [x] PLAIN format
- [x] Cross-document containment detection
- [x] Proxy creation
- [x] Expand feature (serialization + deserialization)
- [x] Proxy with projection

### Features
- [x] EAttribute serialization
- [x] EReference serialization
- [x] Null/default/empty handling
- [x] Key customization
- [x] Enum serialization options (LITERAL, NAME, VALUE strategies)
- [x] Extended metadata names

### Global Options
- [x] Smart compression (type omission)
- [ ] Smart compression (same-schema names)
- [ ] Field ordering
- [x] Global feature ignore list
- [ ] NUMERIC mode

### SuperType Serialization

**Format follows Type format** (no independent format setting):
- Type PLAIN → SuperType as standalone `_supertype` field
- Type STRUCTURED → SuperType inside `_type` object as `supertype` field

**Selection modes:**
- [x] ALL selection
- [x] SINGLE selection
- [x] ALL_EMF selection
- [x] NONE selection

**Presentation:**
- [x] ARRAY presentation (default) - array of URI strings
- [x] STRING presentation - separator-joined URI string (configurable separator, default `,`)

**Value resolution (namespace matching):**
- [x] Same namespace as root → simple EClass name
- [x] Different namespace → full EClass URI

**STRUCTURED format (inside `_type` object):**
- [x] ARRAY presentation inside `_type` object
- [x] STRING presentation inside `_type` object

**Deserialization:**
- [x] Parse supertype values (ARRAY and STRING presentation)
- [x] Optional validation with `validateSuperTypeHierarchy` option

### Advanced
- [ ] Cross-resource references (ResourceSet-based resolution)
- [x] Custom value readers/writers (unified interface)
- [x] Annotation inheritance

---

## 3. Test Coverage

**Total: 774+ tests across 60+ test files**

See [15-test-coverage.md](15-test-coverage.md) for the complete spec-to-test mapping.

| Area | Status | Test Files |
|------|--------|------------|
| Round-trip serialization | ✅ | `CodecResourceRoundTripTest`, `CodecResourceAdvancedTest` |
| Type strategies (URI, NAME, MAPPED) | ✅ | `TypeSerializationEntryTest`, `TypeDeserializationEntryTest` |
| ID strategies (PLAIN, STRUCTURED) | ✅ | `IdSerializationEntryTest`, `CodecResourceIdTest` |
| Reference handling | ✅ | `ReferenceSerializationEntryTest`, `ProxyCreationTest` |
| Configuration merging | ✅ | `ConfigurationMergerTest`, `EffectiveCodecConfigTest` |
| Smart compression | ✅ | `SmartCompressionSameSchemaTest`, `CodecResourceSmartCompressionTest` |
| Enum serialization | ✅ | `EnumSerializationTest` |
| SuperType serialization | ✅ | `SuperTypeSerializationEntryTest`, `CodecResourceSuperTypeTest` |
| Expand references | ✅ | `ExpandReferenceTest` |
| Global ignore features | ✅ | `GlobalIgnoreFeatureTest` |
| Custom value readers/writers | ✅ | `CodecValueRegistryTest`, `ReferenceSerializationEntryCustomWriterTest` |
| Polymorphic lists | ✅ | `CodecResourceAdvancedTest.PolymorphicListTests` |
| Bidirectional references | ✅ | `CodecResourceAdvancedTest.BidirectionalReferenceTests` |
| Circular references | ✅ | `CodecResourceAdvancedTest.CircularReferenceTests` |
| Null/default handling | ✅ | `CodecResourceAdvancedTest.NullDefaultValueTests` |
| Error handling | ✅ | `CodecResourceAdvancedTest.ErrorScenarioTests` |

---

## 4. Next Steps (Priority Order)

### Completed ✅
1. ~~**Cross-Document Containment**: Detect and serialize as reference~~ ✅
2. ~~**Proxy Factory**: Create EMF proxies for non-containment references~~ ✅
3. ~~**Expand Feature**: Inline serialization of non-containment references~~ ✅
4. ~~**Enum Serialization**: LITERAL, NAME, VALUE strategies~~ ✅
5. ~~**SuperType Serialization**: PLAIN format~~ ✅
6. ~~**ID STRUCTURED Format**: Nested object with individual fields~~ ✅
7. ~~**Global Ignore Features**: Codec-wide feature exclusion~~ ✅
8. ~~**Extended Metadata Names**: XSD annotation support~~ ✅
9. ~~**Additional TypeStrategies**: SCHEMA_AND_TYPE, STRUCTURED, NUMERIC~~ ✅
10. ~~**Custom Value Readers/Writers**: Unified interface for attributes and references~~ ✅
11. ~~**Polymorphic Lists**: Mixed types in containment collections~~ ✅
12. ~~**Bidirectional References**: Automatic opposite restoration~~ ✅
13. ~~**Circular References**: Self and mutual reference handling~~ ✅

### Remaining
1. **OSGi Integration**: CodecResourceFactory service registration
2. **Cross-Resource References**: ResourceSet-based resolution
3. **Performance Optimization**: Large object graph handling

### Deferred
- Expand Depth > 1
- Field ordering options
- Streaming serialization

---

[Next: Test Coverage →](15-test-coverage.md)

