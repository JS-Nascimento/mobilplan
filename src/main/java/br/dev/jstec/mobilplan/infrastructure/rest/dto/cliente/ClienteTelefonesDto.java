package br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteTelefonesDto {

    String tipoTelefone;
    String numero;
    String ddd;
    String ddi;
}
