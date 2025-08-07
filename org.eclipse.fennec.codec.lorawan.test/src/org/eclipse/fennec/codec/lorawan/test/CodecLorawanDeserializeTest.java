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
package org.eclipse.fennec.codec.lorawan.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.options.CodecModelInfoOptions;
import org.eclipse.fennec.codec.options.CodecModuleOptions;
import org.eclipse.fennec.codec.options.CodecResourceOptions;
import org.eclipse.fennec.dragino.message.model.dragino.DecodedObject;
import org.eclipse.fennec.dragino.message.model.dragino.DraginoLSE01Uplink;
import org.eclipse.fennec.dragino.message.model.dragino.DraginoPackage;
import org.eclipse.fennec.em310udl.mesage.model.em310udl.EM310UDLPackage;
import org.eclipse.fennec.em310udl.mesage.model.em310udl.EM310UDLUplink;
import org.eclipse.fennec.lorawan.uplink.model.lorawan.DeviceInfo;
import org.eclipse.fennec.lorawan.uplink.model.lorawan.LorawanPackage;
import org.eclipse.fennec.lorawan.uplink.model.lorawan.RxInfo;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.AfterEach;
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


/**
 * See documentation here: 
 * 	https://github.com/osgi/osgi-test
 * 	https://github.com/osgi/osgi-test/wiki
 * Examples: https://github.com/osgi/osgi-test/tree/main/examples
 */
@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = "DefaultCodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")

})
@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="json")
})
public class CodecLorawanDeserializeTest extends JsonTestSetting{

	@InjectService(cardinality = 0, filter = "(" + EMFNamespaces.EMF_CONFIGURATOR_NAME + "=CodecJson)")
	ServiceAware<ResourceSet> rsAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;
	
	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;
	
	private ResourceSet resourceSet;	
	
	@BeforeEach()
	@Override
	public void beforeEach() throws Exception{
		super.beforeEach();
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(40000l);
		assertNotNull(resourceSet);
	}
	
	@AfterEach() 
	@Override
	public void afterEach() throws IOException {
		super.afterEach();
	}
	

	@Test
	public void testDeserializationDragino(@InjectService ServiceAware<DraginoPackage> packageAware) throws IOException {
		
		Resource resource = resourceSet.createResource(URI.createURI("test-data/dragino-example.json"));	
		Map<String, Object> options = new HashMap<>();		
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, DraginoPackage.eINSTANCE.getDraginoLSE01Uplink());
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		resource.load(options);

		// get the person
		assertNotNull(resource);
		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(DraginoLSE01Uplink.class);
		
		DraginoLSE01Uplink msg = (DraginoLSE01Uplink) resource.getContents().get(0);
		assertThat(msg).isNotNull();
		assertThat(msg.getDeduplicationId()).isEqualTo("9ffbc7be-916a-4597-9d64-0713a3d5e030");
		assertThat(msg.getTime()).isEqualTo("2024-06-20T08:48:38.863512+00:00");
		assertThat(msg.getDevAddr()).isEqualTo("01acdec6");
		assertTrue(msg.isAdr());
		assertThat(msg.getDr()).isEqualTo(0);
		assertThat(msg.getFCnt()).isEqualTo(110);
		assertThat(msg.getFPort()).isEqualTo(2);
		assertTrue(msg.isConfirmed());
		assertThat(msg.getData()).isEqualTo("DOUAAAVeBS0AIxA=");
		
		assertThat(msg.getDeviceInfo()).isNotNull();
		DeviceInfo deviceInfo = msg.getDeviceInfo();
		assertThat(deviceInfo.getTenantId()).isEqualTo("52f14cd4-c6f1-4fbd-8f87-4025e1d49242");
		assertThat(deviceInfo.getTenantName()).isEqualTo("Jena");
		assertThat(deviceInfo.getApplicationId()).isEqualTo("5bb92de6-49b3-49af-a366-34667f2230be");
		assertThat(deviceInfo.getApplicationName()).isEqualTo("KSJ_Bodenfeuchte_Stadtforst");
		assertThat(deviceInfo.getDeviceProfileId()).isEqualTo("37cf9cbd-e7e1-4269-8c22-b46bb5261a2a");
		assertThat(deviceInfo.getDeviceProfileName()).isEqualTo("Dragino_LSE01");
		assertThat(deviceInfo.getDeviceName()).isEqualTo("LST25628782");
		assertThat(deviceInfo.getDevEui()).isEqualTo("a8404187d187106e");
		assertThat(deviceInfo.getTags()).isNotNull();
		assertThat(deviceInfo.getTags().getDev_type()).isEqualTo("dragino_lse01");
		
