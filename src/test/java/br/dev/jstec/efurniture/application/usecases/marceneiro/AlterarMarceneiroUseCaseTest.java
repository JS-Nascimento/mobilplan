package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroFixture.buildComId;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarMarceneiroUseCaseFixture.buildInput;
import static java.text.MessageFormat.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroFixture;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
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
class AlterarMarceneiroUseCaseTest {

    @Mock
    private MarceneiroRepository marceneiroRepository;

    @InjectMocks
    private AlterarMarceneiroUseCase alterarMarceneiroUseCase;

    @Spy
    private MarceneiroMapperImpl mapper;

    @Test
    @DisplayName("Deve atualizar o atributos do Marceneiro quando o marceneiro existir")
    void deveAtualizarOsAtributosQuandoMarceneiroExistir() {

        var marceneiro = MarceneiroFixture.build();
        var marceneiroAlterado = buildComId(marceneiro.getId().toString());
        var input = buildInput(marceneiro);

        doReturn(of(marceneiro))
            .when(marceneiroRepository)
            .buscarPorId(fromString(input.id()));

        doReturn(marceneiroAlterado)
            .when(marceneiroRepository)
            .salvar(marceneiroAlterado);

        var result = alterarMarceneiroUseCase.execute(input);

        assertEquals(marceneiro.getId().toString(), result.id());

        verify(mapper).toAlterarMarceneiroOutput(marceneiroAlterado);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o marceneiro não existir")
    void deveLancarBusinessExceptionQuandoMarceneiroNaoExistir() {

        var marceneiro = MarceneiroFixture.build();
        var input = buildInput(marceneiro);

        doReturn(empty()).when(marceneiroRepository).buscarPorId(marceneiro.getId());

        var exception = assertThrows(BusinessException.class, () ->
            alterarMarceneiroUseCase.execute(input));

        assertEquals(ERRO_ID_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_ID_INVALIDO.getMsg(), marceneiro.getId().toString()),
            exception.getErrorMessage().getMsg());
    }
}