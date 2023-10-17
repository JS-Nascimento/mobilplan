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

@Mapper(componentModel = "spring")
interface MarceneiroMapper {

    @Mapping(target = "marceneiroId", expression = "java(marceneiro.getMarceneiroId().value())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documentoFiscal", target = "documentoFiscal",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "auditInfo.createdBy", target = "createdBy", nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "auditInfo.createdAt", target = "createdAt",
        dateFormat = "dd/MM/yyyy HH:mm:ss", nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "auditInfo.updatedBy", target = "updatedBy",
        defaultExpression = "java(defaultUpdateBy(marceneiro.getAuditInfo().updatedBy()))")
    @Mapping(source = "auditInfo.updatedAt", target = "updatedAt",
        dateFormat = "dd/MM/yyyy HH:mm:ss",
        defaultExpression = "java(defaultInstant(marceneiro.getAuditInfo().updatedAt()))")
    BuscarMarceneiroPorEmailUseCase.Output mapperToBuscaPorEmailOutput(Marceneiro marceneiro);

    @Mapping(target = "marceneiroId", expression = "java(marceneiro.getMarceneiroId().value())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documentoFiscal", target = "documentoFiscal",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "auditInfo.createdBy", target = "createdBy", nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "auditInfo.createdAt", target = "createdAt",
        dateFormat = "dd/MM/yyyy HH:mm:ss", nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "auditInfo.updatedBy", target = "updatedBy",
        defaultExpression = "java(defaultUpdateBy(marceneiro.getAuditInfo().updatedBy()))")
    @Mapping(source = "auditInfo.updatedAt", target = "updatedAt",
        dateFormat = "dd/MM/yyyy HH:mm:ss",
        defaultExpression = "java(defaultInstant(marceneiro.getAuditInfo().updatedAt()))")
    BuscarMarceneiroPorDocumentoUseCase.Output mapperToBuscaPorDocumentoOutput(
        Marceneiro marceneiro);

    @Mapping(target = "marceneiroId", expression = "java(marceneiro.getMarceneiroId().value())")
    @Mapping(source = "nome.value", target = "nome")
    @Mapping(source = "nomeComercial.value", target = "nomeComercial",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.tipoPessoa", target = "tipoPessoa",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "tipoCliente.documentoFiscal", target = "documentoFiscal",
        nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "auditInfo.createdBy", target = "createdBy", nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "auditInfo.createdAt", target = "createdAt",
        dateFormat = "dd/MM/yyyy HH:mm:ss", nullValueCheckStrategy = ALWAYS)
    @Mapping(source = "auditInfo.updatedBy", target = "updatedBy",
        defaultExpression = "java(defaultUpdateBy(marceneiro.getAuditInfo().updatedBy()))")
    @Mapping(source = "auditInfo.updatedAt", target = "updatedAt",
        dateFormat = "dd/MM/yyyy HH:mm:ss",
        defaultExpression = "java(defaultInstant(marceneiro.getAuditInfo().updatedAt()))")
    BuscarMarceneiroPorIdUseCase.Output mapperToBuscaPorIdOutput(
        Marceneiro marceneiro);

    default String defaultUpdateBy(UUID value) {
        return value != null ? value.toString() : EMPTY;
    }

    default String defaultInstant(LocalDateTime value) {

        if (isNull(value)) {
            return EMPTY;
        }
        var formatter = ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());
        return formatter.format(value);
    }
}
