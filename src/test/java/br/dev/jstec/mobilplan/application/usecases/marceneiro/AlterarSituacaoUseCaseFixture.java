package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSitucaoUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSitucaoUseCase.Output;
import br.dev.jstec.mobilplan.domain.model.marceneiro.Marceneiro;
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