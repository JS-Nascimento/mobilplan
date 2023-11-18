package br.dev.jstec.mobilplan.infrastructure.gateways;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_INFORMACAO_INCONSISTENTE;
import static java.util.UUID.fromString;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.dev.jstec.mobilplan.application.domain.usuario.Usuario;
import br.dev.jstec.mobilplan.application.repository.UsuarioRepository;
import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import br.dev.jstec.mobilplan.infrastructure.jpa.UsuarioJpaRepository;
import br.dev.jstec.mobilplan.infrastructure.persistence.usuario.UsuarioEntityMapper;
import br.dev.jstec.mobilplan.infrastructure.rest.client.keycloak.KeycloakUserClient;
import br.dev.jstec.mobilplan.infrastructure.services.EventService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioGateway implements UsuarioRepository {

    private final UsuarioJpaRepository repository;
    private final UsuarioEntityMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakUserClient keycloakUserClient;
    private final EventService eventService;

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {

        return repository.findByEmail(email)
            .map(mapper::toUsuario)
            .or(Optional::empty);
    }

    @Override
    @Transactional
    public Usuario criar(Usuario usuario) {

        String userId = null;

        try {

            log.info("Criando usuário no keycloak");
            userId = keycloakUserClient
                .createUser(
                    usuario.getNome().value(),
                    usuario.getEmail().value(),
                    usuario.getSenha().value(),
                    usuario.getRoles()
                );

            var usuarioEntity = mapper.toUsuarioEntity(usuario);
            usuarioEntity.setId(fromString(userId));
            usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));

            log.info("Criando usuário no banco de dados");
            var usuarioEntitySaved = repository.save(usuarioEntity);

            log.info("Publicando evento de confimação de email");
            usuario.publishDomainEvents(this.eventService::send);

            return mapper.toUsuario(usuarioEntitySaved);

        } catch (Exception e) {

            log.error("Erro ao criar usuário, iniciando compensação", e);

            if (userId != null) {

                keycloakUserClient.deleteUser(userId);
            }
            throw new RequestException(
                BAD_REQUEST,
                ERRO_INFORMACAO_INCONSISTENTE,
                this.getClass().getSimpleName(), e);
        }
    }
}
