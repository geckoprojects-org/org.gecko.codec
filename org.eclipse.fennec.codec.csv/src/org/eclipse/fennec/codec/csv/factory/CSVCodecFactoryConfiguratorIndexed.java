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
package org.eclipse.fennec.codec.csv.factory;

import java.util.Map;

import org.eclipse.fennec.codec.CodecGeneratorFactory;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.csv.parser.CSVParserFactoryIndexed;
import org.eclipse.fennec.codec.jackson.configurator.AbstractCodecFactoryConfigurator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Indexed CSV Codec Factory Configurator for lazy loading large CSV files.
 * This configurator uses FastCSV's IndexedCsvReader to enable:
 * - Lazy loading of CSV rows
 * - Random access to any row
 * - Memory-efficient processing of large files
 * - On-demand reference resolution for ECore objects
 *
 * @author Claude Code
 * @since Nov 21, 2025
 */
@Component(immediate = true, service = CodecFactoryConfigurator.class, property = {"type=csv-indexed"})
public class CSVCodecFactoryConfiguratorIndexed extends AbstractCodecFactoryConfigurator {

	@Activate
	public void activate(Map<String, Object> properties) {
		initialize(properties);
		setParserFactory(new CSVParserFactoryIndexed());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.configurator.AbstractCodecFactoryConfigurator#getGenFactory()
	 */
	@Override
	protected CodecGeneratorFactory<?, ?> getGenFactory() {
		// No generation supported
		return null;
	}

}
