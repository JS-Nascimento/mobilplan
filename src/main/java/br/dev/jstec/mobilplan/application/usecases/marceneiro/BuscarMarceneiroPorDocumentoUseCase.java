package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.BuscarMarceneiroPorDocumentoUseCase.Output;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarMarceneiroPorDocumentoUseCase
        extends UseCase<Input, Optional<Output>> {

    private final MarceneiroPort marceneiroPort;
    private final MarceneiroMapper mapper;

    @Override
    public Optional<Output> execute(final Input input) {

        return marceneiroPort
                .buscarPorDocumento(input.documentoFiscal())
                .map(mapper::toBuscarMarceneiroPorDocumentoOutput);
    }

    public record Input(String documentoFiscal) {

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
