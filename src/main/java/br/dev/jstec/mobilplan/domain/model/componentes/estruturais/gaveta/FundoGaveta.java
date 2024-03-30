package br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta;


import static br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda.calcularMetragem;

import br.dev.jstec.mobilplan.domain.model.componentes.AbstractComponente;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoGaveta;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;

public class FundoGaveta extends AbstractComponente {
    private final ConfiguracaoFabricacao configuracaoFabricacao;

    public FundoGaveta(ConfiguracaoFabricacao configuracaoFabricacao) {
        super(configuracaoFabricacao.getFitagem().getFundoGaveta());
        this.configuracaoFabricacao = configuracaoFabricacao;
        this.espessura = configuracaoFabricacao.getGaveta().getEspessuraFundo();
    }

    @Override
    public void aceitar(EstrategiaDeConstrucao estrategia, Dimensoes dimensoes) {
        estrategia.aplicarParaFundoGaveta(this, dimensoes, this.espessura, this.padraoDeFitagem);
    }

    public void setDimensoes(double profundidade, double largura, double espessura, PadraoDeFitagem padraoDeFitagem) {
        this.altura = profundidade;
        this.largura = largura;
        this.area = profundidade * largura;
        this.metragemFita = calcularMetragem(largura, profundidade, padraoDeFitagem);
        this.descricao = "Fundo Gaveta";
    }

    public PadraoGaveta configuracaoPadraoGaveta() {
        return configuracaoFabricacao.getGaveta();
    }
}