package br.dev.jstec.mobilplan.infrastructure.persistence.mapper.impl;

import br.dev.jstec.mobilplan.domain.materiaprima.acessorios.Ferragem;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IFerragemMapper;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FerragemEntity;
import org.springframework.stereotype.Component;

@Component
public class FerragemMapper implements IFerragemMapper {

    @Override
    public Ferragem toModel(FerragemEntity entity) {
        return Ferragem.with(
                entity.getId(),
                entity.getDescricao(),
                entity.getCor(),
                entity.getUnidade(),
                entity.getPreco(),
                entity.getPrecificacao(),
                entity.getTenantId(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm()
        );
    }

    @Override
    public FerragemEntity toEntity(Ferragem model) {

        var entity = new FerragemEntity();
        entity.setId(model.getId());
        entity.setDescricao(model.getDescricao());
        entity.setCor(model.getCor());
        entity.setUnidade(model.getUnidade().name());
        entity.setPreco(model.getPreco());
        entity.setPrecificacao(model.getPrecificacao().name());
        entity.setTenantId(model.getTenantId());

        return entity;
    }
}
