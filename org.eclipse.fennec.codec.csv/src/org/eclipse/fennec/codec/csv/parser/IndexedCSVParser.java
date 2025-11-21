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

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import de.siegmar.fastcsv.reader.IndexedCsvReader;

/**
 * Indexed CSV Parser using FastCSV's IndexedCsvReader for lazy loading.
 * This parser allows random access to CSV rows without loading the entire file into memory.
 *
 * Features:
 * - Lazy loading: rows are only parsed when accessed
 * - Random access: can jump to any row by index
 * - Memory efficient: suitable for large CSV files
 * - On-demand reference resolution: ECore references can be resolved when needed
 *
 * @author Claude Code
 * @since Nov 21, 2025
 */
public class IndexedCSVParser implements Closeable {

    private final IndexedCsvReader<CsvRow> indexedReader;
    private final List<String> headers;
    private final long totalRows;

    /**
     * Creates a new IndexedCSVParser from an InputStream.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @throws IOException when error during reading happen.
     */
    public IndexedCSVParser(InputStream inputStream) throws IOException {
        requireNonNull(inputStream);

        // Create indexed reader for random access
        this.indexedReader = CsvReader.builder()
                .ofCsvRecord(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .indexed();

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
     * Lazily loads a specific row by index.
     * Row indices start at 0 (first data row after header).
     *
     * @param rowIndex The zero-based index of the row to load
     * @return Map containing the row data with column headers as keys
     * @throws IOException when error during reading happen
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

        Map<String, Object> rowMap = new HashMap<>();
        for (int i = 0; i < Math.min(headers.size(), row.getFieldCount()); i++) {
            rowMap.put(headers.get(i), row.getField(i));
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

    @Override
    public void close() throws IOException {
        if (indexedReader != null) {
            indexedReader.close();
        }
    }
}
