package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_DADOS_OBRIGATORIOS_INCONSISTENTES;
import static java.text.MessageFormat.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuditInfoTest {

    @Test
    @DisplayName("Não deve lançar erro ao criar um objeto AuditInfo de criação com sucesso")
    void shouldNotExceptionWhenCreateAuditInfoOfCreationWithSuccess() {

        assertDoesNotThrow(AuditInfoFixture::buildDeCriacao);
    }

    @Test
    @DisplayName("Não deve lançar erro ao criar um objeto AuditInfo de alteração com sucesso")
    void shouldNotExceptionWhenCreateAuditInfoOfUpdateWithSuccess() {

        assertDoesNotThrow(AuditInfoFixture::buildDeAlteracao);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar criar um AuditInfo de criação inválido")
    void shouldThrowExceptionWhenCreateAuditInfoOfCreationNotValid() {

        var exception = assertThrows(BusinessException.class,
            AuditInfoFixture::buildCriacaoInvalida);

        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "Usuário"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar criar um AuditInfo de alteração com usuário inválido")
    void shouldThrowExceptionWhenCreateAuditInfoOfUpdateWithInvalidUser() {

        var exception = assertThrows(BusinessException.class,
            AuditInfoFixture::builAlteracaoComUsuarioInvalido);

        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "Usuário"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar criar um AuditInfo de criação inválido")
    void shouldThrowExceptionWhenCreateAuditInfoOfUpdateWithInvalidAuditInfo() {

        var exception = assertThrows(BusinessException.class,
            AuditInfoFixture::builAlteracaoComAuditInfoInvalido);

        assertEquals(ERRO_DADOS_OBRIGATORIOS_INCONSISTENTES.getMsg(),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_DADOS_OBRIGATORIOS_INCONSISTENTES.getCode(),
            exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve receber um Instant e retornar uma string formatada com sucesso")
    void testFromInstant() {

        var instant = Instant.now();

        var formatter = ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        var expected = formatter.format(instant);
        var actual = AuditInfo.fromInstant(instant);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Deve receber um UUID e retornar uma string com sucesso")
    void testFromUuid() {

        var uuid = UUID.randomUUID();
        var expected = uuid.toString();
        var actual = AuditInfo.fromUuid(uuid);

        assertEquals(expected, actual);
    }
}