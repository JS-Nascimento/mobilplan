package br.dev.jstec.efurniture.application.constraints;

import static br.dev.jstec.efurniture.application.util.ConstraintsHelper.validarConstraints;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConstraintValidatorTest {

    @BeforeEach
    void setup() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    @DisplayName("Deve lançar ConstraintException quando a validação de constraint falhar")
    void shouldThrowConstraintExceptionWhenAnnotationValidationFails() {

        var classWithAnnotations = new ClassWithAnnotations(null, null);

        var exception = assertThrows(ConstraintException.class,
            () -> ConstraintValidator.validarConstraints(classWithAnnotations));

        validarConstraints(
            exception.getMessage(),
            2,
            "string1: must not be blank",
            "string2: must not be null");
    }

    @Test
    @DisplayName("Deve validar as constraints com sucesso")
    void shouldNotThrowConstraintExceptionWhenValidationsIsValid() {

        assertDoesNotThrow(() -> ConstraintValidator.validarConstraints(
            new ClassWithAnnotations("string1", "string2")));
    }

    @AllArgsConstructor
    static class ClassWithAnnotations {

        @NotBlank
        private final String string1;

        @NotNull
        private final String string2;
    }
}