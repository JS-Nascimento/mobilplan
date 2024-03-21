package br.dev.jstec.mobilplan.application.helpers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClassHelper {

    public static List<String> getFieldNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .toList();
    }

    public static List<Map<String, String>> getFieldNamesAndTypes(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(field -> Map.of(
                        "name", field.getName(),
                        "type", field.getType().getName()))
                .toList();
    }
}
