package br.dev.jstec.mobilplan.application.domain;

import br.dev.jstec.mobilplan.application.domain.events.DomainEvent;
import br.dev.jstec.mobilplan.application.domain.events.UserEventPublisher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Events {

    private final List<DomainEvent> domainEvents;

    protected Events(List<DomainEvent> domainEvents) {
        this.domainEvents = domainEvents != null ? domainEvents : new ArrayList<>();
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void publishDomainEvents(final UserEventPublisher publisher) {
        if (publisher == null) {
            return;
        }

        getDomainEvents()
            .forEach(publisher::publishEvent);

        this.domainEvents.clear();
    }

    public void registerEvent(final DomainEvent event) {
        if (event == null) {
            return;
        }

        this.domainEvents.add(event);
    }
}
