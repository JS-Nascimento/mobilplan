package br.dev.jstec.mobilplan.infrastructure.configuration.security;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_TOKEN_INVALIDO;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserContext {

    private static String getUserLoggedByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomJwtAuthenticationToken userDetails) {
            return userDetails.getUserId();
        }
        throw new RequestException(UNAUTHORIZED, ERRO_TOKEN_INVALIDO, UserContext.class.getName());
    }

    public static UUID getUserLogged() {
        return UUID.fromString(getUserLoggedByToken());
    }
}
