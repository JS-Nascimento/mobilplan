package br.dev.jstec.mobilplan.infrastructure.services;

public interface EmailService {

    void sendEmail(String email, String nome, String subject, String template);

    void sendEmail(String email, String nome, String subject, String template, String token);

}
