package br.dev.jstec.mobilplan.infrastructure.jpa.specification;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.MdfEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.MdfEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class MdfSpecification {

    private MdfSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<MdfEntity> tenant(UUID tenantId) {
        if (tenantId == null) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        return (root, query, builder) -> builder.equal(root.get(MdfEntity_.tenantId),
                tenantId);
    }

    private static <T> Specification<MdfEntity> applyConditionIfNotNull(
            T value, TriFunction<Root<MdfEntity>, CriteriaBuilder, T, Predicate> condition) {
        return (root, query, builder) -> value != null ? condition.apply(root, builder, value) : builder.conjunction();
    }

    public static Specification<MdfEntity> altura(Double altura) {
        return (root, query, builder) -> {
            if (altura > 0.0) {
                return builder.equal(root.get(MdfEntity_.altura), altura);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<MdfEntity> largura(Double largura) {
        return (root, query, builder) -> {
            if (largura > 0.0) {
                return builder.equal(root.get(MdfEntity_.largura), largura);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<MdfEntity> espessura(Double espessura) {
        return (root, query, builder) -> {
            if (espessura > 0.0) {
                return builder.equal(root.get(MdfEntity_.espessura), espessura);
            } else {
                return builder.conjunction();
            }
        };
    }


    public static Specification<MdfEntity> descricao(String descricao) {
        return applyConditionIfNotNull(descricao,
                (root, builder, value) -> builder.like(builder.upper(root.get(MdfEntity_.descricao)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<MdfEntity> cor(String cor) {
        return applyConditionIfNotNull(cor,
                (root, builder, value) -> builder.like(builder.upper(root.get(MdfEntity_.cor)),
                        "%" + value.toUpperCase() + "%"));
    }

    public static Specification<MdfEntity> intervaloPreco(Double precoMin, Double precoMax) {
        return (root, query, builder) -> {
            if (precoMin > 0.0 && precoMax > 0.0) {
                return builder.between(root.get(MdfEntity_.preco), precoMin, precoMax);
            } else {
                return builder.conjunction();
            }
        };
    }

    public static Specification<MdfEntity> tipoAcabamento(String tipoAcabamento) {
        return applyConditionIfNotNull(tipoAcabamento,
                (root, builder, value) ->
                        builder.equal(builder.upper(root.get(MdfEntity_.tipoAcabamento)),
                                value.toUpperCase()));
    }

    public static Specification<MdfEntity> unidade(String unidade) {
        return applyConditionIfNotNull(unidade,
                (root, builder, value) -> builder.equal(root.get(MdfEntity_.unidade), value));
    }

    public static Specification<MdfEntity> tipoPrecificacao(String precificacao) {
        return applyConditionIfNotNull(precificacao,
                (root, builder, value) ->
                        builder.equal(builder.upper(root.get(MdfEntity_.precificacao)),
                                value.toUpperCase()));
    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}
