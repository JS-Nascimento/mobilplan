package br.dev.jstec.mobilplan.infrastructure.configuration.properties.email;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
@Getter
@Setter
public class NoReplyEmailProperties implements InitializingBean {

    private String host;
    private int port;
    private String username;
    private String password;

    @Override
    public void afterPropertiesSet() {
        log.info(toString());
    }

    @Override
    public String toString() {
        return "NoReplyEmailProperties{"
           + "host='" + host + '\''
           + ", port=" + port
           + ", username='" + username + '\''
           + ", password='" + password + '\''
           + '}';
    }
}
