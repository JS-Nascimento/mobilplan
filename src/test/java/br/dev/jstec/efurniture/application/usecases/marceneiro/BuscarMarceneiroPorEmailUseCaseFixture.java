package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo.fromInstant;
import static br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo.fromUuid;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
class BuscarMarceneiroPorEmailUseCaseFixture {

    public static BuscarMarceneiroPorEmailUseCase.Output buildOutput(Marceneiro marceneiro) {

        return new BuscarMarceneiroPorEmailUseCase.Output(
            marceneiro.getMarceneiroId().value(),
            marceneiro.getSituacao().getDescricao(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            marceneiro.getTelefones(),
            marceneiro.getEnderecos(),
            fromUuid(marceneiro.getAuditInfo().createdBy()),
            fromInstant(marceneiro.getAuditInfo().createdAt()),
            fromUuid(marceneiro.getAuditInfo().updatedBy()),
            fromInstant(marceneiro.getAuditInfo().updatedAt()));
    }
}