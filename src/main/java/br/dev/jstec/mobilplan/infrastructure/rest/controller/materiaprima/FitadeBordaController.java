package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;

import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.CriarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.FitaDeBordaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/material-prima/fita-de-borda")
@RequiredArgsConstructor
@Slf4j
public class FitadeBordaController {

    private final FitaDeBordaDtoMapper mapper;
    private final CriarFitaDeBordaUseCase criarFitaDeBordaUseCase;

    @PostMapping
    public ResponseEntity<FitaDeBordaDto> salvar(String tenantId,
                                                 @RequestBody FitaDeBordaDto dto) {
        log.info("Criando fita de borda: {}", dto);
        var input = mapper.toInputModel(dto);
        var output = criarFitaDeBordaUseCase.execute(input);

        return ResponseEntity.ok(mapper.toDto(output));
    }
}