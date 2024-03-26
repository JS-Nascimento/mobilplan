package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarFitaDeBordaUseCase extends UseCase<CriarFitaDeBordaUseCase.Input, CriarFitaDeBordaUseCase.Output> {

    private final MateriaPrimaPort<FitaDeBorda> materiaPrima;

    @Override
    public Output execute(Input input) {

        var novaFita = FitaDeBorda.of(input.descricao(), input.cor(), input.largura(),
                input.preco(), input.imagem(), input.tenantId());

        if (materiaPrima.existe(novaFita)) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "fita de borda");
        }

        var fitaSalva = materiaPrima.salvar(novaFita);

        return new Output(fitaSalva.getId(),
                fitaSalva.getDescricao(),
                fitaSalva.getCor(),
                fitaSalva.getLargura(),
                fitaSalva.getUnidade().getDescricao(),
                fitaSalva.getTipoAcabamento().toString(),
                fitaSalva.getPrecificacao().toString(),
                fitaSalva.getImagem(),
                fitaSalva.getPreco(),
                fitaSalva.getCriadoEm(),
                fitaSalva.getAtualizadoEm(),
                fitaSalva.getTenantId());
    }

    public record Input(
            String descricao, String cor, double largura, double preco, String imagem, UUID tenantId) {

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
            LocalDateTime atualizadoEm,
            UUID tenantId) {
    }
}
