package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import static java.util.Objects.isNull;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroId;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.domain.valueobject.Nome;
import br.dev.jstec.efurniture.application.domain.valueobject.NomeComercial;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface MarceneiroEntityMapper {

    @Mapping(source = "id", target = "marceneiroId")
    @Mapping(source = "situacao", target = "situacao")
    @Mapping(source = "nome", target = "nome", qualifiedByName = "mapNome")
    @Mapping(source = "nomeComercial", target = "nomeComercial", qualifiedByName = "mapNomeComercial")
    @Mapping(source = "email", target = "email", qualifiedByName = "mapEmail")
    @Mapping(source = "telefones", target = "telefones")
    @Mapping(source = "enderecos", target = "enderecos")
    Marceneiro mapToMarceneiro(MarceneiroEntity entity);

    @Mapping(target = "id", expression = "java(marceneiro.getMarceneiroId().value())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documento", target = "documento",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(target = "createdBy",
        expression = "java(marceneiro.getAuditInfo().createdBy())")
    @Mapping(target = "createdAt",
        expression = "java(toLocalDateTime(marceneiro.getAuditInfo().createdAt()))")
    @Mapping(target = "updatedBy",
        expression = "java(marceneiro.getAuditInfo().updatedBy())")
    @Mapping(target = "updatedAt",
        expression = "java(toLocalDateTime(marceneiro.getAuditInfo().updatedAt()))")
    MarceneiroEntity mapToMarceneiroEntity(Marceneiro marceneiro);

    default LocalDateTime toLocalDateTime(LocalDateTime value) {

        if (isNull(value)) {

            return null;
        }

        return value.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    default MarceneiroId mapMarceneiroId(String marceneiroId) {

        return new MarceneiroId(marceneiroId);
    }

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
