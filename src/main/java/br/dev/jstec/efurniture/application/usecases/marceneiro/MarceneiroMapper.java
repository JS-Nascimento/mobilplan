package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MarceneiroMapper {

    @Mapping(target = "id", expression = "java(marceneiro.getId().toString())")
    @Mapping(target = "situacao", expression = "java(marceneiro.getSituacao().getDescricao())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documento", target = "documento",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "defaultBy")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    @Mapping(source = "updatedBy", target = "updatedBy", qualifiedByName = "defaultBy")
    BuscarMarceneiroPorEmailUseCase.Output toBuscarMarceneiroPorEmailOutput(Marceneiro marceneiro);

    @Mapping(target = "id", expression = "java(marceneiro.getId().toString())")
    @Mapping(target = "situacao", expression = "java(marceneiro.getSituacao().getDescricao())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documento", target = "documento",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "defaultBy")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    @Mapping(target = "updatedBy", qualifiedByName = "defaultBy")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    BuscarMarceneiroPorDocumentoUseCase.Output toBuscarMarceneiroPorDocumentoOutput(
        Marceneiro marceneiro);

    @Mapping(target = "id", expression = "java(marceneiro.getId().toString())")
    @Mapping(target = "situacao", expression = "java(marceneiro.getSituacao().getDescricao())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documento", target = "documento",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "defaultBy")
    @Mapping(source = "updatedBy", target = "updatedBy", qualifiedByName = "defaultBy")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    BuscarMarceneiroPorIdUseCase.Output toBuscarMarceneiroPorIdOutput(
        Marceneiro marceneiro);

    @Mapping(target = "id", expression = "java(marceneiro.getId().toString())")
    @Mapping(target = "situacao", expression = "java(marceneiro.getSituacao().getDescricao())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documento", target = "documento",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "updatedBy", target = "updatedBy", qualifiedByName = "defaultBy")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "defaultBy")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    BuscarTodosMarceneirosUseCase.Output toBuscarTodosMarceneirosOutput(
        Marceneiro marceneiro);

    @Mapping(target = "id", expression = "java(marceneiro.getId().toString())")
    @Mapping(target = "situacao", expression = "java(marceneiro.getSituacao().getDescricao())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial")
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa")
    @Mapping(source = "tipoCliente.documento", target = "documento")
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "defaultBy")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    @Mapping(source = "updatedBy", target = "updatedBy", qualifiedByName = "defaultBy")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss",
        qualifiedByName = "defaultInstant")
    AlterarMarceneiroUseCase.Output toAlterarMarceneiroOutput(
        Marceneiro marceneiro);

    @Named("defaultBy")
    default String defaultBy(UUID value) {
        return value != null ? value.toString() : EMPTY;
    }

    @Named("defaultInstant")
    default String defaultInstant(LocalDateTime value) {

        if (isNull(value)) {
            return EMPTY;
        }
        var formatter = ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());
        return formatter.format(value);
    }
}
