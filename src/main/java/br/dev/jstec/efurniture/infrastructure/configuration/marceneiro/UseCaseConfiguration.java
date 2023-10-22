package br.dev.jstec.efurniture.infrastructure.configuration.marceneiro;

import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarMarceneiroUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarSitucaoUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarTodosMarceneirosUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.CriarMarceneiroUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.MarceneiroMapper;
import br.dev.jstec.efurniture.application.usecases.marceneiro.SalvarLogomarcaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCaseConfiguration {

    private final MarceneiroRepository marceneiroRepository;
    private final MarceneiroMapper mapper;


    @Bean
    public BuscarMarceneiroPorIdUseCase buscarMarceneiroPorIdUseCase() {

        return new BuscarMarceneiroPorIdUseCase(marceneiroRepository, mapper);
    }

    @Bean
    public BuscarMarceneiroPorEmailUseCase buscarMarceneiroPorEmailUseCase() {

        return new BuscarMarceneiroPorEmailUseCase(marceneiroRepository, mapper);
    }

    @Bean
    public BuscarMarceneiroPorDocumentoUseCase buscarMarceneiroPorDocumentoUseCase() {

        return new BuscarMarceneiroPorDocumentoUseCase(marceneiroRepository, mapper);
    }

    @Bean
    public CriarMarceneiroUseCase criarMarceneiroUseCase() {

        return new CriarMarceneiroUseCase(marceneiroRepository);
    }


    @Bean
    public AlterarSitucaoUseCase alterarSitucaoUseCase() {

        return new AlterarSitucaoUseCase(marceneiroRepository);
    }

    @Bean
    public AlterarMarceneiroUseCase alterarMarceneiroUseCase() {

        return new AlterarMarceneiroUseCase(marceneiroRepository, mapper);
    }

    @Bean
    BuscarTodosMarceneirosUseCase buscarTodosMarceneirosUseCase() {

        return new BuscarTodosMarceneirosUseCase(marceneiroRepository, mapper);
    }

    @Bean
    SalvarLogomarcaUseCase salvarLogomarcaUseCase() {

        return new SalvarLogomarcaUseCase(marceneiroRepository);
    }
}
