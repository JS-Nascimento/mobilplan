package br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta;


import static br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda.calcularMetragem;

import br.dev.jstec.mobilplan.domain.model.componentes.AbstractComponente;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoGaveta;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;

public class ContraFrenteGaveta extends AbstractComponente {
    private final ConfiguracaoFabricacao configuracaoFabricacao;

    public ContraFrenteGaveta(double altura, ConfiguracaoFabricacao configuracaoFabricacao) {
        super(configuracaoFabricacao.getFitagem().getContraFrenteGaveta());
        this.altura = altura;
        this.configuracaoFabricacao = configuracaoFabricacao;
    }

    @Override
    public void aceitar(EstrategiaDeConstrucao estrategia, Dimensoes dimensoes) {
        estrategia.aplicarParaContraFrenteGaveta(this, dimensoes, this.padraoDeFitagem);
    }

    public void setDimensoes(double altura, double largura, double espessura, PadraoDeFitagem padraoDeFitagem) {
        this.altura = altura;
        this.largura = largura;
        this.espessura = espessura;
        this.area = altura * largura;
        this.metragemFita = calcularMetragem(largura, altura, padraoDeFitagem);
        this.descricao = "Contra Frente Gaveta";
    }

    public PadraoGaveta configuracaoPadraoGaveta() {
        return configuracaoFabricacao.getGaveta();
    }
}