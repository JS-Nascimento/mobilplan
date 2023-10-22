package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro.updateStatus;
import static br.dev.jstec.efurniture.application.domain.marceneiro.Situacao.of;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static java.util.UUID.fromString;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.UseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarSitucaoUseCase.Input;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarSitucaoUseCase.Output;
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
