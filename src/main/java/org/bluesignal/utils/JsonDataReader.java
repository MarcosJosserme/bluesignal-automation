package org.bluesignal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class JsonDataReader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonDataReader() {
        // Evita instanciar una clase que solamente contiene utilidades.
    }

    public static <T> T read(
            String resourcePath,
            Class<T> targetType
    ) {

        ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream =
                     classLoader.getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new IllegalArgumentException(
                        "No se encontró el recurso JSON: " + resourcePath
                );
            }

            return OBJECT_MAPPER.readValue(
                    inputStream,
                    targetType
            );

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "No se pudo leer el archivo JSON: " + resourcePath,
                    exception
            );
        }
    }
}