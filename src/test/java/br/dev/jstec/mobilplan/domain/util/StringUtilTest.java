package br.dev.jstec.mobilplan.domain.util;

import static br.dev.jstec.mobilplan.domain.util.StringUtil.normalizeString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StringUtilTest {

    private static Stream<Arguments> provideNamesForNormalization() {
        return Stream.of(
            of("joÃO SILVA de sOUsA", "João Silva de Sousa"),
            of("MARIA", "Maria"),
            of("john-doe", "John-Doe"),
            of("john123", "John123"),
            of("", ""),
            of("   john   doe  ", "John Doe"),
            of("a", "a"),
            of("al", "al"),
            of("ali", "Ali")
        );
    }

    @ParameterizedTest
    @DisplayName("Deve normalizar corretamente os nomes baseando-se em casos variados")
    @MethodSource("provideNamesForNormalization")
    void deveNormalizarNomesBaseadoEmCasoVariados(String input, String expected) {
        var nomeNormalizado = StringUtil.normalizarNome(input);
        assertEquals(expected, nomeNormalizado);
    }

    @Test
    @DisplayName("Deve normalizar uma string contendo caracteres diacríticos")
    void shouldNormalizeStringContainingDiacriticalMarks() {
        var input = "ÁéÍóÜñ";
        var expected = "AeIoUn";

        var actual = normalizeString(input);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Deve retornar a mesma string se não contiver caracteres diacríticos")
    void shouldReturnSameStringIfNotContainingDiacriticalMarks() {
        var input = "Hello World";
        var expected = "Hello World";

        var actual = normalizeString(input);

        assertEquals(expected, actual);
    }
}
