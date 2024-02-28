package br.dev.jstec.mobilplan.infrastructure.persistence.mapper;

import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao.ConfiguracaoFabricacaoEntity;

public interface IConfiguracaoFabricacaoMapper {

    ConfiguracaoFabricacaoEntity toEntity(ConfiguracaoFabricacao configuracaoFabricacao);

    ConfiguracaoFabricacao toDomain(ConfiguracaoFabricacaoEntity configuracaoFabricacaoEntity);
}
