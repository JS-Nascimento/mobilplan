package br.dev.jstec.mobilplan.infrastructure.services;

public interface EmailService {

    void sendEmailConfirmation(String email, String nome);

}
