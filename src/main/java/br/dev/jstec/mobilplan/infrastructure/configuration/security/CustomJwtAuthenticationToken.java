package br.dev.jstec.mobilplan.infrastructure.configuration.security;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Getter
public class CustomJwtAuthenticationToken extends JwtAuthenticationToken {
    private final String userId;

    public CustomJwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities, String userId) {
        super(jwt, authorities);
        this.userId = userId;
    }
}
