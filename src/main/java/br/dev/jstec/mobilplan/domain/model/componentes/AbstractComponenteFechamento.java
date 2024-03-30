package br.dev.jstec.mobilplan.domain.model.componentes;

import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import java.util.Optional;

public abstract class AbstractComponenteFechamento extends AbstractComponente implements Fechamento {

    protected String descricaoCurta;
    //protected Gabinete gabinete;

    protected Optional<Puxador> puxador;

    //protected EstrategiaDeConstrucao estrategia;

    protected AbstractComponenteFechamento(PadraoDeFitagem padraoDeFitagem) {
        super(padraoDeFitagem);
    }


    @Override
    public String getDescricaoCurta() {
        return descricaoCurta;
    }

}
