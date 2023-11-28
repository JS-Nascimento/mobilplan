package br.dev.jstec.mobilplan.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErroTecnico {

    ERRO_PADRAO_INCORRETO("mobilplan-501", "Erro fora do padrão esperado"),
    ERRO_INFORMACAO_INCONSISTENTE("mobilplan-502", "As informações enviadas estão inconsistentes"),
    ERRO_TOKEN_INVALIDO("mobilplan-503", "As informações contidas no token estão inconsistentes"),
    ERRO_USUARIO_EXISTENTE("mobilplan-504", "Já existe um usuário com o mesmo e-mail cadastrado"),
    ERRO_DESERIALIZACAO_JSON("mobilplan-505", "Erro ao deserializar o JSON."),
    ERRO_CODIGO_VALIDACAO_EXPIRADO("mobilplan-506", "O Token de validacao para o email expirou."),
    ERRO_CODIGO_VALIDACAO_INVALIDO("mobilplan-507", "O Token de validacao é inválido para o email informado."),
    ERRO_USUARIO_INEXISTENTE("mobilplan-508", "O email não foi encontrado na base de dados.");

    private final String code;
    private final String msg;
}
