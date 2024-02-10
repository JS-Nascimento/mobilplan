package br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima;

import static java.time.LocalDateTime.now;

import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FitaDeBordaMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    FitaDeBordaEntity toEntity(FitaDeBorda model);

    FitaDeBorda toModel(FitaDeBordaEntity entity);

    @AfterMapping
    default void setTimestamps(@MappingTarget FitaDeBordaEntity entity, FitaDeBorda model) {

        if (entity.getId() == null) {
            entity.setCriadoEm(now());
        }
        entity.setAtualizadoEm(now());
    }
}
