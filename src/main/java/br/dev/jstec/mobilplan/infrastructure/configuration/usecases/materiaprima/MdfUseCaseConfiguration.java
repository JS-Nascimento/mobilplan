package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.materiaprima;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.CriarMdfUseCase;
import br.dev.jstec.mobilplan.domain.materiaprima.acabamento.Mdf;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MdfUseCaseConfiguration {

    private final MateriaPrimaPort<Mdf> materiaPrimaPort;

    @Bean
    public CriarMdfUseCase criarMdfUseCase() {
        return new CriarMdfUseCase(materiaPrimaPort);
    }

}
