package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarFitaDeBordaPorIdUseCase extends
        UseCase<BuscarFitaDeBordaPorIdUseCase.Input, BuscarFitaDeBordaPorIdUseCase.Output> {

    private final MateriaPrimaPort materiaPrima;

    @Override
    public Output execute(Input input) {

        return materiaPrima.buscarPorId(input.id())
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
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "fita de borda"));
    }

    public record Input(Long id) {
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
