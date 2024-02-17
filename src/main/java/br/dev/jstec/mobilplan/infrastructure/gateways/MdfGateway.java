package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.MdfSpecification.cor;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.MdfSpecification.descricao;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.MdfSpecification.espessura;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.MdfSpecification.intervaloPreco;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.MdfSpecification.tenant;
import static br.dev.jstec.mobilplan.infrastructure.jpa.specification.MdfSpecification.tipoAcabamento;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.Mdf;
import br.dev.jstec.mobilplan.infrastructure.jpa.materiaprima.MdfRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.specification.MdfSpecification;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IMdfMapper;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.MdfEntity;
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
public class MdfGateway implements MateriaPrimaPort<Mdf> {

    private final MdfRepository mdfRepository;
    private final IMdfMapper mdfMapper;

    @Override
    public Optional<Mdf> buscarPorId(Long id) {

        if (id == null) {
            return empty();
        }
        return mdfRepository.findByIdAndTenantId(id, getUserLogged())
                .map(mdfMapper::toModel);
    }

    @Override
    public Mdf salvar(Mdf mdf) {

        var entity = mdfMapper.toEntity(mdf);

        var entitySaved = mdfRepository.save(entity);

        return mdfMapper.toModel(entitySaved);
    }

    @Override
    @Transactional
    public void remover(Mdf mdf) {

        var entity = mdfMapper.toEntity(mdf);

        mdfRepository.deleteByIdAndTenantId(entity.getId(), getUserLogged());
    }

    @Override
    public List<Mdf> buscar(
            String descricao, String cor, double dimensaoBase, double doPreco, double atePreco, String tipoAcabamento) {

        log.debug(
                "Buscando fitas de borda por critérios: descricao={}, cor={}, "
                        + "largura={}, doPreco={}, atePreco={}, tipoAcabamento={}",
                descricao, cor, dimensaoBase, doPreco, atePreco, tipoAcabamento);
        var criterios = Specification.where(tenant(getUserLogged()))
                .and(descricao(descricao))
                .and(cor(cor))
                .and(espessura(dimensaoBase))
                .and(intervaloPreco(doPreco, atePreco))
                .and(tipoAcabamento(tipoAcabamento));

        return this.mdfRepository.findAll(criterios)
                .stream()
                .map(mdfMapper::toModel)
                .collect(toList());
    }

    @Override
    public boolean existe(Mdf novoMdf) {

        var entity = mdfMapper.toEntity(novoMdf);

        Specification<MdfEntity> criterios = Specification.where(MdfSpecification.tenant(entity.getTenantId()))
                .and(MdfSpecification.descricao(entity.getDescricao()))
                .and(MdfSpecification.cor(entity.getCor()))
                .and(MdfSpecification.altura(entity.getAltura()))
                .and(MdfSpecification.largura(entity.getLargura()))
                .and(MdfSpecification.espessura(entity.getEspessura()));

        return mdfRepository.exists(criterios);
    }
}
