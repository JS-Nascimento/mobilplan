package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo.fromInstant;
import static br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo.fromUuid;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class BuscarMarceneiroPorIdUseCaseFixture {

    public static BuscarMarceneiroPorIdUseCase.Output buildOutput(Marceneiro marceneiro) {

        return new BuscarMarceneiroPorIdUseCase.Output(
            marceneiro.getId().toString(),
            marceneiro.getSituacao().getDescricao(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            marceneiro.getTelefones(),
            marceneiro.getEnderecos(),
            marceneiro.getLogomarcaUrl(),
            marceneiro.getLogomarcaFilename(),
            fromUuid(marceneiro.getCreatedBy()),
            fromInstant(marceneiro.getCreatedAt()),
            fromUuid(marceneiro.getUpdatedBy()),
            fromInstant(marceneiro.getUpdatedAt()));
    }
}