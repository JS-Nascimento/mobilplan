package br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima;

public record FitaDeBordaDto(
        Long id,
        String tenantId,
        String descricao,
        String cor,
        String largura,
        double preco,
        String unidade,
        String precificacao
) {
}
