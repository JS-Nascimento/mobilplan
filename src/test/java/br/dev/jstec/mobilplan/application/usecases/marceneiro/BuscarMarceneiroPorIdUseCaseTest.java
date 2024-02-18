package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCaseFixture.buildOutput;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.domain.model.marceneiro.MarceneiroFixture;
import java.util.UUID;
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
class BuscarMarceneiroPorIdUseCaseTest {

    @InjectMocks
    private BuscarMarceneiroPorIdUseCase buscarMarceneiroPorIdUseCase;

    @Mock
    private MarceneiroPort marceneiroPort;

    @Spy
    private MarceneiroMapperImpl mapper;

    @Test
    @DisplayName("Deve retornar o marceneiro correspondente quando um id existente é fornecido")
    void deveRetornarMarceneiroQuandoEmailExistenteFornecido() {

        var marceneiroId = UUID.randomUUID();
        var input = new BuscarMarceneiroPorIdUseCase.Input(marceneiroId.toString());

        var marceneiro = MarceneiroFixture.buildComAuditoria();
        var output = buildOutput(marceneiro);

        doReturn(of(marceneiro)).when(marceneiroPort).buscarPorId(marceneiroId);

        var result = buscarMarceneiroPorIdUseCase.execute(input);

        assertTrue(result.isPresent());
        result.ifPresent(
            r -> assertEquals(output, r));

        verify(marceneiroPort).buscarPorId(marceneiroId);
        verify(mapper).toBuscarMarceneiroPorIdOutput(marceneiro);
    }

    @Test
    @DisplayName("Deve retornar um Optional vazio quando um id não existente é fornecido")
    void deveRetornarOptionalVazioQuandoEmailNaoExistenteFornecido() {

        var marceneiroId = UUID.randomUUID();
        var input = new BuscarMarceneiroPorIdUseCase.Input(marceneiroId.toString());

        doReturn(empty())
            .when(marceneiroPort)
            .buscarPorId(marceneiroId);

        var result = buscarMarceneiroPorIdUseCase.execute(input);

        assertTrue(result.isEmpty());

        verify(marceneiroPort).buscarPorId(marceneiroId);
        verifyNoInteractions(mapper);
    }
}