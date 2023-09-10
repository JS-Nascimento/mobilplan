package br.dev.jstec.basicproject.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Projeto básico.",
                version = "1.0.0",
                description = "Projeto com implementações básicas para iniciar qualquer projeto.",
                contact = @Contact(
                        name = "JS Tecnologia",
                        email = "jorge@jstec.dev.br"
                ),
                license = @License(
                        name = "MIT Licence"
                )
        ),
        servers = @Server(
                url = "/"
        )
)
@Configuration
class OpenApiConfiguration {
}
