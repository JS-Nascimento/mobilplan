package br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima;

import lombok.Data;

@Data
public class PesquisaMateriaPrimaDto {
    String descricao;
    String cor;
    String dimensaoBase;
    double doPreco;
    double atePreco;
    String unidade;
    String tipoAcabamento;
}
