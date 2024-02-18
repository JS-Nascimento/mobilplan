package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UnitUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverPuxadorPorIdUseCase extends
        UnitUseCase<RemoverPuxadorPorIdUseCase.Input> {

    private final MateriaPrimaPort<Puxador> materiaPrima;

    @Override
    public void execute(Input input) {

        var puxador = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "puxador"));

        materiaPrima.remover(puxador);
    }

    public record Input(Long id) {
    }
}
