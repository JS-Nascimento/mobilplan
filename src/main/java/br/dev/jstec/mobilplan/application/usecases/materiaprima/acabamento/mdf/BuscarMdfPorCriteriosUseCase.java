package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarMdfPorCriteriosUseCase extends
        UseCase<BuscarMdfPorCriteriosUseCase.Input, List<BuscarMdfPorCriteriosUseCase.Output>> {

    private final MateriaPrimaPort<Mdf> materiaPrima;

    @Override
    public List<Output> execute(Input input) {

        var mdfs = materiaPrima.buscar(input);

        return mdfs.stream()
                .map(mdf -> new Output(
                        mdf.getId(),
                        mdf.getDescricao(),
                        mdf.getCor(),
                        mdf.getCalculaPorLado().name(),
                        mdf.getDimensoesChapa().getAltura(),
                        mdf.getDimensoesChapa().getLargura(),
                        mdf.getDimensoesChapa().getEspessura(),
                        mdf.getPrecificacao().toString(),
                        mdf.getPreco(),
                        mdf.getTenantId(),
                        mdf.getCriadoEm(),
                        mdf.getAtualizadoEm(),
                        mdf.getTipoAcabamento().toString(),
                        mdf.getUnidade().getDescricao()))
                .toList();
    }

    public record Input(
            String descricao, String cor, double espessura, double doPreco, double atePreco, String tipoAcabamento) {

    }

    public record Output(
            Long id,
            String descricao,
            String cor,
            String calculaPorLado,
            double altura,
            double largura,
            double espessura,
            String precificacao,
            double preco,
            UUID tenantId,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            String tipoAcabamento,
            String unidade
    ) {
    }
}
