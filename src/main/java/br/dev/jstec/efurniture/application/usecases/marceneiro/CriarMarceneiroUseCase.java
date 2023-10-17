package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro.createOf;
import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import br.dev.jstec.efurniture.application.domain.valueobject.TipoCliente;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.UseCase;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CriarMarceneiroUseCase
    extends UseCase<CriarMarceneiroUseCase.Input, CriarMarceneiroUseCase.Output> {

    private final MarceneiroRepository marceneiroRepository;

    public Output execute(final Input input) {

        if (marceneiroRepository.buscarPorDocumento(input.tipoCliente().documentoFiscal())
            .isPresent()) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "marceneiro");
        }

        if (marceneiroRepository.buscarPorEmail(new Email(input.email)).isPresent()) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "marceneiro");
        }
        var marceneiro = createOf(
            input.nome,
            input.nomeComercial,
            input.tipoCliente,
            input.email,
            input.telefones,
            input.enderecos);

        var marceneiroSalvo = marceneiroRepository.salvar(marceneiro);

        return new Output(
            marceneiroSalvo.getMarceneiroId().value(),
            marceneiroSalvo.getNome().value());
    }

    public record Input(String nome,
                        String nomeComercial,
                        TipoCliente tipoCliente,
                        String email,
                        List<Telefone> telefones,
                        List<Endereco> enderecos) {

    }

    public record Output(String id,
                         String nome) {

    }
}
