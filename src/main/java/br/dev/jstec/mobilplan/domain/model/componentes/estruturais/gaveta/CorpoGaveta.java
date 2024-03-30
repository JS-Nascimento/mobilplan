package br.dev.jstec.mobilplan.domain.model.componentes.estruturais.gaveta;


import br.dev.jstec.mobilplan.domain.model.componentes.AbstractComponente;
import br.dev.jstec.mobilplan.domain.model.componentes.Estrutural;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;
import java.util.ArrayList;
import java.util.List;

public class CorpoGaveta extends AbstractComponente {
    private final List<Estrutural> componentesEstruturais;
    private final ConfiguracaoFabricacao configuracaoFabricacao;

    public CorpoGaveta(Dimensoes dimensoesGabinete, double altura,
                       ConfiguracaoFabricacao configuracaoFabricacao) {

        super(configuracaoFabricacao.getFitagem().getContraFrenteGaveta());
        this.configuracaoFabricacao = configuracaoFabricacao;

        this.altura = altura;
        this.espessura = configuracaoFabricacao.getGaveta().getEspessuraCorpo();
        this.componentesEstruturais = new ArrayList<>();
        //TODO: Refatorar Largura para ser calculada
        this.largura = 350.0;

        this.componentesEstruturais.add(new FundoGaveta(configuracaoFabricacao));
        this.componentesEstruturais.add(new LateralGaveta(configuracaoFabricacao,
                new Dimensoes(largura, altura, 0.0, this.espessura)));
        this.componentesEstruturais.add(new LateralGaveta(configuracaoFabricacao,
                new Dimensoes(largura, altura, 0.0, this.espessura)));
        this.componentesEstruturais.add(new ContraFrenteGaveta(altura, configuracaoFabricacao));

        this.componentesEstruturais.add(new TraseiroGaveta(altura, configuracaoFabricacao));

        this.descricao = "Gaveta com folga de " + configuracaoFabricacao.getGaveta() + "mm";
    }

    @Override
    public void aceitar(EstrategiaDeConstrucao estrategia, Dimensoes dimensoes) {
        for (Estrutural componente : componentesEstruturais) {
            componente.aceitar(estrategia, dimensoes);
        }
    }

    public void setDimensoes(double altura, double largura, double profundidade, double espessura,
                             PadraoDeFitagem padraoDeFitagem) {
        this.largura = largura;
        this.profundidade = profundidade;
        this.espessura = espessura;
        this.altura = altura;
        //TODO: Refatorar para usar o padraoDeFitagem e metragemFita
    }

    public String listarComponentes() {

        StringBuilder descricaoComponentes = new StringBuilder();
        for (Estrutural componente : componentesEstruturais) {
            descricaoComponentes.append(" --> ")
                    .append(componente.getDescricao())
                    .append("\n");
        }
        return descricaoComponentes.toString();
    }

    public List<Estrutural> componentes() {
        return componentesEstruturais;
    }
}

