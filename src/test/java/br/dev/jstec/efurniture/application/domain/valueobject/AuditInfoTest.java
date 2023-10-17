package br.dev.jstec.efurniture.application.domain.valueobject;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuditInfoTest {

    @Test
    @DisplayName("Deve receber um Instant e retornar uma string formatada com sucesso")
    void testFromInstant() {

        LocalDateTime instant = LocalDateTime.now();

        var formatter = ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        var expected = formatter.format(instant);
        var actual = AuditInfo.fromInstant(instant);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Deve receber um UUID e retornar uma string com sucesso")
    void testFromUuid() {

        var uuid = UUID.randomUUID();
        var expected = uuid.toString();
        var actual = AuditInfo.fromUuid(uuid);

        assertEquals(expected, actual);
    }
}