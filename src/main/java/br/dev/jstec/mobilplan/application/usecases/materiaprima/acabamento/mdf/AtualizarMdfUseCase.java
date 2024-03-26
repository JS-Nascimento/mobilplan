package br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarMdfUseCase extends UseCase<AtualizarMdfUseCase.Input, AtualizarMdfUseCase.Output> {

    private final MateriaPrimaPort<Mdf> materiaPrima;

    @Override
    public Output execute(Input input) {

        var mdfAtual = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "mdf"));

        var novoMdf = Mdf.with(
                input.id(),
                Objects.equals(input.descricao(), mdfAtual.getDescricao())
                        ? mdfAtual.getDescricao()
                        : input.descricao(),
                Objects.equals(input.cor(), mdfAtual.getCor())
                        ? mdfAtual.getCor()
                        : input.cor(),
                Objects.equals(input.calculaPorLado(), mdfAtual.getCalculaPorLado().name())
                        ? mdfAtual.getCalculaPorLado().name()
                        : input.calculaPorLado(),
                Objects.equals(input.altura(), mdfAtual.getDimensoesChapa().getAltura())
                        ? mdfAtual.getDimensoesChapa().getAltura()
                        : input.altura(),
                Objects.equals(input.largura(), mdfAtual.getDimensoesChapa().getLargura())
                        ? mdfAtual.getDimensoesChapa().getLargura()
                        : input.largura(),
                Objects.equals(input.espessura(), mdfAtual.getDimensoesChapa().getEspessura())
                        ? mdfAtual.getDimensoesChapa().getEspessura()
                        : input.espessura(),
                Objects.equals(input.precificacao(), mdfAtual.getPrecificacao().toString())
                        ? mdfAtual.getPrecificacao().toString()
                        : input.precificacao(),
                Objects.equals(input.imagem(), mdfAtual.getImagem())
                        ? mdfAtual.getImagem()
                        : input.imagem(),
                Objects.equals(input.preco(), mdfAtual.getPreco())
                        ? mdfAtual.getPreco()
                        : input.preco(),
                mdfAtual.getTenantId(),
                mdfAtual.getCriadoEm(),
                mdfAtual.getAtualizadoEm());

        if (materiaPrima.existe(novoMdf)) {
            throw new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "mdf");
        }

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
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
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
