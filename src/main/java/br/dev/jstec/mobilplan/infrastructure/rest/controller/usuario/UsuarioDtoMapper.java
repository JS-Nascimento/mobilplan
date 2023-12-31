package br.dev.jstec.mobilplan.infrastructure.rest.controller.usuario;

import br.dev.jstec.mobilplan.application.usecases.usuario.AlterarUsuarioUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.BuscarUsuarioPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.SalvarAvatarUseCase;
import br.dev.jstec.mobilplan.domain.TipoTelefone;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.TelefoneDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.AvatarUrlDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.NewUsuarioDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.RequestUsuarioDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.ResponseUsuarioDto;
import java.io.InputStream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UsuarioDtoMapper {

    ResponseUsuarioDto toResponseUsuarioDto(CriarUsuarioUseCase.Output output);

    @Mapping(source = "telefone.ddi", target = "ddi")
    @Mapping(source = "telefone.ddd", target = "ddd")
    @Mapping(source = "telefone.numero", target = "numero")
    @Mapping(source = "telefone.tipoTelefone", target = "tipoTelefone")
    ResponseUsuarioDto toUsuarioDto(BuscarUsuarioPorIdUseCase.Output output);

    @Mapping(source = "telefone.ddi", target = "ddi")
    @Mapping(source = "telefone.ddd", target = "ddd")
    @Mapping(source = "telefone.numero", target = "numero")
    @Mapping(source = "telefone.tipoTelefone", target = "tipoTelefone")
    ResponseUsuarioDto toUsuarioDto(AlterarUsuarioUseCase.Output output);

    CriarUsuarioUseCase.Input toCriarUsuarioUseCaseInput(NewUsuarioDto dto);

    AlterarUsuarioUseCase.Input toAlterarUsuarioUseCaseInput(RequestUsuarioDto dto);

    SalvarAvatarUseCase.Input toSalvarAvatarInput(String id, String tipoImagem, InputStream inputStream);

    AvatarUrlDto toAvatarUrlDto(SalvarAvatarUseCase.Output output);

    @Mapping(source = "tipoTelefone", target = "tipoTelefone", qualifiedByName = "toTipoTelefone")
    Telefone toTelefone(TelefoneDto telefoneDto);

    @Named("toTipoTelefone")
    default TipoTelefone toTipoTelefone(String tipoTelefone) {

        return TipoTelefone.of(tipoTelefone);
    }


}
