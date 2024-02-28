package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;

import br.dev.jstec.mobilplan.application.ports.ConfiguracaoFabricacaoPort;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import br.dev.jstec.mobilplan.infrastructure.jpa.ConfiguracaoFabricacaoRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.specification.ConfiguracaoFabricacaoSpecification;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.ConfiguracaoFabricacaoEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IConfiguracaoFabricacaoMapper;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfiguracaoFabricacaoGateway implements ConfiguracaoFabricacaoPort {

    private final ConfiguracaoFabricacaoRepository repository;
    private final IConfiguracaoFabricacaoMapper mapper;


    @Override
    public Optional<ConfiguracaoFabricacao> buscarPorId(Long id) {
        if (id == null) {
            return empty();
        }

        return repository.findByIdAndTenantId(id, getUserLogged())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<ConfiguracaoFabricacao> buscarPorTenant() {
        if (isNull(getUserLogged())) {
            return empty();
        }
        return repository.findByTenantId(getUserLogged())
                .map(mapper::toDomain);
    }

    @Override
    public ConfiguracaoFabricacao salvar(ConfiguracaoFabricacao model) {

        var entity = mapper.toEntity(model);

        var entitySaved = repository.save(entity);

        return mapper.toDomain(entitySaved);
    }

    @Override
    @Transactional
    public void remover(ConfiguracaoFabricacao model) {

        var entity = mapper.toEntity(model);

        repository.deleteByIdAndTenantId(entity.getId(), getUserLogged());
    }

    @Override
    public boolean existe(ConfiguracaoFabricacao model) {

        var entity = mapper.toEntity(model);

        Specification<ConfiguracaoFabricacaoEntity> criterios =
                Specification.where(ConfiguracaoFabricacaoSpecification.tenant(getUserLogged()))
                        .and(ConfiguracaoFabricacaoSpecification.descricao(entity.getDescricao()));

        return repository.exists(criterios);
    }
}
