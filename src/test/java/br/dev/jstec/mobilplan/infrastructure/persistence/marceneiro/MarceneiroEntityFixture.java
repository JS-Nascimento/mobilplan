package br.dev.jstec.mobilplan.infrastructure.persistence.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.marceneiro.Marceneiro;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class MarceneiroEntityFixture {

    public static MarceneiroEntity buildComMarceneiro(Marceneiro marceneiro) {

        var enderecoEntities = marceneiro.getEnderecos()
            .stream()
            .map(EnderecoEntityFixture::buildComEndereco)
            .collect(Collectors.toList());

        var telefoneEntities = marceneiro.getTelefones()
            .stream()
            .map(TelefoneEntityFixture::buildComTelefone)
            .collect(Collectors.toList());

        return new MarceneiroEntity(
            marceneiro.getId(),
            marceneiro.getSituacao().getDescricao(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            telefoneEntities,
            enderecoEntities,
            marceneiro.getLogomarcaUrl(),
            marceneiro.getLogomarcaFilename(),
            marceneiro.getCreatedBy(),
            marceneiro.getCreatedAt(),
            marceneiro.getUpdatedBy(),
            marceneiro.getUpdatedAt());
    }

}