package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarMdfUseCase extends UseCase<CriarMdfUseCase.Input, CriarMdfUseCase.Output> {

    private final MateriaPrimaPort<Mdf> materiaPrima;

    @Override
    public Output execute(Input input) {

        var novoMdf = Mdf.of(input.descricao(),
                input.cor(),
                input.calculaPorLado(),
                input.altura(),
                input.largura(),
                input.espessura(),
                input.precificacao(),
                input.preco(),
                input.tenantId());

        var mdfSalvo = materiaPrima.salvar(novoMdf);

        return new Output(mdfSalvo.getId(),
                mdfSalvo.getDescricao(),
                mdfSalvo.getCor(),
                mdfSalvo.getCalculaPorLado().name(),
                mdfSalvo.getDimensoesChapa().getAltura(),
                mdfSalvo.getDimensoesChapa().getLargura(),
                mdfSalvo.getDimensoesChapa().getEspessura(),
                mdfSalvo.getPrecificacao().toString(),
                mdfSalvo.getPreco(),
                mdfSalvo.getTenantId(),
                mdfSalvo.getCriadoEm(),
                mdfSalvo.getAtualizadoEm(),
                mdfSalvo.getTipoAcabamento().toString(),
                mdfSalvo.getUnidade().getDescricao());
    }

    public record Input(
            String descricao,
            String cor,
            String calculaPorLado,
            double altura,
            double largura,
            double espessura,
            String precificacao,
            double preco,
            UUID tenantId
    ) {
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
