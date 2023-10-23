package br.dev.jstec.efurniture.application.domain.usuario;

import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.domain.valueobject.Nome;
import br.dev.jstec.efurniture.application.domain.valueobject.Senha;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@Setter
@Value
public class Usuario {

    UUID id;
    Nome nome;
    Email email;
    Telefone telefone;
    Senha senha;
    Set<Role> roles;
    Set<Authority> authorities;
    Situacao situacao;
    String avatarFilename;
    String avatarUrl;
    UUID createdBy;
    LocalDateTime createdAt;
    UUID updatedBy;
    LocalDateTime updatedAt;
}
