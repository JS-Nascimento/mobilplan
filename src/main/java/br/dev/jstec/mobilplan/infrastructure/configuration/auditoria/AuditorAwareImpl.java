package br.dev.jstec.mobilplan.infrastructure.configuration.auditoria;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_CODIGO_VALIDACAO_INVALIDO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {

        var authentication = getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
            || !(authentication instanceof JwtAuthenticationToken jwtToken)) {
            return Optional.empty();
        }

        var subject = jwtToken.getToken().getClaimAsString("userId");

        try {

            var userUuid = UUID.fromString(subject);
            return Optional.of(userUuid);

        } catch (IllegalArgumentException e) {

            throw new RequestException(BAD_REQUEST, ERRO_CODIGO_VALIDACAO_INVALIDO, this.getClass().getSimpleName(),
                e.getCause());
        }
    }
}

