package edu.unapec.shoppingorders.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public final class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson (Object object) throws JsonProcessingException {
       return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static <T> T toEntity(String json, Class<T> type) throws IOException {
        return mapper.readValue(json, type);
    }

    public static <T> T toEntity(BufferedReader reader, Class<T> type) throws IOException {
        StringBuilder stringBuffer = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            stringBuffer.append(line);
        }

        return toEntity(stringBuffer.toString(), type);
    }
}
