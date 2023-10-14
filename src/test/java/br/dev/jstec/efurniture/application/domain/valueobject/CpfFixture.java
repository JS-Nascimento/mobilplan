package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.domain.valueobject.Cpf.formatedOf;
import static br.dev.jstec.efurniture.application.domain.valueobject.Cpf.onlyNumericOf;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class CpfFixture {

    public static Cpf buildVazio() {

        return Cpf.createOf(EMPTY);
    }

    public static void buildInvalido(String cpf) {

        Cpf.createOf(cpf);
    }

    public static void buildInvalidoQuantidadeDeDigitos(String cpf) {

        Cpf.createOf(cpf);
    }

    public static String buildSoNumeros(Cpf cpf) {

        return onlyNumericOf(cpf);
    }

    public static String buildFormatado(Cpf cpf) {

        return formatedOf(cpf);
    }

}
