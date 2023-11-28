package br.dev.jstec.mobilplan.infrastructure.amqp;

import static br.dev.jstec.mobilplan.infrastructure.configuration.json.Json.readValue;

import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.UserEmailConfirmationDto;
import br.dev.jstec.mobilplan.infrastructure.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class EmailConfirmationListener {

    private final EmailService emailService;
    static final String LISTENER_ID = "emailConfirmationListener";
    private static final String EMAIL_TEMPLATE = "email-confirmation-code";
    private static final String EMAIL_SUBJECT = "Confirmação de email cadastrado.";

    @RabbitListener(id = LISTENER_ID, queues = "email.confirmation.queue")
    public void onEmailConfirmationMessage(@Payload final String message) {

        final var aResult = readValue(message, UserEmailConfirmationDto.class);
        log.info("[message:email.listener.income] [payload:{}]", message);

        emailService.sendEmail(
            aResult.email(),
            aResult.nome(),
            aResult.codigoConfirmacao(),
            EMAIL_SUBJECT,
            EMAIL_TEMPLATE);
    }
}
