package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.PuxadorEntity;

public interface IPuxadorMapper {

    Puxador toModel(PuxadorEntity entity);

    PuxadorEntity toEntity(Puxador model);
}
