package br.dev.jstec.efurniture.application.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DomainConstantsTest {

    @Test
    @DisplayName("DDI_BRASIL deve ser igual a +55")
    void ddiBrasilShouldBePlus55() {

        var expected = "+55";
        var actual = DomainConstants.DDI_BRASIL;

        assertEquals(expected, actual);
    }
}