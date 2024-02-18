package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarFerragemPorCriteriosUseCase extends
        UseCase<BuscarFerragemPorCriteriosUseCase.Input, List<BuscarFerragemPorCriteriosUseCase.Output>> {

    private final MateriaPrimaPort<Ferragem> materiaPrima;

    @Override
    public List<Output> execute(Input input) {

        var ferragens = materiaPrima.buscar(input);

        return ferragens.stream()
                .map(ferragem -> new Output(
                        ferragem.getId(),
                        ferragem.getDescricao(),
                        ferragem.getCor(),
                        ferragem.getUnidade().getDescricao(),
                        ferragem.getPreco(),
                        ferragem.getPrecificacao().name(),
                        ferragem.getCriadoEm(),
                        ferragem.getAtualizadoEm(),
                        ferragem.getTenantId()))
                .toList();
    }

    public record Input(
            String descricao,
            String cor,
            String unidade,
            double doPreco,
            double atePreco,
            String precificacao) {

    }

    public record Output(
            Long id,
            String descricao,
            String cor,
            String unidade,
            double preco,
            String precificacao,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            UUID tenantId) {
    }
}
