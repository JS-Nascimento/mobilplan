package br.dev.jstec.efurniture.infrastructure.exceptions;

import static br.dev.jstec.efurniture.infrastructure.rest.util.ConstantsLog.MSG_REQUESTEXCEPTION;
import static java.text.MessageFormat.format;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RequestException extends RuntimeException {

    private final HttpStatusCode httpStatus;
    private final String responseBody;
    private final String serviceName;

    public RequestException(HttpStatusCode httpStatus, String responseBody, String serviceName,
        Throwable cause) {

        super(format(MSG_REQUESTEXCEPTION, serviceName, httpStatus, responseBody), cause);
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.serviceName = serviceName;
    }

    public RequestException(HttpStatusCode httpStatus, String responseBody, String serviceName) {

        super(format(MSG_REQUESTEXCEPTION, serviceName, httpStatus, responseBody));
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
        this.serviceName = serviceName;
    }
}
