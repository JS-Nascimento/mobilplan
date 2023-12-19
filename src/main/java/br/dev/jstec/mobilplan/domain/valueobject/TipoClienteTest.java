package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CNPJ_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CPF_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_TIPO_PESSOA_INEXISTENTE;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_TIPO_PESSOA_NULO;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarCnpj;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarCpf;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarString;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
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

        var exception = assertThrows(DomainException.class,
                TipoClienteFixture::buildTipoClienteTipoPessoaNulo);

        assertEquals(ERRO_TIPO_PESSOA_NULO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(ERRO_TIPO_PESSOA_NULO.getMsg(), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o Tipo Pessoa não for válido")
    void shouldThrowExceptionWhenTipoPessoaNotValid() {

        var tipoPessoa = gerarString();

        var exception = assertThrows(DomainException.class,
                () -> TipoClienteFixture.buildTipoClienteTipoPessoaInvalida(tipoPessoa));

        assertEquals(ERRO_TIPO_PESSOA_INEXISTENTE.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_TIPO_PESSOA_INEXISTENTE.getMsg(), tipoPessoa),
                exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o CPF não for válido")
    void shouldThrowExceptionWhenCpfNotValid() {

        var cpf = gerarCpf(false);

        var exception = assertThrows(DomainException.class,
                () -> TipoClienteFixture.buildTipoClienteCpfInvalido(cpf));

        assertEquals(ERRO_CPF_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CPF_INVALIDO.getMsg(), cpf), exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o CNPJ não for válido")
    void shouldThrowExceptionWhenCnpjNotValid() {

        var cnpj = gerarCnpj(false);

        var exception = assertThrows(DomainException.class,
                () -> TipoClienteFixture.buildTipoClienteCnpjInvalido(cnpj));

        assertEquals(ERRO_CNPJ_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CNPJ_INVALIDO.getMsg(), cnpj),
                exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Não deve disparar exceção quando o Tipo Cliente for válido")
    void notShouldThrowExceptionWhenTipoCliente() {

        assertDoesNotThrow(TipoClienteFixture::buildTipoClienteValido);
    }

    @Test
    @DisplayName("Não deve disparar exceção quando o Tipo Cliente for válido para pessoa juridica")
    void notShouldThrowExceptionWhenTipoClientePessoaJuridica() {

        assertDoesNotThrow(TipoClienteFixture::buildTipoClienteValidoPessoaJuridica);
    }

}