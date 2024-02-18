package br.dev.jstec.mobilplan.infrastructure.rest.controller.marceneiro;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SITUACAO_INEXISTENTE;
import static br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSituacaoUseCaseFixture.buildOutput;
import static br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCaseFixture.buildOutput;
import static br.dev.jstec.mobilplan.domain.model.marceneiro.MarceneiroFixture.buildComAuditoria;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarObject;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarString;
import static br.dev.jstec.mobilplan.infrastructure.rest.dto.marceneiro.MarceneiroDtoFixture.buildComTodosMarceneiroOutput;
import static java.text.MessageFormat.format;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarMarceneiroUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSitucaoUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCaseFixture;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCaseFixture;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarTodosMarceneirosUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarTodosMarceneirosUseCaseFixture;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.CriarMarceneiroUseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.SalvarLogomarcaUseCase;
import br.dev.jstec.mobilplan.domain.model.marceneiro.Situacao;
import br.dev.jstec.mobilplan.infrastructure.exceptions.CustomExceptionHandler;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(controllers = {MarceneiroController.class, CustomExceptionHandler.class})
class MarceneiroControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BuscarTodosMarceneirosUseCase buscarTodosMarceneirosUseCase;

    @MockBean
    private BuscarMarceneiroPorIdUseCase buscarMarceneiroPorIdUseCase;

    @MockBean
    private BuscarMarceneiroPorEmailUseCase buscarMarceneiroPorEmailUseCase;

    @MockBean
    private BuscarMarceneiroPorDocumentoUseCase buscarMarceneiroPorDocumentoUseCase;

    @MockBean
    private CriarMarceneiroUseCase criarMarceneiroUseCase;

    @MockBean
    private AlterarSitucaoUseCase alterarSitucaoUseCase;

    @MockBean
    private AlterarMarceneiroUseCase alterarMarceneiroUseCase;

    @MockBean
    private SalvarLogomarcaUseCase salvarLogomarcaUseCase;

    @SpyBean
    private MarceneiroDtoMapperImpl mapper;

    private static final String URL = "/v1/marceneiros";

    @Test
    @DisplayName("Deve retornar 200 ao buscar uma lista paginada de marceneiros")
    void testBuscarTodosMarceneiros() throws Exception {

        var marceneiro = buildComAuditoria();
        var output = BuscarTodosMarceneirosUseCaseFixture.buildOutput(marceneiro);
        var marceneiroDto = buildComTodosMarceneiroOutput(output);
        var marceneirosOutput = List.of(output);

        doReturn(List.of(output))
                .when(buscarTodosMarceneirosUseCase)
                .execute();

        mvc.perform(get(URL)
                        .param("page", "0")
                        .param("size", String.valueOf(marceneirosOutput.size())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value(marceneiroDto.getNome()));

        verify(mapper, atLeastOnce()).toMarceneiroDto(output);
    }

    @Test
    @DisplayName("Deve retornar 200 ao buscar um marceneiro pelo id")
    void testBuscarMarceneiroPorId() throws Exception {

        var marceneiro = buildComAuditoria();
        var marceneiroOutput = buildOutput(marceneiro);
        var id = marceneiro.getId().toString();
        var path = format(URL + "/{0}", id);

        var input = new BuscarMarceneiroPorIdUseCase.Input(id);

        doReturn(of(marceneiroOutput)).when(buscarMarceneiroPorIdUseCase).execute(input);

        var retorno = mvc.perform(get(path))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(retorno.getResponse());

        verify(mapper).toMarceneiroDto(marceneiroOutput);
    }

    @Test
    @DisplayName("Deve retornar 200 ao buscar um marceneiro pelo email")
    void testBuscarMarceneiroPorEmail() throws Exception {
        var marceneiro = buildComAuditoria();
        var marceneiroOutput = BuscarMarceneiroPorEmailUseCaseFixture.buildOutput(marceneiro);
        var email = marceneiro.getEmail().toString();
        var path = URL + "/email";

        var input = new BuscarMarceneiroPorEmailUseCase.Input(email);

        doReturn(of(marceneiroOutput)).when(buscarMarceneiroPorEmailUseCase).execute(input);

        var retorno = mvc.perform(get(path)
                        .param("email", email))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(retorno.getResponse());

        verify(mapper).toMarceneiroDto(marceneiroOutput);
    }

    @Test
    @DisplayName("Deve retornar 200 ao buscar um marceneiro pelo documento")
    void testBuscarMarceneiroPorDocumento() throws Exception {

        var marceneiro = buildComAuditoria();
        var marceneiroOutput = BuscarMarceneiroPorDocumentoUseCaseFixture.buildOutput(marceneiro);
        var documento = marceneiro.getEmail().value();
        var path = URL + "/documento";

        var input = new BuscarMarceneiroPorDocumentoUseCase.Input(documento);

        doReturn(of(marceneiroOutput)).when(buscarMarceneiroPorDocumentoUseCase).execute(input);

        var retorno = mvc.perform(get(path)
                        .param("documento", documento))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(retorno.getResponse());

        verify(mapper).toMarceneiroDto(marceneiroOutput);
    }

    @Test
    @DisplayName("Deve retornar 200 ao alterar situacao de um marceneiro")
    void testAlteraSituacao() throws Exception {

        var marceneiro = buildComAuditoria();
        var id = marceneiro.getId().toString();
        var novaSituacao = gerarObject(Situacao.class);
        var output = buildOutput(marceneiro, novaSituacao.getDescricao());

        var path = format(URL + "/{0}/atualizarsituacao", id);

        var input = new AlterarSitucaoUseCase.Input(id, novaSituacao.getDescricao());

        doReturn(output).when(alterarSitucaoUseCase).execute(input);

        var result = mvc.perform(put(path)
                        .param("situacao", novaSituacao.getDescricao()))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(output);
        assertNotNull(result.getResponse());

        verify(alterarSitucaoUseCase).execute(input);
        verify(mapper).toResponseMarceneiroDto(output);
    }

    @Test
    @DisplayName("Deve retornar 400 ao alterar situacao de um marceneiro e nao existir situação")
    void shouldThrowExceptionWhenSituacaoIsNotValid() throws Exception {

        var marceneiro = buildComAuditoria();
        var id = marceneiro.getId().toString();
        var novaSituacao = gerarString();
        var path = format(URL + "/{0}/atualizarsituacao", id);

        var input = new AlterarSitucaoUseCase.Input(id, novaSituacao);

        doThrow(new BusinessException(ERRO_SITUACAO_INEXISTENTE, novaSituacao))
                .when(alterarSitucaoUseCase).execute(input);

        mvc.perform(put(path)
                        .param("situacao", novaSituacao))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code")
                        .value(ERRO_SITUACAO_INEXISTENTE.getCode()))
                .andExpect(jsonPath("$.msg")
                        .value(format(ERRO_SITUACAO_INEXISTENTE.getMsg(), novaSituacao)));

        verify(alterarSitucaoUseCase).execute(input);
    }

    @Test
    @DisplayName("Deve retornar 400 ao alterar situacao e o Marceneiro não existir.")
    void shouldThrowExceptionWhenIdNotExists() throws Exception {

        var idInexistente = UUID.randomUUID().toString();
        var novaSituacao = gerarString();
        var path = format(URL + "/{0}/atualizarsituacao", idInexistente);

        var input = new AlterarSitucaoUseCase.Input(idInexistente, novaSituacao);

        doThrow(new BusinessException(ERRO_ID_INVALIDO, idInexistente))
                .when(alterarSitucaoUseCase).execute(input);

        mvc.perform(put(path)
                        .param("situacao", novaSituacao))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code")
                        .value(ERRO_ID_INVALIDO.getCode()))
                .andExpect(jsonPath("$.msg")
                        .value(format(ERRO_ID_INVALIDO.getMsg(), idInexistente)));

        verify(alterarSitucaoUseCase).execute(input);
    }

