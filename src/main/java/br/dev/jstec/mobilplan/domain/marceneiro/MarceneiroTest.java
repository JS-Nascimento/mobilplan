package br.dev.jstec.mobilplan.domain.marceneiro;


import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ATRIBUTO_OBRIGATORIO;
import static br.dev.jstec.mobilplan.domain.valueobject.EnderecoFixture.build;
import static br.dev.jstec.mobilplan.domain.valueobject.TelefoneFixture.buildCelularOuWhatsapp;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class MarceneiroTest {

    @InjectMocks
    private Marceneiro marceneiroMock = MarceneiroFixture.build();

    @Test
    @DisplayName("Deve instaciar um marceneiro")
    void testCriarMarceneiro() {

        final var marceneiro = MarceneiroFixture.build();

        final var resultado = Marceneiro.updateOf(
                marceneiro.getId().toString(),
                marceneiro.getSituacao().getDescricao(),
                marceneiro.getNome().value(),
                marceneiro.getNomeComercial().value(),
                marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
                marceneiro.getTipoCliente().documento(),
                marceneiro.getEmail().value(),
                marceneiro.getTelefones(),
                marceneiro.getEnderecos());

        assertNotNull(resultado.getId());
        assertEquals(marceneiro.getNome().value(), resultado.getNome().value());
        assertEquals(marceneiro.getNomeComercial().value(), resultado.getNomeComercial().value());
        assertEquals(marceneiro.getTipoCliente(), resultado.getTipoCliente());
        assertEquals(marceneiro.getEmail().value(), resultado.getEmail().value());
        assertEquals(marceneiro.getTelefones(), resultado.getTelefones());
    }

    @Test
    @DisplayName("Deve lancar excecao quando não conter ao menos 1 telefone")
    void shouldThrowExceptionWhenTelefoneIsEmpty() {

        var exception = assertThrows(DomainException.class,
                MarceneiroFixture::buildTelefoneInvalido);

        assertEquals(format(ERRO_ATRIBUTO_OBRIGATORIO.getMsg(), "telefone"),
                exception.getErrorMessage().getMsg());
        assertEquals(ERRO_ATRIBUTO_OBRIGATORIO.getCode(),
                exception.getErrorMessage().getCode());
    }

    @Test
    @DisplayName("Deve lancar excecao quando não conter ao menos 1 endereço")
    void shouldThrowExceptionWhenEnderecoIsEmpty() {

        var exception = assertThrows(DomainException.class,
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

    @Test
    @DisplayName("Deve lançar exceção quando telefones é nulo")
    void shouldThrowExceptionWhenTelefonesIsNull() {

        var enderecos = List.of(build());

        assertThrows(DomainException.class,
                () -> marceneiroMock.validate(null, enderecos));
    }

    @Test
    @DisplayName("Deve lançar exceção quando telefones é vazio")
    void shouldThrowExceptionWhenTelefonesIsEmpty() {

        var telefones = List.<Telefone>of();
        var enderecos = List.of(build());

        assertThrows(DomainException.class,
                () -> marceneiroMock.validate(telefones, enderecos));
    }

    @Test
    @DisplayName("Deve lançar exceção quando enderecos é nulo")
    void shouldThrowExceptionWhenEnderecosIsNull() {

        var telefones = List.of(buildCelularOuWhatsapp());

        assertThrows(DomainException.class,
                () -> marceneiroMock.validate(telefones, null));
    }

    @Test
    @DisplayName("Deve lançar exceção quando enderecos é vazio")
    void shouldThrowExceptionWhenEnderecosIsEmpty() {

        var telefones = List.of(buildCelularOuWhatsapp());
        var enderecos = List.<Endereco>of();

        assertThrows(DomainException.class,
                () -> marceneiroMock.validate(telefones, enderecos));
    }

    @Test
    @DisplayName("Não deve lançar exceção quando todos os parâmetros são válidos")
    void shouldNotThrowExceptionWhenAllParametersAreValid() {

        var telefones = buildCelularOuWhatsapp();
        var enderecos = build();

        assertDoesNotThrow(
                () -> marceneiroMock.validate(List.of(telefones), List.of(enderecos)));
    }

    @Test
    @DisplayName("Deve retornar false quando o objeto comparado é null")
    void shouldReturnFalseWhenComparedObjectIsNull2() {

        var marceneiro = MarceneiroFixture.build();
        assertNotEquals(null, marceneiro);
    }

    @Test
    @DisplayName("Deve retornar false quando o objeto comparado é de uma classe diferente")
    void shouldReturnFalseWhenComparedObjectIsOfDifferentClass() {

        var marceneiro = MarceneiroFixture.build();
        var outroObjeto = new Object();
        assertNotEquals(marceneiro, outroObjeto);
    }

    @Test
    @DisplayName("Deve retornar false quando o objeto comparado tem um ID diferente")
    void shouldReturnFalseWhenComparedObjectHasDifferentId() {

        var marceneiro1 = MarceneiroFixture.build();
        var marceneiro2 = MarceneiroFixture.build();

        assertNotEquals(marceneiro1, marceneiro2);
    }

}