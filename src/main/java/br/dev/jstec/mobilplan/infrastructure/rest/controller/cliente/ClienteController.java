package br.dev.jstec.mobilplan.infrastructure.rest.controller.cliente;

import static br.dev.jstec.mobilplan.infrastructure.configuration.security.UserContext.getUserLogged;
import static java.util.UUID.fromString;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import br.dev.jstec.mobilplan.application.usecases.cliente.AlterarClienteUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorCriteriosUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.BuscarClientePorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.CriarClienteUseCase;
import br.dev.jstec.mobilplan.application.usecases.cliente.RemoverClienteUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente.ClienteDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.cliente.PesquisaClienteDto;
import java.util.Collection;
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
@RequestMapping("/v1/cliente")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteDtoMapper mapper;
    private final CriarClienteUseCase criarClienteUseCase;
    private final AlterarClienteUseCase alterarClienteUseCase;
    private final RemoverClienteUseCase removerClienteUseCase;
    private final BuscarClientePorIdUseCase buscarClientePorIdUseCase;
    private final BuscarClientePorCriteriosUseCase buscarClientePorCriteriosUseCase;

    @PostMapping
    public ResponseEntity<ClienteDto> criar(@RequestBody ClienteDto dto) {

        log.debug("Criando cliente: {}", dto);
        var input = mapper.toInsertInputModel(dto);
        var output = criarClienteUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizar(
            @PathVariable String id,
            @RequestBody ClienteDto dto) {

        log.debug("Atualizando cliente: {}", dto);
        dto.setId(fromString(id));
        dto.setTenantId(getUserLogged());
        var input = mapper.toUpdateInputModel(dto);
        var output = alterarClienteUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable String id) {

        log.debug("Removendo cliente: {}", id);
        var input = mapper.toDeleteInputModel(fromString(id));
        removerClienteUseCase.execute(input);

        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarPorId(@PathVariable String id) {

        log.debug("Buscando cliente por id: {}", id);
        var input = new BuscarClientePorIdUseCase.Input(fromString(id));
        var output = buscarClientePorIdUseCase.execute(input);

        return ok(mapper.toDto(output));
    }

    @PostMapping("/pesquisar")
    public ResponseEntity<Collection<ClienteDto>> pesquisar(@RequestBody PesquisaClienteDto dto) {

        log.debug("Pesquisando cliente por criterios: {}", dto);
        var input = mapper.toInputCriterios(dto);
        var output = buscarClientePorCriteriosUseCase.execute(input);

        return ok(mapper.toDto(output));
    }
}
