package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarSituacaoUseCaseFixture.buildInput;
import static br.dev.jstec.efurniture.application.util.RandomHelper.gerarInteger;
import static java.text.MessageFormat.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroFixture;
import br.dev.jstec.efurniture.application.domain.marceneiro.Situacao;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class AlterarSitucaoUseCaseTest {

    @Mock
    private MarceneiroRepository marceneiroRepository;

    @InjectMocks
    private AlterarSitucaoUseCase alterarSitucaoUseCase;

    @Test
    @DisplayName("Deve atualizar o status quando o marceneiro existir")
    void deveAtualizarStatusQuandoMarceneiroExistir() {

        var marceneiro = MarceneiroFixture.build();
        var situacao = Situacao.values()[gerarInteger(1, 4)].getDescricao();

        doReturn(of(marceneiro)).when(marceneiroRepository).buscarPorId(marceneiro.marceneiroId());

        var input = buildInput(marceneiro, situacao);

        alterarSitucaoUseCase.execute(input);

        verify(marceneiroRepository).salvar(marceneiro);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o marceneiro não existir")
    void deveLancarBusinessExceptionQuandoMarceneiroNaoExistir() {

        var marceneiro = MarceneiroFixture.build();
        var situacao = Situacao.values()[gerarInteger(1, 4)].getDescricao();

        doReturn(empty()).when(marceneiroRepository).buscarPorId(marceneiro.marceneiroId());

        var input = buildInput(marceneiro, situacao);

        var exception = assertThrows(BusinessException.class, () ->
            alterarSitucaoUseCase.execute(input));

        assertEquals(ERRO_ID_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_ID_INVALIDO.getMsg(), marceneiro.marceneiroId().value()),
            exception.getErrorMessage().getMsg());
    }
}