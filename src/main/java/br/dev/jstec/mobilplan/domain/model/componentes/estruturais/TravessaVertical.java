package br.dev.jstec.mobilplan.domain.model.componentes.estruturais;


import static br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda.calcularMetragem;

import br.dev.jstec.mobilplan.domain.model.componentes.AbstractComponente;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;

public class TravessaVertical extends AbstractComponente {
    private final double profundidadeEspecifica;

    public TravessaVertical(PadraoDeFitagem padraoDeFitagem) {
        //TODO: Implementar desconto de profundidade
        super(padraoDeFitagem);
        this.profundidadeEspecifica = 0;
    }

    @Override
    public void aceitar(EstrategiaDeConstrucao estrategia, Dimensoes dimensoes) {
        estrategia.aplicarParaTravessaVertical(this, dimensoes, this.padraoDeFitagem);
    }

    public void setDimensoes(double largura, double espessura, PadraoDeFitagem padraoDeFitagem) {
        this.largura = largura;
        this.espessura = espessura;
        this.altura = profundidadeEspecifica;
        this.area = largura * profundidadeEspecifica;
        this.metragemFita = calcularMetragem(largura, profundidadeEspecifica, padraoDeFitagem);
        this.descricao = "Travessa Vertical";
    }
}