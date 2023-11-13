package br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro;

public record EnderecoDto(

    Long id,
    String cep,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String uf) {

}
