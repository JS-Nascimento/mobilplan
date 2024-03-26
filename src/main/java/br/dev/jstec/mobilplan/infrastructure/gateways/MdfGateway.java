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
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.BuscarMdfPorCriteriosUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
import br.dev.jstec.mobilplan.infrastructure.jpa.materiaprima.MdfRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.specification.MdfSpecification;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.MdfEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.helpers.PersistenceHelper;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IMdfMapper;
import br.dev.jstec.mobilplan.infrastructure.rest.client.bucket.StorageGateway;
import jakarta.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MdfGateway extends PersistenceHelper implements MateriaPrimaPort<Mdf> {

    private final MdfRepository mdfRepository;
    private final IMdfMapper mdfMapper;

    private static final String FOLDER_NAME = "mdfs";

    public MdfGateway(StorageGateway storageGateway,
                      MdfRepository mdfRepository, IMdfMapper mdfMapper) {
        super(storageGateway);
        this.mdfRepository = mdfRepository;
        this.mdfMapper = mdfMapper;
    }

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
    public List<Mdf> buscar(Object... objects) {

        var input = Arrays.stream(objects)
                .filter(BuscarMdfPorCriteriosUseCase.Input.class::isInstance)
                .map(BuscarMdfPorCriteriosUseCase.Input.class::cast)
                .findFirst()
                .orElse(
                        new BuscarMdfPorCriteriosUseCase.Input(null, null, 0, 0, 0, null));


        log.debug("Buscando fitas de borda por critérios: {}", input);

        var criterios = Specification.where(tenant(getUserLogged()))
                .and(descricao(input.descricao())
                        .and(cor(input.cor()))
                        .and(espessura(input.espessura()))
                        .and(intervaloPreco(input.doPreco(), input.atePreco()))
                        .and(tipoAcabamento(input.tipoAcabamento())));

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

    @Override
    public String salvarImagem(Mdf model, String fileName, String tipoImagem, BufferedImage image)
            throws IOException, URISyntaxException {


        var logoUrl = processAndSaveImage(FOLDER_NAME, fileName, tipoImagem, image);

        if (isNotBlank(logoUrl)) {

            var entity = mdfMapper.toEntity(model);
            entity.setImagem(logoUrl);

            log.info("Salvando informações da Imagem para a {} : {}", FOLDER_NAME, entity);
            mdfRepository.save(entity);

            return logoUrl;
        }
        return EMPTY;
    }

    @Override
    public boolean removerImagem(Mdf model, String url) {

        var entity = mdfMapper.toEntity(model);

        var resultado = deleteImage(url);

        if (resultado) {
            entity.setImagem(null);
            mdfRepository.save(entity);
        }

        return resultado;
    }
}
