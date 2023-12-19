package br.dev.jstec.mobilplan.infrastructure.rest.controller.usuario;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_INFORMACAO_INCONSISTENTE;
import static java.net.URI.create;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase;
import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import br.dev.jstec.mobilplan.infrastructure.gateways.UsuarioGateway;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.MeUsuarioDto;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final UsuarioDtoMapper mapper;
    private final UsuarioGateway gateway;

    @PostMapping("/novo")
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
            return ResponseEntity.created(create("/v1/usuarios/" + responseDto.id()))
                    .body(responseDto);
        } else {

            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping("/novo/confirmar-email")
    @Operation(
            summary = "Faz a verificação do email do Usuário, retornando uma página HTML",
            description = "Este endpoint verifica o email do Usuário e retorna uma página HTML com o resultado.",
            tags = {"Usuário"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email validado com sucesso",
                    content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "400", description = "Erro ao validar o email do Usuário",
                    content = @Content)
    })
    @ResponseStatus(value = OK)
    ResponseEntity<ResponseUsuarioDto> confirmarEmail(
            @RequestParam String code,
            @RequestParam String email) {

        if (isBlank(code) || isBlank(email)) {

            throw new RequestException(BAD_REQUEST, ERRO_INFORMACAO_INCONSISTENTE,
                    UsuarioController.class.getSimpleName());
        }

        return ResponseEntity
                .status(OK)
                .body(gateway.validarCodigo(code, email));
    }

    @PutMapping("/{id}/me")
    @Operation(
            summary = "Atualiza um Usuário, retornando seu ID",
            description = "Este endpoint atualiza um Usuário e retorna nome e id atualizados.",
            tags = {"Usuário"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                    content = @Content(schema = @Schema(implementation = ResponseUsuarioDto.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o Usuário.",
                    content = @Content),
    })
    @ResponseStatus(value = OK)
    ResponseEntity<ResponseUsuarioDto> atualizarUsuario(
            @RequestBody @Valid MeUsuarioDto dto) {

        //TODO implementar

        return ResponseEntity.ok().build();
    }
}
