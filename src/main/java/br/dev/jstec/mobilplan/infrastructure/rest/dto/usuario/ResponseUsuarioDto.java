package br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "nome",
        "situacao",
        "email",
        "emailConfirmado",
        "tipoTelefone",
        "ddi",
        "ddd",
        "numero"
})
public record ResponseUsuarioDto(
        String id,
        String nome,
        String situacao,
        String email,
        boolean emailConfirmado,
        String ddi,
        String ddd,
        @JsonProperty("telefone") String numero,
        @JsonProperty("tipo") String tipoTelefone,
        String avatarFilename,
        String avatarUrl,
        LocalDateTime createdAt,
        String updatedBy,
        LocalDateTime updatedAt) {

    public static ResponseUsuarioDto resumedOf(
            String id,
            String nome,
            String situacao,
            String email,
            boolean emailConfirmado) {

        return new ResponseUsuarioDto(
                id,
                nome,
                situacao,
                email,
                emailConfirmado,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
