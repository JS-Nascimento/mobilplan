package br.dev.jstec.mobilplan.application.ports;

import br.dev.jstec.mobilplan.domain.model.cliente.Cliente;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ClientePort {

    Optional<Cliente> buscarPorId(UUID id);

    Cliente salvar(Cliente model);

    void remover(Cliente model);

    Collection<Cliente> buscar(Object... criterios);

    boolean existe(Cliente model);
}
