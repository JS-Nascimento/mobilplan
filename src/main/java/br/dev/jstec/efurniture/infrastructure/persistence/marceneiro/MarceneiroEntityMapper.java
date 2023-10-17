package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarceneiroEntityMapper {

    Marceneiro mapToMarceneiro(MarceneiroEntity entity);
}
