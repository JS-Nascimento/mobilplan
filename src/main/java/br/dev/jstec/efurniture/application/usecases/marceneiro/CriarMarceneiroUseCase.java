package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro.createOf;
import static br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo.fromInstant;
import static br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo.fromUuid;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.efurniture.application.domain.valueobject.Email;
import br.dev.jstec.efurniture.application.domain.valueobject.Endereco;
import br.dev.jstec.efurniture.application.domain.valueobject.Telefone;
import br.dev.jstec.efurniture.application.domain.valueobject.TipoCliente;
import br.dev.jstec.efurniture.application.repository.MarceneiroRepository;
import br.dev.jstec.efurniture.application.usecases.UseCase;
import br.dev.jstec.efurniture.exceptions.BusinessException;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CriarMarceneiroUseCase
        extends UseCase<CriarMarceneiroUseCase.Input, CriarMarceneiroUseCase.Output> {

    private final MarceneiroRepository marceneiroRepository;

    public Output execute(final Input input) {

        if (marceneiroRepository.buscarPorDocumento(input.tipoCliente().documentoFiscal()).isPresent()) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "marceneiro");
        }

        if (marceneiroRepository.buscarPorEmail(new Email(input.email)).isPresent()) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "marceneiro");
        }

        var marceneiro = marceneiroRepository.salvar(
                createOf(
                        input.nome,
                        input.nomeComercial,
                        input.tipoCliente,
                        input.email,
                        input.telefones,
                        input.enderecos,
                        input.createdBy,
                        input.logomarca
                )
        );
        return new Output(
                marceneiro.marceneiroId().value(),
                marceneiro.nome().value(),
                fromUuid(marceneiro.auditInfo().createdBy()),
                fromInstant(marceneiro.auditInfo().createdAt())
        );
    }

    public record Input(String nome,
                        String nomeComercial,
                        TipoCliente tipoCliente,
                        String email,
                        List<Telefone> telefones,
                        List<Endereco> enderecos,
                        String createdBy,
                        String logomarca) {
    }

    public record Output(String id,
                         String nome,
                         String createBy,
                         String createdAt) {
    }
}
