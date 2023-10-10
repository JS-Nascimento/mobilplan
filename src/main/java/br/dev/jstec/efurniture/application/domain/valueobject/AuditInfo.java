package br.dev.jstec.efurniture.application.domain.valueobject;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.UUID.fromString;

import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

public record AuditInfo(UUID createdBy,
                        Instant createdAt,
                        UUID updatedBy,
                        Instant updatedAt) {

    public static AuditInfo auditedCreateOf(String createdBy) {

        return new AuditInfo(fromString(createdBy),
                Instant.now(),
                null,
                null);
    }

    public static AuditInfo auditedUpdateOf(String updatedBy, AuditInfo auditInfo) {

        return new AuditInfo(auditInfo.createdBy,
                auditInfo.createdAt,
                fromString(updatedBy),
                Instant.now());
    }

    public static String fromInstant(Instant instant) {
        var formatter = ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static String fromUuid(UUID uuid) {
        return uuid.toString();
    }
}
