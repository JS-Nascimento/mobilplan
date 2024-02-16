package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.FitaDeBorda;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarFitaDeBordaUseCase
        extends UseCase<AtualizarFitaDeBordaUseCase.Input, AtualizarFitaDeBordaUseCase.Output> {

    private final MateriaPrimaPort<FitaDeBorda> materiaPrima;

    @Override
    public Output execute(Input input) {

        var fitaAtual = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "fita de borda"));

        var fitaAtualizar = FitaDeBorda.with(
                fitaAtual.getId(),
                Objects.equals(input.descricao(), fitaAtual.getDescricao())
                        ? fitaAtual.getDescricao()
                        : input.descricao(),
                Objects.equals(input.cor(), fitaAtual.getCor())
                        ? fitaAtual.getCor()
                        : input.cor(),
                Objects.equals(input.largura(), fitaAtual.getLargura())
                        ? fitaAtual.getLargura()
                        : input.largura(),
                Objects.equals(input.preco(), fitaAtual.getPreco())
                        ? fitaAtual.getPreco()
                        : input.preco(),
                fitaAtual.getTenantId(),
                fitaAtual.getCriadoEm(),
                fitaAtual.getAtualizadoEm());

        if (materiaPrima.existe(fitaAtualizar)) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "fita de borda");
        }

        var fitaSalva = materiaPrima.salvar(fitaAtualizar);

        return new Output(fitaSalva.getId(),
                fitaSalva.getDescricao(),
                fitaSalva.getCor(),
                fitaSalva.getLargura(),
                fitaSalva.getUnidade().getDescricao(),
                fitaSalva.getTipoAcabamento().toString(),
                fitaSalva.getPrecificacao().toString(),
                fitaSalva.getPreco(),
                fitaSalva.getCriadoEm(),
                fitaSalva.getAtualizadoEm(),
                fitaSalva.getTenantId());
    }

    public record Input(Long id,
                        String descricao,
                        String cor,
                        double largura,
                        double preco,
                        UUID tenantId,
                        LocalDateTime criadoEm,
                        LocalDateTime atualizadoEm) {

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
            LocalDateTime atualizadoEm,
            UUID tenantId) {
    }
}
