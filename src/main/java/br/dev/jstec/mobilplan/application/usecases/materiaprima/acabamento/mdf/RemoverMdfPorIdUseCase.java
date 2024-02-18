package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UnitUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverMdfPorIdUseCase extends
        UnitUseCase<RemoverMdfPorIdUseCase.Input> {

    private final MateriaPrimaPort<Mdf> materiaPrima;

    @Override
    public void execute(Input input) {

        var mdf = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "mdf"));

        materiaPrima.remover(mdf);
    }

    public record Input(Long id) {
    }
}
