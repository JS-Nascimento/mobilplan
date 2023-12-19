package br.dev.jstec.mobilplan.domain.usuario;

import static java.lang.String.valueOf;
import static java.util.random.RandomGenerator.getDefault;

import br.dev.jstec.mobilplan.domain.Events;
import br.dev.jstec.mobilplan.domain.events.DomainEvent;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.domain.valueobject.Nome;
import br.dev.jstec.mobilplan.domain.valueobject.Senha;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
public class Usuario extends Events {

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

    public static Usuario createPrincipalOf(
            String nome,
            String email,
            String senha) {

        return new Usuario(
                new Nome(nome),
                new Email(email),
                false,
                new Senha(senha),
                Set.of("mobilplan_usuario_group"),
                Situacao.INATIVO,
                null
        );

    }

    public static Usuario getOf(Usuario usuario) {
        return new Usuario(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.telefone,
                usuario.roles,
                usuario.situacao,
                usuario.avatarFilename,
                usuario.avatarUrl,
                usuario.createdAt,
                usuario.updatedBy,
                usuario.updatedAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static String validationCodeGenerate() {

        return valueOf(getDefault().nextInt(100000, 999999));
    }
}
