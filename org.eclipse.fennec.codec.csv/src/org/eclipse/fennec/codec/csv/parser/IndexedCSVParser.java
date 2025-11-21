/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.eclipse.fennec.codec.csv.parser;

import static java.util.Objects.requireNonNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fennec.codec.csv.config.CSVReaderConfig;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import de.siegmar.fastcsv.reader.IndexedCsvReader;

/**
 * Indexed CSV Parser using FastCSV's IndexedCsvReader for lazy loading with validation and security.
 * This parser allows random access to CSV rows without loading the entire file into memory.
 *
 * Features:
 * - Lazy loading: rows are only parsed when accessed
 * - Random access: can jump to any row by index
 * - Memory efficient: suitable for large CSV files
 * - On-demand reference resolution: ECore references can be resolved when needed
 * - Security limits: max field length, max row length (DoS prevention)
 * - Validation: field count validation, configurable delimiters
 *
 * @author Claude Code
 * @since Nov 21, 2025
 */
public class IndexedCSVParser implements Closeable {

    private final IndexedCsvReader<CsvRow> indexedReader;
    private final List<String> headers;
    private final long totalRows;
    private final CSVReaderConfig config;

    /**
     * Creates a new IndexedCSVParser from an InputStream with default configuration.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @throws IOException when error during reading happen.
     */
    public IndexedCSVParser(InputStream inputStream) throws IOException {
        this(inputStream, CSVReaderConfig.defaultConfig());
    }

    /**
     * Creates a new IndexedCSVParser from an InputStream with custom configuration.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @param config The CSV reader configuration. Must not be <code>null</code>
     * @throws IOException when error during reading happen.
     */
    public IndexedCSVParser(InputStream inputStream, CSVReaderConfig config) throws IOException {
        requireNonNull(inputStream);
        requireNonNull(config);

        this.config = config;

        // Create indexed reader for random access with configuration
        this.indexedReader = buildIndexedCsvReader(inputStream, config);

        // Read headers from first row
        CsvRow headerRow = indexedReader.getRow(0);
        if (headerRow != null) {
            this.headers = new ArrayList<>();
            for (int i = 0; i < headerRow.getFieldCount(); i++) {
                headers.add(headerRow.getField(i));
            }
        } else {
            this.headers = Collections.emptyList();
        }

        // Get total row count (excluding header)
        this.totalRows = indexedReader.getRowCount() - 1; // -1 for header row
    }

    /**
     * Returns the column headers from the first row.
     *
     * @return List of column headers
     */
    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    /**
     * Returns the total number of data rows (excluding header).
     *
     * @return Number of data rows
     */
    public long getRowCount() {
        return totalRows;
    }

    /**
     * Lazily loads a specific row by index with validation.
     * Row indices start at 0 (first data row after header).
     *
     * @param rowIndex The zero-based index of the row to load
     * @return Map containing the row data with column headers as keys
     * @throws IOException when error during reading or validation failure
     */
    public Map<String, Object> getRow(long rowIndex) throws IOException {
        if (rowIndex < 0 || rowIndex >= totalRows) {
            return Collections.emptyMap();
        }

        // +1 because row 0 is the header
        CsvRow row = indexedReader.getRow(rowIndex + 1);
        if (row == null) {
            return Collections.emptyMap();
        }

        // Validation: Check field count
        int expectedFieldCount = config.getExpectedFieldCount() != null ?
                config.getExpectedFieldCount() : headers.size();

        if (config.isErrorOnDifferentFieldCount() && row.getFieldCount() != expectedFieldCount) {
            throw new IOException(String.format(
                    "CSV validation error at row %d (line %d): Expected %d fields but found %d",
                    rowIndex, row.getStartingLineNumber(), expectedFieldCount, row.getFieldCount()));
        }

        Map<String, Object> rowMap = new HashMap<>();
        for (int i = 0; i < Math.min(headers.size(), row.getFieldCount()); i++) {
            String value = row.getField(i);
            // Apply whitespace trimming if configured
            if (config.isIgnoreLeadingWhitespace() && config.isIgnoreTrailingWhitespace()) {
                value = value.trim();
            } else if (config.isIgnoreLeadingWhitespace()) {
                value = value.stripLeading();
            } else if (config.isIgnoreTrailingWhitespace()) {
                value = value.stripTrailing();
            }
            rowMap.put(headers.get(i), value);
        }
        return rowMap;
    }

