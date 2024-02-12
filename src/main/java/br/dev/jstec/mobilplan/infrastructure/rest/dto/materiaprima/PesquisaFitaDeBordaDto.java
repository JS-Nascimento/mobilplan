package br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima;

import lombok.Data;

@Data
public class PesquisaFitaDeBordaDto {
    String descricao;
    String cor;
    String largura;
    double doPreco;
    double atePreco;
    String unidade;
    String tipoAcabamento;
}
