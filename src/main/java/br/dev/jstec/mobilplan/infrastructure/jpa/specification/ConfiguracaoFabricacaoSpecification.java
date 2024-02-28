package br.dev.jstec.mobilplan.infrastructure.jpa.specification;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.ConfiguracaoFabricacaoEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.ConfiguracaoFabricacaoEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class ConfiguracaoFabricacaoSpecification {

    private ConfiguracaoFabricacaoSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<ConfiguracaoFabricacaoEntity> tenant(UUID tenantId) {
        if (tenantId == null) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        return (root, query, builder) -> builder.equal(root.get(ConfiguracaoFabricacaoEntity_.tenantId),
                tenantId);
    }

    private static <T> Specification<ConfiguracaoFabricacaoEntity> applyConditionIfNotNull(
            T value, TriFunction<Root<ConfiguracaoFabricacaoEntity>, CriteriaBuilder, T, Predicate> condition) {
        return (root, query, builder) -> value != null ? condition.apply(root, builder, value) : builder.conjunction();
    }

    public static Specification<ConfiguracaoFabricacaoEntity> descricao(String nome) {
        return applyConditionIfNotNull(nome,
                (root, builder, value) -> builder.like(builder.upper(root.get(ConfiguracaoFabricacaoEntity_.descricao)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<ConfiguracaoFabricacaoEntity> intervaloDataCriacao(LocalDateTime de,
                                                                                   LocalDateTime ate) {
        return (root, query, builder) -> {
            if (isNull(de) || isNull(ate) || de.isAfter(ate)) {
                return builder.conjunction();
            } else {
                return builder.between(root.get(ConfiguracaoFabricacaoEntity_.criadoEm), de, ate);
            }
        };
    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
