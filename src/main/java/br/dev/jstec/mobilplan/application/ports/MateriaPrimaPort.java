package br.dev.jstec.mobilplan.application.ports;

import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import java.util.Collection;

public interface MateriaPrimaPort {

    FitaDeBorda salvar(FitaDeBorda fitaDeBorda);

    void remover(FitaDeBorda fitaDeBorda);

    Collection<FitaDeBorda> buscar();

    boolean existe(FitaDeBorda novaFita);
}
