package br.dev.jstec.mobilplan.application.usecases.usuario;

import br.dev.jstec.mobilplan.application.domain.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", expression = "java(usuario.getId().toString())")
    @Mapping(target = "situacao", expression = "java(usuario.getSituacao().getDescricao())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "email.value", target = "email")
    @Mapping(target = "telefone.tipoTelefone", ignore = true)
    @Mapping(target = "telefone.numero", ignore = true)
    @Mapping(target = "telefone.ddd", ignore = true)
    @Mapping(target = "telefone.ddi", ignore = true)
    CriarUsuarioUseCase.Output toCriarUsuarioOutput(Usuario usuario);
}
