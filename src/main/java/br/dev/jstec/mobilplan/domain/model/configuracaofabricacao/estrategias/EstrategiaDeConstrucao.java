package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias;


import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.Base;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.Fundo;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.Lateral;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.PrateleiraInterna;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.TravessaHorizontal;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.TravessaVertical;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta.ContraFrenteGaveta;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta.FundoGaveta;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta.LateralGaveta;
import br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta.TraseiroGaveta;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;

public interface EstrategiaDeConstrucao {
    void aplicarParaBase(Base base, Dimensoes dimensoes, PadraoDeFitagem padraoDeFitagem);

    void aplicarParaPrateleiraInterna(PrateleiraInterna prateleiraInterna, Dimensoes dimensoes);

//    void aplicarParaPortas(Portas portas, Dimensoes dimensoes);
//
//    void aplicarParaPuxador(ComPuxador fechamento, Dimensoes dimensoes, Gabinete gabinete);

    void aplicarParaTravessaHorizontal(TravessaHorizontal travessaHorizontal, Dimensoes dimensoes,
                                       PadraoDeFitagem padraoDeFitagem);

    void aplicarParaTravessaVertical(TravessaVertical travessaVertical, Dimensoes dimensoes,
                                     PadraoDeFitagem padraoDeFitagem);


    void aplicarParaFundo(Fundo fundo, Dimensoes dimensoes, double espessuraFundo,
                          double valorVariavel, PadraoDeFitagem padraoDeFitagem);

    void aplicarParaLateral(Lateral lateral, Dimensoes dimensoes, PadraoDeFitagem padraoDeFitagem);

    void aplicarParaFundoGaveta(FundoGaveta fundoGaveta, Dimensoes dimensoes, double espessura,
                                PadraoDeFitagem padraoDeFitagem);

    void aplicarParaLateralGaveta(LateralGaveta lateralGaveta, Dimensoes dimensoes, PadraoDeFitagem padraoDeFitagem);

    void aplicarParaContraFrenteGaveta(ContraFrenteGaveta contraFrenteGaveta, Dimensoes dimensoes,
                                       PadraoDeFitagem padraoDeFitagem);

    void aplicarParaTraseiroGaveta(TraseiroGaveta traseiroGaveta, Dimensoes dimensoes, PadraoDeFitagem padraoDeFitagem);

    void aplicarParaGaveta(Gaveteiro gaveteiro, Dimensoes dimensoes);
}

