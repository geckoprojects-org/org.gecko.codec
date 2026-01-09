# Open Questions

[← Back to Overview](00-overview.md) | [← Implementation](14-implementation.md)

---

## Resolved Questions

1. **Type strategy default** ✅ → URI (full EClass URI)
2. **Reference format default** ✅ → STRUCTURED (with `_type` and `_ref`)
3. **Smart compression behavior** ✅ → Omit type when instance equals declared type
4. **Configuration hierarchy** ✅ → Dynamic before static, 6 levels
5. **Proxy handling** ✅ → Create EMF proxies for unresolved references

---

## Open Questions

### Q1: SuperType Selection Default
When supertypes are enabled, should ALL or SINGLE be the default?

**Current:** ALL (full hierarchy excluding EMF base)
**Alternative:** SINGLE (only direct parent)

### Q2: Error Recovery
Should deserialization continue on type resolution errors?

**Option A:** Fail fast on first error
**Option B:** Collect all errors, fail at end
**Option C:** Create placeholder objects, report warnings

### Q3: Circular Reference Detection
How to handle circular references during expansion?

**Current:** expandIgnoreBidirectional=true prevents most cycles
**Issue:** Non-opposite circular refs still possible

### Q4: Feature Order Guarantee
Should DECLARATION order be guaranteed across JVM restarts?

**Note:** EMF feature order depends on model declaration order, which should be stable.

---

## Spec Issues to Resolve

### Issue 1: PLAIN vs STRUCTURED Default
The spec previously said PLAIN was default for references, but this contradicts the need for type information.

**Resolution:** Changed to STRUCTURED as default.

### Issue 2: Smart Compression Documentation
Smart compression was documented in multiple places with slightly different descriptions.

**Resolution:** Consolidated in Section 04-global-options.md.

---

## Future Considerations

1. **Binary format support** - CBOR, MessagePack
2. **Schema generation** - JSON Schema from EMF
3. **Streaming API** - For large documents
4. **Validation hooks** - Custom validators during deserialization

---

[← Back to Overview](00-overview.md)
