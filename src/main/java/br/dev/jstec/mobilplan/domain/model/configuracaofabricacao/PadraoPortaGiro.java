package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao;

import static lombok.AccessLevel.PRIVATE;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = PRIVATE, force = true)
public class PadraoPortaGiro {

    private final int folgaSuperior;
    private final int folgaInferior;
    private final int folgaEsquerda;
    private final int folgaDireita;
    private final int entreComponentes;

    private PadraoPortaGiro(int folgaSuperior,
                            int folgaInferior,
                            int folgaEsquerda,
                            int folgaDireita,
                            int entreComponentes) {

        this.folgaSuperior = folgaSuperior;
        this.folgaInferior = folgaInferior;
        this.folgaEsquerda = folgaEsquerda;
        this.folgaDireita = folgaDireita;
        this.entreComponentes = entreComponentes;

        validar();
    }

    public static PadraoPortaGiro with(int folgaSuperior,
                                       int folgaInferior,
                                       int folgaEsquerda,
                                       int folgaDireita,
                                       int entreComponentes) {

        return new PadraoPortaGiro(folgaSuperior,
                folgaInferior,
                folgaEsquerda,
                folgaDireita,
                entreComponentes);
    }

    private void validar() {

    }
}
