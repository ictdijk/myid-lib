package in.yagnyam.myid.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * Json Utility methods
 */
public class JsonUtils {

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    /**
     * Converts Java Object o JSON String
     * @param o Any Java Object
     * @return Object as JSON String
     */
    @SneakyThrows(IOException.class)
    public static String toJson(Object o) {
        return getObjectMapper().writeValueAsString(o);
    }

    /**
     * Converts JSON String to Java Object
     * @param jsonString JSON String
     * @param destinationClass Class that must be created by reading JSON String
     * @param <T> Class type for creating result Object
     * @return Java object populated from JSON String
     */
    @SneakyThrows(IOException.class)
    public static <T> T fromJson(String jsonString, Class<T> destinationClass) {
        return getObjectMapper().readValue(jsonString, destinationClass);
    }

    /**
     * Converts one Java Object to another Java Object of different type with same fields using JSON
     * @param from The Object that should be converted to
     * @param destinationClass Class that must be created from original Object
     * @param <T>
     * @return Copy of from Object as an Object of destination class
     */
    @SneakyThrows(IOException.class)
    public static <T> T convert(Object from, Class<T> destinationClass) {
        return getObjectMapper().readValue(toJson(from), destinationClass);
    }

}

