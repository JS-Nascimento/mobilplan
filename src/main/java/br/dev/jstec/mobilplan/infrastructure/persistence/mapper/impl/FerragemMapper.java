package br.dev.jstec.mobilplan.infrastructure.persistence.mapper.impl;

import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.FerragemEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IFerragemMapper;
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
                entity.getImagem(),
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
        entity.setImagem(model.getImagem());
        entity.setTenantId(model.getTenantId());

        return entity;
    }
}
