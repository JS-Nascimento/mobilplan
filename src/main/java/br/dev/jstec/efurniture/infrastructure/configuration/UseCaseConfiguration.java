package br.dev.jstec.efurniture.infrastructure.configuration;

import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.MarceneiroMapper;
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
}
