package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.AtualizarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.BuscarFitaDeBordaPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.BuscarFitaDeBordaPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.CriarFitaDeBordaUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.RemoverFitaDeBordaPorIdUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.FitaDeBordaDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PesquisaFitaDeBordaDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/materia-prima/fitas-de-borda")
@RequiredArgsConstructor
@Slf4j
public class FitadeBordaController {

    private final FitaDeBordaDtoMapper mapper;
    private final CriarFitaDeBordaUseCase criarFitaDeBordaUseCase;
    private final BuscarFitaDeBordaPorIdUseCase buscarFitaDeBordaPorIdUseCase;
    private final BuscarFitaDeBordaPorCriteriosUseCase buscarFitaDeBordaPorCriteriosUseCase;
    private final AtualizarFitaDeBordaUseCase atualizarFitaDeBordaUseCase;
    private final RemoverFitaDeBordaPorIdUseCase removerFitaDeBordaPorIdUseCase;


    @PostMapping
    public ResponseEntity<FitaDeBordaDto> criar(@RequestBody FitaDeBordaDto dto) {

        log.debug("Criando fita de borda: {}", dto);
        dto.setTenantId(getUserLogged());
        var input = mapper.toInsertInputModel(dto);
        var output = criarFitaDeBordaUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FitaDeBordaDto> atualizar(@PathVariable Long id, @RequestBody FitaDeBordaDto dto) {

        log.debug("Atualizando fita de borda: {}", dto);
        dto.setTenantId(getUserLogged());
        dto.setId(id);
        var input = mapper.toUpdateInputModel(dto);
        var output = atualizarFitaDeBordaUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        log.debug("Removendo fita de borda: {}", id);
        var input = mapper.toDeleteInputModel(id);
        removerFitaDeBordaPorIdUseCase.execute(input);

        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FitaDeBordaDto> buscarPorId(@PathVariable Long id) {

        var input = new BuscarFitaDeBordaPorIdUseCase.Input(id);
        var output = buscarFitaDeBordaPorIdUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PostMapping("/pesquisar")
    public ResponseEntity<List<FitaDeBordaDto>> buscarPorCriterios(@RequestBody PesquisaFitaDeBordaDto dto) {

        var input = mapper.toInputCriterios(dto);

        var output = buscarFitaDeBordaPorCriteriosUseCase.execute(input);
        return ok(
                mapper.toDto(output));
    }
}