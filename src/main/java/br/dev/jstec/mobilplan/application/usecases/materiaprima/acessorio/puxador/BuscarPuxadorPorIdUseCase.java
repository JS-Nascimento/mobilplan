package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarPuxadorPorIdUseCase extends
        UseCase<BuscarPuxadorPorIdUseCase.Input, BuscarPuxadorPorIdUseCase.Output> {

    private final MateriaPrimaPort<Puxador> materiaPrima;

    @Override
    public Output execute(Input input) {

        return materiaPrima.buscarPorId(input.id())
                .map(puxador -> new Output(
                        puxador.getId(),
                        puxador.isPerfil(),
                        puxador.getTipoPuxador().getDescricao(),
                        puxador.getDescricao(),
                        puxador.getCor(),
                        puxador.getDirecao().name(),
                        puxador.getUnidade().getDescricao(),
                        puxador.getPreco(),
                        puxador.getPrecificacao().name(),
                        puxador.getImagem(),
                        puxador.getDimensoesAcessorio().getAltura(),
                        puxador.getDimensoesAcessorio().getLargura(),
                        puxador.getDimensoesAcessorio().getEspessura(),
                        puxador.getCriadoEm(),
                        puxador.getAtualizadoEm(),
                        puxador.getTenantId()))
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "puxador"));
    }

    public record Input(Long id) {
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
