package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverImagemPuxadorUseCase
        extends UseCase<RemoverImagemPuxadorUseCase.Input, RemoverImagemPuxadorUseCase.Output> {

    private final MateriaPrimaPort<Puxador> materiaPrima;

    @Override
    public Output execute(Input input) {

        var puxador = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ID_INVALIDO, Long.toString(input.id())));

        if (!Objects.equals(puxador.getImagem(), input.imagem())) {
            throw new BusinessException(ERRO_ID_INVALIDO, Long.toString(input.id()));
        }

        return new Output(materiaPrima.removerImagem(puxador, input.imagem()));
    }

    public record Input(
            long id,
            String imagem) {
    }

    public record Output(boolean deletado) {
    }
}
