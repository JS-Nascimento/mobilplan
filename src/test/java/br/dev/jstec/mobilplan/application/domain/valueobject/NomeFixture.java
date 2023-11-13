package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarString;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class NomeFixture {

    public static void buildNomeNulo() {

        new Nome(null);
    }

    public static void buildNomeVazio() {

        new Nome(EMPTY);
    }

    public static Nome buildNome() {

        return new Nome(gerarString());
    }
}
