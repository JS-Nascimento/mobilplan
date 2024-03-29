package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static br.dev.jstec.mobilplan.domain.model.marceneiro.Marceneiro.updateOf;
import static java.util.UUID.fromString;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarMarceneiroUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarMarceneiroUseCase.Output;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlterarMarceneiroUseCase extends UseCase<Input, Output> {

    private final MarceneiroPort marceneiroPort;
    private final MarceneiroMapper mapper;

    @Override
    public Output execute(final Input input) {

        return marceneiroPort.buscarPorId(fromString(input.id))
            .map(marceneiro -> updateOf(
                input.id,
                input.situacao,
                input.nome,
                input.nomeComercial,
                input.tipoPessoa,
                input.documento,
                input.email,
                input.telefones,
                input.enderecos
            ))
            .map(marceneiroPort::salvar)
            .map(mapper::toAlterarMarceneiroOutput)
            .orElseThrow(() -> new BusinessException(ERRO_ID_INVALIDO, input.id));
    }

    public record Input(

        String id,
        String situacao,
        String nome,
        String nomeComercial,
        String tipoPessoa,
        String documento,
        String email,
        List<Telefone> telefones,
        List<Endereco> enderecos) {

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
