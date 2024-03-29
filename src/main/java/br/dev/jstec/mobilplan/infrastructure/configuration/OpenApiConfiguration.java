package br.dev.jstec.mobilplan.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "MobilPlan API",
                version = "1.0.0",
                description = "Aplicativo de gestão de orçamento de móveis sob medida.",
                contact = @Contact(
                        name = "JS Tecnologia e Sistemas Ltda",
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
