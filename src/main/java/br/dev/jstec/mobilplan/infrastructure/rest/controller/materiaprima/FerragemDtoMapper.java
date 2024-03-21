package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;


import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.AtualizarFerragemUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.BuscarFerragemPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.BuscarFerragemPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.CriarFerragemUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.ImportarFerragensEmLoteUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.RemoverFerragemPorIdUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.FerragemDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.ImportarEmLoteResponseDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PesquisaMateriaPrimaDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FerragemDtoMapper {

    FerragemDto toDto(CriarFerragemUseCase.Output output);

    FerragemDto toDto(AtualizarFerragemUseCase.Output output);

    FerragemDto toDto(BuscarFerragemPorIdUseCase.Output output);

    List<FerragemDto> toDto(List<BuscarFerragemPorCriteriosUseCase.Output> output);

    CriarFerragemUseCase.Input toInsertInputModel(FerragemDto dto);

    AtualizarFerragemUseCase.Input toUpdateInputModel(FerragemDto dto);

    RemoverFerragemPorIdUseCase.Input toDeleteInputModel(Long id);

    BuscarFerragemPorCriteriosUseCase.Input toInputCriterios(PesquisaMateriaPrimaDto dto);

    ImportarEmLoteResponseDto toImportarEmLoteResponseDto(ImportarFerragensEmLoteUseCase.Output output);

}
