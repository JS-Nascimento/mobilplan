package br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.ConfiguracaoFabricacaoPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoGaveta;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoPortaGiro;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlterarConfiguracaoFabricacaoUseCase
        extends UseCase<AlterarConfiguracaoFabricacaoUseCase.Input, AlterarConfiguracaoFabricacaoUseCase.Output> {

    private final ConfiguracaoFabricacaoPort configuracaoFabricacaoPort;

    @Override
    public Output execute(Input input) {

        var configuracaoFabricacaoAtual = configuracaoFabricacaoPort.buscarPorTenant()
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_EXISTENTE, "Configuração de fabricação"));

        var configuracaFabricacao = ConfiguracaoFabricacao.with(
                configuracaoFabricacaoAtual.getId(),
                input.descricao,
                PadraoGaveta.with(
                        input.tipoMontagemFundo,
                        input.espessuraFundoGaveta,
                        input.rebaixoFundoGaveta,
                        input.folgaTrilhosGaveta,
                        input.acompanhaTrilhoGaveta,
                        input.folgaProfunidadeGavetaEmRelacaoGabinete,
                        input.corpoEmRelacaoFrenteGaveta,
                        input.espessuraCorpoGaveta),
                PadraoPortaGiro.with(
                        input.folgaSuperiorPortaGiro,
                        input.folgaInferiorPortaGiro,
                        input.folgaEsquerdaPortaGiro,
                        input.folgaDireitaPortaGiro,
                        input.entreComponentesPortaGiro
                ),
                PadraoFitagem.with(
                        input.basePadraoFitagem,
                        input.lateralPadraoFitagem,
                        input.travessaHorizontalPadraoFitagem,
                        input.travessaVerticalPadraoFitagem,
                        input.fundoPadraoFitagem,
                        input.prateleiraInternaPadraoFitagem,
                        input.prateleiraExternaPadraoFitagem,
                        input.tampoPadraoFitagem,
                        input.portaPadraoFitagem,
                        input.frenteGavetaPadraoFitagem
                ),
                configuracaoFabricacaoAtual.getCriadoEm(),
                configuracaoFabricacaoAtual.getAlteradoEm(),
                configuracaoFabricacaoAtual.getTenantId());

        var configuracaoFabricacaoSalva = configuracaoFabricacaoPort.salvar(configuracaFabricacao);

        return new Output(
                configuracaoFabricacaoSalva.getId(),
                configuracaoFabricacaoSalva.getDescricao(),
                configuracaoFabricacaoSalva.getGaveta().getTipoMontagemFundo().name(),
                configuracaoFabricacaoSalva.getGaveta().getEspessuraFundo(),
                configuracaoFabricacaoSalva.getGaveta().getRebaixoFundo(),
                configuracaoFabricacaoSalva.getGaveta().getFolgaTrilhos(),
                configuracaoFabricacaoSalva.getGaveta().isAcompanhaTrilho(),
                configuracaoFabricacaoSalva.getGaveta().getFolgaProfunidadeGavetaEmRelacaoGabinete(),
                configuracaoFabricacaoSalva.getGaveta().getCorpoEmRelacaoFrente(),
                configuracaoFabricacaoSalva.getGaveta().getEspessuraCorpo(),
                configuracaoFabricacaoSalva.getPortasGiro().getFolgaSuperior(),
                configuracaoFabricacaoSalva.getPortasGiro().getFolgaInferior(),
                configuracaoFabricacaoSalva.getPortasGiro().getFolgaEsquerda(),
                configuracaoFabricacaoSalva.getPortasGiro().getFolgaDireita(),
                configuracaoFabricacaoSalva.getPortasGiro().getEntreComponentes(),
                configuracaoFabricacaoSalva.getFitagem().getBase().name(),
                configuracaoFabricacaoSalva.getFitagem().getLateral().name(),
                configuracaoFabricacaoSalva.getFitagem().getTravessaHorizontal().name(),
                configuracaoFabricacaoSalva.getFitagem().getTravessaVertical().name(),
                configuracaoFabricacaoSalva.getFitagem().getFundo().name(),
                configuracaoFabricacaoSalva.getFitagem().getPrateleiraInterna().name(),
                configuracaoFabricacaoSalva.getFitagem().getPrateleiraExterna().name(),
                configuracaoFabricacaoSalva.getFitagem().getTampo().name(),
                configuracaoFabricacaoSalva.getFitagem().getPorta().name(),
                configuracaoFabricacaoSalva.getFitagem().getFrenteGaveta().name(),
                configuracaoFabricacaoSalva.getCriadoEm(),
                configuracaoFabricacaoSalva.getAlteradoEm(),
                configuracaoFabricacaoSalva.getTenantId()
        );
    }

    public record Input(
            long id,
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
