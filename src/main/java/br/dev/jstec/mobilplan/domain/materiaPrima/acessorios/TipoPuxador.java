package br.dev.jstec.mobilplan.domain.materiaprima.acessorios;

import lombok.Getter;

@Getter
public enum TipoPuxador {

    SEM_PUXADOR("Sem Puxador"),
    PUXADOR_ALCA("Puxador Alça"),
    PUXADOR_CAVA("Puxador Cava"),
    PUXADOR_PERFIL("Puxador Perfil");

    private final String descricao;

    TipoPuxador(String descricao) {
        this.descricao = descricao;
    }
}
