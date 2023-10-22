package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarSitucaoUseCase.Input;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarSitucaoUseCase.Output;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class AlterarSituacaoUseCaseFixture {

    public static AlterarSitucaoUseCase.Output buildOutput(Marceneiro marceneiro, String situacao) {

        return new Output(
            marceneiro.getId().toString(),
            marceneiro.getNome().value(),
            situacao
        );
    }

    public static AlterarSitucaoUseCase.Input buildInput(String id, String situacao) {

        return new Input(
            id,
            situacao
        );
    }
}