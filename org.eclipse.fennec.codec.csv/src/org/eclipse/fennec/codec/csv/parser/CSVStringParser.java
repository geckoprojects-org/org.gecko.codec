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
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * CSV parser that converts CSV rows into maps for codec processing
 * @author ilenia
 * @since Sep 23, 2025
 */
public class CSVStringParser {

    /**
     * Converts a single CSV row into a Map<String, Object> using provided headers.
     *
     * @param headers The CSV headers
     * @param record The CSV record
     * @return a {@link Map} with the key value pairs from the row
     */
    public static Map<String, Object> parseRow(String[] headers, CSVRecord record) {
        requireNonNull(headers);
        requireNonNull(record);
        
        Map<String, Object> resultMap = new HashMap<>();
        
        for (int i = 0; i < headers.length && i < record.size(); i++) {
            String header = headers[i];
            String value = record.get(i);
            resultMap.put(header, value);
        }
        
        return resultMap;
    }
    
    /**
     * Parses CSV content and returns a parser with headers properly configured.
     *
     * @param inputStream The input stream with CSV content
     * @return CSVParser for iterating through records
     * @throws IOException when error during reading happens
     */
    public static CSVParser createParser(InputStream inputStream) throws IOException {
        requireNonNull(inputStream);
        
        // Read all content into a string first to avoid InputStream rewind issues
        String csvContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        
        if (csvContent.isEmpty()) {
            throw new IOException("CSV file is empty");
        }
        
        // First pass: read headers manually
        Reader headerReader = new java.io.StringReader(csvContent);
        CSVParser headerParser = CSVFormat.DEFAULT.parse(headerReader);
        CSVRecord firstRecord = headerParser.iterator().next();
        
        String[] headers = new String[firstRecord.size()];
        for (int i = 0; i < firstRecord.size(); i++) {
            headers[i] = firstRecord.get(i);
        }
        headerParser.close();
        
        // Second pass: create parser with explicit headers and skip first record
        Reader dataReader = new java.io.StringReader(csvContent);
        return CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(true).get().parse(dataReader);
    }
}
