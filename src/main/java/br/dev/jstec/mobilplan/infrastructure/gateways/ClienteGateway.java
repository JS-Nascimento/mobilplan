package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static java.util.Optional.empty;

import br.dev.jstec.mobilplan.application.ports.ClientePort;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorCriteriosUseCase;
import br.dev.jstec.mobilplan.domain.model.cliente.Cliente;
import br.dev.jstec.mobilplan.infrastructure.jpa.ClienteRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.specification.ClienteSpecification;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.ClienteEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IClienteMapper;
import jakarta.transaction.Transactional;
import java.util.Arrays;
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
    public Collection<Cliente> buscar(Object... objects) {

        var input = Arrays.stream(objects)
                .filter(BuscarClientePorCriteriosUseCase.Input.class::isInstance)
                .map(BuscarClientePorCriteriosUseCase.Input.class::cast)
                .findFirst()
                .orElse(new BuscarClientePorCriteriosUseCase.Input(null, null, null, null, null, null, null));

        log.debug("Buscando clientes por criterios: {}", input);

        var criterios = Specification.where(ClienteSpecification.tenant(getUserLogged()))
                .and(ClienteSpecification.ativo(input.ativo()))
                .and(ClienteSpecification.nome(input.nome()))
                .and(ClienteSpecification.email(input.email()))
                .and(ClienteSpecification.tipoPessoa(input.tipoPessoa()))
                .and(ClienteSpecification.notificarPorEmail(input.notificarPorEmail()))
                .and(ClienteSpecification.notificarPorWhatsapp(input.notificarPorWhatsapp()));

        return this.repository.findAll(criterios)
                .stream()
                .map(mapper::toDomainModel)
                .toList();
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
