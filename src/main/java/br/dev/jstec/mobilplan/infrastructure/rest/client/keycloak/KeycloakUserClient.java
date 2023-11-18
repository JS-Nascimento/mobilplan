package br.dev.jstec.mobilplan.infrastructure.rest.client.keycloak;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_INFORMACAO_INCONSISTENTE;
import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_USUARIO_EXISTENTE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.valueOf;

import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakUserClient {

    public static final String USER_EXISTS_WITH_SAME_USERNAME = "User exists with same username";

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    private final Keycloak keycloak;

    public String createUser(String username, String email, String password,
        Set<String> groupsName) {

        var user = new UserRepresentation();
        user.setFirstName(username);
        user.setUsername(email);
        user.setEmail(email);
        user.setEmailVerified(false);
        user.setEnabled(false);
        user.setGroups(new ArrayList<>(groupsName));

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        var realmResource = keycloak.realm(keycloakRealm);
        var usersResource = realmResource.users();

        String userId;
        try (var response = usersResource.create(user)) {
            var errorMessage = response.readEntity(String.class);
            if (response.getStatus() == 409
                && errorMessage.contains(USER_EXISTS_WITH_SAME_USERNAME)) {

                throw new RequestException(BAD_REQUEST, ERRO_USUARIO_EXISTENTE,
                    this.getClass().getSimpleName());

            } else if (response.getStatus() != 201) {

                throw new RequestException(valueOf(response.getStatus()), ERRO_INFORMACAO_INCONSISTENTE,
                    this.getClass().getSimpleName());

            }

            userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        }

        return userId;
    }

    public void deleteUser(String userId) {

        var realmResource = keycloak.realm(keycloakRealm);
        var usersResource = realmResource.users();
        var userResource = usersResource.get(userId);
        userResource.remove();
    }
}
