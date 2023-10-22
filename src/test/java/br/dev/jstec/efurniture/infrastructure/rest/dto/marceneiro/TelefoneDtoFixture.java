package br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class TelefoneDtoFixture {

    public static TelefoneDto buildComTelefone(Telefone telefone) {

        return new TelefoneDto(
            telefone.id(),
            telefone.ddi(),
            telefone.ddd(),
            telefone.numero(),
            telefone.tipoTelefone().getDescricao());
    }
}