package br.dev.jstec.efurniture.infrastructure.rest.dto.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.usecases.marceneiro.AlterarMarceneiroUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarMarceneiroPorIdUseCase;
import br.dev.jstec.efurniture.application.usecases.marceneiro.BuscarTodosMarceneirosUseCase;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = PRIVATE)
public class MarceneiroDtoFixture {

    public static MarceneiroDto buildComMarceneiroOutput(
        BuscarMarceneiroPorIdUseCase.Output marceneiro) {

        var enderecoDtos = marceneiro.enderecos()
            .stream()
            .map(EnderecoDtoFixture::buildComEndereco)
            .toList();

        var telefoneDtos = marceneiro.telefones()
            .stream()
            .map(TelefoneDtoFixture::buildComTelefone)
            .toList();

        return new MarceneiroDto(
            marceneiro.id(),
            marceneiro.situacao(),
            marceneiro.nome(),
            marceneiro.nomeComercial(),
            marceneiro.tipoPessoa(),
            marceneiro.documento(),
            marceneiro.email(),
            telefoneDtos,
            enderecoDtos,
            marceneiro.logomarcaFilename(),
            marceneiro.logomarcaUrl(),
            marceneiro.createdBy(),
            marceneiro.createdAt(),
            marceneiro.updatedBy(),
            marceneiro.updatedAt());
    }

    public static MarceneiroDto buildComTodosMarceneiroOutput(
        BuscarTodosMarceneirosUseCase.Output marceneiro) {

        var enderecoDtos = marceneiro.enderecos()
            .stream()
            .map(EnderecoDtoFixture::buildComEndereco)
            .toList();

        var telefoneDtos = marceneiro.telefones()
            .stream()
            .map(TelefoneDtoFixture::buildComTelefone)
            .toList();

        return new MarceneiroDto(
            marceneiro.id(),
            marceneiro.situacao(),
            marceneiro.nome(),
            marceneiro.nomeComercial(),
            marceneiro.tipoPessoa(),
            marceneiro.documento(),
            marceneiro.email(),
            telefoneDtos,
            enderecoDtos,
            marceneiro.logomarcaFilename(),
            marceneiro.logomarcaUrl(),
            marceneiro.createdBy(),
            marceneiro.createdAt(),
            marceneiro.updatedBy(),
            marceneiro.updatedAt());
    }

    public static MarceneiroDto buildComAlterarMarceneiroOutput(
        AlterarMarceneiroUseCase.Output marceneiro) {

        var enderecoDtos = marceneiro.enderecos()
            .stream()
            .map(EnderecoDtoFixture::buildComEndereco)
            .toList();

        var telefoneDtos = marceneiro.telefones()
            .stream()
            .map(TelefoneDtoFixture::buildComTelefone)
            .toList();

        return new MarceneiroDto(
            marceneiro.id(),
            marceneiro.situacao(),
            marceneiro.nome(),
            marceneiro.nomeComercial(),
            marceneiro.tipoPessoa(),
            marceneiro.documento(),
            marceneiro.email(),
            telefoneDtos,
            enderecoDtos,
            marceneiro.logomarcaFilename(),
            marceneiro.logomarcaUrl(),
            marceneiro.createdBy(),
            marceneiro.createdAt(),
            marceneiro.updatedBy(),
            marceneiro.updatedAt());
    }
}