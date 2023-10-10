package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NomeTest {

    private static final String CAMPO = "Nome";

    @Test
    @DisplayName("Deve disparar exceção quando o nome for nulo")
    void shouldThrowExceptionWhenNomeIsNull() {

        var exception = assertThrows(BusinessException.class,
                NomeFixture::buildNomeNulo);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), CAMPO), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o nome for vazio")
    void shouldThrowExceptionWhenNomeIsEmpty() {

        var exception = assertThrows(BusinessException.class,
                NomeFixture::buildNomeVazio);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), CAMPO), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve instanciar um nome")
    void shouldCreateNome() {

        assertDoesNotThrow(NomeFixture::buildNome);

    }
}