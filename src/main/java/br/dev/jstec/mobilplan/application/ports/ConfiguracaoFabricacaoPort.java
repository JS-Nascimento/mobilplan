package br.dev.jstec.mobilplan.application.ports;

import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.ConfiguracaoFabricacao;
import java.util.Optional;

public interface ConfiguracaoFabricacaoPort {

    Optional<ConfiguracaoFabricacao> buscarPorId(Long id);

    Optional<ConfiguracaoFabricacao> buscarPorTenant();

    ConfiguracaoFabricacao salvar(ConfiguracaoFabricacao model);

    void remover(ConfiguracaoFabricacao model);

    boolean existe(ConfiguracaoFabricacao model);
}
