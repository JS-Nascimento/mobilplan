package br.dev.jstec.mobilplan.infrastructure.jpa.specification;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FitaDeBordaEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FitaDeBordaEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class FitaDeBordaSpecification {

    private FitaDeBordaSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<FitaDeBordaEntity> tenant(UUID tenantId) {
        if (tenantId == null) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        return (root, query, builder) -> builder.equal(root.get(FitaDeBordaEntity_.tenantId),
                tenantId);
    }

    private static <T> Specification<FitaDeBordaEntity> applyConditionIfNotNull(
            T value, TriFunction<Root<FitaDeBordaEntity>, CriteriaBuilder, T, Predicate> condition) {
        return (root, query, builder) -> value != null ? condition.apply(root, builder, value) : builder.conjunction();
    }

    public static Specification<FitaDeBordaEntity> largura(Double largura) {
        return (root, query, builder) -> {
            if (largura > 0.0) {
                return builder.equal(root.get(FitaDeBordaEntity_.largura), largura);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<FitaDeBordaEntity> descricao(String descricao) {
        return applyConditionIfNotNull(descricao,
                (root, builder, value) -> builder.like(builder.upper(root.get(FitaDeBordaEntity_.descricao)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<FitaDeBordaEntity> cor(String cor) {
        return applyConditionIfNotNull(cor,
                (root, builder, value) -> builder.like(builder.upper(root.get(FitaDeBordaEntity_.cor)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<FitaDeBordaEntity> intervaloPreco(Double precoMin, Double precoMax) {
        return (root, query, builder) -> {
            if (precoMin > 0.0 && precoMax > 0.0) {
                return builder.between(root.get(FitaDeBordaEntity_.preco), precoMin, precoMax);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<FitaDeBordaEntity> tipoAcabamento(String tipoAcabamento) {
        return applyConditionIfNotNull(tipoAcabamento,
                (root, builder, value) ->
                        builder.equal(builder.upper(root.get(FitaDeBordaEntity_.tipoAcabamento)),
                                value.toUpperCase()));
    }

    public static Specification<FitaDeBordaEntity> unidade(String unidade) {
        return applyConditionIfNotNull(unidade,
                (root, builder, value) -> builder.equal(root.get(FitaDeBordaEntity_.unidade), value));
    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    public static Specification<FitaDeBordaEntity> tipoPrecificacao(String precificacao) {
        return applyConditionIfNotNull(precificacao,
                (root, builder, value) ->
                        builder.equal(builder.upper(root.get(FitaDeBordaEntity_.precificacao)),
                                value.toUpperCase()));
    }
}
