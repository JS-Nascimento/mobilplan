package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;

import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.AtualizarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.BuscarFitaDeBordaPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.BuscarFitaDeBordaPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.CriarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.RemoverFitaDeBordaPorIdUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.FitaDeBordaDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PesquisaFitaDeBordaDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitaDeBordaDtoMapper {

    FitaDeBordaDto toDto(CriarFitaDeBordaUseCase.Output output);

    FitaDeBordaDto toDto(AtualizarFitaDeBordaUseCase.Output output);

    FitaDeBordaDto toDto(BuscarFitaDeBordaPorIdUseCase.Output output);

    List<FitaDeBordaDto> toDto(List<BuscarFitaDeBordaPorCriteriosUseCase.Output> output);

    CriarFitaDeBordaUseCase.Input toInsertInputModel(FitaDeBordaDto dto);

    AtualizarFitaDeBordaUseCase.Input toUpdateInputModel(FitaDeBordaDto dto);

    RemoverFitaDeBordaPorIdUseCase.Input toDeleteInputModel(Long id);

    BuscarFitaDeBordaPorCriteriosUseCase.Input toInputCriterios(PesquisaFitaDeBordaDto dto);
}
