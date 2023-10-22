package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo.fromInstant;
import static br.dev.jstec.efurniture.application.domain.valueobject.AuditInfo.fromUuid;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import br.dev.jstec.efurniture.application.domain.valueobject.EnderecoFixture;
import br.dev.jstec.efurniture.application.domain.valueobject.TelefoneFixture;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarMarceneiroUseCase.Input;
import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarMarceneiroUseCase.Output;
import br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro.UpdateMarceneiroDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class AlterarMarceneiroUseCaseFixture {

    public static Output buildOutput(Marceneiro marceneiro) {

        return new Output(
            marceneiro.getId().toString(),
            marceneiro.getSituacao().getDescricao(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            marceneiro.getTelefones(),
            marceneiro.getEnderecos(),
            marceneiro.getLogomarcaFilename(),
            marceneiro.getLogomarcaUrl(),
            fromUuid(marceneiro.getCreatedBy()),
            fromInstant(marceneiro.getCreatedAt()),
            fromUuid(marceneiro.getUpdatedBy()),
            fromInstant(marceneiro.getUpdatedAt()));
    }

    public static Input buildInput(Marceneiro marceneiro) {

        return new Input(
            marceneiro.getId().toString(),
            marceneiro.getSituacao().getDescricao(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            marceneiro.getTelefones(),
            marceneiro.getEnderecos());
    }

    public static Input buildInputComUpdateMarceneiroDto(UpdateMarceneiroDto marceneiro) {

        var enderecos = marceneiro.enderecos()
            .stream()
            .map(EnderecoFixture::buildComEnderecoDto)
            .toList();

        var telefones = marceneiro.telefones()
            .stream()
            .map(TelefoneFixture::buildComTelefoneDto)
            .toList();


        return new Input(
            marceneiro.id(),
            marceneiro.situacao(),
            marceneiro.nome(),
            marceneiro.nomeComercial(),
            marceneiro.tipoPessoa(),
            marceneiro.documento(),
            marceneiro.email(),
            telefones,
            enderecos);
    }
}