//    @Test
//    @DisplayName("Deve retornar 201 ao criar um marceneiro com sucesso")
//    void shouldCreateMarceneiroSuccessfully() throws Exception {
//
//        var marceneiro = buildComAuditoria();
//        var newMarceneiroDto = buildComParametros(marceneiro);
//
//        var input = buildCriarMarceneiroUseCaseInputComNewMarceneiro(newMarceneiroDto);
//
//        var output = new CriarMarceneiroUseCase.Output(
//                marceneiro.getId().toString(),
//                marceneiro.getNome().value(),
//                marceneiro.getSituacao().getDescricao());
//
//        var responseDto = new ResponseMarceneiroDto(
//                output.id(),
//                output.nome(),
//                output.situacao()
//        );
//
//        doReturn(output).when(criarMarceneiroUseCase).execute(input);
//
//        mvc.perform(post(URL)
//                        .contentType(APPLICATION_JSON)
//                        .content(toJson(newMarceneiroDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(responseDto.id()))
//                .andExpect(jsonPath("$.nome").value(responseDto.nome()))
//                .andExpect(jsonPath("$.situacao").value(responseDto.situacao()));
//    }

//    @Test
//    @DisplayName("Deve retornar 422 se ocorrer um erro ao criar o marceneiro")
//    void shouldReturnPreconditionFailedOnError() throws Exception {
//
//        var marceneiro = buildComAuditoria();
//        var newMarceneiroDto = buildComParametros(marceneiro);
//
//        var input = buildCriarMarceneiroUseCaseInputComNewMarceneiro(newMarceneiroDto);
//
//        doReturn(null).when(criarMarceneiroUseCase).execute(input);
//
//        mvc.perform(post(URL)
//                        .contentType(APPLICATION_JSON)
//                        .content(toJson(newMarceneiroDto)))
//                .andExpect(status().isUnprocessableEntity());
//    }

//    @Test
//    @DisplayName("Deve retornar 200 ao alterar Marceneiro com sucesso")
//    void testAlteraMarceneiro() throws Exception {
//
//        var marceneiro = buildComAuditoria();
//        var updateMarceneiroDto = UpdateMarceneiroDtoFixture.buildComParametros(marceneiro);
//        var input = buildInputComUpdateMarceneiroDto(updateMarceneiroDto);
//        var output = AlterarMarceneiroUseCaseFixture.buildOutput(marceneiro);
//        var marceneiroDto = MarceneiroDtoFixture.buildComAlterarMarceneiroOutput(output);
//
//        var path = format(URL + "/{0}", updateMarceneiroDto.id());
//
//        doReturn(output).when(alterarMarceneiroUseCase).execute(input);
//
//        var result = mvc.perform(put(path)
//                        .content(toJson(marceneiroDto))
//                        .contentType(APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertNotNull(output);
//        assertNotNull(result.getResponse());
//
//        assertEquals(toJson(marceneiroDto), result.getResponse().getContentAsString());
//
//        verify(mapper).toAlterarMarceneiroInput(updateMarceneiroDto);
//        verify(mapper).toMarceneiroDto(output);
//    }
}