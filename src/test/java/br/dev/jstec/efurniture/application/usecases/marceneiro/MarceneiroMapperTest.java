package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MarceneiroMapperTest {

    private final MarceneiroMapper marceneiroMapper = new MarceneiroMapperImpl();

    @Test
    @DisplayName("Testar DefaultUpdateBy com valor não nulo")
    void testDefaultUpdateByWithNonNullValue() {

        var uuid = UUID.randomUUID();
        var result = marceneiroMapper.defaultUpdateBy(uuid);

        assertEquals(uuid.toString(), result);
    }

    @Test
    @DisplayName("Testar DefaultUpdateBy com valor nulo")
    void testDefaultUpdateByWithNullValue() {

        var result = marceneiroMapper.defaultUpdateBy(null);

        assertEquals("", result);
    }

    @Test
    @DisplayName("Testar DefaultInstant com valor não nulo")
    void testDefaultInstantWithNonNullValue() {

        var instant = Instant.now();
        var result = marceneiroMapper.defaultInstant(instant);

        var formatter = ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        var dataFormatada = formatter.format(instant);

        assertEquals(dataFormatada, result);
    }

    @Test
    @DisplayName("Testar DefaultInstant com valor nulo")
    void testDefaultInstantWithNullValue() {

        var result = marceneiroMapper.defaultInstant(null);

        assertEquals("", result);
    }
}