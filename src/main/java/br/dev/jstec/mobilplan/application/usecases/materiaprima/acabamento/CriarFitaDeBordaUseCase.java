package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarFitaDeBordaUseCase extends UseCase<CriarFitaDeBordaUseCase.Input, CriarFitaDeBordaUseCase.Output> {

    private final MateriaPrimaPort materiaPrima;

    @Override
    public Output execute(Input input) {

        var novaFita = FitaDeBorda.of(input.descricao(), input.cor(), input.largura(), input.preco(), input.tenantId());

        if (materiaPrima.existe(novaFita)) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "fita de borda");
        }

        var fitaSalva = materiaPrima.salvar(novaFita);

        return new Output(fitaSalva.getId(), fitaSalva.getDescricao(), fitaSalva.getCor(),
                fitaSalva.getLargura(), fitaSalva.getPreco());
    }

    public record Input(
            String descricao, String cor, double largura, double preco, String tenantId) {

    }

    public record Output(
            long id, String descricao, String cor, double largura, double preco) {
    }
}
