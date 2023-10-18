package br.dev.jstec.efurniture.infrastructure.rest.controller.marceneiro;

import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro.MarceneiroDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarceneiroDtoMapper {

    @Mapping(target = "id", expression = "java(output.marceneiroId())")
    @Mapping(target = "situacao", expression = "java(output.situacao())")
    MarceneiroDto mapToMarceneiroDto(BuscarMarceneiroPorIdUseCase.Output output);

    @Mapping(target = "id", expression = "java(output.marceneiroId())")
    @Mapping(target = "situacao", expression = "java(output.situacao())")
    MarceneiroDto mapToMarceneiroDto(BuscarMarceneiroPorEmailUseCase.Output output);

    @Mapping(target = "id", expression = "java(output.marceneiroId())")
    @Mapping(target = "situacao", expression = "java(output.situacao())")
    MarceneiroDto mapToMarceneiroDto(BuscarMarceneiroPorDocumentoUseCase.Output output);
}
