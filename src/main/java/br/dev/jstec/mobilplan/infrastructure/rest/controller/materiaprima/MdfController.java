package br.dev.jstec.mobilplan.infrastructure.rest.controller.materiaprima;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.AtualizarMdfUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.BuscarMdfPorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.BuscarMdfPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.CriarMdfUseCase;
import br.dev.jstec.mobilplan.application.usecases.materiaprima.acabamento.mdf.RemoverMdfPorIdUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.materiaprima.MdfDto;
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
@RequestMapping("/v1/materia-prima/mdf")
@RequiredArgsConstructor
@Slf4j
public class MdfController {

    private final MdfDtoMapper mapper;
    private final CriarMdfUseCase criarMdfUseCase;
    private final AtualizarMdfUseCase atualizarMdfUseCase;
    private final RemoverMdfPorIdUseCase removerMdfPorIdUseCase;
    private final BuscarMdfPorIdUseCase buscarMdfPorIdUseCase;
    private final BuscarMdfPorCriteriosUseCase buscarMdfPorCriteriosUseCase;

    @PostMapping
    public ResponseEntity<MdfDto> criar(@RequestBody MdfDto dto) {

        log.debug("Criando MDF: {}", dto);
        dto.setTenantId(getUserLogged());
        var input = mapper.toInsertInputModel(dto);
        var output = criarMdfUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MdfDto> atualizar(@RequestBody MdfDto dto) {

        log.debug("Atualizando MDF: {}", dto);
        dto.setTenantId(getUserLogged());
        dto.setId(dto.getId());
        var input = mapper.toUpdateInputModel(dto);
        var output = atualizarMdfUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        log.debug("Removendo mdf: {}", id);
        var input = mapper.toDeleteInputModel(id);
        removerMdfPorIdUseCase.execute(input);

        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MdfDto> buscarPorId(@PathVariable Long id) {

        var input = new BuscarMdfPorIdUseCase.Input(id);
        var output = buscarMdfPorIdUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PostMapping("/pesquisar")
    public ResponseEntity<List<MdfDto>> buscarPorCriterios(@RequestBody PesquisaMateriaPrimaDto dto) {

        var input = mapper.toInputCriterios(dto);

        var output = buscarMdfPorCriteriosUseCase.execute(input);
        return ok(
                mapper.toDto(output));
    }
}
