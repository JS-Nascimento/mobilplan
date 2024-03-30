package br.dev.jstec.mobilplan.domain.model.componentes.estruturais;


import static br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda.calcularMetragem;

import br.dev.jstec.mobilplan.domain.model.componentes.AbstractComponente;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.TipoFundo;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;

public class Fundo extends AbstractComponente {
    private final TipoFundo tipoFundo;
    private final double valorVariavel;

    public Fundo(TipoFundo tipoFundo, double espessura, double valorVariavel, PadraoDeFitagem padraoDeFitagem) {
        super(padraoDeFitagem);
        this.tipoFundo = tipoFundo;
        this.espessura = espessura;
        this.valorVariavel = valorVariavel;
    }

    @Override
    public void aceitar(EstrategiaDeConstrucao estrategia, Dimensoes dimensoes) {
        estrategia.aplicarParaFundo(this, dimensoes, this.espessura, this.valorVariavel, this.padraoDeFitagem);
    }

    public void setDimensoes(double largura, double altura, double espessura, PadraoDeFitagem padraoDeFitagem) {
        this.largura = largura;
        this.altura = altura;
        this.espessura = espessura;
        this.area = largura * altura;
        this.metragemFita = calcularMetragem(largura, altura, padraoDeFitagem);
        this.descricao = "Fundo " + tipoFundo;
    }

    public TipoFundo getTipoFundo() {
        return tipoFundo;
    }

    public double getValorVariavel() {
        return valorVariavel;
    }

}