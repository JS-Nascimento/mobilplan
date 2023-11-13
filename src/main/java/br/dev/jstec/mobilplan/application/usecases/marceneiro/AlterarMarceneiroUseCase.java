package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.domain.marceneiro.Marceneiro.updateOf;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static java.util.UUID.fromString;

import br.dev.jstec.mobilplan.application.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.application.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.repository.MarceneiroRepository;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarMarceneiroUseCase.Input;
import br.dev.jstec.mobilplan.application.usecases.marceneiro.AlterarMarceneiroUseCase.Output;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlterarMarceneiroUseCase extends UseCase<Input, Output> {

    private final MarceneiroRepository marceneiroRepository;
    private final MarceneiroMapper mapper;

    @Override
    public Output execute(final Input input) {

        return marceneiroRepository.buscarPorId(fromString(input.id))
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
            .map(marceneiroRepository::salvar)
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
