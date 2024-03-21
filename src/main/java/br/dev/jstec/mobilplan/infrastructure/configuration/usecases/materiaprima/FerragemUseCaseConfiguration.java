package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.materiaprima;

import br.dev.jstec.mobilplan.application.ports.MateriaPrimaPort;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.AtualizarFerragemUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.BuscarFerragemPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.BuscarFerragemPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.CriarFerragemUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.ImportarFerragensEmLoteUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.RemoverFerragemPorIdUseCase;
import br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.Ferragem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FerragemUseCaseConfiguration {

    private final MateriaPrimaPort<Ferragem> materiaPrimaPort;

    @Bean
    public CriarFerragemUseCase criarFerragemUseCase() {
        return new CriarFerragemUseCase(materiaPrimaPort);
    }

    @Bean
    public AtualizarFerragemUseCase atualizarFerragemUseCase() {
        return new AtualizarFerragemUseCase(materiaPrimaPort);
    }

    @Bean
    public RemoverFerragemPorIdUseCase removerFerragemPorIdUseCase() {
        return new RemoverFerragemPorIdUseCase(materiaPrimaPort);
    }

    @Bean
    public BuscarFerragemPorIdUseCase buscarFerragemPorIdUseCase() {
        return new BuscarFerragemPorIdUseCase(materiaPrimaPort);
    }

    @Bean
    public BuscarFerragemPorCriteriosUseCase buscarFerragemPorCriteriosUseCase() {
        return new BuscarFerragemPorCriteriosUseCase(materiaPrimaPort);
    }

    @Bean
    public ImportarFerragensEmLoteUseCase importarFerragensEmLoteUseCase() {
        return new ImportarFerragensEmLoteUseCase(materiaPrimaPort);
    }
}
