package br.dev.jstec.mobilplan.infrastructure.configuration.amqp;

import br.dev.jstec.mobilplan.infrastructure.configuration.annotations.EmailConfirmationQueue;
import br.dev.jstec.mobilplan.infrastructure.configuration.annotations.UserEvents;
import br.dev.jstec.mobilplan.infrastructure.configuration.properties.amqp.QueueProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

    @Bean
    @ConfigurationProperties("amqp.queues.email-confirmation")
    @EmailConfirmationQueue
    QueueProperties emailConfirmationQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @Qualifier("UserEvents")
    public DirectExchange userEventsExchange(@EmailConfirmationQueue QueueProperties props) {
        return new DirectExchange(props.getExchange());
    }

    @Configuration
    static class Admin {

        @Bean
        @EmailConfirmationQueue
        Queue emailConfirmationQueue(@EmailConfirmationQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @EmailConfirmationQueue
        Binding emailConfirmationQueueBinding(
            @UserEvents DirectExchange exchange,
            @EmailConfirmationQueue Queue queue,
            @EmailConfirmationQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }
    }
}
