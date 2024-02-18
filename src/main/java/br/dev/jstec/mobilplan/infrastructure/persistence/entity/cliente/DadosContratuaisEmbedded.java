package br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DadosContratuaisEmbedded {
    private String documentoFiscal;
    private String documentoIdentificador;
    private String estadoCivil;
}
