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

*All questions resolved - see Resolved Questions below.*

---

## Recently Resolved

### Q1: SuperType Selection Default ✅
When supertypes are enabled, should ALL or SINGLE be the default?

**Resolution:** SINGLE (only direct parent) - simpler default, ALL can be explicitly enabled.

### Q2: Error Recovery ✅
Should deserialization continue on type resolution errors?

**Resolution:** Fail fast on first error (Option A). This aligns with Jackson's default behavior
(`DeserializationFeature.FAIL_ON_*` options) and provides clear error messages.

### Q3: Circular Reference Detection ✅
How to handle circular references during expansion?

**Resolution:** No opposite expansion. The `expandIgnoreBidirectional=true` default prevents cycles
through bidirectional references. Non-opposite circular references are the user's responsibility
to avoid in their expand configuration.

### Q4: Feature Order Guarantee ✅
Should DECLARATION order be guaranteed across JVM restarts?

**Resolution:** Yes, inherently stable. EMF feature order is determined by the model declaration
order in the .ecore file, which is stable across JVM restarts. No special handling needed.

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
