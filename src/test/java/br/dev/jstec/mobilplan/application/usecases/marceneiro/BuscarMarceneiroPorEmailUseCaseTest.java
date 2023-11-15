package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCaseFixture.buildOutput;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarEmail;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import br.dev.jstec.mobilplan.application.domain.marceneiro.MarceneiroFixture;
import br.dev.jstec.mobilplan.application.domain.valueobject.Email;
import br.dev.jstec.mobilplan.application.repository.MarceneiroRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class BuscarMarceneiroPorEmailUseCaseTest {

    @InjectMocks
    private BuscarMarceneiroPorEmailUseCase buscarMarceneiroPorEmailUseCase;

    @Mock
    private MarceneiroRepository marceneiroRepository;

    @Spy
    private MarceneiroMapperImpl mapper;

    @Test
    @DisplayName("Deve retornar o marceneiro correspondente quando um e-mail existente é fornecido")
    void deveRetornarMarceneiroQuandoEmailExistenteFornecido() {

        var email = gerarEmail(true);
        var input = new BuscarMarceneiroPorEmailUseCase.Input(email);

        var marceneiro = MarceneiroFixture.buildComAuditoria();
        var output = buildOutput(marceneiro);

        doReturn(of(marceneiro)).when(marceneiroRepository).buscarPorEmail(new Email(email));

        var result = buscarMarceneiroPorEmailUseCase.execute(input);

        assertTrue(result.isPresent());
        result.ifPresent(
            r -> assertEquals(output, r));

        verify(marceneiroRepository).buscarPorEmail(new Email(email));
        verify(mapper).toBuscarMarceneiroPorEmailOutput(marceneiro);
    }

    @Test
    @DisplayName("Deve retornar um Optional vazio quando um e-mail não existente é fornecido")
    void deveRetornarOptionalVazioQuandoEmailNaoExistenteFornecido() {

        var email = gerarEmail(true);
        var input = new BuscarMarceneiroPorEmailUseCase.Input(email);

        doReturn(empty())
            .when(marceneiroRepository)
            .buscarPorEmail(new Email(email));

        var result = buscarMarceneiroPorEmailUseCase.execute(input);

        assertTrue(result.isEmpty());

        verify(marceneiroRepository).buscarPorEmail(new Email(email));
        verifyNoInteractions(mapper);
    }
}