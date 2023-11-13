package br.dev.jstec.mobilplan.application.domain;

import static br.dev.jstec.mobilplan.application.domain.TipoTelefone.CELULAR;
import static br.dev.jstec.mobilplan.application.domain.TipoTelefone.FIXO;
import static br.dev.jstec.mobilplan.application.domain.TipoTelefone.WHATSAPP;
import static br.dev.jstec.mobilplan.application.domain.TipoTelefone.byOrdinal;
import static br.dev.jstec.mobilplan.application.domain.TipoTelefone.of;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_TIPO_TELEFONE_INEXISTENTE;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_TIPO_TELEFONE_NULO;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarInteger;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarString;
import static java.lang.String.valueOf;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TipoTelefoneTest {

    @Test
    @DisplayName("Deve recuperar o enum pelo nome")
    void shouldRetrieveEnumByDescricao() {

        assertEquals(FIXO, of("FIXO"));
        assertEquals(CELULAR, of("CELULAR"));
        assertEquals(WHATSAPP, of("WHATSAPP"));
    }

    @Test
    @DisplayName("Deve retornar nulo quando a descrição for nula")
    void shouldReturnNullWhenDescricaoIsNull() {

        var exception = assertThrows(BusinessException.class,
            () -> of(null));

        assertEquals(ERRO_TIPO_TELEFONE_NULO.getMsg(), exception.getErrorMessage().getMsg());
        assertEquals(ERRO_TIPO_TELEFONE_NULO.getCode(), exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for inválida")
    void shouldThrowExceptionWhenDescricaoIsInvalid() {

        var descricao = gerarString();

        var exception = assertThrows(BusinessException.class, () -> of(descricao));

        assertEquals(format(ERRO_TIPO_TELEFONE_INEXISTENTE.getMsg(), descricao),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_TIPO_TELEFONE_INEXISTENTE.getCode(),
            exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lançar exceção quando buscar pelo ordinal e o mesmo não existir")
    void shouldThrowExceptionWhenOrdinalIsInvalid() {

        var ordinal = gerarInteger(1000, 9999);

        var exception = assertThrows(BusinessException.class, () -> byOrdinal(ordinal));

        assertEquals(format(ERRO_TIPO_TELEFONE_INEXISTENTE.getMsg(), valueOf(ordinal)),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_TIPO_TELEFONE_INEXISTENTE.getCode(),
            exception.getErrorMessage().getCode());
    }

}