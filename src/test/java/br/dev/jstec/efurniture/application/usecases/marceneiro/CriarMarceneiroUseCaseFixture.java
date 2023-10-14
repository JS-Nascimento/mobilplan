package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class CriarMarceneiroUseCaseFixture {

    public static CriarMarceneiroUseCase.Input
    buildCriarMarceneiroUseCaseInputComMarceneiro(Marceneiro marceneiro) {

        return new CriarMarceneiroUseCase.Input(
            marceneiro.nome().value(),
            marceneiro.nomeComercial().value(),
            marceneiro.tipoCliente(),
            marceneiro.email().value(),
            marceneiro.telefones(),
            marceneiro.enderecos(),
            marceneiro.auditInfo().createdBy().toString());
    }
}
