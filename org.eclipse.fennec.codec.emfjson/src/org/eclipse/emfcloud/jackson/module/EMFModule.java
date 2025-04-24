/*******************************************************************************
 * Copyright (c) 2019-2022 Guillaume Hillairet and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *******************************************************************************/
package org.eclipse.emfcloud.jackson.module;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.annotations.EcoreIdentityInfo;
import org.eclipse.emfcloud.jackson.annotations.EcoreReferenceInfo;
import org.eclipse.emfcloud.jackson.annotations.EcoreTypeInfo;
import org.eclipse.emfcloud.jackson.databind.deser.EMFDeserializers;
import org.eclipse.emfcloud.jackson.databind.deser.EcoreReferenceDeserializer;
import org.eclipse.emfcloud.jackson.databind.deser.ReferenceEntry;
import org.eclipse.emfcloud.jackson.databind.ser.EMFSerializers;
import org.eclipse.emfcloud.jackson.databind.ser.EcoreReferenceSerializer;
import org.eclipse.emfcloud.jackson.databind.ser.NullKeySerializer;
import org.eclipse.emfcloud.jackson.handlers.BaseURIHandler;
import org.eclipse.emfcloud.jackson.handlers.URIHandler;

import tools.jackson.core.Version;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

/**
 * Module implementation that allows serialization and deserialization of
 * EMF objects (EObject and Resource).
 */
@Deprecated
public class EMFModule extends SimpleModule {

   private static final long serialVersionUID = 1L;

   private EcoreReferenceInfo referenceInfo;
   private EcoreTypeInfo typeInfo;
   private EcoreIdentityInfo identityInfo;

   private ValueSerializer<EObject> referenceSerializer;
   private ValueDeserializer<ReferenceEntry> referenceDeserializer;

   public void setTypeInfo(final EcoreTypeInfo info) { this.typeInfo = info; }

   public void setIdentityInfo(final EcoreIdentityInfo identityInfo) { this.identityInfo = identityInfo; }

   public void setReferenceInfo(final EcoreReferenceInfo referenceInfo) { this.referenceInfo = referenceInfo; }

   public void setReferenceSerializer(final ValueSerializer<EObject> serializer) {
      this.referenceSerializer = serializer;
   }

   public ValueSerializer<EObject> getReferenceSerializer() { return referenceSerializer; }

   public void setReferenceDeserializer(final ValueDeserializer<ReferenceEntry> deserializer) {
      this.referenceDeserializer = deserializer;
   }

   public ValueDeserializer<ReferenceEntry> getReferenceDeserializer() { return referenceDeserializer; }

   /**
    * Enumeration that defines all possible options that can be used
    * to customize the behaviour of the EMF Module.
    */
   public enum Feature {
	   
	   NONE(false),

      /**
       * Option used to indicate the module to use the default ID serializer if
       * none are provided. The ID serializer used by default is IdSerializer.
       */
      OPTION_USE_ID(false),

      /**
       * Option used to indicate the module to use the default type serializer if
       * none are provided. The type serializer used by default is ETypeSerializer.
       */
      OPTION_SERIALIZE_TYPE(true),

      /**
       * Option used to indicate the module to serialize default attributes values.
       * Default values are not serialized by default.
       */
      OPTION_SERIALIZE_DEFAULT_VALUE(false),

      /**
       * Option used to indicate whether feature names specified in
       * {@link org.eclipse.emf.ecore.util.ExtendedMetaData} annotations should
       * be respected.
       */
      OPTION_USE_NAMES_FROM_EXTENDED_META_DATA(true);

      private final boolean defaultState;
      private final int mask;

      public static int collectDefaults() {
         int flags = 0;
         for (Feature f : values()) {
            if (f.enabledByDefault()) {
               flags |= f.getMask();
            }
         }
         return flags;
      }

      Feature(final Boolean defaultState) {
         this.defaultState = defaultState;
         this.mask = (1 << ordinal());
      }

      public boolean enabledIn(final int flags) {
         return (flags & mask) != 0;
      }

      public boolean enabledByDefault() {
         return defaultState;
      }

      public int getMask() { return mask; }
   }

   protected static final int DEFAULT_FEATURES = Feature.collectDefaults();

   /**
    * Returns a pre configured mapper with the EMF module.
    *
    * @return mapper
    */
   public static ObjectMapper setupDefaultMapper() {
      return setupDefaultMapper(null);
   }

