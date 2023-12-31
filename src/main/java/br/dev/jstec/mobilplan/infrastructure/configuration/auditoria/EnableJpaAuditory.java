package br.dev.jstec.mobilplan.infrastructure.configuration.auditoria;

import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EnableJpaAuditory {

    @Bean
    public AuditorAware<UUID> auditorProvider() {

        return new AuditorAwareImpl();
    }
}