		DecodedObject object = msg.getObject();
		assertThat(object).isNotNull();
		assertThat(object.getBatV()).isEqualTo(3.301);
		assertThat(object.getTemp_DS18B20()).isEqualTo("0.00");
		assertThat(object.getTemp_SOIL()).isEqualTo("13.25");
		assertThat(object.getTemp_SOIL_f()).isEqualTo(13.25);
		assertThat(object.getWater_SOIL()).isEqualTo("13.74");
		assertThat(object.getWater_SOIL_f()).isEqualTo(13.74);
		assertThat(object.getI_flag()).isEqualTo(0.0);
		assertThat(object.getS_flag()).isEqualTo(1.0);
		assertThat(object.getConduct_SOIL()).isEqualTo(35.0);
		assertThat(object.getConduct_SOIL_f()).isEqualTo(35.0);
		assertThat(object.getMod()).isEqualTo(0.0);
		
		assertThat(msg.getRxInfo()).hasSize(2);
		RxInfo rx1 = null, rx2 = null;
		for(RxInfo rx : msg.getRxInfo()) {
			if("a84041ffff1f4b70".equals(rx.getGatewayId())) {
				rx1 = rx;
			} else if("fcc23dfffe2ea641".equals(rx.getGatewayId())) {
				rx2 = rx;
			}
		}
		assertThat(rx1).isNotNull();
		assertThat(rx2).isNotNull();
	
		assertThat(rx1.getUplinkId()).isEqualTo(45347);
		assertThat(rx1.getTime()).isEqualTo("2024-06-20T08:48:38.863512+00:00");
		assertThat(rx1.getRssi()).isEqualTo(-127);
		assertThat(rx1.getSnr()).isEqualTo(-12.8);
		assertThat(rx1.getChannel()).isEqualTo(3);
		assertThat(rx1.getContext()).isEqualTo("a72XTA==");
		assertThat(rx1.getLocation()).isNotNull();
		assertThat(rx1.getLocation().getLatitude()).isEqualTo(50.949717);
		assertThat(rx1.getLocation().getLongitude()).isEqualTo(11.608153);
		assertThat(rx1.getLocation().getAltitude()).isEqualTo(151.0);
		assertThat(rx1.getMetadata()).isNotNull();
		assertThat(rx1.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx1.getMetadata().getRegion_common_name()).isEqualTo("EU868");
		
		assertThat(rx2.getUplinkId()).isEqualTo(24930);
		assertThat(rx2.getTime()).isNull();
		assertThat(rx2.getRssi()).isEqualTo(-116);
		assertThat(rx2.getSnr()).isEqualTo(-1.0);
		assertThat(rx2.getChannel()).isEqualTo(3);
		assertThat(rx2.getContext()).isEqualTo("4Xz01A==");
		assertThat(rx2.getLocation()).isNotNull();
		assertThat(rx2.getLocation().getLatitude()).isEqualTo(50.932005871988125);
		assertThat(rx2.getLocation().getLongitude()).isEqualTo(11.591793894767763);
		assertThat(rx2.getLocation().getAltitude()).isEqualTo(0.0);
		assertThat(rx2.getMetadata()).isNotNull();
		assertThat(rx2.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx2.getMetadata().getRegion_common_name()).isEqualTo("EU868");
		
