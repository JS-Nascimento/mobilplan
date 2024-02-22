package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CNPJ_INVALIDO;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarCnpj;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarStringNumerica;
import static br.dev.jstec.mobilplan.domain.valueobject.Cnpj.createOf;
import static br.dev.jstec.mobilplan.domain.valueobject.Cnpj.formatedOf;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CnpjTest {

    @Test
    @DisplayName("Deve retornar CNPJ vazio para CNPJ nulo")
    void doReturnEmptyForNullCnpj() {

        var cnpj = CnpjFixture.buildVazio();

        assertEquals(EMPTY, cnpj.value());
    }

    @Test
    @DisplayName("Deve retornar CNPJ vazio para CNPJ nulo pelo Construtor")
    void doReturnEmptyForNullCnpjByConstructor() {

        var cnpj = new Cnpj(null);

        assertEquals(EMPTY, cnpj.value());
    }

    @Test
    @DisplayName("Deve lançar exceção para CNPJ inválido")
    void shouldThrowExceptionForInvalidCnpj() {

        var cnpj = gerarCnpj(false);

        var exception = assertThrows(DomainException.class,
                () -> CnpjFixture.buildInvalido(cnpj));

        assertEquals(ERRO_CNPJ_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CNPJ_INVALIDO.getMsg(), cnpj),
                exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção para CNPJ inválido pela quantidade de dígitos")
    void shouldThrowExceptionForInvalidCnpjByQuantity() {

        var cnpj = gerarStringNumerica(13);

        var exception = assertThrows(DomainException.class,
                () -> CnpjFixture.buildInvalido(cnpj));

        assertEquals(ERRO_CNPJ_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CNPJ_INVALIDO.getMsg(), cnpj),
                exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção para CNPJ inválido pelo Construtor")
    void shouldThrowExceptionForInvalidCnpjByConstructor() {

        var cnpj = gerarCnpj(false);

        var exception = assertThrows(DomainException.class,
                () -> new Cnpj(cnpj));

        assertEquals(ERRO_CNPJ_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CNPJ_INVALIDO.getMsg(), cnpj),
                exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve retornar CNPJ formatado para CNPJ válido")
    void shouldReturnFormattedCnpjForValidCnpj() {

        var cnpj = gerarCnpj(true);
        var cnpjFormatado = CnpjFixture.buildFormatado(createOf(cnpj));

        assertEquals(formatedOf(createOf(cnpj)), cnpjFormatado);
    }

    @Test
    @DisplayName("Deve retornar CNPJ numérico para CNPJ válido")
    void shouldReturnNumericCnpjForValidCnpj() {

        var cnpj = gerarCnpj(true);
        var cnpjSomenteNumeros = CnpjFixture.buildSoNumeros(createOf(cnpj));

        assertEquals(cnpj, cnpjSomenteNumeros);
    }
}
