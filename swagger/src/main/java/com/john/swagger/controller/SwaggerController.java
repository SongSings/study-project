package com.john.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SwaggerController {

    @Autowired
    private RestTemplate restTemplate;

    private final String[] swaggerUrls = {
            "http://192.168.2.1:10001/v2/api-docs?group=a",
            "http://192.168.2.1:10002/v2/api-docs?group=b"
            // 添加更多项目的 Swagger URL
    };

    @GetMapping("/api-docs")
    public Map<String, Object> getApiDocs() {
        Map<String, Object> combinedSwagger = new HashMap<>();
        combinedSwagger.put("openapi", "3.0.0");
        combinedSwagger.put("info", Map.of("title", "Combined API Documentation", "version", "1.0.0"));
        combinedSwagger.put("paths", new HashMap<>());
        combinedSwagger.put("components", Map.of("schemas", new HashMap<>()));

        for (String url : swaggerUrls) {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> swaggerDoc = response.getBody();
                if (swaggerDoc != null) {
                    ((Map) combinedSwagger.get("paths")).putAll((Map<String, Object>) swaggerDoc.getOrDefault("paths", new HashMap<>()));
                    ((Map) ((Map) combinedSwagger.get("components")).get("schemas")).putAll(
                            ((Map<String, HashMap>) swaggerDoc.getOrDefault("components", Map.of())).getOrDefault("schemas", new HashMap<>()));
                }
            }
        }

        return combinedSwagger;
    }
}