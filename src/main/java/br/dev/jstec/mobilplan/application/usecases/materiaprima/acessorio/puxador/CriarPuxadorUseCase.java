package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acessorios.Puxador;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarPuxadorUseCase extends UseCase<CriarPuxadorUseCase.Input, CriarPuxadorUseCase.Output> {

    private final MateriaPrimaPort<Puxador> materiaPrima;

    @Override
    public Output execute(Input input) {

        var novoPuxador = Puxador.of(
                input.perfil(),
                input.tipoPuxador(),
                input.descricao(),
                input.cor(),
                input.direcao(),
                input.preco(),
                input.precificacao(),
                input.altura(),
                input.largura(),
                input.espessura(),
                input.tenantId());

        if (materiaPrima.existe(novoPuxador)) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "puxador");
        }

        var puxador = materiaPrima.salvar(novoPuxador);

        return new Output(
                puxador.getId(),
                puxador.isPerfil(),
                puxador.getTipoPuxador().getDescricao(),
                puxador.getDescricao(),
                puxador.getCor(),
                puxador.getDirecao().name(),
                puxador.getUnidade().getDescricao(),
                puxador.getPreco(),
                puxador.getPrecificacao().name(),
                puxador.getDimensoesAcessorio().getAltura(),
                puxador.getDimensoesAcessorio().getLargura(),
                puxador.getDimensoesAcessorio().getEspessura(),
                puxador.getCriadoEm(),
                puxador.getAtualizadoEm(),
                puxador.getTenantId());
    }

    public record Input(
            boolean perfil,
            String tipoPuxador,
            String descricao,
            String cor,
            String direcao,
            double preco,
            String precificacao,
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
            double altura,
            double largura,
            double espessura,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            UUID tenantId) {
    }
}
