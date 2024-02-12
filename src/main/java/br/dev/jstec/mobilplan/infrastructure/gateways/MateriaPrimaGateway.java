package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.cor;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.descricao;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.intervaloPreco;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.largura;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.tenant;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.tipoAcabamento;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.jpa.domain.Specification.where;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import br.dev.jstec.mobilplan.infrastructure.jpa.FitaDeBordaRepository;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IFitaDeBordaMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MateriaPrimaGateway implements MateriaPrimaPort {

    private final FitaDeBordaRepository fitaDeBordaRepository;
    private final IFitaDeBordaMapper fitaDeBordaMapper;

    public Optional<FitaDeBorda> buscarPorId(Long id) {

        if (id == null) {
            return empty();
        }
        return fitaDeBordaRepository.findByIdAndTenantId(id, getUserLogged())
                .map(fitaDeBordaMapper::toModel);
    }

    @Override
    public FitaDeBorda salvar(FitaDeBorda fitaDeBorda) {

        var entity = fitaDeBordaMapper.toEntity(fitaDeBorda);

        var entitySaved = fitaDeBordaRepository.save(entity);

        return fitaDeBordaMapper.toModel(entitySaved);
    }

    @Override
    @Transactional
    public void remover(FitaDeBorda fitaDeBorda) {

        var entity = fitaDeBordaMapper.toEntity(fitaDeBorda);

        fitaDeBordaRepository.deleteByIdAndTenantId(entity.getId(), getUserLogged());
    }

    @Override
    public List<FitaDeBorda> buscar(
            String descricao, String cor, double largura, double doPreco, double atePreco, String tipoAcabamento) {

        log.info(
                "Buscando fitas de borda por critérios: descricao={}, cor={}, largura={}, doPreco={}, atePreco={}, tipoAcabamento={}",
                descricao, cor, largura, doPreco, atePreco, tipoAcabamento);
        var criterios = Specification.where(tenant(getUserLogged()))
                .and(descricao(descricao))
                .and(cor(cor))
                .and(largura(largura))
                .and(intervaloPreco(doPreco, atePreco))
                .and(tipoAcabamento(tipoAcabamento));

        return this.fitaDeBordaRepository.findAll(criterios)
                .stream()
                .map(fitaDeBordaMapper::toModel)
                .collect(toList());
    }

    public boolean existe(FitaDeBorda fitaDeBorda) {

        var fitaDeBordaEntity = fitaDeBordaMapper.toEntity(fitaDeBorda);
        var criterios = where(tenant(fitaDeBordaEntity.getTenantId()))
                .and(descricao(fitaDeBordaEntity.getDescricao()))
                .and(cor(fitaDeBordaEntity.getCor()))
                .and(largura(fitaDeBordaEntity.getLargura()));

        return fitaDeBordaRepository.exists(criterios);
    }
}
