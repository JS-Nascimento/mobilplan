package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarLong;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class TelefoneEntityFixture {

    public static TelefoneEntity buildComMarceneiro(Telefone telefone) {

        return TelefoneEntity.builder()
            .id(gerarLong())
            .ddi(telefone.ddi())
            .ddd(telefone.ddd())
            .numero(telefone.numero())
            .tipoTelefone(telefone.tipoTelefone().getDescricao())
            .build();
    }
}