package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCaseFixture.buildOutput;
import static br.dev.jstec.mobilplan.domain.util.RandomHelper.gerarCpf;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.domain.model.marceneiro.MarceneiroFixture;
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
class BuscarMarceneiroPorDocumentoUseCaseTest {

    @InjectMocks
    private BuscarMarceneiroPorDocumentoUseCase buscarMarceneiroPorDocUseCase;

    @Mock
    private MarceneiroPort marceneiroPort;

    @Spy
    private MarceneiroMapperImpl mapper;

    @Test
    @DisplayName("Deve retornar o marceneiro correspondente quando um documento existente é fornecido")
    void deveRetornarMarceneiroQuandoEmailExistenteFornecido() {

        var documento = gerarCpf(true);
        var input = new BuscarMarceneiroPorDocumentoUseCase.Input(documento);

        var marceneiro = MarceneiroFixture.buildComAuditoria();
        var output = buildOutput(marceneiro);

        doReturn(of(marceneiro)).when(marceneiroPort).buscarPorDocumento(documento);

        var result = buscarMarceneiroPorDocUseCase.execute(input);

        assertTrue(result.isPresent());
        result.ifPresent(
            r -> assertEquals(output, r));

        verify(marceneiroPort).buscarPorDocumento(documento);
        verify(mapper).toBuscarMarceneiroPorDocumentoOutput(marceneiro);
    }

    @Test
    @DisplayName("Deve retornar um Optional vazio quando um documento não existente é fornecido")
    void deveRetornarOptionalVazioQuandoEmailNaoExistenteFornecido() {

        var documento = gerarCpf(true);
        var input = new BuscarMarceneiroPorDocumentoUseCase.Input(documento);

        doReturn(empty())
            .when(marceneiroPort)
            .buscarPorDocumento(documento);

        var result = buscarMarceneiroPorDocUseCase.execute(input);

        assertTrue(result.isEmpty());

        verify(marceneiroPort).buscarPorDocumento(documento);
        verifyNoInteractions(mapper);
    }
}