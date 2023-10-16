package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnderecoTest {

    @Test
    @DisplayName("Deve disparar exceção quando o CEP for Inválido ")
    void shouldThrowExceptionWhenCepIsNotValid() {

        var exception = assertThrows(BusinessException.class,
            EnderecoFixture::buildCepInvalido);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "CEP"),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o Cep não for informado")
    void shouldThrowExceptionWhenCepIsNull() {

        var exception = assertThrows(BusinessException.class,
            EnderecoFixture::buildCepNulo);

        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getMsg(),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o Logradouro não for informado")
    void shouldThrowExceptionWhenlogradouroIsNull() {

        var exception = assertThrows(BusinessException.class,
            EnderecoFixture::buildLogradouroNulo);

        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getMsg(),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o Numero não for informado")
    void shouldThrowExceptionWhenNumeroIsNull() {

        var exception = assertThrows(BusinessException.class,
            EnderecoFixture::buildNumeroNulo);

        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getMsg(),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o Bairro não for informado")
    void shouldThrowExceptionWhenBairroIsNull() {

        var exception = assertThrows(BusinessException.class,
            EnderecoFixture::buildBairroNulo);

        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getMsg(),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o Cidade não for informado")
    void shouldThrowExceptionWhenCidadeIsNull() {

        var exception = assertThrows(BusinessException.class,
            EnderecoFixture::buildCidadeNula);

        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getMsg(),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando a Uf não for informado")
    void shouldThrowExceptionWhenUfIsNull() {

        var exception = assertThrows(BusinessException.class,
            EnderecoFixture::buildUfNula);

        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getCode(),
            exception.getErrorMessage().getCode());
        assertEquals(ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS.getMsg(),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Deve disparar exceção quando o UF for inválido")
    void shouldThrowExceptionWhenUfIsNotValid() {

        var exception = assertThrows(BusinessException.class,
            EnderecoFixture::buildComUfInvalido);

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "UF"),
            exception.getErrorMessage().getMsg());
    }

    @Test
    @DisplayName("Não deve disparar exceção quando criar um endereço válido")
    void shouldNotThrowExceptionWhenCreateValidEndereco() {

        assertDoesNotThrow(EnderecoFixture::build);
    }

    @Test
    @DisplayName("Não deve disparar exceção quando criar um endereço válido com complemento nulo")
    void shouldNotThrowExceptionWhenCreateValidEnderecoComComplementoNulo() {

        assertDoesNotThrow(EnderecoFixture::buildComplememtoVazio);
    }

    @Test
    @DisplayName("Deve criar endereço válido formatado")
    void shouldCreateValidFormattedEndereco() {

        var endereco = EnderecoFixture.build();
        var enderecoFormatado = EnderecoFixture.buildFormatado(endereco);

        assertEquals(enderecoFormatado, Endereco.formatedOf(endereco));
    }
}