package br.dev.jstec.mobilplan.infrastructure.configuration.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakClientConfiguration {

    @Value("${keycloak.host}")
    private String keycloakHost;

    @Value("${keycloak.username}")
    private String keycloakUsername;

    @Value("${keycloak.password}")
    private String keycloakPassword;

    @Bean
    public Keycloak keycloak() {

        return KeycloakBuilder.builder()
            .serverUrl(keycloakHost)
            .realm("master")
            .username(keycloakUsername)
            .password(keycloakPassword)
            .clientId("admin-cli")
            .build();
    }
}
