package br.dev.jstec.efurniture.application.usecases.marceneiro;

import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.UseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase.Input;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorEmailUseCase.Output;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarMarceneiroPorEmailUseCase
    extends UseCase<Input, Optional<Output>> {

    private final MarceneiroRepository marceneiroRepository;
    private final MarceneiroMapper mapper;

    @Override
    public Optional<Output> execute(final Input input) {

        return marceneiroRepository
            .buscarPorEmail(new Email(input.email()))
            .map(mapper::mapperToBuscaPorEmailOutput);


    }

    public record Input(String email) {

    }

    public record Output(
        String marceneiroId,

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
