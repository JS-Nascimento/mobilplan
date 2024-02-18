package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.enums.TipoPessoa.FISICA;
import static br.dev.jstec.mobilplan.domain.enums.TipoPessoa.JURIDICA;
import static br.dev.jstec.mobilplan.domain.enums.TipoPessoa.of;

import br.dev.jstec.mobilplan.domain.enums.TipoPessoa;

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
