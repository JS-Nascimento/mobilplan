package br.dev.jstec.mobilplan.infrastructure.persistence.mapper.impl;

import br.dev.jstec.mobilplan.domain.model.cliente.Cliente;
import br.dev.jstec.mobilplan.domain.model.cliente.DadosContratuais;
import br.dev.jstec.mobilplan.domain.valueobject.Endereco;
import br.dev.jstec.mobilplan.domain.valueobject.Telefone;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.ClienteEntity;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.DadosContratuaisEmbedded;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.EnderecoCollection;
import br.dev.jstec.mobilplan.infrastructure.persistence.entity.cliente.TelefoneCollection;
import br.dev.jstec.mobilplan.infrastructure.persistence.mapper.IClienteMapper;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements IClienteMapper {
    @Override
    public Cliente toDomainModel(ClienteEntity clienteEntity) {

        return Cliente.with(
                clienteEntity.getId(),
                clienteEntity.isAtivo(),
                clienteEntity.getNome(),
                clienteEntity.getTipoPessoa(),
                clienteEntity.getEmail(),
                toTelefoneDomainModel(
                        clienteEntity.getTelefones()
                ),
                toEnderecoDomainModel(
                        clienteEntity.getEnderecos()
                ),
                toDadosContratuaisDomainModel(
                        clienteEntity.getDadosContratuais()
                ),
                clienteEntity.isNotificarPorEmail(),
                clienteEntity.isNotificarPorWhatsapp(),
                clienteEntity.getCriadoEm(),
                clienteEntity.getAtualizadoEm(),
                clienteEntity.getTenantId()
        );
    }

    @Override
    public ClienteEntity toEntity(Cliente cliente) {

        var entity = new ClienteEntity();
        entity.setAtivo(cliente.isAtivo());
        entity.setNome(cliente.getNome());
        entity.setTipoPessoa(cliente.getTipoPessoa().getDescricao());
        entity.setEmail(cliente.getEmail().value());
        entity.setTelefones(toTelefoneCollection(cliente.getTelefones()));
        entity.setEnderecos(toEnderecoCollection(cliente.getEnderecos()));
        entity.setDadosContratuais(toDadosContratuaisEmbedded(cliente.getDadosContratuais()));
        entity.setNotificarPorEmail(cliente.isNotificarPorEmail());
        entity.setNotificarPorWhatsapp(cliente.isNotificarPorWhatsapp());
        entity.setTenantId(cliente.getTenantId());

        return entity;
    }

    private Set<Telefone> toTelefoneDomainModel(Set<TelefoneCollection> telefoneCollections) {

        if (telefoneCollections == null || telefoneCollections.isEmpty()) {
            return Collections.emptySet();
        }

        return telefoneCollections.stream()
                .map(telefoneCollection -> Telefone.of(
                        telefoneCollection.getTipoTelefone(),
                        telefoneCollection.getNumero(),
                        telefoneCollection.getDdd(),
                        telefoneCollection.getDdi()
                ))
                .collect(Collectors.toSet());
    }

    private Set<Endereco> toEnderecoDomainModel(Set<EnderecoCollection> enderecoCollections) {
        if (enderecoCollections == null || enderecoCollections.isEmpty()) {
            return Collections.emptySet();
        }

        return enderecoCollections.stream()
                .map(enderecoCollection -> Endereco.of(
                        enderecoCollection.getLogradouro(),
                        enderecoCollection.getNumero(),
                        enderecoCollection.getComplemento(),
                        enderecoCollection.getBairro(),
                        enderecoCollection.getCidade(),
                        enderecoCollection.getUf(),
                        enderecoCollection.getCep()
                ))
                .collect(Collectors.toSet());
    }

    private DadosContratuais toDadosContratuaisDomainModel(DadosContratuaisEmbedded dadosContratuaisEmbedded) {

        if (dadosContratuaisEmbedded == null) {
            return null;
        }

        return DadosContratuais.with(
                dadosContratuaisEmbedded.getDocumentoFiscal(),
                dadosContratuaisEmbedded.getDocumentoIdentificador(),
                dadosContratuaisEmbedded.getEstadoCivil()
        );
    }

    private Set<TelefoneCollection> toTelefoneCollection(Set<Telefone> telefones) {

        if (telefones == null || telefones.isEmpty()) {
            return Collections.emptySet();
        }

        return telefones.stream()
                .map(telefone -> new TelefoneCollection(
                        telefone.tipoTelefone().getDescricao(),
                        telefone.numero(),
                        telefone.ddd(),
                        telefone.ddi()
                ))
                .collect(Collectors.toSet());
    }

    private Set<EnderecoCollection> toEnderecoCollection(Set<Endereco> enderecos) {

        if (enderecos == null || enderecos.isEmpty()) {
            return Collections.emptySet();
        }

        return enderecos.stream()
                .map(endereco -> new EnderecoCollection(
                        endereco.logradouro(),
                        endereco.numero(),
                        endereco.complemento(),
                        endereco.bairro(),
                        endereco.cidade(),
                        endereco.uf(),
                        endereco.cep()
                ))
                .collect(Collectors.toSet());
    }

    private DadosContratuaisEmbedded toDadosContratuaisEmbedded(DadosContratuais dadosContratuais) {

        if (dadosContratuais == null) {
            return null;
        }

        return new DadosContratuaisEmbedded(
                dadosContratuais.getDocumentoFiscal(),
                dadosContratuais.getDocumentoIdentificador(),
                dadosContratuais.getEstadoCivil().getDescricao()
        );
    }
}
