package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.TipoTelefone.of;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarInteger;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarStringNumerica;
import static br.dev.jstec.mobilplan.domain.valueobject.Telefone.createWithDdiOf;
import static br.dev.jstec.mobilplan.domain.valueobject.Telefone.formatedOf;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import br.dev.jstec.mobilplan.domain.TipoTelefone;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.TelefoneDto;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class TelefoneFixture {

    public static Telefone buildCelularOuWhatsapp() {

        return createWithDdiOf(
                TipoTelefone.byOrdinal(gerarInteger(1, 2)),
                gerarStringNumerica(9),
                gerarStringNumerica(2),
                gerarStringNumerica(3));
    }

    public static Telefone buildFixo() {

        return createWithDdiOf(
                TipoTelefone.byOrdinal(0),
                gerarStringNumerica(8),
                gerarStringNumerica(2),
                gerarStringNumerica(3));
    }

    public static void buildTelefoneTipoTelefoneInvalido() {

        createWithDdiOf(
                null,
                EMPTY,
                EMPTY,
                EMPTY);
    }

    public static void buildDdiInvalido(String ddi) {

        createWithDdiOf(
                TipoTelefone.byOrdinal(gerarInteger(1, 2)),
                gerarStringNumerica(9),
                gerarStringNumerica(2),
                ddi);
    }

    public static void buildDddInvalido(String ddd) {

        createWithDdiOf(
                TipoTelefone.byOrdinal(gerarInteger(1, 2)),
                gerarStringNumerica(9),
                ddd,
                gerarStringNumerica(3));
    }

    public static void buildNumeroTelefoneIncompativelComNumero(String tipo, String numero) {

        createWithDdiOf(
                of(tipo),
                numero,
                gerarStringNumerica(2),
                gerarStringNumerica(3));
    }

    public static void buildDdiVazio() {

        createWithDdiOf(
                TipoTelefone.byOrdinal(gerarInteger(1, 2)),
                gerarStringNumerica(9),
                gerarStringNumerica(2),
                null);
    }

    public static void buildDddVazio() {

        createWithDdiOf(
                TipoTelefone.byOrdinal(gerarInteger(1, 2)),
                gerarStringNumerica(9),
                null,
                gerarStringNumerica(2));
    }

    public static void buildNumeroVazio() {

        createWithDdiOf(
                TipoTelefone.byOrdinal(gerarInteger(1, 2)),
                null,
                gerarStringNumerica(2),
                gerarStringNumerica(2));
    }

    public static String buildTelfoneFormatado(Telefone telefone) {

        return formatedOf(telefone);
    }

    public static Telefone buildComTelefoneDto(TelefoneDto telefone) {

        return new Telefone(
                telefone.id(),
                of(telefone.tipoTelefone()),
                telefone.numero(),
                telefone.ddd(),
                telefone.ddi());
    }
}
