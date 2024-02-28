package br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.ConfiguracaoFabricacaoPort;
import br.dev.jstec.mobilplan.application.usecases.NullaryUseCase;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BuscarConfiguracaoPadraoPorTenantUseCase
        extends
        NullaryUseCase<BuscarConfiguracaoPadraoPorTenantUseCase.Output> {

    private final ConfiguracaoFabricacaoPort configuracaoFabricacaoPort;

    @Override
    public Output execute() {

        var configuracaoFabricacao = configuracaoFabricacaoPort.buscarPorTenant()
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_EXISTENTE, "Configuração de Fabricação"));

        return new Output(
                configuracaoFabricacao.getId(),
                configuracaoFabricacao.getDescricao(),
                configuracaoFabricacao.getGaveta().getTipoMontagemFundo().name(),
                configuracaoFabricacao.getGaveta().getEspessuraFundo(),
                configuracaoFabricacao.getGaveta().getRebaixoFundo(),
                configuracaoFabricacao.getGaveta().getFolgaTrilhos(),
                configuracaoFabricacao.getGaveta().isAcompanhaTrilho(),
                configuracaoFabricacao.getGaveta().getFolgaProfunidadeGavetaEmRelacaoGabinete(),
                configuracaoFabricacao.getGaveta().getCorpoEmRelacaoFrente(),
                configuracaoFabricacao.getGaveta().getEspessuraCorpo(),
                configuracaoFabricacao.getPortasGiro().getFolgaSuperior(),
                configuracaoFabricacao.getPortasGiro().getFolgaInferior(),
                configuracaoFabricacao.getPortasGiro().getFolgaEsquerda(),
                configuracaoFabricacao.getPortasGiro().getFolgaDireita(),
                configuracaoFabricacao.getPortasGiro().getEntreComponentes(),
                configuracaoFabricacao.getFitagem().getBase().name(),
                configuracaoFabricacao.getFitagem().getLateral().name(),
                configuracaoFabricacao.getFitagem().getTravessaHorizontal().name(),
                configuracaoFabricacao.getFitagem().getTravessaVertical().name(),
                configuracaoFabricacao.getFitagem().getFundo().name(),
                configuracaoFabricacao.getFitagem().getPrateleiraInterna().name(),
                configuracaoFabricacao.getFitagem().getPrateleiraExterna().name(),
                configuracaoFabricacao.getFitagem().getTampo().name(),
                configuracaoFabricacao.getFitagem().getPorta().name(),
                configuracaoFabricacao.getFitagem().getFrenteGaveta().name(),
                configuracaoFabricacao.getCriadoEm(),
                configuracaoFabricacao.getAlteradoEm(),
                configuracaoFabricacao.getTenantId()
        );
    }

    public record Output(
            Long id,
            String descricao,
            String tipoMontagemFundo,
            int espessuraFundoGaveta,
            int rebaixoFundoGaveta,
            int folgaTrilhosGaveta,
            boolean acompanhaTrilhoGaveta,
            int folgaProfunidadeGavetaEmRelacaoGabinete,
            int corpoEmRelacaoFrenteGaveta,
            int espessuraCorpoGaveta,
            int folgaSuperiorPortaGiro,
            int folgaInferiorPortaGiro,
            int folgaEsquerdaPortaGiro,
            int folgaDireitaPortaGiro,
            int entreComponentesPortaGiro,
            String basePadraoFitagem,
            String lateralPadraoFitagem,
            String travessaHorizontalPadraoFitagem,
            String travessaVerticalPadraoFitagem,
            String fundoPadraoFitagem,
            String prateleiraInternaPadraoFitagem,
            String prateleiraExternaPadraoFitagem,
            String tampoPadraoFitagem,
            String portaPadraoFitagem,
            String frenteGavetaPadraoFitagem,
            LocalDateTime criadoEm,
            LocalDateTime alteradoEm,
            UUID tenantId
    ) {

    }
}
