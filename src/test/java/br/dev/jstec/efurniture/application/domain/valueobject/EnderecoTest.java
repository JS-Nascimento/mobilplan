package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnderecoTest {

    @Test
    @DisplayName("Deve disparar exceção quando o CEP for Inválido ")
    void shouldThrowExceptionWhenCepIsNull() {

        var exception = assertThrows(BusinessException.class,
                EnderecoFixture::buildCepInvalido);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "CEP"), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o CEP for nulo")
    void shouldThrowExceptionWhenCepIsNotValid() {

        var exception = assertThrows(BusinessException.class,
                EnderecoFixture::buildCepNulo);

        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getCode(), exception.getErrorMessage().getCode());
        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getMsg(), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o UF for inválido")
    void shouldThrowExceptionWhenUfIsNotValid() {

        var exception = assertThrows(BusinessException.class,
                EnderecoFixture::buildComUfInvalido);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "UF"), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Não deve disparar exceção quando criar um endereço válido")
    void shouldNotThrowExceptionWhenCreateValidEndereco() {

        assertDoesNotThrow(EnderecoFixture::build);

    }
}