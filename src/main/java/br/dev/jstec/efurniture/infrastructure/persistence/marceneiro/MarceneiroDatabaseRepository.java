package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.infrastructure.jpa.MarceneiroJpaRepository;
import br.dev.jstec.efurniture.infrastructure.rest.client.bucket.PutFilesBucket;
import jakarta.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarceneiroDatabaseRepository implements MarceneiroRepository {

    private final MarceneiroJpaRepository repository;
    private final MarceneiroEntityMapper mapper;
    private final PutFilesBucket putFilesBucket;

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

        var outputStream = new ByteArrayOutputStream();

        ImageIO.write(image, tipoImagem, outputStream);

        var buffer = outputStream.toByteArray();
        var inputStream = new ByteArrayInputStream(buffer);
        var contentLength = buffer.length;

        fileName = fileName.concat(".").concat(tipoImagem);

        var logoUrl = putFilesBucket.put(fileName, inputStream, contentLength);

        if (!isBlank(logoUrl)) {

            var entity = mapper.toMarceneiroEntity(marceneiro);
            entity.setLogomarcaUrl(logoUrl);
            entity.setLogomarcaFilename(fileName);

            repository.save(entity);

            return logoUrl;
        }

        return EMPTY;
    }
}
