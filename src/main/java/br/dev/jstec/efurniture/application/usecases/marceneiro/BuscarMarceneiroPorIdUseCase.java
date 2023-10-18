package br.dev.jstec.efurniture.application.usecases.marceneiro;

import br.dev.jstec.efurniture.application.domain.marceneiro.MarceneiroId;
import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.UseCase;
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
            .buscarPorId(MarceneiroId.with(input.marceneiroId()))
            .map(mapper::mapperToBuscaPorIdOutput);
    }

    public record Input(String marceneiroId) {

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
