package br.dev.jstec.mobilplan.infrastructure.jpa.specification;

import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FitaDeBordaEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FitaDeBordaEntity_;
import org.springframework.data.jpa.domain.Specification;

public final class FitaDeBordaSpecification {

    private FitaDeBordaSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<FitaDeBordaEntity> descricaoContains(String descricao) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(FitaDeBordaEntity_.descricao)),
                        "%" + descricao.toLowerCase() + "%");
    }

    public static Specification<FitaDeBordaEntity> corEquals(String cor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get(FitaDeBordaEntity_.cor)), cor.toLowerCase());
    }

    public static Specification<FitaDeBordaEntity> larguraEquals(Double largura) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(FitaDeBordaEntity_.largura), largura);
    }

    public static Specification<FitaDeBordaEntity> precoBetween(Double precoMin, Double precoMax) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(FitaDeBordaEntity_.preco), precoMin, precoMax);
    }

    public static Specification<FitaDeBordaEntity> unidadeEquals(String unidade) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get(FitaDeBordaEntity_.unidade)),
                        unidade.toLowerCase());
    }

    public static Specification<FitaDeBordaEntity> tipoAcabamentoEquals(String tipoAcabamento) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get(FitaDeBordaEntity_.tipoAcabamento)),
                        tipoAcabamento.toLowerCase());
    }

    public static Specification<FitaDeBordaEntity> tipoPrecificacaoEquals(String tipoPrecificacao) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get(FitaDeBordaEntity_.tipoPrecificacao)),
                        tipoPrecificacao.toLowerCase());
    }
}
