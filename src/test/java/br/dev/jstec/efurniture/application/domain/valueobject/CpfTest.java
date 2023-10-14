package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.domain.valueobject.Cpf.createOf;
import static br.dev.jstec.efurniture.application.domain.valueobject.Cpf.formatedOf;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarCpf;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringNumerica;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CPF_INVALIDO;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CpfTest {

    @Test
    @DisplayName("Deve retornar Cpf vazio para Cpf nulo")
    void doReturnEmptyForNullCpf() {

        var cpf = CpfFixture.buildVazio();

        assertEquals(EMPTY, cpf.value());
    }

    @Test
    @DisplayName("Deve retornar Cpf vazio para Cpf nulo pelo Construtor")
    void doReturnEmptyForNullCpfByConstructor() {

        var cpf = new Cpf(null);

        assertEquals(EMPTY, cpf.value());
    }

    @Test
    @DisplayName("Deve lançar exceção para Cpf inválido")
    void shouldThrowExceptionForInvalidCpf() {

        var cpf = gerarCpf(false);

        var exception = assertThrows(BusinessException.class,
            () -> CpfFixture.buildInvalido(cpf));

        assertEquals(ERRO_CPF_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CPF_INVALIDO.getMsg(), cpf),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção para Cpf inválido pela quantidade de dígitos")
    void shouldThrowExceptionForInvalidCpfByQuantity() {

        var cpf = gerarStringNumerica(13);

        var exception = assertThrows(BusinessException.class,
            () -> CpfFixture.buildInvalido(cpf));

        assertEquals(ERRO_CPF_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CPF_INVALIDO.getMsg(), cpf),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve lançar exceção para Cpf inválido pelo Construtor")
    void shouldThrowExceptionForInvalidCpfByConstructor() {

        var cpf = gerarCpf(false);

        var exception = assertThrows(BusinessException.class,
            () -> new Cpf(cpf));

        assertEquals(ERRO_CPF_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CPF_INVALIDO.getMsg(), cpf),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve retornar Cpf formatado para Cpf válido")
    void shouldReturnFormattedCpfForValidCpf() {

        var cpf = gerarCpf(true);
        var cpfFormatado = CpfFixture.buildFormatado(createOf(cpf));

        assertEquals(formatedOf(createOf(cpf)), cpfFormatado);
    }

    @Test
    @DisplayName("Deve retornar Cpf numérico para Cpf válido")
    void shouldReturnNumericCpfForValidCpf() {

        var cpf = gerarCpf(true);
        var cpfSomenteNumeros = CpfFixture.buildSoNumeros(createOf(cpf));

        assertEquals(cpf, cpfSomenteNumeros);
    }

}