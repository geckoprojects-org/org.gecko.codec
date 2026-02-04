# Spec Reorganization Plan

**Status:** ✅ COMPLETED
**Created:** 2026-01-23
**Completed:** 2026-01-23

---

## Summary

The spec reorganization has been completed. All chapters have been revised with:
- Consistent cross-references
- Option key tables with Java constants and property keys
- Links to annotation-reference for definitive configuration lookup
- Proper navigation links

---

## Final Structure

### PART I: FOUNDATIONS (00-05)

| # | Document | Description |
|---|----------|-------------|
| 00 | overview.md | TOC with inline summaries, serialization targets |
| 01 | architecture.md | Component overview, flows, EffectiveCodecConfig |
| 02 | config-resolution.md | Source hierarchy + scope chain resolution |
| 03 | naming-conventions.md | Annotation keys, property keys, builder methods |
| 04 | common-types.md | SerializationFormat, StrategyScope, all enums |
| 05 | global-options.md | Smart compression, field ordering, defaults |

### PART II: SERIALIZATION FEATURES (06-12)

| # | Document | Description |
|---|----------|-------------|
| 06 | type.md | Type strategies (URI, NAME, NUMERIC, NONE) |
| 07 | supertype.md | SuperType serialization |
| 08 | discriminator-mapping.md | Type mapping registry, inline mapping |
| 09 | id.md | ID strategies, key modes, combined IDs |
| 10 | reference.md | Reference formats, expand, cross-document |
| 11 | feature.md | Feature keys, null/default handling, enums |
| 12 | polymorphism.md | Polymorphic references, annotation inheritance |

### PART III: RUNTIME & ADVANCED (13-15)

| # | Document | Description |
|---|----------|-------------|
| 13 | load-save-options.md | Root type, type hints, deser mode, value readers |
| 14 | custom-values.md | CodecValueReader/Writer, context interfaces, registration |
| 15 | error-handling.md | Diagnostics, error scenarios, severity, sources |

### PART IV: REFERENCE (16-19, 99)

| # | Document | Description |
|---|----------|-------------|
| 16 | annotation-reference.md | **Definitive reference** - all config options matrix |
| 17 | format-abstraction.md | Multi-format support, JSON Schema, OpenAPI |
| 18 | scenarios.md | Complete working examples |
| 19 | test-coverage.md | Test coverage matrix |
| 99 | open-questions.md | Design questions, implementation roadmap |

---

## Changes from Original Plan

The final structure differs slightly from the original approved plan:

| Original Plan | Final Structure | Reason |
|---------------|-----------------|--------|
| 13-scenarios | 13-load-save-options | User preference: runtime options after polymorphism |
| 14-load-save-options | 14-custom-values | Follows load-save naturally |
| 15-custom-values | 15-error-handling | Follows custom-values naturally |
| 16-error-handling | 16-annotation-reference | User preference: reference before format-abstraction |
| 18-annotation-reference | 18-scenarios | User preference: scenarios at end |

This ordering makes more sense because:
1. Load/save options are runtime concerns, follows polymorphism
2. Custom values naturally follows load-save (value readers used in load options)
3. Error handling follows custom values (custom readers report diagnostics)
4. Annotation reference is a reference doc, fits before format-abstraction
5. Scenarios are educational, good capstone before reference materials

---

## Completed Tasks

### Phase 1: Content Consolidation ✅
- [x] Error handling extracted from overview → 15-error-handling.md
- [x] Discriminator mapping extracted from type → 08-discriminator-mapping.md
- [x] Root element + type hints merged → 13-load-save-options.md
- [x] OpenAPI merged into format-abstraction → 17-format-abstraction.md
- [x] Common types created → 04-common-types.md

### Phase 2: File Renaming ✅
- [x] All files renamed to final numbers
- [x] Navigation links updated in all files

### Phase 3: Content Revision ✅
- [x] 13-load-save-options.md - Option key tables, cross-references
- [x] 14-custom-values.md - Context interfaces, getName(), registration
- [x] 15-error-handling.md - Serialization/deserialization sources, option tables
- [x] 16-annotation-reference.md - Diagnostic options, cross-references
- [x] 17-format-abstraction.md - Aligned with new context interfaces
- [x] 99-open-questions.md - Consolidated with implementation roadmap

### Phase 4: Cross-References ✅
- [x] All chapters link to annotation-reference for definitive config
- [x] Error handling linked from custom-values and format-abstraction
- [x] Load-save-options linked from custom-values

---

## Revision History

| Date | Changes |
|------|---------|
| 2026-01-23 | Initial proposal |
| 2026-01-23 | Decisions finalized |
| 2026-01-23 | **COMPLETED** - All phases done, structure finalized |
