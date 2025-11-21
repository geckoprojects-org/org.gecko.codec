# CSV Codec Documentation

## Overview

Die CSV Codec Implementierung bietet zwei verschiedene Modi für das Parsen von CSV-Dateien mit unterschiedlichen Performance-Charakteristiken:

1. **Fast Mode (Standard)** - Schnelles Laden ganzer CSV-Zeilen
2. **Indexed Mode (Lazy Loading)** - Speichereffizientes, verzögertes Laden für große Dateien

Beide Modi basieren auf der **FastCSV 4.1** Bibliothek von Siegmar Schubert.

---

## Fast Mode

### Beschreibung
Der Fast Mode lädt CSV-Daten effizient und schnell. Ideal für:
- Kleine bis mittelgroße CSV-Dateien
- Wenn alle Daten sofort benötigt werden
- Batch-Verarbeitung von CSV-Zeilen

### Verwendete Klassen
- `FastCSVParser` - Wrapper für FastCSV CsvReader
- `CodecCSVParserFast` - Jackson Parser-Implementierung
- `CSVParserFactory` - Factory für Fast Mode Parser
- `CSVCodecFactoryConfigurator` - OSGi Component (type=csv)

### Verwendung

```java
// OSGi Configuration
@WithFactoryConfiguration(
    factoryPid = "DefaultCodecModuleConfigurator",
    location = "?",
    name = "csv"
)

// Im Code
CSVResourceFactory csvFactory = ...; // OSGi Service
Resource resource = csvFactory.createResource(URI.createURI("data.csv"));

Map<String, Object> options = new HashMap<>();
options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, MyEClass);

resource.load(inputStream, options);
EObject object = resource.getContents().get(0);
```

### Features
- ✅ Schnelles Parsen
- ✅ Einfache API
- ✅ Header-Erkennung automatisch
- ✅ UTF-8 Unterstützung
- ❌ Nicht für sehr große Dateien (> 1GB)

---

## Indexed Mode (Lazy Loading)

### Beschreibung
Der Indexed Mode verwendet FastCSV's `IndexedCsvReader` für:
- Lazy Loading von CSV-Zeilen
- Random Access zu beliebigen Zeilen
- Speichereffiziente Verarbeitung großer Dateien
- On-demand Auflösung von ECore-Referenzen

### Verwendete Klassen
- `IndexedCSVParser` - Wrapper für FastCSV IndexedCsvReader
- `CodecCSVParserIndexed` - Jackson Parser mit Lazy Loading
- `CSVParserFactoryIndexed` - Factory für Indexed Mode
- `CSVCodecFactoryConfiguratorIndexed` - OSGi Component (type=csv-indexed)

### Verwendung

```java
// OSGi Configuration
@WithFactoryConfiguration(
    factoryPid = "DefaultCodecModuleConfigurator",
    location = "?",
    name = "csv-indexed"
)
@WithFactoryConfiguration(
    factoryPid = "DefaultObjectMapperConfigurator",
    location = "?",
    name = "csv",
    properties = {
        @Property(key = "type", value="csv-indexed"),
        @Property(key = "codecFactoryConfigurator.target", value="(type=csv-indexed)")
    }
)

// Im Code - Random Access
IndexedCSVParser parser = new IndexedCSVParser(inputStream);

// Zeile 42 laden
Map<String, Object> row42 = parser.getRow(42);

// Zeilen 10-20 laden
List<Map<String, Object>> rows = parser.getRows(10, 20);

// Suche nach Referenz
Map<String, Object> row = parser.findRowByValue("id", "REF-123");
```

### Advanced: On-Demand Reference Resolution

```java
// ECore Objekt mit Referenz lazy laden
CodecCSVParserIndexed parser = new CodecCSVParserIndexed(context, inputStream);

// Beim Deserialisieren wird nur die aktuelle Zeile geladen
EObject mainObject = deserialize(parser);

// Referenzen können bei Bedarf aufgelöst werden:
String referenceId = mainObject.eGet(referenceFeature).toString();

// Suche die referenzierte Zeile
Map<String, Object> referencedRow =
    parser.getIndexedParser().findRowByValue("id", referenceId);

// Deserialisiere referenziertes Objekt
EObject referencedObject = createFromRow(referencedRow);
```

### Features
- ✅ Speichereffizient für große Dateien
- ✅ Random Access zu beliebigen Zeilen
- ✅ Lazy Loading - nur bei Bedarf laden
- ✅ Referenz-Auflösung on-demand
- ✅ Geeignet für Dateien mit Millionen von Zeilen
- ❌ Etwas komplexere API
- ❌ Overhead für kleine Dateien

