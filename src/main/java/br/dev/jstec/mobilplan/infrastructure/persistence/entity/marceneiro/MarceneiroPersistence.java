package br.dev.jstec.mobilplan.infrastructure.persistence.entity.marceneiro;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.domain.model.marceneiro.Marceneiro;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.infrastructure.jpa.MarceneiroJpaRepository;
import br.dev.jstec.mobilplan.infrastructure.persistence.helpers.PersistenceHelper;
import br.dev.jstec.mobilplan.infrastructure.rest.client.bucket.StorageGateway;
import jakarta.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MarceneiroPersistence extends PersistenceHelper implements MarceneiroPort {

    private final MarceneiroJpaRepository repository;
    private final MarceneiroEntityMapper mapper;

    @Value("${spring.repository.bucket-name.logomarca}")
    private String bucketName;

    public MarceneiroPersistence(StorageGateway storageGateway, MarceneiroJpaRepository repository,
                                 MarceneiroEntityMapper mapper) {
        super(storageGateway);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Marceneiro> buscarPorId(UUID anId) {

        return repository.findById(anId)
                .map(mapper::toMarceneiro)
                .or(Optional::empty);
    }

    @Override
    public Optional<Marceneiro> buscarPorEmail(Email email) {

        return repository.findByEmail(email.value())
                .map(mapper::toMarceneiro)
                .or(Optional::empty);
    }

    @Override
    public Optional<Marceneiro> buscarPorDocumento(String documento) {

        return repository.findByDocumento(documento)
                .map(mapper::toMarceneiro)
                .or(Optional::empty);
    }

    @Override
    @Transactional
    public Marceneiro salvar(Marceneiro marceneiro) {

        var marceneiroEntity = mapper.toMarceneiroEntity(marceneiro);

        log.info("Salvando Marceneiro : {}", marceneiroEntity);
        var marceneiroEntitySaved = repository.save(marceneiroEntity);

        return mapper.toMarceneiro(marceneiroEntitySaved);
    }

    @Override
    public Collection<Marceneiro> buscarTodos() {

        var marceneirosFounded = repository.findAll();

        return StreamSupport.stream(
                        marceneirosFounded.spliterator(), false)
                .map(mapper::toMarceneiro)
                .toList();
    }

    @Override
    public String salvarLogomarca(Marceneiro marceneiro, String fileName, String tipoImagem,
                                  BufferedImage image)
            throws IOException, URISyntaxException {

        var logoUrl = processAndSaveImage(bucketName, fileName, tipoImagem, image);

        if (!isBlank(logoUrl)) {

            var entity = mapper.toMarceneiroEntity(marceneiro);
            entity.setLogomarcaUrl(logoUrl);
            entity.setLogomarcaFilename(fileName);

            log.info("Salvando informações de logomarca para o Marceneiro : {}", entity);
            repository.save(entity);

            return logoUrl;
        }

        return EMPTY;
    }
}
