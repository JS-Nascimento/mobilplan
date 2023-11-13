package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.domain.marceneiro.MarceneiroFixture.buildComIdESituacao;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSituacaoUseCaseFixture.buildInput;
import static br.dev.jstec.mobilplan.application.util.RandomHelper.gerarInteger;
import static java.text.MessageFormat.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import br.dev.jstec.mobilplan.application.domain.marceneiro.MarceneiroFixture;
import br.dev.jstec.mobilplan.application.domain.marceneiro.Situacao;
import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.repository.MarceneiroRepository;
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
        var marceneiroAlterado = buildComIdESituacao(
            marceneiro.getId().toString(), situacao);

        var input = buildInput(marceneiro.getId().toString(), situacao);

        doReturn(of(marceneiro))
            .when(marceneiroRepository)
            .buscarPorId(marceneiro.getId());

        doReturn(marceneiroAlterado).when(marceneiroRepository).salvar(marceneiro);

        var result = alterarSitucaoUseCase.execute(input);

        assertEquals(marceneiro.getId().toString(), result.id());
        assertEquals(situacao, result.situacao());
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o marceneiro não existir")
    void deveLancarBusinessExceptionQuandoMarceneiroNaoExistir() {

        var marceneiro = MarceneiroFixture.build();
        var situacao = Situacao.values()[gerarInteger(1, 4)].getDescricao();

        doReturn(empty()).when(marceneiroRepository).buscarPorId(marceneiro.getId());

        var input = buildInput(marceneiro.getId().toString(), situacao);

        var exception = assertThrows(BusinessException.class, () ->
            alterarSitucaoUseCase.execute(input));

        assertEquals(ERRO_ID_INVALIDO.getCode(), exception.getErrorMessage().getCode());
        assertEquals(format(ERRO_ID_INVALIDO.getMsg(), marceneiro.getId().toString()),
            exception.getErrorMessage().getMsg());
    }
}