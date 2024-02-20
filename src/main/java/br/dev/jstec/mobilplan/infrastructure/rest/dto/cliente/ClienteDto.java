package br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDto {

    UUID id;
    boolean ativo;
    String nome;
    String tipoPessoa;
    String email;
    String documentoFiscal;
    String documentoIdentificador;
    String estadoCivil;
    boolean notificarPorEmail;
    boolean notificarPorWhatsapp;
    List<ClienteTelefonesDto> telefones;
    List<ClienteEnderecosDto> enderecos;
    LocalDateTime criadoEm;
    LocalDateTime atualizadoEm;
    UUID tenantId;
}
