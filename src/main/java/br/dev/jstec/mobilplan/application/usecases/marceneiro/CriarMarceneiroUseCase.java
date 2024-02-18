package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;
import static br.dev.jstec.mobilplan.domain.model.marceneiro.Marceneiro.createOf;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.MarceneiroPort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.valueobject.Email;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.domain.valueobject.TipoCliente;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarMarceneiroUseCase
        extends UseCase<CriarMarceneiroUseCase.Input, CriarMarceneiroUseCase.Output> {

    private final MarceneiroPort marceneiroPort;

    public Output execute(final Input input) {

        var tipoCliente = TipoCliente.createOf(
                input.tipoPessoa(),
                input.documento());

        if (marceneiroPort.buscarPorDocumento(tipoCliente.documento())
                .isPresent()) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "marceneiro");
        }

        if (marceneiroPort.buscarPorEmail(new Email(input.email)).isPresent()) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "marceneiro");
        }
        var marceneiro = createOf(
                input.nome,
                input.nomeComercial,
                tipoCliente,
                input.email,
                input.telefones,
                input.enderecos);

        var marceneiroSalvo = marceneiroPort.salvar(marceneiro);

        return new Output(
                marceneiroSalvo.getId().toString(),
                marceneiroSalvo.getNome().value(),
                marceneiroSalvo.getSituacao().getDescricao());
    }

    public record Input(String nome,
                        String nomeComercial,
                        String tipoPessoa,
                        String documento,
                        String email,
                        List<Telefone> telefones,
                        List<Endereco> enderecos) {

    }

    public record Output(String id,
                         String nome,
                         String situacao) {
    }
}
