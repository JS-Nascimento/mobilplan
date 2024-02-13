package br.dev.jstec.mobilplan.infrastructure.persistence.mapper.impl;

import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.Mdf;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IMdfMapper;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.MdfEntity;
import org.springframework.stereotype.Component;

@Component
public class MdfMapper implements IMdfMapper {

    public Mdf toModel(MdfEntity entity) {

        return Mdf.with(
                entity.getId(),
                entity.getDescricao(),
                entity.getCor(),
                entity.getCalculaPorLado(),
                entity.getAltura(),
                entity.getLargura(),
                entity.getEspessura(),
                entity.getPrecificacao(),
                entity.getPreco(),
                entity.getTenantId(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    public MdfEntity toEntity(Mdf model) {

        var entity = new MdfEntity();
        entity.setId(model.getId());
        entity.setDescricao(model.getDescricao());
        entity.setCor(model.getCor());
        entity.setCalculaPorLado(model.getCalculaPorLado().toString());
        entity.setEspessura(model.getDimensoesChapa().getAltura());
        entity.setLargura(model.getDimensoesChapa().getLargura());
        entity.setEspessura(model.getDimensoesChapa().getEspessura());
        entity.setUnidade(model.getUnidade().getDescricao());
        entity.setTipoAcabamento(model.getTipoAcabamento().toString());
        entity.setPreco(model.getPreco());
        entity.setPrecificacao(model.getPrecificacao().toString());
        entity.setCriadoEm(model.getCriadoEm());
        entity.setAtualizadoEm(model.getAtualizadoEm());
        entity.setTenantId(model.getTenantId());

        return entity;
    }
}
