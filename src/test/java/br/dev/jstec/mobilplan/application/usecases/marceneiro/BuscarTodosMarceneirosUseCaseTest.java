package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.domain.model.marceneiro.MarceneiroFixture;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class BuscarTodosMarceneirosUseCaseTest {

    @Mock
    private MarceneiroPort marceneiroPort;

    @InjectMocks
    private BuscarTodosMarceneirosUseCase buscarTodosMarceneirosUseCase;

    @Spy
    private MarceneiroMapper mapper;

    @Test
    @DisplayName("Deve retornar todos os marceneiros com sucesso")
    void shouldReturnAllMarceneirosSuccessfully() {

        var marceneiro = MarceneiroFixture.buildComAuditoria();
        var marceneiros = singletonList(marceneiro);

        doReturn(marceneiros).when(marceneiroPort).buscarTodos();

        var output = BuscarTodosMarceneirosUseCaseFixture.buildOutput(marceneiro);
        var expectedOutputs = singletonList(output);

        doReturn(output).when(mapper).toBuscarTodosMarceneirosOutput(marceneiro);

        var result = buscarTodosMarceneirosUseCase.execute();

        assertEquals(expectedOutputs, new ArrayList<>(result));

        verify(marceneiroPort).buscarTodos();
        verify(mapper).toBuscarTodosMarceneirosOutput(marceneiro);
    }
}
