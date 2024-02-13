package br.dev.jstec.mobilplan.domain.materiaprima.acessorios;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_MENOR_IGUAL_ZERO;
import static br.dev.jstec.mobilplan.domain.materiaprima.Unidade.valueOf;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.Tenant;
import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import br.dev.jstec.mobilplan.domain.materiaprima.Unidade;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Ferragem extends Tenant implements Acessorio {

    private Long id;
    private final String descricao;
    private final String cor;
    private final Unidade unidade;
    private final double preco;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    private Ferragem(String descricao, String cor, Unidade unidade, double preco, UUID tenantId) {
        super(tenantId);
        this.descricao = descricao;
        this.cor = cor;
        this.unidade = unidade;
        this.preco = preco;

        validar();
    }

    private Ferragem(Long id, String descricao, String cor, Unidade unidade, double preco, UUID tenantId,
                     LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        super(tenantId);
        this.id = id;
        this.descricao = descricao;
        this.cor = cor;
        this.unidade = unidade;
        this.preco = preco;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;

        validar();
    }

    public static Ferragem of(String descricao, String cor, String unidade, double preco, UUID tenantId) {
        return new Ferragem(descricao, cor, valueOf(unidade), preco, tenantId);
    }

    public static Ferragem with(Long id, String descricao, String cor, String unidade, double preco, UUID tenantId,
                                LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        return new Ferragem(id, descricao, cor, valueOf(unidade), preco, tenantId, criadoEm, atualizadoEm);
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
        if (unidade == null) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, "Unidade");
        }
        if (preco <= 0) {
            throw new DomainException(ERRO_CAMPO_MENOR_IGUAL_ZERO, "Preço");
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
