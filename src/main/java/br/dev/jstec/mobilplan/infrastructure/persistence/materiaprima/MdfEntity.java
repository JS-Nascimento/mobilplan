package br.dev.jstec.mobilplan.infrastructure.persistence.materiaprima;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mdf")
@Getter
@Setter
public class MdfEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;

    @Column(name = "cor", nullable = false, length = 50)
    private String cor;

    @Column(name = "largura", nullable = false, columnDefinition = "numeric(10,1) default 0.0")
    private Double largura;

    @Column(name = "altura", nullable = false, columnDefinition = "numeric(10,1) default 0.0")
    private Double altura;

    @Column(name = "espessura", nullable = false, columnDefinition = "numeric(10,1) default 0.0")
    private Double espessura;

    @Column(name = "preco", nullable = false, columnDefinition = "numeric(10,2) default 0.00")
    private Double preco;

    @Column(name = "unidade", nullable = false)
    private String unidade;

    @Column(name = "tipo_acabamento", nullable = false)
    private String tipoAcabamento;

    @Column(name = "precificacao", nullable = false)
    private String precificacao;

    @Column(name = "calcula_por_lado", nullable = false)
    private String calculaPorLado;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @PreUpdate
    @PrePersist
    void audit() {
        atualizadoEm = now();
        if (criadoEm == null) {
            criadoEm = atualizadoEm;
        }
    }
}
