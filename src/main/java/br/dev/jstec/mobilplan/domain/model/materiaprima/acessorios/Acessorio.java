package br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios;

import br.dev.jstec.mobilplan.domain.model.materiaprima.MateriaPrima;
import br.dev.jstec.mobilplan.domain.model.materiaprima.TipoPrecificacao;

public interface Acessorio extends MateriaPrima {

    final TipoPrecificacao precificacao = TipoPrecificacao.UNIDADE;

}
