package br.dev.jstec.mobilplan.infrastructure.rest.dto.configuracaofabricacao;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ConfiguracaoFabricacaoDto {
    Long id;
    String descricao;
    String tipoMontagemFundo;
    int espessuraFundoGaveta;
    int rebaixoFundoGaveta;
    int folgaTrilhosGaveta;
    boolean acompanhaTrilhoGaveta;
    int folgaProfunidadeGavetaEmRelacaoGabinete;
    int corpoEmRelacaoFrenteGaveta;
    int espessuraCorpoGaveta;
    int folgaSuperiorPortaGiro;
    int folgaInferiorPortaGiro;
    int folgaEsquerdaPortaGiro;
    int folgaDireitaPortaGiro;
    int entreComponentesPortaGiro;
    String basePadraoFitagem;
    String lateralPadraoFitagem;
    String travessaHorizontalPadraoFitagem;
    String travessaVerticalPadraoFitagem;
    String fundoPadraoFitagem;
    String prateleiraInternaPadraoFitagem;
    String prateleiraExternaPadraoFitagem;
    String tampoPadraoFitagem;
    String portaPadraoFitagem;
    String frenteGavetaPadraoFitagem;
    LocalDateTime criadoEm;
    LocalDateTime alteradoEm;
    UUID tenantId;
}
