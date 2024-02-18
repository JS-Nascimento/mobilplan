package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UnitUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverFerragemPorIdUseCase extends
        UnitUseCase<RemoverFerragemPorIdUseCase.Input> {

    private final MateriaPrimaPort<Ferragem> materiaPrima;

    @Override
    public void execute(Input input) {

        var ferragem = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "ferragem"));

        materiaPrima.remover(ferragem);
    }

    public record Input(Long id) {
    }
}
