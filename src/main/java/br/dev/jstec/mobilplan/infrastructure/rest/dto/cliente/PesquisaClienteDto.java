package br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class PesquisaClienteDto {

    UUID id;
    Boolean ativo;
    String nome;
    String tipoPessoa;
    String email;
    Boolean notificarPorEmail;
    Boolean notificarPorWhatsapp;
}
