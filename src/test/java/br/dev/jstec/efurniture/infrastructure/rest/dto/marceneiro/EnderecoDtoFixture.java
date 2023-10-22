package br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class EnderecoDtoFixture {

    public static EnderecoDto buildComEndereco(Endereco endereco) {
        return new EnderecoDto(
            endereco.id(),
            endereco.cep(),
            endereco.logradouro(),
            endereco.numero(),
            endereco.complemento(),
            endereco.bairro(),
            endereco.cidade(),
            endereco.uf());
    }
}
