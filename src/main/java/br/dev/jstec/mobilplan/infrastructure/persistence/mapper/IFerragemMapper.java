package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.FerragemEntity;

public interface IFerragemMapper {

    Ferragem toModel(FerragemEntity entity);

    FerragemEntity toEntity(Ferragem model);
}
