package br.dev.jstec.mobilplan.application.domain.marceneiro;

import static br.dev.jstec.mobilplan.application.domain.marceneiro.Situacao.ATIVO;
import static br.dev.jstec.mobilplan.application.domain.marceneiro.Situacao.BLOQUEADO;
import static br.dev.jstec.mobilplan.application.domain.marceneiro.Situacao.CANCELADO;
import static br.dev.jstec.mobilplan.application.domain.marceneiro.Situacao.INATIVO;
import static br.dev.jstec.mobilplan.application.domain.marceneiro.Situacao.SUSPENSO;
import static br.dev.jstec.mobilplan.application.domain.marceneiro.Situacao.of;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SITUACAO_INEXISTENTE;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SITUACAO_NULA;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarString;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SituacaoTest {

    @Test
    @DisplayName("Deve recuperar o enum pela descrição")
    void shouldRetrieveEnumByDescricao() {

        assertEquals(ATIVO, of("ATIVO"));
        assertEquals(INATIVO, of("INATIVO"));
        assertEquals(SUSPENSO, of("SUSPENSO"));
        assertEquals(BLOQUEADO, of("BLOQUEADO"));
        assertEquals(CANCELADO, of("CANCELADO"));
    }

    @Test
    @DisplayName("Deve retornar nulo quando a descrição for nula")
    void shouldReturnNullWhenDescricaoIsNull() {

        var exception = assertThrows(BusinessException.class,
            () -> of(null));

        assertEquals(ERRO_SITUACAO_NULA.getMsg(), exception.getErrorMessage().getMsg());
        assertEquals(ERRO_SITUACAO_NULA.getCode(), exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for inválida")
    void shouldThrowExceptionWhenDescricaoIsInvalid() {

        var descricao = gerarString();

        var exception = assertThrows(BusinessException.class, () -> of(descricao));

        assertEquals(format(ERRO_SITUACAO_INEXISTENTE.getMsg(), descricao), exception.getErrorMessage().getMsg());
        assertEquals(ERRO_SITUACAO_INEXISTENTE.getCode(), exception.getErrorMessage().getCode());
    }

}