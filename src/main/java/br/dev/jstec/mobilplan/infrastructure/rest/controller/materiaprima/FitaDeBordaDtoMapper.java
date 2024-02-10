package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;

import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.CriarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.FitaDeBordaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitaDeBordaDtoMapper {

    FitaDeBordaDto toDto(CriarFitaDeBordaUseCase.Output output);

    CriarFitaDeBordaUseCase.Input toInputModel(FitaDeBordaDto dto);
}
