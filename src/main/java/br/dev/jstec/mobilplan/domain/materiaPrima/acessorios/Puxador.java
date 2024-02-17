package br.dev.jstec.mobilplan.domain.materiaprima.acessorios;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_COMBINACAO_PERFIL_E_TIPO_PUXADOR_INVALIDO;
import static br.dev.jstec.mobilplan.domain.materiaprima.acessorios.TipoPuxador.PUXADOR_CAVA;
import static br.dev.jstec.mobilplan.domain.materiaprima.acessorios.TipoPuxador.PUXADOR_PERFIL;
import static java.lang.String.valueOf;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.Tenant;
import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.materiaprima.TipoPrecificacao;
import br.dev.jstec.mobilplan.domain.materiaprima.Unidade;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Puxador extends Tenant implements Acessorio {

    private final boolean perfil;
    private final TipoPuxador tipoPuxador;
    private final double preco;
    private final String descricao;
    private final String cor;
    private final Unidade unidade;
    private final Direcao direcao;
    private final TipoPrecificacao precificacao;
    private Long id;
    private final DimensoesAcessorio dimensoesAcessorio;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    private Puxador(boolean perfil,
                    TipoPuxador tipoPuxador,
                    String descricao,
                    String cor,
                    Direcao direcao,
                    double preco,
                    TipoPrecificacao precificacao,
                    DimensoesAcessorio dimensoesAcessorio,
                    UUID tenantId) {
        super(tenantId);
        this.perfil = perfil;
        this.tipoPuxador = tipoPuxador;
        this.descricao = descricao;
        this.cor = cor;
        this.unidade = perfil ? Unidade.METRO_LINEAR : Unidade.UNIDADE;
        this.direcao = direcao;
        this.preco = preco;
        this.precificacao = precificacao;
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
                             double altura,
                             double largura,
                             double espessura,
                             UUID tenantId) {
        var dimensoesAcessorio = new DimensoesAcessorio(altura, largura, espessura);
        return new Puxador(perfil,
                TipoPuxador.valueOf(tipoPuxador),
                descricao,
                cor,
                Direcao.valueOf(direcao),
                preco,
                TipoPrecificacao.valueOf(precificacao),
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
                               double altura,
                               double largura,
                               double espessura,
                               LocalDateTime criadoEm,
                               LocalDateTime atualizadoEm,
                               UUID tenantId) {

        var dimensoesAcessorio = new DimensoesAcessorio(altura, largura, espessura);

        var puxador = new Puxador(perfil,
                TipoPuxador.valueOf(tipoPuxador),
                descricao,
                cor,
                Direcao.valueOf(direcao),
                preco,
                TipoPrecificacao.valueOf(precificacao),
                dimensoesAcessorio,
                tenantId);
        puxador.id = id;
        puxador.criadoEm = criadoEm;
        puxador.atualizadoEm = atualizadoEm;
        return puxador;
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
        if (preco <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Preço");
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
