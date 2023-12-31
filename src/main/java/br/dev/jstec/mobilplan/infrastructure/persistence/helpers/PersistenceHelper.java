package br.dev.jstec.mobilplan.infrastructure.persistence.helpers;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.infrastructure.rest.client.bucket.PutFilesBucket;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class PersistenceHelper {

    private final PutFilesBucket putFilesBucket;

    protected String processAndSaveImage(String bucketName,
                                         String fileName,
                                         String tipoImagem,
                                         BufferedImage image
    ) throws IOException, URISyntaxException {

        var outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, tipoImagem, outputStream);

        var buffer = outputStream.toByteArray();
        var inputStream = new ByteArrayInputStream(buffer);
        var contentLength = buffer.length;

        fileName = fileName.concat(".").concat(tipoImagem);

        var url = putFilesBucket.put(bucketName, fileName, inputStream, contentLength);

        if (isBlank(url)) {
            return EMPTY;
        }

        return url;
    }
}
