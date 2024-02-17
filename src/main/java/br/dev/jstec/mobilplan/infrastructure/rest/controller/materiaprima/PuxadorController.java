package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.AtualizarPuxadorUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.BuscarPuxadorPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.BuscarPuxadorPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.CriarPuxadorUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.puxador.RemoverPuxadorPorIdUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PesquisaMateriaPrimaDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PuxadorDto;
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
@RequestMapping("/v1/materia-prima/puxador")
@RequiredArgsConstructor
@Slf4j
public class PuxadorController {

    private final PuxadorDtoMapper mapper;
    private final CriarPuxadorUseCase criarPuxadorUseCase;
    private final AtualizarPuxadorUseCase atualizarPuxadorUseCase;
    private final RemoverPuxadorPorIdUseCase removerPuxadorPorIdUseCase;
    private final BuscarPuxadorPorIdUseCase buscarPuxadorPorIdUseCase;
    private final BuscarPuxadorPorCriteriosUseCase buscarPuxadorPorCriteriosUseCase;

    @PostMapping
    public ResponseEntity<PuxadorDto> criar(@RequestBody PuxadorDto dto) {

        log.debug("Criando puxador: {}", dto);
        dto.setTenantId(getUserLogged());
        var input = mapper.toInsertInputModel(dto);
        var output = criarPuxadorUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PuxadorDto> atualizar(
            @PathVariable Long id,
            @RequestBody PuxadorDto dto) {

        log.debug("Atualizando puxador: {}", dto);
        dto.setTenantId(getUserLogged());
        dto.setId(id);
        var input = mapper.toUpdateInputModel(dto);
        var output = atualizarPuxadorUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        log.debug("Removendo puxador: {}", id);
        var input = mapper.toDeleteInputModel(id);
        removerPuxadorPorIdUseCase.execute(input);

        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PuxadorDto> buscarPorId(@PathVariable Long id) {

        var input = new BuscarPuxadorPorIdUseCase.Input(id);
        var output = buscarPuxadorPorIdUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PostMapping("/pesquisar")
    public ResponseEntity<List<PuxadorDto>> buscarPorCriterios(@RequestBody PesquisaMateriaPrimaDto dto) {

        var input = mapper.toInputCriterios(dto);

        var output = buscarPuxadorPorCriteriosUseCase.execute(input);
        return ok(
                mapper.toDto(output));
    }
}
