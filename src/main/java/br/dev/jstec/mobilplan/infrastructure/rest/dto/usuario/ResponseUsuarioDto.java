package br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;

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
        @JsonInclude(NON_NULL)
        String ddi,
        @JsonInclude(NON_NULL)
        String ddd,
        @JsonInclude(NON_NULL)
        @JsonProperty("telefone") String numero,
        @JsonInclude(NON_NULL)
        @JsonProperty("tipo") String tipoTelefone,
        @JsonInclude(NON_NULL)
        String avatarFilename,
        @JsonInclude(NON_NULL)
        String avatarUrl,

        List<String> roles,

        @JsonInclude(NON_NULL)
        LocalDateTime createdAt,
        @JsonInclude(NON_NULL)
        String updatedBy,
        @JsonInclude(NON_NULL)
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
                List.of(),
                null,
                null,
                null);
    }
}
