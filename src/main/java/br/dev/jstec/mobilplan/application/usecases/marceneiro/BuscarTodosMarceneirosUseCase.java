package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import br.dev.jstec.mobilplan.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.mobilplan.application.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.application.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.application.repository.MarceneiroRepository;
import br.dev.jstec.mobilplan.application.usecases.NullaryUseCase;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarTodosMarceneirosUseCase extends
    NullaryUseCase<Collection<BuscarTodosMarceneirosUseCase.Output>> {

    private final MarceneiroRepository marceneiroRepository;
    private final MarceneiroMapper mapper;

    @Override
    public Collection<Output> execute() {

        Collection<Marceneiro> marceneiros = marceneiroRepository.buscarTodos();

        return marceneiros
            .stream()
            .map(mapper::toBuscarTodosMarceneirosOutput)
            .toList();
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
        String logomarcaFilename,
        String logomarcaUrl,
        String createdBy,
        String createdAt,
        String updatedBy,
        String updatedAt) {

    }
}

