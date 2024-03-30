package br.dev.jstec.mobilplan.domain.model.componentes;


import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;
import br.dev.jstec.mobilplan.domain.model.materiaprima.MateriaPrima;
import java.util.List;

public interface Componente {

    void aceitar(EstrategiaDeConstrucao estrategia, Dimensoes dimensoes);

    void adicionarAcabamentos(List<MateriaPrima> novosAcabamentos);

    String getDescricao();

    double getArea();

    double getMetragemLinear();

    List<MateriaPrima> getMateriasPrima();

    PadraoDeFitagem getPadraoDeFitagem();

    double altura();

    double largura();

    double profundidade();

    double espessura();

}