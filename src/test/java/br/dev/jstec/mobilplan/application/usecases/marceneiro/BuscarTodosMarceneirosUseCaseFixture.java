package br.dev.jstec.mobilplan.application.usecases.marceneiro;

import static java.time.format.DateTimeFormatter.ofPattern;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.marceneiro.Marceneiro;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class BuscarTodosMarceneirosUseCaseFixture {

    public static BuscarTodosMarceneirosUseCase.Output buildOutput(Marceneiro marceneiro) {

        return new BuscarTodosMarceneirosUseCase.Output(
            marceneiro.getId().toString(),
            marceneiro.getSituacao().getDescricao(),
            marceneiro.getNome().value(),
            marceneiro.getNomeComercial().value(),
            marceneiro.getTipoCliente().tipoPessoa().getDescricao(),
            marceneiro.getTipoCliente().documento(),
            marceneiro.getEmail().toString(),
            marceneiro.getTelefones(),
            marceneiro.getEnderecos(),
            marceneiro.getLogomarcaFilename(),
            marceneiro.getLogomarcaUrl(),
            marceneiro.getCreatedBy().toString(),
            marceneiro.getCreatedAt().format(ofPattern("dd/MM/yyyy HH:mm:ss")),
            marceneiro.getUpdatedBy().toString(),
            marceneiro.getUpdatedAt().format(ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}