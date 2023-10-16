package br.dev.jstec.efurniture.application.domain.marceneiro;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarString;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MarceneiroIdTest {

    @Test
    @DisplayName("Deve lançar exceção quando o valor é nulo")
    void shouldThrowExceptionWhenValueIsNull() {
        var exception = assertThrows(
            BusinessException.class,
            () -> MarceneiroId.with(null)
        );

        assertEquals(format(ERRO_ID_INVALIDO.getMsg(), "marceneiro"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_ID_INVALIDO.getCode(), exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve criar um ID único quando chamado o método unique")
    void shouldCreateUniqueIdWhenUniqueMethodIsCalled() {

        var id = MarceneiroId.unique();

        assertNotNull(id);
        assertDoesNotThrow(() -> UUID.fromString(id.value()));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o valor não é um UUID válido")
    void shouldThrowExceptionWhenValueIsNotValidUuid() {

        var invalidId = gerarString();

        var exception = assertThrows(
            BusinessException.class,
            () -> MarceneiroId.with(invalidId)
        );

        assertEquals(format(ERRO_ID_INVALIDO.getMsg(), "marceneiro"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_ID_INVALIDO.getCode(), exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve criar um MarceneiroId com valor válido")
    void shouldCreateMarceneiroIdWithValidValue() {

        var validId = UUID.randomUUID().toString();

        var id = MarceneiroId.with(validId);

        assertNotNull(id);
        assertEquals(validId, id.value());
    }

    @Test
    @DisplayName("Deve criar um MarceneiroId com valor válido")
    void shouldThrowExceptionWhenMarceneiroIdIsEmpty() {

        var exception = assertThrows(
            BusinessException.class,
            () -> new MarceneiroId(EMPTY)
        );

        assertEquals(format(ERRO_ID_INVALIDO.getMsg(), "marceneiro"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_ID_INVALIDO.getCode(), exception.getErrorMessage().getCode());
    }
}
