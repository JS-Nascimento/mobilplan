package br.dev.jstec.efurniture.application.domain;

import static br.dev.jstec.efurniture.application.domain.TipoPessoa.FISICA;
import static br.dev.jstec.efurniture.application.domain.TipoPessoa.JURIDICA;
import static br.dev.jstec.efurniture.application.domain.TipoPessoa.of;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarString;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_PESSOA_NULO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TipoPessoaTest {

    @Test
    @DisplayName("Deve recuperar o enum pelo nome")
    void shouldRetrieveEnumByDescricao() {

        assertEquals(FISICA, of("FISICA"));
        assertEquals(JURIDICA, of("JURIDICA"));
    }

    @Test
    @DisplayName("Deve retornar nulo quando a descrição for nula")
    void shouldReturnNullWhenDescricaoIsNull() {

        var exception = assertThrows(BusinessException.class,
            () -> of(null));

        assertEquals(ERRO_TIPO_PESSOA_NULO.getMsg(), exception.getErrorMessage().getMsg());
        assertEquals(ERRO_TIPO_PESSOA_NULO.getCode(), exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for inválida")
    void shouldThrowExceptionWhenDescricaoIsInvalid() {

        var descricao = gerarString();

        assertThrows(BusinessException.class, () -> of(descricao));
    }
}