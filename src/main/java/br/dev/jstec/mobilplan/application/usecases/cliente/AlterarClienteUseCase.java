package br.dev.jstec.mobilplan.application.usecases.cliente;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_ENTIDADE_INEXISTENTE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.ports.ClientePort;
import br.dev.jstec.mobilplan.application.usecases.UseCase;
import br.dev.jstec.mobilplan.domain.model.cliente.Cliente;
import br.dev.jstec.mobilplan.domain.model.cliente.DadosContratuais;
import br.dev.jstec.mobilplan.domain.valueobject.EnderecoVO;
import br.dev.jstec.mobilplan.domain.valueobject.TelefoneVO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


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
                input.id,
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
                                telefone.getTipoTelefone().toString(),
                                telefone.getNumero(),
                                telefone.getDdd(),
                                telefone.getDdi()
                        ))
                        .collect(Collectors.toList()),
                clienteSalvo.getEnderecos().stream()
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

    public record TelefoneUseCase(
            String tipoTelefone,
            String numero,
            String ddd,
            String ddi
    ) {
        TelefoneVO getTelefoneModel() {
            return TelefoneVO.with(this.tipoTelefone, this.numero, this.ddd, this.ddi);
        }

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
        EnderecoVO getEnderecoModel() {
            return EnderecoVO.with(this.cep, this.logradouro, this.numero, this.complemento, this.bairro, this.cidade,
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
