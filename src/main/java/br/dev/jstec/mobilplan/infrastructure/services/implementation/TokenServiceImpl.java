package br.dev.jstec.mobilplan.infrastructure.services.implementation;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_CODIGO_VALIDACAO_EXPIRADO;
import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_CODIGO_VALIDACAO_INVALIDO;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.Base64.getUrlDecoder;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import br.dev.jstec.mobilplan.infrastructure.services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${info.app.name}")
    private String app;

    @Override
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

    @Override
    public String validateToken(String token) {

        byte[] decodedKey = getUrlDecoder().decode(secretKey);
        var secretKeyBase = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
            .setSigningKey(secretKeyBase)
            .build()
            .parseClaimsJws(token);

        var expiration = jwsClaims.getBody().getExpiration();

        if (expiration.before(new Date())) {

            throw new RequestException(BAD_REQUEST, ERRO_CODIGO_VALIDACAO_EXPIRADO, TokenService.class.getSimpleName());

        }

        if (isBlank(jwsClaims.getBody().getSubject())) {

            throw new RequestException(BAD_REQUEST, ERRO_CODIGO_VALIDACAO_INVALIDO, TokenService.class.getSimpleName());

        }

        return jwsClaims.getBody().getSubject();
    }
}
