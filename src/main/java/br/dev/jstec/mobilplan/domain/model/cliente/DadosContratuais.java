package br.dev.jstec.mobilplan.domain.model.cliente;

import static br.dev.jstec.mobilplan.domain.enums.EstadoCivil.EMPRESA;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;

import br.dev.jstec.mobilplan.domain.enums.EstadoCivil;
import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.valueobject.Cnpj;
import br.dev.jstec.mobilplan.domain.valueobject.Cpf;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class DadosContratuais {

    private final String documentoFiscal;
    private final String documentoIdentificador;
    private final EstadoCivil estadoCivil;

    private DadosContratuais(String documentoFiscal, String documentoIdentificador, EstadoCivil estadoCivil) {

        this.estadoCivil = estadoCivil;
        if (this.estadoCivil == EMPRESA) {
            this.documentoFiscal = new Cnpj(documentoFiscal).value();
        } else {
            this.documentoFiscal = new Cpf(documentoFiscal).value();
        }
        this.documentoIdentificador = documentoIdentificador;

        validar();
    }

    public static DadosContratuais with(String documentoFiscal,
                                        String documentoIdentificador,
                                        String estadoCivil) {

        return new DadosContratuais(
                documentoFiscal,
                documentoIdentificador,
                EstadoCivil.of(estadoCivil));
    }

    private void validar() {

        if (this.documentoIdentificador == null || this.documentoIdentificador.isEmpty()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Documento Identificador");
        }
    }
}
