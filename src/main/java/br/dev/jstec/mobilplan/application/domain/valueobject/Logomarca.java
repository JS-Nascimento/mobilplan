package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.application.util.ImageUtil.processImage;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Logomarca(

    String fileName,

    BufferedImage image) {

    public Logomarca {

        if (isBlank(fileName)) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, Logomarca.class.getSimpleName());
        }
    }

    public static Logomarca of(String fileName) {

        return new Logomarca(fileName, processImage(null));
    }

    public static Logomarca of(String marceneiroId, InputStream imageStream) {

        return new Logomarca(marceneiroId, processImage(imageStream));
    }
}
