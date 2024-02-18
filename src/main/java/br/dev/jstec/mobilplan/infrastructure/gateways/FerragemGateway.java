package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FerragemSpecification.cor;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FerragemSpecification.descricao;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FerragemSpecification.intervaloPreco;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FerragemSpecification.tenant;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FerragemSpecification.tipoPrecificacao;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FerragemSpecification.unidade;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.BuscarFerragemPorCriteriosUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import br.dev.jstec.mobilplan.infrastructure.jpa.materiaprima.FerragemRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.specification.FerragemSpecification;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.FerragemEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IFerragemMapper;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FerragemGateway implements MateriaPrimaPort<Ferragem> {

    private final FerragemRepository repository;
    private final IFerragemMapper mapper;

    @Override
    public Optional<Ferragem> buscarPorId(Long id) {

        if (id == null) {
            return empty();
        }
        return repository.findByIdAndTenantId(id, getUserLogged())
                .map(mapper::toModel);
    }

    @Override
    public Ferragem salvar(Ferragem ferragem) {

        var entity = mapper.toEntity(ferragem);

        var entitySaved = repository.save(entity);

        return mapper.toModel(entitySaved);
    }

    @Override
    @Transactional
    public void remover(Ferragem ferragem) {

        var entity = mapper.toEntity(ferragem);

        repository.deleteByIdAndTenantId(entity.getId(), getUserLogged());
    }

    @Override
    public List<Ferragem> buscar(Object... objects) {

        var input = Arrays.stream(objects)
                .filter(BuscarFerragemPorCriteriosUseCase.Input.class::isInstance)
                .map(BuscarFerragemPorCriteriosUseCase.Input.class::cast)
                .findFirst()
                .orElse(new BuscarFerragemPorCriteriosUseCase.Input(null, null, null, 0, 0, null));

        log.debug("Buscando ferragens por critérios: {}", input);

        var criterios = Specification.where(tenant(getUserLogged()))
                .and(descricao(input.descricao()))
                .and(cor(input.cor()))
                .and(unidade(input.unidade()))
                .and(intervaloPreco(input.doPreco(), input.atePreco()))
                .and(tipoPrecificacao(input.precificacao()));

        return this.repository.findAll(criterios)
                .stream()
                .map(mapper::toModel)
                .collect(toList());
    }

    @Override
    public boolean existe(Ferragem novaFerragem) {

        var entity = mapper.toEntity(novaFerragem);

        Specification<FerragemEntity> criterios =
                Specification.where(FerragemSpecification.tenant(entity.getTenantId()))
                        .and(FerragemSpecification.descricao(entity.getDescricao()))
                        .and(FerragemSpecification.cor(entity.getCor()))
                        .and(FerragemSpecification.unidade(entity.getUnidade()))
                        .and(FerragemSpecification.tipoPrecificacao(entity.getPrecificacao()));

        return repository.exists(criterios);
    }
}
