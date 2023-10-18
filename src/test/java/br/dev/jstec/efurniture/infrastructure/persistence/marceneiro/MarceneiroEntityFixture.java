package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class MarceneiroEntityFixture {

    public static MarceneiroEntity buildComMarceneiro(Marceneiro marceneiro) {

        List<EnderecoEntity> enderecoEntities = marceneiro.getEnderecos()
            .stream()
            .map(EnderecoEntityFixture::build)
            .collect(Collectors.toList());

        List<TelefoneEntity> telefoneEntities = marceneiro.getTelefones()
            .stream()
            .map(TelefoneEntityFixture::buildComMarceneiro)
            .collect(Collectors.toList());

        return new MarceneiroEntity(
            marceneiro.getMarceneiroId().value(),
            marceneiro.getSituacao().name(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().name(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            telefoneEntities,
            enderecoEntities,
            marceneiro.getAuditInfo().createdBy(),
            marceneiro.getAuditInfo().createdAt(),
            marceneiro.getAuditInfo().updatedBy(),
            marceneiro.getAuditInfo().updatedAt());
    }

}