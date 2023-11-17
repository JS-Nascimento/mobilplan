package br.dev.jstec.mobilplan.infrastructure.configuration.properties.amqp;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
@Getter
@Setter
public class QueueProperties implements InitializingBean {

    private String exchange;
    private String routingKey;
    private String queue;

    @Override
    public void afterPropertiesSet() {
        log.info(toString());
    }

    @Override
    public String toString() {
        return "QueueProperties{"
            + "exchange='" + exchange + '\''
            + ", routingKey='" + routingKey + '\''
            + ", queue='" + queue + '\''
            + '}';
    }

}
