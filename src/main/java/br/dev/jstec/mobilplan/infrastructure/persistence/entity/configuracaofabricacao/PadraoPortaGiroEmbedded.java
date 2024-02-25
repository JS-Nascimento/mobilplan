package br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao;

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
public class PadraoPortaGiroEmbedded {
    private int folgaSuperior;
    private int folgaInferior;
    private int folgaEsquerda;
    private int folgaDireita;
    private int entreComponentes;
}
