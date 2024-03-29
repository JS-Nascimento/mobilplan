package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.model.marceneiro.Marceneiro;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class CriarMarceneiroUseCaseFixture {

    public static CriarMarceneiroUseCase.Input
    buildCriarMarceneiroUseCaseInputComMarceneiro(Marceneiro marceneiro) {

        return new CriarMarceneiroUseCase.Input(
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            marceneiro.getTelefones(),
            marceneiro.getEnderecos());
    }

//    public static CriarMarceneiroUseCase.Input
//    buildCriarMarceneiroUseCaseInputComNewMarceneiro(NewMarceneiroDto marceneiro) {
//
//        var enderecos = marceneiro.enderecos()
//            .stream()
//            .map(EnderecoFixture::buildComEnderecoDto)
//            .toList();
//
//        var telefones = marceneiro.telefones()
//            .stream()
//            .map(TelefoneFixture::buildComTelefoneDto)
//            .toList();
//
//        return new CriarMarceneiroUseCase.Input(
//            marceneiro.nome(),
//            marceneiro.nomeComercial(),
//            marceneiro.tipoPessoa(),
//            marceneiro.documento(),
//            marceneiro.email(),
//            telefones,
//            enderecos);
//    }
}
