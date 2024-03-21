package br.dev.jstec.mobilplan.application.usecases.usuario;

import static java.util.UUID.fromString;

import br.dev.jstec.mobilplan.application.ports.UsuarioPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarUsuarioPorIdUseCase extends
        UseCase<BuscarUsuarioPorIdUseCase.Input, Optional<BuscarUsuarioPorIdUseCase.Output>> {

    private final UsuarioPort usuarioPort;
    private final UsuarioMapper mapper;

    @Override
    public Optional<Output> execute(final Input input) {

        return usuarioPort
                .buscarPorId(fromString(input.id))
                .map(mapper::toBuscaUsuarioPorIdOutPut);
    }

    public record Input(String id) {

    }

    public record Output(
            String id,
            String nome,
            String email,
            boolean emailConfirmado,
            String situacao,
            Telefone telefone,
            String avatarFilename,
            String avatarUrl,
            List<String> roles,
            String createdAt,
            String updatedBy,
            String updatedAt) {
    }
}
