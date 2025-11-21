# CSV Codec: Validation & Security Features

## Übersicht

Die CSV Codec Implementierung nutzt die erweiterten Features von **FastCSV 4.1** für **Validierung** und **Sicherheit**. Diese Anleitung zeigt alle verfügbaren Features und deren Verwendung.

---

## 🔒 Sicherheits-Features

### 1. **Max Field Length** (DoS-Schutz)

Verhindert Denial-of-Service-Angriffe durch extrem große Felder:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setMaxFieldLength(1_000_000); // 1MB pro Feld

IndexedCSVParser parser = new IndexedCSVParser(inputStream, config);
```

**Standard:** 10MB pro Feld
**Empfehlung:** 1MB für öffentliche APIs

### 2. **Max Row Length** (Memory-Schutz)

Verhindert Speicher-Erschöpfung durch sehr breite Zeilen:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setMaxRowLength(10_000_000); // 10MB pro Zeile

FastCSVParser.parseAll(inputStream, config);
```

**Standard:** 100MB pro Zeile
**Empfehlung:** 10MB für normale Anwendungen

### 3. **Secure Configuration**

Vorkonfigurierte sichere Einstellungen:

```java
CSVReaderConfig config = CSVReaderConfig.secureConfig();
// Setzt:
// - maxFieldLength: 1MB
// - maxRowLength: 10MB
// - skipEmptyRows: true
// - errorOnDifferentFieldCount: true

IndexedCSVParser parser = new IndexedCSVParser(inputStream, config);
```

---

## ✅ Validierungs-Features

### 1. **Field Count Validation**

Prüft, ob alle Zeilen die gleiche Anzahl Felder haben:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setErrorOnDifferentFieldCount(true);

try {
    Map<String, Object> row = FastCSVParser.parse(inputStream, config);
} catch (IOException e) {
    // Fehler: "CSV validation error at line 5: Expected 10 fields but found 8"
    System.err.println(e.getMessage());
}
```

### 2. **Expected Field Count**

Explizite Angabe der erwarteten Feldanzahl:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setExpectedFieldCount(10)  // Erwarte genau 10 Felder
    .setErrorOnDifferentFieldCount(true);

IndexedCSVParser parser = new IndexedCSVParser(inputStream, config);
```

### 3. **Skip Empty Rows**

Leere Zeilen automatisch überspringen:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setSkipEmptyRows(true);  // Standard: true

List<Map<String, Object>> rows = FastCSVParser.parseAll(inputStream, config);
```

### 4. **Whitespace Handling**

Leading/Trailing Whitespace entfernen:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setIgnoreLeadingWhitespace(true)
    .setIgnoreTrailingWhitespace(true);

// CSV: "  John  ,  Doe  "
// Result: "John", "Doe"
Map<String, Object> row = FastCSVParser.parse(inputStream, config);
```

### 5. **Strict Configuration**

Vorkonfigurierte strenge Validierung:

```java
CSVReaderConfig config = CSVReaderConfig.strictConfig();
// Setzt:
// - errorOnDifferentFieldCount: true
// - skipEmptyRows: true
// - ignoreLeadingWhitespace: true
// - ignoreTrailingWhitespace: true

List<Map<String, Object>> rows = FastCSVParser.parseAll(inputStream, config);
```

---

## 🔧 Konfigurations-Features

### 1. **Custom Field Separator**

Verschiedene Trennzeichen unterstützen:

```java
// Semicolon (European CSV)
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .useSemicolonSeparator();

// Tab (TSV)
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .useTabSeparator();

// Pipe
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .usePipeSeparator();

// Custom
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setFieldSeparator('|');
```

### 2. **Custom Quote Character**

Anführungszeichen anpassen:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setQuoteCharacter('\'');  // Single quote statt double quote

// CSV: 'John','Doe'
```

### 3. **Comment Support**

Kommentarzeilen überspringen:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setCommentEnabled(true)
    .setCommentCharacter('#');

// CSV:
// # This is a comment
// John,Doe
// # Another comment
// Jane,Smith
```

### 4. **Character Encoding**

Custom Encoding:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setCharset(StandardCharsets.ISO_8859_1);

// oder UTF-16
config.setCharset(StandardCharsets.UTF_16);
```

---

## 📊 Vorkonfigurierte Profile

### Default Config
```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig();
```
- Field separator: `,`
- Quote character: `"`
- Max field length: 10MB
- Max row length: 100MB
- Skip empty rows: true
- No validation

