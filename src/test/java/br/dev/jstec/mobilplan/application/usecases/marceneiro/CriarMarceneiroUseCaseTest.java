package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;
import static br.dev.jstec.mobilplan.application.usecases.marceneiro.CriarMarceneiroUseCaseFixture.buildCriarMarceneiroUseCaseInputComMarceneiro;
import static java.text.MessageFormat.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.domain.marceneiro.Marceneiro;
import br.dev.jstec.mobilplan.domain.marceneiro.MarceneiroFixture;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class CriarMarceneiroUseCaseTest {

    @Mock
    private MarceneiroPort marceneiroPort;

    @InjectMocks
    private CriarMarceneiroUseCase criarMarceneiroUseCase;

    @Captor
    private ArgumentCaptor<Marceneiro> marceneiroCaptor;

    @Test
    @DisplayName("Deve criar um marceneiro com sucesso")
    void shouldCreateMarceneiroSuccessfully() {

        var marceneiro = MarceneiroFixture.build();
        var email = marceneiro.getEmail().value();
        var documento = marceneiro.getTipoCliente().documento();

        doReturn(empty()).when(marceneiroPort).buscarPorDocumento(documento);
        doReturn(empty()).when(marceneiroPort).buscarPorEmail(new Email(email));
        doReturn(marceneiro).when(marceneiroPort).salvar(any(Marceneiro.class));

        var input = buildCriarMarceneiroUseCaseInputComMarceneiro(marceneiro);

        criarMarceneiroUseCase.execute(input);

        verify(marceneiroPort).salvar(marceneiroCaptor.capture());
        var realMarceneiro = marceneiroCaptor.getValue();

        assertEquals(input.nome(), realMarceneiro.getNome().value());
        assertEquals(input.nomeComercial(), realMarceneiro.getNomeComercial().value());
        assertEquals(input.tipoPessoa(),
            realMarceneiro.getTipoCliente().tipoPessoa().getDescricao());
        assertEquals(input.documento(), realMarceneiro.getTipoCliente().documento());
        assertEquals(input.email(), realMarceneiro.getEmail().value());
        assertEquals(input.telefones(), realMarceneiro.getTelefones());
        assertEquals(input.enderecos(), realMarceneiro.getEnderecos());

        verify(marceneiroPort).buscarPorDocumento(documento);
        verify(marceneiroPort).buscarPorEmail(new Email(email));
    }

    @Test
    @DisplayName("Deve lançar um exception quando o email já existe")
    void shouldThrowBusinessExceptionWhenEmailExists() {

        var marceneiro = MarceneiroFixture.build();
        var email = marceneiro.getEmail().value();
        var documento = marceneiro.getTipoCliente().documento();

        doReturn(empty()).when(marceneiroPort).buscarPorDocumento(documento);
        doReturn(of(marceneiro)).when(marceneiroPort).buscarPorEmail(new Email(email));

        var input = buildCriarMarceneiroUseCaseInputComMarceneiro(marceneiro);

        var exception = assertThrows(BusinessException.class, () ->
            criarMarceneiroUseCase.execute(input));

        assertEquals(ERRO_ENTIDADE_EXISTENTE.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_ENTIDADE_EXISTENTE.getMsg(), "marceneiro"),
            exception.getErrorMessage().getMsg());

        verify(marceneiroPort).buscarPorDocumento(documento);
        verify(marceneiroPort).buscarPorEmail(new Email(email));
        verify(marceneiroPort, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar um exception quando o documento já existe")
    void shouldThrowBusinessExceptionWhenDocumentoExists() {

        var marceneiro = MarceneiroFixture.build();
        var documento = marceneiro.getTipoCliente().documento();

        doReturn(of(marceneiro)).when(marceneiroPort).buscarPorDocumento(documento);

        var input = buildCriarMarceneiroUseCaseInputComMarceneiro(marceneiro);

        var exception = assertThrows(BusinessException.class, () ->
            criarMarceneiroUseCase.execute(input));

        assertEquals(ERRO_ENTIDADE_EXISTENTE.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_ENTIDADE_EXISTENTE.getMsg(), "marceneiro"),
            exception.getErrorMessage().getMsg());

        verify(marceneiroPort).buscarPorDocumento(documento);
        verify(marceneiroPort, never()).buscarPorEmail(any());
        verify(marceneiroPort, never()).salvar(any());
    }
}