package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.domain.TipoPessoa.FISICA;
import static br.dev.jstec.mobilplan.application.domain.TipoPessoa.JURIDICA;
import static br.dev.jstec.mobilplan.application.domain.TipoPessoa.of;

import br.dev.jstec.mobilplan.application.domain.TipoPessoa;

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
