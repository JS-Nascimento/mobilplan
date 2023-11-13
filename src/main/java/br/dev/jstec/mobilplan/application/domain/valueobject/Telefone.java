package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.domain.TipoTelefone.FIXO;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_TIPO_TELEFONE_INCOMPATIVEL;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.application.domain.TipoTelefone;
import br.dev.jstec.mobilplan.application.exceptions.BusinessException;

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

        if ((tipoTelefone == FIXO && (numero.length() < 7 || numero.length() > 8))
            || (tipoTelefone != FIXO && numero.length() != 9)) {

            throw new BusinessException(
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

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "DDD");
        }
        return ddd;
    }

    private static String validarDdi(String ddi) {

        if (isBlank(ddi)) {

            return EMPTY;
        }

        ddi = ddi.replaceAll("\\D", "");

        if (ddi.isEmpty() || ddi.length() > 3) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "DDI");
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
}
