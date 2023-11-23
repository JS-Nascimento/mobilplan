package br.dev.jstec.mobilplan.infrastructure.services.implementation;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import br.dev.jstec.mobilplan.infrastructure.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateService implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    private final MimeMessageHelper helper;

    @Override
    public void sendEmail(String email, String nome, String subject, String template) {

       sendEmail(email, nome, subject, template, null);
    }

    @Override
    public void sendEmail(String email, String nome, String subject, String template,
        String token) {

        log.info("Enviando email de confirmação para {}", email);
        try {

            var context = new Context();
            context.setVariable("name", nome);
            context.setVariable("email", email);
            context.setVariable("token", nonNull(token) ? token : EMPTY);

            var body = templateEngine.process(template, context);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(helper.getMimeMessage());

        } catch (Exception e) {

            log.error("Erro ao enviar email para {}", email, e);
        }
    }
}
