package br.dev.jstec.mobilplan.infrastructure.configuration.usecases.marceneiro;

import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarMarceneiroUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSitucaoUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarTodosMarceneirosUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.CriarMarceneiroUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.MarceneiroMapper;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.SalvarLogomarcaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MarceneiroUseCaseConfiguration {

    private final MarceneiroPort marceneiroPort;
    private final MarceneiroMapper mapper;


    @Bean
    public BuscarMarceneiroPorIdUseCase buscarMarceneiroPorIdUseCase() {

        return new BuscarMarceneiroPorIdUseCase(marceneiroPort, mapper);
    }

    @Bean
    public BuscarMarceneiroPorEmailUseCase buscarMarceneiroPorEmailUseCase() {

        return new BuscarMarceneiroPorEmailUseCase(marceneiroPort, mapper);
    }

    @Bean
    public BuscarMarceneiroPorDocumentoUseCase buscarMarceneiroPorDocumentoUseCase() {

        return new BuscarMarceneiroPorDocumentoUseCase(marceneiroPort, mapper);
    }

    @Bean
    public CriarMarceneiroUseCase criarMarceneiroUseCase() {

        return new CriarMarceneiroUseCase(marceneiroPort);
    }


    @Bean
    public AlterarSitucaoUseCase alterarSitucaoUseCase() {

        return new AlterarSitucaoUseCase(marceneiroPort);
    }

    @Bean
    public AlterarMarceneiroUseCase alterarMarceneiroUseCase() {

        return new AlterarMarceneiroUseCase(marceneiroPort, mapper);
    }

    @Bean
    BuscarTodosMarceneirosUseCase buscarTodosMarceneirosUseCase() {

        return new BuscarTodosMarceneirosUseCase(marceneiroPort, mapper);
    }

    @Bean
    SalvarLogomarcaUseCase salvarLogomarcaUseCase() {

        return new SalvarLogomarcaUseCase(marceneiroPort);
    }
}
