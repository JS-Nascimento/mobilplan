package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NomeComercialTest {

    private static final String CAMPO = "NomeComercial";

    @Test
    @DisplayName("Deve disparar exceção quando o nome comercial for nulo")
    void shouldThrowExceptionWhenNomeComercialIsNull() {

        var exception = assertThrows(BusinessException.class,
            NomeComercialFixture::buildNomeNulo);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), CAMPO),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o nome comercial for vazio")
    void shouldThrowExceptionWhenNomeComercialIsEmpty() {

        var exception = assertThrows(BusinessException.class,
            NomeComercialFixture::buildNomeVazio);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), CAMPO),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve criar um nome comercial com sucesso")
    void shouldCreateNomeComercial() {

        assertDoesNotThrow(NomeComercialFixture::build);
    }
}