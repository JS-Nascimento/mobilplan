package br.dev.jstec.efurniture.application.util;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.awt.Color.WHITE;
import static javax.imageio.ImageIO.read;
import static lombok.AccessLevel.PRIVATE;
import static net.coobird.thumbnailator.Thumbnails.of;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ImageUtil {

    public static BufferedImage processImage(InputStream imageStream) {

        try {

            if (imageStream == null) {

                log.info("Imagem não enviada, criando imagem em branco");
                return createBlankImage();
            }

            var image = read(imageStream);

            return of(image)
                .size(200, 200)
                .asBufferedImage();

        } catch (NullPointerException | IOException e) {

            log.error("Erro ao processar a imagem :", e);
            throw new BusinessException(ERRO_CAMPO_INVALIDO, "logomarca");
        }
    }

    private static BufferedImage createBlankImage() {

        var image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        var graphics = image.createGraphics();

        graphics.setColor(WHITE);
        graphics.fillRect(0, 0, 200, 200);

        graphics.dispose();

        return image;
    }
}
