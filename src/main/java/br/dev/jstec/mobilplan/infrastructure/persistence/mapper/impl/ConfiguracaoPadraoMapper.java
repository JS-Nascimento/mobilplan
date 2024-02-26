package br.dev.jstec.mobilplan.infrastructure.persistence.mapper.impl;

import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoFitagem;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoGaveta;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.PadraoPortaGiro;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.ConfiguracaoFabricacaoEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.PadraoFitagemEmbedded;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.PadraoGavetaEmbedded;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.PadraoPortaGiroEmbedded;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IConfiguracaoPadraoMapper;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoPadraoMapper implements IConfiguracaoPadraoMapper {
    @Override
    public ConfiguracaoFabricacaoEntity toEntity(ConfiguracaoFabricacao configuracaoFabricacao) {

        var entity = new ConfiguracaoFabricacaoEntity();
        entity.setId(configuracaoFabricacao.getId());
        entity.setDescricao(configuracaoFabricacao.getDescricao());
        entity.setPadraoGavetaEmbedded(toPadraoGavetaEmbedded(configuracaoFabricacao));
        entity.setPadraoPortaGiroEmbedded(toPadraoPortaGiroEmbedded(configuracaoFabricacao));
        entity.setPadraoFitagemEmbedded(toPadraoFitagemEmbedded(configuracaoFabricacao));
        entity.setCriadoEm(configuracaoFabricacao.getCriadoEm());
        entity.setAtualizadoEm(configuracaoFabricacao.getAlteradoEm());
        entity.setTenantId(configuracaoFabricacao.getTenantId());

        return null;
    }

    @Override
    public ConfiguracaoFabricacao toDomain(ConfiguracaoFabricacaoEntity configuracaoFabricacaoEntity) {

        return ConfiguracaoFabricacao.with(
                configuracaoFabricacaoEntity.getId(),
                configuracaoFabricacaoEntity.getDescricao(),
                toPadraoGaveta(configuracaoFabricacaoEntity.getPadraoGavetaEmbedded()),
                toPadraoPortaGiro(configuracaoFabricacaoEntity.getPadraoPortaGiroEmbedded()),
                toPadraoFitagem(configuracaoFabricacaoEntity.getPadraoFitagemEmbedded()),
                configuracaoFabricacaoEntity.getCriadoEm(),
                configuracaoFabricacaoEntity.getAtualizadoEm(),
                configuracaoFabricacaoEntity.getTenantId());

    }

    private PadraoGavetaEmbedded toPadraoGavetaEmbedded(ConfiguracaoFabricacao configuracaoFabricacao) {

        if (configuracaoFabricacao.getGaveta() == null) {
            return null;
        }

        return new PadraoGavetaEmbedded(
                configuracaoFabricacao.getGaveta().getTipoMontagemFundo().name(),
                configuracaoFabricacao.getGaveta().getEspessuraFundo(),
                configuracaoFabricacao.getGaveta().getRebaixoFundo(),
                configuracaoFabricacao.getGaveta().getFolgaTrilhos(),
                configuracaoFabricacao.getGaveta().isAcompanhaTrilho(),
                configuracaoFabricacao.getGaveta().getFolgaProfunidadeGavetaEmRelacaoGabinete(),
                configuracaoFabricacao.getGaveta().getCorpoEmRelacaoFrente(),
                configuracaoFabricacao.getGaveta().getEspessuraCorpo());
        );
    }

    private PadraoPortaGiroEmbedded toPadraoPortaGiroEmbedded(ConfiguracaoFabricacao configuracaoFabricacao) {

        if (configuracaoFabricacao.getPortasGiro() == null) {
            return null;
        }

        return new PadraoPortaGiroEmbedded(
                configuracaoFabricacao.getPortasGiro().getFolgaSuperior(),
                configuracaoFabricacao.getPortasGiro().getFolgaInferior(),
                configuracaoFabricacao.getPortasGiro().getFolgaEsquerda(),
                configuracaoFabricacao.getPortasGiro().getFolgaDireita(),
                configuracaoFabricacao.getPortasGiro().getEntreComponentes());
    }

    private PadraoFitagemEmbedded toPadraoFitagemEmbedded(ConfiguracaoFabricacao configuracaoFabricacao) {

        if (configuracaoFabricacao.getFitagem() == null) {
            return null;
        }

        return new PadraoFitagemEmbedded(
                configuracaoFabricacao.getFitagem().getBase().name(),
                configuracaoFabricacao.getFitagem().getLateral().name(),
                configuracaoFabricacao.getFitagem().getTravessaHorizontal().name(),
                configuracaoFabricacao.getFitagem().getTravessaVertical().name(),
                configuracaoFabricacao.getFitagem().getFundo().name(),
                configuracaoFabricacao.getFitagem().getPrateleiraInterna().name(),
                configuracaoFabricacao.getFitagem().getPrateleiraExterna().name(),
                configuracaoFabricacao.getFitagem().getTampo().name(),
                configuracaoFabricacao.getFitagem().getPorta().name(),
                configuracaoFabricacao.getFitagem().getFrenteGaveta().name());
    }

    private PadraoGaveta toPadraoGaveta(PadraoGavetaEmbedded padraoGavetaEmbedded) {

        if (padraoGavetaEmbedded == null) {
            return null;
        }

        return PadraoGaveta.with(
                padraoGavetaEmbedded.getTipoMontagemFundo(),
                padraoGavetaEmbedded.getEspessuraFundo(),
                padraoGavetaEmbedded.getRebaixoFundo(),
                padraoGavetaEmbedded.getFolgaTrilhos(),
                padraoGavetaEmbedded.isAcompanhaTrilho(),
                padraoGavetaEmbedded.getFolgaProfunidadeGavetaEmRelacaoGabinete(),
                padraoGavetaEmbedded.getCorpoEmRelacaoFrente(),
                padraoGavetaEmbedded.getEspessuraCorpo());
    }

    private PadraoPortaGiro toPadraoPortaGiro(PadraoPortaGiroEmbedded padraoPortaGiroEmbedded) {

        if (padraoPortaGiroEmbedded == null) {
            return null;
        }

        return PadraoPortaGiro.with(
                padraoPortaGiroEmbedded.getFolgaSuperior(),
                padraoPortaGiroEmbedded.getFolgaInferior(),
                padraoPortaGiroEmbedded.getFolgaEsquerda(),
                padraoPortaGiroEmbedded.getFolgaDireita(),
                padraoPortaGiroEmbedded.getEntreComponentes());
    }

    private PadraoFitagem toPadraoFitagem(PadraoFitagemEmbedded padraoFitagemEmbedded) {

        if (padraoFitagemEmbedded == null) {
            return null;
        }

        return PadraoFitagem.with(
                padraoFitagemEmbedded.getBase(),
                padraoFitagemEmbedded.getLateral(),
                padraoFitagemEmbedded.getTravessaHorizontal(),
                padraoFitagemEmbedded.getTravessaVertical(),
                padraoFitagemEmbedded.getFundo(),
                padraoFitagemEmbedded.getPrateleiraInterna(),
                padraoFitagemEmbedded.getPrateleiraExterna(),
                padraoFitagemEmbedded.getTampo(),
                padraoFitagemEmbedded.getPorta(),
                padraoFitagemEmbedded.getFrenteGaveta());
    }
}
