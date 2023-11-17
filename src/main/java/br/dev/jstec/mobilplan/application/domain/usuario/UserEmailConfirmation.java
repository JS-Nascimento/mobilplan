package br.dev.jstec.mobilplan.application.domain.usuario;

import br.dev.jstec.mobilplan.application.domain.events.DomainEvent;
import java.time.Instant;

public record UserEmailConfirmation(
    String email,
    String Nome,
    Instant occurredOn
) implements DomainEvent {

    public UserEmailConfirmation(final String email, final String nome) {
        this(email, nome, Instant.now());
    }

}
