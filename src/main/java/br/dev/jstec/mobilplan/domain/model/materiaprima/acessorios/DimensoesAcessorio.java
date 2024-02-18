package br.dev.jstec.mobilplan.domain.model.materiaprima.acessorios;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class DimensoesAcessorio {

    private final double altura;
    private final double largura;
    private final double espessura;
}
