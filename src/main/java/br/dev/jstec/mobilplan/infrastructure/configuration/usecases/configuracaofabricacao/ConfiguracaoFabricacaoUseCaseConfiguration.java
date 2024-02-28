package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.configuracaofabricacao;

import br.dev.jstec.mobilplan.application.ports.ConfiguracaoFabricacaoPort;
import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.AlterarConfiguracaoFabricacaoUseCase;
import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.BuscarConfiguracaoFabricacaoPorTenantUseCase;
import br.dev.jstec.mobilplan.application.usecases.configuracaofabricacao.CriarConfiguracaoFabricacaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ConfiguracaoFabricacaoUseCaseConfiguration {

    private final ConfiguracaoFabricacaoPort port;

    @Bean
    public CriarConfiguracaoFabricacaoUseCase criarConfiguracaoFabricacaoUseCase() {
        return new CriarConfiguracaoFabricacaoUseCase(port);
    }

    @Bean
    public AlterarConfiguracaoFabricacaoUseCase alterarConfiguracaoFabricacaoUseCase() {
        return new AlterarConfiguracaoFabricacaoUseCase(port);
    }

    @Bean
    public BuscarConfiguracaoFabricacaoPorTenantUseCase buscarConfiguracaoPadraoPorTenantUseCase() {
        return new BuscarConfiguracaoFabricacaoPorTenantUseCase(port);
    }
}
