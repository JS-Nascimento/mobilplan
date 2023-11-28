package br.dev.jstec.mobilplan.application.domain.usuario;

import br.dev.jstec.mobilplan.application.domain.events.DomainEvent;
import java.time.Instant;

public record UserEmailConfirmation(
    String id,
    String email,
    String nome,
    String codigoConfirmacao,
    Instant occurredOn

) implements DomainEvent {

    public UserEmailConfirmation(
        final String id,
        final String email,
        final String nome,
        final String codigoConfirmacao
    ) {
        this(id, email, nome, codigoConfirmacao, Instant.now());
    }

}
