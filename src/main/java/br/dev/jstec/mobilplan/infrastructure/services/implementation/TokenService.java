package br.dev.jstec.mobilplan.infrastructure.services.implementation;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.Base64.getUrlDecoder;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${info.app.name}")
    private String app;


    public String generateEmailVerificationToken(String email) {

        long currentTimeMillis = System.currentTimeMillis();
        int tokenDurationMillis = 1800 * 1000;

        byte[] decodedKey = getUrlDecoder().decode(secretKey);
        SecretKey secretKeyBase = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");

        log.info("email: {}", email);
        return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(email)
            .setIssuer(app)
            .setIssuedAt(new Date(currentTimeMillis))
            .setExpiration(new Date(currentTimeMillis + tokenDurationMillis))
            .signWith(secretKeyBase, HS512)
            .compact();
    }
}
