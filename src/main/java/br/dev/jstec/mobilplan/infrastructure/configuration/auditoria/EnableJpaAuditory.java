package br.dev.jstec.mobilplan.infrastructure.configuration.auditoria;

import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware",  dateTimeProviderRef = "dateTimeProvider")
@EnableJdbcAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class EnableJpaAuditory {

    @Bean
    public AuditorAware<UUID> auditorProvider() {

        return new AuditorAwareImpl();
    }
}
