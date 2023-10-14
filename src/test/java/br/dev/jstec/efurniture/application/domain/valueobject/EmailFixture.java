package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarEmail;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarString;
import static lombok.AccessLevel.PRIVATE;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class EmailFixture {

    public static void buildEmailNulo() {
        new Email(null);
    }

    public static void buildEmailVazio() {
        new Email("");
    }

    public static Email buildEmailInvalido() {

        return new Email(gerarString());
    }

    public static Email buildEmailValido() {

        return new Email(gerarEmail(true));
    }
}
