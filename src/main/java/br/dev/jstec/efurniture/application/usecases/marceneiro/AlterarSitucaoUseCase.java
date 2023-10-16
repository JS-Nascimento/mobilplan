package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro.updateStatus;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.UnitUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarSitucaoUseCase.Input;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlterarSitucaoUseCase extends UnitUseCase<Input> {

    private final MarceneiroRepository marceneiroRepository;

    @Override
    public void execute(final Input input) {

        marceneiroRepository.buscarPorId(input.marceneiro().marceneiroId())
            .stream()
            .findFirst()
            .ifPresentOrElse(
                marceneiro -> {
                    updateStatus(marceneiro, input.situacao);
                    marceneiroRepository.salvar(marceneiro);
                },
                () -> {
                    throw new BusinessException(ERRO_ID_INVALIDO,
                        input.marceneiro().marceneiroId().value());
                });
    }

    public record Input(Marceneiro marceneiro, String situacao) {

    }
}
