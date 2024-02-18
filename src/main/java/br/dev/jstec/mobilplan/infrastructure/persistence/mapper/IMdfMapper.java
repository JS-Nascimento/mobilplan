package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.MdfEntity;

public interface IMdfMapper {

    Mdf toModel(MdfEntity entity);

    MdfEntity toEntity(Mdf model);
}
