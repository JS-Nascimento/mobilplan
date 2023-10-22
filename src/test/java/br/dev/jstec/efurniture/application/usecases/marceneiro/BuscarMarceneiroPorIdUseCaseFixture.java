package br.dev.jstec.efurniture.application.usecases.marceneiro;

import static java.time.format.DateTimeFormatter.ofPattern;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.application.domain.marceneiro.Marceneiro;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class BuscarMarceneiroPorIdUseCaseFixture {

    public static BuscarMarceneiroPorIdUseCase.Output buildOutput(Marceneiro marceneiro) {

        return new BuscarMarceneiroPorIdUseCase.Output(
            marceneiro.getId().toString(),
            marceneiro.getSituacao().getDescricao(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().value(),
            marceneiro.getTelefones(),
            marceneiro.getEnderecos(),
            marceneiro.getLogomarcaUrl(),
            marceneiro.getLogomarcaFilename(),
            marceneiro.getCreatedBy().toString(),
            marceneiro.getCreatedAt().format(ofPattern("dd/MM/yyyy HH:mm:ss")),
            marceneiro.getUpdatedBy().toString(),
            marceneiro.getUpdatedAt().format(ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}