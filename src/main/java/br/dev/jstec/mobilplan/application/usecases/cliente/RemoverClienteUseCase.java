package br.dev.jstec.mobilplan.application.usecases.cliente;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.ClientePort;
import br.dev.jstec.mobilplan.application.usecases.UnitUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoverClienteUseCase extends UnitUseCase<RemoverClienteUseCase.Input> {

    private final ClientePort clientePort;

    public void execute(Input input) {

        var cliente = clientePort.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "Cliente"));

        clientePort.remover(cliente);
    }

    public static record Input(UUID id) {
    }
}
