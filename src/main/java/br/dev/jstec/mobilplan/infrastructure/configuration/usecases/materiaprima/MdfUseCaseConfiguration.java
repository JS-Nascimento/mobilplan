package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.materiaprima;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.AtualizarMdfUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.BuscarMdfPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.BuscarMdfPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.CriarMdfUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.RemoverMdfPorIdUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento.Mdf;
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

    @Bean
    public AtualizarMdfUseCase atualizarMdfUseCase() {
        return new AtualizarMdfUseCase(materiaPrimaPort);
    }

    @Bean
    public RemoverMdfPorIdUseCase removerMdfPorIdUseCase() {
        return new RemoverMdfPorIdUseCase(materiaPrimaPort);
    }

    @Bean
    public BuscarMdfPorIdUseCase buscarMdfPorIdUseCase() {
        return new BuscarMdfPorIdUseCase(materiaPrimaPort);
    }

    @Bean
    public BuscarMdfPorCriteriosUseCase buscarMdfPorCriteriosUseCase() {
        return new BuscarMdfPorCriteriosUseCase(materiaPrimaPort);
    }
}
