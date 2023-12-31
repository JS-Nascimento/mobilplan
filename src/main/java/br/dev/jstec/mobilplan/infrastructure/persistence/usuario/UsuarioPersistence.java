package br.dev.jstec.mobilplan.infrastructure.persistence.usuario;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_INFORMACAO_INCONSISTENTE;
import static java.util.Optional.of;
import static java.util.UUID.fromString;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.dev.jstec.mobilplan.application.ports.UsuarioPort;
import br.dev.jstec.mobilplan.domain.usuario.Usuario;
import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import br.dev.jstec.mobilplan.infrastructure.jpa.CodigoValidacaoJpaRepository;
import br.dev.jstec.mobilplan.infrastructure.jpa.UsuarioJpaRepository;
import br.dev.jstec.mobilplan.infrastructure.persistence.helpers.PersistenceHelper;
import br.dev.jstec.mobilplan.infrastructure.rest.client.bucket.PutFilesBucket;
import br.dev.jstec.mobilplan.infrastructure.rest.client.keycloak.KeycloakUserClient;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.ResponseUsuarioDto;
import br.dev.jstec.mobilplan.infrastructure.services.EventService;
import jakarta.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsuarioPersistence extends PersistenceHelper implements UsuarioPort {

    private final UsuarioJpaRepository repository;
    private final CodigoValidacaoJpaRepository codigoValidacaoJpaRepository;
    private final UsuarioEntityMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakUserClient keycloakUserClient;
    private final EventService eventService;

    @Value("${spring.application.waiting-time-to-confirm-email-in-minutes}")
    private int waitingTime;

    @Value("${spring.repository.bucket-name.avatar}")
    private String bucketName;

    public UsuarioPersistence(PutFilesBucket putFilesBucket, UsuarioJpaRepository repository,
                              CodigoValidacaoJpaRepository codigoValidacaoJpaRepository, UsuarioEntityMapper mapper,
                              PasswordEncoder passwordEncoder, KeycloakUserClient keycloakUserClient,
                              EventService eventService) {
        super(putFilesBucket);
        this.repository = repository;
        this.codigoValidacaoJpaRepository = codigoValidacaoJpaRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.keycloakUserClient = keycloakUserClient;
        this.eventService = eventService;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(final String email) {

        return repository.findByEmail(email)
                .map(mapper::toUsuario)
                .or(Optional::empty);
    }

    @Override
    public Optional<Usuario> buscarPorId(final UUID anId) {

        return repository.findById(anId)
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
                            usuario.getSenha().getValue(),
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
    public Usuario atualizar(Usuario usuario) {

        var usuarioEntity = mapper.toUsuarioEntity(usuario);
        var usuarioEntitySaved = repository.save(usuarioEntity);

        return mapper.toUsuario(usuarioEntitySaved);
    }

    @Override
    public void criarValidacaoEmail(final Usuario usuario) {

        var codigoValidacao = CodigoValidacaoEntity.builder()
                .codigo(usuario.getCodigoConfirmacao())
                .validoAte(LocalDateTime.now().plusMinutes(waitingTime))
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

            return ResponseUsuarioDto.resumedOf(
                    u.getId().toString(),
                    u.getNome(),
                    u.getEmail(),
                    u.getSituacao(),
                    u.isEmailConfirmado());
        }).orElseThrow(() -> new RequestException(BAD_REQUEST, ERRO_INFORMACAO_INCONSISTENTE,
                UsuarioPersistence.class.getSimpleName()));

    }


    public String salvarAvatar(Usuario usuario, String fileName, String tipoImagem, BufferedImage image)
            throws IOException, URISyntaxException {

        var logoUrl = processAndSaveImage(bucketName, fileName, tipoImagem, image);

        if (!isBlank(logoUrl)) {

            var entity = mapper.toUsuarioEntity(usuario);
            entity.setAvatarUrl(logoUrl);
            entity.setAvatarFilename(fileName);

            log.info("Salvando informações do avatar para o Usuário : {}", entity);
            repository.save(entity);

            return logoUrl;
        }

        return EMPTY;
    }
}