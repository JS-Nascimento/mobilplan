package br.dev.jstec.efurniture.infrastructure.persistence.marceneiro;

import static jakarta.persistence.CascadeType.ALL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode(exclude = {"telefones", "enderecos"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marceneiros")
public class MarceneiroEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "situacao", nullable = false)
    private String situacao;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nome_comercial", nullable = false)
    private String nomeComercial;

    @Column(name = "tipo_pessoa", nullable = false)
    private String tipoPessoa;

    @Column(name = "documento", nullable = false, unique = true)
    private String documento;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "marceneiro")
    private List<TelefoneEntity> telefones;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "marceneiro")
    private List<EnderecoEntity> enderecos;

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
