package br.dev.jstec.mobilplan.infrastructure.rest.controller.marceneiro;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarMarceneiroUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSitucaoUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarTodosMarceneirosUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.CriarMarceneiroUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.SalvarLogomarcaUseCase;
import br.dev.jstec.mobilplan.domain.TipoTelefone;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.EnderecoDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.MarceneiroDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.NewMarceneiroDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.ResponseMarceneiroDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.TelefoneDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.UpdateMarceneiroDto;
import java.io.InputStream;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MarceneiroDtoMapper {
    MarceneiroDto toMarceneiroDto(BuscarMarceneiroPorIdUseCase.Output output);

    MarceneiroDto toMarceneiroDto(BuscarMarceneiroPorEmailUseCase.Output output);

    MarceneiroDto toMarceneiroDto(BuscarMarceneiroPorDocumentoUseCase.Output output);

    MarceneiroDto toMarceneiroDto(BuscarTodosMarceneirosUseCase.Output output);

    MarceneiroDto toMarceneiroDto(AlterarMarceneiroUseCase.Output output);

    @Mapping(source = "id", target = "id", nullValueCheckStrategy = ALWAYS)
    ResponseMarceneiroDto toResponseMarceneiroDto(CriarMarceneiroUseCase.Output output);

    ResponseMarceneiroDto toResponseMarceneiroDto(AlterarSitucaoUseCase.Output output);

    CriarMarceneiroUseCase.Input toCriarMarceneiroInput(NewMarceneiroDto newMarceneiroDto);

    AlterarMarceneiroUseCase.Input toAlterarMarceneiroInput(UpdateMarceneiroDto updateMarceneiroDto);

    @Mapping(source = "tipoTelefone", target = "tipoTelefone", qualifiedByName = "toTipoTelefone")
    Telefone toTelefone(TelefoneDto telefoneDto);

    Endereco toEndereco(EnderecoDto enderecoDto);

    List<Telefone> toTelefoneList(List<TelefoneDto> telefoneDtoList);

    List<Endereco> toEnderecoList(List<EnderecoDto> enderecoDtoList);

    SalvarLogomarcaUseCase.Input toSalvarLogomarcaInput(String id, String tipoImagem, InputStream inputStream);

    @Named("toTipoTelefone")
    default TipoTelefone toTipoTelefone(String tipoTelefone) {

        return TipoTelefone.of(tipoTelefone);
    }
}
