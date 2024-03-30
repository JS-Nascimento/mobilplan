package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao;

import static lombok.AccessLevel.PRIVATE;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = PRIVATE, force = true)
public class PadraoGabinete {

    private final int folgaProfundidadePrateleiraInternaFixa;
    private final int folgaProfundidadePrateleiraInternaMovel;
    private final int folgaProfundidadePrateleiraDivisoria;

    private PadraoGabinete(int folgaProfundidadePrateleiraInternaFixa,
                           int folgaProfundidadePrateleiraInternaMovel,
                           int folgaProfundidadePrateleiraDivisoria) {

        this.folgaProfundidadePrateleiraInternaFixa = folgaProfundidadePrateleiraInternaFixa;
        this.folgaProfundidadePrateleiraInternaMovel = folgaProfundidadePrateleiraInternaMovel;
        this.folgaProfundidadePrateleiraDivisoria = folgaProfundidadePrateleiraDivisoria;

        validar();
    }

    public static PadraoGabinete with(int folgaProfundidadePrateleiraInternaFixa,
                                      int folgaProfundidadePrateleiraInternaMovel,
                                      int folgaProfundidadePrateleiraDivisoria) {

        return new PadraoGabinete(
                folgaProfundidadePrateleiraInternaFixa,
                folgaProfundidadePrateleiraInternaMovel,
                folgaProfundidadePrateleiraDivisoria);
    }

    private void validar() {

    }
}
