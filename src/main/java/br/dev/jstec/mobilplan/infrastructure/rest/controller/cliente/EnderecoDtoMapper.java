package br.dev.jstec.mobilplan.infrastructure.rest.controller.cliente;

import br.dev.jstec.mobilplan.application.usecases.cliente.AlterarClienteUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.CriarClienteUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente.ClienteEnderecosDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoDtoMapper {


    BuscarClientePorCriteriosUseCase.EnderecoUseCase toPesquisaInputModel(ClienteEnderecosDto dto);

    ClienteEnderecosDto pesquisaOutputModelToDto(BuscarClientePorCriteriosUseCase.EnderecoUseCase output);

    CriarClienteUseCase.EnderecoUseCase toInsertInputModel(ClienteEnderecosDto dto);

    ClienteEnderecosDto insertOutputModelToDto(CriarClienteUseCase.EnderecoUseCase output);

    AlterarClienteUseCase.EnderecoUseCase toUpdateInputModel(ClienteEnderecosDto dto);

    ClienteEnderecosDto updateOutputModelToDto(AlterarClienteUseCase.EnderecoUseCase output);

    ClienteEnderecosDto buscarPorIdOutputModelToDto(BuscarClientePorIdUseCase.EnderecoUseCase output);

}
