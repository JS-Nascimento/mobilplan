package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarSitucaoUseCase.Input;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
class AlterarSituacaoUseCaseFixture {

    public static AlterarSitucaoUseCase.Input buildInput(Marceneiro marceneiro, String situacao) {

        return new Input(
            marceneiro,
            situacao
        );
    }
}