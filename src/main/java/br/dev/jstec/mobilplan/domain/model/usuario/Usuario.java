package br.dev.jstec.mobilplan.domain.model.usuario;

import static java.lang.String.valueOf;
import static java.util.UUID.fromString;
import static java.util.random.RandomGenerator.getDefault;

import br.dev.jstec.mobilplan.domain.Events;
import br.dev.jstec.mobilplan.domain.events.DomainEvent;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.domain.valueobject.Nome;
import br.dev.jstec.mobilplan.domain.valueobject.Senha;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Usuario extends Events {

    @Include
    UUID id;
    Nome nome;
    Email email;
    boolean emailConfirmado;
    Senha senha;
    Telefone telefone;
    Set<String> roles;
    Situacao situacao;
    String avatarFilename;
    String avatarUrl;
    LocalDateTime createdAt;
    UUID updatedBy;
    LocalDateTime updatedAt;
    String codigoConfirmacao;

    public Usuario() {
        super(null);
    }

    protected Usuario(
            UUID id,
            Nome nome,
            Email email,
            boolean emailConfirmado,
            Senha senha,
            Telefone telefone,
            Set<String> roles,
            Situacao situacao,
            String avatarFilename,
            String avatarUrl,
            LocalDateTime createdAt,
            UUID updatedBy,
            LocalDateTime updatedAt,
            List<DomainEvent> domainEvents) {

        super(domainEvents);
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.emailConfirmado = emailConfirmado;
        this.senha = senha;
        this.telefone = telefone;
        this.roles = roles;
        this.situacao = situacao;
        this.avatarFilename = avatarFilename;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    protected Usuario(
            UUID id,
            Nome nome,
            Email email,
            boolean emailConfirmado,
            Senha senha,
            Telefone telefone,
            Set<String> roles,
            Situacao situacao,
            String avatarFilename,
            String avatarUrl,
            LocalDateTime createdAt,
            UUID updatedBy,
            LocalDateTime updatedAt) {
        super(null);

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.emailConfirmado = emailConfirmado;
        this.senha = senha;
        this.telefone = telefone;
        this.roles = roles;
        this.situacao = situacao;
        this.avatarFilename = avatarFilename;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    protected Usuario(
            UUID id,
            Nome nome,
            Email email,
            Telefone telefone,
            Senha senha,
            Set<String> roles,
            Situacao situacao,
            String avatarFilename,
            String avatarUrl,
            LocalDateTime createdAt,
            UUID updatedBy,
            LocalDateTime updatedAt) {
        super(null);

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.roles = roles;
        this.situacao = situacao;
        this.avatarFilename = avatarFilename;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    private Usuario(
            Nome nome,
            Email email,
            boolean emailConfirmado,
            Senha senha,
            Set<String> roles,
            Situacao situacao,
            List<DomainEvent> domainEvents) {

        super(domainEvents);
        this.nome = nome;
        this.email = email;
        this.emailConfirmado = emailConfirmado;
        this.senha = senha;
        this.roles = roles;
        this.situacao = situacao;

        validationCodeGenerate();
    }

    private Usuario(
            UUID id,
            Nome nome,
            Email email,
            Telefone telefone) {

        super(null);
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public static Usuario createPrincipalOf(
            String nome,
            String email,
            String senha) {

        return new Usuario(
                new Nome(nome),
                new Email(email),
                false,
                Senha.ofPureText(senha),
                Set.of("mobilplan_usuario"),
                Situacao.INATIVO,
                null
        );

    }

    public static Usuario canonicalOf(
            UUID id,
            Nome nome,
            Email email,
            boolean emailConfirmado,
            Senha senha,
            Telefone telefone,
            Set<String> roles,
            Situacao situacao,
            String avatarFilename,
            String avatarUrl,
            LocalDateTime createdAt,
            UUID updatedBy,
            LocalDateTime updatedAt,
            List<DomainEvent> domainEvents) {

        return new Usuario(
                id,
                new Nome(nome.value()),
                new Email(email.value()),
                emailConfirmado,
                Senha.ofHashed(senha.getValue()),
                telefone,
                roles,
                situacao,
                avatarFilename,
                avatarUrl,
                createdAt,
                updatedBy,
                updatedAt,
                domainEvents
        );
    }

    public static Usuario with(
            String id,
            String nome,
            String email,
            Telefone telefone) {

        return new Usuario(
                fromString(id),
                new Nome(nome),
                new Email(email),
                telefone
        );
    }

    public static Usuario getOf(Usuario usuario) {
        return new Usuario(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.telefone,
                usuario.senha,
                usuario.roles,
                usuario.situacao,
                usuario.avatarFilename,
                usuario.avatarUrl,
                usuario.createdAt,
                usuario.updatedBy,
                usuario.updatedAt
        );
    }

    public static String validationCodeGenerate() {

        return valueOf(getDefault().nextInt(100000, 999999));
    }
}
