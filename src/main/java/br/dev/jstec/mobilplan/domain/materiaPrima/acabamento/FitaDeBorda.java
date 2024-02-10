package br.dev.jstec.mobilplan.domain.materiaprima.acabamento;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static br.dev.jstec.mobilplan.domain.materiaprima.TipoPrecificacao.ML;
import static br.dev.jstec.mobilplan.domain.materiaprima.Unidade.METRO_LINEAR;
import static br.dev.jstec.mobilplan.domain.materiaprima.acabamento.TipoAcabamento.FITA_DE_BORDA;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.materiaprima.TipoPrecificacao;
import br.dev.jstec.mobilplan.domain.materiaprima.Unidade;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class FitaDeBorda implements Acabamento {

    private final TipoPrecificacao precificacao = ML;
    private final TipoAcabamento tipoAcabamento = FITA_DE_BORDA;
    private final Unidade unidade = METRO_LINEAR;
    private final String descricao;
    private final String cor;
    private final Double largura;
    private final Double preco;
    private final String tenantId;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private Long id;

    private FitaDeBorda(String descricao, String cor, double largura, double preco, String tenantId) {
        this.descricao = descricao;
        this.cor = cor;
        this.largura = largura;
        this.preco = preco;
        this.tenantId = tenantId;
        validar();
    }

    public static FitaDeBorda of(String descricao, String cor, double largura, double preco, String tenantId) {
        return new FitaDeBorda(descricao, cor, largura, preco, tenantId);
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

        if (tenantId == null || tenantId.isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "TenantId");
        }

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
