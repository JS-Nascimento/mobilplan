package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static org.springframework.http.ResponseEntity.ok;

import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.CriarMdfUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.MdfDto;
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
@RequestMapping("/v1/materia-prima/mdf")
@RequiredArgsConstructor
@Slf4j
public class MdfController {

    private final MdfDtoMapper mapper;
    private final CriarMdfUseCase criarMdfUseCase;

    @PostMapping
    public ResponseEntity<MdfDto> criar(@RequestBody MdfDto dto) {

        log.debug("Criando MDF: {}", dto);
        dto.setTenantId(getUserLogged());
        var input = mapper.toInsertInputModel(dto);
        var output = criarMdfUseCase.execute(input);

        return ok(mapper.toDto(output));
    }
}
