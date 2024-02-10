package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.cor;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.descricao;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.largura;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.FitaDeBordaSpecification.tenant;
import static java.util.Optional.empty;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import br.dev.jstec.mobilplan.infrastructure.jpa.FitaDeBordaRepository;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FitaDeBordaMapper;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MateriaPrimaGateway implements MateriaPrimaPort {

    private final FitaDeBordaRepository fitaDeBordaRepository;
    private final FitaDeBordaMapper fitaDeBordaMapper;

    public Optional<FitaDeBorda> buscarPorId(Long id) {

        if (id == null) {
            return empty();
        }
        return fitaDeBordaRepository.findById(id)
                .map(fitaDeBordaMapper::toModel);
    }

    @Override
    public FitaDeBorda salvar(FitaDeBorda fitaDeBorda) {
        return null;
    }

    @Override
    public void remover(FitaDeBorda fitaDeBorda) {

    }

    @Override
    public Collection<FitaDeBorda> buscar() {
        return null;
    }

    public boolean existe(FitaDeBorda fitaDeBorda) {

        var fitaDeBordaEntity = fitaDeBordaMapper.toEntity(fitaDeBorda);
        var criterios = Specification.where(tenant(fitaDeBordaEntity.getTenantId()))
                .and(descricao(fitaDeBordaEntity.getDescricao()))
                .and(cor(fitaDeBordaEntity.getCor()))
                .and(largura(fitaDeBordaEntity.getLargura()));

        return fitaDeBordaRepository.exists(criterios);
    }
}
