package br.dev.jstec.mobilplan.infrastructure.rest.controller.usuario;

import static java.net.URI.create;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.NewUsuarioDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.ResponseUsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final UsuarioDtoMapper mapper;

    @PostMapping()
    @Operation(
        summary = "Cria um Usuário, retornando seu ID",
        description = "Este endpoint cria um Usuário e retorna nome e id criados. "
            + "Além disso, inicia o fluxo de validação do email do Usuário, enviando um email de confirmação.",
        tags = {"Usuário"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado",
            content = @Content(schema = @Schema(implementation = ResponseUsuarioDto.class))),
        @ApiResponse(responseCode = "422", description = "Erro ao criar o Usuário",
            content = @Content),
    })
    @ResponseStatus(value = CREATED)
    ResponseEntity<ResponseUsuarioDto> criarUsuario(
        @RequestBody @Valid NewUsuarioDto dto) {

        var output = criarUsuarioUseCase
            .execute(mapper.toCriarUsuarioUseCaseInput(dto));

        if (nonNull(output)) {

            var responseDto = mapper.toResponseUsuarioDto(output);
            return ResponseEntity.created(create("/usuario/"
                    + responseDto.id()))
                .body(responseDto);

        } else {

            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
