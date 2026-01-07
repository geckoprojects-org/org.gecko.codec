# Implementation

[← Back to Overview](00-overview.md) | [← Architecture](12-architecture.md)

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

### Phase 3: Advanced Features (Current)
- Cross-resource references
- Custom value readers/writers
- Additional TypeStrategies (SCHEMA_AND_TYPE, STRUCTURED, NUMERIC)
- OSGi integration

### Phase 4: Production Readiness
- Performance optimization
- Complete test coverage
- Documentation

---

## 2. Implementation Checklist

### Core
- [x] CodecResource implementation
- [x] ConfigurationMerger
- [x] EffectiveCodecConfig with lazy caching
- [x] EMFContextHolder using EffectiveCodecConfig
- [x] Jackson context integration

### Type Serialization
- [x] URI strategy (default)
- [x] NAME strategy
- [x] CLASS strategy
- [x] MAPPED strategy with discriminators
- [ ] SCHEMA_AND_TYPE strategy
- [ ] STRUCTURED strategy
- [ ] NUMERIC strategy

### ID Serialization
- [x] Single ID field
- [x] Multiple ID fields with separator
- [x] PLAIN format
- [ ] STRUCTURED format
- [x] ID_ONLY, BOTH, FEATURE_ONLY modes

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
- [ ] Enum serialization options
- [ ] Extended metadata names

### Global Options
- [x] Smart compression (type omission)
- [ ] Smart compression (same-schema names)
- [ ] Field ordering
- [ ] Global feature ignore list
- [ ] NUMERIC mode

### Advanced
- [ ] Cross-resource references
- [ ] Custom value readers/writers
- [ ] SuperType serialization
- [ ] Annotation inheritance

---

## 3. Test Coverage

| Area | Status |
|------|--------|
| Round-trip serialization | ✅ |
| Type strategies | Partial |
| ID strategies | Partial |
| Reference handling | Partial |
| Configuration merging | ✅ |
| Smart compression | Partial |
| Error handling | TODO |

---

## 4. Next Steps (Priority Order)

1. ~~**Cross-Document Containment**: Detect and serialize as reference~~ ✅
2. ~~**Proxy Factory**: Create EMF proxies for non-containment references~~ ✅
3. ~~**Expand Feature**: Inline serialization of non-containment references~~ ✅
4. **Additional TypeStrategies**: SCHEMA_AND_TYPE, STRUCTURED, NUMERIC
5. **Custom Value Readers/Writers**: CodecValueRegistry integration
6. **OSGi Integration**: CodecResourceFactory for service registration

**Deferred:**
- Cross-Resource References (ResourceSet-based resolution)
- Expand Depth > 1
- SuperType serialization
- ID STRUCTURED format
- Smart compression (same-schema names)
- Field ordering options
- Enum serialization options

---

[Next: Open Questions →](99-open-questions.md)
