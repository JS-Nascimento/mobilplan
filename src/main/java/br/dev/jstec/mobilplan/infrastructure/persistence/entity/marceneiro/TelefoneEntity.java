package br.dev.jstec.mobilplan.infrastructure.persistence.entity.marceneiro;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marceneiro_telefones")
public class TelefoneEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "tipo", nullable = false)
    private String tipoTelefone;

    @Column(name = "ddi", nullable = false)
    private String ddi;

    @Column(name = "ddd", nullable = false)
    private String ddd;

    @Column(name = "numero", nullable = false)
    private String numero;
}
