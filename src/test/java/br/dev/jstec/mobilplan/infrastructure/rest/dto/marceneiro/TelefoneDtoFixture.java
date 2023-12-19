package br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
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