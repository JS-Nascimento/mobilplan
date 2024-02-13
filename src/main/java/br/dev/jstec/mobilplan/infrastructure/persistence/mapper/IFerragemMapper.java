package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.materiaprima.acessorios.Ferragem;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FerragemEntity;

public interface IFerragemMapper {

    Ferragem toModel(FerragemEntity entity);

    FerragemEntity toEntity(Ferragem model);
}
