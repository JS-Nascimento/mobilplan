package br.dev.jstec.mobilplan.domain.materiaprima.acabamento;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static br.dev.jstec.mobilplan.domain.materiaprima.TipoPrecificacao.ML;
import static br.dev.jstec.mobilplan.domain.materiaprima.Unidade.METRO_LINEAR;
import static br.dev.jstec.mobilplan.domain.materiaprima.acabamento.TipoAcabamento.FITA_DE_BORDA;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.materiaprima.TipoPrecificacao;
import br.dev.jstec.mobilplan.domain.materiaprima.Unidade;
import lombok.Getter;

@Getter
public class FitaDeBorda implements Acabamento {

    private final TipoPrecificacao precificacao = ML;
    private final TipoAcabamento tipoAcabamento = FITA_DE_BORDA;
    private final Unidade unidade = METRO_LINEAR;
    private final String descricao;
    private final String cor;
    private final Double largura;
    private final Double preco;
    private Long id;

    private FitaDeBorda(String descricao, String cor, double largura, double preco) {
        this.descricao = descricao;
        this.cor = cor;
        this.largura = largura;
        this.preco = preco;
        validar();
    }

    public static FitaDeBorda of(String descricao, String cor, double largura, double preco) {
        return new FitaDeBorda(descricao, cor, largura, preco);
    }

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
    public TipoAcabamento getTipoAcabamento() {
        return tipoAcabamento;
    }

    @Override
    public String getCor() {
        return cor;
    }

    private void validar() {
        if (descricao == null || descricao.isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Descrição");
        }
        if (cor == null || cor.isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Cor");
        }
        //TODO verificar se a largura é um valor válido
        if (largura <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Largura");
        }
        if (preco <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Preço");
        }
    }
}
