package br.dev.jstec.efurniture.application.util;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.awt.Color.WHITE;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageUtilTest {

    @Test
    @DisplayName("Ao fornecer um InputStream nulo, deve retornar uma imagem em branco")
    void testProcessImageWithNullInputStreamShouldReturnBlankImage() {

        var image = ImageUtil.processImage(null);

        assertEquals(200, image.getHeight());
        assertEquals(200, image.getWidth());
        assertEquals(WHITE, image.getGraphics().getColor());
    }

    @Test
    @DisplayName("Ao fornecer um InputStream inválido, deve lançar uma exceção")
    void testProcessImageWithInvalidInputStreamShouldThrowException() {

        var corruptedInputStream = new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5});

        var exception = assertThrows(BusinessException.class,
            () -> ImageUtil.processImage(corruptedInputStream));

        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "logomarca"),
            exception.getErrorMessage().getMsg());
        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
    }
}