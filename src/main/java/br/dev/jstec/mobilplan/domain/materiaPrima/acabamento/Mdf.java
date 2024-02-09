package br.dev.jstec.mobilplan.domain.materiaprima.acabamento;

import br.dev.jstec.mobilplan.domain.materiaprima.Unidade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Mdf implements Acabamento {

    private final TipoAcabamento tipoAcabamento = TipoAcabamento.MELAMINICO;
    private final Unidade unidade = Unidade.METRO_QUADRADO;
    private final String descricao;
    private final String cor;
    private final CalculaPorLado calculaPorLado;
    private final DimensoesChapa dimensoesChapa;
    private final double preco;

    @Override
    public TipoAcabamento getTipoAcabamento() {
        return tipoAcabamento;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getCor() {
        return cor;
    }

}
