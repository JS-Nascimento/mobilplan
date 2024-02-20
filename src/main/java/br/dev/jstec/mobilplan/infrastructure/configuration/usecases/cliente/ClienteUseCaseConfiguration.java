package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.cliente;

import br.dev.jstec.mobilplan.application.ports.ClientePort;
import br.dev.jstec.mobilplan.application.usecases.cliente.AlterarClienteUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.CriarClienteUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.RemoverClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClienteUseCaseConfiguration {

    private final ClientePort clientePort;

    @Bean
    public CriarClienteUseCase criarClienteUseCase() {
        return new CriarClienteUseCase(clientePort);
    }

    @Bean
    public AlterarClienteUseCase atualizarClienteUseCase() {
        return new AlterarClienteUseCase(clientePort);
    }

    @Bean
    public RemoverClienteUseCase removerClienteUseCase() {
        return new RemoverClienteUseCase(clientePort);
    }

    @Bean
    public BuscarClientePorIdUseCase buscarClientePorIdUseCase() {
        return new BuscarClientePorIdUseCase(clientePort);
    }

    @Bean
    public BuscarClientePorCriteriosUseCase buscarClientePorCriteriosUseCase() {
        return new BuscarClientePorCriteriosUseCase(clientePort);
    }
}
