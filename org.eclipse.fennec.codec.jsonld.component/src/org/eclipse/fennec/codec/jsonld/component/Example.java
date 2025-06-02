package org.eclipse.fennec.codec.jsonld.component;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.jsonld.model.jsonld.ContextObject;
import org.eclipse.fennec.jsonld.model.jsonld.ContextObjectValue;
import org.eclipse.fennec.jsonld.model.jsonld.ContextStringValue;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLDFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.gecko.emf.json.constants.EMFJs;



@Component(immediate=true, name = "JsonLDTestComponent")
public class Example {
	
	@Reference
	ResourceSet resSet;
	

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

	        

	        contextObject.getContext().put("Person", "http://xmlns.com/foaf/0.1/Person");
	        
	        

	        // === 2. Add a complex term: "person": { "@id": "http://schema.org/Person", "@type": "@id" }
	        
	        ContextObjectValue objValue = factory.createContextObjectValue();
	        ContextStringValue v1 = factory.createContextStringValue();
	        v1.setValue("http://schema.org/v1");
//	        Map.Entry<String, ContextValue> t1 = Map.entry("v1", v1);
	        
	        ContextStringValue v2 = factory.createContextStringValue();
	        v2.setValue("http://schema.org/v2");
//	        Map.Entry<String, ContextValue> t2 = Map.entry("v2", v2);
	        
	        objValue.getProperties().put("v1", v1);
	        objValue.getProperties().put("v2", v2);
	        
//	        Map.Entry<String, ContextValue> objTerm = Map.entry("obj", objValue);
	        contextObject.getContext().put("obj", objValue);
	        
	        
	        Resource res = resSet.createResource(URI.createURI("test.json"));
	        res.getContents().add(contextObject);
	        Map<String, Object> options = new HashMap<>();
	        options.put(EMFJs.OPTION_SERIALIZE_TYPE, false);
	        res.save(options);

			
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

}
