package br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PadraoFitagemEmbedded {
    @Column(name = "base_componente")
    private String base;
    @Column(name = "lateral_componente")
    private String lateral;
    @Column(name = "travessa_horizontal_componente")
    private String travessaHorizontal;
    @Column(name = "travessa_vertical_componente")
    private String travessaVertical;
    @Column(name = "fundo_componente")
    private String fundo;
    @Column(name = "prateleira_interna_componente")
    private String prateleiraInterna;
    @Column(name = "prateleira_externa_componente")
    private String prateleiraExterna;
    @Column(name = "tampo_componente")
    private String tampo;
    @Column(name = "porta_componente")
    private String porta;
    @Column(name = "frente_gaveta_componente")
    private String frenteGaveta;
}