   /**
    * Returns a pre configured mapper using the EMF module and the specified jackson factory.
    * This method can be used to work with formats others than JSON (such as YAML).
    *
    * @param factory Jackson factory
    * @return mapper
    */
//   public static ObjectMapper setupDefaultMapper(final TokenStreamFactory factory) {
//      final ObjectMapper mapper;
//      // same as emf
//      final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
//      dateFormat.setTimeZone(TimeZone.getDefault());
//
//      mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//      mapper.serializationConfig().getDateFormat() setDateFormat(dateFormat);
//      mapper.setTimeZone(TimeZone.getDefault());
//      mapper.  registerModule(new EMFModule());
//      // add default serializer for null EMap key
//      this.setDefaultNullKeySerializer(new NullKeySerializer());
//
//      
//      JsonFactory f3 = JsonFactory.builder() 
//    		    .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS)
//    		    .build();
//    		ObjectMapper mapper3 = JsonMapper.builder(f3)
//    		   .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
//    		   .addMixIn(MyValue.class, MixinOverrides.class)
//    		   .build();
//      
//      return mapper;
//   }
   
   public static ObjectMapper setupDefaultMapper(final JsonFactory factory) {
	   
	   final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
       dateFormat.setTimeZone(TimeZone.getDefault());
	   JsonMapper.Builder mapperBuilder = JsonMapper.builder(factory);
	   mapperBuilder.enable(SerializationFeature.INDENT_OUTPUT);
	   EMFModule module = new EMFModule();
	   module.setDefaultNullKeySerializer(new NullKeySerializer());
	   mapperBuilder.addModule(module);
	   mapperBuilder.defaultDateFormat(dateFormat);
	   mapperBuilder.defaultTimeZone(TimeZone.getDefault());	   
	   return mapperBuilder.build();
   }

   protected int moduleFeatures = DEFAULT_FEATURES;

   private URIHandler handler;

   public EMFModule() {}

   @Override
   public void setupModule(final SetupContext context) {
      if (handler == null) {
         handler = new BaseURIHandler();
      }

      if (typeInfo == null) {
         typeInfo = new EcoreTypeInfo();
      }

      if (identityInfo == null) {
         identityInfo = new EcoreIdentityInfo();
      }

      if (referenceInfo == null) {
         referenceInfo = new EcoreReferenceInfo(handler);
      }

      if (referenceSerializer == null) {
         referenceSerializer = new EcoreReferenceSerializer(referenceInfo, typeInfo);
      }

      if (referenceDeserializer == null) {
         referenceDeserializer = new EcoreReferenceDeserializer(referenceInfo, typeInfo);
      }

      EMFDeserializers deserializers = new EMFDeserializers(this);
      EMFSerializers serializers = new EMFSerializers(this);

      context.addDeserializers(deserializers);
      context.addSerializers(serializers);

      super.setupModule(context);
   }

   @Override
   public String getModuleName() { return "emfjson-module"; }

   @Override
   public Version version() {
      return new Version(1, 0, 0, "rc1", "org.eclipse.emfcloud", "emfjson-jackson");
   }

   private EMFModule enable(final Feature f) {
      moduleFeatures |= f.getMask();
      return this;
   }

   private EMFModule disable(final Feature f) {
      moduleFeatures &= ~f.getMask();
      return this;
   }

   /**
    * Returns true if the current feature is used by the module.
    *
    * @param f feature
    * @return true if used
    */
   public final boolean isEnabled(final Feature f) {
      return (moduleFeatures & f.getMask()) != 0;
   }

   public int getFeatures() { return moduleFeatures; }

   /**
    * Configures the module with one of possible Feature.
    *
    * @param feature feature
    * @param state   of feature
    * @return EMFModule
    */
   public EMFModule configure(final Feature feature, final boolean state) {
      if (state) {
         enable(feature);
      } else {
         disable(feature);
      }
      return this;
   }

   /**
    * Tells the module which URIHandler to use to de/resolve URIs during
    * de/serialization.
    *
    * @param handler use for de/serialization
    */
   public void setUriHandler(final URIHandler handler) { this.handler = handler; }

   /**
    * Returns the URIHandler that will be used to de/resolve URIs during
    * de/serialization.
    *
    * @return handler
    */
   public URIHandler getUriHandler() { return handler; }

   public EcoreIdentityInfo getIdentityInfo() { return identityInfo; }

   public EcoreTypeInfo getTypeInfo() { return typeInfo; }

   public EcoreReferenceInfo getReferenceInfo() { return referenceInfo; }

}
