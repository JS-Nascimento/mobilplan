package br.dev.jstec.mobilplan.infrastructure.jpa.specification;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FerragemEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FerragemEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class FerragemSpecification {

    private FerragemSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<FerragemEntity> tenant(UUID tenantId) {
        if (tenantId == null) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        return (root, query, builder) -> builder.equal(root.get(FerragemEntity_.tenantId),
                tenantId);
    }

    private static <T> Specification<FerragemEntity> applyConditionIfNotNull(
            T value, TriFunction<Root<FerragemEntity>, CriteriaBuilder, T, Predicate> condition) {
        return (root, query, builder) -> value != null ? condition.apply(root, builder, value) : builder.conjunction();
    }

    public static Specification<FerragemEntity> descricao(String descricao) {
        return applyConditionIfNotNull(descricao,
                (root, builder, value) -> builder.like(builder.upper(root.get(FerragemEntity_.descricao)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<FerragemEntity> cor(String cor) {
        return applyConditionIfNotNull(cor,
                (root, builder, value) -> builder.like(builder.upper(root.get(FerragemEntity_.cor)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<FerragemEntity> intervaloPreco(Double precoMin, Double precoMax) {
        return (root, query, builder) -> {
            if (precoMin > 0.0 && precoMax > 0.0) {
                return builder.between(root.get(FerragemEntity_.preco), precoMin, precoMax);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<FerragemEntity> unidade(String unidade) {
        return applyConditionIfNotNull(unidade,
                (root, builder, value) ->
                        builder.equal(builder.upper(root.get(FerragemEntity_.unidade)),
                                value.toUpperCase()));
    }

    public static Specification<FerragemEntity> tipoPrecificacao(String precificacao) {
        return applyConditionIfNotNull(precificacao,
                (root, builder, value) ->
                        builder.equal(builder.upper(root.get(FerragemEntity_.precificacao)),
                                value.toUpperCase()));
    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
