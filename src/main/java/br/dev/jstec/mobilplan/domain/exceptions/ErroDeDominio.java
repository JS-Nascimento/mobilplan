package br.dev.jstec.mobilplan.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErroDeDominio {

    ERRO_ID_INVALIDO("mobilplan-001", "O ID para {0} é inválido."),
    ERRO_CAMPO_INVALIDO("mobilplan-002", "O valor para o atributo {0} é inválido."),
    ERRO_TIPO_CLIENTE_INVALIDO("mobilplan-003",
        "O Tipo Pessoa e Documento Fiscal são obrigatórios."),
    ERRO_CPF_INVALIDO("mobilplan-004", "O Cpf: {0} é inválido."),
    ERRO_CNPJ_INVALIDO("mobilplan-005", "O Cnpj: {0} é inválido."),
    ERRO_ENTIDADE_EXISTENTE("mobilplan-006",
        "Já existe um registro para {0} com os dados informados."),
    ERRO_TIPO_PESSOA_INEXISTENTE("mobilplan-007", "Tipo de pessoa {0} inválido."),
    ERRO_TIPO_PESSOA_NULO("mobilplan-008", "Tipo de pessoa não pode ser nulo."),
    ERRO_TIPO_TELEFONE_NULO("mobilplan-009", "Tipo de telefone não pode ser nulo."),
    ERRO_TIPO_TELEFONE_INEXISTENTE("mobilplan-010", "Tipo de telefone {0} inválido."),
    ERRO_TIPO_TELEFONE_INCOMPATIVEL("mobilplan-011",
        "Tipo de telefone {0} é incompátivel com o numero {1}."),
    ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS("mobilplan-012",
        "Os atributos de Endereço, com exceção de complemento são obrigatórios."),

    ERRO_DADOS_OBRIGATORIOS_INCONSISTENTES("mobilplan-013",
        "As informações informadas são inconsistentes."),
    ERRO_ATRIBUTO_OBRIGATORIO("mobilplan-014", "O atributo {0} é obrigatório."),
    ERRO_SITUACAO_INEXISTENTE("mobilplan-015", "Situação {0} inválida."),
    ERRO_SITUACAO_NULA("mobilplan-016", "Situação não pode ser nulo."),
    ERRO_INFORMACAO_INVALIDA("mobilplan-017", "Erro ao validar informações. Erro: {0}"),
    ERRO_CONVERTER_IMAGEM("mobilplan-018",
        "Erro ao converter a imagem enviado em formato válido."),
    ERRO_SALVAR_IMAGEM("mobilplan-018", "Erro ao salvar a logomarca."),
    ERRO_SENHA_INVALIDA("mobilplan-019",
        "Senha inválida. A senha deve ter entre 6 e 10 caracteres. "
            + "Dever ao menos uma letra maiúscula, uma minúscula e um número. "
                + "Não pode conter caracteres especiais ex: !@#$%&*()"),
    ERRO_EMAIL_CADASTRADO("mobilplan-020", "O Email {0} já existe em nossa base de dados.");

    private final String code;
    private final String msg;
}
