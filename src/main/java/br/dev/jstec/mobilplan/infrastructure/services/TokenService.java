package br.dev.jstec.mobilplan.infrastructure.services;

public interface TokenService {

        String generateEmailVerificationToken(String email);

        String validateToken(String token);

}
