package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;
import static br.dev.jstec.efurniture.application.usecases.marceneiro.CriarMarceneiroUseCaseFixture.buildCriarMarceneiroUseCaseInputComMarceneiro;
import static java.text.MessageFormat.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroFixture;
import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
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
    private MarceneiroRepository marceneiroRepository;

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

        doReturn(empty()).when(marceneiroRepository).buscarPorDocumento(documento);
        doReturn(empty()).when(marceneiroRepository).buscarPorEmail(new Email(email));
        doReturn(marceneiro).when(marceneiroRepository).salvar(any(Marceneiro.class));

        var input = buildCriarMarceneiroUseCaseInputComMarceneiro(marceneiro);

        criarMarceneiroUseCase.execute(input);

        verify(marceneiroRepository).salvar(marceneiroCaptor.capture());
        var realMarceneiro = marceneiroCaptor.getValue();

        assertEquals(input.nome(), realMarceneiro.getNome().value());
        assertEquals(input.nomeComercial(), realMarceneiro.getNomeComercial().value());
        assertEquals(input.tipoCliente(), realMarceneiro.getTipoCliente());
        assertEquals(input.email(), realMarceneiro.getEmail().value());
        assertEquals(input.telefones(), realMarceneiro.getTelefones());
        assertEquals(input.enderecos(), realMarceneiro.getEnderecos());

        verify(marceneiroRepository).buscarPorDocumento(documento);
        verify(marceneiroRepository).buscarPorEmail(new Email(email));
    }

    @Test
    @DisplayName("Deve lançar um exception quando o email já existe")
    void shouldThrowBusinessExceptionWhenEmailExists() {

        var marceneiro = MarceneiroFixture.build();
        var email = marceneiro.getEmail().value();
        var documento = marceneiro.getTipoCliente().documento();

        doReturn(empty()).when(marceneiroRepository).buscarPorDocumento(documento);
        doReturn(of(marceneiro)).when(marceneiroRepository).buscarPorEmail(new Email(email));

        var input = buildCriarMarceneiroUseCaseInputComMarceneiro(marceneiro);

        var exception = assertThrows(BusinessException.class, () ->
            criarMarceneiroUseCase.execute(input));

        assertEquals(ERRO_ENTIDADE_EXISTENTE.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_ENTIDADE_EXISTENTE.getMsg(), "marceneiro"),
            exception.getErrorMessage().getMsg());

        verify(marceneiroRepository).buscarPorDocumento(documento);
        verify(marceneiroRepository).buscarPorEmail(new Email(email));
        verify(marceneiroRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar um exception quando o documento já existe")
    void shouldThrowBusinessExceptionWhenDocumentoExists() {

        var marceneiro = MarceneiroFixture.build();
        var documento = marceneiro.getTipoCliente().documento();

        doReturn(of(marceneiro)).when(marceneiroRepository).buscarPorDocumento(documento);

        var input = buildCriarMarceneiroUseCaseInputComMarceneiro(marceneiro);

        var exception = assertThrows(BusinessException.class, () ->
            criarMarceneiroUseCase.execute(input));

        assertEquals(ERRO_ENTIDADE_EXISTENTE.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_ENTIDADE_EXISTENTE.getMsg(), "marceneiro"),
            exception.getErrorMessage().getMsg());

        verify(marceneiroRepository).buscarPorDocumento(documento);
        verify(marceneiroRepository, never()).buscarPorEmail(any());
        verify(marceneiroRepository, never()).salvar(any());
    }
}