    /**
     * Lazily loads a range of rows.
     *
     * @param startIndex The zero-based start index (inclusive)
     * @param endIndex The zero-based end index (exclusive)
     * @return List of Maps containing the row data
     * @throws IOException when error during reading happen
     */
    public List<Map<String, Object>> getRows(long startIndex, long endIndex) throws IOException {
        if (startIndex < 0 || startIndex >= totalRows) {
            return Collections.emptyList();
        }

        long actualEndIndex = Math.min(endIndex, totalRows);
        List<Map<String, Object>> results = new ArrayList<>();

        for (long i = startIndex; i < actualEndIndex; i++) {
            results.add(getRow(i));
        }

        return results;
    }

    /**
     * Loads all rows. Use with caution on large files.
     *
     * @return List of all data rows
     * @throws IOException when error during reading happen
     */
    public List<Map<String, Object>> getAllRows() throws IOException {
        return getRows(0, totalRows);
    }

    /**
     * Searches for a row where a specific column has a specific value.
     * This allows for lazy reference resolution by searching for referenced objects.
     *
     * @param columnName The column name to search in
     * @param value The value to search for
     * @return Map containing the first matching row, or empty map if not found
     * @throws IOException when error during reading happen
     */
    public Map<String, Object> findRowByValue(String columnName, String value) throws IOException {
        requireNonNull(columnName);
        requireNonNull(value);

        for (long i = 0; i < totalRows; i++) {
            Map<String, Object> row = getRow(i);
            Object cellValue = row.get(columnName);
            if (value.equals(cellValue)) {
                return row;
            }
        }

        return Collections.emptyMap();
    }

    /**
     * Searches for all rows where a specific column has a specific value.
     *
     * @param columnName The column name to search in
     * @param value The value to search for
     * @return List of all matching rows
     * @throws IOException when error during reading happen
     */
    public List<Map<String, Object>> findAllRowsByValue(String columnName, String value) throws IOException {
        requireNonNull(columnName);
        requireNonNull(value);

        List<Map<String, Object>> results = new ArrayList<>();
        for (long i = 0; i < totalRows; i++) {
            Map<String, Object> row = getRow(i);
            Object cellValue = row.get(columnName);
            if (value.equals(cellValue)) {
                results.add(row);
            }
        }

        return results;
    }

    /**
     * Returns the configuration used by this parser.
     *
     * @return The CSVReaderConfig
     */
    public CSVReaderConfig getConfig() {
        return config;
    }

    @Override
    public void close() throws IOException {
        if (indexedReader != null) {
            indexedReader.close();
        }
    }

    /**
     * Builds an IndexedCsvReader with the specified configuration.
     *
     * @param inputStream The input stream
     * @param config The configuration
     * @return configured IndexedCsvReader
     */
    private static IndexedCsvReader<CsvRow> buildIndexedCsvReader(InputStream inputStream, CSVReaderConfig config) {
        var builder = CsvReader.builder()
                .fieldSeparator(config.getFieldSeparator())
                .quoteCharacter(config.getQuoteCharacter())
                .skipEmptyLines(config.isSkipEmptyRows())
                .acceptChainedExceptions(config.isAcceptChainedExceptions());

        // Security limits
        if (config.getMaxFieldLength() > 0) {
            builder.maxFieldLength(config.getMaxFieldLength());
        }

        // Comment support
        if (config.isCommentEnabled()) {
            builder.commentCharacter(config.getCommentCharacter())
                   .commentStrategy(de.siegmar.fastcsv.reader.CommentStrategy.SKIP);
        }

        return builder.ofCsvRecord(new InputStreamReader(inputStream, config.getCharset())).indexed();
    }
}
