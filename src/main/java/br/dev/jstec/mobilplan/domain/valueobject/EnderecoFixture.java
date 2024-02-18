package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarString;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarStringComLetraENumero;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarStringNumerica;
import static br.dev.jstec.mobilplan.domain.valueobject.Endereco.formatedOf;
import static br.dev.jstec.mobilplan.domain.valueobject.Endereco.of;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.EnderecoDto;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class EnderecoFixture {

    public static Endereco build() {

        return of(
                gerarStringNumerica(8),
                "Rua " + gerarString(),
                gerarStringNumerica(4),
                gerarStringComLetraENumero(3),
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static Endereco buildComplememtoVazio() {

        return of(
                gerarStringNumerica(8),
                "Rua " + gerarString(),
                gerarStringNumerica(4),
                EMPTY,
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static void buildCepNulo() {

        of(
                null,
                "Rua " + gerarString(),
                gerarStringNumerica(4),
                gerarStringComLetraENumero(3),
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static void buildCepInvalido() {

        of(
                gerarStringComLetraENumero(8),
                "Rua " + gerarString(),
                gerarStringNumerica(4),
                gerarStringComLetraENumero(3),
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static void buildLogradouroNulo() {

        of(
                gerarStringComLetraENumero(8),
                null,
                gerarStringNumerica(4),
                gerarStringComLetraENumero(3),
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static void buildNumeroNulo() {

        of(
                gerarStringComLetraENumero(8),
                gerarString(),
                null,
                gerarStringComLetraENumero(3),
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static void buildBairroNulo() {

        of(
                gerarStringComLetraENumero(8),
                gerarString(),
                gerarStringNumerica(3),
                gerarStringComLetraENumero(3),
                null,
                gerarString(),
                gerarString(2));
    }

    public static void buildCidadeNula() {

        of(
                gerarStringComLetraENumero(8),
                gerarString(),
                gerarStringNumerica(3),
                gerarStringComLetraENumero(3),
                gerarString(),
                null,
                gerarString(2));
    }

    public static void buildUfNula() {

        of(
                gerarStringComLetraENumero(8),
                gerarString(),
                gerarStringNumerica(3),
                gerarStringComLetraENumero(3),
                gerarString(),
                gerarString(2),
                null);
    }

    public static void buildComUfInvalido() {

        of(
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
