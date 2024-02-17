package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acessorios.Ferragem;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarFerragemPorIdUseCase extends
        UseCase<BuscarFerragemPorIdUseCase.Input, BuscarFerragemPorIdUseCase.Output> {

    private final MateriaPrimaPort<Ferragem> materiaPrima;

    @Override
    public Output execute(Input input) {

        return materiaPrima.buscarPorId(input.id())
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
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "ferragem"));
    }

    public record Input(Long id) {
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
