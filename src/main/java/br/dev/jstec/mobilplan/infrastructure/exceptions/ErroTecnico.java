package br.dev.jstec.mobilplan.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErroTecnico {

    ERRO_PADRAO_INCORRETO("mobilplan-501", "Erro fora do padrão esperado"),
    ERRO_INFORMACAO_INCONSISTENTE("mobilplan-502", "As informações enviadas estão inconsistentes"),
    ERRO_TOKEN_INVALIDO("mobilplan-503", "As informações contidas no token estão inconsistentes"),
    ERRO_USUARIO_EXISTENTE("mobilplan-504", "Já existe um usuário com o mesmo e-mail cadastrado");

    private final String code;
    private final String msg;
}
