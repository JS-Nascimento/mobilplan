package br.dev.jstec.mobilplan.domain.util;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = PRIVATE)
public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public static String toJson(Object object) {

        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }
}
