package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_TIPO_TELEFONE_INCOMPATIVEL;
import static br.dev.jstec.mobilplan.domain.util.NumberHelper.removeNotNumbers;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.enums.TipoTelefone;
import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = PRIVATE)
public class TelefoneVO {

    TipoTelefone tipoTelefone;
    String numero;
    String ddd;
    String ddi;

    private TelefoneVO(TipoTelefone tipoTelefone, String numero, String ddd, String ddi) {
        this.tipoTelefone = tipoTelefone;
        this.numero = validarNumero(numero);
        this.ddd = validarDdd(ddd);
        this.ddi = validarDdi(ddi);
    }

    public static TelefoneVO with(String tipoTelefone,
                                  String numero,
                                  String ddd,
                                  String ddi) {
        return new TelefoneVO(
                TipoTelefone.of(tipoTelefone),
                numero,
                ddd,
                ddi);
    }

    private String validarNumero(String numero) {

        if (isBlank(numero)) {

            return EMPTY;
        }

        numero = removeNotNumbers(numero);

        if ((tipoTelefone == TipoTelefone.FIXO && (numero.length() < 7 || numero.length() > 8))
                || (tipoTelefone != TipoTelefone.FIXO && numero.length() < 7 || numero.length() > 9)) {

            throw new DomainException(
                    ERRO_TIPO_TELEFONE_INCOMPATIVEL, tipoTelefone.toString(), numero);
        }

        return numero;
    }

    private String validarDdi(String ddi) {

        if (isBlank(ddi)) {

            return EMPTY;
        }

        ddi = removeNotNumbers(ddi);

        if (ddi.isEmpty() || ddi.length() > 3) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "DDI");
        }

        return ddi;
    }

    private String validarDdd(String ddd) {

        if (isBlank(ddd)) {

            return EMPTY;
        }

        ddd = removeNotNumbers(ddd);

        if (ddd.length() < 2 || ddd.length() > 3) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "DDD");
        }
        return ddd;
    }
}
