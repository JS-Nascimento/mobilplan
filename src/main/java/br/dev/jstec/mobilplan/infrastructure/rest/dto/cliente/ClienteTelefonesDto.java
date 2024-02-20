package br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteTelefonesDto {
    @JsonProperty("tipo")
    String tipoTelefone;
    String numero;
    String ddd;
    String ddi;
}
