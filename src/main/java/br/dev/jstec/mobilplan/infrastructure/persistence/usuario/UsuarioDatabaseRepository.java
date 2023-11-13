package br.dev.jstec.mobilplan.infrastructure.persistence.usuario;

import static java.util.UUID.fromString;

import br.dev.jstec.mobilplan.application.domain.usuario.Usuario;
import br.dev.jstec.mobilplan.application.repository.UsuarioRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.UsuarioJpaRepository;
import br.dev.jstec.mobilplan.infrastructure.rest.client.keycloak.KeycloakUserClient;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioDatabaseRepository implements UsuarioRepository {

    private final UsuarioJpaRepository repository;
    private final UsuarioEntityMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakUserClient keycloakUserClient;

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {

        return repository.findByEmail(email)
            .map(mapper::toUsuario)
            .or(Optional::empty);
    }

    @Override
    @Transactional
    public Usuario criar(Usuario usuario) {

        var userId = keycloakUserClient
            .createUser(
                usuario.getNome().value(),
                usuario.getEmail().value(),
                usuario.getSenha().value(),
                usuario.getRoles()
            );

        var usuarioEntity = mapper.toUsuarioEntity(usuario);
        usuarioEntity.setId(fromString(userId));
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));

        var usuarioEntitySaved = repository.save(usuarioEntity);

        return mapper.toUsuario(usuarioEntitySaved);
    }
}
