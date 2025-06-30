package org.eclipse.fennec.codec.gemini;

// --- 10. ResourceServiceImpl.java (Implementation of ResourceService) ---
// This implementation demonstrates how to obtain and use the ObjectMapper from the extender.

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.math.BigDecimal; // For BigDecimal example

/**
 * Implementation of ResourceService that uses JacksonExtenderService.
 */
@Component(service = ResourceService.class, immediate = true)
public class ResourceServiceImpl implements ResourceService {

    private JacksonExtenderService jacksonExtenderService;
    private ObjectMapper defaultObjectMapper;
    private ObjectMapper specialObjectMapper;

    @Reference
    protected void setJacksonExtenderService(JacksonExtenderService jacksonExtenderService) {
        this.jacksonExtenderService = jacksonExtenderService;
    }

    protected void unsetJacksonExtenderService(JacksonExtenderService jacksonExtenderService) {
        this.jacksonExtenderService = null;
    }

    @Activate
    protected void activate() {
        System.out.println("ResourceServiceImpl activated.");
        // Get the default ObjectMapper
        this.defaultObjectMapper = jacksonExtenderService.getObjectMapper();
        System.out.println("Default ObjectMapper obtained: " + defaultObjectMapper);

        // Get the named ObjectMapper if needed
        this.specialObjectMapper = jacksonExtenderService.getObjectMapper("SpecialConfig");
        System.out.println("SpecialConfig ObjectMapper obtained: " + specialObjectMapper);

        // Example usage:
        try {
            // Create some dummy data
            ObjectNode rootNode = defaultObjectMapper.createObjectNode();
            rootNode.put("message", "Hello from OSGi Jackson!");
            rootNode.put("version", 1.0);
            rootNode.put("timestamp", System.currentTimeMillis());
            rootNode.put("bigDecimalValue", new BigDecimal("12345.67890")); // Test StreamWriteFeature

            // Save using default ObjectMapper
            saveResource("default_resource.json", rootNode);
            JsonNode loadedDefault = loadResource("default_resource.json");
            System.out.println("Loaded default resource:\n" + defaultObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loadedDefault));

            // Create some dummy data for special config
            ObjectNode specialNode = specialObjectMapper.createObjectNode();
            specialNode.put("specialMessage", "This is a special resource!");
            specialNode.put("type", "secure");
            // Note: If FAIL_ON_EMPTY_BEANS is enabled, and this object were a POJO with no fields, it would fail.
            // For JsonNode, it's fine.

            // Save using special ObjectMapper
            saveResource("special_resource.json", specialNode);
            JsonNode loadedSpecial = loadResource("special_resource.json");
            System.out.println("Loaded special resource:\n" + specialObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loadedSpecial));


        } catch (Exception e) {
            System.err.println("Error in ResourceServiceImpl activation example: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Deactivate
    protected void deactivate() {
        this.defaultObjectMapper = null;
        this.specialObjectMapper = null;
        System.out.println("ResourceServiceImpl deactivated.");
    }

    @Override
    public JsonNode loadResource(String resourcePath) throws Exception {
        System.out.println("Loading resource from: " + resourcePath);
        String content = new String(Files.readAllBytes(Paths.get(resourcePath)));
        // Use the appropriate ObjectMapper based on context
        if (resourcePath.contains("special")) {
            return specialObjectMapper.readTree(content);
        }
        return defaultObjectMapper.readTree(content);
    }

    @Override
    public void saveResource(String resourcePath, JsonNode data) throws Exception {
        System.out.println("Saving resource to: " + resourcePath);
        ObjectMapper mapperToUse = resourcePath.contains("special") ? specialObjectMapper : defaultObjectMapper;
        mapperToUse.writerWithDefaultPrettyPrinter().writeValue(new File(resourcePath), data);
    }
}
