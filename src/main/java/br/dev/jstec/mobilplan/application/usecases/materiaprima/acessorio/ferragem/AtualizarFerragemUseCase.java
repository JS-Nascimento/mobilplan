package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acessorios.Ferragem;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarFerragemUseCase
        extends UseCase<AtualizarFerragemUseCase.Input, AtualizarFerragemUseCase.Output> {

    private final MateriaPrimaPort<Ferragem> materiaPrima;

    @Override
    public Output execute(Input input) {

        var ferragemAtual = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "ferragem"));

        var ferragemAtualizar = Ferragem.with(
                ferragemAtual.getId(),
                Objects.equals(input.descricao(), ferragemAtual.getDescricao())
                        ? ferragemAtual.getDescricao()
                        : input.descricao(),
                Objects.equals(input.cor(), ferragemAtual.getCor())
                        ? ferragemAtual.getCor()
                        : input.cor(),
                Objects.equals(input.unidade(), ferragemAtual.getUnidade().getDescricao())
                        ? ferragemAtual.getUnidade().getDescricao()
                        : input.unidade(),
                Objects.equals(input.preco(), ferragemAtual.getPreco())
                        ? ferragemAtual.getPreco()
                        : input.preco(),
                Objects.equals(input.precificacao(), ferragemAtual.getPrecificacao().name())
                        ? ferragemAtual.getPrecificacao().name()
                        : input.precificacao(),
                ferragemAtual.getTenantId(),
                ferragemAtual.getCriadoEm(),
                ferragemAtual.getAtualizadoEm());

        if (materiaPrima.existe(ferragemAtualizar)) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "Puxador");
        }

        var puxadorSalvo = materiaPrima.salvar(ferragemAtualizar);

        return new Output(
                puxadorSalvo.getId(),
                puxadorSalvo.getDescricao(),
                puxadorSalvo.getCor(),
                puxadorSalvo.getUnidade().getDescricao(),
                puxadorSalvo.getPreco(),
                puxadorSalvo.getPrecificacao().name(),
                puxadorSalvo.getCriadoEm(),
                puxadorSalvo.getAtualizadoEm(),
                puxadorSalvo.getTenantId());
    }

    public record Input(Long id,
                        String descricao,
                        String cor,
                        String unidade,
                        double preco,
                        String precificacao,
                        UUID tenantId) {
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
