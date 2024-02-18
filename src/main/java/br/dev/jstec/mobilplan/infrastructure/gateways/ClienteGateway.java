package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static java.util.Optional.empty;

import br.dev.jstec.mobilplan.application.ports.ClientePort;
import br.dev.jstec.mobilplan.domain.model.cliente.Cliente;
import br.dev.jstec.mobilplan.infrastructure.jpa.ClienteRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.specification.ClienteSpecification;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.ClienteEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IClienteMapper;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteGateway implements ClientePort {

    private final ClienteRepository repository;
    private final IClienteMapper mapper;


    @Override
    public Optional<Cliente> buscarPorId(UUID id) {
        if (id == null) {
            return empty();
        }

        return repository.findByIdAndTenantId(id, getUserLogged())
                .map(mapper::toDomainModel);
    }

    @Override
    public Cliente salvar(Cliente model) {

        var entity = mapper.toEntity(model);

        var entitySaved = repository.save(entity);

        return mapper.toDomainModel(entitySaved);
    }

    @Override
    @Transactional
    public void remover(Cliente model) {

        var entity = mapper.toEntity(model);

        repository.deleteByIdAndTenantId(entity.getId(), getUserLogged());
    }

    @Override
    public Collection<Cliente> buscar(Object... criterios) {

        return null;
    }

    @Override
    public boolean existe(Cliente model) {

        var entity = mapper.toEntity(model);

        Specification<ClienteEntity> criterios =
                Specification.where(ClienteSpecification.tenant(getUserLogged()))
                        .and(ClienteSpecification.ativo(true))
                        .and(ClienteSpecification.nome(entity.getNome()))
                        .and(ClienteSpecification.email(entity.getEmail()));

        return repository.exists(criterios);
    }
}
