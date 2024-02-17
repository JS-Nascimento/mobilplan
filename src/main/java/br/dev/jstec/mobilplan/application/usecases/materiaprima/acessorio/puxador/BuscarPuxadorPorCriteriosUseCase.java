package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarPuxadorPorCriteriosUseCase extends
        UseCase<BuscarPuxadorPorCriteriosUseCase.Input, List<BuscarPuxadorPorCriteriosUseCase.Output>> {

    private final MateriaPrimaPort<FitaDeBorda> materiaPrima;

    @Override
    public List<Output> execute(Input input) {

        var fitas = materiaPrima.buscar(input.descricao(), input.cor(), input.largura(), input.doPreco(),
                input.atePreco(), input.tipoAcabamento());

        return fitas.stream()
                .map(fita -> new Output(
                        fita.getId(),
                        fita.getDescricao(),
                        fita.getCor(),
                        fita.getLargura(),
                        fita.getUnidade().getDescricao(),
                        fita.getTipoAcabamento().toString(),
                        fita.getPrecificacao().toString(),
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
            double preco,
            LocalDateTime criadoEm,
            UUID tenantId) {
    }
}
