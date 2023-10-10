package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.domain.TipoPessoa.FISICA;
import static br.dev.jstec.efurniture.application.domain.TipoPessoa.JURIDICA;
import static br.dev.jstec.efurniture.application.domain.TipoPessoa.of;

import br.dev.jstec.efurniture.application.domain.TipoPessoa;

public record TipoCliente(TipoPessoa tipoPessoa, String documentoFiscal) {

    public TipoCliente {

        if (FISICA.equals(tipoPessoa)) {

            documentoFiscal = Cpf.createOf(documentoFiscal, true);

        } else if (JURIDICA.equals(tipoPessoa)) {

            documentoFiscal = Cnpj.createOf(documentoFiscal, true);
        }
    }

    public static TipoCliente createOf(String tipoPessoa, String documentoFiscal) {

        return new TipoCliente(of(tipoPessoa), documentoFiscal);
    }
}
