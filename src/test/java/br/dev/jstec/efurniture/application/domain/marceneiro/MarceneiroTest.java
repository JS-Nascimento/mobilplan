package br.dev.jstec.efurniture.application.domain.marceneiro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MarceneiroTest {

    @Test
    @DisplayName("Deve instaciar um marceneiro")
    void testCriarMarceneiro() {

        final var marceneiro = MarceneiroFixture.build();

        final var resultado = Marceneiro.createOf(
                marceneiro.nome().value(),
                marceneiro.nomeComercial().value(),
                marceneiro.tipoCliente(),
                marceneiro.email().value(),
                marceneiro.telefones(),
                marceneiro.endereco(),
                AuditInfo.fromUuid(marceneiro.auditInfo().createdBy()),
                marceneiro.logomarca().fileName()
        );

        assertNotNull(resultado.marceneiroId());
        assertEquals(marceneiro.nome().value(), resultado.nome().value());
        assertEquals(marceneiro.nomeComercial().value(), resultado.nomeComercial().value());
        assertEquals(marceneiro.tipoCliente(), resultado.tipoCliente());
        assertEquals(marceneiro.email().value(), resultado.email().value());
        assertEquals(marceneiro.telefones(), resultado.telefones());
    }
}