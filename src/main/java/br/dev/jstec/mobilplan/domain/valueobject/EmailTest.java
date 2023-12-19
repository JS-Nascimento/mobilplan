package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

    private static final String CAMPO = "Email";

    @Test
    @DisplayName("Deve disparar exceção quando o email for nulo")
    void shouldThrowExceptionWhenEmailIsNull() {

        var exception = assertThrows(DomainException.class,
            EmailFixture::buildEmailNulo);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), CAMPO),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o email for vazio")
    void shouldThrowExceptionWhenEmailIsEmpty() {

        var exception = assertThrows(DomainException.class,
            EmailFixture::buildEmailVazio);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), CAMPO),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o email for inválido")
    void shouldThrowExceptionWhenEmailIsInvalid() {

        var exception = assertThrows(DomainException.class,
            EmailFixture::buildEmailInvalido);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), CAMPO),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Não deve disparar exceção quando criar um email válido")
    void shouldNotThrowExceptionWhenCreateValidEmail() {

        assertDoesNotThrow(EmailFixture::buildEmailValido);

    }
}