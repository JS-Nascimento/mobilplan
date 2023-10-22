package br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro;

import java.util.List;

public record NewMarceneiroDto(String nome,
                               String nomeComercial,
                               String tipoPessoa,
                               String documento,
                               String email,
                               List<TelefoneDto> telefones,
                               List<EnderecoDto> enderecos) {

}
