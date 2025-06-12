package org.eclipse.fennec.codec.jsonld.component;

import java.util.Map.Entry;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.fennec.jsonld.model.jsonld.ContextObject;
import org.eclipse.fennec.jsonld.model.jsonld.ContextStringValue;
import org.eclipse.fennec.jsonld.model.jsonld.ContextValue;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLDFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.annotation.JsonFormat.Value;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.Version;
import tools.jackson.core.json.JsonWriteFeature;
import tools.jackson.databind.BeanDescription.Supplier;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.Serializers;
import tools.jackson.databind.type.TypeFactory;



@Component(immediate=true, name = "JsonLDTestComponent")
public class Example {
	
//	@Reference
//	ResourceSet resSet;
	

	@Activate
	public void activate() {
	
		
		try {
			JsonLDFactory factory = JsonLDFactory.eINSTANCE;

	        // Create the root context object
	        ContextObject contextObject = factory.createContextObject();

	        // === 1. Add a simple term: "name": "http://schema.org/name"
	        ContextStringValue nameValue = factory.createContextStringValue();
	        nameValue.setValue("http://xmlns.com/foaf/0.1/Person");
	        
//	        Map.Entry<String, ContextValue> nameTerm = Map.entry("name", nameValue);
//	        nameTerm.setKey("name");

	        ContextStringValue v1 = factory.createContextStringValue();
	        v1.setValue("http://xmlns.com/foaf/0.1/Person");

	        contextObject.getContext().put("@context", v1);
	        
	        ContextObject v2 = factory.createContextObject();
	        ContextStringValue v3 = factory.createContextStringValue();
	        v3.setValue("blabla");
	        v2.getContext().put("@id", v3);
	        
	        ContextObject v4 = factory.createContextObject();
	        ContextStringValue v5 = factory.createContextStringValue();
	        v5.setValue("etwas");
	        v4.getContext().put("something", v5);
	        v2.getContext().put("@type", v4);
//	        contextObject.getContext().put("@context", v2);
	        
//	        JsonLD jsonLD = factory.createJsonLD();
//	        jsonLD.setContext(contextObject);
	        SimpleModule module = new SimpleModule();
	        module.addSerializer(ContextObject.class, new MyObjectSerializer());

	        JsonMapper mapper = JsonMapper.builder()
	        		.disable(JsonWriteFeature.ESCAPE_FORWARD_SLASHES)
	        	    .addModule(module)
	        	    .build();

	        String ser = mapper.writeValueAsString(contextObject);
	        System.out.println(ser);
//	        Resource res = resSet.createResource(URI.createURI("test.json"));
//	        res.getContents().add(contextObject);
//	        Map<String, Object> options = new HashMap<>();
//	        options.put(EMFJs.OPTION_SERIALIZE_TYPE, false);
//	        res.save(options);

			
//			
//			String filePath = System.getProperty("data")+"person.json";
//			Path path = Path.of(filePath).toAbsolutePath();
//			
//			
//			JsonArray array =	JsonLd.expand("file:"+path.toString())    // HTTP(S) and File schemes supported // external context
//		      .get();
//
//		// Compaction
//		 JsonObject object =JsonLd.compact("file:"+path.toString(), "https://json-ld.org/contexts/person.jsonld")
//		      .compactToRelative(false)
//		      .get();
//
//		// Flattening
////		JsonStructure structure = JsonLd.flatten("https://example/document.jsonld").get();
//		
//		RdfDataset rdf = JsonLd.toRdf("file:"+path.toString()).get();
//		System.out.println("Test");
		} catch(Exception e) {
			e.printStackTrace();
		}
//		
		
	}
	

public class EMapContextValueSerializers extends Serializers.Base {
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.ser.Serializers.Base#findSerializer(tools.jackson.databind.SerializationConfig, tools.jackson.databind.JavaType, tools.jackson.databind.BeanDescription.Supplier, com.fasterxml.jackson.annotation.JsonFormat.Value)
	 */
	@Override
	public ValueSerializer<?> findSerializer(SerializationConfig config, JavaType type, Supplier beanDescRef,
			Value formatOverrides) {
		TypeFactory tf = config.getTypeFactory();

        JavaType expectedType = tf.constructParametricType(EMap.class, String.class, ContextValue.class);

        if (type.equals(expectedType)) {
            return new MyObjectSerializer(); // Your custom serializer here
        }
		return super.findSerializer(config, type, beanDescRef, formatOverrides);
	}

  
}
	
	public class MyObjectSerializer3 extends ValueSerializer<EMap<String, ContextValue>> {

	    @Override
	    public void serialize(EMap<String, ContextValue> value, JsonGenerator gen, SerializationContext ctxt) {
	        gen.writeStartObject();
	        for (Entry<String, ContextValue> entry : value.entrySet()) {
	        	 gen.writeName(entry.getKey());
	        	 if(entry.getValue() instanceof ContextStringValue strValue) {
	        		 ctxt.writeValue(gen, strValue.getValue());
	        	 } else {
	        		 ctxt.writeValue(gen, entry.getValue());
	        	 }  	 
	            
	        }
	        gen.writeEndObject();
	    }

		
	}
	
	public class MyObjectSerializer extends ValueSerializer<ContextObject> {

	    @Override
	    public void serialize(ContextObject value, JsonGenerator gen, SerializationContext ctxt) {
	    	
	        gen.writeStartObject();
	        for (Entry<String, ContextValue> entry : value.getContext().entrySet()) {
	        	 gen.writeName(entry.getKey());
	        	 if(entry.getValue() instanceof ContextStringValue strValue) {
	        		 ctxt.writeValue(gen, strValue.getValue());
	        	 } else {
	        		 ctxt.writeValue(gen, entry.getValue());
	        	 }  	 
	            
	        }
	        gen.writeEndObject();
	    }

		
	}
	
	public class EMapContextValueModule extends SimpleModule {

	    /** serialVersionUID */
	private static final long serialVersionUID = 1L;

		@Override
	    public String getModuleName() {
	        return "EMapContextValueModule";
	    }

	    @Override
	    public Version version() {
	        return Version.unknownVersion();
	    }

	    @Override
	    public void setupModule(SetupContext context) {
	        context.addSerializers(new Serializers.Base() {
	        	@Override
	        	public ValueSerializer<?> findSerializer(SerializationConfig config, JavaType type, Supplier beanDescRef,
	        			Value formatOverrides) {
	        		TypeFactory tf = config.getTypeFactory();

	                JavaType expectedType = tf.constructParametricType(EMap.class, String.class, ContextValue.class);

	                if (type.equals(expectedType)) {
	                    return new MyObjectSerializer3(); // Your custom serializer here
	                }
	        		return super.findSerializer(config, type, beanDescRef, formatOverrides);
	        	}
	        });
	    }
	}
	
	
}
