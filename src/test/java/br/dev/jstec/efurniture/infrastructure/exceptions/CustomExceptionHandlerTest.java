package br.dev.jstec.efurniture.infrastructure.exceptions;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_INFORMACAO_INVALIDA;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarObject;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarString;
import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.dev.jstec.efurniture.application.constraints.ConstraintException;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

    private static final String SERVICE = "service";
    private static final String CODE = "EFURNITURE-001";
    private static final String CODE_ERRO_PADRAO_INCORRETO = "EFURNITURE-501";
    private static final String MSG_ERRO_PADRAO_INCORRETO = "Erro fora do padrão esperado";
    private static final String MSG = "Erro ao receber requisição. Informações inválidas.";
    private static final String BODY =
        "{\"code\":\"EFURNITURE-001\",\"msg\":\"Erro ao receber requisição. Informações inválidas.\"}";
    private static final String BODY_SEM_CODE =
        "{\"msg\":\"Erro ao receber requisição. Informações inválidas.\"}";

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @Test
    @DisplayName("Deve tratar exceção do tipo ConstraintViolationException e retornar HttpStatus 400")
    void handleConstraintViolationExceptionDeveRetornar400() {

        var erroResponseHandler =
            customExceptionHandler.handleConstraintViolationException(
                new ConstraintViolationException(MSG, null));

        assertEquals(BAD_REQUEST, erroResponseHandler.getStatusCode());
        assertNotNull(erroResponseHandler.getBody());
        assertEquals(ERRO_INFORMACAO_INVALIDA.getCode(), erroResponseHandler.getBody().getCode());
        assertEquals(format(ERRO_INFORMACAO_INVALIDA.getMsg(), MSG),
            erroResponseHandler.getBody().getMsg());
    }

    @Test
    @DisplayName("Deve tratar exceção do tipo ConstraintException e retornar HttpStatus 400")
    void handleConstraintExceptionDeveRetornar400() {

        var parameter = gerarString();
        var erroResponseHandler =
            customExceptionHandler.handleConstraintViolationException(
                new ConstraintException(parameter));

        assertEquals(BAD_REQUEST, erroResponseHandler.getStatusCode());
        assertNotNull(erroResponseHandler.getBody());
        assertEquals(ERRO_INFORMACAO_INVALIDA.getCode(), erroResponseHandler.getBody().getCode());
        assertEquals(format(ERRO_INFORMACAO_INVALIDA.getMsg(), parameter),
            erroResponseHandler.getBody().getMsg());
    }

    @Test
    @DisplayName("Deve tratar exceção do tipo BusinessException e retornar HttpStatus 400")
    void handleBusinessExceptionDeveRetornar400() {

        var error = gerarObject(ErroDeNegocio.class);
        var erroResponseHandler =
            customExceptionHandler.handleBusinessException(new BusinessException(error));

        assertEquals(BAD_REQUEST, erroResponseHandler.getStatusCode());
        assertNotNull(erroResponseHandler.getBody());
        assertEquals(error.getCode(), erroResponseHandler.getBody().getCode());
        assertEquals(error.getMsg(), erroResponseHandler.getBody().getMsg());
    }

    @Test
    @DisplayName("Deve tratar exceção do tipo RequestException com sucesso")
    void handleRequestExceptionComSucesso() {

        var httpStatusCodeBadRequest = BAD_REQUEST;
        var requestException = new RequestException(httpStatusCodeBadRequest, BODY, SERVICE,
            new HttpClientErrorException(httpStatusCodeBadRequest));
        var erroResponseHandler = customExceptionHandler.handleRequestException(requestException);

        assertEquals(BAD_REQUEST, erroResponseHandler.getStatusCode());
        assertNotNull(erroResponseHandler.getBody());
        assertEquals(CODE, erroResponseHandler.getBody().getCode());
        assertEquals(MSG, erroResponseHandler.getBody().getMsg());
        assertEquals(SERVICE, erroResponseHandler.getBody().getServiceName());
    }

    @Test
    @DisplayName("Deve tratar exceção do tipo RequestException quando não houver o atributo 'code' com sucesso")
    void handleRequestExceptionComErroForaDoPadrao() {

        var httpStatusCodeBadRequest = BAD_REQUEST;
        var requestException = new RequestException(httpStatusCodeBadRequest, BODY_SEM_CODE,
            SERVICE,
            new HttpClientErrorException(httpStatusCodeBadRequest));
        var erroResponseHandler = customExceptionHandler.handleRequestException(requestException);

        assertEquals(BAD_REQUEST, erroResponseHandler.getStatusCode());
        assertNotNull(erroResponseHandler.getBody());
        assertEquals(CODE_ERRO_PADRAO_INCORRETO, erroResponseHandler.getBody().getCode());
        assertEquals(MSG_ERRO_PADRAO_INCORRETO, erroResponseHandler.getBody().getMsg());
        assertEquals(SERVICE, erroResponseHandler.getBody().getServiceName());
        assertEquals(BODY_SEM_CODE, erroResponseHandler.getBody().getResponse());
    }
}