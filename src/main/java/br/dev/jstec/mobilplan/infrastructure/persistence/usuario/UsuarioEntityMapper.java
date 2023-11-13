package br.dev.jstec.mobilplan.infrastructure.persistence.usuario;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import br.dev.jstec.mobilplan.application.domain.usuario.Usuario;
import br.dev.jstec.mobilplan.application.domain.valueobject.Email;
import br.dev.jstec.mobilplan.application.domain.valueobject.Nome;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {

    @Mapping(source = "nome", target = "nome", qualifiedByName = "mapNome")
    @Mapping(source = "email", target = "email", qualifiedByName = "mapEmail")
    @Mapping(source = "situacao", target = "situacao")
    @Mapping(source = "tipoTelefone", target = "telefone.tipoTelefone",
        nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(source = "numero", target = "telefone.numero",
        nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(source = "ddd", target = "telefone.ddd",
        nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "senha", ignore = true)
    Usuario toUsuario(UsuarioEntity usuarioEntity);

    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "telefone.tipoTelefone", target = "tipoTelefone",
        nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(source = "telefone.numero", target = "numero",
        nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(source = "telefone.ddd", target = "ddd",
        nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(source = "telefone.ddi", target = "ddi",
        nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(source = "situacao", target = "situacao")
    @Mapping(source = "senha.value", target = "senha")
    UsuarioEntity toUsuarioEntity(Usuario usuario);

    @Named("mapNome")
    default Nome mapNome(String nome) {
        return new Nome(nome);
    }

    @Named("mapEmail")
    default Email mapEmail(String email) {
        return new Email(email);
    }
}
