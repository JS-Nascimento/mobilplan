package br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima;

import java.util.List;
import lombok.Data;

@Data
public class ImportarEmLoteResponseDto {

    List<String> ferragensImportadas;
    List<String> ferragensExistentes;
    List<String> ferragensNaoImportadas;
}
