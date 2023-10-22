package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarLong;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class EnderecoEntityFixture {

    public static EnderecoEntity buildComEndereco(Endereco endereco) {
        return EnderecoEntity.builder()
            .id(gerarLong())
            .cep(endereco.cep())
            .logradouro(endereco.logradouro())
            .numero(endereco.numero())
            .complemento(endereco.complemento())
            .bairro(endereco.bairro())
            .cidade(endereco.cidade())
            .uf(endereco.uf())
            .build();
    }
}
