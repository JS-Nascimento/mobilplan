package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarCpf;
import static lombok.AccessLevel.PRIVATE;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class TipoClienteFixture {

    public static void buildTipoClienteTipoPessoaInvalida(String tipoPessoa) {

        TipoCliente.createOf(
                tipoPessoa,
                gerarCpf(true));
    }

    public static void buildTipoClienteCpfInvalido(String cpf) {

        TipoCliente.createOf(
                "FISICA",
                cpf);
    }

    public static void buildTipoClienteCnpjInvalido(String cnpj) {

        TipoCliente.createOf(
                "JURIDICA",
                cnpj);
    }

    public static void buildTipoClienteTipoPessoaNulo() {

        TipoCliente.createOf(
                null,
                gerarCpf(true));
    }

    public static TipoCliente buildTipoClienteValido() {

        return TipoCliente.createOf(
                "FISICA",
                gerarCpf(true));
    }
}
