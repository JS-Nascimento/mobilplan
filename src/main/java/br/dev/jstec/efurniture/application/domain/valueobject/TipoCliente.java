package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.domain.TipoPessoa.FISICA;
import static br.dev.jstec.efurniture.application.domain.TipoPessoa.JURIDICA;
import static br.dev.jstec.efurniture.application.domain.TipoPessoa.of;

import br.dev.jstec.efurniture.application.domain.TipoPessoa;

public record TipoCliente(TipoPessoa tipoPessoa, String documento) {

    public TipoCliente {

        if (FISICA.equals(tipoPessoa)) {

            documento = Cpf.createOf(documento).value();

        } else if (JURIDICA.equals(tipoPessoa)) {

            documento = Cnpj.createOf(documento).value();
        }
    }

    public static TipoCliente createOf(String tipoPessoa, String documento) {

        return new TipoCliente(of(tipoPessoa), documento);
    }
}
