package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarPuxadorUseCase
        extends UseCase<AtualizarPuxadorUseCase.Input, AtualizarPuxadorUseCase.Output> {

    private final MateriaPrimaPort<Puxador> materiaPrima;

    @Override
    public Output execute(Input input) {

        var puxadorAtual = materiaPrima.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "puxador"));

        var puxadorAtualizar = Puxador.with(
                puxadorAtual.getId(),
                puxadorAtual.isPerfil(),
                Objects.equals(input.tipoPuxador(), puxadorAtual.getTipoPuxador().getDescricao())
                        ? puxadorAtual.getTipoPuxador().getDescricao()
                        : input.tipoPuxador(),
                Objects.equals(input.descricao(), puxadorAtual.getDescricao())
                        ? puxadorAtual.getDescricao()
                        : input.descricao(),
                Objects.equals(input.cor(), puxadorAtual.getCor())
                        ? puxadorAtual.getCor()
                        : input.cor(),
                Objects.equals(input.direcao(), puxadorAtual.getDirecao().name())
                        ? puxadorAtual.getDirecao().name()
                        : input.direcao(),
                Objects.equals(input.preco(), puxadorAtual.getPreco())
                        ? puxadorAtual.getPreco()
                        : input.preco(),
                Objects.equals(input.precificacao(), puxadorAtual.getPrecificacao().name())
                        ? puxadorAtual.getPrecificacao().name()
                        : input.precificacao(),
                Objects.equals(input.imagem(), puxadorAtual.getImagem())
                        ? puxadorAtual.getImagem()
                        : input.imagem(),
                Objects.equals(input.altura(), puxadorAtual.getDimensoesAcessorio().getAltura())
                        ? puxadorAtual.getDimensoesAcessorio().getAltura()
                        : input.altura(),
                Objects.equals(input.largura(), puxadorAtual.getDimensoesAcessorio().getLargura())
                        ? puxadorAtual.getDimensoesAcessorio().getLargura()
                        : input.largura(),
                Objects.equals(input.espessura(), puxadorAtual.getDimensoesAcessorio().getEspessura())
                        ? puxadorAtual.getDimensoesAcessorio().getEspessura()
                        : input.espessura(),
                puxadorAtual.getCriadoEm(),
                puxadorAtual.getAtualizadoEm(),
                puxadorAtual.getTenantId());

        if (materiaPrima.existe(puxadorAtualizar)) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "Puxador");
        }

        var puxadorSalvo = materiaPrima.salvar(puxadorAtualizar);

        return new Output(
                puxadorSalvo.getId(),
                puxadorSalvo.isPerfil(),
                puxadorSalvo.getTipoPuxador().getDescricao(),
                puxadorSalvo.getDescricao(),
                puxadorSalvo.getCor(),
                puxadorSalvo.getDirecao().name(),
                puxadorSalvo.getUnidade().getDescricao(),
                puxadorSalvo.getPreco(),
                puxadorSalvo.getPrecificacao().name(),
                puxadorSalvo.getImagem(),
                puxadorSalvo.getDimensoesAcessorio().getAltura(),
                puxadorSalvo.getDimensoesAcessorio().getLargura(),
                puxadorSalvo.getDimensoesAcessorio().getEspessura(),
                puxadorSalvo.getCriadoEm(),
                puxadorSalvo.getAtualizadoEm(),
                puxadorSalvo.getTenantId());
    }

    public record Input(Long id,
                        boolean perfil,
                        String tipoPuxador,
                        String descricao,
                        String cor,
                        String direcao,
                        double preco,
                        String precificacao,
                        String imagem,
                        double altura,
                        double largura,
                        double espessura,
                        UUID tenantId) {

    }

    public record Output(
            long id,
            boolean perfil,
            String tipoPuxador,
            String descricao,
            String cor,
            String direcao,
            String unidade,
            double preco,
            String precificacao,
            String imagem,
            double altura,
            double largura,
            double espessura,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            UUID tenantId) {
    }
}
