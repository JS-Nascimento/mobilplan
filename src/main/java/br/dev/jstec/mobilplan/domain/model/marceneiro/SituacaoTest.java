package br.dev.jstec.mobilplan.domain.model.marceneiro;


import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_SITUACAO_INEXISTENTE;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_SITUACAO_NULA;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarString;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SituacaoTest {

    @Test
    @DisplayName("Deve recuperar o enum pela descrição")
    void shouldRetrieveEnumByDescricao() {

        Assertions.assertEquals(Situacao.ATIVO, Situacao.of("ATIVO"));
        Assertions.assertEquals(Situacao.INATIVO, Situacao.of("INATIVO"));
        Assertions.assertEquals(Situacao.SUSPENSO, Situacao.of("SUSPENSO"));
        Assertions.assertEquals(Situacao.BLOQUEADO, Situacao.of("BLOQUEADO"));
        Assertions.assertEquals(Situacao.CANCELADO, Situacao.of("CANCELADO"));
    }

    @Test
    @DisplayName("Deve retornar nulo quando a descrição for nula")
    void shouldReturnNullWhenDescricaoIsNull() {

        var exception = assertThrows(DomainException.class,
                () -> Situacao.of(null));

        assertEquals(ERRO_SITUACAO_NULA.getMsg(), exception.getErrorMessage().getMsg());
        assertEquals(ERRO_SITUACAO_NULA.getCode(), exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for inválida")
    void shouldThrowExceptionWhenDescricaoIsInvalid() {

        var descricao = gerarString();

        var exception = assertThrows(DomainException.class, () -> Situacao.of(descricao));

        assertEquals(format(ERRO_SITUACAO_INEXISTENTE.getMsg(), descricao), exception.getErrorMessage().getMsg());
        assertEquals(ERRO_SITUACAO_INEXISTENTE.getCode(), exception.getErrorMessage().getCode());
    }

}