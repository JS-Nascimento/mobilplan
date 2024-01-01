package br.dev.jstec.mobilplan.domain.util;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static java.awt.Color.WHITE;
import static javax.imageio.ImageIO.read;
import static lombok.AccessLevel.PRIVATE;
import static net.coobird.thumbnailator.Thumbnails.of;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
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

            BufferedImage image = read(imageStream);
            if (image == null) {
                log.warn("Não foi possível ler a imagem do stream");
                return createBlankImage();
            }

            return of(image).size(200, 200).asBufferedImage();

        } catch (NullPointerException | IOException e) {

            log.error("Erro ao processar a imagem: {}", e.getMessage(), e);
            throw new DomainException(ERRO_CAMPO_INVALIDO, "logomarca");
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
