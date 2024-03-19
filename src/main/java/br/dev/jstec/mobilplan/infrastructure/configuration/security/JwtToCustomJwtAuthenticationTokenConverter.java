package br.dev.jstec.mobilplan.infrastructure.configuration.security;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtToCustomJwtAuthenticationTokenConverter implements Converter<Jwt, CustomJwtAuthenticationToken> {
    @Override
    public CustomJwtAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = jwt.getClaimAsStringList("roles").stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());

        var nome = jwt.getClaimAsString("nome");
        var userId = jwt.getClaimAsString("userId");
        return new CustomJwtAuthenticationToken(jwt, authorities, userId, nome, jwt.getSubject());
    }
}

