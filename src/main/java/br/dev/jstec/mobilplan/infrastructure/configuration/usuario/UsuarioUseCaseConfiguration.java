package br.dev.jstec.mobilplan.infrastructure.configuration.usuario;

import br.dev.jstec.mobilplan.application.ports.UsuarioPort;
import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UsuarioUseCaseConfiguration {

    private final UsuarioPort repository;
    private final UsuarioMapper mapper;

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase() {
        return new CriarUsuarioUseCase(repository, mapper);
    }
}
