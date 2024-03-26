package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarFitaDeBordaPorCriteriosUseCase extends
        UseCase<BuscarFitaDeBordaPorCriteriosUseCase.Input, List<BuscarFitaDeBordaPorCriteriosUseCase.Output>> {

    private final MateriaPrimaPort<FitaDeBorda> materiaPrima;

    @Override
    public List<Output> execute(Input input) {

        var fitas = materiaPrima.buscar(input);

        return fitas.stream()
                .map(fita -> new Output(
                        fita.getId(),
                        fita.getDescricao(),
                        fita.getCor(),
                        fita.getLargura(),
                        fita.getUnidade().getDescricao(),
                        fita.getTipoAcabamento().toString(),
                        fita.getPrecificacao().toString(),
                        fita.getImagem(),
                        fita.getPreco(),
                        fita.getCriadoEm(),
                        fita.getTenantId()))
                .toList();
    }

    public record Input(
            String descricao, String cor, double largura, double doPreco, double atePreco, String tipoAcabamento) {

    }

    public record Output(
            long id,
            String descricao,
            String cor,
            double largura,
            String unidade,
            String tipoAcabamento,
            String precificacao,
            String imagem,
            double preco,
            LocalDateTime criadoEm,
            UUID tenantId) {
    }
}
