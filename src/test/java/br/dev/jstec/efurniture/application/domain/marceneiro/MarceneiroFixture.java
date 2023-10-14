package br.dev.jstec.efurniture.application.domain.marceneiro;

import static br.dev.jstec.efurniture.application.domain.TipoTelefone.WHATSAPP;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarCpf;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarString;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringNumerica;
import static java.util.List.of;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo;
import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import br.dev.jstec.efurniture.application.domain.valueobject.TipoCliente;
import java.util.UUID;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class MarceneiroFixture {

    public static Marceneiro build() {

        return Marceneiro.createOf(
            gerarString(),
            gerarString(),
            TipoCliente.createOf("FISICA", gerarCpf(true)),
            "jorge@gmail.com",
            of(Telefone.createOf(WHATSAPP,
                gerarStringNumerica(9),
                gerarStringNumerica(2))),
            of(Endereco.createOf(
                gerarStringNumerica(8),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(2))),
            UUID.randomUUID().toString(),
            gerarString());
    }

    public static void buildConstrutorIdNulo() {

        new Marceneiro(
            null,
            gerarString(),
            gerarString(),
            TipoCliente.createOf("FISICA", gerarCpf(true)),
            "jorge@gmail.com",
            of(Telefone.createOf(WHATSAPP,
                gerarStringNumerica(9),
                gerarStringNumerica(2))),
            of(Endereco.createOf(
                gerarStringNumerica(8),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(2))),
            AuditInfo.auditedCreateOf(UUID.randomUUID().toString()),
            gerarString());
    }
}
