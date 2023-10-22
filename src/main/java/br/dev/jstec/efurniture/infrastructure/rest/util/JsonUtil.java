package br.dev.jstec.efurniture.infrastructure.rest.util;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.exceptions.ErrorMessage;
import com.google.gson.Gson;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class JsonUtil {

    public static Optional<ErrorMessage> jsonToErrorMessage(String errorJson) {

        try {
            return of(new Gson().fromJson(errorJson, ErrorMessage.class));

        } catch (Exception e) {
            log.error("Erro ao converter Json. Dados: {}", errorJson, e);
            return empty();
        }
    }
}
