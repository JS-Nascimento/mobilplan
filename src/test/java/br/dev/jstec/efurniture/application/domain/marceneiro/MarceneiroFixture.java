package br.dev.jstec.efurniture.application.domain.marceneiro;

import static br.dev.jstec.efurniture.application.domain.TipoTelefone.WHATSAPP;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarCpf;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarEmail;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarInteger;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarLong;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarObject;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarString;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarStringNumerica;
import static java.util.List.of;
import static java.util.UUID.fromString;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.TipoTelefone;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import br.dev.jstec.efurniture.application.domain.valueobject.Nome;
import br.dev.jstec.efurniture.application.domain.valueobject.NomeComercial;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import br.dev.jstec.efurniture.application.domain.valueobject.TipoCliente;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class MarceneiroFixture {

    public static Marceneiro build() {

        return new Marceneiro(
            UUID.randomUUID(),
            gerarObject(Situacao.class),
            TipoCliente.createOf("FISICA", gerarCpf(true)),
            of(Telefone.createWithDdiOf(WHATSAPP,
                gerarStringNumerica(9),
                gerarStringNumerica(2),
                gerarStringNumerica(3))),
            of(Endereco.createOf(
                gerarStringNumerica(8),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(2))),
            new Nome(gerarString()),
            new NomeComercial(gerarString()),
            new Email(gerarEmail(true)));
    }

    public static Marceneiro buildComAuditoria() {

        return new Marceneiro(
            UUID.randomUUID(),
            gerarObject(Situacao.class),
            TipoCliente.createOf("FISICA", gerarCpf(true)),
            of(new Telefone(
                gerarLong(),
                TipoTelefone.byOrdinal(gerarInteger(1, 2)),
                gerarStringNumerica(9),
                gerarStringNumerica(2),
                gerarStringNumerica(3))),
            of(Endereco.createOf(
                gerarStringNumerica(8),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(2))),
            new Nome(gerarString()),
            new NomeComercial(gerarString()),
            new Email(gerarEmail(true)),
            UUID.randomUUID(),
            LocalDateTime.now(),
            UUID.randomUUID(),
            LocalDateTime.now());
    }

    public static void buildTelefoneInvalido() {

        Marceneiro.createOf(
            gerarString(),
            gerarString(),
            TipoCliente.createOf("FISICA", gerarCpf(true)),
            gerarEmail(true),
            new ArrayList<>(),
            of(Endereco.createOf(
                gerarStringNumerica(8),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(2))));
    }

    public static void buildEnderecoInvalido() {

        Marceneiro.createOf(
            gerarString(),
            gerarString(),
            TipoCliente.createOf("FISICA", gerarCpf(true)),
            gerarEmail(true),
            of(Telefone.createWithDdiOf(WHATSAPP,
                gerarStringNumerica(9),
                gerarStringNumerica(2),
                gerarStringNumerica(3))),
            new ArrayList<>());
    }

    public static Marceneiro buildComIdESituacao(String id, String situacao) {

        return new Marceneiro(
            fromString(id),
            Situacao.of(situacao),
            TipoCliente.createOf("FISICA", gerarCpf(true)),
            of(Telefone.createWithDdiOf(WHATSAPP,
                gerarStringNumerica(9),
                gerarStringNumerica(2),
                gerarStringNumerica(3))),
            of(Endereco.createOf(
                gerarStringNumerica(8),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(2))),
            new Nome(gerarString()),
            new NomeComercial(gerarString()),
            new Email(gerarEmail(true)),
            UUID.randomUUID(),
            LocalDateTime.now(),
            UUID.randomUUID(),
            LocalDateTime.now());
    }

    public static Marceneiro buildComId(String id) {

        return new Marceneiro(
            fromString(id),
            gerarObject(Situacao.class),
            TipoCliente.createOf("FISICA", gerarCpf(true)),
            of(Telefone.createWithDdiOf(WHATSAPP,
                gerarStringNumerica(9),
                gerarStringNumerica(2),
                gerarStringNumerica(3))),
            of(Endereco.createOf(
                gerarStringNumerica(8),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(),
                gerarString(2))),
            new Nome(gerarString()),
            new NomeComercial(gerarString()),
            new Email(gerarEmail(true)),
            UUID.randomUUID(),
            LocalDateTime.now(),
            UUID.randomUUID(),
            LocalDateTime.now());
    }
}
