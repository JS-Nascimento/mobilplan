package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.domain.valueobject.Endereco.createOf;
import static br.dev.jstec.mobilplan.application.domain.valueobject.Endereco.formatedOf;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarString;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarStringComLetraENumero;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarStringNumerica;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.EnderecoDto;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class EnderecoFixture {

    public static Endereco build() {

        return createOf(
            gerarStringNumerica(8),
            "Rua " + gerarString(),
            gerarStringNumerica(4),
            gerarStringComLetraENumero(3),
            gerarString(),
            gerarString(),
            gerarString(2));
    }

    public static Endereco buildComplememtoVazio() {

        return createOf(
            gerarStringNumerica(8),
            "Rua " + gerarString(),
            gerarStringNumerica(4),
            EMPTY,
            gerarString(),
            gerarString(),
            gerarString(2));
    }

    public static void buildCepNulo() {

        createOf(
            null,
            "Rua " + gerarString(),
            gerarStringNumerica(4),
            gerarStringComLetraENumero(3),
            gerarString(),
            gerarString(),
            gerarString(2));
    }

    public static void buildCepInvalido() {

        createOf(
            gerarStringComLetraENumero(8),
            "Rua " + gerarString(),
            gerarStringNumerica(4),
            gerarStringComLetraENumero(3),
            gerarString(),
            gerarString(),
            gerarString(2));
    }

    public static void buildLogradouroNulo() {

        createOf(
            gerarStringComLetraENumero(8),
            null,
            gerarStringNumerica(4),
            gerarStringComLetraENumero(3),
            gerarString(),
            gerarString(),
            gerarString(2));
    }

    public static void buildNumeroNulo() {

        createOf(
            gerarStringComLetraENumero(8),
            gerarString(),
            null,
            gerarStringComLetraENumero(3),
            gerarString(),
            gerarString(),
            gerarString(2));
    }

    public static void buildBairroNulo() {

        createOf(
            gerarStringComLetraENumero(8),
            gerarString(),
            gerarStringNumerica(3),
            gerarStringComLetraENumero(3),
            null,
            gerarString(),
            gerarString(2));
    }

    public static void buildCidadeNula() {

        createOf(
            gerarStringComLetraENumero(8),
            gerarString(),
            gerarStringNumerica(3),
            gerarStringComLetraENumero(3),
            gerarString(),
            null,
            gerarString(2));
    }

    public static void buildUfNula() {

        createOf(
            gerarStringComLetraENumero(8),
            gerarString(),
            gerarStringNumerica(3),
            gerarStringComLetraENumero(3),
            gerarString(),
            gerarString(2),
            null);
    }

    public static void buildComUfInvalido() {

        createOf(
            gerarStringNumerica(8),
            "Rua " + gerarString(),
            gerarStringNumerica(4),
            gerarStringComLetraENumero(3),
            gerarString(),
            gerarString(),
            gerarStringNumerica(2));
    }

    public static String buildFormatado(Endereco endereco) {

        return formatedOf(endereco);
    }

    public static Endereco buildComEnderecoDto(EnderecoDto endereco) {
        return new Endereco(
            endereco.cep(),
            endereco.logradouro(),
            endereco.numero(),
            endereco.complemento(),
            endereco.bairro(),
            endereco.cidade(),
            endereco.uf());
    }
}