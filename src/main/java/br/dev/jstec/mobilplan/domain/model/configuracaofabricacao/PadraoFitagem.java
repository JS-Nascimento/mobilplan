package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao;

import static br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoDeFitagem.NENHUM;
import static br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoDeFitagem.of;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = PRIVATE, force = true)
public class PadraoFitagem {

    private final PadraoDeFitagem base;
    private final PadraoDeFitagem lateral;
    private final PadraoDeFitagem travessaHorizontal;
    private final PadraoDeFitagem travessaVertical;
    private final PadraoDeFitagem fundo;
    private final PadraoDeFitagem prateleiraInterna;
    private final PadraoDeFitagem prateleiraExterna;
    private final PadraoDeFitagem tampo;
    private final PadraoDeFitagem porta;
    private final PadraoDeFitagem frenteGaveta;

    private PadraoFitagem(PadraoDeFitagem base,
                          PadraoDeFitagem lateral,
                          PadraoDeFitagem travessaHorizontal,
                          PadraoDeFitagem travessaVertical,
                          PadraoDeFitagem fundo,
                          PadraoDeFitagem prateleiraInterna,
                          PadraoDeFitagem prateleiraExterna,
                          PadraoDeFitagem tampo,
                          PadraoDeFitagem porta,
                          PadraoDeFitagem frenteGaveta) {

        this.base = isNull(base) ? NENHUM : base;
        this.lateral = isNull(lateral) ? NENHUM : lateral;
        this.travessaHorizontal = isNull(travessaHorizontal) ? NENHUM : travessaHorizontal;
        this.travessaVertical = isNull(travessaVertical) ? NENHUM : travessaVertical;
        this.fundo = isNull(fundo) ? NENHUM : fundo;
        this.prateleiraInterna = isNull(prateleiraInterna) ? NENHUM : prateleiraInterna;
        this.prateleiraExterna = isNull(prateleiraExterna) ? NENHUM : prateleiraExterna;
        this.tampo = isNull(tampo) ? NENHUM : tampo;
        this.porta = isNull(porta) ? NENHUM : porta;
        this.frenteGaveta = isNull(frenteGaveta) ? NENHUM : frenteGaveta;
    }

    public static PadraoFitagem with(String base,
                                     String lateral,
                                     String travessaHorizontal,
                                     String travessaVertical,
                                     String fundo,
                                     String prateleiraInterna,
                                     String prateleiraExterna,
                                     String tampo,
                                     String porta,
                                     String frenteGaveta) {

        return new PadraoFitagem(
                of(base),
                of(lateral),
                of(travessaHorizontal),
                of(travessaVertical),
                of(fundo),
                of(prateleiraInterna),
                of(prateleiraExterna),
                of(tampo),
                of(porta),
                of(frenteGaveta));
    }
}
