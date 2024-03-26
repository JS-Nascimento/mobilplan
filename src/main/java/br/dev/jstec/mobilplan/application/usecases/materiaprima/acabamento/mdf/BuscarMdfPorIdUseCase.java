package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarMdfPorIdUseCase extends
        UseCase<BuscarMdfPorIdUseCase.Input, BuscarMdfPorIdUseCase.Output> {

    private final MateriaPrimaPort<Mdf> materiaPrima;

    @Override
    public Output execute(Input input) {

        return materiaPrima.buscarPorId(input.id())
                .map(mdf -> new Output(
                        mdf.getId(),
                        mdf.getDescricao(),
                        mdf.getCor(),
                        mdf.getCalculaPorLado().name(),
                        mdf.getDimensoesChapa().getAltura(),
                        mdf.getDimensoesChapa().getLargura(),
                        mdf.getDimensoesChapa().getEspessura(),
                        mdf.getPrecificacao().toString(),
                        mdf.getImagem(),
                        mdf.getPreco(),
                        mdf.getTenantId(),
                        mdf.getCriadoEm(),
                        mdf.getAtualizadoEm(),
                        mdf.getTipoAcabamento().toString(),
                        mdf.getUnidade().getDescricao()))
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "Mdf"));
    }

    public record Input(Long id) {
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
            String imagem,
            double preco,
            UUID tenantId,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            String tipoAcabamento,
            String unidade
    ) {
    }
}
