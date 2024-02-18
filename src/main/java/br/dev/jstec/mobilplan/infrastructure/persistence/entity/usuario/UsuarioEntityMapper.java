package br.dev.jstec.mobilplan.infrastructure.persistence.entity.usuario;

import static org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_NULL;

import br.dev.jstec.mobilplan.domain.model.usuario.Usuario;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.domain.valueobject.Nome;
import br.dev.jstec.mobilplan.domain.valueobject.Senha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {

    @Mapping(source = "nome", target = "nome", qualifiedByName = "mapNome")
    @Mapping(source = "email", target = "email", qualifiedByName = "mapEmail")
    @Mapping(source = "situacao", target = "situacao")
    @Mapping(source = "ddi", target = "telefone.ddi",
            nullValuePropertyMappingStrategy = SET_TO_NULL)
    @Mapping(source = "tipoTelefone", target = "telefone.tipoTelefone",
            nullValuePropertyMappingStrategy = SET_TO_NULL)
    @Mapping(source = "numero", target = "telefone.numero",
            nullValuePropertyMappingStrategy = SET_TO_NULL)
    @Mapping(source = "ddd", target = "telefone.ddd",
            nullValuePropertyMappingStrategy = SET_TO_NULL)
    @Mapping(target = "senha", source = "senha", qualifiedByName = "stringToSenha")
    Usuario toUsuario(UsuarioEntity usuarioEntity);

    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "senha.value", target = "senha")
    @Mapping(source = "telefone.tipoTelefone", target = "tipoTelefone")
    @Mapping(source = "telefone.numero", target = "numero")
    @Mapping(source = "telefone.ddd", target = "ddd")
    @Mapping(source = "telefone.ddi", target = "ddi")
    @Mapping(source = "situacao", target = "situacao")
    @Mapping(source = "avatarUrl", target = "avatarUrl")
    @Mapping(source = "avatarFilename", target = "avatarFilename")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "roles", target = "roles")
    UsuarioEntity toUsuarioEntity(Usuario usuario);

    @Named("mapNome")
    default Nome mapNome(String nome) {
        return new Nome(nome);
    }

    @Named("mapEmail")
    default Email mapEmail(String email) {
        return new Email(email);
    }

    @Named("stringToSenha")
    default Senha stringToSenha(String value) {
        return (value != null) ? Senha.ofHashed(value) : null;
    }
}
