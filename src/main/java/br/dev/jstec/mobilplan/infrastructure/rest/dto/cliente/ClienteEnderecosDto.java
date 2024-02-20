package br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteEnderecosDto {
    String cep;
    String logradouro;
    String numero;
    String complemento;
    String bairro;
    String cidade;
    String uf;
}
