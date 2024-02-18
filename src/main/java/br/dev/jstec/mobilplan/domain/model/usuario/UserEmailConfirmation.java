package br.dev.jstec.mobilplan.domain.model.usuario;

import br.dev.jstec.mobilplan.domain.events.DomainEvent;
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
