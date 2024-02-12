package br.dev.jstec.mobilplan.application.ports;

import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import java.util.Collection;
import java.util.Optional;

public interface MateriaPrimaPort {

    FitaDeBorda salvar(FitaDeBorda fitaDeBorda);

    void remover(FitaDeBorda fitaDeBorda);

    Collection<FitaDeBorda> buscar(
            String descricao, String cor, double largura, double doPreco, double atePreco, String tipoAcabamento);

    boolean existe(FitaDeBorda novaFita);

    Optional<FitaDeBorda> buscarPorId(Long id);
}
