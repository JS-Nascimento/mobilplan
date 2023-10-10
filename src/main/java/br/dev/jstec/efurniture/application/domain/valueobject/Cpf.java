package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CPF_INVALIDO;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import br.dev.jstec.efurniture.exceptions.BusinessException;

public record Cpf(String value) {

    public Cpf {

        if (isNull(trimToNull(value))) {

            value = EMPTY;
        }
    }

    private static boolean validar(String value) {

        value = value.replaceAll("\\D", "");

        if (value.length() != 11 || value.chars().distinct().count() == 1) {
            return true;
        }

        var numeros = value.chars().map(Character::getNumericValue).toArray();

        return validarDigito(numeros, 9, 10) || validarDigito(numeros, 10, 11);
    }

    private static boolean validarDigito(int[] numeros, int posicao, int total) {

        int soma = 0;
        for (int i = 0; i < posicao; i++) {
            soma += numeros[i] * (total - i);
        }
        int resto = (soma * 10) % 11;
        if (resto == 10) {
            resto = 0;
        }
        return resto != numeros[posicao];
    }

    public static String formatedOf(Cpf cpf) {

        if (isNull(trimToNull(cpf.value))) {

            return EMPTY;
        }

        if (validar(cpf.value)) {
            throw new BusinessException(ERRO_CPF_INVALIDO, cpf.value);
        }

        var bloco1 = cpf.value.substring(0, 3);
        var bloco2 = cpf.value.substring(3, 6);
        var bloco3 = cpf.value.substring(6, 9);
        var digitos = cpf.value.substring(9, 11);

        return format("%s.%s.%s-%s", bloco1, bloco2, bloco3, digitos);
    }

    public static String onlyNumericOf(Cpf cpf) {

        if (isNull(trimToNull(cpf.value))) {

            return EMPTY;
        }

        if (validar(cpf.value)) {
            throw new BusinessException(ERRO_CPF_INVALIDO, cpf.value);
        }

        return cpf.value.replaceAll("\\D", "");
    }

    public static String createOf(String value, boolean formated) {

        var cpf = new Cpf(value);

        if (cpf.value().isEmpty()) {

            return EMPTY;
        }

        return formated ? formatedOf(cpf) : onlyNumericOf(cpf);
    }
}
