package br.dev.jstec.efurniture.infrastructure.rest.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

class RequestUtilTest {

    @Test
    @DisplayName("Deve retornar HttpEntity com Content-Type application/json")
    void shouldReturnHttpEntityWithJsonContentType() {

        HttpEntity<String> httpEntity = RequestUtil.getRequestEntity();

        HttpHeaders headers = httpEntity.getHeaders();
        assertThat(headers).isNotNull();
        assertThat(((HttpHeaders) headers).get(CONTENT_TYPE)).containsExactly(APPLICATION_JSON_VALUE);
    }
}