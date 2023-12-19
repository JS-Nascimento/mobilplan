package br.dev.jstec.mobilplan.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TelefoneDto(
    Long id,
    String ddi,
    String ddd,
    String numero,
    @JsonProperty("tipo") String tipoTelefone) {

}
