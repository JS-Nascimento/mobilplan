package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.materiaprima;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.CriarFitaDeBordaUseCase;
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

}
