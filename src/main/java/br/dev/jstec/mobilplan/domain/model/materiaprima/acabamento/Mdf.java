package br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static br.dev.jstec.mobilplan.domain.model.materiaprima.Unidade.METRO_QUADRADO;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.model.materiaprima.CommonAttributes;
import br.dev.jstec.mobilplan.domain.model.materiaprima.TipoPrecificacao;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Mdf extends CommonAttributes implements Acabamento {

    private final TipoAcabamento tipoAcabamento = TipoAcabamento.MDF;

    private final CalculaPorLado calculaPorLado;
    private final DimensoesChapa dimensoesChapa;

    private Mdf(String descricao,
                String cor,
                CalculaPorLado calculaPorLado,
                DimensoesChapa dimensoesChapa,
                TipoPrecificacao precificacao,
                String imagem,
                double preco,
                UUID tenantId) {
        super(descricao,
                cor,
                METRO_QUADRADO,
                preco,
                precificacao,
                imagem,
                tenantId);

        this.calculaPorLado = calculaPorLado;
        this.dimensoesChapa = dimensoesChapa;

        validar();
    }

    private Mdf(Long id,
                String descricao,
                String cor,
                CalculaPorLado calculaPorLado,
                DimensoesChapa dimensoesChapa,
                TipoPrecificacao precificacao,
                String imagem,
                double preco,
                UUID tenantId,
                LocalDateTime criadoEm,
                LocalDateTime atualizadoEm) {
        super(descricao,
                cor,
                METRO_QUADRADO,
                preco,
                precificacao,
                imagem,
                tenantId,
                id,
                criadoEm,
                atualizadoEm);

        this.calculaPorLado = calculaPorLado;
        this.dimensoesChapa = dimensoesChapa;

        validar();
    }

    public static Mdf of(String descricao,
                         String cor,
                         String calculaPorLado,
                         double altura,
                         double largura,
                         double espessura,
                         String precificacao,
                         String imagem,
                         double preco,
                         UUID tenantId) {


        var dimensoesChapa = new DimensoesChapa(altura, largura, espessura);

        return new Mdf(descricao,
                cor,
                CalculaPorLado.valueOf(calculaPorLado),
                dimensoesChapa,
                TipoPrecificacao.valueOf(precificacao),
                imagem,
                preco,
                tenantId);
    }

    public static Mdf with(Long id,
                           String descricao,
                           String cor,
                           String calculaPorLado,
                           double altura,
                           double largura,
                           double espessura,
                           String precificacao,
                           String imagem,
                           double preco,
                           UUID tenantId,
                           LocalDateTime criadoEm,
                           LocalDateTime atualizadoEm) {

        var dimensoesChapa = new DimensoesChapa(altura, largura, espessura);

        return new Mdf(id,
                descricao,
                cor,
                CalculaPorLado.valueOf(calculaPorLado),
                dimensoesChapa,
                TipoPrecificacao.valueOf(precificacao),
                imagem,
                preco,
                tenantId,
                criadoEm,
                atualizadoEm);
    }

    @Override
    public TipoAcabamento getTipoAcabamento() {
        return tipoAcabamento;
    }

    @Override
    protected void validar() {

        super.validar();

        if (calculaPorLado == null) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Calcula por lado");
        }
        if (dimensoesChapa.getAltura() <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Altura");
        }
        if (dimensoesChapa.getLargura() <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Largura");
        }
    }
}
