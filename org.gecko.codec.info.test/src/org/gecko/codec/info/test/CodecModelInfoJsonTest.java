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
package org.gecko.codec.info.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.gecko.code.demo.model.person.Address;
import org.gecko.code.demo.model.person.Person;
import org.gecko.code.demo.model.person.PersonFactory;
import org.gecko.code.demo.model.person.PersonPackage;
import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.demo.resource.CodecJsonResource;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.ObjectMapperOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.Scalar;
import org.osgi.test.common.annotation.Property.Type;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
public class CodecModelInfoJsonTest {

	
	@Test
	public void testEMFJson(@InjectService(timeout = 2000l) PersonPackage demoModel) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		
		EMFModule module = new EMFModule();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.registerModule(module);
		JsonResource resource = new JsonResource(URI.createURI("test.json"), mapper);
		
		Person person = PersonFactory.eINSTANCE.createPerson();
		person.setName("Ilenia");
		person.setLastName("Salvadori");
		person.setBirthDate(Date.valueOf(LocalDate.of(1990, 6, 20)));
		Address address = PersonFactory.eINSTANCE.createAddress();
		address.setStreet("Camsdorfer Str. 41");
		address.setZip("07749");
		person.setAddress(address);
		
		resource.getContents().add(person);
		resource.save(null);
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecFactory", location = "?", name = "test", properties = {
			@Property(key = "dateFormat", value="yyyy-MM-dd"),
			@Property(key = "serFeaturesEnabled", value={"A", "B", "C"}, type = Type.Array)
	})
	@WithFactoryConfiguration(factoryPid = "CodecModule", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="json"), 
			@Property(key = "useId", value="true", scalar = Scalar.Boolean),
			@Property(key = "idOnTop", value="true", scalar = Scalar.Boolean),
			@Property(key = "defaultIdKey", value="custom_id_key")
	})
	@Test
	public void testCodecJson(@InjectService(timeout = 2000l) PersonPackage demoModel,  
			@InjectService(timeout = 2000l) CodecModelInfo codecModelInfo,
			@InjectService(timeout = 2000l) CodecModule codecModule
			) throws InterruptedException, IOException {
	
		assertNotNull(demoModel);
		assertNotNull(codecModelInfo);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(codecModule);
		CodecJsonResource resource = new CodecJsonResource(URI.createURI("mytest.json"), codecModelInfo, codecModule, mapper);
		
		Person person = PersonFactory.eINSTANCE.createPerson();
		person.setName("Ilenia");
		person.setLastName("Salvadori");
		person.setBirthDate(Date.valueOf(LocalDate.of(1990, 6, 20)));
		Address address = PersonFactory.eINSTANCE.createAddress();
		address.setStreet("Camsdorfer Str. 41");
		address.setZip("07749");
		person.setAddress(address);
		
		resource.getContents().add(person);
		Map<String, Object> options = new HashMap<>();
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		options.put(ObjectMapperOptions.OBJ_MAPPER_DATE_FORMAT, df);
		resource.save(options);
	}
	
	
}
