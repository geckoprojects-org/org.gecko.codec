# Offene Fragen - OpenAPI Ecore Modell v2

Dieses Dokument listet Punkte auf, bei denen ich unsicher war und die wir gemeinsam klären sollten.

---

## ~~1. "Any"-Typen in OpenAPI~~ ✅ GELÖST

**Lösung:** `EObject` mit `containment="true"` verwenden. Der konkrete Typ wird zur Laufzeit über die neue `CODEC_FEATURE_TYPE_HINTS` Load-Option bestimmt.

Siehe: `docs/codec-v2-spec/18-feature-type-hints.md`

Betroffene Features im Modell:
- `Example.value` → `EObject`
- `Extension.value` → `EObject`
- `Link.requestBody` → `EObject`
- `Link.parameters` → `AnyEntry[*]` (Map mit EObject-Values)

---

## ~~2. PathItem Design-Entscheidung~~ ✅ GELÖST

**Lösung:** Flaches Design mit einer `Operation`-Klasse beibehalten.

Zusätzlich: `HttpMethod` Enum auf Operation hinzugefügt:
```
Operation {
  method : HttpMethod  // GET, PUT, POST, DELETE, OPTIONS, HEAD, PATCH, TRACE
  // ... rest
}
```

- Das `method`-Feld wird vom Deserializer basierend auf dem PathItem-Key gesetzt
- Mit `serialize=false` annotiert - wird nicht ins JSON geschrieben
- **TODO**: Deserializer-Logik implementieren, die das Feld beim Einlesen setzt

---

## ~~3. Schema.additionalProperties~~ ✅ GELÖST

**Lösung:** Zwei separate Felder:

```
Schema {
  additionalProperties : Schema           // Schema-Fall
  additionalPropertiesAllowed : EBooleanObject  // Boolean-Fall (null/true/false)
}
```

Semantik:
- `additionalProperties` gesetzt → Schema-Validierung für zusätzliche Props
- `additionalPropertiesAllowed = true` → beliebige Props erlaubt
- `additionalPropertiesAllowed = false` → keine zusätzlichen Props
- `additionalPropertiesAllowed = null` → nicht spezifiziert (default)
- Wenn beide gesetzt: `additionalProperties` (Schema) hat Vorrang

**TODO**: Custom ValueReader/Writer für korrekte JSON-Serialisierung (`additionalProperties: true/false` vs `additionalProperties: {...}`)

---

## ~~4. Enum-Serialisierung~~ ✅ GELÖST

**Lösung:** Kein Handlungsbedarf - funktioniert mit Default-Strategie.

Der Codec unterstützt Enum-Strategien (LITERAL, NAME, VALUE). Mit `LITERAL` (Default) wird der Enum-Literal-Name serialisiert.

Die Enum-Literale sind bereits korrekt definiert:
- `ParameterLocation`: query, header, path, cookie
- `ParameterStyle`: matrix, label, form, simple, ...
- `SecuritySchemeType`: apiKey, http, oauth2, openIdConnect
- `ApiKeyLocation`: query, header, cookie
- `HttpMethod`: GET, POST, ... (aber `serialize=false`, wird nicht serialisiert)

---

## ~~5. components.schemas - EPackage Integration~~ ✅ GELÖST

**Lösung:** Duale Repräsentation - beide Ansätze kombiniert:

```
Components {
  schemas : SchemaEntry[*]     // OpenAPI-konforme JSON-Schema Darstellung
  schemasPackage : EPackage    // EMF-native Darstellung (wenn konvertierbar)
}
```

**Verhalten:**
- `schemas` → Immer gefüllt bei Deserialisierung, wird serialisiert
- `schemasPackage` → Gefüllt wenn JSON-Schema → EPackage erfolgreich, `serialize=false`

**Vorteile:**
- ✅ Spec-konforme Roundtrips über `schemas`
- ✅ Direkte EMF-Nutzung über `schemasPackage`
- ✅ Fallback wenn Konvertierung fehlschlägt

**TODO**: Deserializer-Logik die beide Felder befüllt (schemas immer, schemasPackage wenn möglich)

---

## ~~6. XML Object~~ ✅ GELÖST

**Entscheidung:** Erstmal weggelassen - bei Bedarf später implementieren.

Falls benötigt, wäre hinzuzufügen:
```
Schema {
  xml : XML
}

XML {
  name : EString
  namespace : EString
  prefix : EString
  attribute : EBoolean
  wrapped : EBoolean
}
```

---

## ~~7. $ref Handling~~ ✅ GELÖST

**Entscheidung:** `ref` Attribut zu allen betroffenen Klassen hinzugefügt (ohne Interface).

Klassen mit `ref : EString` (annotiert mit `key="$ref"`):
- Schema
- PathItem
- Parameter
- RequestBody
- Response
- Header
- Callback
- Example
- Link

Ein Interface bringt wenig Mehrwert, da der Codec das Feld sowieso pro Feature behandelt.

---

## ~~8. Callback Expression Keys~~ ✅ GELÖST

**Entscheidung:** String als Key reicht aus.

```yaml
callbacks:
  onPayment:
    '{$request.body#/callbackUrl}':  # Expression als Key
      post:
        ...
```

Die Runtime Expression wird einfach als String gespeichert. Parsing/Auswertung ist Sache der Runtime, nicht des Modells.

---

## Zusammenfassung

**Alle Fragen gelöst!** ✅

| # | Thema | Entscheidung |
|---|-------|--------------|
| 1 | Any-Typen | `EObject` + `CODEC_FEATURE_TYPE_HINTS` Load-Option |
| 2 | PathItem Design | Flach mit `HttpMethod` Enum auf Operation |
| 3 | additionalProperties | Zwei Felder: Schema + Boolean |
| 4 | Enum-Serialisierung | Kein Handlungsbedarf - LITERAL Default |
| 5 | EPackage Integration | Duale Repräsentation: schemas + schemasPackage |
| 6 | XML Object | Weggelassen - bei Bedarf später |
| 7 | $ref Handling | `ref` Attribut auf allen betroffenen Klassen |
| 8 | Callback Expressions | String als Key reicht |

### Offene TODOs für Implementierung

1. Deserializer-Logik: `Operation.method` aus PathItem-Key setzen
2. Custom ValueReader/Writer für `additionalProperties` (boolean vs Schema)
3. Deserializer-Logik: `schemas` und `schemasPackage` befüllen
