package br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "configuracao_fabricacao", indexes = {
        @Index(name = "tenant_id_config_padrao", columnList = "tenant_id")})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConfiguracaoFabricacaoEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Include
    private Long id;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Embedded
    private PadraoGavetaEmbedded padraoGavetaEmbedded;

    @Embedded
    private PadraoPortaGiroEmbedded padraoPortaGiroEmbedded;

    @Embedded
    private PadraoFitagemEmbedded padraoFitagemEmbedded;

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
