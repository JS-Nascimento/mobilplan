package br.dev.jstec.mobilplan.domain.events;

@FunctionalInterface
public interface UserEventPublisher {
    void publishEvent(DomainEvent event);
}
