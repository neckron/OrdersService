package com.demo.service.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.*;


import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TestsUtils {
    private static Path workingDirectory;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonStringFromFile(String fileName) throws IOException {
        final Resource resource = new ClassPathResource(fileName);
        String jsonFromFileAsString = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
        return jsonFromFileAsString;
    }

    public static String getJsonFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

}