		assertThat(msg.getTxInfo()).isNotNull();
		assertThat(msg.getTxInfo().getFrequency()).isEqualTo(867100000);
		assertThat(msg.getTxInfo().getModulation()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora().getBandwidth()).isEqualTo(125000);
		assertThat(msg.getTxInfo().getModulation().getLora().getSpreadingFactor()).isEqualTo(12);
		assertThat(msg.getTxInfo().getModulation().getLora().getCodeRate()).isEqualTo("CR_4_5");
		
	}
	
	@Test
	public void testDeserializationDraginoFromTypeInfo(@InjectService ServiceAware<DraginoPackage> packageAware) throws IOException, InterruptedException {
		
		Thread.sleep(2000l);
		Resource resource = resourceSet.createResource(URI.createURI("test-data/dragino-example.json"));	
		Map<String, Object> options = new HashMap<>();		
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, LorawanPackage.eINSTANCE.getUplinkMessage());
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		resource.load(options);

		// get the person
		assertNotNull(resource);
		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(DraginoLSE01Uplink.class);
		
		DraginoLSE01Uplink msg = (DraginoLSE01Uplink) resource.getContents().get(0);
		assertThat(msg).isNotNull();
		assertThat(msg.getDeduplicationId()).isEqualTo("9ffbc7be-916a-4597-9d64-0713a3d5e030");
		assertThat(msg.getTime()).isEqualTo("2024-06-20T08:48:38.863512+00:00");
		assertThat(msg.getDevAddr()).isEqualTo("01acdec6");
		assertTrue(msg.isAdr());
		assertThat(msg.getDr()).isEqualTo(0);
		assertThat(msg.getFCnt()).isEqualTo(110);
		assertThat(msg.getFPort()).isEqualTo(2);
		assertTrue(msg.isConfirmed());
		assertThat(msg.getData()).isEqualTo("DOUAAAVeBS0AIxA=");
		
		assertThat(msg.getDeviceInfo()).isNotNull();
		DeviceInfo deviceInfo = msg.getDeviceInfo();
		assertThat(deviceInfo.getTenantId()).isEqualTo("52f14cd4-c6f1-4fbd-8f87-4025e1d49242");
		assertThat(deviceInfo.getTenantName()).isEqualTo("Jena");
		assertThat(deviceInfo.getApplicationId()).isEqualTo("5bb92de6-49b3-49af-a366-34667f2230be");
		assertThat(deviceInfo.getApplicationName()).isEqualTo("KSJ_Bodenfeuchte_Stadtforst");
		assertThat(deviceInfo.getDeviceProfileId()).isEqualTo("37cf9cbd-e7e1-4269-8c22-b46bb5261a2a");
		assertThat(deviceInfo.getDeviceProfileName()).isEqualTo("Dragino_LSE01");
		assertThat(deviceInfo.getDeviceName()).isEqualTo("LST25628782");
		assertThat(deviceInfo.getDevEui()).isEqualTo("a8404187d187106e");
		assertThat(deviceInfo.getTags()).isNotNull();
		assertThat(deviceInfo.getTags().getDev_type()).isEqualTo("dragino_lse01");
		
		DecodedObject object = msg.getObject();
		assertThat(object).isNotNull();
		assertThat(object.getBatV()).isEqualTo(3.301);
		assertThat(object.getTemp_DS18B20()).isEqualTo("0.00");
		assertThat(object.getTemp_SOIL()).isEqualTo("13.25");
		assertThat(object.getTemp_SOIL_f()).isEqualTo(13.25);
		assertThat(object.getWater_SOIL()).isEqualTo("13.74");
		assertThat(object.getWater_SOIL_f()).isEqualTo(13.74);
		assertThat(object.getI_flag()).isEqualTo(0.0);
		assertThat(object.getS_flag()).isEqualTo(1.0);
		assertThat(object.getConduct_SOIL()).isEqualTo(35.0);
		assertThat(object.getConduct_SOIL_f()).isEqualTo(35.0);
		assertThat(object.getMod()).isEqualTo(0.0);
		
		assertThat(msg.getRxInfo()).hasSize(2);
		RxInfo rx1 = null, rx2 = null;
		for(RxInfo rx : msg.getRxInfo()) {
			if("a84041ffff1f4b70".equals(rx.getGatewayId())) {
				rx1 = rx;
			} else if("fcc23dfffe2ea641".equals(rx.getGatewayId())) {
				rx2 = rx;
			}
		}
		assertThat(rx1).isNotNull();
		assertThat(rx2).isNotNull();
	
		assertThat(rx1.getUplinkId()).isEqualTo(45347);
		assertThat(rx1.getTime()).isEqualTo("2024-06-20T08:48:38.863512+00:00");
		assertThat(rx1.getRssi()).isEqualTo(-127);
		assertThat(rx1.getSnr()).isEqualTo(-12.8);
		assertThat(rx1.getChannel()).isEqualTo(3);
		assertThat(rx1.getContext()).isEqualTo("a72XTA==");
		assertThat(rx1.getLocation()).isNotNull();
		assertThat(rx1.getLocation().getLatitude()).isEqualTo(50.949717);
		assertThat(rx1.getLocation().getLongitude()).isEqualTo(11.608153);
		assertThat(rx1.getLocation().getAltitude()).isEqualTo(151.0);
		assertThat(rx1.getMetadata()).isNotNull();
		assertThat(rx1.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx1.getMetadata().getRegion_common_name()).isEqualTo("EU868");
		
		assertThat(rx2.getUplinkId()).isEqualTo(24930);
		assertThat(rx2.getTime()).isNull();
		assertThat(rx2.getRssi()).isEqualTo(-116);
		assertThat(rx2.getSnr()).isEqualTo(-1.0);
		assertThat(rx2.getChannel()).isEqualTo(3);
		assertThat(rx2.getContext()).isEqualTo("4Xz01A==");
		assertThat(rx2.getLocation()).isNotNull();
		assertThat(rx2.getLocation().getLatitude()).isEqualTo(50.932005871988125);
		assertThat(rx2.getLocation().getLongitude()).isEqualTo(11.591793894767763);
		assertThat(rx2.getLocation().getAltitude()).isEqualTo(0.0);
		assertThat(rx2.getMetadata()).isNotNull();
		assertThat(rx2.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx2.getMetadata().getRegion_common_name()).isEqualTo("EU868");
		
		assertThat(msg.getTxInfo()).isNotNull();
		assertThat(msg.getTxInfo().getFrequency()).isEqualTo(867100000);
		assertThat(msg.getTxInfo().getModulation()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora().getBandwidth()).isEqualTo(125000);
		assertThat(msg.getTxInfo().getModulation().getLora().getSpreadingFactor()).isEqualTo(12);
		assertThat(msg.getTxInfo().getModulation().getLora().getCodeRate()).isEqualTo("CR_4_5");
		
	}
	
	@Test
	public void test(@InjectService ServiceAware<DraginoPackage> packageAware) throws IOException, InterruptedException {
		
		Thread.sleep(2000l);
		Resource resource = resourceSet.createResource(URI.createURI("test-data/em310-example.json"));	
		Map<String, Object> options = new HashMap<>();		
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, LorawanPackage.eINSTANCE.getUplinkMessage());
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		resource.load(options);

		// get the person
		assertNotNull(resource);
		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(EM310UDLUplink.class);
		
		EM310UDLUplink msg = (EM310UDLUplink) resource.getContents().get(0);
		
		resource = resourceSet.createResource(URI.createURI("test-data/em310-example.xmi"));	
		resource.getContents().add(msg);
		resource.save(null);
		
	}
	
	@Test
	public void testDeserializationDraginoFromTypeInfoOptions(@InjectService ServiceAware<DraginoPackage> packageAware) throws IOException {
		
		Resource resource = resourceSet.createResource(URI.createURI("test-data/dragino-example.json"));	
		Map<String, Object> options = new HashMap<>();		
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, LorawanPackage.eINSTANCE.getUplinkMessage());
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		Map<String, Object> classOptions = new HashMap<>();		
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "dr");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_MAP, Map.of("0", "https://eclipse.org/fennec/lorawan/dragino#//DraginoLSE01Uplink",
				"2", "http://www.example.org/lorawan/specific/em310udl#//EM310UDLUplink"));
		options.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(LorawanPackage.eINSTANCE.getUplinkMessage(), classOptions));
		
		resource.load(options);

		// get the person
		assertNotNull(resource);
		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(DraginoLSE01Uplink.class);
		
		DraginoLSE01Uplink msg = (DraginoLSE01Uplink) resource.getContents().get(0);
		assertThat(msg).isNotNull();
		assertThat(msg.getDeduplicationId()).isEqualTo("9ffbc7be-916a-4597-9d64-0713a3d5e030");
		assertThat(msg.getTime()).isEqualTo("2024-06-20T08:48:38.863512+00:00");
		assertThat(msg.getDevAddr()).isEqualTo("01acdec6");
		assertTrue(msg.isAdr());
		assertThat(msg.getDr()).isEqualTo(0);
		assertThat(msg.getFCnt()).isEqualTo(110);
		assertThat(msg.getFPort()).isEqualTo(2);
		assertTrue(msg.isConfirmed());
		assertThat(msg.getData()).isEqualTo("DOUAAAVeBS0AIxA=");
		
		assertThat(msg.getDeviceInfo()).isNotNull();
		DeviceInfo deviceInfo = msg.getDeviceInfo();
		assertThat(deviceInfo.getTenantId()).isEqualTo("52f14cd4-c6f1-4fbd-8f87-4025e1d49242");
		assertThat(deviceInfo.getTenantName()).isEqualTo("Jena");
		assertThat(deviceInfo.getApplicationId()).isEqualTo("5bb92de6-49b3-49af-a366-34667f2230be");
		assertThat(deviceInfo.getApplicationName()).isEqualTo("KSJ_Bodenfeuchte_Stadtforst");
		assertThat(deviceInfo.getDeviceProfileId()).isEqualTo("37cf9cbd-e7e1-4269-8c22-b46bb5261a2a");
		assertThat(deviceInfo.getDeviceProfileName()).isEqualTo("Dragino_LSE01");
		assertThat(deviceInfo.getDeviceName()).isEqualTo("LST25628782");
		assertThat(deviceInfo.getDevEui()).isEqualTo("a8404187d187106e");
		assertThat(deviceInfo.getTags()).isNotNull();
		assertThat(deviceInfo.getTags().getDev_type()).isEqualTo("dragino_lse01");
		
		DecodedObject object = msg.getObject();
		assertThat(object).isNotNull();
		assertThat(object.getBatV()).isEqualTo(3.301);
		assertThat(object.getTemp_DS18B20()).isEqualTo("0.00");
		assertThat(object.getTemp_SOIL()).isEqualTo("13.25");
		assertThat(object.getTemp_SOIL_f()).isEqualTo(13.25);
		assertThat(object.getWater_SOIL()).isEqualTo("13.74");
		assertThat(object.getWater_SOIL_f()).isEqualTo(13.74);
		assertThat(object.getI_flag()).isEqualTo(0.0);
		assertThat(object.getS_flag()).isEqualTo(1.0);
		assertThat(object.getConduct_SOIL()).isEqualTo(35.0);
		assertThat(object.getConduct_SOIL_f()).isEqualTo(35.0);
		assertThat(object.getMod()).isEqualTo(0.0);
		
		assertThat(msg.getRxInfo()).hasSize(2);
		RxInfo rx1 = null, rx2 = null;
		for(RxInfo rx : msg.getRxInfo()) {
			if("a84041ffff1f4b70".equals(rx.getGatewayId())) {
				rx1 = rx;
			} else if("fcc23dfffe2ea641".equals(rx.getGatewayId())) {
				rx2 = rx;
			}
		}
		assertThat(rx1).isNotNull();
		assertThat(rx2).isNotNull();
	
		assertThat(rx1.getUplinkId()).isEqualTo(45347);
		assertThat(rx1.getTime()).isEqualTo("2024-06-20T08:48:38.863512+00:00");
		assertThat(rx1.getRssi()).isEqualTo(-127);
		assertThat(rx1.getSnr()).isEqualTo(-12.8);
		assertThat(rx1.getChannel()).isEqualTo(3);
		assertThat(rx1.getContext()).isEqualTo("a72XTA==");
		assertThat(rx1.getLocation()).isNotNull();
		assertThat(rx1.getLocation().getLatitude()).isEqualTo(50.949717);
		assertThat(rx1.getLocation().getLongitude()).isEqualTo(11.608153);
		assertThat(rx1.getLocation().getAltitude()).isEqualTo(151.0);
		assertThat(rx1.getMetadata()).isNotNull();
		assertThat(rx1.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx1.getMetadata().getRegion_common_name()).isEqualTo("EU868");
		
		assertThat(rx2.getUplinkId()).isEqualTo(24930);
		assertThat(rx2.getTime()).isNull();
		assertThat(rx2.getRssi()).isEqualTo(-116);
		assertThat(rx2.getSnr()).isEqualTo(-1.0);
		assertThat(rx2.getChannel()).isEqualTo(3);
		assertThat(rx2.getContext()).isEqualTo("4Xz01A==");
		assertThat(rx2.getLocation()).isNotNull();
		assertThat(rx2.getLocation().getLatitude()).isEqualTo(50.932005871988125);
		assertThat(rx2.getLocation().getLongitude()).isEqualTo(11.591793894767763);
		assertThat(rx2.getLocation().getAltitude()).isEqualTo(0.0);
		assertThat(rx2.getMetadata()).isNotNull();
		assertThat(rx2.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx2.getMetadata().getRegion_common_name()).isEqualTo("EU868");
		
		assertThat(msg.getTxInfo()).isNotNull();
		assertThat(msg.getTxInfo().getFrequency()).isEqualTo(867100000);
		assertThat(msg.getTxInfo().getModulation()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora().getBandwidth()).isEqualTo(125000);
		assertThat(msg.getTxInfo().getModulation().getLora().getSpreadingFactor()).isEqualTo(12);
		assertThat(msg.getTxInfo().getModulation().getLora().getCodeRate()).isEqualTo("CR_4_5");
		
	}
	
	
	
	@Test
	public void testDeserializationEm130(@InjectService ServiceAware<DraginoPackage> packageAware) throws IOException {
		
		Resource resource = resourceSet.createResource(URI.createURI("test-data/em310-example.json"));	
		Map<String, Object> options = new HashMap<>();		
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, EM310UDLPackage.eINSTANCE.getEM310UDLUplink());
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		resource.load(options);

		// get the person
		assertNotNull(resource);
		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(EM310UDLUplink.class);
		
		EM310UDLUplink msg = (EM310UDLUplink) resource.getContents().get(0);
		assertThat(msg).isNotNull();
		assertThat(msg.getDeduplicationId()).isEqualTo("a98ecdf0-c93c-4d7f-9b55-e99d0eba5b9b");
		assertThat(msg.getTime()).isEqualTo("2025-07-07T15:42:11.141117+00:00");
		assertThat(msg.getDevAddr()).isEqualTo("0103fd23");
		assertTrue(msg.isAdr());
		assertThat(msg.getDr()).isEqualTo(2);
		assertThat(msg.getFCnt()).isEqualTo(55);
		assertThat(msg.getFPort()).isEqualTo(85);
		assertFalse(msg.isConfirmed());
		assertThat(msg.getData()).isEqualTo("AXVFA4JSAgQAAA==");
		
		assertThat(msg.getDeviceInfo()).isNotNull();
		DeviceInfo deviceInfo = msg.getDeviceInfo();
		assertThat(deviceInfo.getTenantId()).isEqualTo("52f14cd4-c6f1-4fbd-8f87-4025e1d49242");
		assertThat(deviceInfo.getTenantName()).isEqualTo("Jena");
		assertThat(deviceInfo.getApplicationId()).isEqualTo("e8d62144-b9b6-4fee-937f-5d8248eee499");
		assertThat(deviceInfo.getApplicationName()).isEqualTo("TrashLevel_KSJ");
		assertThat(deviceInfo.getDeviceProfileId()).isEqualTo("1e6d47f7-1432-4c83-b48d-dd34a7e5e9c6");
		assertThat(deviceInfo.getDeviceProfileName()).isEqualTo("EM310-UDL");
		assertThat(deviceInfo.getDeviceName()).isEqualTo("EM310-UDL_6713C40192590001");
		assertThat(deviceInfo.getDevEui()).isEqualTo("24e124713c401925");
		assertThat(deviceInfo.getTags()).isNotNull();
		assertThat(deviceInfo.getTags().getDev_type()).isEqualTo("em310-udl");
		
		org.eclipse.fennec.em310udl.mesage.model.em310udl.DecodedObject object = msg.getObject();
		assertThat(object).isNotNull();
		assertThat(object.getDistance()).isEqualTo(594.0);
		assertThat(object.getPosition()).isEqualTo("normal");
		assertThat(object.getBattery()).isEqualTo(69.0);
		
		
		assertThat(msg.getRxInfo()).hasSize(1);
		RxInfo rx1 = null;
		for(RxInfo rx : msg.getRxInfo()) {
			if("503139535d604750".equals(rx.getGatewayId())) {
				rx1 = rx;
			}
		}
		assertThat(rx1).isNotNull();
	
		assertThat(rx1.getUplinkId()).isEqualTo(44289);
		assertThat(rx1.getTime()).isEqualTo("2025-07-07T15:42:11.141117+00:00");
		assertThat(rx1.getRssi()).isEqualTo(-103);
		assertThat(rx1.getSnr()).isEqualTo(1.0);
		assertThat(rx1.getChannel()).isEqualTo(4);
		assertThat(rx1.getContext()).isEqualTo("JkxkFA==");
		assertThat(rx1.getLocation()).isNotNull();
		assertThat(rx1.getLocation().getLatitude()).isEqualTo(50.88423992258394);
		assertThat(rx1.getLocation().getLongitude()).isEqualTo(11.596755981445314);
		assertThat(rx1.getLocation().getAltitude()).isEqualTo(0.0);
		assertThat(rx1.getMetadata()).isNotNull();
		assertThat(rx1.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx1.getMetadata().getRegion_common_name()).isEqualTo("EU868");
	
		
		assertThat(msg.getTxInfo()).isNotNull();
		assertThat(msg.getTxInfo().getFrequency()).isEqualTo(867300000);
		assertThat(msg.getTxInfo().getModulation()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora().getBandwidth()).isEqualTo(125000);
		assertThat(msg.getTxInfo().getModulation().getLora().getSpreadingFactor()).isEqualTo(10);
		assertThat(msg.getTxInfo().getModulation().getLora().getCodeRate()).isEqualTo("CR_4_5");
	}
	
	@Test
	public void testDeserializationEm130FromTypeInfo(@InjectService ServiceAware<DraginoPackage> packageAware) throws IOException {
		
		Resource resource = resourceSet.createResource(URI.createURI("test-data/em310-example.json"));	
		Map<String, Object> options = new HashMap<>();		
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, LorawanPackage.eINSTANCE.getUplinkMessage());
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		resource.load(options);

		// get the person
		assertNotNull(resource);
		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(EM310UDLUplink.class);
		
		EM310UDLUplink msg = (EM310UDLUplink) resource.getContents().get(0);
		assertThat(msg).isNotNull();
		assertThat(msg.getDeduplicationId()).isEqualTo("a98ecdf0-c93c-4d7f-9b55-e99d0eba5b9b");
		assertThat(msg.getTime()).isEqualTo("2025-07-07T15:42:11.141117+00:00");
		assertThat(msg.getDevAddr()).isEqualTo("0103fd23");
		assertTrue(msg.isAdr());
		assertThat(msg.getDr()).isEqualTo(2);
		assertThat(msg.getFCnt()).isEqualTo(55);
		assertThat(msg.getFPort()).isEqualTo(85);
		assertFalse(msg.isConfirmed());
		assertThat(msg.getData()).isEqualTo("AXVFA4JSAgQAAA==");
		
		assertThat(msg.getDeviceInfo()).isNotNull();
		DeviceInfo deviceInfo = msg.getDeviceInfo();
		assertThat(deviceInfo.getTenantId()).isEqualTo("52f14cd4-c6f1-4fbd-8f87-4025e1d49242");
		assertThat(deviceInfo.getTenantName()).isEqualTo("Jena");
		assertThat(deviceInfo.getApplicationId()).isEqualTo("e8d62144-b9b6-4fee-937f-5d8248eee499");
		assertThat(deviceInfo.getApplicationName()).isEqualTo("TrashLevel_KSJ");
		assertThat(deviceInfo.getDeviceProfileId()).isEqualTo("1e6d47f7-1432-4c83-b48d-dd34a7e5e9c6");
		assertThat(deviceInfo.getDeviceProfileName()).isEqualTo("EM310-UDL");
		assertThat(deviceInfo.getDeviceName()).isEqualTo("EM310-UDL_6713C40192590001");
		assertThat(deviceInfo.getDevEui()).isEqualTo("24e124713c401925");
		assertThat(deviceInfo.getTags()).isNotNull();
		assertThat(deviceInfo.getTags().getDev_type()).isEqualTo("em310-udl");
		
		org.eclipse.fennec.em310udl.mesage.model.em310udl.DecodedObject object = msg.getObject();
		assertThat(object).isNotNull();
		assertThat(object.getDistance()).isEqualTo(594.0);
		assertThat(object.getPosition()).isEqualTo("normal");
		assertThat(object.getBattery()).isEqualTo(69.0);
		
		
		assertThat(msg.getRxInfo()).hasSize(1);
		RxInfo rx1 = null;
		for(RxInfo rx : msg.getRxInfo()) {
			if("503139535d604750".equals(rx.getGatewayId())) {
				rx1 = rx;
			}
		}
		assertThat(rx1).isNotNull();
	
		assertThat(rx1.getUplinkId()).isEqualTo(44289);
		assertThat(rx1.getTime()).isEqualTo("2025-07-07T15:42:11.141117+00:00");
		assertThat(rx1.getRssi()).isEqualTo(-103);
		assertThat(rx1.getSnr()).isEqualTo(1.0);
		assertThat(rx1.getChannel()).isEqualTo(4);
		assertThat(rx1.getContext()).isEqualTo("JkxkFA==");
		assertThat(rx1.getLocation()).isNotNull();
		assertThat(rx1.getLocation().getLatitude()).isEqualTo(50.88423992258394);
		assertThat(rx1.getLocation().getLongitude()).isEqualTo(11.596755981445314);
		assertThat(rx1.getLocation().getAltitude()).isEqualTo(0.0);
		assertThat(rx1.getMetadata()).isNotNull();
		assertThat(rx1.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx1.getMetadata().getRegion_common_name()).isEqualTo("EU868");
	
		
		assertThat(msg.getTxInfo()).isNotNull();
		assertThat(msg.getTxInfo().getFrequency()).isEqualTo(867300000);
		assertThat(msg.getTxInfo().getModulation()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora().getBandwidth()).isEqualTo(125000);
		assertThat(msg.getTxInfo().getModulation().getLora().getSpreadingFactor()).isEqualTo(10);
		assertThat(msg.getTxInfo().getModulation().getLora().getCodeRate()).isEqualTo("CR_4_5");
	}
	
	@Test
	public void testDeserializationEm130FromTypeInfoOptions(@InjectService ServiceAware<DraginoPackage> packageAware) throws IOException {
		
		Resource resource = resourceSet.createResource(URI.createURI("test-data/em310-example.json"));	
		Map<String, Object> options = new HashMap<>();		
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, LorawanPackage.eINSTANCE.getUplinkMessage());
		options.put(CodecModuleOptions.CODEC_MODULE_USE_NAMES_FROM_EXTENDED_METADATA, true);
		Map<String, Object> classOptions = new HashMap<>();		
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_KEY, "dr");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_STRATEGY, "URI");
		classOptions.put(CodecModelInfoOptions.CODEC_TYPE_MAP, Map.of("0", "https://eclipse.org/fennec/lorawan/dragino#//DraginoLSE01Uplink",
				"2", "http://www.example.org/lorawan/specific/em310udl#//EM310UDLUplink"));
		options.put(CodecResourceOptions.CODEC_OPTIONS, Map.of(LorawanPackage.eINSTANCE.getUplinkMessage(), classOptions));
		resource.load(options);

		// get the person
		assertNotNull(resource);
		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(EM310UDLUplink.class);
		
		EM310UDLUplink msg = (EM310UDLUplink) resource.getContents().get(0);
		assertThat(msg).isNotNull();
		assertThat(msg.getDeduplicationId()).isEqualTo("a98ecdf0-c93c-4d7f-9b55-e99d0eba5b9b");
		assertThat(msg.getTime()).isEqualTo("2025-07-07T15:42:11.141117+00:00");
		assertThat(msg.getDevAddr()).isEqualTo("0103fd23");
		assertTrue(msg.isAdr());
		assertThat(msg.getDr()).isEqualTo(2);
		assertThat(msg.getFCnt()).isEqualTo(55);
		assertThat(msg.getFPort()).isEqualTo(85);
		assertFalse(msg.isConfirmed());
		assertThat(msg.getData()).isEqualTo("AXVFA4JSAgQAAA==");
		
		assertThat(msg.getDeviceInfo()).isNotNull();
		DeviceInfo deviceInfo = msg.getDeviceInfo();
		assertThat(deviceInfo.getTenantId()).isEqualTo("52f14cd4-c6f1-4fbd-8f87-4025e1d49242");
		assertThat(deviceInfo.getTenantName()).isEqualTo("Jena");
		assertThat(deviceInfo.getApplicationId()).isEqualTo("e8d62144-b9b6-4fee-937f-5d8248eee499");
		assertThat(deviceInfo.getApplicationName()).isEqualTo("TrashLevel_KSJ");
		assertThat(deviceInfo.getDeviceProfileId()).isEqualTo("1e6d47f7-1432-4c83-b48d-dd34a7e5e9c6");
		assertThat(deviceInfo.getDeviceProfileName()).isEqualTo("EM310-UDL");
		assertThat(deviceInfo.getDeviceName()).isEqualTo("EM310-UDL_6713C40192590001");
		assertThat(deviceInfo.getDevEui()).isEqualTo("24e124713c401925");
		assertThat(deviceInfo.getTags()).isNotNull();
		assertThat(deviceInfo.getTags().getDev_type()).isEqualTo("em310-udl");
		
		org.eclipse.fennec.em310udl.mesage.model.em310udl.DecodedObject object = msg.getObject();
		assertThat(object).isNotNull();
		assertThat(object.getDistance()).isEqualTo(594.0);
		assertThat(object.getPosition()).isEqualTo("normal");
		assertThat(object.getBattery()).isEqualTo(69.0);
		
		
		assertThat(msg.getRxInfo()).hasSize(1);
		RxInfo rx1 = null;
		for(RxInfo rx : msg.getRxInfo()) {
			if("503139535d604750".equals(rx.getGatewayId())) {
				rx1 = rx;
			}
		}
		assertThat(rx1).isNotNull();
	
		assertThat(rx1.getUplinkId()).isEqualTo(44289);
		assertThat(rx1.getTime()).isEqualTo("2025-07-07T15:42:11.141117+00:00");
		assertThat(rx1.getRssi()).isEqualTo(-103);
		assertThat(rx1.getSnr()).isEqualTo(1.0);
		assertThat(rx1.getChannel()).isEqualTo(4);
		assertThat(rx1.getContext()).isEqualTo("JkxkFA==");
		assertThat(rx1.getLocation()).isNotNull();
		assertThat(rx1.getLocation().getLatitude()).isEqualTo(50.88423992258394);
		assertThat(rx1.getLocation().getLongitude()).isEqualTo(11.596755981445314);
		assertThat(rx1.getLocation().getAltitude()).isEqualTo(0.0);
		assertThat(rx1.getMetadata()).isNotNull();
		assertThat(rx1.getMetadata().getRegion_name()).isEqualTo("eu868");
		assertThat(rx1.getMetadata().getRegion_common_name()).isEqualTo("EU868");
	
		
		assertThat(msg.getTxInfo()).isNotNull();
		assertThat(msg.getTxInfo().getFrequency()).isEqualTo(867300000);
		assertThat(msg.getTxInfo().getModulation()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora()).isNotNull();
		assertThat(msg.getTxInfo().getModulation().getLora().getBandwidth()).isEqualTo(125000);
		assertThat(msg.getTxInfo().getModulation().getLora().getSpreadingFactor()).isEqualTo(10);
		assertThat(msg.getTxInfo().getModulation().getLora().getCodeRate()).isEqualTo("CR_4_5");
	}
}
