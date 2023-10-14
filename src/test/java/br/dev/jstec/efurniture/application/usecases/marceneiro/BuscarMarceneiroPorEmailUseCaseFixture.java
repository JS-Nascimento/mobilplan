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
            marceneiro.marceneiroId().value(),
            marceneiro.nome().value(),
            marceneiro.nomeComercial().value(),
            marceneiro.tipoCliente().tipoPessoa().name(),
            marceneiro.tipoCliente().documentoFiscal(),
            marceneiro.email().value(),
            marceneiro.telefones(),
            marceneiro.enderecos(),
            fromUuid(marceneiro.auditInfo().createdBy()),
            fromInstant(marceneiro.auditInfo().createdAt()),
            fromUuid(marceneiro.auditInfo().updatedBy()),
            fromInstant(marceneiro.auditInfo().updatedAt()));
    }
}