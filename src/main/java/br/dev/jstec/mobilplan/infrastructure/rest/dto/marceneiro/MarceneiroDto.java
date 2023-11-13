package br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro;

import java.util.Collection;
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

    String logomarcaFilename;

    String logomarcaUrl;

    String createdBy;

    String createdAt;

    String updatedBy;

    String updatedAt;
}
