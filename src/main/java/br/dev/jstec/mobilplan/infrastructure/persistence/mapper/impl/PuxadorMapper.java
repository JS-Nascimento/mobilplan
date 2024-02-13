package br.dev.jstec.mobilplan.infrastructure.persistence.mapper.impl;

import br.dev.jstec.mobilplan.domain.materiaprima.acessorios.Puxador;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IPuxadorMapper;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.PuxadorEntity;
import org.springframework.stereotype.Component;

@Component
public class PuxadorMapper implements IPuxadorMapper {

    @Override
    public Puxador toModel(PuxadorEntity entity) {
        return Puxador.with(
                entity.getId(),
                entity.isPerfil(),
                entity.getTipoPuxador(),
                entity.getDescricao(),
                entity.getCor(),
                entity.getDirecao(),
                entity.getPreco(),
                entity.getAltura(),
                entity.getLargura(),
                entity.getEspessura(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm(),
                entity.getTenantId()
        );
    }

    @Override
    public PuxadorEntity toEntity(Puxador model) {

        var entity = new PuxadorEntity();
        entity.setId(model.getId());
        entity.setPerfil(model.isPerfil());
        entity.setTipoPuxador(model.getTipoPuxador().toString());
        entity.setDescricao(model.getDescricao());
        entity.setCor(model.getCor());
        entity.setUnidade(model.getUnidade().toString());
        entity.setPreco(model.getPreco());
        entity.setDirecao(model.getDirecao().toString());
        entity.setAltura(model.getDimensoesAcessorio().getAltura());
        entity.setLargura(model.getDimensoesAcessorio().getLargura());
        entity.setEspessura(model.getDimensoesAcessorio().getEspessura());
        entity.setTenantId(model.getTenantId());
        return entity;
    }
}
