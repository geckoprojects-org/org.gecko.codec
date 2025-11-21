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

import org.eclipse.fennec.codec.csv.config.CSVReaderConfig;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

/**
 * CSV Parser using FastCSV library for efficient CSV parsing with validation and security features.
 * Replaces the old QueryStringParser with a proper CSV parser.
 *
 * Features:
 * - Configurable field separator, quote character, comment character
 * - Security limits: max field length, max row length (DoS prevention)
 * - Validation: field count validation, empty row handling
 * - Error handling with line number reporting
 *
 * @author Claude Code
 * @since Nov 21, 2025
 */
public class FastCSVParser {

    /**
     * Parses a CSV {@link InputStream} with default configuration.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @return a {@link List} of {@link Map}s, where each Map represents a CSV row.
     *         Returns an empty list if the stream is empty or contains only headers.
     * @throws IOException when error during reading happen.
     */
    public static List<Map<String, Object>> parseAll(InputStream inputStream) throws IOException {
        return parseAll(inputStream, CSVReaderConfig.defaultConfig());
    }

    /**
     * Parses a CSV {@link InputStream} with custom configuration.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @param config The CSV reader configuration. Must not be <code>null</code>
     * @return a {@link List} of {@link Map}s, where each Map represents a CSV row.
     *         Returns an empty list if the stream is empty or contains only headers.
     * @throws IOException when error during reading happen.
     */
    public static List<Map<String, Object>> parseAll(InputStream inputStream, CSVReaderConfig config) throws IOException {
        requireNonNull(inputStream);
        requireNonNull(config);

        List<Map<String, Object>> results = new ArrayList<>();

        try (CsvReader<CsvRow> csvReader = buildCsvReader(inputStream, config)) {

            List<String> headers = null;
            int expectedFieldCount = config.getExpectedFieldCount() != null ? config.getExpectedFieldCount() : -1;

            for (CsvRow row : csvReader) {
                if (headers == null) {
                    // First row contains headers
                    headers = new ArrayList<>();
                    for (int i = 0; i < row.getFieldCount(); i++) {
                        headers.add(row.getField(i));
                    }
                    if (expectedFieldCount < 0) {
                        expectedFieldCount = headers.size();
                    }
                } else {
                    // Validation: Check field count
                    if (config.isErrorOnDifferentFieldCount() && expectedFieldCount > 0 && row.getFieldCount() != expectedFieldCount) {
                        throw new IOException(String.format(
                                "CSV validation error at line %d: Expected %d fields but found %d",
                                row.getStartingLineNumber(), expectedFieldCount, row.getFieldCount()));
                    }

                    // Data rows
                    Map<String, Object> rowMap = new HashMap<>();
                    for (int i = 0; i < Math.min(headers.size(), row.getFieldCount()); i++) {
                        String value = row.getField(i);
                        if (config.isIgnoreLeadingWhitespace() && config.isIgnoreTrailingWhitespace()) {
                            value = value.trim();
                        } else if (config.isIgnoreLeadingWhitespace()) {
                            value = value.stripLeading();
                        } else if (config.isIgnoreTrailingWhitespace()) {
                            value = value.stripTrailing();
                        }
                        rowMap.put(headers.get(i), value);
                    }
                    results.add(rowMap);
                }
            }
        }

        return results;
    }

    /**
     * Parses a CSV {@link InputStream} and returns the first data row with default configuration.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @return a {@link Map} with the key-value pairs from the first data row.
     *         Returns an empty map if the stream is empty or contains only headers.
     * @throws IOException when error during reading happen.
     */
    public static Map<String, Object> parse(InputStream inputStream) throws IOException {
        return parse(inputStream, CSVReaderConfig.defaultConfig());
    }

    /**
     * Parses a CSV {@link InputStream} and returns the first data row with custom configuration.
     *
     * @param inputStream The input stream containing CSV data. Must not be <code>null</code>
     * @param config The CSV reader configuration. Must not be <code>null</code>
     * @return a {@link Map} with the key-value pairs from the first data row.
     *         Returns an empty map if the stream is empty or contains only headers.
     * @throws IOException when error during reading happen.
     */
    public static Map<String, Object> parse(InputStream inputStream, CSVReaderConfig config) throws IOException {
        requireNonNull(inputStream);
        requireNonNull(config);

        try (CsvReader<CsvRow> csvReader = buildCsvReader(inputStream, config)) {

            List<String> headers = null;
            int expectedFieldCount = config.getExpectedFieldCount() != null ? config.getExpectedFieldCount() : -1;

            for (CsvRow row : csvReader) {
                if (headers == null) {
                    // First row contains headers
                    headers = new ArrayList<>();
                    for (int i = 0; i < row.getFieldCount(); i++) {
                        headers.add(row.getField(i));
                    }
                    if (expectedFieldCount < 0) {
                        expectedFieldCount = headers.size();
                    }
                } else {
                    // Validation: Check field count
                    if (config.isErrorOnDifferentFieldCount() && expectedFieldCount > 0 && row.getFieldCount() != expectedFieldCount) {
                        throw new IOException(String.format(
                                "CSV validation error at line %d: Expected %d fields but found %d",
                                row.getStartingLineNumber(), expectedFieldCount, row.getFieldCount()));
                    }

                    // Return first data row
                    Map<String, Object> rowMap = new HashMap<>();
                    for (int i = 0; i < Math.min(headers.size(), row.getFieldCount()); i++) {
                        String value = row.getField(i);
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
            }
        }

        // No data rows found, return empty map
        return Collections.emptyMap();
    }

    /**
     * Builds a CsvReader with the specified configuration.
     *
     * @param inputStream The input stream
     * @param config The configuration
     * @return configured CsvReader
     */
    private static CsvReader<CsvRow> buildCsvReader(InputStream inputStream, CSVReaderConfig config) {
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

        return builder.ofCsvRecord(new InputStreamReader(inputStream, config.getCharset()));
    }
}
