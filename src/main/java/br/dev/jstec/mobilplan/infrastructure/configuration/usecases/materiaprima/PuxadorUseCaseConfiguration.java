package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.materiaprima;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.AtualizarPuxadorUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.BuscarPuxadorPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.BuscarPuxadorPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.CriarPuxadorUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.RemoverPuxadorPorIdUseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acessorios.Puxador;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PuxadorUseCaseConfiguration {

    private final MateriaPrimaPort<Puxador> materiaPrimaPort;

    @Bean
    public CriarPuxadorUseCase criarPuxadorUseCase() {
        return new CriarPuxadorUseCase(materiaPrimaPort);
    }

    @Bean
    public AtualizarPuxadorUseCase atualizarPuxadorUseCase() {
        return new AtualizarPuxadorUseCase(materiaPrimaPort);
    }

    @Bean
    public RemoverPuxadorPorIdUseCase removerPuxadorPorIdUseCase() {
        return new RemoverPuxadorPorIdUseCase(materiaPrimaPort);
    }

    @Bean
    public BuscarPuxadorPorIdUseCase buscarPuxadorPorIdUseCase() {
        return new BuscarPuxadorPorIdUseCase(materiaPrimaPort);
    }

    @Bean
    public BuscarPuxadorPorCriteriosUseCase buscarPuxadorPorCriteriosUseCase() {
        return new BuscarPuxadorPorCriteriosUseCase(materiaPrimaPort);
    }
}
