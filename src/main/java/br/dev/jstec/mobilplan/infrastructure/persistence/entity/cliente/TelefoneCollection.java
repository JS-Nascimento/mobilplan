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
public class TelefoneCollection {

    private String tipoTelefone;
    private String numero;
    private String ddd;
    private String ddi;
}
