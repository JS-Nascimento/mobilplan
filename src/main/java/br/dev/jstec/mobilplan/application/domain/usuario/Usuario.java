package br.dev.jstec.mobilplan.application.domain.usuario;

import static br.dev.jstec.mobilplan.application.domain.usuario.Situacao.INATIVO;

import br.dev.jstec.mobilplan.application.domain.valueobject.Email;
import br.dev.jstec.mobilplan.application.domain.valueobject.Nome;
import br.dev.jstec.mobilplan.application.domain.valueobject.Senha;
import br.dev.jstec.mobilplan.application.domain.valueobject.Telefone;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
@NoArgsConstructor()
public class Usuario {

    UUID id;
    Nome nome;
    Email email;
    Senha senha;
    Telefone telefone;
    Set<String> roles;
    Situacao situacao;
    String avatarFilename;
    String avatarUrl;
    LocalDateTime createdAt;
    UUID updatedBy;
    LocalDateTime updatedAt;

    private Usuario(
        UUID id,
        Nome nome,
        Email email,
        Senha senha,
        Telefone telefone,
        Set<String> roles,
        Situacao situacao,
        String avatarFilename,
        String avatarUrl,
        LocalDateTime createdAt,
        UUID updatedBy,
        LocalDateTime updatedAt) {

        this.id = id;
        this.nome = nome;
        this.email = email;
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

    private Usuario(
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

    public static Usuario createPrincipalOf(
        String nome,
        String email,
        String senha) {

        return new Usuario(
            null,
            new Nome(nome),
            new Email(email),
            new Senha(senha),
            null,
            Set.of("ROLE_MOBILPLAN_USUARIO"),
            INATIVO,
            null,
            null,
            null,
            null,
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
