package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarCnpj;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarCpf;
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

    public static TipoCliente buildTipoClienteValidoPessoaJuridica() {

        return TipoCliente.createOf(
            "JURIDICA",
            gerarCnpj(true));
    }
}
