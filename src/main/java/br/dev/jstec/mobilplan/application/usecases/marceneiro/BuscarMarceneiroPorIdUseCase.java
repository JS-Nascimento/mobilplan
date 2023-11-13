package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static java.util.UUID.fromString;

import br.dev.jstec.mobilplan.application.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.application.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.application.repository.MarceneiroRepository;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarMarceneiroPorIdUseCase extends
    UseCase<BuscarMarceneiroPorIdUseCase.Input, Optional<BuscarMarceneiroPorIdUseCase.Output>> {

    private final MarceneiroRepository marceneiroRepository;
    private final MarceneiroMapper mapper;

    @Override
    public Optional<Output> execute(final Input input) {

        return marceneiroRepository
            .buscarPorId(fromString(input.id))
            .map(mapper::toBuscarMarceneiroPorIdOutput);
    }

    public record Input(String id) {

    }

    public record Output(
        String id,
        String situacao,
        String nome,
        String nomeComercial,
        String tipoPessoa,
        String documento,
        String email,
        List<Telefone> telefones,
        List<Endereco> enderecos,
        String logomarcaUrl,
        String logomarcaFilename,
        String createdBy,
        String createdAt,
        String updatedBy,
        String updatedAt) {
    }
}
