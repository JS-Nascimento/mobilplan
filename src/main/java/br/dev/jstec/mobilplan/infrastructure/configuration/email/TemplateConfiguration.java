package br.dev.jstec.mobilplan.infrastructure.configuration.email;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TemplateConfiguration {

    private static final String RESOURCE_PATH = "static/images/";
    private final JavaMailSender mailSender;

    @Bean
    public MimeMessageHelper mimeMessageHelper(
        @Value("${info.app.name}") String setFromName,
        @Value("${spring.mail.username}") String setFromEmail
    ) throws MessagingException, UnsupportedEncodingException {

        MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), true,
            "UTF-8");

        helper.setFrom(setFromEmail, setFromName);

        var resource = new ClassPathResource(RESOURCE_PATH + "mobilplan-ia.jpg");
        helper.addInline("logoImage", resource);

        var twitter = new ClassPathResource(RESOURCE_PATH + "twitter2x.png");
        helper.addInline("twitterIcon", twitter);

        var facebook = new ClassPathResource(RESOURCE_PATH + "facebook2x.png");
        helper.addInline("facebookIcon", facebook);

        var linkedin = new ClassPathResource(RESOURCE_PATH + "linkedin2x.png");
        helper.addInline("linkedinIcon", linkedin);

        var instagram = new ClassPathResource(RESOURCE_PATH + "instagram2x.png");
        helper.addInline("instagramIcon", instagram);

        return helper;
    }
}
