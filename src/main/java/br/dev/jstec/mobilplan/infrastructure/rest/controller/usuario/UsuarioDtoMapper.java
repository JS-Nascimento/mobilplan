package br.dev.jstec.mobilplan.infrastructure.rest.controller.usuario;

import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.NewUsuarioDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.ResponseUsuarioDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioDtoMapper {

    ResponseUsuarioDto toResponseUsuarioDto(CriarUsuarioUseCase.Output output);

    CriarUsuarioUseCase.Input toCriarUsuarioUseCaseInput(NewUsuarioDto dto);

}
