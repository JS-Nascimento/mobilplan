package br.dev.jstec.mobilplan.domain.materiaprima.acessorios;

import br.dev.jstec.mobilplan.domain.materiaprima.Unidade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Ferragem implements Acessorio {

    private final String descricao;
    private final String cor;
    private final Unidade unidade;
    private final double preco;

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public Unidade getUnidade() {
        return unidade;
    }

    @Override
    public double getPreco() {
        return preco;
    }

    @Override
    public String getCor() {
        return cor;
    }
}
