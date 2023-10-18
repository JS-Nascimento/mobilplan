package br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class MarceneiroDto {

    String id;

    String situacao;

    String nome;

    String nomeComercial;

    String tipoPessoa;

    String documento;

    String email;

    Collection<TelefoneDto> telefones;

    Collection<EnderecoDto> enderecos;

    UUID createdBy;

    LocalDateTime createdAt;

    UUID updatedBy;

    LocalDateTime updatedAt;
}
