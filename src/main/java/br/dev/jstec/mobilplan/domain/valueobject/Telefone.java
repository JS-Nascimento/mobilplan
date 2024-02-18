package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_TIPO_TELEFONE_INCOMPATIVEL;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.enums.TipoTelefone;
import br.dev.jstec.mobilplan.domain.exceptions.DomainException;

public record Telefone(
        Long id,
        TipoTelefone tipoTelefone,
        String numero,
        String ddd,
        String ddi) {

    private Telefone(TipoTelefone tipoTelefone, String numero, String ddd, String ddi) {

        this(null,
                tipoTelefone,
                numero,
                ddd,
                ddi);
    }

    public Telefone {

        ddi = validarDdi(ddi);
        ddd = validarDdd(ddd);
        numero = validarNumero(tipoTelefone, numero);
    }

    private static String validarNumero(TipoTelefone tipoTelefone, String numero) {

        if (isBlank(numero)) {

            return EMPTY;
        }

        numero = numero.replaceAll("\\D", "");

        if ((tipoTelefone == TipoTelefone.FIXO && (numero.length() < 7 || numero.length() > 8))
                || (tipoTelefone != TipoTelefone.FIXO && numero.length() != 9)) {

            throw new DomainException(
                    ERRO_TIPO_TELEFONE_INCOMPATIVEL, tipoTelefone.toString(), numero);
        }

        return numero;
    }

    private static String validarDdd(String ddd) {

        if (isBlank(ddd)) {

            return EMPTY;
        }

        ddd = ddd.replaceAll("\\D", "");

        if (ddd.length() < 2 || ddd.length() > 3) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "DDD");
        }
        return ddd;
    }

    private static String validarDdi(String ddi) {

        if (isBlank(ddi)) {

            return EMPTY;
        }

        ddi = ddi.replaceAll("\\D", "");

        if (ddi.isEmpty() || ddi.length() > 3) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "DDI");
        }

        return "+".concat(ddi);
    }

    public static String formatedOf(Telefone telefone) {

        return String.format("(%s) %s %s-%s",
                telefone.ddi(),
                telefone.ddd(),
                telefone.numero().substring(0, 4),
                telefone.numero().substring(4));
    }

    public static Telefone createWithDdiOf(TipoTelefone tipoTelefone, String numero, String ddd,
                                           String ddi) {

        return new Telefone(tipoTelefone, numero, ddd, ddi);
    }

    public static Telefone of(String tipoTelefone, String numero, String ddd, String ddi) {

        return new Telefone(
                TipoTelefone.of(tipoTelefone),
                numero,
                ddd,
                ddi);
    }
}
