package br.dev.jstec.mobilplan.infrastructure.jpa.specification;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.ClienteEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.ClienteEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecification {

    private ClienteSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<ClienteEntity> tenant(UUID tenantId) {
        if (tenantId == null) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        return (root, query, builder) -> builder.equal(root.get(ClienteEntity_.tenantId),
                tenantId);
    }

    private static <T> Specification<ClienteEntity> applyConditionIfNotNull(
            T value, TriFunction<Root<ClienteEntity>, CriteriaBuilder, T, Predicate> condition) {
        return (root, query, builder) -> value != null ? condition.apply(root, builder, value) : builder.conjunction();
    }

    public static Specification<ClienteEntity> ativo(Boolean ativo) {
        return (root, query, builder) -> {
            if (ativo != null) {
                return builder.equal(root.get(ClienteEntity_.ativo), ativo);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<ClienteEntity> nome(String nome) {
        return applyConditionIfNotNull(nome,
                (root, builder, value) -> builder.like(builder.upper(root.get(ClienteEntity_.nome)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<ClienteEntity> tipoPessoa(String tipoPessoa) {
        return applyConditionIfNotNull(tipoPessoa,
                (root, builder, value) -> builder.equal(builder.upper(root.get(ClienteEntity_.tipoPessoa)),
                        value.toUpperCase()));
    }

    public static Specification<ClienteEntity> email(String email) {
        return applyConditionIfNotNull(email,
                (root, builder, value) -> builder.like(builder.upper(root.get(ClienteEntity_.email)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<ClienteEntity> notificarPorEmail(Boolean notificarPorEmail) {
        return (root, query, builder) -> {
            if (notificarPorEmail != null) {
                return builder.equal(root.get(ClienteEntity_.notificarPorEmail), notificarPorEmail);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<ClienteEntity> notificarPorWhatsapp(Boolean notificarPorWhatsapp) {
        return (root, query, builder) -> {
            if (notificarPorWhatsapp != null) {
                return builder.equal(root.get(ClienteEntity_.notificarPorWhatsapp), notificarPorWhatsapp);
            } else {
                return builder.conjunction();
            }
        };
    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
