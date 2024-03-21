package br.dev.jstec.mobilplan.infrastructure.configuration.security;

import br.dev.jstec.mobilplan.infrastructure.persistence.entity.usuario.UsuarioEntity;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UsuarioEntity usuario;

    public CustomUserDetails(UsuarioEntity usuario) {
        this.usuario = usuario;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (usuario.getRoles().isEmpty()) {
            return List.of();
        }
        return usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .toList();
    }

    public String getNome() {
        return usuario.getNome();
    }

    public String getAvatar() {
        return usuario.getAvatarUrl();
    }

    public String getId() {
        return usuario.getId().toString();
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.isEmailConfirmado();
    }
}
