package br.dev.jstec.mobilplan.application.usecases.cliente;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.ClientePort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BuscarClientePorIdUseCase
        extends UseCase<BuscarClientePorIdUseCase.Input, BuscarClientePorIdUseCase.Output> {

    private final ClientePort clientePort;

    @Override
    public Output execute(Input input) {

        var cliente = clientePort.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_EXISTENTE, "Cliente"));

        return new Output(
                cliente.getId(),
                cliente.isAtivo(),
                cliente.getNome(),
                cliente.getTipoPessoa().toString(),
                cliente.getEmail().value(),
                cliente.getDadosContratuais().getDocumentoFiscal(),
                cliente.getDadosContratuais().getDocumentoIdentificador(),
                cliente.getDadosContratuais().getEstadoCivil().getDescricao(),
                cliente.isNotificarPorEmail(),
                cliente.isNotificarPorWhatsapp(),
                cliente.getTelefones().stream()
                        .map(telefone -> new TelefoneUseCase(
                                telefone.getTipoTelefone().toString(),
                                telefone.getNumero(),
                                telefone.getDdd(),
                                telefone.getDdi()
                        ))
                        .collect(Collectors.toList()),
                cliente.getEnderecos().stream()
                        .map(endereco -> new EnderecoUseCase(
                                endereco.getCep(),
                                endereco.getLogradouro(),
                                endereco.getNumero(),
                                endereco.getComplemento(),
                                endereco.getBairro(),
                                endereco.getCidade(),
                                endereco.getUf()
                        ))
                        .collect(Collectors.toList()),
                cliente.getCriadoEm(),
                cliente.getAtualizadoEm(),
                cliente.getTenantId()
        );
    }

    public record Input(
            UUID id
    ) {

    }

    public record TelefoneUseCase(
            String tipoTelefone,
            String numero,
            String ddd,
            String ddi
    ) {

    }

    public record EnderecoUseCase(
            String cep,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf
    ) {
    }

    public record Output(
            UUID id,
            boolean ativo,
            String nome,
            String tipoPessoa,
            String email,
            String documentoFiscal,
            String documentoIdentificador,
            String estadoCivil,
            boolean notificarPorEmail,
            boolean notificarPorWhatsapp,
            List<TelefoneUseCase> telefones,
            List<EnderecoUseCase> enderecos,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm,
            UUID tenantId
    ) {

    }
}
