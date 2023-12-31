package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.util.ImageUtil.processImage;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Imagem(

        String fileName,

        BufferedImage image) {

    public Imagem {

        if (isBlank(fileName)) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, Imagem.class.getSimpleName());
        }
    }

    public static Imagem of(String fileName) {

        return new Imagem(fileName, processImage(null));
    }

    public static Imagem of(String entityId, InputStream imageStream) {

        return new Imagem(entityId, processImage(imageStream));
    }
}
