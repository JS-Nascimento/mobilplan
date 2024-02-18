package br.dev.jstec.mobilplan.application.usecases.usuario;

import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.domain.model.usuario.Usuario;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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

    @Mapping(target = "id", expression = "java(usuario.getId().toString())")
    @Mapping(target = "situacao", expression = "java(usuario.getSituacao().getDescricao())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "email.value", target = "email")
    @Mapping(target = "telefone.tipoTelefone")
    @Mapping(target = "telefone.numero")
    @Mapping(target = "telefone.ddd")
    @Mapping(target = "telefone.ddi")
    @Mapping(target = "telefone.id")
    @Mapping(target = "emailConfirmado")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "stringToLocalDateTime")
    AlterarUsuarioUseCase.Output toAlterarUsuarioOutput(Usuario usuario);


    @Mapping(target = "id", expression = "java(usuario.getId().toString())")
    @Mapping(target = "situacao", expression = "java(usuario.getSituacao().getDescricao())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "email.value", target = "email")
    @Mapping(target = "telefone.tipoTelefone")
    @Mapping(target = "telefone.numero")
    @Mapping(target = "telefone.ddd")
    @Mapping(target = "telefone.ddi")
    @Mapping(target = "telefone.id")
    @Mapping(target = "emailConfirmado")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "stringToLocalDateTime")
    BuscarUsuarioPorIdUseCase.Output toBuscaUsuarioPorIdOutPut(Usuario usuario);

    @Named("stringToLocalDateTime")
    default String stringToLocalDateTime(LocalDateTime dateTimeStr) {

        if (isNull(dateTimeStr)) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(dateTimeStr.toString(), formatter).toString();
    }
}
