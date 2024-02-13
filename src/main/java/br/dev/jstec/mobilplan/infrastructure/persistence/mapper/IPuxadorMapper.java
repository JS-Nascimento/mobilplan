package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.materiaprima.acessorios.Puxador;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.PuxadorEntity;

public interface IPuxadorMapper {

    Puxador toModel(PuxadorEntity entity);

    PuxadorEntity toEntity(Puxador model);
}
