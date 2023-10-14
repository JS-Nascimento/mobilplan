package br.dev.jstec.efurniture.application.domain.valueobject;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class AuditInfoFixture {

    public static AuditInfo buildDeCriacao() {

        return AuditInfo.auditedCreateOf(UUID.randomUUID().toString());
    }

    public static AuditInfo buildDeAlteracao() {

        var auditInfo = buildDeCriacao();

        return AuditInfo.auditedUpdateOf(
            UUID.randomUUID().toString(),
            auditInfo);
    }

    public static void buildCriacaoInvalida() {

        AuditInfo.auditedCreateOf(null);
    }

    public static void builAlteracaoComUsuarioInvalido() {

        var auditInfo = buildDeCriacao();

        AuditInfo.auditedUpdateOf(null, auditInfo);
    }

    public static void builAlteracaoComAuditInfoInvalido() {

        AuditInfo.auditedUpdateOf(UUID.randomUUID().toString(), null);
    }
}
