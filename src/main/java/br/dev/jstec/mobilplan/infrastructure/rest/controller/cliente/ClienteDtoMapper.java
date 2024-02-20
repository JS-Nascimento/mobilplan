package br.dev.jstec.mobilplan.infrastructure.rest.controller.cliente;

import br.dev.jstec.mobilplan.application.usecases.cliente.AlterarClienteUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.CriarClienteUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.RemoverClienteUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente.ClienteDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente.PesquisaClienteDto;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TelefoneDtoMapper.class, EnderecoDtoMapper.class})
public interface ClienteDtoMapper {

    ClienteDto toDto(CriarClienteUseCase.Output output);

    ClienteDto toDto(AlterarClienteUseCase.Output output);

    ClienteDto toDto(BuscarClientePorIdUseCase.Output output);

    List<ClienteDto> toDto(List<BuscarClientePorCriteriosUseCase.Output> output);

    CriarClienteUseCase.Input toInsertInputModel(ClienteDto dto);

    AlterarClienteUseCase.Input toUpdateInputModel(ClienteDto dto);

    RemoverClienteUseCase.Input toDeleteInputModel(UUID id);

    BuscarClientePorCriteriosUseCase.Input toInputCriterios(PesquisaClienteDto dto);
}
