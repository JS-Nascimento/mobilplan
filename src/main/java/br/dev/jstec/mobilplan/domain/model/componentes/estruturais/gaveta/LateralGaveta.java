package br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta;


import static br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda.calcularMetragem;

import br.dev.jstec.mobilplan.domain.model.componentes.AbstractComponente;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoGaveta;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;

public class LateralGaveta extends AbstractComponente {

    private final ConfiguracaoFabricacao configuracaoFabricacao;

    public LateralGaveta(ConfiguracaoFabricacao configuracaoFabricacao, Dimensoes dimensoes
    ) {
        super(configuracaoFabricacao.getFitagem().getLateralGaveta());
        this.altura = dimensoes.getAltura();
        //TODO: Refatorar para usar o padrão configurado
        if (configuracaoFabricacao.getGaveta().isAcompanhaTrilho()) {
            this.profundidade = dimensoes.getProfundidade();
        } else {
            this.profundidade = dimensoes.getProfundidade();
        }
        this.espessura = configuracaoFabricacao.getGaveta().getEspessuraCorpo();
        this.configuracaoFabricacao = configuracaoFabricacao;
    }

    @Override
    public void aceitar(EstrategiaDeConstrucao estrategia, Dimensoes dimensoes) {
        estrategia.aplicarParaLateralGaveta(this, dimensoes, this.padraoDeFitagem);
    }

    public void setDimensoes(double altura, double largura, double espessura, PadraoDeFitagem padraoDeFitagem) {
        this.altura = altura;
        this.largura = largura;
        this.area = altura * largura;
        this.metragemFita = calcularMetragem(largura, altura, padraoDeFitagem);
        this.descricao = "Lateral Gaveta";
    }

    public PadraoGaveta configuracaoPadraoGaveta() {
        return configuracaoFabricacao.getGaveta();
    }
}