package com.example.utilities;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for reading Json from a file and storing it in a Java Object.
 */
public class MyJson {

    private MyJson() {
    }

    private static Logger logger = LogManager.getLogger(MyJson.class);
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {

        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;

    }

    /**
     * Reads a Json file and stores it in a Java Object
     * 
     * @param path String path to the Json File
     * @return An object which stores the config variables and an array of the items
     *         listed in the Json file.
     * 
     */
    public static JsonInputReaderPOJO buildJsonInputReaderPOJO(String path) {
        JsonInputReaderPOJO jsonInputReaderObject = null;

        try {

            jsonInputReaderObject = objectMapper.readValue(Paths.get(path).toFile(), JsonInputReaderPOJO.class);

        } catch (StreamReadException sre) {
            logger.error(sre);
        } catch (DatabindException dbe) {
            logger.error(dbe);
        } catch (IOException ioe) {
            logger.error(ioe);
        } catch (Exception e) {
            logger.error(e);
        }

        return jsonInputReaderObject;

    }

}