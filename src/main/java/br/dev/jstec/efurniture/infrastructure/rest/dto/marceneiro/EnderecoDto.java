package br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record EnderecoDto(

    @JsonIgnore
    Long id,
    String cep,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String uf) {

}
