package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarString;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class NomeComercialFixture {

    public static void buildNomeVazio() {
        new NomeComercial(EMPTY);
    }

    public static void buildNomeNulo() {
        new NomeComercial(null);
    }

    public static NomeComercial build() {

        return new NomeComercial(gerarString());
    }
}
