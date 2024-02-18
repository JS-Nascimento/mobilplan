package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.materiaprima.FitaDeBordaEntity;

public interface IFitaDeBordaMapper {

    FitaDeBorda toModel(FitaDeBordaEntity entity);

    FitaDeBordaEntity toEntity(FitaDeBorda model);
}
