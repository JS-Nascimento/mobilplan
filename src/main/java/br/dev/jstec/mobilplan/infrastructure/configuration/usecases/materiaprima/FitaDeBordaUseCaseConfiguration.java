package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.materiaprima;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.AtualizarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.BuscarFitaDeBordaPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.BuscarFitaDeBordaPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.CriarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.fitadeborda.RemoverFitaDeBordaPorIdUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.FitaDeBorda;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FitaDeBordaUseCaseConfiguration {

    private final MateriaPrimaPort<FitaDeBorda> materiaPrimaPort;

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
