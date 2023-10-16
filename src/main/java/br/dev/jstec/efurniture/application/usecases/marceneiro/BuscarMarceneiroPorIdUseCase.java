package br.dev.jstec.efurniture.application.usecases.marceneiro;

import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroId;
import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.UseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase.Input;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase.Output;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarMarceneiroPorIdUseCase
    extends UseCase<Input, Optional<Output>> {

    private final MarceneiroRepository marceneiroRepository;
    private final MarceneiroMapper mapper;

    @Override
    public Optional<Output> execute(final Input input) {

        return marceneiroRepository
            .buscarPorId(input.marceneiroId())
            .map(mapper::mapperToBuscaPorIdOutput);
    }

    public record Input(MarceneiroId marceneiroId) {

    }

    public record Output(
        String marceneiroId,
        String nome,
        String nomeComercial,
        String tipoPessoa,
        String documentoFiscal,
        String email,
        List<Telefone> telefones,
        List<Endereco> enderecos,
        String createdBy,
        String createdAt,
        String updatedBy,
        String updatedAt) {
    }
}
