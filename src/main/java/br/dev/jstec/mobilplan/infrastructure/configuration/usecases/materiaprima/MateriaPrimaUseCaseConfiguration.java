package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.materiaprima;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.AtualizarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.BuscarFitaDeBordaPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.BuscarFitaDeBordaPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.CriarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.RemoverFitaDeBordaPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MateriaPrimaUseCaseConfiguration {

    private final MateriaPrimaPort materiaPrimaPort;

    @Bean
    public CriarFitaDeBordaUseCase criarFitaDeBordaUseCase() {
        return new CriarFitaDeBordaUseCase(materiaPrimaPort);
    }

    @Bean
    public BuscarFitaDeBordaPorIdUseCase buscarFitaDeBordaPorIdUseCase() {
        return new BuscarFitaDeBordaPorIdUseCase(materiaPrimaPort);
    }

    @Bean
    public BuscarFitaDeBordaPorCriteriosUseCase buscarFitaDeBordaPorCriteriosUseCase() {
        return new BuscarFitaDeBordaPorCriteriosUseCase(materiaPrimaPort);
    }

    @Bean
    public AtualizarFitaDeBordaUseCase atualizarFitaDeBordaUseCase() {
        return new AtualizarFitaDeBordaUseCase(materiaPrimaPort);
    }

    @Bean
    public RemoverFitaDeBordaPorIdUseCase removerFitaDeBordaPorIdUseCase() {
        return new RemoverFitaDeBordaPorIdUseCase(materiaPrimaPort);
    }

}
