package br.dev.jstec.mobilplan.infrastructure.configuration.usuario;

import br.dev.jstec.mobilplan.application.ports.UsuarioPort;
import br.dev.jstec.mobilplan.application.usecases.usuario.AlterarUsuarioUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.BuscarUsuarioPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.SalvarAvatarUseCase;
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

    @Bean
    public AlterarUsuarioUseCase alterarUsuarioUseCase() {
        return new AlterarUsuarioUseCase(repository, mapper);
    }

    @Bean
    public SalvarAvatarUseCase salvarAvatarUseCase() {
        return new SalvarAvatarUseCase(repository);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase() {
        return new BuscarUsuarioPorIdUseCase(repository, mapper);
    }
}
