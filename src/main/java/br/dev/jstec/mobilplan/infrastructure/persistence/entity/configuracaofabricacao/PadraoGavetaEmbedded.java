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
public class PadraoGavetaEmbedded {
    private String tipoMontagemFundo;
    private int espessuraFundo;
    private int rebaixoFundo;
    private int folgaTrilhos;
    private boolean acompanhaTrilho;
    private int folgaProfunidadeGavetaEmRelacaoGabinete;
    private int corpoEmRelacaoFrente;
    private int espessuraCorpo;
}
