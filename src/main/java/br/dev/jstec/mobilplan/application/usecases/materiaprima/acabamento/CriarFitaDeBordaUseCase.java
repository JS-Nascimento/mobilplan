package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento;

import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;

public class CriarFitaDeBordaUseCase extends UseCase<CriarFitaDeBordaUseCase.Input, CriarFitaDeBordaUseCase.Output> {
    @Override
    public Output execute(Input input) {

        var novaFita = FitaDeBorda.of(input.descricao(), input.cor(), input.largura(), input.preco());

        return null;
    }

    public record Input(
            String descricao, String cor, double largura, double preco) {

    }

    public record Output(
            long id, String descricao, String cor, double largura, double preco) {
    }
}
