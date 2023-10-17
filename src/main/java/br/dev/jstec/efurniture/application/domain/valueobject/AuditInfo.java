package br.dev.jstec.efurniture.application.domain.valueobject;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public record AuditInfo(UUID createdBy,
                        LocalDateTime createdAt,
                        UUID updatedBy,
                        LocalDateTime updatedAt) {

    public static String fromInstant(LocalDateTime instant) {

        if (isNull(instant)) {

            return EMPTY;
        }

        var formatter = ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static String fromUuid(UUID uuid) {

        if (isNull(uuid)) {

            return EMPTY;
        }

        return uuid.toString();
    }
}
