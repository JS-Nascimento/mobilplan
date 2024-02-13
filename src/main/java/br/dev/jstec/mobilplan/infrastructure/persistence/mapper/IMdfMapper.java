package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.Mdf;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.MdfEntity;

public interface IMdfMapper {

    Mdf toModel(MdfEntity entity);

    MdfEntity toEntity(Mdf model);
}