### Strict Config
```java
CSVReaderConfig config = CSVReaderConfig.strictConfig();
```
- Alle default-Einstellungen
- **+ Field count validation**
- **+ Whitespace trimming**
- Ideal für: Datenqualität sicherstellen

### Secure Config
```java
CSVReaderConfig config = CSVReaderConfig.secureConfig();
```
- **Max field length: 1MB** (DoS-Schutz)
- **Max row length: 10MB** (Memory-Schutz)
- Field count validation
- Skip empty rows
- Ideal für: Öffentliche APIs, User-Uploads

### Permissive Config
```java
CSVReaderConfig config = CSVReaderConfig.permissiveConfig();
```
- Keine Field count validation
- Skip empty rows
- Whitespace trimming
- Ideal für: Variabel formatierte CSV-Dateien

---

## 💡 Verwendungsbeispiele

### Beispiel 1: Sichere API für User-Uploads

```java
// Sichere Konfiguration für User-generierte CSVs
CSVReaderConfig config = CSVReaderConfig.secureConfig()
    .setMaxFieldLength(100_000)    // 100KB pro Feld
    .setMaxRowLength(1_000_000)    // 1MB pro Zeile
    .setExpectedFieldCount(5);     // Erwarte 5 Felder

try {
    IndexedCSVParser parser = new IndexedCSVParser(userUploadStream, config);

    // Lazy loading - kein OOM bei großen Dateien
    for (long i = 0; i < parser.getRowCount(); i++) {
        Map<String, Object> row = parser.getRow(i);
        processRow(row);
    }
} catch (IOException e) {
    // Fehler-Handling mit Zeilennummer
    logger.error("CSV validation failed: " + e.getMessage());
}
```

### Beispiel 2: Strict Data Warehouse Import

```java
// Strenge Validierung für Data Warehouse
CSVReaderConfig config = CSVReaderConfig.strictConfig()
    .setExpectedFieldCount(20)           // Genau 20 Felder
    .setErrorOnDifferentFieldCount(true) // Fehler bei Abweichung
    .setCommentEnabled(true);            // Kommentare erlauben

try {
    List<Map<String, Object>> allRows =
        FastCSVParser.parseAll(warehouseInputStream, config);

    // Alle Zeilen wurden validiert
    importToDatabase(allRows);
} catch (IOException e) {
    // Fehler mit Zeilennummer für Debugging
    throw new DataImportException("Invalid CSV format: " + e.getMessage());
}
```

### Beispiel 3: European CSV (Semicolon)

```java
// Europäische CSVs mit Semicolon
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .useSemicolonSeparator()             // ';' statt ','
    .setIgnoreLeadingWhitespace(true)
    .setIgnoreTrailingWhitespace(true);

// CSV: "John; Doe; 1.234,56"
Map<String, Object> row = FastCSVParser.parse(europeInputStream, config);
```

### Beispiel 4: TSV (Tab-Separated Values)

```java
// TSV-Dateien parsen
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .useTabSeparator();  // '\t' als Separator

List<Map<String, Object>> rows =
    FastCSVParser.parseAll(tsvInputStream, config);
```

### Beispiel 5: Lazy Loading mit Validierung

```java
// Große Datei mit Lazy Loading und Validierung
CSVReaderConfig config = CSVReaderConfig.secureConfig();

try (IndexedCSVParser parser = new IndexedCSVParser(largeFile, config)) {

    // Random access mit Validierung
    Map<String, Object> row100 = parser.getRow(100);

    // Reference resolution
    String refId = (String) row100.get("customer_id");
    Map<String, Object> customerRow = parser.findRowByValue("id", refId);

    // Batch processing
    List<Map<String, Object>> batch = parser.getRows(0, 1000);

} catch (IOException e) {
    System.err.println("Error: " + e.getMessage());
}
```

---

## 🛡️ Error Handling

### Line Number in Exceptions

FastCSV bietet detaillierte Fehlerinformationen:

```java
CSVReaderConfig config = CSVReaderConfig.strictConfig()
    .setExpectedFieldCount(5);

try {
    IndexedCSVParser parser = new IndexedCSVParser(inputStream, config);
    Map<String, Object> row = parser.getRow(42);
} catch (IOException e) {
    // Fehler-Meldung enthält Zeilennummer:
    // "CSV validation error at row 42 (line 43): Expected 5 fields but found 3"
    logger.error(e.getMessage());
}
```

### Chained Exceptions

Ermöglicht besseres Exception-Handling:

```java
CSVReaderConfig config = CSVReaderConfig.defaultConfig()
    .setAcceptChainedExceptions(true);  // Standard: true

try {
    List<Map<String, Object>> rows = FastCSVParser.parseAll(inputStream, config);
} catch (IOException e) {
    // Original-Exception ist verfügbar
    Throwable cause = e.getCause();
    logger.error("Root cause: " + cause.getMessage());
}
```

---

## 🚀 Performance-Tipps

### 1. Security vs. Performance

```java
// Hohe Sicherheit (langsamer)
CSVReaderConfig secure = CSVReaderConfig.secureConfig()
    .setMaxFieldLength(10_000);  // Klein = sicherer aber langsamer

// Hohe Performance (weniger sicher)
CSVReaderConfig fast = CSVReaderConfig.defaultConfig()
    .setMaxFieldLength(100_000_000);  // Groß = schneller aber risikoreicher
```

### 2. Validation vs. Speed

```java
// Strenge Validation (langsamer)
CSVReaderConfig strict = CSVReaderConfig.strictConfig();

// Keine Validation (schneller)
CSVReaderConfig permissive = CSVReaderConfig.permissiveConfig()
    .setErrorOnDifferentFieldCount(false);
```

### 3. Indexed vs. Fast Mode

```java
// Fast Mode: Ganze Datei laden (schnell für kleine Dateien)
List<Map<String, Object>> all = FastCSVParser.parseAll(smallFile, config);

// Indexed Mode: Lazy loading (schnell für große Dateien)
IndexedCSVParser parser = new IndexedCSVParser(largeFile, config);
Map<String, Object> row = parser.getRow(1000000);  // Nur diese Zeile laden!
```

---

## 📋 Zusammenfassung der Features

| Feature | Zweck | API |
|---------|-------|-----|
| **Max Field Length** | DoS-Schutz | `setMaxFieldLength(int)` |
| **Max Row Length** | Memory-Schutz | `setMaxRowLength(int)` |
| **Field Count Validation** | Datenqualität | `setErrorOnDifferentFieldCount(boolean)` |
| **Expected Field Count** | Struktur-Check | `setExpectedFieldCount(int)` |
| **Skip Empty Rows** | Datenbereinigung | `setSkipEmptyRows(boolean)` |
| **Whitespace Trimming** | Datenbereinigung | `setIgnoreLeadingWhitespace(boolean)` |
| **Custom Separator** | Format-Flexibilität | `setFieldSeparator(char)` |
| **Comment Support** | Metadaten | `setCommentEnabled(boolean)` |
| **Custom Encoding** | Internationalisierung | `setCharset(Charset)` |
| **Line Number Errors** | Debugging | Automatisch in Exceptions |

---

## ⚙️ Standard-Werte

```java
CSVReaderConfig defaultConfig = CSVReaderConfig.defaultConfig();

// Werte:
fieldSeparator: ','
quoteCharacter: '"'
commentCharacter: '#'
commentEnabled: false
charset: UTF-8
maxFieldLength: 10_000_000 (10MB)
maxRowLength: 100_000_000 (100MB)
skipEmptyRows: true
ignoreLeadingWhitespace: false
ignoreTrailingWhitespace: false
errorOnDifferentFieldCount: false
acceptChainedExceptions: true
expectedFieldCount: null (automatisch)
```

---

## 🔐 Security Best Practices

1. **Für öffentliche APIs:**
   ```java
   CSVReaderConfig config = CSVReaderConfig.secureConfig()
       .setMaxFieldLength(100_000)    // 100KB
       .setMaxRowLength(1_000_000);   // 1MB
   ```

2. **Für User-Uploads:**
   ```java
   CSVReaderConfig config = CSVReaderConfig.secureConfig()
       .setExpectedFieldCount(expectedCount)
       .setErrorOnDifferentFieldCount(true);
   ```

3. **Für Data Warehouse:**
   ```java
   CSVReaderConfig config = CSVReaderConfig.strictConfig()
       .setExpectedFieldCount(schemaFieldCount);
   ```

4. **Für unbekannte Quellen:**
   ```java
   CSVReaderConfig config = CSVReaderConfig.secureConfig();
   // Niedrige Limits + Validation
   ```

---

## Autor
- **Claude Code** - FastCSV 4.1 Validation & Security Integration
- **Data In Motion** - CSV Codec Framework

## License
Eclipse Public License 2.0 (EPL-2.0)

## Version
1.0.0-SNAPSHOT

## Datum
November 21, 2025
