package br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static br.dev.jstec.mobilplan.domain.model.materiaprima.TipoPrecificacao.M2;
import static br.dev.jstec.mobilplan.domain.model.materiaprima.Unidade.METRO_QUADRADO;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.model.Tenant;
import br.dev.jstec.mobilplan.domain.model.materiaprima.TipoPrecificacao;
import br.dev.jstec.mobilplan.domain.model.materiaprima.Unidade;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Mdf extends Tenant implements Acabamento {

    private final TipoAcabamento tipoAcabamento = TipoAcabamento.MDF;
    private final Unidade unidade = METRO_QUADRADO;
    private final String descricao;
    private final String cor;
    private final CalculaPorLado calculaPorLado;
    private final DimensoesChapa dimensoesChapa;
    private final double preco;
    private TipoPrecificacao precificacao = M2;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private Long id;

    private Mdf(String descricao,
                String cor,
                CalculaPorLado calculaPorLado,
                DimensoesChapa dimensoesChapa,
                TipoPrecificacao precificacao,
                double preco,
                UUID tenantId) {
        super(tenantId);
        this.precificacao = precificacao;
        this.descricao = descricao;
        this.cor = cor;
        this.calculaPorLado = calculaPorLado;
        this.dimensoesChapa = dimensoesChapa;
        this.preco = preco;

        validar();
    }

    private Mdf(Long id,
                String descricao,
                String cor,
                CalculaPorLado calculaPorLado,
                DimensoesChapa dimensoesChapa,
                TipoPrecificacao precificacao,
                double preco,
                UUID tenantId,
                LocalDateTime criadoEm,
                LocalDateTime atualizadoEm) {
        super(tenantId);
        this.precificacao = precificacao;
        this.id = id;
        this.descricao = descricao;
        this.cor = cor;
        this.calculaPorLado = calculaPorLado;
        this.dimensoesChapa = dimensoesChapa;
        this.preco = preco;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        validar();
    }

    public static Mdf of(String descricao,
                         String cor,
                         String calculaPorLado,
                         double altura,
                         double largura,
                         double espessura,
                         String precificacao,
                         double preco,
                         UUID tenantId) {


        var dimensoesChapa = new DimensoesChapa(altura, largura, espessura);

        return new Mdf(descricao, cor, CalculaPorLado.valueOf(calculaPorLado),
                dimensoesChapa, TipoPrecificacao.valueOf(precificacao), preco, tenantId);
    }

    public static Mdf with(Long id,
                           String descricao,
                           String cor,
                           String calculaPorLado,
                           double altura,
                           double largura,
                           double espessura,
                           String precificacao,
                           double preco,
                           UUID tenantId,
                           LocalDateTime criadoEm,
                           LocalDateTime atualizadoEm) {

        var dimensoesChapa = new DimensoesChapa(altura, largura, espessura);

        return new Mdf(id, descricao, cor, CalculaPorLado.valueOf(calculaPorLado),
                dimensoesChapa, TipoPrecificacao.valueOf(precificacao), preco, tenantId, criadoEm, atualizadoEm);
    }

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

    private void validar() {

        if (super.getTenantId() == null || super.getTenantId().toString().isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "TenantId");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Descrição");
        }
        if (cor == null || cor.isBlank()) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Cor");
        }
        if (calculaPorLado == null) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Calcula por lado");
        }
        if (dimensoesChapa.getAltura() <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Altura");
        }
        if (dimensoesChapa.getLargura() <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Largura");
        }
        if (preco <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Preço");
        }
    }
}
