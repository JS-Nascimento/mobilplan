package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase.Output;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarMarceneiroPorEmailUseCase
    extends UseCase<Input, Optional<Output>> {

    private final MarceneiroPort marceneiroPort;
    private final MarceneiroMapper mapper;

    @Override
    public Optional<Output> execute(final Input input) {

        return marceneiroPort
            .buscarPorEmail(new Email(input.email()))
            .map(mapper::toBuscarMarceneiroPorEmailOutput);


    }

    public record Input(String email) {

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
        String createdBy,
        String createdAt,
        String updatedBy,
        String updatedAt) {

    }
}
