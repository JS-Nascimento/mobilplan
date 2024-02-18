package br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Puxador;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarPuxadorPorCriteriosUseCase extends
        UseCase<BuscarPuxadorPorCriteriosUseCase.Input, List<BuscarPuxadorPorCriteriosUseCase.Output>> {

    private final MateriaPrimaPort<Puxador> materiaPrima;

    @Override
    public List<Output> execute(Input input) {

        var puxadores = materiaPrima.buscar(input);

        return puxadores.stream()
                .map(puxador -> new Output(
                        puxador.getId(),
                        puxador.isPerfil(),
                        puxador.getTipoPuxador().getDescricao(),
                        puxador.getDescricao(),
                        puxador.getCor(),
                        puxador.getDirecao().getDescricao(),
                        puxador.getUnidade().getDescricao(),
                        puxador.getPreco(),
                        puxador.getPrecificacao().getDescricao(),
                        puxador.getDimensoesAcessorio().getAltura(),
                        puxador.getDimensoesAcessorio().getLargura(),
                        puxador.getDimensoesAcessorio().getEspessura(),
                        puxador.getCriadoEm(),
                        puxador.getAtualizadoEm(),
                        puxador.getTenantId()))
                .toList();
    }

    public record Input(
            Boolean perfil,
            String tipoPuxador,
            String descricao,
            String cor,
            double largura,
            double doPreco,
            double atePreco,
            String precificacao) {

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
