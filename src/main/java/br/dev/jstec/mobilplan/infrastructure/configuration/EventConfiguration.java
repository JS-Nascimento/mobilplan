package br.dev.jstec.mobilplan.infrastructure.configuration;

import br.dev.jstec.mobilplan.infrastructure.configuration.annotations.EmailConfirmationQueue;
import br.dev.jstec.mobilplan.infrastructure.configuration.properties.amqp.QueueProperties;
import br.dev.jstec.mobilplan.infrastructure.services.EventService;
import br.dev.jstec.mobilplan.infrastructure.services.implementation.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    @EmailConfirmationQueue
    @ConditionalOnMissingBean
    EventService userEmailConfirmation(
        @EmailConfirmationQueue final QueueProperties props,
        final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
