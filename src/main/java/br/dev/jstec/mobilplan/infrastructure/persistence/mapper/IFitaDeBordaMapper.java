package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima.FitaDeBordaEntity;

public interface IFitaDeBordaMapper {

    FitaDeBorda toModel(FitaDeBordaEntity entity);

    FitaDeBordaEntity toEntity(FitaDeBorda model);
}
