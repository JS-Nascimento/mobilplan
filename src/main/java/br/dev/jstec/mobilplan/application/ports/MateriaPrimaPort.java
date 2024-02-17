package br.dev.jstec.mobilplan.application.ports;

import java.util.Collection;
import java.util.Optional;

public interface MateriaPrimaPort<T> {

    Optional<T> buscarPorId(Long id);

    T salvar(T model);

    void remover(T model);

    Collection<T> buscar(
            String descricao, String cor, double dimensaoBase, double doPreco, double atePreco, String tipoAcabamento);

    boolean existe(T model);

}
