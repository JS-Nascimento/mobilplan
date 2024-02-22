package br.dev.jstec.mobilplan.application.usecases.cliente;

import br.dev.jstec.mobilplan.application.ports.ClientePort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BuscarClientePorCriteriosUseCase
        extends UseCase<BuscarClientePorCriteriosUseCase.Input, List<BuscarClientePorCriteriosUseCase.Output>> {

    private final ClientePort clientePort;

    @Override
    public List<Output> execute(Input input) {

        var clientes = clientePort.buscar(input);

        return clientes.stream()
                .map(cliente -> new Output(
                        cliente.getId(),
                        cliente.isAtivo(),
                        cliente.getNome(),
                        cliente.getTipoPessoa().toString(),
                        cliente.getEmail().toString(),
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
                ))
                .toList();

    }

    public record Input(
            UUID id,
            Boolean ativo,
            String nome,
            String tipoPessoa,
            String email,
            Boolean notificarPorEmail,
            Boolean notificarPorWhatsapp

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
