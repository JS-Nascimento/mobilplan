package br.dev.jstec.mobilplan.domain.materiaprima.acabamento;

import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.domain.util.NumberHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DimensoesChapa {

    private final double altura;
    private final double largura;
    private final double espessura;
    private double quantidadePorChapa;

    public double quantidadePorChapa() {

        if ((isNull(altura) || altura == 0)
                || (isNull(largura) || largura == 0)) {

            return 0.0;
        }
        return NumberHelper.roundDouble(altura * largura, 2);
    }
}
