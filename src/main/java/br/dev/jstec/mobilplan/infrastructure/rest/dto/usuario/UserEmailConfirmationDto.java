package br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserEmailConfirmationDto(
    @JsonProperty("id") String id,
    @JsonProperty("email") String email,
    @JsonProperty("nome") String nome,
    @JsonProperty("codigo_confirmacao") String codigoConfirmacao
) {

}
