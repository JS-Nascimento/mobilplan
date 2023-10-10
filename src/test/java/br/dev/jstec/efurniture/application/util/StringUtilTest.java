package br.dev.jstec.efurniture.application.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StringUtilTest {

    @ParameterizedTest
    @DisplayName("Deve normalizar corretamente os nomes baseando-se em casos variados")
    @MethodSource("provideNamesForNormalization")
    void deveNormalizarNomesBaseadoEmCasoVariados(String input, String expected) {
        var nomeNormalizado = StringUtil.normalizarNome(input);
        assertEquals(expected, nomeNormalizado);
    }

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
}
