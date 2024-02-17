package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;


import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.AtualizarPuxadorUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.BuscarPuxadorPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.BuscarPuxadorPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.CriarPuxadorUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.RemoverPuxadorPorIdUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PesquisaMateriaPrimaDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PuxadorDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PuxadorDtoMapper {

    PuxadorDto toDto(CriarPuxadorUseCase.Output output);

    PuxadorDto toDto(AtualizarPuxadorUseCase.Output output);

    PuxadorDto toDto(BuscarPuxadorPorIdUseCase.Output output);

    List<PuxadorDto> toDto(List<BuscarPuxadorPorCriteriosUseCase.Output> output);

    CriarPuxadorUseCase.Input toInsertInputModel(PuxadorDto dto);

    AtualizarPuxadorUseCase.Input toUpdateInputModel(PuxadorDto dto);

    RemoverPuxadorPorIdUseCase.Input toDeleteInputModel(Long id);

    BuscarPuxadorPorCriteriosUseCase.Input toInputCriterios(PesquisaMateriaPrimaDto dto);

}
