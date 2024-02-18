package br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento;


import br.dev.jstec.mobilplan.domain.model.materiaprima.MateriaPrima;
import br.dev.jstec.mobilplan.domain.model.materiaprima.TipoPrecificacao;

public interface Acabamento extends MateriaPrima {

    final TipoPrecificacao precificacao = TipoPrecificacao.M2;

    TipoAcabamento getTipoAcabamento();
}

