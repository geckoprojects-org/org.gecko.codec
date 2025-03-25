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
package org.gecko.codec.json.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.codec.configurator.CodecFactoryConfigurator;
import org.gecko.codec.configurator.CodecModuleConfigurator;
import org.gecko.codec.configurator.ObjectMapperConfigurator;
import org.gecko.codec.constants.CodecResourceOptions;
import org.gecko.codec.demo.model.person.MapInMap;
import org.gecko.codec.demo.model.person.PersonPackage;
import org.gecko.codec.demo.model.person.SimpleValue;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = "DefaultCodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json") })
@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json") })
@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json") })
public class OpenApiTest {

	@InjectService(cardinality = 0, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "=person)")
	ServiceAware<ResourceSet> rsAware;

	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;

	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;

	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;

	private ResourceSet resourceSet;

	@BeforeEach()
	public void beforeEach() throws InterruptedException {
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
	}

	@Test
	public void testLoadMapWithStringKeyWithMapValue() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/test-map-in-map.json"));
		Map<String, Object> options = new HashMap<>();

		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, PersonPackage.eINSTANCE.getMapInMap());
		resource.load(options);

		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(MapInMap.class);

		MapInMap types = (MapInMap) resource.getContents().get(0);

		assertThat(types.getStringMapInMapValues()).hasSize(2);

		EMap<String, EMap<String, SimpleValue>> mapValues = types.getStringMapInMapValues();

		EMap<String, SimpleValue> hello = mapValues.get("Hello");
		assertThat(hello).hasSize(2);
		assertThat(hello.get("1.1")).isEqualTo(11);
		assertThat(hello.get("1.2")).isEqualTo(12);

		EMap<String, SimpleValue> world = mapValues.get("World");
		assertThat(world).hasSize(2);
		assertThat(world.get("2.1")).isEqualTo(21);
		assertThat(world.get("2.2")).isEqualTo(22);
	}
}
