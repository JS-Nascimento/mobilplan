package br.dev.jstec.mobilplan.infrastructure.persistence.usuario;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_INFORMACAO_INCONSISTENTE;
import static java.util.Optional.of;
import static java.util.UUID.fromString;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.dev.jstec.mobilplan.application.ports.UsuarioPort;
import br.dev.jstec.mobilplan.domain.usuario.Usuario;
import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import br.dev.jstec.mobilplan.infrastructure.jpa.CodigoValidacaoJpaRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.UsuarioJpaRepository;
import br.dev.jstec.mobilplan.infrastructure.rest.client.keycloak.KeycloakUserClient;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.ResponseUsuarioDto;
import br.dev.jstec.mobilplan.infrastructure.services.EventService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioPersistence implements UsuarioPort {

    private final UsuarioJpaRepository repository;
    private final CodigoValidacaoJpaRepository codigoValidacaoJpaRepository;
    private final UsuarioEntityMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakUserClient keycloakUserClient;
    private final EventService eventService;

    @Override
    public Optional<Usuario> buscarPorEmail(final String email) {

        return repository.findByEmail(email)
                .map(mapper::toUsuario)
                .or(Optional::empty);
    }

    public Optional<UsuarioEntity> buscarEntidadePorEmail(final String email) {

        return repository.findByEmail(email);

    }

    @Override
    @Transactional
    public Usuario criar(final Usuario usuario) {

        String userId = null;

        try {

            log.debug("Criando usuário no keycloak");
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

            log.debug("Criando usuário no banco de dados");
            var usuarioEntitySaved = repository.save(usuarioEntity);

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

    @Override
    public void criarValidacaoEmail(final Usuario usuario) {

        var codigoValidacao = CodigoValidacaoEntity.builder()
                .codigo(usuario.getCodigoConfirmacao())
                .validoAte(LocalDateTime.now().plusMinutes(30))
                .usuario(mapper.toUsuarioEntity(usuario))
                .build();

        var codigoSalvo = of(codigoValidacaoJpaRepository.save(codigoValidacao));

        codigoSalvo.ifPresent(codigo -> {
            log.debug("Publicando evento de confimação de email");
            usuario.publishDomainEvents(this.eventService::send);
        });
    }

    public ResponseUsuarioDto atualizandoStatusUsuarioAposConfirmacaoEmail(final Optional<UsuarioEntity> usuario) {

        return usuario.map(u -> {
            u.setSituacao("ATIVO");
            u.setEmailConfirmado(true);

            repository.save(u);

            keycloakUserClient.updateUserStatus(u.getId().toString(), true);

            return new ResponseUsuarioDto(
                    u.getId().toString(),
                    u.getNome(),
                    u.getEmail(),
                    u.getSituacao());
        }).orElseThrow(() -> new RequestException(BAD_REQUEST, ERRO_INFORMACAO_INCONSISTENTE,
                UsuarioPersistence.class.getSimpleName()));

    }
}