/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
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
package org.eclipse.fennec.codec.ecowitt.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.codec.ecowitt.resource.EcoWittResource;
import org.eclipse.fennec.codec.ecowitt.resource.EcoWittResourceFactory;
import org.eclipse.fennec.codec.options.CodecModuleOptions;
import org.eclipse.fennec.codec.options.CodecResourceOptions;
import org.eclipse.fennec.ecowitt.model.ecowitt.EcoWittPackage;
import org.eclipse.fennec.ecowitt.model.ecowitt.EcoWittWeather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;

/**
 * See documentation here: 
 * 	https://github.com/osgi/osgi-test
 * 	https://github.com/osgi/osgi-test/wiki
 * Examples: https://github.com/osgi/osgi-test/tree/main/examples
 */
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
//@ExtendWith(MockitoExtension.class)
public class EcowittTest {
	
	private static String DATA = "PASSKEY=BCB57A2838A90E31BA1F61139D74EFE6&stationtype=GW2000A_V3.2.6&runtime=149100&heap=93148&dateutc=2025-08-07%2013%3A02%3A00&dns_ip=192.168.1.10&dns_err_cnt=0&cdnflg=0&tempinf=70.34&humidityin=66&baromrelin=29.616&baromabsin=29.616&tempf=78.98&humidity=32&vpd=0.679&winddir=221&winddir_avg10m=265&windspeedmph=5.82&windgustmph=8.05&maxdailygust=12.53&solarradiation=728.85&uv=7&rainratein=0.000&eventrainin=0.000&hourlyrainin=0.000&last24hrainin=0.000&dailyrainin=0.000&weeklyrainin=0.272&monthlyrainin=0.953&yearlyrainin=10.563&wh65batt=0&freq=868M&model=GW2000A&interval=0";
	@BeforeEach
	public void before(@InjectBundleContext BundleContext ctx) {
		
	}
	
	@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "ecowitt", properties = {
			@Property(key = "type", value="ecowitt"),
			@Property(key = "codecFactoryConfigurator.target", value="(type=ecowitt)")
	})
	@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "ecowitt")
	@Test
	public void testFactoryConfigDisableJsonFactoryFeature(
			@InjectService(timeout = 2000l) EcoWittResourceFactory ecowittRF) throws InterruptedException, IOException, ParseException {
	
		assertNotNull(ecowittRF);
		Resource resource = ecowittRF.createResource(URI.createURI("test.ecowitt"));
		assertInstanceOf(EcoWittResource.class, resource);
		Map<String, Object> properties = new HashMap<>();
		properties.put(CodecResourceOptions.CODEC_ROOT_OBJECT, EcoWittPackage.eINSTANCE.getEcoWittWeather());
		properties.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, Boolean.TRUE);
		resource.load(new ByteArrayInputStream(DATA.getBytes()), properties);
		assertFalse(resource.getContents().isEmpty());
		EObject content = resource.getContents().get(0);
		assertInstanceOf(EcoWittWeather.class, content);
		EcoWittWeather weather = (EcoWittWeather) content;
		assertEquals("BCB57A2838A90E31BA1F61139D74EFE6", weather.getPasskey());
		assertEquals("GW2000A_V3.2.6", weather.getStationType());
		assertEquals("GW2000A", weather.getModel());
		assertEquals("868M", weather.getFrequency());
		assertEquals("149100", weather.getRuntime());
		assertEquals(93148, weather.getHeap());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// IMPORTANT: Set the timezone to UTC
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = dateFormat.parse("2025-08-07 13:02:00");
		assertEquals(date, weather.getDateUTC());
		assertEquals(10.563, weather.getRainYearly());
		assertEquals(29.616, weather.getBarometerAbs());
	}

}
