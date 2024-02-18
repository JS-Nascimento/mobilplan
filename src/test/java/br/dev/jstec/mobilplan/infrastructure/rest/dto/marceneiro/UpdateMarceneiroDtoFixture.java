package br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.model.marceneiro.Marceneiro;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class UpdateMarceneiroDtoFixture {

    public static UpdateMarceneiroDto buildComParametros(Marceneiro marceneiro) {

        var enderecoDtos = marceneiro.getEnderecos()
            .stream()
            .map(EnderecoDtoFixture::buildComEndereco)
            .toList();

        var telefoneDtos = marceneiro.getTelefones()
            .stream()
            .map(TelefoneDtoFixture::buildComTelefone)
            .toList();

        return new UpdateMarceneiroDto(
            marceneiro.getId().toString(),
            marceneiro.getSituacao().getDescricao(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            telefoneDtos,
            enderecoDtos);
    }
}