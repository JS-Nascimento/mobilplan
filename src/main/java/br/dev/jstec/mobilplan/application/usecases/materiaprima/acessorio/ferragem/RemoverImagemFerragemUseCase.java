package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.RemoverImagemFerragemUseCase.Input;
import static br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.RemoverImagemFerragemUseCase.Output;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverImagemFerragemUseCase extends UseCase<Input, Output> {

    private final MateriaPrimaPort<Ferragem> materiaPrima;

    @Override
    public Output execute(Input input) {

        var ferragem = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ID_INVALIDO, Long.toString(input.id())));

        if (!Objects.equals(ferragem.getImagem(), input.imagem())) {
            throw new BusinessException(ERRO_ID_INVALIDO, Long.toString(input.id()));
        }

        return new Output(materiaPrima.removerImagem(ferragem, input.imagem()));
    }

    public record Input(
            long id,
            String imagem) {
    }

    public record Output(boolean deletado) {
    }
}
