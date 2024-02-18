package br.dev.jstec.mobilplan.infrastructure.persistence.marceneiro;

import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarLong;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.marceneiro.TelefoneEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
class TelefoneEntityFixture {

    public static TelefoneEntity buildComTelefone(Telefone telefone) {

        return TelefoneEntity.builder()
            .id(gerarLong())
            .ddi(telefone.ddi())
            .ddd(telefone.ddd())
            .numero(telefone.numero())
            .tipoTelefone(telefone.tipoTelefone().getDescricao())
            .build();
    }
}