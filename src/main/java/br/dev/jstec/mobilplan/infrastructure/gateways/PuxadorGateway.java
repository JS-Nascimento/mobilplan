package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification.cor;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification.descricao;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification.intervaloPreco;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification.largura;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification.perfil;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification.tenant;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification.tipoPrecificacao;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification.tipoPuxador;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.BuscarPuxadorPorCriteriosUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import br.dev.jstec.mobilplan.infrastructure.jpa.materiaprima.PuxadorRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.specification.PuxadorSpecification;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.PuxadorEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IPuxadorMapper;
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
public class PuxadorGateway implements MateriaPrimaPort<Puxador> {

    private final PuxadorRepository repository;
    private final IPuxadorMapper mapper;

    @Override
    public Optional<Puxador> buscarPorId(Long id) {

        if (id == null) {
            return empty();
        }
        return repository.findByIdAndTenantId(id, getUserLogged())
                .map(mapper::toModel);
    }

    @Override
    public Puxador salvar(Puxador puxador) {

        var entity = mapper.toEntity(puxador);

        var entitySaved = repository.save(entity);

        return mapper.toModel(entitySaved);
    }

    @Override
    @Transactional
    public void remover(Puxador puxador) {

        var entity = mapper.toEntity(puxador);

        repository.deleteByIdAndTenantId(entity.getId(), getUserLogged());
    }

    @Override
    public List<Puxador> buscar(Object... objects) {

        var input = Arrays.stream(objects)
                .filter(BuscarPuxadorPorCriteriosUseCase.Input.class::isInstance)
                .map(BuscarPuxadorPorCriteriosUseCase.Input.class::cast)
                .findFirst()
                .orElse(new
                        BuscarPuxadorPorCriteriosUseCase.Input(null, null, null, null, 0, 0, 0, null));

        log.debug("Buscando fitas de borda por critérios: {}", input);

        var criterios = Specification.where(tenant(getUserLogged()))
                .and(perfil(input.perfil()))
                .and(tipoPuxador(input.tipoPuxador()))
                .and(descricao(input.descricao()))
                .and(cor(input.cor()))
                .and(largura(input.largura()))
                .and(intervaloPreco(input.doPreco(), input.atePreco()))
                .and(tipoPrecificacao(input.precificacao()));

        return this.repository.findAll(criterios)
                .stream()
                .map(mapper::toModel)
                .collect(toList());
    }

    @Override
    public boolean existe(Puxador novoPuxador) {

        var entity = mapper.toEntity(novoPuxador);

        Specification<PuxadorEntity> criterios = Specification.where(PuxadorSpecification.tenant(entity.getTenantId()))
                .and(perfil(entity.isPerfil()))
                .and(tipoPuxador(entity.getTipoPuxador()))
                .and(PuxadorSpecification.descricao(entity.getDescricao()))
                .and(PuxadorSpecification.cor(entity.getCor()))
                .and(PuxadorSpecification.altura(entity.getAltura()))
                .and(PuxadorSpecification.largura(entity.getLargura()))
                .and(PuxadorSpecification.espessura(entity.getEspessura()));

        return repository.exists(criterios);
    }
}
