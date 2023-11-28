package br.dev.jstec.mobilplan.infrastructure.configuration.email;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
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

        Map<String, String> inlineImages = Map.of(
            "instagramIcon", "instagram2x.png",
            "twitterIcon", "twitter2x.png",
            "facebookIcon", "facebook2x.png",
            "linkedinIcon", "linkedin2x.png",
            "logoImage", "mobilplan-ia.jpg"
        );

        for (Map.Entry<String, String> entry : inlineImages.entrySet()) {
            ClassPathResource imageResource = new ClassPathResource(RESOURCE_PATH + entry.getValue());
            helper.addInline(entry.getKey(), imageResource);
        }

        helper.setFrom(setFromEmail, setFromName);

        return helper;
    }
}
