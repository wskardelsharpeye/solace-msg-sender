package com.example.demo.utils;

import com.example.demo.dto.Option;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RequestBuilder {

    private final static String filePath = "static/RFQ.json";

    public static List<Option> build() {
        return readJsonFileToList(filePath);
    }

    private static List<Option> readJsonFileToList(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            List<Option> options = objectMapper.readValue(inputStream, new TypeReference<List<Option>>() {});
            return options;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
    }
}