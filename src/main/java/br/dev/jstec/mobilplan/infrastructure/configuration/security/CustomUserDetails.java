package br.dev.jstec.mobilplan.infrastructure.configuration.security;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class CustomUserDetails extends User {
    private final String userId;

    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities, String userId) {
        super(username, password, authorities);
        this.userId = userId;
    }
}
