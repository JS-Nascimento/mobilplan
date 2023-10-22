package br.dev.jstec.efurniture.infrastructure.rest.util;

import static br.dev.jstec.efurniture.infrastructure.rest.util.JsonUtil.jsonToErrorMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.dev.jstec.efurniture.application.exceptions.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JsonUtilTest {

    private static final String JSON_INVALIDO = "\"codigo\":\"ABCD\",\"mensagem\"\"teste\"}";
    private static final String JSON = "{\"code\":\"GARANTIAS-API-1234\",\"msg\":\"Mensagem de Teste\","
        + "\"serviceName\":\"service\",\"response\":\"Response de Teste\"}";
    private static final String CODE = "GARANTIAS-API-1234";
    private static final String MSG = "Mensagem de Teste";
    private static final String SERVICE_NAME = "service";
    private static final String RESPONSE = "Response de Teste";

    @Test
    @DisplayName("Deve retornar optional vazio quando json for inválido")
    void jsonToErrorMessageDeveRetornarOptionalVazio() {

        assertTrue(jsonToErrorMessage(JSON_INVALIDO).isEmpty());
    }

    @Test
    @DisplayName("Deve retornar optional contendo ErrorMessage com sucesso")
    void jsonToErrorMessageDeveRetornarErrorMessage() {

        var errorMessage = jsonToErrorMessage(JSON);

        var expect =
            ErrorMessage.builder()
                .code(CODE)
                .msg(MSG)
                .serviceName(SERVICE_NAME)
                .response(RESPONSE)
                .build();

        assertThat(errorMessage).isNotEmpty();
        assertEquals(expect, errorMessage.get());
    }
}