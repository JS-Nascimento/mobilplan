package br.dev.jstec.mobilplan.domain.materiaprima.acessorios;

import static br.dev.jstec.mobilplan.domain.materiaprima.TipoPrecificacao.UNIDADE;

import br.dev.jstec.mobilplan.domain.materiaprima.MateriaPrima;
import br.dev.jstec.mobilplan.domain.materiaprima.TipoPrecificacao;

public interface Acessorio extends MateriaPrima {

    final TipoPrecificacao precificacao = UNIDADE;

}
