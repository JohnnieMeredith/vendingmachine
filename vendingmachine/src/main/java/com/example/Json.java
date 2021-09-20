package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json
 */
public class Json {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {

        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;

    }

    public static JsonNode parse(String src) throws IOException {
        return objectMapper.readTree(src);

    }

    public static void main(String[] args) throws Exception {
        JsonInputReaderPOJO objectArray = null;
        try {
            objectArray = objectMapper.readValue(Paths.get("vendingmachine\\src\\main\\resources\\input.json").toFile(),
                    JsonInputReaderPOJO.class);
            System.out.println(objectArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(
                "Rows: " + objectArray.getConfig().getRows() + " Columns: " + objectArray.getConfig().getColumns());
        Items[] itemArray = objectArray.getItems();
        for (Items item : itemArray) {
            System.out.println(item);
        }

    }
}