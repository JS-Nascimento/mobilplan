package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.domain.valueobject.Telefone.createWithDdiOf;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarInteger;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringNumerica;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import br.dev.jstec.efurniture.application.domain.TipoTelefone;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class TelefoneFixture {

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
                TipoTelefone.of(tipo),
                numero,
                gerarStringNumerica(2),
                gerarStringNumerica(3));
    }
}
