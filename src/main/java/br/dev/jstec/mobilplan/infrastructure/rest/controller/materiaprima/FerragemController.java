package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.AtualizarFerragemUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.BuscarFerragemPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.BuscarFerragemPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.CriarFerragemUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acessorio.ferragem.RemoverFerragemPorIdUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.FerragemDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.PesquisaMateriaPrimaDto;
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
@RequestMapping("/v1/materia-prima/ferragem")
@RequiredArgsConstructor
@Slf4j
public class FerragemController {

    private final FerragemDtoMapper mapper;
    private final CriarFerragemUseCase criarFerragemUseCase;
    private final AtualizarFerragemUseCase atualizarFerragemUseCase;
    private final RemoverFerragemPorIdUseCase removerFerragemPorIdUseCase;
    private final BuscarFerragemPorIdUseCase buscarFerragemPorIdUseCase;
    private final BuscarFerragemPorCriteriosUseCase buscarFerragemPorCriteriosUseCase;

    @PostMapping
    public ResponseEntity<FerragemDto> criar(@RequestBody FerragemDto dto) {

        log.debug("Criando ferragem: {}", dto);
        dto.setTenantId(getUserLogged());
        var input = mapper.toInsertInputModel(dto);
        var output = criarFerragemUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FerragemDto> atualizar(
            @PathVariable Long id,
            @RequestBody FerragemDto dto) {

        log.debug("Atualizando ferragem: {}", dto);
        dto.setTenantId(getUserLogged());
        dto.setId(id);
        var input = mapper.toUpdateInputModel(dto);
        var output = atualizarFerragemUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        log.debug("Removendo ferragem: {}", id);
        var input = mapper.toDeleteInputModel(id);
        removerFerragemPorIdUseCase.execute(input);

        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FerragemDto> buscarPorId(@PathVariable Long id) {

        log.debug("Buscando ferragem por id: {}", id);
        var input = new BuscarFerragemPorIdUseCase.Input(id);
        var output = buscarFerragemPorIdUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PostMapping("/pesquisar")
    public ResponseEntity<List<FerragemDto>> buscarPorCriterios(@RequestBody PesquisaMateriaPrimaDto dto) {

        var input = mapper.toInputCriterios(dto);

        var output = buscarFerragemPorCriteriosUseCase.execute(input);
        return ok(
                mapper.toDto(output));
    }
}