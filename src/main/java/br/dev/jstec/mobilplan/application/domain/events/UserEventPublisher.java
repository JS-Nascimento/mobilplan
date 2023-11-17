package br.dev.jstec.mobilplan.application.domain.events;

@FunctionalInterface
public interface UserEventPublisher {
    void publishEvent(DomainEvent event);
}
