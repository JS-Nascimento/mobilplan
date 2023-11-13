package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.domain.marceneiro.Marceneiro.updateStatus;
import static br.dev.jstec.mobilplan.application.domain.marceneiro.Situacao.of;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static java.util.UUID.fromString;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.repository.MarceneiroRepository;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSitucaoUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarSitucaoUseCase.Output;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlterarSitucaoUseCase extends UseCase<Input, Output> {

    private final MarceneiroRepository marceneiroRepository;

    @Override
    public Output execute(final Input input) {

        var situacao = of(input.situacao).getDescricao();

        var marceneiro = marceneiroRepository.buscarPorId(fromString(input.id))
            .orElseThrow(() -> new BusinessException(ERRO_ID_INVALIDO, input.id));

        var marceneiroAlterar = updateStatus(marceneiro, situacao);
        var marceneiroAlterado = marceneiroRepository.salvar(marceneiroAlterar);

        return new Output(
            marceneiroAlterado.getId().toString(),
            marceneiroAlterado.getNome().value(),
            marceneiroAlterado.getSituacao().getDescricao());
    }

    public record Input(String id, String situacao) {

    }

    public record Output(String id,
                         String nome,
                         String situacao) {

    }
}
