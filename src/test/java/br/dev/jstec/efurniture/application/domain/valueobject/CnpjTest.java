package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.domain.valueobject.Cnpj.createOf;
import static br.dev.jstec.efurniture.application.domain.valueobject.Cnpj.formatedOf;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_CNPJ_INVALIDO;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarCnpj;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringNumerica;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;
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

        var exception = assertThrows(BusinessException.class,
            () -> CnpjFixture.buildInvalido(cnpj));

        assertEquals(ERRO_CNPJ_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CNPJ_INVALIDO.getMsg(), cnpj),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção para CNPJ inválido pela quantidade de dígitos")
    void shouldThrowExceptionForInvalidCnpjByQuantity() {

        var cnpj = gerarStringNumerica(13);

        var exception = assertThrows(BusinessException.class,
            () -> CnpjFixture.buildInvalido(cnpj));

        assertEquals(ERRO_CNPJ_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CNPJ_INVALIDO.getMsg(), cnpj),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção para CNPJ inválido pelo Construtor")
    void shouldThrowExceptionForInvalidCnpjByConstructor() {

        var cnpj = gerarCnpj(false);

        var exception = assertThrows(BusinessException.class,
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
