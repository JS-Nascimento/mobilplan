package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.model.cliente.Cliente;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.ClienteEntity;

public interface IClienteMapper {

    Cliente toDomainModel(ClienteEntity clienteEntity);

    ClienteEntity toEntity(Cliente cliente);
}
