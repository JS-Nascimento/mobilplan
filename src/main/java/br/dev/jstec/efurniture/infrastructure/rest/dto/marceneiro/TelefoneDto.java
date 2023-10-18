package br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TelefoneDto(

    @JsonIgnore
    Long id,
    String ddi,
    String ddd,
    String numero,
    @JsonProperty("tipo") String tipoTelefone) {

}
