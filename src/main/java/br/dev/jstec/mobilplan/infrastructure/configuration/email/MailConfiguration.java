package br.dev.jstec.mobilplan.infrastructure.configuration.email;

import br.dev.jstec.mobilplan.infrastructure.configuration.annotations.UserConfirmationEmail;
import br.dev.jstec.mobilplan.infrastructure.configuration.properties.email.NoReplyEmailProperties;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@RequiredArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
public class MailConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "spring.mail")
    @UserConfirmationEmail
    NoReplyEmailProperties emailConfirmationNoReplyProperties() {
        return new NoReplyEmailProperties();
    }

    @Configuration
    static class Admin {

        @Bean
        JavaMailSender mailSender(
            @UserConfirmationEmail NoReplyEmailProperties noReplyEmailProperties) {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(noReplyEmailProperties.getHost());
            mailSender.setPort(noReplyEmailProperties.getPort());
            mailSender.setUsername(noReplyEmailProperties.getUsername());
            mailSender.setPassword(noReplyEmailProperties.getPassword());

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.connectiontimeout", 5000);
            props.put("mail.smtp.timeout", 5000);
            props.put("mail.smtp.writetimeout", 5000);
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.mime.charset", "utf-8");

            return mailSender;
        }
    }
}
