package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarCnpj;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarCpf;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarString;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CNPJ_INVALIDO;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CPF_INVALIDO;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_PESSOA_INEXISTENTE;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_PESSOA_NULO;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@AllArgsConstructor
class TipoClienteTest {

    @Test
    @DisplayName("Deve disparar exceção quando o Tipo Pessoa for nulo")
    void shouldThrowExceptionWhenTipoPessoaIsNull() {

        var exception = assertThrows(BusinessException.class,
                TipoClienteFixture::buildTipoClienteTipoPessoaNulo);

        assertEquals(ERRO_TIPO_PESSOA_NULO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(ERRO_TIPO_PESSOA_NULO.getMsg(), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o Tipo Pessoa não for válido")
    void shouldThrowExceptionWhenTipoPessoaNotValid() {

        var tipoPessoa = gerarString();

        var exception = assertThrows(BusinessException.class,
                () -> TipoClienteFixture.buildTipoClienteTipoPessoaInvalida(tipoPessoa));

        assertEquals(ERRO_TIPO_PESSOA_INEXISTENTE.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_TIPO_PESSOA_INEXISTENTE.getMsg(), tipoPessoa), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o CPF não for válido")
    void shouldThrowExceptionWhenCpfNotValid() {

        var cpf = gerarCpf(false);

        var exception = assertThrows(BusinessException.class,
                () -> TipoClienteFixture.buildTipoClienteCpfInvalido(cpf));

        assertEquals(ERRO_CPF_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CPF_INVALIDO.getMsg(), cpf), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o CNPJ não for válido")
    void shouldThrowExceptionWhenCnpjNotValid() {

        var cnpj = gerarCnpj(false);

        var exception = assertThrows(BusinessException.class,
                () -> TipoClienteFixture.buildTipoClienteCnpjInvalido(cnpj));

        assertEquals(ERRO_CNPJ_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CNPJ_INVALIDO.getMsg(), cnpj), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Não deve disparar exceção quando o Tipo Cliente for válido")
    void notShouldThrowExceptionWhenTipoCliente() {

        assertDoesNotThrow(TipoClienteFixture::buildTipoClienteValido);
    }

}