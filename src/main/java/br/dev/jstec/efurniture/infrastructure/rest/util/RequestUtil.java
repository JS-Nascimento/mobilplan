package br.dev.jstec.efurniture.infrastructure.rest.util;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@NoArgsConstructor(access = PRIVATE)
public class RequestUtil {

    public static HttpEntity<String> getRequestEntity() {

        var requestHeaders = new HttpHeaders();
        requestHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return new HttpEntity<>(requestHeaders);
    }
}
