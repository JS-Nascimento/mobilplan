package br.dev.jstec.efurniture.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErroDeNegocio {

    ERRO_ID_INVALIDO("EFURNITURE-001", "O ID para {0} é inválido."),
    ERRO_CAMPO_INVALIDO("EFURNITURE-002", "O valor para o atributo {0} é inválido."),
    ERRO_TIPO_CLIENTE_INVALIDO("EFURNITURE-003",
        "O Tipo Pessoa e Documento Fiscal são obrigatórios."),
    ERRO_CPF_INVALIDO("EFURNITURE-004", "O Cpf: {0} é inválido."),
    ERRO_CNPJ_INVALIDO("EFURNITURE-005", "O Cnpj: {0} é inválido."),
    ERRO_ENTIDADE_EXISTENTE("EFURNITURE-006",
        "Já existe um registro para {0} com os dados informados."),
    ERRO_TIPO_PESSOA_INEXISTENTE("EFURNITURE-007", "Tipo de pessoa {0} inválido."),
    ERRO_TIPO_PESSOA_NULO("EFURNITURE-008", "Tipo de pessoa não pode ser nulo."),
    ERRO_TIPO_TELEFONE_NULO("EFURNITURE-009", "Tipo de telefone não pode ser nulo."),
    ERRO_TIPO_TELEFONE_INEXISTENTE("EFURNITURE-010", "Tipo de telefone {0} inválido."),
    ERRO_TIPO_TELEFONE_INCOMPATIVEL("EFURNITURE-011",
        "Tipo de telefone {0} é incompátivel com o numero {1}."),
    ERRO_ATRIBUTOS_ENDERECO_OBRIGATORIOS("EFURNITURE-012",
        "Os atributos de Endereço, com exceção de complemento são obrigatórios."),

    ERRO_DADOS_OBRIGATORIOS_INCONSISTENTES("EFURNITURE-013",
        "As informações informadas são inconsistentes."),
    ERRO_ATRIBUTO_OBRIGATORIO("EFURNITURE-014", "O atributo {0} é obrigatório."),
    ERRO_SITUACAO_INEXISTENTE("EFURNITURE-015", "Situação {0} inválida."),
    ERRO_SITUACAO_NULA("EFURNITURE-016", "Situação não pode ser nulo."),
    ERRO_INFORMACAO_INVALIDA("EFURNITURE-017", "Erro ao validar informações. Erro: {0}"),
    ERRO_CONVERTER_IMAGEM("EFURNITURE-018",
        "Erro ao converter a imagem enviado em formato válido."),
    ERRO_SALVAR_IMAGEM("EFURNITURE-018", "Erro ao salvar a logomarca.");

    private final String code;
    private final String msg;
}
