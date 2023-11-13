package br.dev.jstec.mobilplan.infrastructure.rest.controller.marceneiro;

import static br.dev.jstec.mobilplan.infrastructure.exceptions.ErroTecnico.ERRO_INFORMACAO_INCONSISTENTE;
import static java.net.URI.create;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarMarceneiroUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSitucaoUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarTodosMarceneirosUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.CriarMarceneiroUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.SalvarLogomarcaUseCase;
import br.dev.jstec.mobilplan.infrastructure.exceptions.RequestException;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.MarceneiroDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.NewMarceneiroDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.ResponseMarceneiroDto;
import br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.UpdateMarceneiroDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/v1/marceneiros")
@RequiredArgsConstructor
public class MarceneiroController {

    private final BuscarTodosMarceneirosUseCase buscarTodosMarceneirosUseCase;
    private final BuscarMarceneiroPorIdUseCase buscarMarceneiroPorIdUseCase;
    private final BuscarMarceneiroPorEmailUseCase buscarMarceneiroPorEmailUseCase;
    private final BuscarMarceneiroPorDocumentoUseCase buscarMarceneiroPorDocumentoUseCase;
    private final CriarMarceneiroUseCase criarMarceneiroUseCase;
    private final AlterarSitucaoUseCase alterarSitucaoUseCase;
    private final AlterarMarceneiroUseCase alterarMarceneiroUseCase;
    private final SalvarLogomarcaUseCase salvarLogomarcaUseCase;
    private final MarceneiroDtoMapper mapper;

