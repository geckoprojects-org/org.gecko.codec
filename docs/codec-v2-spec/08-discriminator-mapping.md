# Discriminator Mapping

[← SuperType Serialization](07-supertype.md) | [Next: ID Serialization →](09-id.md)

---

> **See also:** [Annotation Reference](16-annotation-reference.md) (Discriminator Mapping) for configuration key reference.

---

Discriminator mapping provides type resolution based on field values within the JSON data itself. This is a **separate layer** from TypeStrategy - it works alongside any strategy to translate arbitrary discriminator values to concrete EClasses.

## 1. Overview

### 1.1 Why Discriminator Mapping?

Standard TypeStrategy values (URI, NAME, CLASS, etc.) represent the type directly - the serialized value is self-describing. Discriminator mapping handles cases where the type information is encoded differently:

| Aspect | TypeStrategy (URI, NAME, etc.) | Discriminator Mapping |
|--------|-------------------------------|----------------------|
| **What it answers** | "What value represents this type?" | "How do I translate an arbitrary string to a type?" |
| **Serialization output** | Deterministic from EClass | Requires external mapping definition |
| **Deserialization input** | Self-describing (URI resolvable) | Requires registry lookup |
| **Standalone?** | Yes | No - needs discriminator definitions |

### 1.2 When to Use Discriminator Mapping

- **IoT/LoRaWAN devices** - device type embedded in payload metadata (e.g., `info.profileName`)
- **JSON Schema oneOf patterns** - type determined by feature presence or values
- **Legacy APIs** - type information encoded in application-specific fields
- **External APIs** - where you cannot control the type field name or format

### 1.3 Resolution Priority

During deserialization, type resolution follows this priority:

```
1. Type Mapping Registry (if configured on EClass or base class)
2. Inline Mapping (if configured on the EReference)
3. Type Strategy Resolution (URI, NAME, etc.)
4. Fallback (reference type, CODEC_FEATURE_TYPE_HINTS, CODEC_ROOT_TYPE)
```

> **Design rationale:** Type Mapping Registry has highest priority because it's our implementation of Jackson's `@JsonTypeInfo` + `@JsonSubTypes` pattern - the primary, flexible mechanism for polymorphic type handling. Inline Mapping is a simpler, more static variant for per-reference cases.

