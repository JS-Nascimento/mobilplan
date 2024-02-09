package br.dev.jstec.mobilplan.domain.materiaprima.acabamento;


import br.dev.jstec.mobilplan.domain.materiaprima.MateriaPrima;
import br.dev.jstec.mobilplan.domain.materiaprima.TipoPrecificacao;

public interface Acabamento extends MateriaPrima {

    final TipoPrecificacao precificacao = TipoPrecificacao.M2;

    TipoAcabamento getTipoAcabamento();
}

