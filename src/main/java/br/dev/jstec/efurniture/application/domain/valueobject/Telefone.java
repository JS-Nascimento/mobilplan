package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.domain.DomainConstants.DDI_BRASIL;
import static br.dev.jstec.efurniture.application.domain.TipoTelefone.FIXO;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_TIPO_TELEFONE_INCOMPATIVEL;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.efurniture.application.domain.TipoTelefone;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;

public record Telefone(
    TipoTelefone tipoTelefone,
    String numero,
    String ddd,
    String ddi) {

    public Telefone(TipoTelefone tipoTelefone, String numero, String ddd) {

        this(tipoTelefone,
            validarNumero(tipoTelefone, numero),
            validarDdd(ddd),
            DDI_BRASIL);
    }

    public Telefone {

        if (isNull(tipoTelefone)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "Tipo de telefone");
        }

        ddi = validarDdi(ddi);
        ddd = validarDdd(ddd);
        numero = validarNumero(tipoTelefone, numero);
    }

    private static String validarNumero(TipoTelefone tipoTelefone, String numero) {

        if (isBlank(numero)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "número do telefone");
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

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "DDD");
        }

        ddd = ddd.replaceAll("\\D", "");

        if (ddd.length() < 2 || ddd.length() > 3) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "DDD");
        }
        return ddd;
    }

    private static String validarDdi(String ddi) {

        if (isBlank(ddi)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "DDI");
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

    public static Telefone createOf(TipoTelefone tipoTelefone, String numero, String ddd) {

        return new Telefone(tipoTelefone, numero, ddd);
    }

    public static Telefone createWithDdiOf(TipoTelefone tipoTelefone, String numero, String ddd,
        String ddi) {

        return new Telefone(tipoTelefone, numero, ddd, ddi);
    }
}
