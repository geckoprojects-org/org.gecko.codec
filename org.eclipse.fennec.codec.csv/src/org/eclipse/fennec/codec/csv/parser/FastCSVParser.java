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

/**
 * CSV Parser using FastCSV library for efficient CSV parsing
 * Replaces the old QueryStringParser with a proper CSV parser
 *
 * @author Claude Code
 * @since Nov 21, 2025
 */
public class FastCSVParser {

    /**
     * Parses a CSV {@link InputStream} and converts it into a List of Maps.
     * Each Map represents one CSV row with column headers as keys.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @return a {@link List} of {@link Map}s, where each Map represents a CSV row.
     *         Returns an empty list if the stream is empty or contains only headers.
     * @throws IOException when error during reading happen.
     */
    public static List<Map<String, Object>> parseAll(InputStream inputStream) throws IOException {
        requireNonNull(inputStream);

        List<Map<String, Object>> results = new ArrayList<>();

        try (CsvReader<CsvRow> csvReader = CsvReader.builder()
                .ofCsvRecord(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            List<String> headers = null;

            for (CsvRow row : csvReader) {
                if (headers == null) {
                    // First row contains headers
                    headers = new ArrayList<>();
                    for (int i = 0; i < row.getFieldCount(); i++) {
                        headers.add(row.getField(i));
                    }
                } else {
                    // Data rows
                    Map<String, Object> rowMap = new HashMap<>();
                    for (int i = 0; i < Math.min(headers.size(), row.getFieldCount()); i++) {
                        rowMap.put(headers.get(i), row.getField(i));
                    }
                    results.add(rowMap);
                }
            }
        }

        return results;
    }

    /**
     * Parses a CSV {@link InputStream} and returns the first data row as a Map.
     * This is useful for single-row CSV parsing.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @return a {@link Map} with the key-value pairs from the first data row.
     *         Returns an empty map if the stream is empty or contains only headers.
     * @throws IOException when error during reading happen.
     */
    public static Map<String, Object> parse(InputStream inputStream) throws IOException {
        requireNonNull(inputStream);

        try (CsvReader<CsvRow> csvReader = CsvReader.builder()
                .ofCsvRecord(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            List<String> headers = null;

            for (CsvRow row : csvReader) {
                if (headers == null) {
                    // First row contains headers
                    headers = new ArrayList<>();
                    for (int i = 0; i < row.getFieldCount(); i++) {
                        headers.add(row.getField(i));
                    }
                } else {
                    // Return first data row
                    Map<String, Object> rowMap = new HashMap<>();
                    for (int i = 0; i < Math.min(headers.size(), row.getFieldCount()); i++) {
                        rowMap.put(headers.get(i), row.getField(i));
                    }
                    return rowMap;
                }
            }
        }

        // No data rows found, return empty map
        return Collections.emptyMap();
    }
}