---

## Vergleich

| Feature | Fast Mode | Indexed Mode |
|---------|-----------|--------------|
| **Geschwindigkeit** | Sehr schnell | Schnell |
| **Speicherverbrauch** | Hoch bei großen Dateien | Niedrig |
| **Random Access** | ❌ Nein | ✅ Ja |
| **Lazy Loading** | ❌ Nein | ✅ Ja |
| **Dateigrößenlimit** | < 1 GB empfohlen | Unbegrenzt |
| **API-Komplexität** | Einfach | Mittel |
| **Use Case** | Standard CSV-Verarbeitung | Große Dateien, Reference Resolution |

---

## Implementation Details

### FastCSV 4.1 Integration

Beide Modi nutzen FastCSV 4.1:

**Maven Dependency** (bereits in `cnf/central.mvn`):
```
de.siegmar:fastcsv:4.1.0
```

**Build Path** (bereits in `org.eclipse.fennec.codec.csv/bnd.bnd`):
```
de.siegmar.fastcsv
```

### Architektur

```
CodecResource (EMF)
    ↓
CSVResource (CSV-spezifisch)
    ↓
ObjectMapper (Jackson)
    ↓
CodecCSVParser[Fast|Indexed] (Jackson Parser)
    ↓
[FastCSVParser|IndexedCSVParser] (FastCSV Wrapper)
    ↓
FastCSV 4.1 Library
```

---

## Migration von alter Implementierung

### Alte Implementation (QueryStringParser)
```java
// Alt: URL Query String Format
// key1=value1&key2=value2&key3=value3
QueryStringParser.parse(inputStream);
```

### Neue Implementation (FastCSV)
```java
// Neu: Echtes CSV Format
// header1,header2,header3
// value1,value2,value3
FastCSVParser.parse(inputStream);
```

### Breaking Changes
- ❌ QueryStringParser wird nicht mehr verwendet
- ✅ CodecCSVParser verwendet jetzt CodecCSVParserFast
- ✅ Echter CSV-Support statt Query-String-Format
- ✅ Tests müssen nicht mehr CSV → Query-String konvertieren

---

## Beispiel: Test Migration

### Alt
```java
// CSV-Datei manuell zu Query-String konvertieren
CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT);
for (CSVRecord record : csvParser) {
    StringBuilder sb = new StringBuilder();
    for (String header : headers) {
        sb.append(header + "=" + record.get(header) + "&");
    }
    String queryString = sb.toString();
    resource.load(new ByteArrayInputStream(queryString.getBytes()), options);
}
```

### Neu
```java
// Direkt CSV laden - keine Konvertierung nötig!
resource.load(csvInputStream, options);
```

---

## Performance Tips

### Fast Mode
- Verwenden für Dateien < 100 MB
- Perfekt für Batch-Processing
- Geeignet wenn alle Daten benötigt werden

### Indexed Mode
- Verwenden für Dateien > 100 MB
- Ideal für selektives Laden
- Nutzen für Streaming-Szenarien
- Perfekt für Reference Resolution

---

## Troubleshooting

### Problem: "No CodecFactoryConfigurator found"
**Lösung**: OSGi Component nicht registriert
```java
@WithFactoryConfiguration(
    factoryPid = "DefaultObjectMapperConfigurator",
    properties = {
        @Property(key = "type", value="csv"), // oder "csv-indexed"
        @Property(key = "codecFactoryConfigurator.target",
                  value="(type=csv)") // oder "(type=csv-indexed)"
    }
)
```

### Problem: "IndexedCSVParser throws IOException"
**Lösung**: Datei muss seekable sein (kein Pipe/Stream)
```java
// Schlecht: Pipe
InputStream pipe = process.getInputStream();

// Gut: File oder ByteArray
InputStream file = new FileInputStream("data.csv");
InputStream bytes = new ByteArrayInputStream(csvData);
```

### Problem: "Out of Memory bei großen CSV"
**Lösung**: Indexed Mode verwenden statt Fast Mode
```java
// Wechsel von Fast Mode zu Indexed Mode
type = "csv-indexed" // statt "csv"
```

---

## Autor
- **Claude Code** - FastCSV 4.1 Integration & Lazy Loading
- **Data In Motion** - Original CSV Codec Framework
- **Ilenia** - Original Implementation

## License
Eclipse Public License 2.0 (EPL-2.0)

## Version
1.0.0-SNAPSHOT

## Datum
November 21, 2025
