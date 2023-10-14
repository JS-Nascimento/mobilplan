package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_DADOS_OBRIGATORIOS_INCONSISTENTES;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.isNull;
import static java.util.UUID.fromString;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

public record AuditInfo(UUID createdBy,
                        Instant createdAt,
                        UUID updatedBy,
                        Instant updatedAt) {

    public static AuditInfo auditedCreateOf(String createdBy) {

        if (isBlank(createdBy)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "Usuário");
        }

        return new AuditInfo(fromString(createdBy),
            Instant.now(),
            null,
            null);
    }

    public static AuditInfo auditedUpdateOf(String updatedBy, AuditInfo auditInfo) {

        if (isBlank(updatedBy)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, "Usuário");
        }

        if (isNull(auditInfo)) {

            throw new BusinessException(ERRO_DADOS_OBRIGATORIOS_INCONSISTENTES);
        }

        return new AuditInfo(auditInfo.createdBy,
            auditInfo.createdAt,
            fromString(updatedBy),
            Instant.now());
    }

    public static String fromInstant(Instant instant) {

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
