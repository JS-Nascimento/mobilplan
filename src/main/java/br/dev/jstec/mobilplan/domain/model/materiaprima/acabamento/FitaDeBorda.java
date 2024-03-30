package br.dev.jstec.mobilplan.domain.model.materiaprima.acabamento;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static br.dev.jstec.mobilplan.domain.model.materiaprima.TipoPrecificacao.ML;
import static br.dev.jstec.mobilplan.domain.model.materiaprima.Unidade.METRO_LINEAR;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums.PadraoDeFitagem;
import br.dev.jstec.mobilplan.domain.model.materiaprima.CommonAttributes;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class FitaDeBorda extends CommonAttributes implements Acabamento {

    private final TipoAcabamento tipoAcabamento = TipoAcabamento.FITA_DE_BORDA;
    private final Double largura;

    private FitaDeBorda(String descricao, String cor, double largura, double preco, String imagem, UUID tenantId) {
        super(descricao,
                cor,
                METRO_LINEAR,
                preco,
                ML,
                imagem,
                tenantId);
        this.largura = largura;
        validar();
    }

    private FitaDeBorda(Long id, String descricao, String cor, double largura, double preco, String imagem,
                        UUID tenantId, LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        super(descricao,
                cor,
                METRO_LINEAR,
                preco,
                ML,
                imagem,
                tenantId,
                id,
                criadoEm,
                atualizadoEm);

        this.largura = largura;
        validar();
    }

    public static FitaDeBorda of(String descricao, String cor, double largura, double preco, String imagem,
                                 UUID tenantId) {
        return new FitaDeBorda(descricao, cor, largura, preco, imagem, tenantId);
    }

    public static FitaDeBorda with(Long id, String descricao, String cor, double largura, double preco, String imagem,
                                   UUID tenantId,
                                   LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        return new FitaDeBorda(id, descricao, cor, largura, preco, imagem, tenantId, criadoEm, atualizadoEm);
    }

    @Override
    public TipoAcabamento getTipoAcabamento() {
        return tipoAcabamento;
    }

    public static double calcularMetragem(double altura, double largura, PadraoDeFitagem padraoDeFitagem) {
        if (isNull(padraoDeFitagem)) {
            return 0.0;
        }

        return switch (padraoDeFitagem) {
            case NENHUM -> 0.0;
            case UMA_ALTURA -> altura;
            case UMA_ALTURA_UMA_LARGURA -> altura + largura;
            case UMA_ALTURA_DUAS_LARGURAS -> altura + (largura * 2);
            case DUAS_ALTURAS -> altura * 2;
            case DUAS_ALTURAS_UMA_LARGURA -> (altura * 2) + largura;
            case QUATRO_LADOS -> (largura + altura) * 2;
        };
    }

    @Override
    protected void validar() {

        super.validar();

        //TODO verificar se a largura é um valor válido
        if (largura <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Largura");
        }

    }
}
