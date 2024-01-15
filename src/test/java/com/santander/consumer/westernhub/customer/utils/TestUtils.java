package com.santander.consumer.westernhub.customer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.santander.ademma.common.exception.MappingException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestUtils {


    public static String fileToJson(String nameFile) throws IOException {
        var classLoader = TestUtils.class.getClassLoader();
        var is = classLoader.getResourceAsStream(nameFile);
        return IOUtils.toString(is, StandardCharsets.UTF_8);
    }

    public static <T> List<T> getListObj(String json, Class<T> clazz) {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new MappingException(e.getMessage());
        }
    }

    /**
     * Gets the obj.
     *
     * @param <T>   the generic type
     * @param json  the json
     * @param clazz the clazz
     * @return the obj
     */
    public static <T> T getObj(String json, Class<T> clazz) {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new MappingException(e.getMessage());
        }
    }

}
