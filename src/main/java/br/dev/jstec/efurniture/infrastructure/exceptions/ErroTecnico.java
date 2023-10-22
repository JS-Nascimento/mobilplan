package br.dev.jstec.efurniture.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErroTecnico {

    ERRO_PADRAO_INCORRETO("EFURNITURE-501", "Erro fora do padrão esperado"),
    ERRO_INFORMACAO_INCONSISTENTE("EFURNITURE-502", "As informações enviadas estão inconsistentes");

    private final String code;
    private final String msg;
}
