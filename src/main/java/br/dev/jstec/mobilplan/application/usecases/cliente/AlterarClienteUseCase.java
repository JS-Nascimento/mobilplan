package br.dev.jstec.mobilplan.application.usecases.cliente;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_EXISTENTE;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.ClientePort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.cliente.Cliente;
import br.dev.jstec.mobilplan.domain.model.cliente.DadosContratuais;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarClienteUseCase extends UseCase<AlterarClienteUseCase.Input, AlterarClienteUseCase.Output> {

    private final ClientePort clientePort;

    @Override
    public Output execute(Input input) {

        var clienteAtual = clientePort.buscarPorId(input.id())
                .orElseThrow(() -> new BusinessException(ERRO_ENTIDADE_INEXISTENTE, "Cliente"));

        var telefones = input.telefones.stream()
                .map(TelefoneUseCase::getTelefoneModel)
                .collect(Collectors.toSet());

        var enderecos = input.enderecos.stream()
                .map(EnderecoUseCase::getEnderecoModel)
                .collect(Collectors.toSet());

        var clienteAtualizar = Cliente.with(
                clienteAtual.getId(),
                input.ativo,
                input.nome,
                input.tipoPessoa,
                input.email,
                telefones,
                enderecos,
                DadosContratuais.with(
                        input.documentoFiscal,
                        input.documentoIdentificador,
                        input.estadoCivil
                ),
                input.notificarPorEmail,
                input.notificarPorWhatsapp,
                clienteAtual.getCriadoEm(),
                clienteAtual.getAtualizadoEm(),
                clienteAtual.getTenantId()
        );

        if (clientePort.existe(clienteAtualizar)) {
            throw new BusinessException(ERRO_ENTIDADE_EXISTENTE, "Cliente");
        }

        var clienteSalvo = clientePort.salvar(clienteAtualizar);

        return new Output(
                clienteSalvo.getId(),
                clienteSalvo.isAtivo(),
                clienteSalvo.getNome(),
                clienteSalvo.getTipoPessoa().toString(),
                clienteSalvo.getEmail().toString(),
                clienteSalvo.getDadosContratuais().getDocumentoFiscal(),
                clienteSalvo.getDadosContratuais().getDocumentoIdentificador(),
                clienteSalvo.getDadosContratuais().getEstadoCivil().getDescricao(),
                clienteSalvo.isNotificarPorEmail(),
                clienteSalvo.isNotificarPorWhatsapp(),
                clienteSalvo.getTelefones().stream()
                        .map(telefone -> new TelefoneUseCase(
                                telefone.tipoTelefone().toString(),
                                telefone.numero(),
                                telefone.ddd(),
                                telefone.ddi()
                        ))
                        .collect(Collectors.toList()),
                clienteSalvo.getEnderecos().stream()
                        .map(endereco -> new EnderecoUseCase(
                                endereco.cep(),
                                endereco.logradouro(),
                                endereco.numero(),
                                endereco.complemento(),
                                endereco.bairro(),
                                endereco.cidade(),
                                endereco.uf()
                        ))
                        .collect(Collectors.toList()),
                clienteSalvo.getCriadoEm(),
                clienteSalvo.getAtualizadoEm(),
                clienteSalvo.getTenantId()
        );
    }

    public record Input(
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
            UUID tenantId
    ) {

    }

    private record TelefoneUseCase(
            String tipoTelefone,
            String numero,
            String ddd,
            String ddi
    ) {
        Telefone getTelefoneModel() {
            return Telefone.of(this.tipoTelefone, this.numero, this.ddd, this.ddi);
        }

    }

    private record EnderecoUseCase(
            String cep,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf
    ) {
        Endereco getEnderecoModel() {
            return Endereco.of(this.cep, this.logradouro, this.numero, this.complemento, this.bairro, this.cidade,
                    this.uf);
        }
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
