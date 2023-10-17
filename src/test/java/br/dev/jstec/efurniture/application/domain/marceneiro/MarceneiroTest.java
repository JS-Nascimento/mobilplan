package br.dev.jstec.efurniture.application.domain.marceneiro;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ATRIBUTO_OBRIGATORIO;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MarceneiroTest {

    @Test
    @DisplayName("Deve instaciar um marceneiro")
    void testCriarMarceneiro() {

        final var marceneiro = MarceneiroFixture.build();

        final var resultado = Marceneiro.createOf(
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente(),
            marceneiro.getEmail().value(),
            marceneiro.getTelefones(),
            marceneiro.getEnderecos());

        assertNotNull(resultado.getMarceneiroId());
        assertEquals(marceneiro.getNome().value(), resultado.getNome().value());
        assertEquals(marceneiro.getNomeComercial().value(), resultado.getNomeComercial().value());
        assertEquals(marceneiro.getTipoCliente(), resultado.getTipoCliente());
        assertEquals(marceneiro.getEmail().value(), resultado.getEmail().value());
        assertEquals(marceneiro.getTelefones(), resultado.getTelefones());
    }

    @Test
    @DisplayName("Deve lancar excecao quando o Id for nulo")
    void shouldThrowExceptionWhenMarceneiroIdIsNull() {

        var exception = assertThrows(BusinessException.class,
            MarceneiroFixture::buildConstrutorIdNulo);

        assertEquals(format(ERRO_ID_INVALIDO.getMsg(), "marceneiro"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_ID_INVALIDO.getCode(),
            exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lancar excecao quando não conter ao menos 1 telefone")
    void shouldThrowExceptionWhenTelefoneIsEmpty() {

        var exception = assertThrows(BusinessException.class,
            MarceneiroFixture::buildTelefoneInvalido);

        assertEquals(format(ERRO_ATRIBUTO_OBRIGATORIO.getMsg(), "telefone"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_ATRIBUTO_OBRIGATORIO.getCode(),
            exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lancar excecao quando não conter ao menos 1 endereço")
    void shouldThrowExceptionWhenEnderecoIsEmpty() {

        var exception = assertThrows(BusinessException.class,
            MarceneiroFixture::buildEnderecoInvalido);

        assertEquals(format(ERRO_ATRIBUTO_OBRIGATORIO.getMsg(), "endereço"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_ATRIBUTO_OBRIGATORIO.getCode(),
            exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando os objetos Marceneiro são iguais")
    void shouldReturnTrueWhenMarceneirosAreEqual() {

        var marceneiro1 = MarceneiroFixture.build();
        var marceneiro2 = marceneiro1;

        assertEquals(marceneiro1, marceneiro2);
    }

    @Test
    @DisplayName("Deve retornar falso quando os objetos Marceneiro não são iguais")
    void shouldReturnFalseWhenMarceneirosAreNotEqual() {

        var marceneiro1 = MarceneiroFixture.build();
        var marceneiro2 = MarceneiroFixture.build();

        assertNotEquals(marceneiro1, marceneiro2);
    }

    @Test
    @DisplayName("Deve retornar o mesmo hashCode para objetos Marceneiro iguais")
    void shouldReturnSameHashCodeForEqualMarceneiros() {

        var marceneiro1 = MarceneiroFixture.build();
        var marceneiro2 = marceneiro1;

        assertEquals(marceneiro1.hashCode(), marceneiro2.hashCode());
    }

    @Test
    @DisplayName("Deve retornar falso se o objeto comparado for nulo")
    void shouldReturnFalseWhenComparedObjectIsNull() {

        var marceneiro = MarceneiroFixture.build();

        assertNotEquals(null, marceneiro);
    }
}