package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarFerragemUseCase extends UseCase<CriarFerragemUseCase.Input, CriarFerragemUseCase.Output> {

    private final MateriaPrimaPort<Ferragem> materiaPrima;

    @Override
    public Output execute(Input input) {

        var novaFerragem = Ferragem.of(
                input.descricao(),
                input.cor(),
                input.unidade(),
                input.preco(),
                input.precificacao(),
                input.imagem(),
                input.tenantId());

        if (materiaPrima.existe(novaFerragem)) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "ferragem");
        }

        var ferragem = materiaPrima.salvar(novaFerragem);

        return new Output(
                ferragem.getId(),
                ferragem.getDescricao(),
                ferragem.getCor(),
                ferragem.getUnidade().name(),
                ferragem.getPreco(),
                ferragem.getPrecificacao().name(),
                ferragem.getImagem(),
                ferragem.getCriadoEm(),
                ferragem.getAtualizadoEm(),
                ferragem.getTenantId());
    }

    public record Input(String descricao,
                        String cor,
                        String unidade,
                        double preco,
                        String precificacao,
                        String imagem,
                        UUID tenantId) {
    }

    public record Output(
            Long id,
            String descricao,
            String cor,
            String unidade,
            double preco,
            String precificacao,
            String imagem,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            UUID tenantId) {
    }
}
