package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.awt.Color.WHITE;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LogomarcaTest {

    @Test
    @DisplayName("Quando fornecido um InputStream nulo, uma Logomarca deve ser criada com uma imagem padrão")
    void createWithNullInputStream() {

        var logomarca = LogomarcaFixture.buildComFilename();

        assertNotNull(logomarca.fileName());
        assertNotNull(logomarca.image());
        assertEquals(200, logomarca.image().getWidth());
        assertEquals(200, logomarca.image().getHeight());
        assertEquals(WHITE, logomarca.image().getGraphics().getColor());
    }

    @Test
    @DisplayName("Quando fornecido um InputStream válido, uma Logomarca deve ser criada com uma imagem não nula")
    void createWithValidInputStream() {

        var logomarca = LogomarcaFixture.buildComFilenameEInputStream();
        assertNotNull(logomarca.fileName());
        assertNotNull(logomarca.image());
        assertEquals(200, logomarca.image().getWidth());
    }

    @Test
    @DisplayName("Deve lançar uma Business Exception quando não fornecido um filename válido")
    void throwExceptionWithFileNameNotValid() {

        var exception = assertThrows(BusinessException.class, () -> Logomarca.of(null));

        assertEquals(ERRO_CAMPO_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_CAMPO_INVALIDO.getMsg(), "Logomarca"),
            exception.getErrorMessage().getMsg());
    }
}