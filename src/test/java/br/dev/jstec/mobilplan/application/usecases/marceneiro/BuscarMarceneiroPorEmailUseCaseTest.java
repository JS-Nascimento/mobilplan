package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCaseFixture.buildOutput;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarEmail;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.domain.model.marceneiro.MarceneiroFixture;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
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
    private MarceneiroPort marceneiroPort;

    @Spy
    private MarceneiroMapperImpl mapper;

    @Test
    @DisplayName("Deve retornar o marceneiro correspondente quando um e-mail existente é fornecido")
    void deveRetornarMarceneiroQuandoEmailExistenteFornecido() {

        var email = gerarEmail(true);
        var input = new BuscarMarceneiroPorEmailUseCase.Input(email);

        var marceneiro = MarceneiroFixture.buildComAuditoria();
        var output = buildOutput(marceneiro);

        doReturn(of(marceneiro)).when(marceneiroPort).buscarPorEmail(new Email(email));

        var result = buscarMarceneiroPorEmailUseCase.execute(input);

        assertTrue(result.isPresent());
        result.ifPresent(
                r -> assertEquals(output, r));

        verify(marceneiroPort).buscarPorEmail(new Email(email));
        verify(mapper).toBuscarMarceneiroPorEmailOutput(marceneiro);
    }

    @Test
    @DisplayName("Deve retornar um Optional vazio quando um e-mail não existente é fornecido")
    void deveRetornarOptionalVazioQuandoEmailNaoExistenteFornecido() {

        var email = gerarEmail(true);
        var input = new BuscarMarceneiroPorEmailUseCase.Input(email);

        doReturn(empty())
                .when(marceneiroPort)
                .buscarPorEmail(new Email(email));

        var result = buscarMarceneiroPorEmailUseCase.execute(input);

        assertTrue(result.isEmpty());

        verify(marceneiroPort).buscarPorEmail(new Email(email));
        verifyNoInteractions(mapper);
    }
}