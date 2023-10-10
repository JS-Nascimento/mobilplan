package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarString;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringComLetraENumero;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringNumerica;
import static lombok.AccessLevel.PRIVATE;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class EnderecoFixture {

    public static Endereco build() {

        return Endereco.createOf(
                gerarStringNumerica(8),
                "Rua " + gerarString(),
                gerarStringNumerica(4),
                null,
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static void buildCepNulo() {

        Endereco.createOf(
                null,
                "Rua " + gerarString(),
                gerarStringNumerica(4),
                null,
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static void buildCepInvalido() {

        Endereco.createOf(
                gerarStringComLetraENumero(8),
                "Rua " + gerarString(),
                gerarStringNumerica(4),
                null,
                gerarString(),
                gerarString(),
                gerarString(2));
    }

    public static Endereco buildComUfInvalido() {

        return Endereco.createOf(
                gerarStringNumerica(8),
                "Rua " + gerarString(),
                gerarStringNumerica(4),
                null,
                gerarString(),
                gerarString(),
                gerarStringNumerica(2));
    }
}
