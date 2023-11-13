package br.dev.jstec.mobilplan.infrastructure.exceptions;

import static br.dev.jstec.mobilplan.infrastructure.rest.util.ConstantsLog.MSG_REQUESTEXCEPTION;
import static java.text.MessageFormat.format;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RequestException extends RuntimeException {

    private final HttpStatusCode httpStatus;
    private final ErroTecnico responseBody;
    private final String serviceName;

    public RequestException(HttpStatusCode httpStatus, ErroTecnico responseBody, String serviceName,
        Throwable cause) {

        super(format(MSG_REQUESTEXCEPTION, serviceName, httpStatus, responseBody), cause);
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.serviceName = serviceName;
    }

    public RequestException(HttpStatusCode httpStatus, ErroTecnico responseBody, String serviceName) {

        super(format(MSG_REQUESTEXCEPTION, serviceName, httpStatus, responseBody));
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.serviceName = serviceName;
    }
}
