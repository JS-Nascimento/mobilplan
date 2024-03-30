package br.dev.jstec.mobilplan.domain.model.componentes.estruturais;

import static br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda.calcularMetragem;

import br.dev.jstec.mobilplan.domain.model.componentes.AbstractComponente;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.TipoPrateleira;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;

public class PrateleiraInterna extends AbstractComponente {

    private final TipoPrateleira tipoPrateleira;
    private double descontoProfundidade;

    public PrateleiraInterna(TipoPrateleira tipoPrateleira, double espessura, PadraoDeFitagem padraoDeFitagem) {
        super(padraoDeFitagem);
        this.tipoPrateleira = tipoPrateleira;
        this.descontoProfundidade = verificarDescontoProfundidade(tipoPrateleira);
        this.espessura = espessura;
    }

    private double verificarDescontoProfundidade(TipoPrateleira tipoPrateleira) {
        //TODO: Implementar desconto de profundidade
        return switch (tipoPrateleira) {
            case MOVEL -> 0;
            case FIXA -> 0;
            case DIVISORIA_HORIZONTAL -> 0;
        };
    }

    @Override
    public void aceitar(EstrategiaDeConstrucao estrategia, Dimensoes dimensoes) {

        estrategia.aplicarParaPrateleiraInterna(this,
                dimensoes);
    }


    public void setDimensoes(double largura, double altura, double espessura, PadraoDeFitagem padraoDeFitagem) {
        this.largura = largura;
        this.altura = altura;
        this.espessura = espessura;
        this.area = largura * altura;
        this.metragemFita = calcularMetragem(largura, altura, padraoDeFitagem);
        this.descricao = "Prateleira "
                + this.tipoPrateleira.toString();
    }

    public double descontoProfundidade() {
        return descontoProfundidade;
    }
}