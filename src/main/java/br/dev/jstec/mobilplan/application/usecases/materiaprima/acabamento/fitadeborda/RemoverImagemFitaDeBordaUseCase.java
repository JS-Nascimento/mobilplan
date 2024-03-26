package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverImagemFitaDeBordaUseCase
        extends UseCase<RemoverImagemFitaDeBordaUseCase.Input, RemoverImagemFitaDeBordaUseCase.Output> {

    private final MateriaPrimaPort<FitaDeBorda> materiaPrima;

    @Override
    public Output execute(Input input) {

        var mdf = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ID_INVALIDO, Long.toString(input.id())));

        if (!Objects.equals(mdf.getImagem(), input.imagem())) {
            throw new BusinessException(ERRO_ID_INVALIDO, Long.toString(input.id()));
        }

        return new Output(materiaPrima.removerImagem(mdf, input.imagem()));
    }

    public record Input(
            long id,
            String imagem) {
    }

    public record Output(boolean deletado) {
    }
}