    @GetMapping()
    @Operation(
        summary = "Retorna uma lista paginada de Marceneiros",
        description = "Este endpoint retorna uma lista paginada baseada nos critérios da paginação.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso",
            content = @Content(schema = @Schema(implementation = MarceneiroDto.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Erro ao realizar consulta.",
            content = @Content)
    })
    @ResponseStatus(value = OK)
    ResponseEntity<Page<MarceneiroDto>> buscarTodosMarceneiros(Pageable pageable) {

        var marceneiros = buscarTodosMarceneirosUseCase.execute()
            .stream()
            .map(mapper::toMarceneiroDto)
            .toList();

        var response = new PageImpl<>(marceneiros, pageable, marceneiros.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Retorna um marceneiro pelo id",
        description = "Este endpoint retorna um marceneiro específico baseado no ID fornecido.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marceneiro encontrado",
            content = @Content(schema = @Schema(implementation = MarceneiroDto.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "ID inválido",
            content = @Content)
    })
    @ResponseStatus(value = OK)
    ResponseEntity<MarceneiroDto> buscarMarceneiroPorId(@PathVariable String id) {

        return buscarMarceneiroPorIdUseCase
            .execute(new BuscarMarceneiroPorIdUseCase.Input(id))
            .map(mapper::toMarceneiroDto)
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
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Email inválido",
            content = @Content)
    })
    @ResponseStatus(value = OK)
    ResponseEntity<MarceneiroDto> buscarMarceneiroPorEmail(
        @RequestParam(name = "email") String email) {

        return buscarMarceneiroPorEmailUseCase
            .execute(new BuscarMarceneiroPorEmailUseCase.Input(email))
            .map(mapper::toMarceneiroDto)
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
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Documento inválido",
            content = @Content)
    })
    @ResponseStatus(value = OK)
    ResponseEntity<MarceneiroDto> buscarMarceneiroPorDocumento(
        @RequestParam(name = "documento") String documento) {

        return buscarMarceneiroPorDocumentoUseCase
            .execute(new BuscarMarceneiroPorDocumentoUseCase.Input(documento))
            .map(mapper::toMarceneiroDto)
            .map(ResponseEntity::ok)
            .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping()
    @Operation(
        summary = "Cria um Marceneiro, retornando seu ID",
        description = "Este endpoint cria um Marceneiro e retorna nome e Id criados.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Marceneiro criado",
            content = @Content(schema = @Schema(implementation = ResponseMarceneiroDto.class))),
        @ApiResponse(responseCode = "422", description = "Erro ao criar o Marceneiro",
            content = @Content),
    })
    @ResponseStatus(value = CREATED)
    ResponseEntity<ResponseMarceneiroDto> criarMarceneiro(
        @RequestBody @Valid NewMarceneiroDto dto) {

        var output = criarMarceneiroUseCase
            .execute(mapper.toCriarMarceneiroInput(dto));

        if (nonNull(output)) {

            var responseDto = mapper.toResponseMarceneiroDto(output);
            return ResponseEntity.created(create("/marceneiro/"
                    + responseDto.id()))
                .body(responseDto);

        } else {

            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/{id}/atualizarsituacao")
    @Operation(
        summary = "Atualiza a situação do Marceneiro pelo ID",
        description = "Este endpoint atualizar a situação do Marceneiro por seu Id.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Situação atualizada.",
            content = @Content(schema = @Schema(implementation = ResponseMarceneiroDto.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Erro ao atualizar a situação do Marceneiro",
            content = @Content)
    })
    @ResponseStatus(value = OK)
    ResponseEntity<ResponseMarceneiroDto> alteraSituacao(@PathVariable String id,
        @RequestParam(name = "situacao") String situacao) {

        var marceneiroAlterado = alterarSitucaoUseCase.execute(
            new AlterarSitucaoUseCase.Input(id, situacao));

        return ResponseEntity.ok(
            mapper.toResponseMarceneiroDto(marceneiroAlterado));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualiza a atributos do Marceneiro pelo ID",
        description = "Este endpoint atualizar os atributos do Marceneiro por seu Id.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marceneiro atualizado.",
            content = @Content(schema = @Schema(implementation = MarceneiroDto.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Erro ao atualizar o Marceneiro",
            content = @Content)
    })
    @ResponseStatus(value = OK)
    ResponseEntity<MarceneiroDto> alteraMarceneiro(@PathVariable String id,
        @RequestBody @Valid UpdateMarceneiroDto dto) {

        if (!id.equals(dto.id())) {

            throw new RequestException(BAD_REQUEST, ERRO_INFORMACAO_INCONSISTENTE,
                this.getClass().getSimpleName());
        }

        var marceneiroParaAlterar = alterarMarceneiroUseCase
            .execute(mapper.toAlterarMarceneiroInput(dto));

        var marceneiroAlterado = mapper.toMarceneiroDto(marceneiroParaAlterar);

        return ResponseEntity.ok()
            .contentType(APPLICATION_JSON)
            .body(marceneiroAlterado);
    }

    @PostMapping("/{id}/logomarca")
    @Operation(
        summary = "Salvar imagem da Logomarca do Marceneiro",
        description = "Este endpoint salva a imagem da logomarca do Marceneiro.",
        tags = {"Marceneiro"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logomarca salva.",
            content = @Content(schema = @Schema(implementation = ResponseMarceneiroDto.class))),
        @ApiResponse(responseCode = "422", description = "Erro ao criar o Marceneiro",
            content = @Content),
    })
    @ResponseStatus(value = CREATED)
    ResponseEntity<String> salvarLogomarca(
        @PathVariable String id,
        @RequestParam("logomarca") MultipartFile logomarca) throws IOException {

        if (nonNull(logomarca) && ("image/png".equals(logomarca.getContentType())
            || "image/jpeg".equals(logomarca.getContentType()))) {

            var output = salvarLogomarcaUseCase
                .execute(mapper.toSalvarLogomarcaInput(id,
                    Objects.equals(logomarca.getContentType(), "image/png") ? "png" : "jpeg",
                    logomarca.getInputStream()));

            if (nonNull(output)) {

                return ResponseEntity.ok(output.url());
            }

        }

        return ResponseEntity.unprocessableEntity()
            .body(ERRO_INFORMACAO_INCONSISTENTE.getMsg());
    }
}
