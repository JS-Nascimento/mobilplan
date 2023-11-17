package br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserEmailConfirmationDto(
    @JsonProperty("email") String email,
    @JsonProperty("nome") String nome
) {

}
