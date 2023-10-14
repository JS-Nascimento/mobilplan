package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.domain.valueobject.Cnpj.formatedOf;
import static br.dev.jstec.efurniture.application.domain.valueobject.Cnpj.onlyNumericOf;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class CnpjFixture {

    public static Cnpj buildVazio() {

        return Cnpj.createOf(EMPTY);
    }

    public static void buildInvalido(String cnpj) {

        Cnpj.createOf(cnpj);
    }

    public static void buildInvalidoQuantidadeDeDigitos(String cnpj) {

        Cnpj.createOf(cnpj);
    }

    public static String buildSoNumeros(Cnpj cnpj) {

        return onlyNumericOf(cnpj);
    }

    public static String buildFormatado(Cnpj cnpj) {

        return formatedOf(cnpj);
    }

}
