package br.dev.jstec.mobilplan.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.dev.jstec.mobilplan.domain.util.DomainConstants;
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