package br.dev.jstec.mobilplan.infrastructure.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final long validityInMillisecondsForAccessToken = 3600000; // 1 hora
    private static final long validityInMillisecondsForRefreshToken = 28_800_000; // 8 horas, por exemplo
    private final CustomUserDetailsService customUserDetailsService;
    private Key key;
    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Authentication authentication) {

        return createToken(authentication, validityInMillisecondsForAccessToken);
    }

    public String createRefreshToken(Authentication authentication) {

        return createRefreshToken(authentication, validityInMillisecondsForRefreshToken);
    }

    private String createRefreshToken(Authentication authentication, long validityDuration) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + validityDuration);
        Map<String, Object> additionalInfo = new HashMap<>();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        additionalInfo.put("userId", userDetails.getId());
        additionalInfo.put("nome", userDetails.getNome());
        additionalInfo.put("roles", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .addClaims(additionalInfo) // Adiciona informações adicionais sem incluir roles
                .signWith(key)
                .setExpiration(validity)
                .compact();
    }

    private String createToken(Authentication authentication, long validityInMilliseconds) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + validityInMilliseconds);

        Map<String, Object> additionalInfo = new HashMap<>();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        additionalInfo.put("userId", userDetails.getId());
        additionalInfo.put("nome", userDetails.getNome());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .addClaims(additionalInfo)
                .claim("roles", authentication.getAuthorities().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()))
                .signWith(key)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        var principal = customUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
