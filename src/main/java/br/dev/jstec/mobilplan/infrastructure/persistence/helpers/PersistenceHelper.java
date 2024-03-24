package br.dev.jstec.mobilplan.infrastructure.persistence.helpers;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static java.util.Optional.of;
import static javax.imageio.ImageIO.getWriterFormatNames;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.infrastructure.rest.client.bucket.S3BucketClient;
import br.dev.jstec.mobilplan.infrastructure.rest.client.bucket.StorageGateway;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;


@Slf4j
public abstract class PersistenceHelper {

    @Value("${spring.repository.bucket-name}")
    private String bucketName;
    private final StorageGateway storageGateway;

    private String userDirectory;

    public PersistenceHelper(StorageGateway storageGateway) {
        this.storageGateway = storageGateway;

    }

    protected boolean deleteImage(String url) {
        return storageGateway.deleteFromUrl(url);
    }

    protected String processAndSaveImage(String fileName,
                                         String tipoImagem,
                                         BufferedImage image
    ) throws IOException, URISyntaxException {

        userDirectory = bucketName.concat("/").concat(getUserLogged().toString());
        return processAndSaveImage(userDirectory, fileName, tipoImagem, image);
    }


    protected String processAndSaveImage(String folderName,
                                         String fileName,
                                         String tipoImagem,
                                         BufferedImage image
    ) throws IOException, URISyntaxException {

        var folder = getFolderName(folderName);

        if (image == null) {
            log.warn("A imagem fornecida é nula");
            return EMPTY;
        }

        var contentType = of(S3BucketClient.getShortContentType(tipoImagem))
                .filter(tipo -> Arrays.asList(getWriterFormatNames()).contains(tipo))
                .orElseGet(() -> {
                    log.warn("Tipo de imagem não suportado: {}", tipoImagem);
                    return EMPTY;
                });

        try (var outputStream = new ByteArrayOutputStream()) {
            boolean writeStatus = ImageIO.write(image, contentType, outputStream);

            if (!writeStatus) {
                log.warn("Falha ao escrever a imagem. Tipo de imagem suportados: {}",
                        Arrays.toString(getWriterFormatNames()));
                return EMPTY;
            }

            var buffer = outputStream.toByteArray();
            try (var inputStream = new ByteArrayInputStream(buffer)) {
                var contentLength = buffer.length;

                var url = storageGateway.put(folder, getFileName(fileName, contentType), inputStream, contentLength);

                return isBlank(url) ? EMPTY : url;
            }
        }
    }

    private String getFolderName(String folderName) {
        userDirectory = bucketName.concat("/").concat(getUserLogged().toString());
        return userDirectory.concat("/").concat(folderName);
    }

    private String getFileName(String fileName, String contentType) {
        var formatter = DateTimeFormatter
                .ofPattern("yyyyMMddHHmmss")
                .withZone(ZoneOffset.UTC);

        var timeStamp = formatter.format(OffsetDateTime.now());

        return fileName
                .concat("-")
                .concat(timeStamp)
                .concat(".")
                .concat(contentType);
    }
}
