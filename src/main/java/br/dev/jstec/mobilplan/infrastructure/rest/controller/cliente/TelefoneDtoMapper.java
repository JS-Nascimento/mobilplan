package br.dev.jstec.mobilplan.infrastructure.rest.controller.cliente;

import br.dev.jstec.mobilplan.application.usecases.cliente.AlterarClienteUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.CriarClienteUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente.ClienteTelefonesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TelefoneDtoMapper {


    BuscarClientePorCriteriosUseCase.TelefoneUseCase toPesquisaInputModel(ClienteTelefonesDto dto);

    ClienteTelefonesDto pesquisaOutputModelToDto(BuscarClientePorCriteriosUseCase.TelefoneUseCase output);

    CriarClienteUseCase.TelefoneUseCase toInsertInputModel(ClienteTelefonesDto dto);

    ClienteTelefonesDto insertOutputModelToDto(CriarClienteUseCase.TelefoneUseCase output);

    AlterarClienteUseCase.TelefoneUseCase toUpdateInputModel(ClienteTelefonesDto dto);

    ClienteTelefonesDto updateOutputModelToDto(AlterarClienteUseCase.TelefoneUseCase output);

    ClienteTelefonesDto buscarPorIdOutputModelToDto(BuscarClientePorIdUseCase.TelefoneUseCase output);

}
