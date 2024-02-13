package br.dev.jstec.mobilplan.infrastructure.persistence.helpers;

import static java.util.Optional.of;
import static javax.imageio.ImageIO.getWriterFormatNames;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.infrastructure.rest.client.bucket.PutFilesBucket;
import br.dev.jstec.mobilplan.infrastructure.rest.client.bucket.S3BucketClient;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class PersistenceHelper {

    private final PutFilesBucket putFilesBucket;

    protected String processAndSaveImage(String bucketName,
                                         String fileName,
                                         String tipoImagem,
                                         BufferedImage image
    ) throws IOException, URISyntaxException {

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

                fileName = fileName.concat(".").concat(contentType);
                var url = putFilesBucket.put(bucketName, fileName, inputStream, contentLength);

                return isBlank(url) ? EMPTY : url;
            }
        }
    }
}