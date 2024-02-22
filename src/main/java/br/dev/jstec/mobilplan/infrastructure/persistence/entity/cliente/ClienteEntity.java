package br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente;

import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cliente")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = AUTO, generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Column(name = "nome", nullable = false, length = 300)
    private String nome;

    @Column(name = "tipo_pessoa", nullable = false)
    private String tipoPessoa;

    @Column(name = "email", nullable = false, length = 300)
    private String email;

    @ElementCollection
    @CollectionTable(name = "cliente_enderecos", joinColumns = @JoinColumn(name = "cliente_id"))
    private Set<EnderecoCollection> enderecos;

    @ElementCollection
    @CollectionTable(name = "cliente_telefones", joinColumns = @JoinColumn(name = "cliente_id"))
    private Set<TelefoneCollection> telefones;

    @Embedded
    private DadosContratuaisEmbedded dadosContratuais;

    @Column(name = "notificar_por_email", nullable = false)
    private boolean notificarPorEmail;

    @Column(name = "notificar_por_whatsapp", nullable = false)
    private boolean notificarPorWhatsapp;

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
