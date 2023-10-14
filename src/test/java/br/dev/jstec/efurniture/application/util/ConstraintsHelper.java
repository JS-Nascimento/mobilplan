package br.dev.jstec.efurniture.application.util;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ConstraintsHelper {

    private static final String SEPARADOR_ERROR_CONSTRAINT = ", ";

    public static void validarConstraints(String exception, int quantity, String... messages) {

        validaMensagensDeExcecao(exception, quantity, messages);
    }

    private static void validaMensagensDeExcecao(String exception, int quantity,
        String... messages) {

        assertThat(exception.split(SEPARADOR_ERROR_CONSTRAINT))
            .hasSize(quantity)
            .extracting(String::valueOf)
            .containsExactlyInAnyOrder(messages);
    }

    public static String notBeNull(String value) {

        return constraintMessage(value, "must not be null");
    }

    public static String notBeEmpty(String value) {

        return constraintMessage(value, "must not be empty");
    }

    public static String notBeBlank(String value) {

        return constraintMessage(value, "must not be blank");
    }

    public static String mustMatch(String value, String pattern) {

        return constraintMessage(value, format("must match %s", pattern));
    }

    public static String mustBePastDate(String value) {

        return constraintMessage(value, "must be a past date");
    }

    public static String mustBeGreaterOrEqual(String value, int minValue) {

        return constraintMessage(value, format("must be greater than or equal to %d", minValue));
    }

    private static String constraintMessage(String value, String message) {

        return format("%s: %s", value, message);
    }
}