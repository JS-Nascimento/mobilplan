package br.dev.jstec.mobilplan.infrastructure.persistence.entity.configuracaofabricacao;

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
    private String base;
    private String lateral;
    private String travessaHorizontal;
    private String travessaVertical;
    private String fundo;
    private String prateleiraInterna;
    private String prateleiraExterna;
    private String tampo;
    private String porta;
    private String frenteGaveta;
}
