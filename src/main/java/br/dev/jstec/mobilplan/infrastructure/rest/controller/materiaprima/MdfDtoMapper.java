package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;


import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.AtualizarMdfUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.BuscarMdfPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.BuscarMdfPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.CriarMdfUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.RemoverMdfPorIdUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.MdfDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PesquisaMateriaPrimaDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MdfDtoMapper {

    MdfDto toDto(CriarMdfUseCase.Output output);

    MdfDto toDto(AtualizarMdfUseCase.Output output);

    MdfDto toDto(BuscarMdfPorIdUseCase.Output output);

    List<MdfDto> toDto(List<BuscarMdfPorCriteriosUseCase.Output> output);

    CriarMdfUseCase.Input toInsertInputModel(MdfDto dto);

    AtualizarMdfUseCase.Input toUpdateInputModel(MdfDto dto);

    RemoverMdfPorIdUseCase.Input toDeleteInputModel(Long id);

    BuscarMdfPorCriteriosUseCase.Input toInputCriterios(PesquisaMateriaPrimaDto dto);


}
