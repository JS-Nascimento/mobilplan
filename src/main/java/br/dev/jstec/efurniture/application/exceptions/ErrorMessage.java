package br.dev.jstec.efurniture.application.exceptions;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(NON_NULL)
public class ErrorMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -9106756257499068077L;

    String code;
    String msg;
    String serviceName;
    String response;
}
