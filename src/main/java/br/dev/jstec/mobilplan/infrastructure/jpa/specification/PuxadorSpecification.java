package br.dev.jstec.mobilplan.infrastructure.jpa.specification;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.PuxadorEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.PuxadorEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class PuxadorSpecification {

    private PuxadorSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<PuxadorEntity> tenant(UUID tenantId) {
        if (tenantId == null) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        return (root, query, builder) -> builder.equal(root.get(PuxadorEntity_.tenantId),
                tenantId);
    }

    private static <T> Specification<PuxadorEntity> applyConditionIfNotNull(
            T value, TriFunction<Root<PuxadorEntity>, CriteriaBuilder, T, Predicate> condition) {
        return (root, query, builder) -> value != null ? condition.apply(root, builder, value) : builder.conjunction();
    }

    public static Specification<PuxadorEntity> perfil(Boolean perfil) {
        return (root, query, builder) -> {
            if (perfil != null) {
                return builder.equal(root.get(PuxadorEntity_.perfil), perfil);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<PuxadorEntity> tipoPuxador(String tipoPuxador) {
        return applyConditionIfNotNull(tipoPuxador,
                (root, builder, value) -> builder.equal(builder.upper(root.get(PuxadorEntity_.tipoPuxador)),
                        value.toUpperCase()));
    }

    public static Specification<PuxadorEntity> altura(Double altura) {
        return (root, query, builder) -> {
            if (altura > 0.0) {
                return builder.equal(root.get(PuxadorEntity_.altura), altura);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<PuxadorEntity> largura(Double largura) {
        return (root, query, builder) -> {
            if (largura > 0.0) {
                return builder.equal(root.get(PuxadorEntity_.largura), largura);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<PuxadorEntity> espessura(Double espessura) {
        return (root, query, builder) -> {
            if (espessura > 0.0) {
                return builder.equal(root.get(PuxadorEntity_.espessura), espessura);
            } else {
                return builder.conjunction();
            }
        };
    }


    public static Specification<PuxadorEntity> descricao(String descricao) {
        return applyConditionIfNotNull(descricao,
                (root, builder, value) -> builder.like(builder.upper(root.get(PuxadorEntity_.descricao)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<PuxadorEntity> cor(String cor) {
        return applyConditionIfNotNull(cor,
                (root, builder, value) -> builder.like(builder.upper(root.get(PuxadorEntity_.cor)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<PuxadorEntity> intervaloPreco(Double precoMin, Double precoMax) {
        return (root, query, builder) -> {
            if (precoMin > 0.0 && precoMax > 0.0) {
                return builder.between(root.get(PuxadorEntity_.preco), precoMin, precoMax);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<PuxadorEntity> unidade(String unidade) {
        return applyConditionIfNotNull(unidade,
                (root, builder, value) -> builder.equal(root.get(PuxadorEntity_.unidade), value));
    }

    public static Specification<PuxadorEntity> tipoPrecificacao(String precificacao) {
        return applyConditionIfNotNull(precificacao,
                (root, builder, value) ->
                        builder.equal(builder.upper(root.get(PuxadorEntity_.precificacao)),
                                value.toUpperCase()));
    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
