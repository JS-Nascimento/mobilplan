package br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_COMBINACAO_PERFIL_E_TIPO_PUXADOR_INVALIDO;
import static br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.TipoPuxador.PUXADOR_CAVA;
import static br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios.TipoPuxador.PUXADOR_PERFIL;
import static java.lang.String.valueOf;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.model.materiaprima.CommonAttributes;
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
public class Puxador extends CommonAttributes implements Acessorio {

    private final boolean perfil;
    private final TipoPuxador tipoPuxador;
    private final Direcao direcao;
    private final DimensoesAcessorio dimensoesAcessorio;
    private Long id;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    private Puxador(boolean perfil,
                    TipoPuxador tipoPuxador,
                    String descricao,
                    String cor,
                    Direcao direcao,
                    double preco,
                    TipoPrecificacao precificacao,
                    String imagem,
                    DimensoesAcessorio dimensoesAcessorio,
                    UUID tenantId) {
        super(descricao,
                cor,
                perfil ? Unidade.METRO_LINEAR : Unidade.UNIDADE,
                preco,
                precificacao,
                imagem,
                tenantId);
        this.perfil = perfil;
        this.tipoPuxador = tipoPuxador;
        this.direcao = direcao;
        this.dimensoesAcessorio = dimensoesAcessorio;

        validar();
    }

    public static Puxador of(boolean perfil,
                             String tipoPuxador,
                             String descricao,
                             String cor,
                             String direcao,
                             double preco,
                             String precificacao,
                             String imagem,
                             double altura,
                             double largura,
                             double espessura,
                             UUID tenantId) {
        var dimensoesAcessorio = new DimensoesAcessorio(altura, largura, espessura);
        return new Puxador(perfil,
                TipoPuxador.of(tipoPuxador),
                descricao,
                cor,
                Direcao.of(direcao),
                preco,
                TipoPrecificacao.of(precificacao),
                imagem,
                dimensoesAcessorio,
                tenantId);
    }

    public static Puxador with(Long id,
                               boolean perfil,
                               String tipoPuxador,
                               String descricao,
                               String cor,
                               String direcao,
                               double preco,
                               String precificacao,
                               String imagem,
                               double altura,
                               double largura,
                               double espessura,
                               LocalDateTime criadoEm,
                               LocalDateTime atualizadoEm,
                               UUID tenantId) {

        var dimensoesAcessorio = new DimensoesAcessorio(altura, largura, espessura);

        var puxador = new Puxador(perfil,
                TipoPuxador.of(tipoPuxador),
                descricao,
                cor,
                Direcao.of(direcao),
                preco,
                TipoPrecificacao.of(precificacao),
                imagem,
                dimensoesAcessorio,
                tenantId);
        puxador.id = id;
        puxador.criadoEm = criadoEm;
        puxador.atualizadoEm = atualizadoEm;
        return puxador;
    }

    @Override
    protected void validar() {

        super.validar();

        if (dimensoesAcessorio == null) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Dimensões");
        }
        if (dimensoesAcessorio.getAltura() <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Altura");
        }
        if (dimensoesAcessorio.getLargura() <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Largura");
        }
        if (dimensoesAcessorio.getEspessura() <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Espessura");
        }
        if (direcao == null) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Direção");
        }

        if (tipoPuxador == null) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Tipo de puxador");
        }

        if (perfil && !(tipoPuxador == PUXADOR_PERFIL || tipoPuxador == PUXADOR_CAVA)) {
            throw new DomainException(ERRO_COMBINACAO_PERFIL_E_TIPO_PUXADOR_INVALIDO, valueOf(perfil),
                    tipoPuxador.getDescricao());
        }

        if (!perfil && (tipoPuxador == PUXADOR_PERFIL || tipoPuxador == PUXADOR_CAVA)) {
            throw new DomainException(ERRO_COMBINACAO_PERFIL_E_TIPO_PUXADOR_INVALIDO, valueOf(perfil),
                    tipoPuxador.getDescricao());
        }
    }
}
