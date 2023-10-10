package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CNPJ_INVALIDO;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import br.dev.jstec.efurniture.exceptions.BusinessException;

public record Cnpj(String value) {

    public Cnpj {

        if (isNull(trimToNull(value))) {

            value = EMPTY;
        }
    }

    private static boolean validar(String value) {

        value = value.replaceAll("\\D", "");

        if (value.length() != 14 || value.chars().distinct().count() == 1) {
            return true;
        }

        var numeros = value.chars().map(Character::getNumericValue).toArray();

        return validarDigito(numeros, 12, new int[] {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2})
                || validarDigito(numeros, 13, new int[] {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
    }

    private static boolean validarDigito(int[] numeros, int posicao, int[] pesos) {

        var soma = 0;

        for (var i = 0; i < pesos.length; i++) {
            soma += numeros[i] * pesos[i];
        }

        var resto = soma % 11;

        if (resto < 2) {

            resto = 0;
        } else {

            resto = 11 - resto;
        }

        return resto != numeros[posicao];
    }

    public static String formatedOf(Cnpj cnpj) {

        if (isNull(trimToNull(cnpj.value))) {

            return EMPTY;
        }

        if (validar(cnpj.value)) {

            throw new BusinessException(ERRO_CNPJ_INVALIDO, cnpj.value);
        }

        var bloco1 = cnpj.value.substring(0, 2);
        var bloco2 = cnpj.value.substring(2, 5);
        var bloco3 = cnpj.value.substring(5, 8);
        var bloco4 = cnpj.value.substring(8, 12);
        var digitos = cnpj.value.substring(12, 14);

        return format("%s.%s.%s/%s-%s", bloco1, bloco2, bloco3, bloco4, digitos);
    }

    public static String onlyNumericOf(Cnpj cnpj) {

        if (isNull(trimToNull(cnpj.value))) {

            return EMPTY;
        }

        if (validar(cnpj.value)) {
            throw new BusinessException(ERRO_CNPJ_INVALIDO, cnpj.value);
        }

        return cnpj.value.replaceAll("\\D", "");
    }

    public static String createOf(String value, boolean formated) {

        var cnpj = new Cnpj(value);

        if (cnpj.value().isEmpty()) {

            return EMPTY;
        }

        return formated ? formatedOf(cnpj) : onlyNumericOf(cnpj);
    }
}
