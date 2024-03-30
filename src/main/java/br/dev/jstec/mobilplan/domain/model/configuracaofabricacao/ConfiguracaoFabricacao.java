package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.model.Tenant;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.TipoEstrategiaDeConstrucao;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = PRIVATE, force = true)
public class ConfiguracaoFabricacao extends Tenant implements Serializable {

    @Include
    private Long id;
    private final String descricao;
    private final TipoEstrategiaDeConstrucao tipoEstrategiaDeConstrucao;
    private final PadraoGaveta gaveta;
    private final PadraoPortaGiro portasGiro;
    private final PadraoFitagem fitagem;
    private LocalDateTime criadoEm;
    private LocalDateTime alteradoEm;


    private ConfiguracaoFabricacao(Long id,
                                   String descricao,
                                   PadraoGaveta gaveta,
                                   PadraoPortaGiro portasGiro,
                                   PadraoFitagem fitagem,
                                   TipoEstrategiaDeConstrucao tipoEstrategiaDeConstrucao,
                                   LocalDateTime criadoEm,
                                   LocalDateTime alteradoEm,
                                   UUID tenantId) {
        super(tenantId);
        this.id = id;
        this.descricao = descricao;
        this.gaveta = gaveta;
        this.portasGiro = portasGiro;
        this.fitagem = fitagem;
        this.criadoEm = criadoEm;
        this.alteradoEm = alteradoEm;
        this.tipoEstrategiaDeConstrucao = tipoEstrategiaDeConstrucao;

        validar();
    }

    public static ConfiguracaoFabricacao of(
            String descricao,
            PadraoGaveta gaveta,
            PadraoPortaGiro portasGiro,
            PadraoFitagem fitagem,
            TipoEstrategiaDeConstrucao tipoEstrategiaDeConstrucao,
            UUID tenantId) {

        return new ConfiguracaoFabricacao(null,
                descricao,
                gaveta,
                portasGiro,
                fitagem,
                tipoEstrategiaDeConstrucao,
                null,
                null,
                tenantId);
    }

    public static ConfiguracaoFabricacao with(
            Long id,
            String descricao,
            PadraoGaveta gaveta,
            PadraoPortaGiro portasGiro,
            PadraoFitagem fitagem,
            TipoEstrategiaDeConstrucao tipoEstrategiaDeConstrucao,
            LocalDateTime criadoEm,
            LocalDateTime alteradoEm,
            UUID tenantId) {

        return new ConfiguracaoFabricacao(id,
                descricao,
                gaveta,
                portasGiro,
                fitagem,
                tipoEstrategiaDeConstrucao,
                criadoEm,
                alteradoEm,
                tenantId);
    }

    @Override
    protected void validar() {

        super.validar();

        if (isBlank(descricao)) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Descrição");
        }
    }
}
