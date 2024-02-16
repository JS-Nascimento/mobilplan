package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;


import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.CriarMdfUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.MdfDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MdfDtoMapper {

    MdfDto toDto(CriarMdfUseCase.Output output);

    CriarMdfUseCase.Input toInsertInputModel(MdfDto dto);

}
