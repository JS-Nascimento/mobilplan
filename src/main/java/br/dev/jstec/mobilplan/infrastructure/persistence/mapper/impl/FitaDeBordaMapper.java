package br.dev.jstec.mobilplan.infrastructure.persistence.mapper.impl;

import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.FitaDeBordaEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IFitaDeBordaMapper;
import org.springframework.stereotype.Component;

@Component
public class FitaDeBordaMapper implements IFitaDeBordaMapper {

    public FitaDeBorda toModel(FitaDeBordaEntity entity) {

        return FitaDeBorda.with(
                entity.getId(),
                entity.getDescricao(),
                entity.getCor(),
                entity.getLargura(),
                entity.getPreco(),
                entity.getImagem(),
                entity.getTenantId(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    public FitaDeBordaEntity toEntity(FitaDeBorda model) {

        var entity = new FitaDeBordaEntity();
        entity.setId(model.getId());
        entity.setDescricao(model.getDescricao());
        entity.setCor(model.getCor());
        entity.setLargura(model.getLargura());
        entity.setUnidade(model.getUnidade().getDescricao());
        entity.setTipoAcabamento(model.getTipoAcabamento().toString());
        entity.setPreco(model.getPreco());
        entity.setPrecificacao(model.getPrecificacao().toString());
        entity.setImagem(model.getImagem());
        entity.setCriadoEm(model.getCriadoEm());
        entity.setAtualizadoEm(model.getAtualizadoEm());
        entity.setTenantId(model.getTenantId());

        return entity;
    }
}
