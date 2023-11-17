package br.dev.jstec.mobilplan.infrastructure.services.implementation;

import br.dev.jstec.mobilplan.infrastructure.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
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

    @Override
    public void sendEmailConfirmation(String email, String nome) {

        log.info("Enviando email de confirmação para {}", email);
        try {

            var context = new Context();
            context.setVariable("name", nome);
            context.setVariable("email", email);

            var body = templateEngine.process("UserEmailConfirmation", context);

            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("noreply@mobilplan.com.br", "Mobilplan");
            helper.setTo(email);
            helper.setSubject("Confirmação do email de Cadastro");
            helper.setText(body, true);

            var resource = new ClassPathResource("static/images/mobilplan-ia.jpg");
            helper.addInline("logoImage", resource);

            var twitter = new ClassPathResource("static/images/twitter2x.png");
            helper.addInline("twitterIcon", twitter);

            var facebook = new ClassPathResource("static/images/facebook2x.png");
            helper.addInline("facebookIcon", facebook);

            var linkedin = new ClassPathResource("static/images/linkedin2x.png");
            helper.addInline("linkedinIcon", linkedin);

            var instagram = new ClassPathResource("static/images/instagram2x.png");
            helper.addInline("instagramIcon", instagram);

            mailSender.send(mimeMessage);

        } catch (Exception e) {

            log.error("Erro ao enviar email de confirmação para {}", email, e);
        }
    }
}
