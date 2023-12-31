package br.dev.jstec.mobilplan.infrastructure.rest.controller.usuario;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_INFORMACAO_INCONSISTENTE;
import static java.net.URI.create;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import br.dev.jstec.mobilplan.application.usecases.usuario.AlterarUsuarioUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.BuscarUsuarioPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.CriarUsuarioUseCase;
import br.dev.jstec.mobilplan.application.usecases.usuario.SalvarAvatarUseCase;
import br.dev.jstec.mobilplan.domain.exceptions.ErrorMessage;
import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import br.dev.jstec.mobilplan.infrastructure.gateways.UsuarioGateway;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.AvatarUrlDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.NewUsuarioDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.RequestUsuarioDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.usuario.ResponseUsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final AlterarUsuarioUseCase alterarUsuarioUseCase;
    private final SalvarAvatarUseCase salvarAvatarUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
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
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o Usuário.",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
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
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o Usuário.",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
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

    @GetMapping("/{id}")
    @Operation(
            summary = "Retorna um marceneiro pelo id",
            description = "Este endpoint retorna um marceneiro específico baseado no ID fornecido.",
            tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = ResponseUsuarioDto.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @ResponseStatus(value = OK)
    ResponseEntity<ResponseUsuarioDto> buscarPorId(@PathVariable String id) {

        return buscarUsuarioPorIdUseCase
                .execute(new BuscarUsuarioPorIdUseCase.Input(id))
                .map(mapper::toUsuarioDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("/{id}/me")
    @Operation(
            summary = "Atualiza informacoes do Usuário logado, retornando dados atualizados",
            description = "Este endpoint atualiza informacoes do Usuário logado e retorna dados atualizados. "
                    + "Se houver alteração de email, inicia o fluxo de validação do email do Usuário, "
                    + "enviando um email de confirmação.\nSe houver alteração de telefone, inicia o fluxo de validação "
                    + "do telefone do Usuário.",
            tags = {"Usuário"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                    content = @Content(schema = @Schema(implementation = ResponseUsuarioDto.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o Usuário.",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Erro ao atualizar o Usuário", content = @Content)
    })
    @ResponseStatus(value = OK)
    ResponseEntity<EntityModel<ResponseUsuarioDto>> atualizarUsuario(
            @RequestBody @Valid RequestUsuarioDto dto) {

        return ofNullable(alterarUsuarioUseCase.execute(mapper.toAlterarUsuarioUseCaseInput(dto)))
                .map(output -> {
                    var responseDto = mapper.toUsuarioDto(output);
                    EntityModel<ResponseUsuarioDto> resource = EntityModel.of(responseDto);
                    resource.add(linkTo(methodOn(UsuarioController.class).buscarPorId(
                            requireNonNull(resource.getContent()).id())).withRel("usuario"));
                    return ResponseEntity.ok(resource);
                })
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping("/{id}/avatar")
    @Operation(
            summary = "Salvar o avatar da foto do usuário.",
            description = "Este endpoint salva o avatar da foto do usuário.",
            tags = {"Usuário"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar salvo.",
                    content = @Content(schema = @Schema(implementation = ResponseUsuarioDto.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o avatar do usuário.",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Erro ao validar o avatar do usuário.",
                    content = @Content),
    })
    @ResponseStatus(value = CREATED)
    ResponseEntity<EntityModel<AvatarUrlDto>> salvarAvatar(
            @PathVariable String id,
            @RequestParam("avatar") MultipartFile avatar) throws IOException {

        if (nonNull(avatar) && ("image/png".equals(avatar.getContentType())
                || "image/jpeg".equals(avatar.getContentType()))) {

            var output = salvarAvatarUseCase
                    .execute(mapper.toSalvarAvatarInput(id,
                            Objects.equals(avatar.getContentType(), "image/png") ? "png" : "jpeg",
                            avatar.getInputStream()));

            var responseDto = mapper.toAvatarUrlDto(output);

            if (nonNull(output)) {
                EntityModel<AvatarUrlDto> resource = EntityModel.of(responseDto);
                resource.add(linkTo(methodOn(UsuarioController.class).buscarPorId(id)).withRel("usuario"));
                return ResponseEntity.ok(resource);
            }

        }

        return ResponseEntity.unprocessableEntity().build();
    }
}
