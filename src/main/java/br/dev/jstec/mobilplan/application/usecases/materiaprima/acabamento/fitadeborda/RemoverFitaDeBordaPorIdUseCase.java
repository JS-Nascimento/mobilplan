package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UnitUseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverFitaDeBordaPorIdUseCase extends
        UnitUseCase<RemoverFitaDeBordaPorIdUseCase.Input> {

    private final MateriaPrimaPort<FitaDeBorda> materiaPrima;

    @Override
    public void execute(Input input) {

        var fita = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "fita de borda"));

        materiaPrima.remover(fita);
    }

    public record Input(Long id) {
    }
}
