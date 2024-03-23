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

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.BuscarFitaDeBordaPorCriteriosUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda;
import br.dev.jstec.mobilplan.infrastructure.jpa.materiaprima.FitaDeBordaRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.FitaDeBordaEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IFitaDeBordaMapper;
import jakarta.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
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
public class FitaDeBordaGateway implements MateriaPrimaPort<FitaDeBorda> {

    private final FitaDeBordaRepository fitaDeBordaRepository;

    private final IFitaDeBordaMapper fitaDeBordaMapper;

    @Override
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
    public List<FitaDeBorda> buscar(Object... objects) {

        var input = Arrays.stream(objects)
                .filter(BuscarFitaDeBordaPorCriteriosUseCase.Input.class::isInstance)
                .map(BuscarFitaDeBordaPorCriteriosUseCase.Input.class::cast)
                .findFirst()
                .orElse(
                        new BuscarFitaDeBordaPorCriteriosUseCase.Input(
                                null, null, 0, 0, 0, null));

        log.info("Buscando fitas de borda por critérios: {}", input);

        var criterios = Specification.where(tenant(getUserLogged()))
                .and(descricao(input.descricao()))
                .and(cor(input.cor()))
                .and(largura(input.largura()))
                .and(intervaloPreco(input.doPreco(), input.atePreco()))
                .and(tipoAcabamento(input.tipoAcabamento()));

        return this.fitaDeBordaRepository.findAll(criterios)
                .stream()
                .map(fitaDeBordaMapper::toModel)
                .collect(toList());
    }

    public boolean existe(FitaDeBorda fitaDeBorda) {

        var fitaDeBordaEntity = fitaDeBordaMapper.toEntity(fitaDeBorda);
        Specification<FitaDeBordaEntity> criterios =
                Specification.where(FitaDeBordaSpecification.tenant(fitaDeBordaEntity.getTenantId()))
                        .and(FitaDeBordaSpecification.descricao(fitaDeBordaEntity.getDescricao()))
                        .and(FitaDeBordaSpecification.cor(fitaDeBordaEntity.getCor()))
                        .and(FitaDeBordaSpecification.largura(fitaDeBordaEntity.getLargura()));

        return fitaDeBordaRepository.exists(criterios);
    }

    @Override
    public String salvarImagem(FitaDeBorda model, String fileName, String tipoImagem, BufferedImage image)
            throws IOException, URISyntaxException {
        return null;
    }

    @Override
    public boolean removerImagem(FitaDeBorda model, String url) {
        return false;
    }


}
