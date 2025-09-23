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
package org.eclipse.fennec.codec.csv.test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * 
 * @author ilenia
 * @since Sep 23, 2025
 */
public class CSVWrapper {
	
	public List<String> getCSVRowStringList(String csvFilePath) {
		try (Reader reader = new FileReader(csvFilePath);
	            CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT.builder().setSkipHeaderRecord(false).get());) {
	         	List<String> csvRowStringList = new LinkedList<>();
	            // The parser automatically uses the first row as headers.
	            // You can get the headers if needed:
	            Map<String, Integer> headers = csvParser.getHeaderMap();
	            System.out.println("CSV Headers: " + headers.keySet());

	            // Iterate over each record (row) in the CSV file
	            for (CSVRecord csvRecord : csvParser) {
	                StringBuilder sb = new StringBuilder();
	                // Iterate through the headers to get the key-value pairs
	                for (String header : headers.keySet()) {
	                    // Use the header to get the corresponding value from the record
	                    String value = csvRecord.get(header);
	                    sb.append(header+"="+value+"&");
	                }
	                csvRowStringList.add(sb.toString().substring(0, sb.toString().length()-1));

	                // Pass the map of key-value pairs to your existing parser
//	                populateEObject(rowData);
	            }
	            return csvRowStringList;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return Collections.emptyList();
	        }
	}

}