See [Type Serialization - Type Resolution Flow](06-type.md#530-type-resolution-flow) for the complete deserialization flow with all decision points.

---

## 2. Annotation Sources

Discriminator mappings use **dedicated annotation sources** (not the main `http://eclipse.org/fennec/codec` source):

| Feature | Annotation Source | Applies To |
|---------|------------------|------------|
| Type Mapping | `http://eclipse.org/fennec/codec/typeMapping/{mapId}` | EClass |
| Inline Mapping | `http://eclipse.org/fennec/codec/inlineMapping` | EReference |

---

## 3. Configuration Properties

| Annotation Key | Property Key | EClass | ERef | Description |
|----------------|--------------|:------:|:----:|-------------|
| — | `codec.typeMapId` | ✅ | ❌ | Registry ID (embedded in annotation source URI) |
| `typeDiscriminatorPath` | `codec.typeDiscriminatorPath` | ✅ | ❌ | JSON path to discriminator value (dot notation) |
| `{value}` | — | ✅ | ✅ | Mapping entries as direct key/value details |
| — | `codec.typeMappings` | ✅ | ❌ | Mappings as nested Map (for EClass property config) |
| — | `codec.inlineMappings` | ❌ | ✅ | Mappings as nested Map (for EReference property config) |
| `typeDiscriminator` | `codec.typeDiscriminator` | ✅ | ❌ | This class's discriminator value (distributed registration) |
| `fallbackStrategy` | `codec.fallbackStrategy` | ✅ | ✅ | `ERROR`, `SKIP`, `FALLBACK` (**default:** `SKIP`) |
| `fallbackEClass` | `codec.fallbackEClass` | ✅ | ✅ | Explicit fallback EClass URI |

> **Note:** The `{value}` entries are mapping entries where key = discriminator value and value = EClass URI. These are direct annotation details, not prefixed with `codec.`.

**Implementation:** `CodecAnnotationConstants.KEY_TYPE_MAP_ID`, `KEY_TYPE_DISCRIMINATOR`, `KEY_TYPE_DISCRIMINATOR_PATH`, `KEY_INLINE_MAPPINGS`, `ANNOTATION_SOURCE_TYPE_MAPPING_PREFIX`, `ANNOTATION_SOURCE_INLINE_MAPPING`

---

## 4. Type Mapping Registry (on EClass)

The type mapping registry defines discriminator-to-EClass mappings using a dedicated annotation source that includes the registry ID.

**Annotation source:** `http://eclipse.org/fennec/codec/typeMapping/{mapId}`

The `{mapId}` is embedded in the source URI, making it structurally impossible to forget.

### 4.1 Centralized Configuration (Static Mappings)

Define all mappings on the base class. Useful when you control all concrete classes and want the mapping in one place.

**How it works:**
1. **Base class** uses annotation source with `{mapId}` and defines `typeDiscriminatorPath` + all mappings
2. Mappings are direct key/value details (key = discriminator value, value = EClass URI)
3. Concrete classes don't need any annotations

```xml
<!-- UplinkMessage is the abstract base class -->
<eClassifiers name="UplinkMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec/typeMapping/lorawan-devices">
    <!-- JSON path where discriminator value is found (dot notation for nested) -->
    <details key="typeDiscriminatorPath" value="info.profileName"/>
    <!-- Mappings: discriminator value → EClass URI -->
    <details key="temp-sensor" value="http://example.org#//TemperatureSensor"/>
    <details key="humidity-sensor" value="http://example.org#//HumiditySensor"/>
    <details key="pressure-sensor" value="http://example.org#//PressureSensor"/>
  </eAnnotations>
</eClassifiers>
```

**Deserialization example:**
```json
{
  "info": {
    "profileName": "temp-sensor",
    "timestamp": "2026-01-23T10:00:00Z"
  },
  "value": 23.5
}
```
→ Deserializer reads `info.profileName` = `"temp-sensor"` → looks up in `lorawan-devices` registry → resolves to `TemperatureSensor`

### 4.2 Distributed Configuration (Self-Registration)

Concrete classes can register themselves with a registry. Useful when concrete classes are in different packages or when you want extensible type hierarchies.

**How it works:**
1. **Base class** defines the registry with `typeDiscriminatorPath`
2. **Concrete classes** register themselves using `typeDiscriminator` in the main codec annotation
3. At runtime, `TypeDiscriminatorService` maintains the registry and resolves types

**Base class configuration:**
```xml
<eClassifiers name="UplinkMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec/typeMapping/lorawan-devices">
    <details key="typeDiscriminatorPath" value="info.profileName"/>
  </eAnnotations>
</eClassifiers>
```

**Concrete class self-registration:**
```xml
<!-- TemperatureMessage extends UplinkMessage -->
<eClassifiers name="TemperatureMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <!-- Register with the registry, provide discriminator value -->
    <details key="typeMapId" value="lorawan-devices"/>
    <details key="typeDiscriminator" value="temperature-profile"/>
  </eAnnotations>
</eClassifiers>
```

### 4.3 Combining Static and Distributed Configuration

Static mappings and distributed registration **can be combined** in the same registry. This enables extensible type hierarchies where the base package defines known types, and extension packages can add new types without modifying the base.

**Base class with static mappings:**
```xml
<eClassifiers name="UplinkMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec/typeMapping/lorawan-devices">
    <details key="typeDiscriminatorPath" value="info.profileName"/>
    <!-- Known types defined statically -->
    <details key="temp-sensor" value="http://example.org#//TemperatureSensor"/>
    <details key="humidity-sensor" value="http://example.org#//HumiditySensor"/>
  </eAnnotations>
</eClassifiers>
```

**Extension class registering dynamically (e.g., from another package):**
```xml
<!-- FooMessage extends UplinkMessage, defined in a different package -->
<eClassifiers name="FooMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="typeMapId" value="lorawan-devices"/>
    <details key="typeDiscriminator" value="foo-bar"/>
  </eAnnotations>
</eClassifiers>
```

**Result:** The `lorawan-devices` registry contains:
- `temp-sensor` → `TemperatureSensor` (from static mapping)
- `humidity-sensor` → `HumiditySensor` (from static mapping)
- `foo-bar` → `FooMessage` (from distributed registration)

### 4.4 Programmatic Configuration

**Builder API:**
```java
ClassConfigBuilder.forEClass(ExamplePackage.Literals.UPLINK_MESSAGE)
    .typeMapId("lorawan-devices")
    .typeDiscriminatorPath("info.profileName")
    .addDiscriminatorMapping("temp-sensor", TemperaturePackage.Literals.TEMPERATURE_SENSOR)
    .addDiscriminatorMapping("humidity-sensor", HumidityPackage.Literals.HUMIDITY_SENSOR)
    .build();
```

**Property Map:**
```java
Map<String, Object> config = Map.of(
    "codec.typeMapId", "lorawan-devices",
    "codec.typeDiscriminatorPath", "info.profileName",
    "codec.typeMappings", Map.of(
        "temp-sensor", "http://example.org#//TemperatureSensor",
        "humidity-sensor", "http://example.org#//HumiditySensor"
    )
);
```

---

## 5. Inline Mapping (on EReference)

Inline mapping defines value→EClass mappings directly on an EReference using a dedicated annotation source. This is the simplest approach for small, reference-specific mappings.

**Annotation source:** `http://eclipse.org/fennec/codec/inlineMapping`

**How it works:**
1. **Main codec annotation** defines the type key (`typeKey`)
2. **Inline mapping annotation** defines mappings as direct key/value details
3. Only applies to objects accessed through this specific reference
4. Uses the same fallback and error handling as Type Mapping Registry

```xml
<eStructuralFeatures name="contacts" upperBound="-1" eType="#//Contact">
  <!-- Type key in main codec annotation -->
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="typeKey" value="contactType"/>
  </eAnnotations>
  <!-- Mappings in dedicated annotation -->
  <eAnnotations source="http://eclipse.org/fennec/codec/inlineMapping">
    <details key="friend" value="http://example.org#//Friend"/>
    <details key="enemy" value="http://example.org#//Enemy"/>
    <details key="colleague" value="http://example.org#//Colleague"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Deserialization example:**
```json
{
  "name": "John",
  "contacts": [
    { "contactType": "friend", "name": "Alice" },
    { "contactType": "enemy", "name": "Bob" },
    { "contactType": "colleague", "name": "Carol" }
  ]
}
```
→ Each contact's `contactType` is looked up in inline mapping → resolves to `Friend`, `Enemy`, `Colleague` respectively

### 5.1 Programmatic Configuration

For property maps or builder configuration, use `codec.inlineMappings`:

**Property Map:**
```java
// Per-EReference configuration with inline mappings
Map<EReference, Map<String, Object>> eReferenceConfig = new HashMap<>();

Map<String, Object> contactsConfig = Map.of(
    "codec.typeKey", "contactType",
    "codec.inlineMappings", Map.of(
        "friend", "http://example.org#//Friend",
        "enemy", "http://example.org#//Enemy",
        "colleague", ExamplePackage.Literals.COLLEAGUE  // EClass instance also works
    ),
    "codec.fallbackStrategy", "SKIP"
);
eReferenceConfig.put(PersonPackage.Literals.PERSON__CONTACTS, contactsConfig);

options.put("codec.eReferenceConfig", eReferenceConfig);
```

**Builder API:**
```java
CodecConfiguration.builder()
    .forReference(PersonPackage.Literals.PERSON__CONTACTS)
        .typeKey("contactType")
        .inlineMapping("friend", FriendPackage.Literals.FRIEND)
        .inlineMapping("enemy", EnemyPackage.Literals.ENEMY)
        .inlineMapping("colleague", ColleaguePackage.Literals.COLLEAGUE)
        .fallbackStrategy(FallbackStrategy.SKIP)
        .end()
    .build();
```

---

## 6. Fallback and Error Handling

When a discriminator value cannot be resolved to an EClass, the behavior is controlled by `fallbackStrategy` and `fallbackEClass`.

### 6.1 Fallback Strategy Values

| Value | Behavior |
|-------|----------|
| `SKIP` **(default)** | Log WARNING, continue to next resolution step |
| `ERROR` | Fail immediately, throw exception |
| `FALLBACK` | Use `fallbackEClass` (MUST be set, else ERROR) |

**"Next resolution step" depends on where fallback occurs:**
- Type Mapping Registry (step 1) fails with SKIP → try Inline Mapping (step 2), then Type Strategy (step 3)
- Inline Mapping (step 2) fails with SKIP → try Type Strategy (step 3)

> **Note:** `fallbackStrategy` only applies to Type Mapping Registry and Inline Mapping (steps 1 and 2 of type resolution). Type Strategy (step 3) does not have a configurable fallback strategy - if it cannot resolve the type, it continues to step 4 (Fallback hints: reference type, CODEC_FEATURE_TYPE_HINTS, CODEC_ROOT_TYPE).

### 6.2 Fallback Behavior

```
When discriminator value not found in mappings:

1. Check fallbackStrategy (default: SKIP):
   - ERROR    → Fail immediately
   - SKIP     → WARNING, continue to next resolution step
   - FALLBACK → Use fallbackEClass:
                 - If fallbackEClass is set → use it, RESOLVED ✓
                 - If fallbackEClass is NOT set → ERROR (misconfiguration)
```

**FALLBACK requires fallbackEClass:** When using `fallbackStrategy=FALLBACK`, you MUST configure `fallbackEClass`. This is explicit by design - if you want automatic fallback to reference type or hints, use `SKIP` instead (which continues to Type Strategy and eventually the hint-based fallback in step 4).

See [Type Serialization - Type Resolution Flow](06-type.md#630-type-resolution-flow) for the complete deserialization flow.

### 6.3 Relationship to Feature Type Hints

The `CODEC_FEATURE_TYPE_HINTS` load option (see [Load/Save Options](13-load-save-options.md)) integrates with inline mapping fallback resolution. This allows runtime control over fallback types without modifying the model:

```java
// Model defines inline mapping but no fallbackEClass
// At runtime, provide fallback via feature type hint
Map<EStructuralFeature, EClass> hints = Map.of(
    PersonPackage.Literals.PERSON__CONTACTS, ContactPackage.Literals.GENERIC_CONTACT
);
options.put(CODEC_FEATURE_TYPE_HINTS, hints);
```

**Priority:** `fallbackEClass` (model/config) takes precedence over `CODEC_FEATURE_TYPE_HINTS` (runtime).

### 6.4 Examples

```xml
<!-- Type Mapping with default SKIP behavior -->
<!-- If discriminator value not found → WARNING, continue to Type Strategy -->
<eAnnotations source="http://eclipse.org/fennec/codec/typeMapping/lorawan-devices">
  <details key="typeDiscriminatorPath" value="info.profileName"/>
  <!-- fallbackStrategy defaults to SKIP -->
  <details key="temp-sensor" value="http://example.org#//TemperatureSensor"/>
  <details key="humidity-sensor" value="http://example.org#//HumiditySensor"/>
</eAnnotations>

<!-- Type Mapping with explicit fallback EClass -->
<!-- Unknown discriminator → use GenericMessage -->
<eAnnotations source="http://eclipse.org/fennec/codec/typeMapping/lorawan-devices">
  <details key="typeDiscriminatorPath" value="info.profileName"/>
  <details key="fallbackStrategy" value="FALLBACK"/>
  <details key="fallbackEClass" value="http://example.org#//GenericMessage"/>
  <details key="temp-sensor" value="http://example.org#//TemperatureSensor"/>
  <details key="humidity-sensor" value="http://example.org#//HumiditySensor"/>
</eAnnotations>

<!-- Inline Mapping with ERROR (fail fast, strict mode) -->
<eAnnotations source="http://eclipse.org/fennec/codec/inlineMapping">
  <details key="fallbackStrategy" value="ERROR"/>
  <details key="friend" value="http://example.org#//Friend"/>
</eAnnotations>

<!-- Inline Mapping with default SKIP behavior -->
<!-- Unknown value → WARNING, continue to Type Strategy resolution -->
<eAnnotations source="http://eclipse.org/fennec/codec/inlineMapping">
  <details key="friend" value="http://example.org#//Friend"/>
  <details key="enemy" value="http://example.org#//Enemy"/>
</eAnnotations>
```

**Property keys for programmatic configuration:**

| Property Key | Description |
|--------------|-------------|
| `codec.fallbackStrategy` | `ERROR`, `SKIP`, `FALLBACK` |
| `codec.fallbackEClass` | EClass URI string or EClass instance |

---

## 7. Invalid Configurations

| Misconfiguration | Severity | Reason |
|------------------|----------|--------|
| `typeDiscriminator` on EReference | ERROR | Discriminator values are per-class, not per-reference |
| `typeDiscriminatorPath` on EReference | ERROR | Discriminator path is defined on base class |
| `typeMapId` on EReference | ERROR | Type mapping registry is class-level |
| `inlineMapping` annotation on EClass | WARNING | Inline mappings are per-reference only |

---

## 8. Summary

| Feature | Annotation Source | Applies To | Use Case |
|---------|------------------|------------|----------|
| **Type Mapping Registry** | `http://eclipse.org/fennec/codec/typeMapping/{mapId}` | EClass | Centralized or distributed type mappings |
| **Inline Mapping** | `http://eclipse.org/fennec/codec/inlineMapping` | EReference | Per-reference type mappings |

**Key differences:**
- Type Mapping Registry defines mappings on the base class (centralized) or concrete classes (distributed)
- Inline Mapping defines mappings on the EReference that accesses the objects
- Both support fallback strategies (`ERROR`, `SKIP`, `FALLBACK`)

---

[Next: ID Serialization →](09-id.md)
