package br.dev.jstec.efurniture.infrastructure.rest.controller.marceneiro;

import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro.MarceneiroDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/marceneiros")
@RequiredArgsConstructor
public class MarceneiroController {

    private final BuscarMarceneiroPorIdUseCase buscarMarceneiroPorIdUseCase;
    private final BuscarMarceneiroPorEmailUseCase buscarMarceneiroPorEmailUseCase;
    private final BuscarMarceneiroPorDocumentoUseCase buscarMarceneiroPorDocumentoUseCase;
    private final MarceneiroDtoMapper mapper;

    @GetMapping("/{id}")
    @Operation(
        summary = "Retorna um marceneiro pelo id",
        description = "Este endpoint retorna um marceneiro específico baseado no ID fornecido.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marceneiro encontrado",
            content = @Content(schema = @Schema(implementation = MarceneiroDto.class))),
        @ApiResponse(responseCode = "404", description = "Marceneiro não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "ID inválido",
            content = @Content)
    })
    @ResponseStatus(value = HttpStatus.OK)
    ResponseEntity<MarceneiroDto> getMarceneiroById(@PathVariable String id) {

        return buscarMarceneiroPorIdUseCase
            .execute(new BuscarMarceneiroPorIdUseCase.Input(id))
            .map(mapper::mapToMarceneiroDto)
            .map(ResponseEntity::ok)
            .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/email")
    @Operation(
        summary = "Retorna um marceneiro pelo email",
        description = "Este endpoint retorna um marceneiro específico baseado no Email fornecido.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marceneiro encontrado",
            content = @Content(schema = @Schema(implementation = MarceneiroDto.class))),
        @ApiResponse(responseCode = "404", description = "Marceneiro não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Email inválido",
            content = @Content)
    })
    @ResponseStatus(value = HttpStatus.OK)
    ResponseEntity<MarceneiroDto> getMarceneiroByEmail(
        @RequestParam(name = "email", required = true) String email) {

        return buscarMarceneiroPorEmailUseCase
            .execute(new BuscarMarceneiroPorEmailUseCase.Input(email))
            .map(mapper::mapToMarceneiroDto)
            .map(ResponseEntity::ok)
            .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/documento")
    @Operation(
        summary = "Retorna um marceneiro pelo documento fiscal",
        description = "Este endpoint retorna um marceneiro específico baseado no documento fiscal fornecido.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marceneiro encontrado",
            content = @Content(schema = @Schema(implementation = MarceneiroDto.class))),
        @ApiResponse(responseCode = "404", description = "Marceneiro não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Documento inválido",
            content = @Content)
    })
    @ResponseStatus(value = HttpStatus.OK)
    ResponseEntity<MarceneiroDto> getMarceneiroByDocumento(
        @RequestParam(name = "documento", required = true) String documento) {

        return buscarMarceneiroPorDocumentoUseCase
            .execute(new BuscarMarceneiroPorDocumentoUseCase.Input(documento))
            .map(mapper::mapToMarceneiroDto)
            .map(ResponseEntity::ok)
            .orElseGet(ResponseEntity.notFound()::build);
    }
}
