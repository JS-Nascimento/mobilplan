package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.TipoFundo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = PRIVATE, force = true)
public class PadraoGaveta {

    private final TipoFundo tipoMontagemFundo;
    private final int espessuraFundo;
    private final int rebaixoFundo;
    private final int folgaTrilhos;
    private final boolean acompanhaTrilho;
    private final int folgaProfunidadeGavetaEmRelacaoGabinete;
    private final int corpoEmRelacaoFrente;
    private final int espessuraCorpo;

    private PadraoGaveta(TipoFundo tipoMontagemFundo,
                         int espessuraFundo,
                         int rebaixoFundo,
                         int folgaTrilhos,
                         boolean acompanhaTrilho,
                         int folgaProfunidadeGavetaEmRelacaoGabinete,
                         int corpoEmRelacaoFrente,
                         int espessuraCorpo) {

        this.tipoMontagemFundo = tipoMontagemFundo;
        this.espessuraFundo = espessuraFundo;
        this.rebaixoFundo = rebaixoFundo;
        this.folgaTrilhos = folgaTrilhos;
        this.acompanhaTrilho = acompanhaTrilho;
        this.folgaProfunidadeGavetaEmRelacaoGabinete = folgaProfunidadeGavetaEmRelacaoGabinete;
        this.corpoEmRelacaoFrente = corpoEmRelacaoFrente;
        this.espessuraCorpo = espessuraCorpo;

        validar();
    }

    public static PadraoGaveta with(String tipoMontagemFundo,
                                    int espessuraFundo,
                                    int rebaixoFundo,
                                    int folgaTrilhos,
                                    boolean acompanhaTrilho,
                                    int folgaProfunidadeGavetaEmRelacaoGabinete,
                                    int corpoEmRelacaoFrente,
                                    int espessuraCorpo) {

        return new PadraoGaveta(
                TipoFundo.of(tipoMontagemFundo),
                espessuraFundo,
                rebaixoFundo,
                folgaTrilhos,
                acompanhaTrilho,
                folgaProfunidadeGavetaEmRelacaoGabinete,
                corpoEmRelacaoFrente,
                espessuraCorpo);
    }

    private void validar() {

        if (tipoMontagemFundo == TipoFundo.ENCAIXADO && rebaixoFundo <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Rebaixo do fundo");
        }

        if (tipoMontagemFundo == TipoFundo.SOBREPOSTO && rebaixoFundo > 0) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Rebaixo do fundo");
        }
    }
}
