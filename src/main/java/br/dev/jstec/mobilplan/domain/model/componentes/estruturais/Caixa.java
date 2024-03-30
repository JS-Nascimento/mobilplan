package br.dev.jstec.mobilplan.domain.model.componentes.estruturais;


import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_INFORMACAO_INVALIDA;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.model.componentes.Estrutural;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.Dimensoes;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.TipoEstrategiaDeConstrucao;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.BaseEntreLaterais;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.BaseSobreLaterais;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.estrategias.EstrategiaDeConstrucao;
import br.dev.jstec.mobilplan.domain.model.materiaprima.MateriaPrima;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Caixa {
    private final List<Estrutural> componentes;
    private final List<MateriaPrima> materiasPrima;
    private final Map<Class<? extends Estrutural>, List<MateriaPrima>> acabamentosPorTipo;

    public Caixa() {
        this.acabamentosPorTipo = new HashMap<>();
        this.materiasPrima = new ArrayList<>();
        this.componentes = new ArrayList<>();
    }

    public void adicionarComponente(Estrutural componente) {
        componentes.add(componente);
    }

    public void adicionarAcabamento(MateriaPrima materiaPrima) {
        this.materiasPrima.add(materiaPrima);
    }

    public void definirAcabamentoPorTipo(Class<? extends Estrutural> tipoComponente, List<MateriaPrima> materiasPrima) {
        acabamentosPorTipo.put(tipoComponente, materiasPrima);
    }

    public void aplicarAcabamentos() {
        for (Estrutural componente : componentes) {
            List<MateriaPrima> acabamentosEspecificos = acabamentosPorTipo.get(componente.getClass());
            if (acabamentosEspecificos != null) {
                componente.adicionarAcabamentos(acabamentosEspecificos);
            }
        }
    }

    public void aplicarEstrategia(TipoEstrategiaDeConstrucao estrategia, Dimensoes dimensoes,
                                  PadraoDeFitagem padraoDeFitagem) {

        if (estrategia == null) {
            throw new DomainException(ERRO_INFORMACAO_INVALIDA, "Estratégia de construção não pode ser nula");
        }

        EstrategiaDeConstrucao estrategiaDeConstrucao = null;

        if (estrategia == TipoEstrategiaDeConstrucao.BASE_ENTRE_LATERAIS) {
            estrategiaDeConstrucao = new BaseEntreLaterais();
        }

        if (estrategia == TipoEstrategiaDeConstrucao.BASE_SOBRE_LATERAL) {
            estrategiaDeConstrucao = new BaseSobreLaterais();
        }

        for (Estrutural componente : componentes) {
            componente.aceitar(estrategiaDeConstrucao, dimensoes);
        }
    }

    public List<Estrutural> componentes() {
        return componentes;
    }
}