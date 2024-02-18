package br.dev.jstec.mobilplan.infrastructure.persistence.entity.marceneiro;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import br.dev.jstec.mobilplan.domain.model.marceneiro.Marceneiro;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.domain.valueobject.Nome;
import br.dev.jstec.mobilplan.domain.valueobject.NomeComercial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", nullValueMappingStrategy = RETURN_NULL)
public interface MarceneiroEntityMapper {

    @Mapping(source = "situacao", target = "situacao")
    @Mapping(source = "nome", target = "nome", qualifiedByName = "mapNome")
    @Mapping(source = "nomeComercial", target = "nomeComercial", qualifiedByName = "mapNomeComercial")
    @Mapping(source = "email", target = "email", qualifiedByName = "mapEmail")
    @Mapping(source = "tipoPessoa", target = "tipoCliente.tipoPessoa")
    @Mapping(source = "documento", target = "tipoCliente.documento")
    @Mapping(source = "telefones", target = "telefones")
    @Mapping(source = "enderecos", target = "enderecos")
    Marceneiro toMarceneiro(MarceneiroEntity entity);

    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documento", target = "documento",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "telefones", target = "telefones")
    @Mapping(source = "enderecos", target = "enderecos")
    MarceneiroEntity toMarceneiroEntity(Marceneiro marceneiro);

    @Named("mapNome")
    default Nome mapNome(String nome) {
        return new Nome(nome);
    }

    @Named("mapNomeComercial")
    default NomeComercial mapNomeComercial(String nomeComercial) {
        return new NomeComercial(nomeComercial);
    }

    @Named("mapEmail")
    default Email mapEmail(String email) {
        return new Email(email);
    }
}
